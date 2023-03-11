/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notaslinkia;

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
            System.out.println("Bienvenido al sistema de gestión académica.");
            System.out.println("Por favor, seleccione su opción:");
            System.out.println("1. Menú de administrador");
            System.out.println("2. Menú de profesor");
            System.out.println("3. Menú de alumno");
            System.out.println("0. Salir");

            String opcion = sc.nextLine();
            // sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case "1":
                    System.out.println("Ingrese la contraseña:");
                    String password = sc.nextLine();
                    // Contraseña de administrador valida en tabla profesores
                    if (!password.equals("admin")) {
                        System.out.println("Contraseña incorrecta. Acceso denegado.");
                        break;
                    }

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
                    sc.nextLine();

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
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }

                    break;
                case "B":

                    System.out.println("Ingrese su usuario:");
                    String userProf = sc.nextLine();

                    System.out.println("Ingrese su contraseña:");
                    String passProf = sc.nextLine();

                    // Comprobar si el usuario y la contraseña son correctos
                    if (!gestor.comprobarProfesor(userProf, passProf)) {
                        System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
                        break;
                    }

                    System.out.println("Menú de profesor:");
                    System.out.println("1. Insertar módulo");
                    System.out.println("2. Listar TODOS los módulos");
                    System.out.println("3. Eliminar módulo");
                    System.out.println("4. Insertar alumno");
                    System.out.println("5. Listar TODOS los alumnos");
                    System.out.println("6. Listar alumnos por módulo");
                    System.out.println("7. Eliminar alumno");

                    int opcionProfesor = sc.nextInt();
                    sc.nextLine();

                    switch (opcionProfesor) {
                        case 1:
                            // Llamar a método para insertar módulo
                            gestor.insertarModulo();
                            break;
                        case 2:
                            // Llamar a método para listar todos los módulos
                            gestor.listarModulos();
                            break;
                        case 3:
                            // Llamar a método para eliminar módulo
                            gestor.borrarModulo();
                            break;
                        case 4:
                            // Llamar a método para insertar alumno
                            gestor.insertarAlumno();
                            break;
                        case 5:
                            // Llamar a método para listar todos los alumnos
                            gestor.consultarTodosAlumnos();
                            break;
                        case 6:
                            // Llamar a método para listar alumnos por módulo
                            gestor.listarAlumnosPorModulo();
                            break;
                        case 7:
                            // Llamar a método para eliminar alumno
                            gestor.borrarAlumno();
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }

                    break;
                case "C":

                    System.out.println("Ingrese su usuario:");
                    String userAlumno = sc.nextLine();

                    System.out.println("Ingrese su contraseña:");
                    String passAlumno = sc.nextLine();

                    // Comprobar si el usuario y la contraseña son correctos
                    if (!gestor.comprobarAlumnor(userAlumno, passAlumno)) {
                        System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
                        break;
                    }

                    System.out.println("Menú de alumno:");
                    System.out.println("1. Consultar notas");
                    System.out.println("2. Listar módulos de los que es alumno");

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
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }

                    break;
                case "D":
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