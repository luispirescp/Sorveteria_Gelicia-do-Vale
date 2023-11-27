package br.com.cursojava.petshop.model;

public enum Tipo {
    GELADOS("Gelados"), BEBIDA("Bebida"), LIMPEZA("Limpeza"), OUTROS("Outros");

    private final String descricao;
    Tipo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Tipo fromString(String texto) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.descricao.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado para: " + texto);
    }
}
