package entidades;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlanoDeSaude implements Serializable {

    private String nome;
    // Um mapa para guardar o desconto para cada especialidade. Ex: {"Cardiologia", 0.50}
    private Map<String, Double> descontosPorEspecialidade;

    public PlanoDeSaude(String nome) {
        this.nome = nome;
        this.descontosPorEspecialidade = new HashMap<>();
    }

    // Métodos de Gerenciamento de Descontos 

    /**
     * Adiciona ou atualiza um desconto para uma especialidade específica.
     * @param especialidade O nome da especialidade (ex: "Cardiologia").
     * @param desconto O valor do desconto (ex: 0.3 para 30%).
     */
      public void adicionarDesconto(String especialidade, double desconto) {
        this.descontosPorEspecialidade.put(especialidade.trim().toLowerCase(), desconto);
    }

    /**
     * Retorna o desconto para uma dada especialidade.
     * @param especialidade O nome da especialidade.
     * @return O valor do desconto, ou 0.0 se não houver desconto específico.
     */
     public double getDesconto(String especialidade) {
        return this.descontosPorEspecialidade.getOrDefault(especialidade.trim().toLowerCase(), 0.0);
    }

    // --- Getters e Setters ---
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String toCSV() {
        return getNome();
    }

    public static PlanoDeSaude fromCSV(String csvLine) {
        return new PlanoDeSaude(csvLine);
    }
}