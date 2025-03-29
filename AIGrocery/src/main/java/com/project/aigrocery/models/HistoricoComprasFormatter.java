package com.project.aigrocery.models;

import java.util.List;

import com.project.aigrocery.models.produto.Categoria;

public class HistoricoComprasFormatter {

    public static String formatarHistorico(Historico90Dias historico) {
        StringBuilder sb = new StringBuilder();
        sb.append("O nosso usuário nos últimos 90 dias comprou:\n");

        List<produto> compras = historico.get_produtos();
        int index = 1;

        for (produto p : compras) {
            sb.append(index).append("️ Nome: ").append(p.get_nome()).append("\n\n")
              .append("    Marca: ").append(p.get_marca()).append("\n\n")
              .append("    ID: ").append(p.get_id_prod()).append("\n\n")
              .append("    Preço: €").append(String.format("%.2f", p.get_preco())).append("\n\n")
              .append("    Quantidade em estoque: ").append(p.get_quant_arm()).append("\n\n")
              .append("    Categoria: ").append(formatarCategoria(p.get_categoria())).append("\n\n")
              .append("    Saudabilidade: ").append(p.get_tier_saudavel()).append(formatarSaudabilidade(p.get_tier_saudavel())).append("\n\n")
              .append("    Dieta: ").append(formatarDieta(p.get_tier_dieta())).append(" (").append(p.get_tier_dieta()).append(")").append("\n\n")
              .append("    Familiar: ").append(formatarFamiliar(p.get_tier_familiar())).append(" (").append(p.get_tier_familiar()).append(")").append("\n\n")
              .append("    Marca: ").append(formatarMarca(p.get_tier_marca())).append(" (").append(p.get_tier_marca()).append(")").append("\n\n")
              .append("    Sustentabilidade: ").append(p.get_tier_sustentavel()).append(formatarSustentabilidade(p.get_tier_sustentavel())).append("\n\n");
            index++;
        }

        return sb.toString();
    }

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