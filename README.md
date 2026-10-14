# P7 · Spring Boot: arquitectura en capas y CRUD REST

> Práctica individual · Sesión 7 · Realizable en clase (haz hasta donde llegues)

## 🎯 Objetivo

Implementar una API REST con **Spring Boot** y **arquitectura en capas** (Controller → Service → Repository → Model). El recurso `Student` viene **completo como ejemplo de referencia**; tu trabajo es implementar el recurso `Subject` reflejando ese mismo patrón.

## 📚 Lo que vas a practicar
- Las cuatro capas y la inyección de dependencias
- Endpoints REST con `@RestController`, `@GetMapping`, `@PostMapping`…
- Validación de entrada con `@Valid` y Jakarta Bean Validation
- Códigos de estado correctos (`200`, `201`, `204`, `404`)

> 📎 Teoría de apoyo: apuntes de la **Sesión 7**.

---

## 📋 Antes de empezar

```bash
git clone https://github.com/UFV-INGINF/dis-p07-TUUSUARIO.git
cd dis-p07-TUUSUARIO
mvn spring-boot:run        # arranca en http://localhost:8080
```

Prueba que el ejemplo funciona (con Thunder Client, Postman o `curl`):
```bash
curl http://localhost:8080/api/v1/students
curl -X POST http://localhost:8080/api/v1/students \
     -H "Content-Type: application/json" \
     -d '{"name":"Ada Lovelace","email":"ada@ufv.es","grade":9.2,"tags":["java"]}'
```

## 📂 Estructura del repositorio

```
dis-p07-TUUSUARIO/
└─ src/main/java/com/ufv/dis/
   ├─ DisApplication.java                  arranque
   ├─ model/Student.java                   ✅ ejemplo completo
   ├─ model/Subject.java                   ✅ modelo dado
   ├─ repository/StudentRepository.java    ✅ ejemplo completo
   ├─ repository/SubjectRepository.java    ✅ dado (persistencia JSON)
   ├─ service/StudentService.java          ✅ ejemplo completo
   ├─ service/SubjectService.java          ← TÚ lo implementas
   ├─ controller/StudentController.java    ✅ ejemplo completo
   ├─ controller/SubjectController.java    ← TÚ lo implementas
   └─ exception/                           ✅ manejo de errores (404 / 400)
```

> El recurso **Student** está terminado: úsalo como plantilla. El recurso **Subject** es tu tarea.

---

## 🛠 Tarea

### Parte 1 — Núcleo: implementa el CRUD de `Subject`

Reflejando la rama `Student`:

1. **`SubjectService`**: implementa `getAll`, `getById`, `create`, `update`, `delete` (ahora son stubs). Cuando algo no exista, lanza `NotFoundException`.
2. **`SubjectController`**: añade los cinco endpoints REST:
   - `GET /api/v1/subjects` → `200`
   - `GET /api/v1/subjects/{id}` → `200` o `404`
   - `POST /api/v1/subjects` → `201` con header `Location`
   - `PUT /api/v1/subjects/{id}` → `200` o `404`
   - `DELETE /api/v1/subjects/{id}` → `204` o `404`
   - Valida el cuerpo con `@Valid`.

Prueba cada endpoint y comprueba los códigos de estado.

### Parte 2 — Ampliación (si te da tiempo)

3. Comprueba que las **validaciones** funcionan: crea un `Subject` con `credits` fuera de rango o sin `code` y verifica que devuelve `400` con el cuerpo de error.
4. Añade un endpoint extra: `GET /api/v1/subjects/search?code=...` que filtre por código.

---

## ✅ Entrega

```bash
git add .
git commit -m "P7: CRUD de Subject implementado"
git push origin main      # o 'master'
```

Debe quedar el recurso `Subject` con su service y su controller funcionando, con los códigos de estado correctos. No subas `target/` ni `data/`.

---

## ✅ Cómo se corrige

Al hacer push se lanza la corrección automática. **No se lee tu código: se llaman a tus endpoints
de verdad** y se comprueba qué responden. Da igual cómo hayas llamado a tus métodos.

| Comprobación | Puntos |
| --- | --- |
| La aplicación arranca y no has roto el recurso `Student` | 1 |
| `GET /api/v1/subjects` → `200` con la lista | 1 |
| `POST /api/v1/subjects` → `201` con cabecera `Location` | 2 |
| `GET /api/v1/subjects/{id}` → `200`, y `404` si no existe | 1 |
| `PUT /api/v1/subjects/{id}` guarda el cambio, y `404` si no existe | 1 |
| `DELETE /api/v1/subjects/{id}` → `204`, y `404` si no existe | 1 |
| La lógica está en el service; el controller solo orquesta HTTP | 1 |
| Ampliación: la validación rechaza datos malos con `400` | 1 |
| Ampliación: `GET /api/v1/subjects/search?code=...` filtra | 1 |

Cosas que conviene saber:

- **Cada apartado se prueba por separado.** Si tienes bien el `GET` por id pero no el `POST`,
  cobras el `GET` igual: la corrección no crea recursos con tu `POST` para probar lo demás.
- **`POST` que devuelve `200` cuesta 2 puntos.** Crear es `201` y lleva `Location`:
  `ResponseEntity.created(URI.create(...)).body(creado)`.
- **El service no puede saber de HTTP.** Si aparece un `ResponseEntity` o un `@GetMapping` dentro
  de `SubjectService`, pierdes el punto de arquitectura: eso es trabajo del controller.
- **La corrección se ejecuta sin acceso a internet.** No cambies las versiones del `pom.xml`.

**Para ver qué falla**: pestaña *Actions* → última ejecución.

Puedes volver a subir las veces que quieras: **siempre se conserva tu mejor nota**.

## 💡 Errores comunes
- **Meter lógica en el Controller** → la lógica va en el Service; el Controller solo orquesta HTTP.
- **Olvidar `@Valid`** → no se validan los datos de entrada.
- **`POST` que devuelve `200`** → crear es `201`; usa `ResponseEntity.created(...)`.
- **El puerto 8080 está ocupado** → cambia `server.port` en `application.properties`.
