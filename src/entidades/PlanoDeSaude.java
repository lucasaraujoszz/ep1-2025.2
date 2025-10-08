package entidades;

import java.io.Serializable;

public class PlanoDeSaude implements Serializable {

    private String nome;

    // Construtor
    public PlanoDeSaude(String nome) {
        this.nome = nome;
    }

    // Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "PlanoDeSaude [nome=" + nome + "]";
    }

    // Formato CSV: nomeDoPlano
    public String toCSV() {
        return getNome();
    }

    public static PlanoDeSaude fromCSV(String csvLine) {
        return new PlanoDeSaude(csvLine);
    }
}