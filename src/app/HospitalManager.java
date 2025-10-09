package app;

import entidades.*;
import utils.CsvManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HospitalManager {

    // Nomes dos arquivos de dados
    private static final String PACIENTES_FILE = "pacientes.csv";
    private static final String MEDICOS_FILE = "medicos.csv";
    private static final String PLANOS_SAUDE_FILE = "planos.csv";
    private static final String CONSULTAS_FILE = "consultas.csv";
    private static final String INTERNACOES_FILE = "internacoes.csv";

    // Listas para guardar os dados em memória
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<PlanoDeSaude> planosDeSaude;
    private List<Consulta> consultas;
    private List<Internacao> internacoes;

    // --- Construtor ---
    public HospitalManager() {
        System.out.println("Iniciando sistema e carregando dados dos arquivos...");

        this.planosDeSaude = CsvManager.carregarPlanosDeSaude(PLANOS_SAUDE_FILE);
        this.medicos = CsvManager.carregarMedicos(MEDICOS_FILE);
        
        this.pacientes = CsvManager.carregarPacientes(PACIENTES_FILE, this.planosDeSaude);

        this.consultas = CsvManager.carregarConsultas(CONSULTAS_FILE, this.pacientes, this.medicos);
        this.internacoes = CsvManager.carregarInternacoes(INTERNACOES_FILE, this.pacientes, this.medicos);
        
        System.out.println("Carga de dados finalizada.");
    }
    
    // --- Persistência ---
    public void salvarDados() {
        System.out.println("Salvando todos os dados nos arquivos...");
        CsvManager.salvarPacientes(pacientes, PACIENTES_FILE);
        CsvManager.salvarMedicos(medicos, MEDICOS_FILE);
        CsvManager.salvarPlanosDeSaude(planosDeSaude, PLANOS_SAUDE_FILE);
        CsvManager.salvarConsultas(consultas, CONSULTAS_FILE);
        CsvManager.salvarInternacoes(internacoes, INTERNACOES_FILE);
        System.out.println("Dados salvos com sucesso!");
    }

    //  Métodos de Gerenciamento de Pacientes 

    public void cadastrarPaciente(Paciente paciente) {
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

    //  Métodos de Gerenciamento de Médicos 

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

    public Consulta agendarConsulta(Paciente paciente, Medico medico, LocalDateTime horario, String local) {
        if (!medico.verificarDisponibilidade(horario)) {
            System.out.println("Erro: O médico " + medico.getNome() + " já possui um agendamento neste horário.");
            return null; // Retorna nulo em caso de erro
        }
        
        for (Consulta c : this.consultas) {
            if (c.getLocal().equalsIgnoreCase(local) && c.getDataHora().equals(horario)) {
                 System.out.println("Erro: Já existe uma consulta agendada para este local e horário.");
                 return null; // Retorna nulo em caso de erro
            }
        }

        Consulta novaConsulta = new Consulta(paciente, medico, horario, local);
        this.consultas.add(novaConsulta);
        medico.agendarHorario(horario);
        
        System.out.println("Consulta agendada com sucesso para " + paciente.getNome() + " com Dr(a). " + medico.getNome() + ".");
        return novaConsulta; 
    }
    // --- Getters para as listas (úteis para os relatórios) ---
    public List<Paciente> getPacientes() {
        return pacientes;
    }
    
    public List<Medico> getMedicos() {
        return medicos;
    }

    // Dentro da classe HospitalManager

    // --- Métodos de Gerenciamento de Planos de Saúde ---

    public void cadastrarPlanoDeSaude(PlanoDeSaude plano) {
        this.planosDeSaude.add(plano);
        System.out.println("Plano de Saúde '" + plano.getNome() + "' cadastrado com sucesso!");
    }

    public PlanoDeSaude buscarPlanoPorNome(String nome) {
        for (PlanoDeSaude plano : this.planosDeSaude) {
            if (plano.getNome().equalsIgnoreCase(nome)) {
                return plano;
            }
        }
        return null;
    }

    public void listarPlanosDeSaude() {
        System.out.println("\n--- Planos de Saúde Disponíveis ---");
        if (planosDeSaude.isEmpty()) {
            System.out.println("Nenhum plano de saúde cadastrado.");
            return;
        }
        for (PlanoDeSaude plano : this.planosDeSaude) {
            System.out.println("- " + plano.getNome());
        }
    }

    // --- Métodos de Gerenciamento de Internações 

    /**
     * Registra uma nova internação, validando se o quarto está vago.
     */
    public void registrarInternacao(Paciente paciente, Medico medico, String quarto) {
        // Validação: Verifica se o quarto já está ocupado por um paciente não liberado
        for (Internacao internacaoExistente : this.internacoes) {
            if (internacaoExistente.getQuarto().equalsIgnoreCase(quarto) && internacaoExistente.getDataSaida() == null) {
                System.out.println("Erro: O quarto " + quarto + " já está ocupado pelo paciente " + internacaoExistente.getPaciente().getNome() + ".");
                return; // Interrompe a operação
            }
        }

        // Se o quarto está livre, cria a nova internação
        LocalDate dataEntrada = LocalDate.now(); // Pega a data atual
        Internacao novaInternacao = new Internacao(paciente, medico, dataEntrada, quarto);

        this.internacoes.add(novaInternacao);
        paciente.adicionarInternacaoAoHistorico(novaInternacao); // Adiciona ao histórico do paciente

        System.out.println("Paciente " + paciente.getNome() + " internado com sucesso no quarto " + quarto + ".");
    }

    /**
     * Exibe a lista de todas as internações (ativas e passadas).
     */
    public void listarInternacoes() {
        System.out.println("\n--- Histórico de Internações ---");
        if (internacoes.isEmpty()) {
            System.out.println("Nenhuma internação registrada.");
            return;
        }
        for (Internacao i : this.internacoes) {
            String status = (i.getDataSaida() == null) ? "ATIVO" : "FINALIZADO";
            System.out.println("Paciente: " + i.getPaciente().getNome() + 
                               " | Quarto: " + i.getQuarto() + 
                               " | Data Entrada: " + i.getDataEntrada() +
                               " | Status: " + status);
        }
    }

     public void finalizarInternacao(String cpfPaciente) {
        Internacao internacaoAtiva = null;

        // Procura pela internação ativa do paciente com o CPF fornecido
        for (Internacao internacao : this.internacoes) {
            if (internacao.getPaciente().getCpf().equals(cpfPaciente) && internacao.getDataSaida() == null) {
                internacaoAtiva = internacao;
                break; 
            }
        }

        // Se uma internação ativa foi encontrada, registra a alta
        if (internacaoAtiva != null) {
            internacaoAtiva.setDataSaida(LocalDate.now()); // Define a data de saída como a data atual
            System.out.println("Alta registrada para o paciente " + internacaoAtiva.getPaciente().getNome() + ".");
            System.out.println("O quarto " + internacaoAtiva.getQuarto() + " agora está livre.");
        } else {
            System.out.println("Erro: Nenhuma internação ativa encontrada para o paciente com CPF " + cpfPaciente + ".");
        }
    }
    // --- Métodos de Relatórios ---

    public void relatorioPacientesInternados() {
        System.out.println("\n--- RELATÓRIO: PACIENTES INTERNADOS ATUALMENTE ---");
        
        boolean encontrouAlgum = false;
        for (Internacao internacao : this.internacoes) {
            // A condição para uma internação estar ativa é a data de saída ser nula
            if (internacao.getDataSaida() == null) {
                
                // Calcula há quantos dias o paciente está internado
                long diasInternado = java.time.temporal.ChronoUnit.DAYS.between(internacao.getDataEntrada(), java.time.LocalDate.now());

                System.out.println(
                    "Paciente: " + internacao.getPaciente().getNome() +
                    " | Quarto: " + internacao.getQuarto() +
                    " | Médico: Dr(a). " + internacao.getMedicoResponsavel().getNome() +
                    " | Dias de Internação: " + diasInternado
                );
                encontrouAlgum = true;
            }
        }

        if (!encontrouAlgum) {
            System.out.println("Nenhum paciente internado no momento.");
        }
        System.out.println("----------------------------------------------------");
    }

    public void relatorioConsultasFuturas() {
        System.out.println("\n--- RELATÓRIO: CONSULTAS AGENDADAS FUTURAS ---");
        LocalDateTime agora = LocalDateTime.now();
        boolean encontrouAlguma = false;

        for (Consulta consulta : this.consultas) {
            // Verifica se a data da consulta é DEPOIS de agora
            if (consulta.getDataHora().isAfter(agora)) {
                System.out.println(consulta.toString());
                encontrouAlguma = true;
            }
        }

        if (!encontrouAlguma) {
            System.out.println("Nenhuma consulta futura agendada.");
        }
        System.out.println("---------------------------------------------");
    }

    public void relatorioConsultasPassadas() {
        System.out.println("\n--- RELATÓRIO: CONSULTAS JÁ REALIZADAS/PASSADAS ---");
        LocalDateTime agora = LocalDateTime.now();
        boolean encontrouAlguma = false;

        for (Consulta consulta : this.consultas) {
            // Verifica se a data da consulta é ANTES de agora
            if (consulta.getDataHora().isBefore(agora)) {
                System.out.println(consulta.toString());
                encontrouAlguma = true;
            }
        }

        if (!encontrouAlguma) {
            System.out.println("Nenhum histórico de consultas passadas.");
        }
        System.out.println("--------------------------------------------------");
    }
}