package com.project.aigrocery.view;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.project.aigrocery.AI.Recomendation;
import com.project.aigrocery.models.*;
import com.project.aigrocery.modelsDAO.Banco;

public class AIGroceryApp {

    private static Banco banco;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private Color[] profileColors = {
            new Color(240, 253, 244), // green-100 for healthy
            new Color(239, 246, 255), // blue-100 for economic
            new Color(243, 232, 255), // purple-100 for gourmet
            new Color(254, 243, 199)  // amber-100 for lazy
    };



    private String promocoesParaSi;
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new AIGroceryApp().createAndShowGUI();
        });
    }
    
    private void createAndShowGUI() {
        banco = new Banco();

        // Set up the main frame
        mainFrame = new JFrame("AI Grocery");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        
        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Create logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Logo (placeholder)
        JLabel logoLabel = new JLabel(createLogoIcon());
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        // Title
        JLabel titleLabel = new JLabel("AI Grocery");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Select your user profile to get started");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(107, 114, 128)); // text-gray-600
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        logoPanel.add(titleLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        logoPanel.add(subtitleLabel);
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        
        // Profile buttons
        String[] profileNames = {"Healthy User", "Economic Family", "Gourmet Family", "Lazy Family"};
        String[] profileDescriptions = {
                "Nutritious and health-conscious options",
                "Budget-friendly family meal options",
                "Premium culinary experiences",
                "Quick and convenient meal solutions"
        };
        
        for (int i = 0; i < 4; i++) {
            final int index = i;
            JPanel buttonPanel = createProfileButton(profileNames[i], profileDescriptions[i], profileColors[i], index);
            buttonsPanel.add(buttonPanel);
        }
        
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    
    private JPanel createProfileButton(String name, String description, Color color, int profileIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(color);
        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(descLabel);
        
        panel.add(textPanel, BorderLayout.CENTER);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openProfilePage(profileIndex);
                
                Historico90Dias hist = null;
                if (banco.getUsers() != null && profileIndex >= 0 && profileIndex < banco.getUsers().size()) {
                    hist = banco.getUsers().get(profileIndex).getHistorico_90_dias();
                    if (hist != null) {
                        System.out.println("Profile " + profileIndex + " clicked!");
                    } else {
                        System.err.println("Historico90Dias is null for profile " + profileIndex);
                    }
                } else {
                    System.err.println("Invalid profile index or banco.getUsers() is null.");
                }
                System.out.println("Profile " + profileIndex + " clicked!");
                
                // Top recommended products
                List<String> tops = null;
                if (hist != null) {
                    tops = Recomendation.produtosEmAlta(3, hist);
                    System.out.println("Top recommended products: " + tops.toString());
                } else {
                    System.err.println("Cannot fetch recommended products as hist is null.");
                }
                if (tops != null) {
                    System.out.println("Top recommended products: " + tops.toString());
                }

                // Personalized recommendations
                List<Produto> produtosParaPromocao = Recomendation.produtosNecessitamPromocao(hist);
                for (Produto produto : produtosParaPromocao) {
                    System.out.println("Produto que necessita de promoção: " + produto.get_nome());
                }

                // Check for associated items with promotions
                PromocoesExistentes promExist = new PromocoesExistentes(banco.getPromocoes());
                List<String> produtosComPromocao = new ArrayList<>();
                try {
                    produtosComPromocao = Recomendation.produtosSimilaresComPromocao(produtosParaPromocao, promExist);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                
                    StringBuilder sb = new StringBuilder();
                    for (String produto : produtosComPromocao) {
                        sb.append("Produtos com promoção similares: ");
                        if (sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(produto);
                    }
                    promocoesParaSi = sb.toString();
                    
                System.out.println("Produtos com promoção similares: " + promocoesParaSi);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(color.darker());
                textPanel.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(color);
                textPanel.setBackground(color);
            }
        });
        
        return panel;
    }
    
    private void openProfilePage(int profileIndex) {
        String[] profileNames = {"Healthy User", "Economic Family", "Gourmet Family", "Lazy Family"};
        String[] profileIds = {"healthy", "economic", "gourmet", "lazy"};
        
        JFrame profileFrame = new JFrame(profileNames[profileIndex]);
        profileFrame.setSize(900, 700);
        profileFrame.setLocationRelativeTo(mainFrame);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(profileColors[profileIndex]);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Back button
        JButton backButton = new JButton("Back to profiles");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(event -> profileFrame.dispose());
        
        // Title
        JLabel titleLabel = new JLabel(profileNames[profileIndex]);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description
        String[] descriptions = {
                "Personalized nutritious options for health-conscious individuals",
                "Budget-friendly meal plans for the whole family",
                "Premium culinary experiences for food enthusiasts",
                "Quick and convenient meal solutions with minimal effort"
        };
        JLabel descLabel = new JLabel(descriptions[profileIndex]);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(new Color(107, 114, 128));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Purchase history title
        JLabel historyTitle = new JLabel("Últimas 3 Semanas de Compras");
        historyTitle.setFont(new Font("Arial", Font.BOLD, 18));
        historyTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Purchase history table
        JPanel tablePanel = createPurchaseHistoryTable(profileIds[profileIndex]);
        tablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tablePanel.getPreferredSize().height));
        
        // AI Insights section
        JPanel insightsPanel = createAIInsightsPanel(profileIds[profileIndex]);
        insightsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Button at bottom
        JButton detailsButton = new JButton("Ver Detalhes Completos");
        detailsButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(detailsButton);
        
        // Add components to content panel
        contentPanel.add(backButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(historyTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(tablePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(insightsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonPanel);
        
        // Add scroll pane for content
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        profileFrame.add(mainPanel);
        profileFrame.setVisible(true);
    }
    
    private JPanel createPurchaseHistoryTable(String profileType) {
        // Get purchase history data
        Object[][] data = getPurchaseHistoryData(profileType);
        
        // Column names
        String[] columnNames = {"Data", "Itens Comprados", "Total"};
        
        // Create table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Create table
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowGrid(true);
        table.setGridColor(new Color(229, 231, 235));
        
        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(500);
        columnModel.getColumn(2).setPreferredWidth(100);
        
        // Right-align the total column
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(2).setCellRenderer(rightRenderer);
        
        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        
        // Create panel to hold the table
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private Object[][] getPurchaseHistoryData(String profileType) {
        // Generate dates for the last 3 weeks
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        String[] weeks = new String[3];
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        weeks[2] = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        weeks[1] = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        weeks[0] = dateFormat.format(calendar.getTime());
        
        // Define purchase data for each profile
        Map<String, Object[][]> purchaseData = new HashMap<>();
        
        // Healthy profile
        purchaseData.put("healthy", new Object[][]{
                {weeks[0], "Quinoa, Kale, Avocados, Greek Yogurt, Almonds, Salmon, Blueberries", "R$ 187,50"},
                {weeks[1], "Spinach, Chia Seeds, Sweet Potatoes, Chicken Breast, Broccoli, Olive Oil, Apples", "R$ 165,20"},
                {weeks[2], "Oats, Almond Milk, Bananas, Eggs, Bell Peppers, Tofu, Mixed Berries", "R$ 142,75"}
        });
        
        // Economic profile
        purchaseData.put("economic", new Object[][]{
                {weeks[0], "Rice, Beans, Pasta, Chicken Thighs, Potatoes, Onions, Frozen Vegetables", "R$ 98,30"},
                {weeks[1], "Ground Beef, Bread, Eggs, Milk, Bananas, Tomatoes, Flour", "R$ 112,45"},
                {weeks[2], "Canned Tuna, Carrots, Apples, Peanut Butter, Oats, Cheese, Chicken Drumsticks", "R$ 105,80"}
        });
        
        // Gourmet profile
        purchaseData.put("gourmet", new Object[][]{
                {weeks[0], "Filet Mignon, Truffle Oil, Saffron, Aged Balsamic Vinegar, Artisanal Cheese, Fresh Herbs, Imported Wine", "R$ 385,60"},
                {weeks[1], "Duck Breast, Wild Mushrooms, Organic Honey, Smoked Salmon, Specialty Olives, Artisan Bread, Gourmet Chocolate", "R$ 412,90"},
                {weeks[2], "Wagyu Beef, Fresh Scallops, Organic Berries, Specialty Coffee, Aged Cheese, Prosciutto, Champagne", "R$ 456,25"}
        });
        
        // Lazy profile
        purchaseData.put("lazy", new Object[][]{
                {weeks[0], "Frozen Pizza, Instant Noodles, Microwave Meals, Cereal, Pre-cut Fruit, Protein Bars, Juice Boxes", "R$ 143,20"},
                {weeks[1], "Frozen Lasagna, Canned Soup, Sandwich Bread, Deli Meat, Pre-washed Salad, Instant Coffee, Granola Bars", "R$ 128,75"},
                {weeks[2], "TV Dinners, Instant Rice, Frozen Burritos, Ready-to-eat Chicken, Bagged Chips, Energy Drinks, Cookies", "R$ 156,40"}
        });
        
        return purchaseData.getOrDefault(profileType, new Object[0][0]);
    }
    
    private JPanel createAIInsightsPanel(String profileType) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(249, 250, 251)); // bg-gray-50
        panel.setBorder(BorderFactory.createCompoundBorder(
                new DashedBorder(new Color(209, 213, 219), 1, 5), // border-dashed border-gray-300
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Title with icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        titlePanel.setBackground(new Color(249, 250, 251));
        
        JLabel iconLabel = new JLabel(createInsightIcon());
        JLabel titleLabel = new JLabel("Insights da IA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);
        
        // Make title panel full width
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, titlePanel.getPreferredSize().height));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titlePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        System.out.println(promocoesParaSi);
        // Add insights based on profile type
        if ("healthy".equals(profileType)) {
            panel.add(createInsightCard("Produtos Mais Requisitados", 
                    "Abacates, quinoa e vegetais de folhas verdes são seus itens mais frequentes. Considere comprar em maior quantidade para economizar.", 
                    new Color(22, 163, 74)));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(createInsightCard("Sugestão Nutricional", 
                    "Suas compras são ricas em proteínas e gorduras saudáveis, mas poderia aumentar a variedade de vegetais coloridos para mais antioxidantes.", 
                    new Color(22, 163, 74)));
        } else if ("economic".equals(profileType)) {
            panel.add(createInsightCard("Produtos Mais Requisitados", 
                    "Arroz, feijão e frango são seus itens básicos. Considere comprar a granel ou em promoções sazonais para economizar mais.", 
                    new Color(29, 78, 216)));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(createInsightCard("Economia Potencial", 
                    "Substituir algumas marcas por alternativas mais econômicas poderia reduzir seu gasto mensal em aproximadamente 15%.", 
                    new Color(29, 78, 216)));
        } else if ("gourmet".equals(profileType)) {
            panel.add(createInsightCard("Produtos Mais Requisitados", 
                    "Queijos artesanais, carnes premium e azeites especiais dominam suas compras. Há uma nova seleção de trufas disponível que combinaria com seu perfil.", 
                    new Color(126, 34, 206)));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(createInsightCard("Tendências Culinárias", 
                    "Baseado em suas compras, você pode se interessar pela nova coleção de especiarias mediterrâneas que acabou de chegar.", 
                    new Color(126, 34, 206)));
        } else if ("lazy".equals(profileType)) {
            panel.add(createInsightCard("Produtos Mais Requisitados", 
                    "Refeições congeladas, snacks e alimentos pré-preparados são seus favoritos. Novas opções de refeições de 3 minutos foram adicionadas ao catálogo.", 
                    new Color(217, 119, 6)));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            if (promocoesParaSi != null && !promocoesParaSi.isEmpty()) {
                panel.add(createInsightCard("Promoções para Si", 
                        promocoesParaSi, 
                        new Color(217, 119, 6)));
            } else {
                panel.add(createInsightCard("Promoções para Si", 
                        "Nenhuma promoção disponível no momento.", 
                        new Color(217, 119, 6)));
            }
        }
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Info note
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(239, 246, 255)); // bg-blue-50
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(191, 219, 254), 1), // border-blue-200
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel infoLabel = new JLabel("<html><i>Este espaço será atualizado automaticamente pela IA com insights personalizados baseados no seu histórico de compras.</i></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(29, 78, 216)); // text-blue-700
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, infoPanel.getPreferredSize().height));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(infoPanel);
        
        return panel;
    }
    
    private JPanel createInsightCard(String title, String content, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 13));
        contentArea.setForeground(new Color(107, 114, 128)); // text-gray-600
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(null);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(contentArea);
        
        // Make panel full width
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        return panel;
    }
    
    // Custom dashed border
    private static class DashedBorder extends LineBorder {
        private final float dash;
        
        public DashedBorder(Color color, int thickness, float dash) {
            super(color, thickness);
            this.dash = dash;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(lineColor);
            
            float[] dashPattern = {dash, dash};
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
            g2d.drawRect(x, y, width - 1, height - 1);
            g2d.dispose();
        }
    }
    
    // Create a simple logo icon
    private ImageIcon createLogoIcon() {
        int size = 100;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw circle background
        g2d.setColor(new Color(219, 234, 254)); // bg-blue-100
        g2d.fillOval(0, 0, size, size);
        
        // Draw stylized "AI" text
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.setColor(new Color(59, 130, 246)); // text-blue-500
        FontMetrics fm = g2d.getFontMetrics();
        String text = "AI";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (size - textWidth) / 2, size / 2 + textHeight / 4);
        
        // Draw a shopping cart icon
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(25, 65, 75, 65); // cart base
        g2d.drawLine(30, 55, 70, 55); // cart top
        g2d.drawLine(30, 55, 25, 65); // cart left side
        g2d.drawLine(70, 55, 75, 65); // cart right side
        
        // Draw cart wheels
        g2d.fillOval(35, 70, 10, 10);
        g2d.fillOval(65, 70, 10, 10);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    // Create insight icon
    private ImageIcon createInsightIcon() {
        int size = 20;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw lightning bolt
        g2d.setColor(new Color(59, 130, 246)); // blue-500
        int[] xPoints = {10, 15, 8, 12, 5, 10, 17};
        int[] yPoints = {2, 10, 10, 13, 18, 13, 5};
        g2d.fillPolygon(xPoints, yPoints, 7);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
}