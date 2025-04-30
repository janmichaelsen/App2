package app;

public class App2 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("El uso es: java app.App2 <archivo.csv>");
            System.exit(1);
        }
        String csvFile = args[0];
        System.out.println("Inicializando el archivo: " + csvFile);
        // aquí luego se invocará la lógica de lectura CSV y menú
    }
}