package br.com.hospital.model; // Ou o pacote que você escolheu para suas classes de modelo

import java.io.Serializable;

public class Pessoa implements Serializable {
    private String nome;
    private String cpf;

    // Construtor
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
               "nome='" + nome + '\'' +
               ", cpf='" + cpf + '\'' +
               '}';
    }
}