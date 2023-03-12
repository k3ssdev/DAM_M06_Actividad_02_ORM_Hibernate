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

    // Definición de variables de clase
    private static Session sesion;
    private static Transaction tx;

    // Constructor de la clase
    public NotasORM() {
        // Obtenemos una sesión de Hibernate
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    // Metodo close
    public void close() {
        sesion.close();
    }

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
            // Cerrar el objeto Scanner y la sesión de Hibernate
            sc.close();
            session.close();
        }
    }

    public static void borrarAlumno() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del alumno que quieres borrar:");
        int id = scanner.nextInt();

        try {
            // Iniciar transacción
            tx = sesion.beginTransaction();
            // Obtener el alumno con el ID proporcionado
            Alumnos alumno = sesion.get(Alumnos.class, id);
            // Si se encontró un alumno con el ID
            if (alumno != null) {
                // Borrar el alumno
                sesion.delete(alumno);
                // Confirmar la transacción
                tx.commit();
                System.out.println("Alumno eliminado correctamente.");
            } else { // Si no se encontró un alumno con el ID
                System.out.println("No se encontró ningún alumno con el ID " + id);
            }
        } catch (Exception e) { // En caso de error
            if (tx != null) {
                tx.rollback(); // Deshacer la transacción
            }
            System.out.println("Error al intentar borrar el alumno: " + e.getMessage());
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

        try {
            tx = session.beginTransaction(); // Se inicia una nueva transacción.

            // Se pide al usuario que proporcione los detalles del alumno.
            System.out.println("Ingrese el nombre del alumno:");
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

        try {
            // Iniciar una nueva transacción
            tx = session.beginTransaction();

            // Pedir al usuario que ingrese el nombre del nuevo módulo y guardarlo en una
            // variable
            System.out.println("Ingrese el nombre del nuevo módulo:");
            String nombre = sc.nextLine();

            // Crear un nuevo objeto Modulos con el nombre ingresado
            Modulos modulo = new Modulos(nombre);

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del modulo que quieres borrar:");
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
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el id del módulo:");
            int id = sc.nextInt();
            sc.nextLine(); // Agregar esta línea para eliminar la línea en blanco del buffer
            Modulos modulo = session.get(Modulos.class, id);
            if (modulo == null) {
                System.out.println("El módulo con id " + id + " no existe.");
                return;
            }
            List<Notas> notas = session.createQuery("FROM Notas WHERE id_modulo = :id_modulo")
                    .setParameter("id_modulo", id).list();
            System.out.println("Alumnos que han cursado el módulo " + modulo.getNombre() + " con id " + id + ":");
    
            // Imprimir encabezado de la tabla en morado
            System.out.println("+------------+-----------------+------------+----------------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-15s\033[0m | \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Modulo", "Nombre Modulo", "ID Alumno",
                    "Nombre Alumno", "Nota");
            System.out.println("+------------+-----------------+------------+----------------------+-------+");
    
            // Imprimir cada registro en la tabla
            for (Notas nota : notas) {
                Alumnos alumno = session.get(Alumnos.class, nota.getAlumnosId());
                float notaFloat = nota.getNotas();
                System.out.printf("| %-10d | %-15s | %-10d | %-20s | %-5.2f |\n", id, modulo.getNombre(),
                        alumno.getIdAlumno(), alumno.getNombre(), notaFloat);
                System.out.println("+------------+-----------------+------------+----------------------+-------+");
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

            // Si se proporciona un ID de alumno, obtener ese alumno. De lo contrario, pedir
            // al usuario que ingrese un ID de alumno
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
        Scanner scanner = new Scanner(System.in);
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

    public void listarHistorial() {
    }

    // metodo para consultar notas de un alumno, pasandole el usuario del alumno
    public void consultarNotasAlumno(String usuario) {
        // Obtener el alumno con el nombre de usuario dado
        Alumnos alumno = (Alumnos) sesion.createQuery("FROM Alumnos WHERE nomUser = :usuario")
                .setParameter("usuario", usuario).uniqueResult();

        // Obtener las notas de todos los módulos para el alumno dado
        List<Notas> notas = sesion.createQuery("FROM Notas WHERE alumnos = :alumnos")
                .setParameter("alumnos", alumno.getIdAlumno()).list();

        System.out.println("Módulos cursados por " + alumno.getNombre() + " con id " + alumno.getIdAlumno() + ":");

        // Imprimir encabezado de la tabla
        System.out.println("+------------+----------------------+-----------------+-------+");
        System.out.printf(
                "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
        System.out.println("+------------+----------------------+-----------------+-------+");

        for (Notas nota : notas) {
            Alumnos alumnos = sesion.get(Alumnos.class, nota.getAlumnosId());
            Modulos modulo = sesion.get(Modulos.class, nota.getAlumnosId());
            float notaFloat = nota.getNotas();

            System.out.printf("| %-10d | %-20s | %-15s | %-5.2f |\n", alumno.getIdAlumno(), alumno.getNombre(),
                    modulo.getNombre(), notaFloat);
            System.out.println("+------------+----------------------+-----------------+-------+");
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
}