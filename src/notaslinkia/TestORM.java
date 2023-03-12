/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notaslinkia;

import java.io.Console;
/**
 *
 * @author alber
 */
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import resources.Alumnos;
import resources.Modulos;
import resources.Notas;
import resources.Profesores;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestORM {

    public static void main(String[] args) {

        // Desactivar mensajes de registro de Hibernate
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        // Crear el gestor de sesiones
        NotasORM gestor = new NotasORM();

        Scanner sc = new Scanner(System.in);

        while (true) {
            // Limpiar pantalla
            System.out.print("\033[H\033[2J");

            // Banner en color verde
            System.out.println("\033[32m");
            System.out.print(
                    ".d88b. w       w                              8                           w   w                                  8                 w          \n");
            System.out.print(
                    "YPwww. w d88b w8ww .d88b 8d8b.d8b. .d88    .d88 .d88b    .d88 .d88b d88b w8ww w .d8b. 8d8b.    .d88 .d8b .d88 .d88 .d88b 8d8b.d8b. w .d8b .d88\n");
            System.out.print(
                    "    d8 8 `Yb.  8   8.dP' 8P Y8P Y8 8  8    8  8 8.dP'    8  8 8.dP' `Yb.  8   8 8' .8 8P Y8    8  8 8    8  8 8  8 8.dP' 8P Y8P Y8 8 8    8  8\n");
            System.out.print(
                    "`Y88P' 8 Y88P  Y8P `Y88P 8   8   8 `Y88    `Y88 `Y88P    `Y88 `Y88P Y88P  Y8P 8 `Y8P' 8   8    `Y88 `Y8P `Y88 `Y88 `Y88P 8   8   8 8 `Y8P `Y88\n");
            System.out.print(
                    "                                                         wwdP                                                                                 \n");
            System.out.println("\033[0m");

            System.out.println("\033[33m Por favor, seleccione su opción:\033[0m");
            System.out.println("\n--------------------------------");
            System.out.println("\033[36m  1. Menú de profesor\033[0m");
            System.out.println("\033[35m  2. Menú de alumno\033[0m");
            System.out.println("\n--------------------------------");
            System.out.println("\033[33m  3. Menú de administrador\033[0m");
            System.out.println("--------------------------------\n");
            System.out.println("\033[31m 0. Salir \033[0m");
            System.out.print("\n\033[32m Ingrese su opción: \033[0m");

            String opcion = sc.nextLine();

            switch (opcion) {

                case "1":
                    // Limpiar pantalla
                    System.out.print("\033[H\033[2J");
                    System.out.print("\033[32m");
                    System.out.print("Ingrese su usuario: ");
                    System.out.print("\033[0m");
                    String userProf = sc.nextLine();
                    System.out.print("\033[32m");
                    Console console = System.console();
                    char[] password = console.readPassword("Ingresa la contraseña: ");
                    String passProf = new String(password);
                    System.out.print("\033[0m");

                    // Comprobar si el usuario y la contraseña son correctos
                    if (!gestor.comprobarProfesor(userProf, passProf)) {
                        System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
                        break;
                    }

                    // Bucle para mostrar el menú de profesor hasta que se elija la opción de salir

                    Boolean menuProfesor = true;

                    while (menuProfesor) {
                        // Limpiar pantalla
                        System.out.print("\033[H\033[2J");
                        System.out.print("\033[33m");
                        System.out.println(" Menú de profesor:\n");
                        System.out.print("\033[32m");
                        System.out.println(" 1. Insertar módulo");
                        System.out.println(" 2. Listar TODOS los módulos");
                        System.out.println(" 3. Eliminar módulo");
                        System.out.println(" 4. Insertar alumno");
                        System.out.println(" 5. Listar TODOS los alumnos");
                        System.out.println(" 6. Listar alumnos por módulo");
                        System.out.println(" 7. Eliminar alumno");
                        System.out.print("\033[31m");
                        System.out.println("\n 0. Salir");
                        System.out.print("\033[0m");

                        int opcionProfesor = sc.nextInt();
                        sc.nextLine();

                        Scanner scanner = new Scanner(System.in);

                        switch (opcionProfesor) {
                            case 1:
                                // Llamar a método para insertar módulo
                                System.out.print("\033[H\033[2J");
                                gestor.insertarModulo();
                                // volver al menu profesor
                                break;
                            case 2:
                                // Llamar a método para listar todos los módulos
                                System.out.print("\033[H\033[2J");
                                gestor.listarModulos();
                                System.out.println("\nPresione una tecla para continuar...");

                                scanner.nextLine();

                                break;
                            case 3:
                                // Llamar a método para eliminar módulo
                                System.out.print("\033[H\033[2J");
                                gestor.borrarModulo();
                                System.out.println("\nPresione una tecla para continuar...");
                                scanner.nextLine();
                                break;
                            case 4:
                                // Llamar a método para insertar alumno
                                System.out.print("\033[H\033[2J");
                                gestor.insertarAlumno();
                                System.out.println("\nPresione una tecla para continuar...");
                                scanner.nextLine();
                                break;
                            case 5:
                                // Llamar a método para listar todos los alumnos
                                System.out.print("\033[H\033[2J");
                                gestor.consultarTodosAlumnos();
                                System.out.println("\nPresione una tecla para continuar...");

                                scanner.nextLine();
                                break;
                            case 6:
                                // Llamar a método para listar alumnos por módulo
                                System.out.print("\033[H\033[2J");
                                gestor.listarAlumnosPorModulo();
                                System.out.println("\nPresione una tecla para continuar...");

                                scanner.nextLine();
                                break;
                            case 7:
                                // Llamar a método para eliminar alumno
                                System.out.print("\033[H\033[2J");
                                gestor.borrarAlumno();
                                System.out.println("\nPresione una tecla para continuar...");

                                scanner.nextLine();
                                break;
                            case 0:
                                System.out.print("\033[H\033[2J");
                                System.out.println("Saliendo...");
                                menuProfesor = false;
                                System.out.println("\nPresione una tecla para continuar...");

                                scanner.nextLine();
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                    }

                    break;
                case "2":
                    Boolean menuAlumno = true;

                    while (menuAlumno) {
                        System.out.println("Ingrese su usuario:");
                        String userAlumno = sc.nextLine();

                        System.out.println("Ingrese su contraseña:");
                        // String passAlumno = sc.nextLine();

                        // Comprobar si el usuario y la contraseña son correctos
                        Console adminConsole = System.console();
                        char[] readPassword = adminConsole.readPassword("Ingresa la contraseña: ");
                        String passAlumno = new String(readPassword);
                        if (!gestor.comprobarAlumnor(userAlumno, passAlumno)) {
                            System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
                            break;
                        }

                        System.out.println("Menú de alumno:");
                        System.out.println("1. Consultar notas");
                        System.out.println("2. Listar módulos de los que es alumno");
                        System.out.println("0. Salir");

                        int opcionAlumno = sc.nextInt();
                        sc.nextLine();

                        switch (opcionAlumno) {
                            case 1:
                                // Llamar a método para consultar notas
                                gestor.consultarNotas();
                                break;
                            case 2:
                                // Llamar a método para listar módulos de los que es alumno
                                gestor.listarAlumnosPorModulo();
                                break;
                            case 0:
                                System.out.println("Saliendo...");
                                menuAlumno = false;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                    }
                case "3":
                    Console adminConsole = System.console();
                    char[] readPassword = adminConsole.readPassword("Ingresa la contraseña: ");
                    String adminPass = new String(readPassword);
                    // Contraseña de administrador valida en tabla profesores
                    if (!adminPass.equals("admin")) {
                        System.out.println("Contraseña incorrecta. Acceso denegado.");
                        break;
                    }

                    Boolean menuAdmin = true;

                    while (menuAdmin) {
                        System.out.println("Menú de administrador:");
                        System.out.println("1. Listar tabla historial");
                        System.out.println("2. Insertar módulo");
                        System.out.println("3. Listar TODOS los módulos");
                        System.out.println("4. Eliminar módulo");
                        System.out.println("5. Insertar alumno");
                        System.out.println("6. Listar TODOS los alumnos");
                        System.out.println("7. Listar alumnos por módulo");
                        System.out.println("8. Eliminar alumno");
                        System.out.println("9. Listar tabla notas");
                        System.out.println("10. Listar tabla profesores");
                        System.out.println("11. Modificar profesor");
                        System.out.println("12. Eliminar profesor");
                        System.out.println("0. Salir");

                        int opcionAdmin = sc.nextInt();
                        // sc.nextLine();

                        switch (opcionAdmin) {
                            case 1:
                                // Llamar a método para listar historial
                                gestor.listarHistorial();

                                break;
                            case 2:
                                // Llamar a método para insertar módulo
                                gestor.insertarModulo();
                                break;
                            case 3:
                                // Llamar a método para listar todos los módulos
                                gestor.listarModulos();
                                break;
                            case 4:
                                // Llamar a método para eliminar módulo
                                gestor.borrarModulo();
                                break;
                            case 5:
                                // Llamar a método para insertar alumno
                                gestor.insertarAlumno();
                                break;
                            case 6:
                                // Llamar a método para listar todos los alumnos
                                gestor.consultarTodosAlumnos();
                                break;
                            case 7:
                                // Llamar a método para listar alumnos por módulo
                                gestor.listarAlumnosPorModulo();
                                break;
                            case 8:
                                // Llamar a método para eliminar alumno
                                gestor.borrarAlumno();
                                break;
                            case 9:
                                // Llamar a método para listar tabla notas
                                gestor.consultarNotas();
                                break;
                            case 10:
                                // Llamar a método para listar tabla profesores
                                gestor.consultarTodosProfesores();
                                break;
                            case 11:
                                // Llamar a método para modificar profesor
                                gestor.modificarProfesorId();
                                break;
                            case 12:
                                // Llamar a método para eliminar profesor
                                gestor.borrarProfesor();
                                break;
                            case 0:
                                System.out.println("Saliendo...");
                                menuAdmin = false;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                    }

                    break;
                case "0":
                    System.out.println("Hasta luego.");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}