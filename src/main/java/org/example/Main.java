package org.example;

import org.example.dao.DomicilioDAOImpl;
import org.example.model.Domicilio;
import org.example.service.DomicilioServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DomicilioDAOImpl domicilioDao = new DomicilioDAOImpl();

        DomicilioServiceImpl domicilioService = new DomicilioServiceImpl(domicilioDao);

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
    private static void menuDomicilios(Scanner scanner, DomicilioServiceImpl domicilioService) {

        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MENÚ DOMICILIOS ---");
            System.out.println("1. Crear domicilio");
            System.out.println("2. Leer domicilio");
            System.out.println("3. Actualizar domicilio");
            System.out.println("4. Eliminar domicilio");
            System.out.println("5. Listar domicilios");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese calle: ");
                        String calle = scanner.nextLine();

                        System.out.print("Ingrese número: ");
                        String numero = scanner.nextLine();

                        System.out.print("Ingrese ciudad: ");
                        String ciudad = scanner.nextLine();

                        System.out.println("Ingrese provincia:");
                        String provincia = scanner.nextLine();

                        System.out.println("Ingrese país:");
                        String pais = scanner.nextLine();

                        System.out.print("Ingrese código postal: ");
                        String codigoPostal = scanner.nextLine();

                        Domicilio domicilio = Domicilio.builder()
                                .calle(calle)
                                .numero(numero)
                                .ciudad(ciudad)
                                .provincia(provincia)
                                .pais(pais)
                                .codigoPostal(codigoPostal)
                                .build();

                        int id = domicilioService.crear(domicilio);

                        System.out.println("Domicilio creado con ID: " + id);

                        break;
                    case 2:
                        System.out.print("Ingrese ID del domicilio a leer: ");
                        int idLeer = scanner.nextInt();
                        scanner.nextLine();

                        Domicilio domicilioLeido = domicilioService.leer(idLeer);
                        if (domicilioLeido == null) {
                            System.out.println("No se encontró un domicilio con ID " + idLeer);
                        } else {
                            System.out.println(domicilioLeido.toString());
                        }
                        break;
                    case 3:
                        System.out.print("Ingrese ID del domicilio a actualizar: ");
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine();

                        Domicilio domicilioActualizar = domicilioService.leer(idActualizar);
                        if (domicilioActualizar == null) {
                            System.out.println("No se encontró un domicilio con ID " + idActualizar);
                        } else {
                            System.out.print("Calle (enter para no cambiar) [" + domicilioActualizar.getCalle() + "]: ");
                            String calleNueva = scanner.nextLine();
                            if (!calleNueva.isBlank()) {
                                domicilioActualizar.setCalle(calleNueva);
                            }

                            System.out.print("Número (enter para no cambiar) [" + domicilioActualizar.getNumero() + "]: ");
                            String numeroNuevo = scanner.nextLine();
                            if (!numeroNuevo.isBlank()) {
                                domicilioActualizar.setNumero(numeroNuevo);
                            }

                            System.out.print("Ciudad (enter para no cambiar) [" + domicilioActualizar.getCiudad() + "]: ");
                            String ciudadNueva = scanner.nextLine();
                            if (!ciudadNueva.isBlank()) {
                                domicilioActualizar.setCiudad(ciudadNueva);
                            }

                            System.out.print("Provincia (enter para no cambiar) [" + domicilioActualizar.getProvincia() + "]: ");
                            String provinciaNuevo = scanner.nextLine();
                            if (!provinciaNuevo.isBlank()) {
                                domicilioActualizar.setProvincia(provinciaNuevo);
                            }

                            System.out.print("País (enter para no cambiar) [" + domicilioActualizar.getPais() + "]: ");
                            String paisNuevo = scanner.nextLine();
                            if (!paisNuevo.isBlank()) {
                                domicilioActualizar.setPais(paisNuevo);
                            }

                            System.out.print("Código Postal (enter para no cambiar) [" + domicilioActualizar.getCodigoPostal() + "]: ");
                            String codigoPostalNuevo = scanner.nextLine();
                            if (!codigoPostalNuevo.isBlank()) {
                                domicilioActualizar.setCodigoPostal(codigoPostalNuevo);
                            }

                            boolean actualizado = domicilioService.actualizar(domicilioActualizar);
                            if (actualizado) {
                                System.out.println("Domicilio actualizado correctamente.");
                            } else {
                                System.out.println("No se pudo actualizar el domicilio.");
                            }
                        }
                        break;
                    case 4:
                        System.out.print("Ingrese ID del domicilio a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();

                        boolean eliminado = domicilioService.eliminar(idEliminar);
                        if (eliminado) {
                            System.out.println("Domicilio eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el domicilio. Verifique el ID.");
                        }
                        break;
                    case 5:
                        try {
                            List<Domicilio> domicilios = domicilioService.listar();
                            if (domicilios.isEmpty()) {
                                System.out.println("No hay domicilios registrados.");
                            } else {
                                System.out.println("Listado de domicilios:");
                                for (Domicilio d : domicilios) {
                                    System.out.println(d);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al listar domicilios: " + e.getMessage());
                        }
                        break;
                    case 6:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
