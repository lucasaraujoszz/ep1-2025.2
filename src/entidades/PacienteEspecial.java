package entidades;

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
        String infoBaseDoPaciente = super.toString();
        
        // Adiciona a informação do plano de saúde de forma organizada
        return infoBaseDoPaciente + " | Plano: " + this.getPlanoDeSaude().getNome();
    }

     @Override
    public String toCSV() {
        // Pacientes especiais salvam seu tipo e o nome do plano de saúde
        return getCpf() + ";" + getNome() + ";" + getIdade() + ";ESPECIAL;" + getPlanoDeSaude().getNome();
    }
}
