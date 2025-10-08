package entidades;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {

    private int idade;
    private List<Consulta> historicoConsultas;
    private List<Internacao> historicoInternacoes;

    // Construtor
    public Paciente(String nome, String cpf, int idade) {
        super(nome, cpf);
        this.idade = idade;
        // Inicializa as listas para evitar erros
        this.historicoConsultas = new ArrayList<>();
        this.historicoInternacoes = new ArrayList<>();
    }

    // Getters e Setters...
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Métodos para gerenciar o histórico
    public List<Consulta> getHistoricoConsultas() {
        return historicoConsultas;
    }

    public void adicionarConsultaAoHistorico(Consulta consulta) {
        this.historicoConsultas.add(consulta);
    }

     public List<Internacao> getHistoricoInternacoes() {
        return historicoInternacoes;
    }

    public void adicionarInternacaoAoHistorico(Internacao internacao) {
        this.historicoInternacoes.add(internacao);
    }


    @Override
    public String toString() {
        return "Paciente [" + super.toString() + ", Idade=" + idade + "]";
    }
    
    // Formato CSV: cpf;nome;idade
    public String toCSV() {
        return getCpf() + ";" + getNome() + ";" + getIdade();
    }

    public static Paciente fromCSV(String csvLine) {
        String[] fields = csvLine.split(";");
        return new Paciente(fields[1], fields[0], Integer.parseInt(fields[2]));
    }
}