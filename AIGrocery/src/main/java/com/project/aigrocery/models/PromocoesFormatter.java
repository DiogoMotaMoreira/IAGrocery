package com.project.aigrocery.models;

import java.util.List;
import com.project.aigrocery.models.PromocoesExistentes;
import com.project.aigrocery.models.Promocao;

public class PromocoesFormatter {

    // Função para formatar as promoções existentes
    public static String formatarPromocoes(PromocoesExistentes promocoesExistentes) {
        StringBuilder sb = new StringBuilder();
        sb.append("As promoções existentes são:\n");

        List<Promocao> promocoes = promocoesExistentes.get_promocoes(); // Acessando a lista de promoções
        int index = 1;

        // Iterando sobre as promoções e formatando as informações
        for (Promocao promocao : promocoes) {
            sb.append(index).append(". Nome: ").append(promocao.get_nome()).append("\n")
              .append("    Marca: ").append(promocao.get_marca()).append("\n")
              .append("    ID: ").append(promocao.get_id_prod()).append("\n")
              .append("    Preço: €").append(String.format("%.2f", promocao.get_preco())).append("\n")
              .append("    Quantidade em estoque: ").append(promocao.get_quant_arm()).append("\n")
              .append("    Categoria: ").append(formatarCategoria(promocao.get_categoria())).append("\n")
              .append("    Saudabilidade: ").append(promocao.get_tier_saudavel()).append(formatarSaudabilidade(promocao.get_tier_saudavel())).append("\n")
              .append("    Dieta: ").append(formatarDieta(promocao.get_tier_dieta())).append(" (").append(promocao.get_tier_dieta()).append(")").append("\n")
              .append("    Familiar: ").append(formatarFamiliar(promocao.get_tier_familiar())).append(" (").append(promocao.get_tier_familiar()).append(")").append("\n")
              .append("    Marca: ").append(formatarMarca(promocao.get_tier_marca())).append(" (").append(promocao.get_tier_marca()).append(")").append("\n")
              .append("    Sustentabilidade: ").append(promocao.get_tier_sustentavel()).append(formatarSustentabilidade(promocao.get_tier_sustentavel())).append("\n")
              .append("    Descrição da Promoção: ").append(promocao.get_descricao_promocao()).append("\n")
              .append("    Data Final: ").append(promocao.get_data_final()).append("\n\n");
            index++;
        }

        return sb.toString();
    }

    // Formatar a categoria
    private static String formatarCategoria(Categoria categoria) {
        switch (categoria) {
            case FRESCOS:
                return "Frescos";
            case LACTICINIOS_E_OVOS:
                return "Laticínios e Ovos";
            case CARNES_E_PROTEINAS:
                return "Carnes e Proteínas";
            case GRAOS_E_CEREAIS:
                return "Grãos e Cereais";
            case PAES_E_PANIFICACAO:
                return "Pães e Panificação";
            case BEBIDAS:
                return "Bebidas";
            case DOCES_E_SOBREMESAS:
                return "Doces e Sobremesas";
            case SNACKS_E_PETISCOS:
                return "Snacks e Petiscos";
            case TEMPEROS_E_CONDIMENTOS:
                return "Temperos e Condimentos";
            case CONGELADOS:
                return "Congelados";
            default:
                return "Geral";
        }
    }
    

    // Formatar a saudabilidade
    private static String formatarSaudabilidade(int tier) {
        return switch (tier) {
            case 5 -> " (Muito saudável)";
            case 4 -> " (Saudável)";
            case 3 -> " (Moderado)";
            case 2 -> " (Pouco saudável)";
            case 1 -> " (Não saudável)";
            default -> "";
        };
    }

    // Formatar a dieta
    private static String formatarDieta(int tier) {
        return switch (tier) {
            case 1 -> "Sem Glúten";
            case 2 -> "Sem Lactose";
            case 3 -> "Diabéticos";
            case 4 -> "Vegetariana";
            case 5 -> "Vegan";
            case 6 -> "Pescetariana";
            case 7 -> "Kosher";
            case 8 -> "Halal";
            case 9 -> "Geral";
            default -> "Desconhecida";
        };
    }

    // Formatar familiar
    private static String formatarFamiliar(int tier) {
        return switch (tier) {
            case 5 -> "Pack Familiar";
            case 4 -> "Pack Grande";
            case 3 -> "Médio";
            case 2 -> "Pequeno";
            case 1 -> "Individual";
            default -> "Desconhecido";
        };
    }

    // Formatar marca
    private static String formatarMarca(int tier) {
        return switch (tier) {
            case 5 -> "Premium";
            case 4 -> "Alta Qualidade";
            case 3 -> "Médio";
            case 2 -> "Econômico";
            case 1 -> "Baixo Custo";
            default -> "Desconhecido";
        };
    }

    // Formatar sustentabilidade
    private static String formatarSustentabilidade(int tier) {
        return switch (tier) {
            case 5 -> " (Muito sustentável)";
            case 4 -> " (Sustentável)";
            case 3 -> " (Neutro)";
            case 2 -> " (Pouco sustentável)";
            case 1 -> " (Insustentável)";
            default -> "";
        };
    }
}