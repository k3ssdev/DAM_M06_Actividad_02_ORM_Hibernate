package resources;
// Generated 09-mar-2023 22:01:34 by Hibernate Tools 4.3.1



/**
 * Alumnos generated by hbm2java
 */
public class Alumnos  implements java.io.Serializable {


     private Integer idAlumno;
     private String nombre;
     private String nomUser;
     private String password;

    public Alumnos() {
    }

    public Alumnos(String nombre, String nomUser, String password) {
       this.nombre = nombre;
       this.nomUser = nomUser;
       this.password = password;
    }
   
     public Alumnos(int idAlumno) {
       this.idAlumno = idAlumno;
    }
    
    public Integer getIdAlumno() {
        return this.idAlumno;
    }
    
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNomUser() {
        return this.nomUser;
    }
    
    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }




}

