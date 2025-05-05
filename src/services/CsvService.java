package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import models.Cultivo;
import utils.CsvParser;

public class CsvService {
    public List<Cultivo> leer(String ruta) {
        List<Cultivo> lista = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(ruta))) {
            String line;
            while ((line = br.readLine()) != null) {
                Cultivo c = CsvParser.parse(line);
                if (c != null) lista.add(c);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
        }
        return lista;
    }

    public void escribir(String ruta, List<Cultivo> datos) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(ruta))) {
            for (Cultivo c : datos) {
                bw.write(c.toCsvLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo CSV: " + e.getMessage());
        }
    }
}

