package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import models.Cultivo;

public class CultivoService {
    private List<Cultivo> cultivos;

    public CultivoService(List<Cultivo> inicial) {
        this.cultivos = new ArrayList<>(inicial);
    }

    public List<Cultivo> listar() {
        return cultivos;
    }

    public void crear(Cultivo c) {
        cultivos.add(c);
    }

    public boolean borrar(String nombre) {
        return cultivos.removeIf(c ->
            c.getNombre().equalsIgnoreCase(nombre) && c.getActividades().isEmpty()
        );
    }

    public void editar(String nombre, Scanner sc) {
        for (int i = 0; i < cultivos.size(); i++) {
            Cultivo c = cultivos.get(i);
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.print("Nueva variedad: ");
                String variedad = sc.nextLine();
                System.out.print("Nueva superficie: ");
                double sup = Double.parseDouble(sc.nextLine());
                cultivos.set(i, new Cultivo(
                    c.getNombre(), variedad, sup,
                    c.getParcelaCodigo(), c.getFecha(), c.getEstado(), c.getActividades()
                ));
                System.out.println("Cultivo actualizado.");
                return;
            }
        }
        System.out.println("Cultivo no encontrado.");
    }

    public Cultivo buscarPorNombre(String nombre) {
        for (Cultivo c : cultivos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public List<Cultivo> buscar(String termino) {
        String lower = termino.toLowerCase();
        return cultivos.stream()
            .filter(c -> c.getNombre().toLowerCase().contains(lower)
                      || c.getVariedad().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    public List<Cultivo> reportePorEstado(String estado) {
        return cultivos.stream()
            .filter(c -> c.getEstado().equalsIgnoreCase(estado))
            .collect(Collectors.toList());
    }

    public Cultivo inputNuevo(Scanner sc) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Variedad: ");
        String variedad = sc.nextLine();
        System.out.print("Superficie: ");
        double sup = Double.parseDouble(sc.nextLine());
        System.out.print("Código parcela: ");
        String cod = sc.nextLine();
        System.out.print("Fecha siembra (YYYY-MM-DD): ");
        java.time.LocalDate fecha = java.time.LocalDate.parse(sc.nextLine());
        System.out.print("Estado: ");
        String est = sc.nextLine();
        return new Cultivo(nombre, variedad, sup, cod, fecha, est, new ArrayList<>());
    }
}
