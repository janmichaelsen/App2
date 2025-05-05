package models;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Cultivo implements ElementoAgricola {
    private String nombre;
    private String variedad;
    private double superficie;
    private String parcelaCodigo;
    private LocalDate fechaSiembra;
    private String estado;
    private List<String> actividades;

    public Cultivo(String nombre, String variedad, double superficie,
                   String parcelaCodigo, LocalDate fechaSiembra,
                   String estado, List<String> actividades) {
        this.nombre        = nombre;
        this.variedad      = variedad;
        this.superficie    = superficie;
        this.parcelaCodigo = parcelaCodigo;
        this.fechaSiembra  = fechaSiembra;
        this.estado        = estado;
        this.actividades   = actividades;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public LocalDate getFecha() {
        return fechaSiembra;
    }

    // getters adicionales...
    public String getVariedad()       { return variedad; }
    public double getSuperficie()     { return superficie; }
    public String getParcelaCodigo()  { return parcelaCodigo; }
    public String getEstado()         { return estado; }
    public List<String> getActividades() { return actividades; }

    @Override
    public String toCsvLine() {
        // Generamos algo como ["RIEGO:2023-03-10","FERTILIZACION:2023-03-20"]
        String actos = actividades.stream()
            .map(a -> "\"" + a + "\"")
            .collect(Collectors.joining(","));
        actos = "[" + actos + "]";

        return String.format(
            "Cultivo,\"%s\",\"%s\",%.1f,\"%s\",\"%s\",\"%s\",%s",
            nombre, variedad, superficie,
            parcelaCodigo, fechaSiembra, estado,
            actos
        );
    }
}
