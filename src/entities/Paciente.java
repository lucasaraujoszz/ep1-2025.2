package entities;

public class Paciente extends Pessoa {
    private int idade;

    public Paciente(String nome, String cpf, int idade) {
        super(nome, cpf); // chama o construtor da classe Pessoa
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Paciente [" + super.toString() + ", Idade: " + idade + "]";
    }
}
