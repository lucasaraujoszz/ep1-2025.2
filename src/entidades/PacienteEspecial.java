package entities;

public class PacienteEspecial extends Paciente {

    // Atributo específico desta classe
    private PlanoDeSaude planoDeSaude;

    // Construtor
    public PacienteEspecial(String nome, String cpf, int idade, PlanoDeSaude planoDeSaude) {
        super(nome, cpf, idade); 
        this.planoDeSaude = planoDeSaude;
    }

    // Getter e Setter
    public PlanoDeSaude getPlanoDeSaude() {
        return planoDeSaude;
    }

    public void setPlanoDeSaude(PlanoDeSaude planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }

    @Override
    public String toString() {
        return "PacienteEspecial [" + super.toString() + ", planoDeSaude=" + planoDeSaude + "]";
    }
}
