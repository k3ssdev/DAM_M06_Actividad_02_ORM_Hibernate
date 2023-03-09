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


public class TestORM {

    public static void main(String[] args) {
       
        NotasORM miGestor = new NotasORM();
        
        
        Alumnos login = new Alumnos(1);
         
       NotasORM.consulta("select a from Alumnos a where idAlumno=1");
       //miGestor.close();

     if (miGestor.existeAlumno(login)) {
       System.out.println("El alumno ya existe.");
   } else {
       miGestor.insertarAlumno(login);
       System.out.println("Alumno dado de alta");
   }

       
    }
}

        