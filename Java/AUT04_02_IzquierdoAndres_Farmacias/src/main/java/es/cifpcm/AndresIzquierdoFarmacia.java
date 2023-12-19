package es.cifpcm;


import es.cifpcm.data.Persistance;
import es.cifpcm.data.PersistanceImpl;
import es.cifpcm.util.ConsoleUtils;
import es.cifpcm.model.Farmacia;

import java.util.Scanner;

public class AndresIzquierdoFarmacia {
    static Persistance pst = new PersistanceImpl();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleUtils consoleUtils = new ConsoleUtils();

        printMainMenu();
        getOption();
        pst.openJson();

        consoleUtils.clearConsole();

    }

    static void buscarFarmacia() {

    }

    static void insertarFarmacia() {

    }

    static void borrarFarmacia() {

    }

    //static ArrayList<Farmacia> listarFarmacia(){
    //    return ;
    //}

    static void goAdminMenu() {
        System.out.print("================ FARMACIAS DE MI CIUDAD – ADMIN ==================\n\n1. Añadir farmacias\n2. Borrar farmacias\n3. Listar farmacias\n0. Salir\n");
        System.out.print("\nIntroduzca opción: ");
    }

    static void printMainMenu() {
        System.out.print("================ Farmacias de mi ciudad ==================\n1. Busque por nombre\n2. Lista de farmacias disponibles\n0. Salir\n-----------------------\n9. Admin\n");
        System.out.print("Introduzca opción: ");

    }

    //static Farmacia getNewFarmaciaInput(){
    //
    //}

    // static Farmacia deleteNewFarmaciaInput(){
    //
    //}

    static int getOption() {
        int option = 0;

        try {
            option = sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }

        return option;
    }
}