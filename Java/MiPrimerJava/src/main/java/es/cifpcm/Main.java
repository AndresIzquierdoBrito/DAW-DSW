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
        // EjerPersonas();
        EjercicioBits();
        EjercicioMates();
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

    public static void EjercicioBits(){
        StringBuilder sb = new StringBuilder("Tipo\t\tRango\n---------------------------\nint\t\t\t[");
        sb.append(Integer.MIN_VALUE);
        sb.append(",");
        sb.append(Integer.MAX_VALUE);
        sb.append("]\nlong\t\t[");
        sb.append(Long.MIN_VALUE);
        sb.append(",");
        sb.append(Long.MAX_VALUE);
        sb.append("]\nfloat\t\t[");
        sb.append(Float.MIN_VALUE);
        sb.append(",");
        sb.append(Float.MAX_VALUE);
        sb.append("]\ndouble\t\t[");
        sb.append(Double.MIN_VALUE);
        sb.append(",");
        sb.append(Double.MAX_VALUE);
        sb.append("]\nTipo\t\tBits\n---------------------------\nint\t\t\t");
        sb.append(Integer.SIZE);
        sb.append("\nlong\t\t");
        sb.append(Long.SIZE);

        System.out.println(sb);
    }

    public static void EjercicioMates(){
        int j;
        Scanner sc = new Scanner(System.in);
        j = sc.nextInt();
        System.out.println((int)(Math.ceil(Math.log10(j)/Math.log10(2))));
    }
}



