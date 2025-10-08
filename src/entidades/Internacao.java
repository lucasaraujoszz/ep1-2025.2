package entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Internacao implements Serializable {

    private Paciente paciente;
    private Medico medicoResponsavel;
    private LocalDate dataEntrada;
    private LocalDate dataSaida; // Será nulo se o paciente ainda estiver internado
    private String quarto;
    private double custo;

    public Internacao(Paciente paciente, Medico medicoResponsavel, LocalDate dataEntrada, String quarto) {
        this.paciente = paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.dataEntrada = dataEntrada;
        this.quarto = quarto;
        this.dataSaida = null; 
        this.custo = 0.0;      
    }

    // Getters 
    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public String getQuarto() {
        return quarto;
    }

    public double getCusto() {
        return custo;
    }

    // Setters 
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedicoResponsavel(Medico medicoResponsavel) {
        this.medicoResponsavel = medicoResponsavel;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    // Formato CSV: quarto;cpfPaciente;crmMedico;dataEntrada;dataSaida
    public String toCSV() {
        String dataSaidaStr = (getDataSaida() == null) ? "N/A" : getDataSaida().toString();
        
        return getQuarto() + ";" +
               getPaciente().getCpf() + ";" +
               getMedicoResponsavel().getCrm() + ";" +
               getDataEntrada().toString() + ";" +
               dataSaidaStr;
    }
}