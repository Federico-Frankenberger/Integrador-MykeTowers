package org.example;

import org.example.dao.DomicilioDAOImpl;
import org.example.dao.PersonaDAOImpl;
import org.example.model.Domicilio;
import org.example.model.Persona;
import org.example.service.DomicilioServiceImpl;
import org.example.service.PersonaServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DomicilioDAOImpl domicilioDao = new DomicilioDAOImpl();

        DomicilioServiceImpl domicilioService = new DomicilioServiceImpl(domicilioDao);

        PersonaDAOImpl personaDAO = new PersonaDAOImpl();

        PersonaServiceImpl personaService = new PersonaServiceImpl(personaDAO,domicilioService);

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
                    menuPersonas(scanner, personaService,domicilioService);
                    break;
                case 2:
                    menuDomicilios(scanner, domicilioService);
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
    private static void menuPersonas(Scanner scanner, PersonaServiceImpl personaService,DomicilioServiceImpl domicilioService) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MENÚ PERSONAS ---");
            System.out.println("1. Crear persona");
            System.out.println("2. Leer persona");
            System.out.println("3. Actualizar persona");
            System.out.println("4. Eliminar persona");
            System.out.println("5. Listar personas");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        try {
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine();

                            System.out.print("Apellido: ");
                            String apellido = scanner.nextLine();

                            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                            String fechaStr = scanner.nextLine();
                            LocalDate fechaNacimiento = LocalDate.parse(fechaStr);


                            System.out.print("¿Desea usar un domicilio existente? (s/n): ");
                            String resp = scanner.nextLine();

                            Domicilio domicilio;

                            if (resp.equalsIgnoreCase("s")) {
                                System.out.print("Ingrese ID del domicilio existente: ");
                                int idDom = Integer.parseInt(scanner.nextLine());
                                domicilio = domicilioService.leer(idDom);
                                if (domicilio == null) {
                                    System.out.println("No existe domicilio con ese ID.");
                                    break;
                                }
                            } else {
                                domicilio = new Domicilio();
                                System.out.print("Calle: ");
                                domicilio.setCalle(scanner.nextLine());

                                System.out.print("Número: ");
                                domicilio.setNumero(scanner.nextLine());

                                System.out.print("Ciudad: ");
                                domicilio.setCiudad(scanner.nextLine());

                                System.out.print("Provincia: ");
                                domicilio.setProvincia(scanner.nextLine());

                                System.out.print("Código Postal: ");
                                domicilio.setCodigoPostal(scanner.nextLine());

                                System.out.print("País: ");
                                domicilio.setPais(scanner.nextLine());

                            }

                            Persona persona = Persona.builder()
                                    .nombre(nombre)
                                    .apellido(apellido)
                                    .fechaNacimiento(fechaNacimiento)
                                    .domicilio(domicilio)
                                    .build();

                            int idGenerado = personaService.crear(persona);

                            System.out.println("Persona creada con ID: " + idGenerado);
                        } catch (Exception e) {
                            System.out.println("Error al crear persona: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            System.out.print("Ingrese ID de la persona a buscar: ");
                            int id = Integer.parseInt(scanner.nextLine());

                            Persona persona = personaService.leer(id);

                            System.out.println(persona);

                        } catch (Exception e) {
                            System.out.println("Error al leer persona: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.print("Ingrese ID de la persona a actualizar: ");
                            int idActualizar = Integer.parseInt(scanner.nextLine());

                            Persona personaExistente = personaService.leer(idActualizar);

                            System.out.println("Actualizando persona con ID: " + idActualizar);

                            System.out.print("Nuevo nombre (enter para no cambiar) [" + personaExistente.getNombre() + "]: ");
                            String nombreAct = scanner.nextLine();
                            if (nombreAct.isBlank()) {
                                nombreAct = personaExistente.getNombre();
                            }

                            System.out.print("Nuevo apellido (enter para no cambiar) [" + personaExistente.getApellido() + "]: ");
                            String apellidoAct = scanner.nextLine();
                            if (apellidoAct.isBlank()) {
                                apellidoAct = personaExistente.getApellido();
                            }

                            System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD) (enter para no cambiar) [" + personaExistente.getFechaNacimiento() + "]: ");
                            String fechaStrAct = scanner.nextLine();
                            LocalDate fechaNacimientoAct;
                            if (fechaStrAct.isBlank()) {
                                fechaNacimientoAct = personaExistente.getFechaNacimiento();
                            } else {
                                fechaNacimientoAct = LocalDate.parse(fechaStrAct);
                            }

                            Domicilio domicilioExistente = personaExistente.getDomicilio();

                            System.out.println("Actualizando domicilio:");

                            System.out.print("Nueva calle (enter para no cambiar) [" + domicilioExistente.getCalle() + "]: ");
                            String calleAct = scanner.nextLine();
                            if (calleAct.isBlank()) {
                                calleAct = domicilioExistente.getCalle();
                            }

                            System.out.print("Nuevo número (enter para no cambiar) [" + domicilioExistente.getNumero() + "]: ");
                            String numeroAct = scanner.nextLine();
                            if (numeroAct.isBlank()) {
                                numeroAct = domicilioExistente.getNumero();
                            }

                            System.out.print("Nueva ciudad (enter para no cambiar) [" + domicilioExistente.getCiudad() + "]: ");
                            String ciudadAct = scanner.nextLine();
                            if (ciudadAct.isBlank()) {
                                ciudadAct = domicilioExistente.getCiudad();
                            }
                            System.out.print("Nueva provincia (enter para no cambiar) [" + domicilioExistente.getProvincia() + "]: ");
                            String provinciaAct = scanner.nextLine();
                            if (provinciaAct.isBlank()) {
                                provinciaAct = domicilioExistente.getProvincia();
                            }

                            System.out.print("Nueva país (enter para no cambiar) [" + domicilioExistente.getPais() + "]: ");
                            String paisAct = scanner.nextLine();
                            if (paisAct.isBlank()) {
                                paisAct = domicilioExistente.getPais();
                            }

                            System.out.print("Nuevo código postal (enter para no cambiar) [" + domicilioExistente.getCodigoPostal() + "]: ");
                            String codigoPostalAct = scanner.nextLine();
                            if (codigoPostalAct.isBlank()) {
                                codigoPostalAct = domicilioExistente.getCodigoPostal();
                            }

                            Domicilio domicilioActualizado = Domicilio.builder()
                                    .id(domicilioExistente.getId())
                                    .calle(calleAct)
                                    .numero(numeroAct)
                                    .ciudad(ciudadAct)
                                    .provincia(provinciaAct)
                                    .codigoPostal(codigoPostalAct)
                                    .pais(paisAct)
                                    .build();

                            Persona personaActualizada = Persona.builder()
                                    .id(idActualizar)
                                    .nombre(nombreAct)
                                    .apellido(apellidoAct)
                                    .fechaNacimiento(fechaNacimientoAct)
                                    .domicilio(domicilioActualizado)
                                    .build();

                            boolean exitoAct = personaService.actualizar(personaActualizada);

                            if (exitoAct) {
                                System.out.println("Persona actualizada correctamente.");
                            } else {
                                System.out.println("No se pudo actualizar la persona.");
                            }

                        } catch (Exception e) {
                            System.out.println("Error al actualizar persona: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            System.out.print("Ingrese ID de la persona a eliminar: ");
                            int idEliminar = Integer.parseInt(scanner.nextLine());

                            boolean exitoEliminar = personaService.eliminar(idEliminar);

                            if (exitoEliminar) {
                                System.out.println("Persona eliminada correctamente.");
                            } else {
                                System.out.println("No se pudo eliminar la persona.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error al eliminar persona: " + e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            List<Persona> personas = personaService.listar();

                            if (personas.isEmpty()) {
                                System.out.println("No hay personas para mostrar.");
                            } else {
                                System.out.println("Listado de personas:");
                                for (Persona p : personas) {
                                    System.out.println(p);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error al listar personas: " + e.getMessage());
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
    };
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
