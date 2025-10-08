package utils;

import entidades.Medico;
import entidades.Paciente;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    public static List<Paciente> carregarPacientes(String filename) {
        List<Paciente> pacientes = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            // Se o arquivo não existe, simplesmente retorna uma lista vazia.
            return pacientes;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Pula a linha do cabeçalho
            }

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                pacientes.add(Paciente.fromCSV(linha));
            }
        } catch (FileNotFoundException e) {
            // Este erro não deve acontecer por causa da verificação file.exists() acima.
        }
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
}