package resources;
// Generated 09-mar-2023 22:01:34 by Hibernate Tools 4.3.1




/**
 * Notas generated by hbm2java
 */
public class Notas  implements java.io.Serializable {


    private Integer idNotas;
    private Alumnos alumnos;
    private Modulos modulos;
    private float notas;

   public Notas() {
   }

   public Notas(Alumnos alumnos, Modulos modulos, float notas) {
      this.alumnos = alumnos;
      this.modulos = modulos;
      this.notas = notas;
   }
  
   public Integer getIdNotas() {
       return this.idNotas;
   }
   
   public void setIdNotas(Integer idNotas) {
       this.idNotas = idNotas;
   }
   public Alumnos getAlumnos() {
       return this.alumnos;
   }
   
   public Integer getAlumnosId() {
    return this.alumnos.getIdAlumno();
}
   public void setAlumnos(Alumnos alumnos) {
       this.alumnos = alumnos;
   }
   public Modulos getModulos() {
       return this.modulos;
   }
   
   public Integer getModulosId() {
    return this.modulos.getId();
}

   public void setModulos(Modulos modulos) {
       this.modulos = modulos;
   }
   public float getNotas() {
       return this.notas;
   }
   
   public void setNotas(float notas) {
       this.notas = notas;
   }




}


