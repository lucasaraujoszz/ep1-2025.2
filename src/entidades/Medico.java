package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa{

    private String crm;
    private String especialidade;
    private double custoDaConsulta;
    private List<LocalDateTime> horariosOcupados;
    private int numeroDeConsultasRealizadas;

    public Medico(String nome, String cpf, String crm, String especialidade, double custoDaConsulta) {
        super(nome, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoDaConsulta = custoDaConsulta;
        this.horariosOcupados = new ArrayList<>();
        this.numeroDeConsultasRealizadas = 0;
    }

    public boolean verificarDisponibilidade(LocalDateTime horario) {
        return !this.horariosOcupados.contains(horario);
    }

    public void agendarHorario(LocalDateTime horario) {
        this.horariosOcupados.add(horario);
    }

    public void cancelarHorario(LocalDateTime horario) {
        this.horariosOcupados.remove(horario);
    }

    public void registrarConsultaRealizada() {
        this.numeroDeConsultasRealizadas++;
    }

    // Getters e Setters
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getCustoDaConsulta() {
        return custoDaConsulta;
    }

    public void setCustoDaConsulta(double custoDaConsulta) {
        this.custoDaConsulta = custoDaConsulta;
    }

    public List<LocalDateTime> getHorariosOcupados() {
        return horariosOcupados;
    }

    public int getNumeroDeConsultasRealizadas() {
        return numeroDeConsultasRealizadas;
    }

    @Override
    public String toString() {
        return "Médico: " + getNome() +
               " | CRM: " + crm +
               " | Especialidade: " + especialidade +
               " | Custo da Consulta: R$ " + String.format("%.2f", custoDaConsulta);
    }
}