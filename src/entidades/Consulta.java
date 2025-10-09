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
        double custoAtual = this.getMedico().getCustoDaConsulta();
        Paciente paciente = this.getPaciente();

        System.out.println("Custo base da consulta: R$ " + String.format("%.2f", custoAtual));

        // Aplica desconto para pacientes com 60+ anos
        if (paciente.getIdade() >= 60) {
            double descontoIdade = custoAtual * 0.20; // 20% de desconto
            custoAtual -= descontoIdade;
            System.out.println("Aplicado desconto de 20% por idade (60+): - R$ " + String.format("%.2f", descontoIdade));
        }

        // Aplica desconto por Plano de Saúde, se houver
        if (paciente instanceof PacienteEspecial) {
            PacienteEspecial pacienteEspecial = (PacienteEspecial) paciente;
            PlanoDeSaude plano = pacienteEspecial.getPlanoDeSaude();
            String especialidadeMedico = this.getMedico().getEspecialidade();
            double descontoPlano = plano.getDesconto(especialidadeMedico);

            if (descontoPlano > 0) {
                double valorDesconto = custoAtual * descontoPlano;
                custoAtual -= valorDesconto;
                System.out.println("Aplicado desconto de " + (descontoPlano * 100) + "% do plano '" + plano.getNome() + "': - R$ " + String.format("%.2f", valorDesconto));
            }
        }

        return custoAtual;
    }

}