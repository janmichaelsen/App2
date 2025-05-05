package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Parcela implements ElementoAgricola {
    private String codigo;
    private double tamano;
    private String ubicacion;
    private LocalDate fechaCreacion;
    private List<Cultivo> cultivos = new ArrayList<>();

    public Parcela(String codigo, double tamano, String ubicacion, LocalDate fechaCreacion) {
        this.codigo = codigo;
        this.tamano = tamano;
        this.ubicacion = ubicacion;
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String getNombre() {
        return codigo;
    }

    @Override
    public LocalDate getFecha() {
        return fechaCreacion;
    }

    public double getTamano() {
        return tamano;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void addCultivo(Cultivo c) {
        cultivos.add(c);
    }

    public List<Cultivo> getCultivos() {
        return new ArrayList<>(cultivos);
    }

    @Override
    public String toCsvLine() {
        return String.format(
            "Parcela,\"%s\",%.2f,\"%s\",\"%s\"",
            codigo, tamano, ubicacion, fechaCreacion
        );
    }
}
