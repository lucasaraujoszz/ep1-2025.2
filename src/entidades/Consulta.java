package entidades;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Consulta implements Serializable {

    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private String status;
    private String diagnostico;
    private String prescricao;

    // --- Construtor ---
    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = "Agendada";
        this.diagnostico = "";
        this.prescricao = "";
    }

    // --- Getters e Setters ---
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    // toString
    @Override
    public String toString() {
        return "Consulta [Paciente=" + paciente.getNome() + ", Medico=" + medico.getNome() + ", Data=" + dataHora + ", Status=" + status + "]";
    }

    // Formato CSV: cpfPaciente;crmMedico;dataHora;local;status
    public String toCSV() {
        return getPaciente().getCpf() + ";" + 
               getMedico().getCrm() + ";" + 
               getDataHora().toString() + ";" + 
               getLocal() + ";" + 
               getStatus();
    }
    // O método fromCSV para Consulta será mais complexo e ficará dentro do CsvManager.

    /**
     * Calcula o custo final da consulta, aplicando regras de desconto.
     * @return O valor final da consulta em double.
     */
    public double calcularCusto() {
        double custoBase = this.getMedico().getCustoDaConsulta();
        Paciente paciente = this.getPaciente();

        System.out.println("Custo base da consulta: R$ " + String.format("%.2f", custoBase));

        // Regra 1: Desconto para pacientes com 60+ anos
        if (paciente.getIdade() >= 60) {
            double desconto = custoBase * 0.20; 
            custoBase -= desconto;
            System.out.println("Aplicado desconto de 20% por idade (60+): - R$ " + String.format("%.2f", desconto));
        }

        // Futuramente, aqui entrará a lógica de desconto do plano de saúde (polimorfismo)

        return custoBase;
    }
}