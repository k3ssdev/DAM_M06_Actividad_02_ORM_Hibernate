package resources;
// Generated 09-mar-2023 22:01:34 by Hibernate Tools 4.3.1



/**
 * Notas generated by hbm2java
 */
public class Notas  implements java.io.Serializable {


     private Integer idNotas;
     private int idAlumno;
     private int idModulo;
     private float notas;

    public Notas() {
    }

    public Notas(int idAlumno, int idModulo, float notas) {
       this.idAlumno = idAlumno;
       this.idModulo = idModulo;
       this.notas = notas;
    }
   
    public Integer getIdNotas() {
        return this.idNotas;
    }
    
    public void setIdNotas(Integer idNotas) {
        this.idNotas = idNotas;
    }
    public int getIdAlumno() {
        return this.idAlumno;
    }
    
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
    public int getIdModulo() {
        return this.idModulo;
    }
    
    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }
    public float getNotas() {
        return this.notas;
    }
    
    public void setNotas(float notas) {
        this.notas = notas;
    }




}


