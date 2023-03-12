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
public class NotasORMcopy {

    private static Session sesion;
    private static Transaction tx;

    public NotasORMcopy() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public static void consulta(String c) {// Ejecuta una consulta cuando el resultado se devuelve como un objeto
                                           // Albumes
        System.out.println("Salida de consulta");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery(c);
        List results = q.list();

        Iterator alumnoIterator = results.iterator();
        while (alumnoIterator.hasNext()) {
            Alumnos a2 = (Alumnos) alumnoIterator.next();
            System.out.println(a2.getNombre());
        }
        session.close();
    }

    public static void insertarAlumno(Alumnos a) throws ConstraintViolationException {
        try {// Siempre se trabaja con transacciones
            tx = sesion.beginTransaction();
            sesion.save(a);
            tx.commit();
        } catch (ConstraintViolationException ex) {
            // haríamos el rollback
            tx.rollback();
            throw ex;
        }
    }

    public void modificarAlumnoId() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el id del alumno a modificar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer
            Alumnos alumno = session.get(Alumnos.class, id);
            System.out.println("Alumno seleccionado: " + alumno.getNombre());
            System.out.println("Ingrese el nuevo nombre del alumno:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nuevo nombre de usuario del alumno:");
            String nomUser = sc.nextLine();
            System.out.println("¿Quiere cambiar la contraseña? (S/N)");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                System.out.println("Ingrese la nueva contraseña del alumno:");
                String password = sc.nextLine();
                alumno.setPassword(password);
            }
            alumno.setNombre(nombre);
            alumno.setNomUser(nomUser);
            session.update(alumno);
            System.out.println("Modificación realizada.");
            tx.commit();
            System.out.println("Alumno modificado: " + alumno.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            sc.close();
            session.close();
        }
    }

    public void modificarProfesorId() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el id del profesor a modificar:");
            int id = sc.nextInt();
            sc.nextLine(); // Consumir la línea en blanco en el buffer
            Profesores profesor = session.get(Profesores.class, id);
            System.out.println("Profesor seleccionado: " + profesor.getNombre());
            System.out.println("Ingrese el nuevo nombre del profesor:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nuevo nombre de usuario del profesor:");
            String nomUser = sc.nextLine();
            System.out.println("¿Quiere cambiar la contraseña? (S/N)");
            String respuesta = sc.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                System.out.println("Ingrese la nueva contraseña del profesor:");
                String password = sc.nextLine();
                profesor.setPassword(password);
            }
            profesor.setNombre(nombre);
            profesor.setNomUser(nomUser);
            session.update(profesor);
            System.out.println("Modificación realizada.");
            tx.commit();
            System.out.println("Profesor modificado: " + profesor.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            sc.close();
            session.close();
        }
    }

    public static void borrarAlumno() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del alumno que quieres borrar:");
        int id = scanner.nextInt();
    
        try {
            tx = sesion.beginTransaction();
            Alumnos alumno = sesion.get(Alumnos.class, id);
            if (alumno != null) {
                sesion.delete(alumno);
                tx.commit();
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún alumno con el ID " + id);
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al intentar borrar el alumno: " + e.getMessage());
        }
    }
    


    public Alumnos iniciarSesionAlumno(String nomUser, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Alumnos alumno = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Alumnos where nomUser = :nomUser and password = :password");
            query.setParameter("nomUser", nomUser);
            query.setParameter("password", password);
            alumno = (Alumnos) query.uniqueResult();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
        return alumno;
    }

    // Metodo close
    public void close() {
        sesion.close();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("from Alumnos where nomUser = :nomUser");
            query.setParameter("nomUser", nomUser);
            Alumnos alumno = (Alumnos) query.getSingleResult();

            alumno.setPassword(nuevaPassword);
            session.update(alumno);
            tx.commit();

            System.out.println("La contraseña del alumno con nombre de usuario '" + nomUser
                    + "' ha sido actualizada exitosamente.");

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

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
            Profesores profesor = new Profesores(nombre, nomUser, password);
            session.save(profesor);
            tx.commit();
            System.out.println("Profesor insertado: " + profesor.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al insertar el profesor: " + ex.getMessage());
        } finally {
            sc.close();
            session.close();
        }
    }

    public void insertarAlumno() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el nombre del alumno:");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de usuario del alumno:");
            String nomUser = sc.nextLine();
            System.out.println("Ingrese la contraseña del alumno:");
            String password = sc.nextLine();
            Alumnos alumno = new Alumnos(nombre, nomUser, password);
            session.save(alumno);
            tx.commit();
            System.out.println("Alumno insertado: " + alumno.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al insertar alumno: " + ex.getMessage());
        } finally {
            sc.close();
            session.close();
        }
    }

    public void insertarModulo() {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el nombre del nuevo módulo:");
            String nombre = sc.nextLine();
            Modulos modulo = new Modulos(nombre);
            session.save(modulo);
            tx.commit();
            System.out.println("Módulo insertado: " + modulo.getNombre());
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al insertar el módulo: " + ex.getMessage());
        } finally {
            sc.close();
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
        try {
            tx = session.beginTransaction();
            System.out.println("Ingrese el id del módulo:");
            int id = sc.nextInt();
            Modulos modulo = session.get(Modulos.class, id);
            List<Notas> notas = session.createQuery("FROM Notas WHERE idModulo = :idModulo")
                    .setParameter("idModulo", id).list();
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
                Alumnos alumno = session.get(Alumnos.class, nota.getAlumnos());
                float notaFloat = nota.getNotas();
                System.out.printf("| %-10s | %-15s | %-10d | %-20s | %-5.2f |\n", id, modulo.getNombre(),
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
            sc.close();
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
            List<Notas> notas = session.createQuery("FROM Notas WHERE alumnos = :alumnos")
                    .setParameter("alumnos", alumno.getIdAlumno()).list();

            System.out.println("Módulos cursados por " + alumno.getNombre() + " con id " + alumno.getIdAlumno() + ":");

            // Imprimir encabezado de la tabla
            System.out.println("+------------+----------------------+-----------------+-------+");
            System.out.printf(
                    "| \033[35m%-10s\033[0m | \033[35m%-20s\033[0m | \033[35m%-15s\033[0m | \033[35m%-5s\033[0m |\n",
                    "ID Alumno", "Nombre Alumno", "Nombre Modulo", "Nota");
            System.out.println("+------------+----------------------+-----------------+-------+");

            for (Notas nota : notas) {
                Alumnos alumnos = session.get(Alumnos.class, nota.getAlumnos());
                Modulos modulo = session.get(Modulos.class, nota.getModulos());
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
            sc.close();
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
    
    public static void borrarModulo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del módulo que quieres borrar:");
        int id = scanner.nextInt();
    
        try {
            tx = sesion.beginTransaction();
            Modulos modulo = sesion.get(Modulos.class, id);
            if (modulo != null) {
                sesion.delete(modulo);
                tx.commit();
                System.out.println("Módulo eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún módulo con el ID " + id);
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error al intentar borrar el módulo: " + e.getMessage());
        }
    }
    

}
