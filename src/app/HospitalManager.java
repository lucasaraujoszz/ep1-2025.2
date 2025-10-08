package app;

import entidades.*;
import utils.CsvManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HospitalManager {

    // Nomes dos arquivos onde os dados serão guardados
    private static final String PACIENTES_FILE = "pacientes.csv";
    private static final String MEDICOS_FILE = "medicos.csv";
    private static final String PLANOS_SAUDE_FILE = "planos.csv";
    private static final String CONSULTAS_FILE = "consultas.csv";
    private static final String INTERNACOES_FILE = "internacoes.csv";

    // Listas para guardar todos os dados em memória
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<PlanoDeSaude> planosDeSaude;
    private List<Consulta> consultas;
    private List<Internacao> internacoes;

    // Construtor
    public HospitalManager() {
        System.out.println("Iniciando sistema e carregando dados dos arquivos...");

        // Tenta carregar as listas a partir dos arquivos CSV
        this.pacientes = CsvManager.carregarPacientes(PACIENTES_FILE);
        this.medicos = CsvManager.carregarMedicos(MEDICOS_FILE);
        this.planosDeSaude = CsvManager.carregarPlanosDeSaude(PLANOS_SAUDE_FILE);
        this.consultas = CsvManager.carregarConsultas(CONSULTAS_FILE, this.pacientes, this.medicos);
        this.internacoes = CsvManager.carregarInternacoes(INTERNACOES_FILE, this.pacientes, this.medicos);
        
        System.out.println("Carga de dados finalizada.");
    }

    // Método para salvar tudo antes de fechar o programa.
    public void salvarDados() {
        System.out.println("Salvando todos os dados nos arquivos...");
        CsvManager.salvarPacientes(pacientes, PACIENTES_FILE);
        CsvManager.salvarMedicos(medicos, MEDICOS_FILE);
        CsvManager.salvarPlanosDeSaude(planosDeSaude, PLANOS_SAUDE_FILE);
        CsvManager.salvarConsultas(consultas, CONSULTAS_FILE);
        CsvManager.salvarInternacoes(internacoes, INTERNACOES_FILE);
        System.out.println("Dados salvos com sucesso!");
    }

    //  Métodos de Gerenciamento 

    public void cadastrarPaciente(Paciente paciente) {
        // Validação de CPF duplicado
        for (Paciente p : this.pacientes) {
            if (p.getCpf().equals(paciente.getCpf())) {
                System.out.println("Erro: CPF já cadastrado.");
                return;
            }
        }
        this.pacientes.add(paciente);
        System.out.println("Paciente '" + paciente.getNome() + "' cadastrado com sucesso!");
    }

    public void listarPacientes() {
        System.out.println("\n--- Lista de Pacientes Cadastrados ---");
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : this.pacientes) {
            System.out.println(p.toString());
        }
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        for (Paciente p : this.pacientes) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    // --- Métodos de Gerenciamento de Médicos ---

    public void cadastrarMedico(Medico medico) {
        for (Medico m : this.medicos) {
            if (m.getCrm().equals(medico.getCrm())) {
                System.out.println("Erro: CRM já cadastrado.");
                return;
            }
        }
        this.medicos.add(medico);
        System.out.println("Médico '" + medico.getNome() + "' cadastrado com sucesso!");
    }
    
    public void listarMedicos() {
        System.out.println("\n--- Lista de Médicos Cadastrados ---");
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
            return;
        }
        for (Medico m : this.medicos) {
            System.out.println(m.toString());
        }
    }

    public Medico buscarMedicoPorCrm(String crm) {
        for (Medico m : this.medicos) {
            if (m.getCrm().equals(crm)) {
                return m;
            }
        }
        return null;
    }

    //  Métodos de Gerenciamento de Consultas 

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDateTime horario, String local) {
        if (!medico.verificarDisponibilidade(horario)) {
            System.out.println("Erro: O médico " + medico.getNome() + " já possui um agendamento neste horário.");
            return;
        }
        
        for (Consulta c : this.consultas) {
            if (c.getLocal().equalsIgnoreCase(local) && c.getDataHora().equals(horario)) {
                 System.out.println("Erro: Já existe uma consulta agendada para este local e horário.");
                 return;
            }
        }

        Consulta novaConsulta = new Consulta(paciente, medico, horario, local);
        this.consultas.add(novaConsulta);
        medico.agendarHorario(horario);
        
        System.out.println("Consulta agendada com sucesso para " + paciente.getNome() + " com Dr(a). " + medico.getNome() + ".");
    }

}