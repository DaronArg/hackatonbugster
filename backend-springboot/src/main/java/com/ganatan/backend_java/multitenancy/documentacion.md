Documentación: Arquitectura Multi-Tenant por Subdominio
1. Resumen General
Esta arquitectura implementa un modelo de multi-tenancy con base de datos compartida. Todas las entidades de todos los clientes (tenants) residen en las mismas tablas, y cada fila se diferencia por una columna tenant_id.

El sistema identifica al tenant actual extrayendo el subdominio de la URL de la petición HTTP (ej: tenant1 en tenant1.miapi.com).

El flujo se basa en cuatro componentes principales que trabajan en conjunto:

TenantInterceptor: Captura el subdominio de cada petición entrante.
TenantContext: Almacena el ID del tenant actual de forma segura para el hilo de la petición.
TenantListener: Inserta automáticamente el tenant_id correcto antes de guardar cualquier entidad nueva o actualizada.
Filtro de Hibernate: Modifica todas las consultas de lectura (SELECT) para que solo devuelvan los datos pertenecientes al tenant actual.
2. Componentes Clave
A continuación se detalla el funcionamiento de cada clase.

a. TenantContext
Esta clase es el corazón del sistema para mantener el estado del tenant.

Propósito: Guardar el tenantId extraído de la petición de una manera que sea accesible durante todo el ciclo de vida de esa petición, pero que esté aislada de otras peticiones concurrentes.
Mecanismo: Utiliza un ThreadLocal<String>. Un ThreadLocal es una variable especial de Java que garantiza que cada hilo (en un servidor web, cada petición es manejada por un hilo) tenga su propia copia independiente de la variable. Esto evita que una petición del tenant1 pueda ver o interferir con los datos de una petición simultánea del tenant2.
Métodos Clave:
setTenantId(String tenantId): Llamado por el TenantInterceptor al inicio de la petición para guardar el ID del tenant.
getTenantId(): Llamado por el TenantListener (al escribir) y por el sistema de filtros de Hibernate (al leer) para recuperar el ID del tenant actual.
clear(): Método crucial llamado por el TenantInterceptor al final de la petición para limpiar el ThreadLocal. Esto previene fugas de memoria y asegura que el hilo no contenga un tenantId obsoleto cuando sea reutilizado para una nueva petición.
b. TenantInterceptor
Es un interceptor de peticiones de Spring MVC que actúa como puerta de entrada al sistema de multi-tenancy.

Propósito: Inspeccionar cada petición HTTP entrante, extraer el subdominio y establecer el contexto del tenant.
Mecanismo: Implementa la interfaz HandlerInterceptor de Spring.
Métodos Clave:
preHandle(...): Se ejecuta antes de que la petición llegue al controlador. Aquí se implementa la lógica para:
Obtener el Host del encabezado de la petición (ej: tenant1.localhost:8080).
Extraer la primera parte del host como el tenantId (ej: tenant1).
Llamar a TenantContext.setTenantId(...) con el valor extraído.
afterCompletion(...): Se ejecuta después de que la petición se ha completado y la respuesta se ha enviado. Su única y crítica responsabilidad es llamar a TenantContext.clear() para limpiar el contexto.
c. TenantAware
Es una interfaz simple que actúa como un marcador o contrato.

Propósito: Marcar qué entidades JPA están sujetas a la lógica de multi-tenancy.
Mecanismo: Define un único método: setTenantId(String tenantId).
Uso: Cualquier entidad que deba pertenecer a un tenant (como Person, Product, etc.) debe implementar esta interfaz. Esto le permite al TenantListener asignarle un tenantId de forma genérica. En tu proyecto, la BaseEntity implementa esta interfaz, por lo que todas las entidades que heredan de ella son automáticamente "conscientes del tenant".
d. TenantListener
Es un "oyente" de eventos de ciclo de vida de JPA/Hibernate.

Propósito: Automatizar la inserción del tenant_id en las entidades justo antes de que se guarden en la base de datos.
Mecanismo: Utiliza las anotaciones @PrePersist y @PreUpdate de JPA.
Métodos Clave:
Un método anotado con @PrePersist y @PreUpdate (ej: beforeSave(...)):
Se ejecuta automáticamente antes de cualquier operación INSERT o UPDATE.
Comprueba si la entidad que se va a guardar es una instancia de TenantAware.
Si lo es, recupera el tenantId actual llamando a TenantContext.getTenantId().
Asigna ese tenantId a la entidad usando el método setTenantId(...) de la interfaz TenantAware.
3. Flujo de una Petición
Para unir todo, aquí está el ciclo de vida completo de una petición en el sistema:

Petición Entrante: Un cliente hace una petición a http://tenant2.miapi.com/persons.
Intercepción: TenantInterceptor.preHandle() se activa. Extrae "tenant2" del host y llama a TenantContext.setTenantId("tenant2").
Ejecución del Controlador: La petición llega al PersonController.
Operación de Lectura (GET):
El código llama a personRepository.findAll().
El filtro de Hibernate, definido en BaseEntity (@Filter(name = "tenantFilter")), se activa.
El sistema de persistencia obtiene el tenantId de TenantContext.getTenantId() ("tenant2") y lo usa para parametrizar el filtro.
La consulta SQL generada es SELECT * FROM person WHERE tenant_id = 'tenant2'. El controlador solo recibe las personas del tenant 2.
Operación de Escritura (POST):
El código llama a personRepository.save(newPerson).
Justo antes de que Hibernate genere el INSERT, el TenantListener se activa.
El listener obtiene "tenant2" de TenantContext.getTenantId() y llama a newPerson.setTenantId("tenant2").
La consulta SQL generada es INSERT INTO person (name, tenant_id) VALUES ('un nombre', 'tenant2').
Finalización: La respuesta se envía al cliente.
Limpieza: TenantInterceptor.afterCompletion() se activa y llama a TenantContext.clear(), dejando el hilo limpio para la siguiente petición.