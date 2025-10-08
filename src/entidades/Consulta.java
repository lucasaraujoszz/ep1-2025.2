package entities;

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
}