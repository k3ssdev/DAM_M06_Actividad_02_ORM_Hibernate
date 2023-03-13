/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notaslinkia;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import resources.Alumnos;
import resources.Profesores;
import resources.Notas;
import resources.Modulos;
import resources.Historial;
import resources.HibernateUtil;

/**
 *
 * @author alber
 */
public class NotasORM {

    // Constructor de la clase
    public NotasORM() {
        // Obtenemos una sesión de Hibernate
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    // Metodo close
    public void close() {
        sesion.close();
    }

    // Definición de variables de clase
    private static Session sesion;
    private static Transaction tx;

    private static NotasORM notasORM = new NotasORM();

    // Método para realizar una consulta y mostrar sus resultados
    public static void consulta(String c) {

        System.out.println("Salida de consulta");

        // Abrimos una nueva sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Creamos una consulta a partir de la cadena "c"
        Query q = session.createQuery(c);

        // Ejecutamos la consulta y obtenemos los resultados
        List results = q.list();

        // Iteramos sobre los resultados y los mostramos en pantalla
        Iterator alumnoIterator = results.iterator();
        while (alumnoIterator.hasNext()) {
            Alumnos a2 = (Alumnos) alumnoIterator.next();
            System.out.println(a2.getNombre());
        }

        // Cerramos la sesión de Hibernate
        session.close();
    }

    // Método para insertar un alumno en la base de datos
    public static void insertarAlumno(Alumnos a) throws ConstraintViolationException {
        try {
            // Iniciamos una transacción
            tx = sesion.beginTransaction();

            // Guardamos el alumno en la sesión de Hibernate
            sesion.save(a);

            // Confirmamos la transacción
            tx.commit();
        } catch (ConstraintViolationException ex) {
            // Si ocurre una excepción de violación de restricciones, hacemos un rollback de
            // la transacción
            tx.rollback();

            // Lanzamos la excepción para que sea manejada por quien llame al método
            throw ex;
        }
    }

    // Método para modificar un alumno en la base de datos a partir de su ID
    public void modificarAlumnoId() {
        Scanner sc = new Scanner(System.in);

        // Abrimos una nueva sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Iniciamos una transacción
            tx = session.beginTransaction();

            // Pedimos al usuario que ingrese el ID del alumno a modificar
            System.out.println("Ingrese el id del alumno a modificar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer

            // Obtenemos el alumno a modificar a partir de su ID
            Alumnos alumno = session.get(Alumnos.class, id);
            System.out.println("Alumno seleccionado: " + alumno.getNombre());

            // Pedimos al usuario que ingrese el nuevo nombre y nombre de usuario del alumno
            System.out.println("Ingrese el nuevo nombre del alumno:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nuevo nombre de usuario del alumno:");
            String nomUser = sc.nextLine();

            // Preguntamos al usuario si desea cambiar la contraseña del alumno
            System.out.println("¿Quiere cambiar la contraseña? (S/N)");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                // Si el usuario desea cambiar la contraseña, pedimos que ingrese la nueva
                // contraseña
                System.out.println("Ingrese la nueva contraseña del alumno:");
                String password = sc.nextLine();

                // Actualizamos la contraseña del alumno en el objeto
                alumno.setPassword(password);
            }

            // Actualizamos el nombre y nombre de usuario del alumno en el objeto
            alumno.setNombre(nombre);
            alumno.setNomUser(nomUser);

            // Guardamos los cambios en la sesión de Hibernate
            session.update(alumno);

            // Confirmamos la transacción
            tx.commit();

            // Mostramos un mensaje de éxito y el alumno modificado
            System.out.println("Modificación realizada.");
            System.out.println("Alumno modificado: " + alumno.getNombre());

        } catch (Exception ex) {
            // Si ocurre alguna excepción, hacemos un rollback de la transacción y la
            // lanzamos para que sea manejada por quien llame al método
            if (tx != null) {
                tx.rollback();
            }
            throw ex;

        } finally {
            // Cerramos el scanner y la sesión de Hibernate en el bloque finally para
            // asegurarnos de que se cierren aunque ocurra una excepción
            sc.close();
            session.close();
        }
    }

