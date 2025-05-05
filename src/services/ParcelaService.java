package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import models.Cultivo;
import models.Parcela;

public class ParcelaService {
    private List<Parcela> parcelas;

    public ParcelaService(List<Cultivo> cultivos) {
        // Agrupamos cultivos por código de parcela
        Map<String, List<Cultivo>> porParcela = new HashMap<>();
        for (Cultivo c : cultivos) {
            porParcela
                .computeIfAbsent(c.getParcelaCodigo(), k -> new ArrayList<>())
                .add(c);
        }

        // Creamos lista de parcelas con superficie total y cultivos asociados
        this.parcelas = new ArrayList<>();
        for (Map.Entry<String, List<Cultivo>> entry : porParcela.entrySet()) {
            String codigo = entry.getKey();
            List<Cultivo> lista = entry.getValue();
            double superficieTotal = lista.stream()
                .mapToDouble(Cultivo::getSuperficie)
                .sum();
            LocalDate fecha = lista.get(0).getFecha();
            Parcela p = new Parcela(codigo, superficieTotal, "Desconocida", fecha);
            lista.forEach(p::addCultivo);
            parcelas.add(p);
        }
    }

    /**
     * Devuelve todas las parcelas existentes.
     */
    public List<Parcela> listar() {
        return parcelas;
    }

    /**
     * Crea una nueva parcela.
     */
    public void crear(Parcela p) {
        parcelas.add(p);
    }

    /**
     * Elimina la parcela si no tiene cultivos asociados.
     */
    public boolean borrar(String codigo) {
        return parcelas.removeIf(p ->
            p.getNombre().equalsIgnoreCase(codigo) && p.getCultivos().isEmpty()
        );
    }

    /**
     * Edita tamaño y ubicación de la parcela.
     */
    public void editar(String codigo, Scanner sc) {
        for (int i = 0; i < parcelas.size(); i++) {
            Parcela p = parcelas.get(i);
            if (p.getNombre().equalsIgnoreCase(codigo)) {
                System.out.print("Nuevo tamaño: ");
                double tam = Double.parseDouble(sc.nextLine());
                System.out.print("Nueva ubicación: ");
                String ub = sc.nextLine();
                parcelas.set(
                    i,
                    new Parcela(p.getNombre(), tam, ub, p.getFecha())
                );
                System.out.println("Parcela actualizada.");
                return;
            }
        }
        System.out.println("Parcela no encontrada.");
    }

    /**
     * Asocia un cultivo existente a la parcela.
     */
    public boolean asignarCultivo(String codigoParcela, Cultivo cultivo) {
        for (Parcela p : parcelas) {
            if (p.getNombre().equalsIgnoreCase(codigoParcela)) {
                p.addCultivo(cultivo);
                return true;
            }
        }
        return false;
    }

    /**
     * Solicita datos por consola y crea una nueva parcela.
     */
    public Parcela inputNuevo(Scanner sc) {
        System.out.print("Código parcela: ");
        String codigo = sc.nextLine();
        System.out.print("Tamaño (ha): ");
        double tam = Double.parseDouble(sc.nextLine());
        System.out.print("Ubicación: ");
        String ub = sc.nextLine();
        System.out.print("Fecha creación (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());
        return new Parcela(codigo, tam, ub, fecha);
    }
}