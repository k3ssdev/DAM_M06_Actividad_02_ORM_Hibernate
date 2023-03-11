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
        NotasORM miGestor = new NotasORM();

        Alumnos login = new Alumnos("Alberto", "alberto", "1234");

        // NotasORM.borrarAlumno(1);

        NotasORM.insertarAlumno(login);

        NotasORM.consulta("select a from Alumnos a where idAlumno=1");
        // miGestor.close();

        if (miGestor.existeAlumno(login)) {
            System.out.println("El alumno ya existe.");
        } else {
            miGestor.insertarAlumno(login);
            System.out.println("Alumno dado de alta");
        }

    }
}