    // Método para modificar un profesor por su ID
    public void modificarProfesorId() {
        // Crear un objeto Scanner para leer la entrada del usuario desde la consola
        Scanner sc = new Scanner(System.in);
        // Abrir una sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Llamar al método consultarTodosProfesores para mostrar todos los profesores
        notasORM.consultarTodosProfesores();

        try {
            // Comenzar una transacción
            tx = session.beginTransaction();
            // Pedir al usuario que ingrese el ID del profesor a modificar
            System.out.println("Ingrese el id del profesor a modificar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer
            // Obtener el profesor seleccionado por su ID
            Profesores profesor = session.get(Profesores.class, id);
            // Mostrar el nombre del profesor seleccionado
            System.out.println("Profesor seleccionado: " + profesor.getNombre());
            // Pedir al usuario que ingrese el nuevo nombre del profesor
            System.out.println("Ingrese el nuevo nombre del profesor:");
            String nombre = sc.nextLine();
            // Pedir al usuario que ingrese el nuevo nombre de usuario del profesor
            System.out.println("Ingrese el nuevo nombre de usuario del profesor:");
            String nomUser = sc.nextLine();
            // Preguntar al usuario si quiere cambiar la contraseña
            System.out.println("¿Quiere cambiar la contraseña? (S/N)");
            String respuesta = sc.nextLine();
            // Si el usuario responde "S", pedir al usuario que ingrese la nueva contraseña
            // y actualizarla
            if (respuesta.equalsIgnoreCase("S")) {
                System.out.println("Ingrese la nueva contraseña del profesor:");
                String password = sc.nextLine();
                profesor.setPassword(password);
            }
            // Actualizar el nombre y nombre de usuario del profesor
            profesor.setNombre(nombre);
            profesor.setNomUser(nomUser);
            // Actualizar el objeto profesor en la base de datos
            session.update(profesor);
            // Hacer commit a la transacción
            tx.commit();
            // Mostrar el nombre del profesor modificado
            System.out.println("Profesor modificado: " + profesor.getNombre());
        } catch (Exception ex) {
            // Si ocurre algún error, hacer rollback a la transacción y lanzar la excepción
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    // Método para eliminar un alumno por su ID, mostrar primero el listado de
    // alumnos
    public static void borrarAlumno() {
        // Crear un objeto Scanner para leer la entrada del usuario desde la consola
        Scanner sc = new Scanner(System.in);
        // Abrir una sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Obtener el listado de alumnos con elñ método consultarTodosAlumnos();
        // Consultar todos los alumnos
        notasORM.consultarTodosAlumnos();
        try {
            // Comenzar una transacción
            tx = session.beginTransaction();
            // Pedir al usuario que ingrese el ID del alumno a eliminar
            System.out.println("\nIngrese el id del alumno a eliminar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer
            // Obtener el alumno seleccionado por su ID
            Alumnos alumno = session.get(Alumnos.class, id);
            // Mostrar el nombre del alumno seleccionado
            System.out.println("Alumno seleccionado: " + alumno.getNombre());
            // Preguntar al usuario si está seguro de eliminar el alumno
            System.out.println("¿Está seguro de eliminar el alumno? (S/N)");
            String respuesta = sc.nextLine();
            // Si el usuario responde "S", eliminar el alumno
            if (respuesta.equalsIgnoreCase("S")) {
                // Eliminar el alumno de la base de datos
                session.delete(alumno);
                // Hacer commit a la transacción
                tx.commit();
                // Mostrar un mensaje de éxito
                System.out.println("Alumno eliminado.");
            } else {
                // Si el usuario no está seguro de eliminar el alumno, hacer rollback a la
                // transacción
                tx.rollback();
            }
        } catch (Exception ex) {
            // Si ocurre algún error, hacer rollback a la transacción y lanzar la excepción
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    // Método para iniciar sesión de alumno
    public Alumnos iniciarSesionAlumno(String nomUser, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Alumnos alumno = null;
        try {
            tx = session.beginTransaction();
            // Crear una consulta con los criterios para buscar el alumno
            Query query = session.createQuery("from Alumnos where nomUser = :nomUser and password = :password");
            query.setParameter("nomUser", nomUser); // Asignar el valor de nomUser
            query.setParameter("password", password); // Asignar el valor de password
            // Ejecutar la consulta y obtener el resultado único
            alumno = (Alumnos) query.uniqueResult();
            tx.commit(); // Confirmar la transacción
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex; // Lanzar la excepción para manejarla en el nivel superior
        } finally {
            session.close(); // Cerrar la sesión de Hibernate
        }
        return alumno; // Devolver el resultado de la búsqueda
    }

    // Metodo para consultar todos los alumnos
    public List<Alumnos> consultarTodosAlumnos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Objeto para ejecutar la consulta
        Query query = session.createQuery("from Alumnos");

        // Obtener la lista de alumnos
        List<Alumnos> listaAlumnos = query.getResultList();

        // Imprimir encabezado de la tabla
        System.out.println("+-------+--------------------------------+-----------------+----------------------+");
        System.out.printf(
                "|\033[38;5;206m %-5s \033[0m| \033[38;5;206m%-30s \033[0m| \033[38;5;206m%-15s \033[0m| \033[38;5;206m%-20s \033[0m|\n",
                "ID", "Nombre", "NomUser", "Password");
        System.out.println("+-------+--------------------------------+-----------------+----------------------+");

        // Imprimir cada registro en la tabla
        for (Alumnos a : listaAlumnos) {
            System.out.printf("| %-5s | %-30s | %-15s | %-20s |\n", a.getIdAlumno(), a.getNombre(),
                    a.getNomUser(), a.getPassword());
            System.out.println("+-------+--------------------------------+-----------------+----------------------+");
        }
        // Cerrar la sesion
        session.close();
        return listaAlumnos;
    }

    // Metodo para consultar todos los profesores
    public List<Profesores> consultarTodosProfesores() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Objeto para ejecutar la consulta
        Query query = session.createQuery("from Profesores");

        // Obtener la lista de alumnos
        List<Profesores> listaProfesores = query.getResultList();

        // Imprimir encabezado de la tabla
        System.out.println("+-------+--------------------------------+-----------------+----------------------+");
        System.out.printf(
                "|\033[38;5;206m %-5s \033[0m| \033[38;5;206m%-30s \033[0m| \033[38;5;206m%-15s \033[0m| \033[38;5;206m%-20s \033[0m|\n",
                "ID", "Nombre", "NomUser", "Password");
        System.out.println("+-------+--------------------------------+-----------------+----------------------+");

        // Imprimir cada registro en la tabla
        for (Profesores p : listaProfesores) {
            System.out.printf("| %-5s | %-30s | %-15s | %-20s |\n", p.getId(), p.getNombre(),
                    p.getNomUser(), p.getPassword());
            System.out.println("+-------+--------------------------------+-----------------+----------------------+");
        }
        // Cerrar la sesion
        session.close();
        return listaProfesores;
    }

    public void cambiarPasswordAlumno(String nomUser, String nuevaPassword) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Abrimos una sesión de Hibernate
        Transaction tx = null; // Creamos una transacción

        try {
            tx = session.beginTransaction(); // Iniciamos la transacción

            // Creamos una consulta para buscar al alumno por su nombre de usuario
            Query query = session.createQuery("from Alumnos where nomUser = :nomUser");
            query.setParameter("nomUser", nomUser);
            Alumnos alumno = (Alumnos) query.getSingleResult(); // Ejecutamos la consulta y obtenemos el resultado

            alumno.setPassword(nuevaPassword); // Actualizamos la contraseña del alumno con la nueva contraseña
                                               // proporcionada
            session.update(alumno); // Actualizamos los cambios en la base de datos
            tx.commit(); // Confirmamos la transacción

            // Mostramos un mensaje de éxito al usuario
            System.out.println("La contraseña del alumno con nombre de usuario '" + nomUser
                    + "' ha sido actualizada exitosamente.");

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback(); // En caso de error, hacemos un rollback de la transacción
            }
            e.printStackTrace(); // Imprimimos el error

        } finally {
            session.close(); // Cerramos la sesión de Hibernate
        }
    }

    /**
     * 
     * Método para insertar un nuevo registro de un profesor en la base de datos.
     */
    public void insertarProfesor() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el nombre del profesor:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de usuario del profesor:");
            String nomUser = sc.nextLine();
            System.out.println("Ingrese la contraseña del profesor:");
            String password = sc.nextLine();
            Profesores profesor = new Profesores(nombre, nomUser, password); // Se crea un objeto de tipo Profesores con
                                                                             // los datos ingresados
            session.save(profesor); // Se guarda el objeto en la base de datos
            tx.commit(); // Se confirma la transacción
            System.out.println("Profesor insertado: " + profesor.getNombre()); // Se muestra un mensaje de éxito
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback(); // Si hay un error, se hace un rollback de la transacción
            }
            System.out.println("Error al insertar el profesor: " + ex.getMessage()); // Se muestra un mensaje de error
        } finally {
            sc.close(); // Se cierra el objeto Scanner
            session.close(); // Se cierra la sesión de Hibernate
        }
    }

    public void insertarAlumno() {
        Scanner sc = new Scanner(System.in); // Se crea un objeto Scanner para obtener la entrada del usuario.
        Session session = HibernateUtil.getSessionFactory().openSession(); // Se obtiene una nueva sesión de Hibernate.

        // Consultar todos los alumnos
        notasORM.consultarTodosAlumnos();

        try {
            tx = session.beginTransaction(); // Se inicia una nueva transacción.

            // Se pide al usuario que proporcione los detalles del alumno.
            System.out.println("\nIngrese el nombre del alumno:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de usuario del alumno:");
            String nomUser = sc.nextLine();
            System.out.println("Ingrese la contraseña del alumno:");
            String password = sc.nextLine();

            // Se crea un nuevo objeto Alumnos con los detalles proporcionados por el
            // usuario.
            Alumnos alumno = new Alumnos(nombre, nomUser, password);

            session.save(alumno); // Se guarda el objeto alumno en la base de datos.
            tx.commit(); // Se confirma la transacción.

            System.out.println("Alumno insertado: " + alumno.getNombre()); // Se imprime un mensaje de éxito.
        } catch (Exception ex) { // Si se produce una excepción, se maneja aquí.
            if (tx != null) {
                tx.rollback(); // Se deshace la transacción.
            }
            System.out.println("Error al insertar alumno: " + ex.getMessage()); // Se imprime un mensaje de error.
        } finally { // Se asegura de que los recursos se liberen correctamente.
            // sc.close();
            session.close();
        }
    }

    public void insertarModulo() {
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner sc = new Scanner(System.in);

        // Abrir una nueva sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consultar todos los módulos
        notasORM.listarModulos();

        try {
            // Iniciar una nueva transacción
            tx = session.beginTransaction();

            // Pedir al usuario que ingrese el nombre del nuevo módulo y guardarlo en una
            // variable
            System.out.println("\nIngrese el nombre del nuevo módulo:");
            String nombre = sc.nextLine();

            // Verificar si el módulo ya existe en la base de datos
            Modulos modulo = (Modulos) session.createQuery("FROM Modulos WHERE nombre = :nombre")
                    .setParameter("nombre", nombre).uniqueResult();

            if (modulo != null) {
                // Si el módulo ya existe, mostrar un mensaje de error y cancelar la transacción
                System.out.println("Error: el módulo ya existe en la base de datos");
                return;
            }

            // Crear un nuevo objeto Modulos con el nombre ingresado
            modulo = new Modulos(nombre);

            // Guardar el objeto Modulos en la base de datos
            session.save(modulo);

            // Confirmar la transacción
            tx.commit();

            // Mostrar un mensaje indicando que el módulo fue insertado exitosamente
            System.out.println("Módulo insertado: " + modulo.getNombre());

        } catch (Exception ex) {
            // En caso de excepción, cancelar la transacción y mostrar un mensaje de error
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al insertar el módulo: " + ex.getMessage());

        } finally {
            session.close();
        }
    }

    // Método para eliminar un módulo
    public static void eliminarModulo() {
        // Abrir una nueva sesión de Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consultar todos los módulos
        notasORM.listarModulos();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIntroduce el nombre del modulo que quieres borrar:");
        String nombre = scanner.next();

        try {
            // Iniciar transacción
            tx = session.beginTransaction();
            // Obtener el modulo con el nombre proporcionado
            Modulos modulo = (Modulos) session.createQuery("from Modulos where nombre = :nombre")
                    .setParameter("nombre", nombre).uniqueResult();
            // Eliminar el modulo
            session.delete(modulo);
            // Confirmar la transacción
            tx.commit();
            // Mostrar un mensaje de éxito
            System.out.println("Módulo eliminado: " + modulo.getNombre());
        } catch (Exception ex) {
            // En caso de excepción, cancelar la transacción y mostrar un mensaje de error
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al eliminar el módulo: " + ex.getMessage());
        } finally {
            // Cerrar sesión
            session.close();
        }
    }

    public List<Modulos> listarModulos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Modulos");
        List<Modulos> listaModulos = query.getResultList();

        // Imprimir encabezado de la tabla
        System.out.println("+-------+---------+");
        System.out.printf("| \033[38;5;206m%-5s\033[0m | \033[38;5;206m%-7s\033[0m |\n", "ID", "Nombre");
        System.out.println("+-------+---------+");

        // Imprimir cada registro en la tabla
        for (Modulos m : listaModulos) {
            System.out.printf("| %-5d | %-7s |\n", m.getId(), m.getNombre());
            System.out.println("+-------+---------+");
        }

        session.close();
        return listaModulos;
    }

    public void modificarModulos() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el id del módulo a modificar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer
            Modulos modulo = session.get(Modulos.class, id);
            if (modulo == null) {
                System.out.println("El módulo con id " + id + " no existe.");
                return;
            }
            if (modulo == null) {
                throw new RuntimeException("El módulo no existe en la base de datos.");
            }
            System.out.println("Módulo seleccionado: " + modulo.getNombre());
            System.out.println("Ingrese el nuevo nombre del módulo:");
            String nombre = sc.nextLine();
            modulo.setNombre(nombre);
            session.update(modulo);
            System.out.println("Modificación realizada.");
            tx.commit();
            System.out.println("Módulo modificado: " + modulo.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al modificar el módulo: " + ex.getMessage());
        } finally {
            sc.close();
            session.close();
        }
    }

    public void listarAlumnosPorModulo() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        // Consultar todos los módulos
        notasORM.listarModulos();

        try {
            tx = session.beginTransaction();
            System.out.println("\nIngrese el nombre del módulo:");
            String nombre = sc.nextLine();
            Modulos modulo = (Modulos) session.createQuery("FROM Modulos WHERE nombre = :nombre")
                    .setParameter("nombre", nombre).uniqueResult();
            if (modulo == null) {
                System.out.println("El módulo con nombre " + nombre + " no existe.");
                return;
            }
            List<Notas> notas = session.createQuery("FROM Notas WHERE id_modulo = :id_modulo")
                    .setParameter("id_modulo", modulo.getId()).list();
            System.out.println(
                    "\nAlumnos que han cursado el módulo " + modulo.getNombre() + " con id " + modulo.getId() + ":");

            // Imprimir encabezado de la tabla
            System.out.println("+------------+----------------------+-----------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
            System.out.println("+------------+----------------------+-----------------+-------+");

            for (Notas nota : notas) {

                // Esta línea obtiene un objeto de la clase Alumnos utilizando el ID de alumno
                // asociado a la nota
                // Se utiliza el método "get" de la sesión de Hibernate para obtener el objeto
                // de la base de datos
                // La clase "Alumnos" se especifica como primer parámetro y se utiliza el método
                // "getIdAlumno" de la nota para obtener el ID de alumno asociado
                Alumnos alumno = session.get(Alumnos.class, nota.getAlumnos().getIdAlumno());
                float notaFloat = nota.getNotas();

                System.out.printf("| %-10d | %-20s | %-15s | %-5.2f |\n", alumno.getIdAlumno(), alumno.getNombre(),
                        modulo.getNombre(), notaFloat);
                System.out.println("+------------+----------------------+-----------------+-------+");
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al listar los alumnos por módulo: " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    // Obtener id de alumno con el nombre de usuario proporcionado
    public Integer getIdAlumno(String nombreUsuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Alumnos alumno = (Alumnos) session.createQuery("from Alumnos where nomUser = :nombreUsuario")
                    .setParameter("nombreUsuario", nombreUsuario).uniqueResult();
            return alumno.getIdAlumno();
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public void listarModulosPorAlumno(Integer idAlumno) {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();

            // Si se proporciona un ID de alumno, usar ese ID
            Alumnos alumno;
            if (idAlumno != null) {
                alumno = session.get(Alumnos.class, idAlumno);
            } else {
                System.out.println("Ingrese el id del alumno:");
                int id = sc.nextInt();
                alumno = session.get(Alumnos.class, id);
            }

            // Obtener las notas de todos los módulos para el alumno dado
            List<Notas> notas = session.createQuery("FROM Notas WHERE id_alumno = :idAlumno")
                    .setParameter("idAlumno", alumno.getIdAlumno()).list();

            System.out.println("Módulos cursados por " + alumno.getNombre() + " con id " + alumno.getIdAlumno() + ":");

            // Imprimir encabezado de la tabla
            System.out.println("+------------+----------------------+-----------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
            System.out.println("+------------+----------------------+-----------------+-------+");

            for (Notas nota : notas) {
                Modulos modulo = session.get(Modulos.class, nota.getModulosId());
                float notaFloat = nota.getNotas();

                System.out.printf("| %-10d | %-20s | %-15s | %-5.2f |\n", alumno.getIdAlumno(), alumno.getNombre(),
                        modulo.getNombre(), notaFloat);
                System.out.println("+------------+----------------------+-----------------+-------+");
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {

            session.close();
        }
    }

    public static void borrarProfesor() {
        Transaction tx = null;
        Scanner scanner = new Scanner(System.in);

        // Llamar al método consultarTodosProfesores para mostrar todos los profesores
        notasORM.consultarTodosProfesores();
        System.out.println("Introduce el ID del profesor que quieres borrar:");
        int id = scanner.nextInt();

        try {
            tx = sesion.beginTransaction();
            Profesores profesor = sesion.get(Profesores.class, id);
            if (profesor != null) {
                sesion.delete(profesor);
                tx.commit();
                System.out.println("Profesor eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún profesor con el ID " + id);
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al intentar borrar el profesor: " + e.getMessage());
        }
    }

    // Mostrar tabla historial
    public void listarHistorial() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();

            // Obtener todos los registros de la tabla historial
            List<Historial> historial = session.createQuery("FROM Historial").list();

            // Imprimir encabezado de la tabla
            System.out.println("+------------+---------------------------+------------+---------------------------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-25s\033[0m | \033[35m%-10s\033[0m | \033[35m%-25s\033[0m |\n",
                    "ID", "Tipo", "Usuario", "Detalle");
            System.out.println("+------------+---------------------------+------------+---------------------------+");

            // Imprimir cada registro en la tabla
            for (Historial registro : historial) {
                System.out.printf("| %-10d | %-25s | %-10s | %-25s |\n", registro.getId(), registro.getTipo(),
                        registro.getUser(), registro.getDetalle());
                System.out
                        .println("+------------+---------------------------+------------+---------------------------+");
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {

            session.close();
        }

    }

    // metodo para consultar notas de un alumno, pasandole el usuario del alumno
    public void consultarNotasAlumno(String usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();

            // Obtener las notas de todos los módulos para el alumno introducido
            List<Notas> notas = session.createQuery(
                    "SELECT n FROM Notas n INNER JOIN n.alumnos a INNER JOIN n.modulos m WHERE a.nomUser = :usuario")
                    .setParameter("usuario", usuario).list();

            // Imprimir encabezado de la tabla
            System.out.println("+------------+----------------------+-----------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
            System.out.println("+------------+----------------------+-----------------+-------+");

            for (Notas nota : notas) {
                Alumnos alumno = nota.getAlumnos();
                Modulos modulo = nota.getModulos();
                float notaFloat = nota.getNotas();

                System.out.printf("| %-10d | %-20s | %-15s | %-5.2f |\n", alumno.getIdAlumno(), alumno.getNombre(),
                        modulo.getNombre(), notaFloat);
                System.out.println("+------------+----------------------+-----------------+-------+");
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {

            session.close();
        }
    }

    // Metodo para consultar todas las notas con el nombre del alumno y el nombre
    // del modulo
    public void consultarNotas() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            tx = session.beginTransaction();

            // Obtener todas las notas con información de alumno y módulo
            List<Notas> notas = session
                    .createQuery("SELECT n FROM Notas n INNER JOIN n.alumnos a INNER JOIN n.modulos m").list();

            // Imprimir encabezado de la tabla
            System.out.println("+------------+----------------------+-----------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
            System.out.println("+------------+----------------------+-----------------+-------+");

            for (Notas nota : notas) {
                Alumnos alumno = nota.getAlumnos();
                Modulos modulo = nota.getModulos();
                float notaFloat = nota.getNotas();

                System.out.printf("| %-10d | %-20s | %-15s | %-5.2f |\n", alumno.getIdAlumno(), alumno.getNombre(),
                        modulo.getNombre(), notaFloat);
                System.out.println("+------------+----------------------+-----------------+-------+");
            }

            tx.commit();

        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    public boolean comprobarProfesor(String usuario, String password) {

        // Obtener el profesor con el nombre de usuario dado
        Profesores profesor = (Profesores) sesion.createQuery("FROM Profesores WHERE nom_user = :usuario")
                .setParameter("usuario", usuario).uniqueResult();

        // Si el profesor existe, comprobar la contraseña
        if (profesor != null) {
            return profesor.getPassword().equals(password);
        }

        // Si el profesor no existe, devolver false
        return false;
    }

    public boolean comprobarAlumno(String usuario, String password) {

        // Obtener el profesor con el nombre de usuario dado
        Alumnos alumno = (Alumnos) sesion.createQuery("FROM Alumnos WHERE nomUser = :usuario")
                .setParameter("usuario", usuario).uniqueResult();

        // Si el profesor existe, comprobar la contraseña
        if (alumno != null) {
            return alumno.getPassword().equals(password);
        }

        // Si el profesor no existe, devolver false
        return false;
    }

    // Metodo para registrar acciones en tabla historial
    public void registroHistorial(String tipo, int userId, String detalle) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();

            // Crear objeto historial
            Historial historial = new Historial();
            historial.setTipo(tipo);
            historial.setUser(userId);
            historial.setDetalle(detalle);

            // Guardar objeto historial en la base de datos
            session.save(historial);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {

            session.close();
        }
    }

    // Obtener id de profesor con el nombre de usuario proporcionado
    public Integer getIdProfesor(String nombreUsuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Profesores profesor = (Profesores) session.createQuery("from Profesores where nomUser = :nombreUsuario")
                    .setParameter("nombreUsuario", nombreUsuario).uniqueResult();
            return profesor.getId();
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }
    }

    public void insertarNotas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        // Consultar todos los alumnos
        notasORM.consultarTodosAlumnos();

        try {
            tx = session.beginTransaction();
            Scanner sc = new Scanner(System.in);

            // Pedir datos al usuario
            System.out.println("Introduce el id del alumno:");
            int idAlumno = sc.nextInt();
            System.out.println();
            notasORM.listarModulos();
            System.out.println("Introduce el id del módulo:");
            int idModulo = sc.nextInt();
            System.out.println("Introduce la nota:");
            float nota = sc.nextFloat();

            // Obtener el objeto Alumnos correspondiente al idAlumno
            Alumnos alumno = session.get(Alumnos.class, idAlumno);

            // Obtener el objeto Modulos correspondiente al idModulo
            Modulos modulo = session.get(Modulos.class, idModulo);

            // Crear el objeto Notas y asignarle el alumno, módulo y nota correspondientes
            Notas notas = new Notas(alumno, modulo, nota);

            // Comprobar si existe la nota
            Notas notaComprobar = (Notas) session
                    .createQuery("FROM Notas WHERE id_alumno = :idAlumno AND id_modulo = :idModulo")
                    .setParameter("idAlumno", idAlumno).setParameter("idModulo", idModulo).uniqueResult();

            if (notaComprobar != null) {
                System.out.println("Ya existe una nota para este alumno y módulo.");
                return;
            }

            // Guardar el objeto Notas en la base de datos
            session.save(notas);

            tx.commit();
            System.out.println("Nota insertada correctamente.");
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Actualizar nota de un alumno en la base de datos
    public void actualizarNotas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        // Consultar todos los alumnos
        notasORM.consultarTodosAlumnos();

        try {
            tx = session.beginTransaction();

            // Pedir datos al usuario
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce el id del alumno:");
            int idAlumno = sc.nextInt();
            notasORM.listarModulosPorAlumno(idAlumno);
            System.out.println("Introduce el nombre del modulo:");
            String nombreModulo = sc.next();
            System.out.println("Introduce la nota");
            float nota = sc.nextFloat();

            // Obtener el id del módulo con el nombre dado
            Modulos modulo = (Modulos) session.createQuery("FROM Modulos WHERE nombre = :nombreModulo")
                    .setParameter("nombreModulo", nombreModulo).uniqueResult();

            // Verificar si se encontró el módulo
            if (modulo == null) {
                System.out.println("El módulo no existe");
                return;
            }

            // Obtener la nota del alumno en el módulo dado
            Notas notaObj = (Notas) session
                    .createQuery("FROM Notas WHERE id_alumno = :idAlumno AND id_modulo = :idModulo")
                    .setParameter("idAlumno", idAlumno).setParameter("idModulo", modulo.getId()).uniqueResult();

            // Verificar si se encontró la nota
            if (notaObj == null) {
                System.out.println("No se encontró la nota del alumno en el módulo dado");
                return;
            }

            // Actualizar la nota
            notaObj.setNotas(nota);

            // Guardar objeto nota en la base de datos
            session.update(notaObj);

            // Obtener el nombre del alumno
            Alumnos alumno = (Alumnos) session.createQuery("FROM Alumnos WHERE id_alumno = :idAlumno")
                    .setParameter("idAlumno", idAlumno).uniqueResult();

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    // Borrar notas de tabla notas
    public void borrarNotas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        // Consultar todos los alumnos
        notasORM.consultarTodosAlumnos();

        try {
            tx = session.beginTransaction();
            // Pedir datos al usuario
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce el id del alumno:");
            int idAlumno = sc.nextInt();
            notasORM.listarModulosPorAlumno(idAlumno);
            System.out.println("Introduce el nombre del modulo:");
            String nombreModulo = sc.next();

            // Obtener el id del módulo con el nombre dado
            Modulos modulo = (Modulos) session.createQuery("FROM Modulos WHERE nombre = :nombreModulo")
                    .setParameter("nombreModulo", nombreModulo).uniqueResult();

            // Verificar si se encontró el módulo
            if (modulo == null) {
                System.out.println("El módulo no existe");
                return;
            }

            // Obtener la nota del alumno en el módulo dado
            Notas notaObj = (Notas) session
                    .createQuery("FROM Notas WHERE id_alumno = :idAlumno AND id_modulo = :idModulo")
                    .setParameter("idAlumno", idAlumno).setParameter("idModulo", modulo.getId()).uniqueResult();

            // Verificar si se encontró la nota
            if (notaObj == null) {
                System.out.println("No se encontró la nota del alumno en el módulo dado");
                return;
            }

            // Borrar la nota
            session.delete(notaObj);

            // Obtener el nombre del alumno
            Alumnos alumno = (Alumnos) session.createQuery("FROM Alumnos WHERE id_alumno = :idAlumno")
                    .setParameter("idAlumno", idAlumno).uniqueResult();

            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }
}