package utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.Cultivo;

public class CsvParser {
    public static Cultivo parse(String linea) {
        // Dividimos en 8 partes: primeros 7 campos y luego la lista de actividades completa
        String[] parts = linea.split(",", 8);
        if (parts.length < 8 || !"Cultivo".equalsIgnoreCase(parts[0].trim())) {
            return null;
        }
        // Eliminamos comillas de los campos
        String nombre = stripQuotes(parts[1].trim());
        String variedad = stripQuotes(parts[2].trim());
        double sup = Double.parseDouble(parts[3].trim());
        String parcela = stripQuotes(parts[4].trim());
        LocalDate fecha = LocalDate.parse(stripQuotes(parts[5].trim()));
        String estado = stripQuotes(parts[6].trim());

        // Procesamos lista de actividades: "[..."]"
        String raw = parts[7].trim();
        if (raw.startsWith("[")) raw = raw.substring(1);
        if (raw.endsWith("]")) raw = raw.substring(0, raw.length() - 1);
        List<String> acts = new ArrayList<>();
        if (!raw.isEmpty()) {
            // Separa en items sin perder comillas internas
            String[] items = raw.split("\",\"");
            for (String item : items) {
                // Limpiamos comillas al inicio y fin
                acts.add(item.replaceAll("^\"|\"$", ""));
            }
        }

        return new Cultivo(
            nombre,
            variedad,
            sup,
            parcela,
            fecha,
            estado,
            acts
        );
    }

    private static String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
