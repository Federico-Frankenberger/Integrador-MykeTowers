package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Operaciones sobre personas");
            System.out.println("2. Operaciones sobre domicilios");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcionPrincipal) {
                case 1:
                    menuPersonas(scanner);
                    break;
                case 2:
                    menuDomicilios(scanner);
                    break;
                case 3:
                    salir = true;
                    System.out.println("------------------------------");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("Muchas gracias por su tiempo!");
                    System.out.println("-------------------------------");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();

    }
    private static void menuPersonas(Scanner scanner) {};
    private static void menuDomicilios(Scanner scanner) {};
}
