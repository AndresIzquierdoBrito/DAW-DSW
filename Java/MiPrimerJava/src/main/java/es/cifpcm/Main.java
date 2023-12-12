package es.cifpcm;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Eje1();
        // Eje2();
        // Eje3();
        // Eje4();
        //Eje5();
        // EjerStrings();
        EjerPersonas();
    }

    public static void Eje1(){
        System.out.println("Hola mundo!");
    }

    public static void Eje2(){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        sc.close();
        System.out.println("Tu número multiplicado por 10 es: " + (num + 10));

    }

    public static void Eje3() {
        Scanner sc = new Scanner(System.in);
        String userIn = sc.nextLine();
        sc.close();
        System.out.println("Bienvenido/a " + userIn);
    }

    public static void Eje4(){
        Scanner sc = new Scanner(System.in);
        int i, j;
        System.out.print("Primer número: ");
        i = sc.nextInt();
        System.out.print("Segundo número: ");
        j = sc.nextInt();
        sc.close();

        System.out.printf("Suma: %s + %s = %s%n", i, j, (i+j));
        System.out.printf("Resta: %s - %s = %s%n", i, j, (i-j));
        System.out.printf("Multiplicación: %s * %s = %s%n", i, j, (i*j));
        System.out.printf("División: %s / %s = %s%n", i, j, (i/j));
        System.out.printf("Módulo: %s mod %s = %s%n", i, j, (i % j));

    }

    public static void Eje5() {
        Scanner sc = new Scanner(System.in);
        int currentYear, birthYear;
        System.out.print("Tu año de nacimiento: ");
        birthYear = sc.nextInt();
        System.out.print("Año actual: ");
        currentYear = sc.nextInt();
        sc.close();

        System.out.println("Tienes " + (currentYear - birthYear) + " años.");
    }

    public static void EjerStrings() {
        String sysRoot = System.getenv("SystemRoot");
        System.out.println("El SYSTEM_ROOT de Windows está ubicado en: " + sysRoot);
        System.out.println("\nEn un lugar de \"La Mancha\" de cuyo nombre no quiero acordarme\nVivía un 'Hidalgo'\n");
        System.out.println("Procesador\tFabricante\nPentium\t\tIntel\nAthlon\t\tAMD");
    }

    public static void EjerBits() {
        int j = 255;
    }

    public static void EjerPersonas() {
        Persona juan, marta;

        juan = new Persona("Juan", "Diego", 39);
        marta = new Persona("Marta", "Sánchez", 42);

        System.out.println(juan);
        System.out.println(marta);

    }
}



