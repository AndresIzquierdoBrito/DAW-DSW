package es.cifpcm;


import es.cifpcm.data.Persistance;
import es.cifpcm.data.PersistanceImpl;
import es.cifpcm.util.ConsoleUtils;
import es.cifpcm.model.Farmacia;

import java.util.List;
import java.util.Scanner;

public class AndresIzquierdoFarmacia {
    static Persistance pst = new PersistanceImpl();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleUtils consoleUtils = new ConsoleUtils();

        pst.openJson(); // Opening the JSON file
        System.out.println("El archivo se encuentra en " +  System.getProperty("java.io.tmpdir") + "IzquierdoAndres_farmacias.json");
        int menuOption;

        do {
            printMainMenu();
            menuOption = getOption();
            switch (menuOption){
                case 1:
                    System.out.print("\nNombre de la farmacia: ");
                    buscarFarmacia(sc.nextLine());
                    break;
                case 2:
                    listarFarmacia();
                    break;
                case 9:
                    goAdminMenu();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Selecciona una opción válida.");
                    break;
            }
        } while(menuOption != 0);

        sc.close();
        consoleUtils.clearConsole();

        System.out.println("Escribiendo en el archivo JSON... ");
        pst.closeJson();
        System.out.println("Escritura terminada con exito.");
    }

    public static void buscarFarmacia(String nombre) {
        List<Farmacia> farmacias = pst.list();
        Farmacia foundFarmacia = getFarmaciaByName(farmacias, nombre);

        if (foundFarmacia != null) {
            System.out.println("\nFarmacia encontrada:\n================\n" + foundFarmacia);
        } else {
            System.out.println("No se encontró una farmacia con el nombre: " + nombre);
        }
    }

    static void insertarFarmacia() {
        Farmacia farmaciaToAdd = getNewFarmaciaInput();
        pst.add(farmaciaToAdd);
    }

    static void borrarFarmacia(String nombre) {
        List<Farmacia> farmacias = pst.list();
        Farmacia farmaciaToDelete = getFarmaciaByName(farmacias, nombre);

        if (farmaciaToDelete != null){
            pst.delete(farmaciaToDelete);
            System.out.println("Farmacia borrada con éxito.");
        }
        else{
            System.out.println("No se encontró una farmacia con el nombre: " + nombre);
        }
    }

    static Farmacia getFarmaciaByName(List<Farmacia> farmacias, String nombre){
        return farmacias.stream()
                .filter(farmacia -> farmacia.getNOMBRE().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    static void listarFarmacia(){
        List<Farmacia> farmacias = pst.list();
        System.out.println("\n[ Lista de farmacias ]");
        for (Farmacia farmacia : farmacias) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n=============================\n");
            sb.append("WEB: ").append(farmacia.getWEB()).append("\n");
            sb.append("NOMBRE: ").append(farmacia.getNOMBRE()).append("\n");
            sb.append("LUNES: ").append(farmacia.getLUNES()).append("\n");
            sb.append("TELEFONO: ").append(farmacia.getTELEFONO()).append("\n");
            sb.append("UTM_X: ").append(farmacia.getUTM_X()).append("\n");
            sb.append("UTM_Y: ").append(farmacia.getUTM_Y()).append("\n");
            sb.append("=============================");
            System.out.println(sb);
        }
    }

    static void printMainMenu() {
        System.out.print("\n================ Farmacias de mi ciudad ==================\n1. Busque por nombre\n2. Lista de farmacias disponibles\n0. Salir\n-----------------------\n9. Admin\n");
        System.out.print("Introduzca opción: ");
    }

    static void goAdminMenu() {
        int menuOption;
        do{
            System.out.print("\n================ FARMACIAS DE MI CIUDAD – ADMIN ==================\n\n1. Añadir farmacias\n2. Borrar farmacias\n3. Listar farmacias\n0. Salir\n");
            System.out.print("\nIntroduzca opción: ");
            menuOption = getOption();
            switch (menuOption){
                case 1:
                    insertarFarmacia();
                    break;
                case 2:
                    System.out.print("\nNombre de la farmacia: ");
                    borrarFarmacia(sc.nextLine());
                    break;
                case 3:
                    listarFarmacia();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Selecciona una opción válida.");
                    break;
            }
        } while(menuOption != 0);
    }

    static Farmacia getNewFarmaciaInput(){

        System.out.println("Introduce los datos para crear la nueva farmacia\n=============================");

        String web;
        do {
            web = getInput("\nWEB: ");
            if (!web.matches("^(http|https)://.*$")) {
                System.out.println("Introduce una web correcta. Debe empezar con http:// o https://");
            }
        } while (!web.matches("^(http|https)://.*$"));

        String nombre = getInput("\nNOMBRE: ");
        String lunes = getInput("\nLUNES: ");

        String telefono;
        do {
            telefono = getInput("\nTELEFONO: ");
            if (!telefono.matches("^\\d{9}$")) {
                System.out.println("Introduce un teléfono correcto. Debe ser un número de 9 dígitos.");
            }
        } while (!telefono.matches("^\\d{9}$"));
        float utmX = getFloat("\nUTM_X: ");
        float utmY = getFloat("\nUTM_Y: ");

        Farmacia farmacia = new Farmacia(web, nombre, lunes, telefono, utmX, utmY);

        return farmacia;
    }

    static String getInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }

    public static float getFloat(String prompt) {
        float number;
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextFloat()) {
                number = sc.nextFloat();
                break;
            } else {
                System.out.println("Input no váldo. Introduce un valor de nuevo, número real.");
                sc.next(); // discard the non-numeric input
            }
        }
        return number;
    }

    static int getOption() {
        int option = 0;

        while (true) {
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.print("\nInput no válido. Introduce una de las opciones:");
                sc.next();
            }
        }
        return option;
    }
}