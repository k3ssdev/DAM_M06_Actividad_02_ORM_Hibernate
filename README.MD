# [DAM][M06] Actividad 2 - Base de datos con JDBC y ORM
## Proyecto de sistema de gestión de notas de Linkia

Este proyecto es una aplicación de Java que utiliza Hibernate para interactuar con una base de datos MySQL que contiene información sobre estudiantes, profesores, módulos y notas.

## Estructura de archivos

El proyecto consta de los siguientes archivos:

- `hibernate.cfg.xml`: configuración de Hibernate
- `hibernate.reveng.xml`: archivo de reversión de ingeniería para generar archivos de mapeo de Hibernate
- `persistence.xml`: archivo de configuración de JPA
- `Alumnos.hbm.xml`: archivo de mapeo de Hibernate para la tabla `alumnos`
- `Alumnos.java`: clase Java para la entidad `Alumnos`
- `HibernateUtil.java`: clase Java para configurar la sesión de Hibernate
- `Historial.hbm.xml`: archivo de mapeo de Hibernate para la tabla `historial`
- `Historial.java`: clase Java para la entidad `Historial`
- `Modulos.hbm.xml`: archivo de mapeo de Hibernate para la tabla `modulos`
- `Modulos.java`: clase Java para la entidad `Modulos`
- `Notas.hbm.xml`: archivo de mapeo de Hibernate para la tabla `notas`
- `Notas.java`: clase Java para la entidad `Notas`
- `Notascopy.java`: clase Java para la entidad `Notascopy`
- `Profesores.hbm.xml`: archivo de mapeo de Hibernate para la tabla `profesores`
- `Profesores.java`: clase Java para la entidad `Profesores`
- `NotasORM.java`: clase Java para interactuar con la base de datos y realizar operaciones CRUD
- `TestORM.java`: clase Java para probar las operaciones CRUD
- `notaslinkia_Alberto_Perez_del_Rio.sql`: archivo SQL para crear y poblar la base de datos
- `required_libraries.zip`: archivo comprimido con las librerías necesarias para ejecutar el proyecto.
- `NotasLinkia.ja`: ejecutable de la aplicación de Java.


## Uso

Para utilizar este proyecto, debe seguir los siguientes pasos:

1. Descargue e instale XAMPP para MySQL.
2. Cree una base de datos llamada `notaslinkia` en MySQL.
3. Ejecute el archivo `notaslinkia_Alberto_Perez_del_Rio.sql` para crear y poblar las tablas necesarias en la base de datos.
4. Es importante que el usuario de MySQL sea `root` y la contraseña esté vacía.
5. En la base de datos, debe haber un usuario en la tabla `profesores` con el nombre `Administrador`, el usuario `admin` y la contraseña `admin` para acceder al menú de administrador.
6. Abra el proyecto en su IDE.
7. Configure la conexión a la base de datos en `hibernate.cfg.xml`.
8. Ejecute la clase `TestORM.java` para probar las operaciones CRUD en la base de datos.


## Créditos

Este proyecto fue creado por Alberto Pérez del Río para un ejercicio de programación en Java de la asignatura de Acceso a Datos del ciclo superior de Desarrollo de Aplicaciones Multiplataforma.
