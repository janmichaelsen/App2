package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import models.Cultivo;

public class ActividadService {
    /** Registra una nueva actividad en el cultivo. */
    public void registrar(Cultivo c, Scanner sc) {
        System.out.print("Tipo de actividad (riego, fertilización, etc.): ");
        String tipo = sc.nextLine();
        System.out.print("Fecha actividad (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());
        c.getActividades().add(tipo + ":" + fecha);
        System.out.println("Actividad registrada.");
    }

    /** Lista todas las actividades de un cultivo. */
    public void listar(Cultivo c) {
        List<String> acts = c.getActividades();
        if (acts.isEmpty()) {
            System.out.println("  (ninguna)");
        } else {
            for (int i = 0; i < acts.size(); i++) {
                System.out.printf("  %d. %s%n", i + 1, acts.get(i));
            }
        }
    }

    /** Elimina una actividad seleccionada por índice. */
    public void eliminar(Cultivo c, Scanner sc) {
        listar(c);
        System.out.print("Número de actividad a eliminar: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx >= 0 && idx < c.getActividades().size()) {
            c.getActividades().remove(idx);
            System.out.println("Actividad eliminada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    /** Marca una actividad como completada. */
    public void marcarCompletada(Cultivo c, Scanner sc) {
        listar(c);
        System.out.print("Número de actividad a marcar como completada: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx >= 0 && idx < c.getActividades().size()) {
            String act = c.getActividades().get(idx);
            c.getActividades().set(idx, act + " (COMPLETADA)");
            System.out.println("Actividad marcada como completada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }
}