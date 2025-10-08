package utils;

import entidades.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvManager {

    //  Métodos para Pacientes 

    public static void salvarPacientes(List<Paciente> pacientes, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("cpf;nome;idade"); // Escreve o cabeçalho

            for (Paciente paciente : pacientes) {
                writer.println(paciente.toCSV());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    public static List<Paciente> carregarPacientes(String filename, List<PlanoDeSaude> todosOsPlanos) {
        List<Paciente> pacientes = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return pacientes;

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Pula o cabeçalho
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(";");
                String cpf = fields[0];
                String nome = fields[1];
                int idade = Integer.parseInt(fields[2]);
                String tipo = fields[3];

                if (tipo.equals("ESPECIAL")) {
                    String nomePlano = fields[4];
                    
                    PlanoDeSaude planoEncontrado = null;
                    for (PlanoDeSaude plano : todosOsPlanos) {
                        if (plano.getNome().equals(nomePlano)) {
                            planoEncontrado = plano;
                            break; 
                        }
                    }
                    
                    if (planoEncontrado != null) {
                        pacientes.add(new PacienteEspecial(nome, cpf, idade, planoEncontrado));
                    }
                } else { // Se for "COMUM"
                    pacientes.add(new Paciente(nome, cpf, idade));
                }
            }
        } catch (FileNotFoundException e) { /* Ignorado */ }
        return pacientes;
    }

    //  Métodos para Médicos 

    public static void salvarMedicos(List<Medico> medicos, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("crm;nome;especialidade;custoConsulta"); // Cabeçalho

            for (Medico medico : medicos) {
                writer.println(medico.toCSV());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    public static List<Medico> carregarMedicos(String filename) {
        List<Medico> medicos = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return medicos;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Pula a linha do cabeçalho
            }

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                medicos.add(Medico.fromCSV(linha));
            }
        } catch (FileNotFoundException e) {
            // Não deve acontecer.
        }
        return medicos;
    }

        // Metodos para plano de saude
    public static void salvarPlanosDeSaude(List<PlanoDeSaude> planos, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("nome"); // Cabeçalho
            for (PlanoDeSaude plano : planos) {
                writer.println(plano.toCSV());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar planos de saúde: " + e.getMessage());
        }
    }

    public static List<PlanoDeSaude> carregarPlanosDeSaude(String filename) {
        List<PlanoDeSaude> planos = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return planos;

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Pula cabeçalho
            while (scanner.hasNextLine()) {
                planos.add(PlanoDeSaude.fromCSV(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) { /* Ignorado */ }
        return planos;
    }

    // --- Métodos para Consultas ---

    public static void salvarConsultas(List<Consulta> consultas, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("cpfPaciente;crmMedico;dataHora;local;status"); // Cabeçalho
            for (Consulta consulta : consultas) {
                writer.println(consulta.toCSV());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public static List<Consulta> carregarConsultas(String filename, List<Paciente> todosOsPacientes, List<Medico> todosOsMedicos) {
        List<Consulta> consultas = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return consultas;

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Pula cabeçalho
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(";");
                String cpfPaciente = fields[0];
                String crmMedico = fields[1];
                LocalDateTime dataHora = LocalDateTime.parse(fields[2]);
                String local = fields[3];
                String status = fields[4];

                // Lógica de busca 
                Paciente pacienteEncontrado = buscarPacientePorCpf(cpfPaciente, todosOsPacientes);
                Medico medicoEncontrado = buscarMedicoPorCrm(crmMedico, todosOsMedicos);

                if (pacienteEncontrado != null && medicoEncontrado != null) {
                    Consulta consulta = new Consulta(pacienteEncontrado, medicoEncontrado, dataHora, local);
                    consulta.setStatus(status);
                    consultas.add(consulta);
                }
            }
        } catch (FileNotFoundException e) { /* Ignorado */ }
        return consultas;
    }

    private static Paciente buscarPacientePorCpf(String cpf, List<Paciente> pacientes) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }
        return null; // Retorna nulo se não encontrar
    }

    private static Medico buscarMedicoPorCrm(String crm, List<Medico> medicos) {
        for (Medico medico : medicos) {
            if (medico.getCrm().equals(crm)) {
                return medico;
            }
        }
        return null; // Retorna nulo se não encontrar
    }

    // --- Métodos para Internações ---

    public static void salvarInternacoes(List<Internacao> internacoes, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("quarto;cpfPaciente;crmMedico;dataEntrada;dataSaida"); // Cabeçalho
            for (Internacao internacao : internacoes) {
                writer.println(internacao.toCSV());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar internações: " + e.getMessage());
        }
    }

    public static List<Internacao> carregarInternacoes(String filename, List<Paciente> todosOsPacientes, List<Medico> todosOsMedicos) {
        List<Internacao> internacoes = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return internacoes;

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); // Pula cabeçalho
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(";");
                String quarto = fields[0];
                String cpfPaciente = fields[1];
                String crmMedico = fields[2];
                LocalDate dataEntrada = LocalDate.parse(fields[3]);
                String dataSaidaStr = fields[4];

                // Reutiliza os métodos de busca que já criamos
                Paciente paciente = buscarPacientePorCpf(cpfPaciente, todosOsPacientes);
                Medico medico = buscarMedicoPorCrm(crmMedico, todosOsMedicos);

                if (paciente != null && medico != null) {
                    Internacao internacao = new Internacao(paciente, medico, dataEntrada, quarto);
                    
                    // Se a data de saída não for "N/A", a restaura
                    if (!dataSaidaStr.equals("N/A")) {
                        internacao.setDataSaida(LocalDate.parse(dataSaidaStr));
                    }
                    internacoes.add(internacao);
                }
            }
        } catch (FileNotFoundException e) { /* Ignorado */ }
        return internacoes;
    }
    
}

