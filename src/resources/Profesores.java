package resources;
// Generated 09-mar-2023 22:01:34 by Hibernate Tools 4.3.1



/**
 * Profesores generated by hbm2java
 */
public class Profesores  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String nomUser;
     private String password;

    public Profesores() {
    }

    public Profesores(String nombre, String nomUser, String password) {
       this.nombre = nombre;
       this.nomUser = nomUser;
       this.password = password;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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

