/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notaslinkia;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
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

    private Session sesion;
    private Transaction tx;
    
    public NotasORM() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

        public static void consulta(String c)
    {//Ejecuta una consulta cuando el resultado se devuelve como un objeto Albumes
        System.out.println ("Salida de consulta");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q= session.createQuery(c);
        List results = q.list();
        
        Iterator alumnoIterator= results.iterator();
        while (alumnoIterator.hasNext())
        {
            Alumnos a2= (Alumnos)alumnoIterator.next();
            System.out.println ( a2.getNombre());
        }
        session.close();
    }

    public void insertarAlumno(Alumnos a) {
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

    public void modificarAlumno(Alumnos a) {
        tx = sesion.beginTransaction();
        sesion.update(a);
        tx.commit();
    }

    public void modificarNombreAlumno(Alumnos a) {
        modificarAlumno(a);
    }

    public void borrarAlumno(Alumnos a) {
        tx = sesion.beginTransaction();
        sesion.delete(a);
        tx.commit();
    }

    public boolean existeAlumno(Alumnos a) {
        Alumnos checkAlumno = (Alumnos) sesion.get(Alumnos.class, a.getIdAlumno());
//        if (elSocio != null) {
//            return true;
//        } else {
//            return false;
//        }
        return (checkAlumno != null);
    }
       
    public void close() {
      sesion.close();
    }
    
    
}
