package app;

import java.util.List;
import java.util.Scanner;
import services.CsvService;
import services.CultivoService;
import services.ParcelaService;
import services.ActividadService;
import models.Cultivo;

public class App2 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java app.App2 <archivo.csv>");
            System.exit(1);
        }
        String csvFile = args[0];
        CsvService csv = new CsvService();
        List<Cultivo> cultivos = csv.leer(csvFile);
        CultivoService cs = new CultivoService(cultivos);
        ParcelaService ps = new ParcelaService(cultivos);
        ActividadService as = new ActividadService();

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Cultivos");
            System.out.println("2. Parcelas");
            System.out.println("3. Actividades");
            System.out.println("4. Búsqueda/Reporte");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            int opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1: menuCultivos(sc, cs); break;
                case 2: menuParcelas(sc, ps, cs); break;
                case 3: menuActividades(sc, cs, as); break;
                case 4: menuReportes(sc, cs); break;
                case 5:
                    csv.escribir(csvFile, cs.listar());
                    run = false;
                    break;
                default: System.out.println("Opción inválida.");
            }
        }
        sc.close();
        System.out.println("Fin del programa.");
    }

    private static void menuCultivos(Scanner sc, CultivoService cs) {
        System.out.println("\n--- Gestión de Cultivos ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Eliminar");
        System.out.println("4. Editar");
        System.out.print("Opción: ");
        int o = Integer.parseInt(sc.nextLine());
        switch (o) {
            case 1: cs.listar().forEach(c -> System.out.println(c.toCsvLine())); break;
            case 2: cs.crear(cs.inputNuevo(sc)); System.out.println("Creado."); break;
            case 3: System.out.print("Nombre a eliminar: "); if(cs.borrar(sc.nextLine())) System.out.println("Eliminado."); else System.out.println("No se pudo."); break;
            case 4: System.out.print("Nombre a editar: "); cs.editar(sc.nextLine(), sc); break;
            default: System.out.println("Inválida.");
        }
    }

    private static void menuParcelas(Scanner sc, ParcelaService ps, CultivoService cs) {
        System.out.println("\n--- Gestión de Parcelas ---");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Eliminar");
        System.out.println("4. Editar");
        System.out.println("5. Asignar cultivo");
        System.out.print("Opción: ");
        int o = Integer.parseInt(sc.nextLine());
        switch (o) {
            case 1:
                ps.listar().forEach(p -> {
                    System.out.printf("Parcela %s (%.2f ha, %s, %s)%n",
                        p.getNombre(), p.getTamano(), p.getUbicacion(), p.getFecha());
                    String cults = p.getCultivos().isEmpty() ? "ninguno" : String.join(", ", p.getCultivos().stream().map(Cultivo::getNombre).toArray(String[]::new));
                    System.out.println("  Cultivos: " + cults);
                });
                break;
            case 2: ps.crear(ps.inputNuevo(sc)); System.out.println("Creada."); break;
            case 3: System.out.print("Código a eliminar: "); if(ps.borrar(sc.nextLine())) System.out.println("Eliminada."); else System.out.println("No se pudo."); break;
            case 4: System.out.print("Código a editar: "); ps.editar(sc.nextLine(), sc); break;
            case 5:
                System.out.print("Código parcela: "); String cod = sc.nextLine();
                System.out.print("Nombre cultivo: "); String nom = sc.nextLine();
                if (ps.asignarCultivo(cod, cs.buscarPorNombre(nom))) System.out.println("Asignado."); else System.out.println("Error.");
                break;
            default: System.out.println("Inválida.");
        }
    }

    private static void menuActividades(Scanner sc, CultivoService cs, ActividadService as) {
        System.out.println("\n--- Gestión de Actividades ---");
        System.out.print("Nombre cultivo: "); String nombre = sc.nextLine();
        Cultivo c = cs.buscarPorNombre(nombre);
        if (c == null) { System.out.println("No encontrado."); return; }
        System.out.println("1. Registrar");
        System.out.println("2. Listar");
        System.out.println("3. Eliminar");
        System.out.println("4. Marcar completada");
        System.out.print("Opción: "); int o = Integer.parseInt(sc.nextLine());
        switch (o) {
            case 1: as.registrar(c, sc); break;
            case 2: as.listar(c); break;
            case 3: as.eliminar(c, sc); break;
            case 4: as.marcarCompletada(c, sc); break;
            default: System.out.println("Inválida.");
        }
    }

    private static void menuReportes(Scanner sc, CultivoService cs) {
        System.out.println("\n--- Búsqueda/Reporte ---");
        System.out.println("1. Buscar por nombre o variedad");
        System.out.println("2. Reporte por estado");
        System.out.print("Opción: "); int o = Integer.parseInt(sc.nextLine());
        switch (o) {
            case 1:
                System.out.print("Término: ");
                cs.buscar(sc.nextLine()).forEach(c -> System.out.println(c.toCsvLine()));
                break;
            case 2:
                System.out.print("Estado (ACTIVO/EN_RIESGO/COSECHADO): ");
                cs.reportePorEstado(sc.nextLine()).forEach(c -> System.out.println(c.toCsvLine()));
                break;
            default:
                System.out.println("Inválida.");
        }
    }
}