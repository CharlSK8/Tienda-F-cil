# TIENDA FACIL

- - -

## Descripción del proyecto

Este proyecto consiste en el desarrollo de una Tienda con uns sistema de gestión para controlar sus clientes y pedidos
de manera eficiente. Este sistema permitirá a los empleados de la tienda registrar y gestionar la información de los
clientes y relaizar seguimiento de pedidos activos y pendientes.

- - - 

## Integrantes del Equipo

- **Hcharlinson Perez** - _Líder del Proyecto_
    - [CharlSK8](https://github.com/CharlSK8)

- **BREYMO**
    - [Breymo](https://github.com/breymo)
- **Cloud**
    - [Gusgonza42](https://github.com/gusgonza42)
- **DeadShooter**
    - [ErikGarciaLopez](https://github.com/ErikGarciaLopez)
- **Jeisson Santander**
    - [JeissonSantander](https://github.com/JeissonSantander)
- **Marcos Carmona**
    - [MarcosCamona02](https://github.com/MarcosCarmona02)

---

## Tecnologías Utilizadas

### Backend

- **Java 17** y **Spring Boot**: Framework para construir la API REST que servirá como backend de la aplicación.
- **JPA / Hibernate**: Para la gestión de la base de datos.
- **MySQL**: Base de datos para almacenar la información de clientes y pedidos.
- **Swagger**: Para la documentación de la API.

### Frontend

- **Angular**: Framework para construir la interfaz de usuario que los empleados de la tienda utilizarán para
  interactuar con el sistema.

### Herramientas de Colaboración y Organización

- **Git y Git Flow**: Para el control de versiones y organización del trabajo en equipo.
- **Trello**: Para organizar tareas y el flujo de trabajo.
- **GitHub Projects**: Para gestionar las tareas y el progreso de cada miembro del equipo.
- **Discord**: Para coordinar reuniones, resolver dudas y facilitar la comunicación directa para explicaciones
  detalladas del proyecto.

---

## Metodología de Trabajo

Para gestionar las ramas y versiones del proyecto, seguimos la metodología **Git Flow**. Además, contamos con guías
específicas que detallan cómo implementar esta metodología y utilizar Trello en el flujo de trabajo.

- **Git Flow**:
    - **`master`**: Contiene la versión estable y lista para producción.
    - **`develop`**: Reúne la última versión en desarrollo, con las funcionalidades recientes integradas y listas para
      pruebas.
    - **Feature branches**: Se utilizan para el desarrollo de nuevas funcionalidades, permitiendo que cada cambio sea
      independiente y fácil de probar.
    - **Release branches**: Ramas dedicadas a la preparación de una versión para producción, enfocadas en estabilizar el
      código antes de su lanzamiento.
    - **Hotfix branches**: Ramas para corregir errores críticos en producción que necesitan solución inmediata.

Consulta nuestra guía detallada sobre *
*[Guía de Creación de Ramas y Draft PR en GitHub](utils/guides/Como_trabajar_con_GitHub_y_Git_V1.pdf)**
para obtener una explicación paso a paso de cómo emplear esta metodología en el proyecto.

- **Organización de Tareas**:
    - **Trello** y **GitHub Projects**: Utilizamos **Trello** para la planificación inicial de tareas y organización del
      flujo de trabajo, y **GitHub Projects** para gestionar los Pull Requests y vincular tareas con las ramas de Git.

Para conocer cómo estructurar las tareas y asignaciones, revisa nuestra *
*[Guía para uso de Trello](utils/guides/Como_usar_Trello_V1.pdf)**.

---

## 3. Instrucciones para Configurar el Proyecto en Local

Esta sección explica cómo clonar el repositorio, configurar el entorno local y ejecutar el proyecto tanto para el
backend como el frontend. Asegúrate de seguir estos pasos para que el proyecto se ejecute correctamente en tu entorno.

### Clonar el Repositorio

1. **Clonar desde IntelliJ**:
    - Abre IntelliJ y selecciona **File > New > Project from Version Control (VCS)**.
    - Pega la URL del repositorio y selecciona la carpeta de destino en HTTP o SSH:
      ```bash
      https://github.com/CharlSK8/Tienda-Facil.git
      ```
      ```bash
      git@github.com:CharlSK8/Tienda-Facil.git
      ```
    - IntelliJ descargará el repositorio y abrirá el proyecto automáticamente.

### Configuración del Backend (Spring Boot y Docker)

1. **Iniciar Docker**:
    - Asegúrate de tener Docker instalado y en ejecución en tu máquina, ya que Docker se utilizará para configurar y
      ejecutar los servicios necesarios para la base de datos.

2. **Ejecutar el Archivo Docker**:
    - En la raíz del repositorio, encontrarás el archivo `docker-compose.yml`.
    - Para construir y ejecutar los contenedores necesarios, abre una terminal en la raíz del proyecto y ejecuta:
      ```bash
      docker-compose up --build
      ```
      Alternativamente, si tienes el plugin de Docker instalado en IntelliJ, puedes hacer clic en **Run** desde el
      archivo `docker-compose.yml`.

3. **Ejecutar el Backend en IntelliJ**:
    - Ejecuta el archivo `CoreApplication` con la opción **Run** de IntelliJ para iniciar la aplicación. El archivo se
      encuentra en:
      ```plaintext
      `src/main/java/com/tienda/facil/core/CoreApplication.java`
      ```

4. **Verificar la Ejecución**:
    - Una vez iniciado Spring Boot, verifica que la aplicación esté funcionando correctamente accediendo a:
      ```plaintext
      http://localhost:8080
      ```
    - También puedes acceder a la documentación de la API si se configuró Swagger u OpenAPI en:
      ```plaintext
      http://localhost:8080/swagger-ui.html
      ```

Con estos pasos, el backend se ejecutará y utilizará Docker para la base de datos MySQL, configurada según el archivo
`application.properties`.

### Configuración del Frontend (Angular)

> [!NOTE]  
> La configuración del frontend con Angular está en proceso de desarrollo. Esta sección se actualizará pronto con
> instrucciones detalladas para configurar y ejecutar el frontend en el entorno local.

---