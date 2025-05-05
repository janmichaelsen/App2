package models;

import java.time.LocalDate;

public class Actividad implements ElementoAgricola {
    private String tipo;
    private LocalDate fecha;

    public Actividad(String tipo, LocalDate fecha) {
        this.tipo = tipo;
        this.fecha = fecha;
    }

    @Override
    public String getNombre() {
        return tipo;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toCsvLine() {
        return String.format(
            "Actividad,\"%s\",\"%s\"",
            tipo, fecha
        );
    }
}

