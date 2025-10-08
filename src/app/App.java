package app;

import entidades.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HospitalManager manager = new HospitalManager();
        boolean executando = true;

        while (executando) {
            exibirMenu();
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do scanner

                switch (opcao) {
                    case 1:
                        cadastrarNovoPaciente(scanner, manager);
                        break;
                    case 2:
                        manager.listarPacientes();
                        break;
                    case 3:
                        cadastrarNovoMedico(scanner, manager);
                        break;
                    case 4:
                        manager.listarMedicos();
                        break;
                    case 5:
                        agendarNovaConsulta(scanner, manager);
                        break;
                    case 6: 
                    cadastrarNovoPlanoDeSaude(scanner, manager);
                        break;
                    case 7: 
                        registrarNovaInternacao(scanner, manager);
                        break;
                    case 8: 
                        manager.listarInternacoes();
                        break;
                    case 9: 
                        darAltaPaciente(scanner, manager);
                        break;
                    case 0:
                        manager.salvarDados();
                        System.out.println("Saindo do sistema... Até logo!");
                        executando = false; // Encerra o loop
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número válido para a opção.");
                scanner.nextLine(); // Limpa o buffer do scanner em caso de erro
            }
        }
        scanner.close(); // Fecha o scanner ao final do programa
    }

    private static void exibirMenu() {
        System.out.println("\n--- SISTEMA DE GERENCIAMENTO HOSPITALAR ---");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Cadastrar Médico");
        System.out.println("4. Listar Médicos");
        System.out.println("5. Agendar Consulta");
        System.out.println("6. Cadastrar Novo Plano de Saúde");
        System.out.println("7. Registrar Internação");
        System.out.println("8. Listar Internações");
        System.out.println("9. Dar Alta a Paciente");
        // Futuras opções: Registrar Internação, Relatórios, etc.
        System.out.println("0. Sair e Salvar");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarNovoPaciente(Scanner scanner, HospitalManager manager) {
        try {
            System.out.println("\n--- Cadastro de Novo Paciente ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            System.out.print("O paciente possui plano de saúde? (s/n): ");
            String temPlano = scanner.nextLine();

            Paciente novoPaciente; // Declara a variável aqui

            if (temPlano.equalsIgnoreCase("s")) {
                manager.listarPlanosDeSaude(); // Mostra os planos disponíveis
                System.out.print("Digite o nome do plano de saúde: ");
                String nomePlano = scanner.nextLine();
                PlanoDeSaude plano = manager.buscarPlanoPorNome(nomePlano);

                if (plano == null) {
                    System.out.println("Erro: Plano de saúde não encontrado. O paciente será cadastrado como comum.");
                    novoPaciente = new Paciente(nome, cpf, idade);
                } else {
                    // Se o plano foi encontrado, cria um PacienteEspecial
                    novoPaciente = new PacienteEspecial(nome, cpf, idade, plano);
                }
            } else {
                // Se não tem plano, cria um Paciente comum
                novoPaciente = new Paciente(nome, cpf, idade);
            }
            
            manager.cadastrarPaciente(novoPaciente);

        } catch (InputMismatchException e) {
            System.out.println("Erro: Idade deve ser um número.");
            scanner.nextLine();
        }
    }

    private static void cadastrarNovoMedico(Scanner scanner, HospitalManager manager) {
        try {
            System.out.println("\n--- Cadastro de Novo Médico ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("CRM: ");
            String crm = scanner.nextLine();
            System.out.print("Especialidade: ");
            String especialidade = scanner.nextLine();
            System.out.print("Custo da Consulta (ex: 150.00): ");
            double custo = scanner.nextDouble();
            scanner.nextLine();

            Medico novoMedico = new Medico(nome, cpf, crm, especialidade, custo);
            manager.cadastrarMedico(novoMedico);
        } catch (InputMismatchException e) {
            System.out.println("Erro: Custo da consulta deve ser um número.");
            scanner.nextLine();
        }
    }

    private static void agendarNovaConsulta(Scanner scanner, HospitalManager manager) {
        System.out.println("\n--- Agendamento de Nova Consulta ---");
        System.out.print("Digite o CPF do paciente: ");
        String cpf = scanner.nextLine();
        Paciente paciente = manager.buscarPacientePorCpf(cpf);

        if (paciente == null) {
            System.out.println("Erro: Paciente com CPF " + cpf + " não encontrado.");
            return;
        }

        System.out.print("Digite o CRM do médico: ");
        String crm = scanner.nextLine();
        Medico medico = manager.buscarMedicoPorCrm(crm);

        if (medico == null) {
            System.out.println("Erro: Médico com CRM " + crm + " não encontrado.");
            return;
        }

        LocalDateTime dataHora;
        try {
        
            System.out.print("Digite a data da consulta (formato DD/MM/AAAA): ");
            String dataStr = scanner.nextLine();
            
            // Pede a hora no formato 24h
            System.out.print("Digite a hora da consulta (formato HH:MM): ");
            String horaStr = scanner.nextLine();

            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Converte as strings de texto em objetos de Data e Hora
            java.time.LocalDate localDate = java.time.LocalDate.parse(dataStr, dateFormatter);
            java.time.LocalTime localTime = java.time.LocalTime.parse(horaStr);
            
            dataHora = localDate.atTime(localTime);

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data ou hora inválido. Use os formatos DD/MM/AAAA e HH:MM.");
            return;
        }

        System.out.print("Digite o local da consulta (ex: Consultório 101): ");
        String local = scanner.nextLine();

        Consulta novaConsulta = manager.agendarConsulta(paciente, medico, dataHora, local);

        if (novaConsulta != null) {
            double custoFinal = novaConsulta.calcularCusto();
            System.out.println("Custo final da consulta: R$ " + String.format("%.2f", custoFinal));
        }
    }

   
    private static void cadastrarNovoPlanoDeSaude(Scanner scanner, HospitalManager manager) {
        System.out.println("\n--- Cadastro de Novo Plano de Saúde ---");
        System.out.print("Nome do Plano: ");
        String nome = scanner.nextLine();
        PlanoDeSaude novoPlano = new PlanoDeSaude(nome);
        manager.cadastrarPlanoDeSaude(novoPlano);
    }

    private static void registrarNovaInternacao(Scanner scanner, HospitalManager manager) {
        System.out.println("\n--- Registrar Nova Internação ---");
        System.out.print("Digite o CPF do paciente a ser internado: ");
        String cpf = scanner.nextLine();
        Paciente paciente = manager.buscarPacientePorCpf(cpf);

        if (paciente == null) {
            System.out.println("Erro: Paciente com CPF " + cpf + " não encontrado.");
            return;
        }

        System.out.print("Digite o CRM do médico responsável: ");
        String crm = scanner.nextLine();
        Medico medico = manager.buscarMedicoPorCrm(crm);

        if (medico == null) {
            System.out.println("Erro: Médico com CRM " + crm + " não encontrado.");
            return;
        }

        System.out.print("Digite o número do quarto para internação: ");
        String quarto = scanner.nextLine();

        manager.registrarInternacao(paciente, medico, quarto);
    }

    private static void darAltaPaciente(Scanner scanner, HospitalManager manager) {
        System.out.println("\n--- Dar Alta a Paciente ---");
        System.out.print("Digite o CPF do paciente que receberá alta: ");
        String cpf = scanner.nextLine();
        
        manager.finalizarInternacao(cpf);
    }
}