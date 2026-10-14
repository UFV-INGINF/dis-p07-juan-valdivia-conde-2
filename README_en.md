# P7 · Spring Boot: layered architecture and REST CRUD

> Individual assignment · Session 7 · Designed for class (get as far as you can)

## 🎯 Goal

Implement a REST API with **Spring Boot** and a **layered architecture** (Controller → Service → Repository → Model). The `Student` resource is provided **complete as a reference example**; your job is to implement the `Subject` resource mirroring that same pattern.

## 📚 What you'll practise
- The four layers and dependency injection
- REST endpoints with `@RestController`, `@GetMapping`, `@PostMapping`…
- Input validation with `@Valid` and Jakarta Bean Validation
- Correct status codes (`200`, `201`, `204`, `404`)

> 📎 Supporting theory: **Session 7** notes.

---

## 📋 Before you start

```bash
git clone https://github.com/UFV-INGINF/dis-p07-YOURUSERNAME.git
cd dis-p07-YOURUSERNAME
mvn spring-boot:run        # starts on http://localhost:8080
```

Check the example works (with Thunder Client, Postman or `curl`):
```bash
curl http://localhost:8080/api/v1/students
curl -X POST http://localhost:8080/api/v1/students \
     -H "Content-Type: application/json" \
     -d '{"name":"Ada Lovelace","email":"ada@ufv.es","grade":9.2,"tags":["java"]}'
```

## 📂 Repository structure

```
dis-p07-YOURUSERNAME/
└─ src/main/java/com/ufv/dis/
   ├─ DisApplication.java                  startup
   ├─ model/Student.java                   ✅ complete example
   ├─ model/Subject.java                   ✅ model given
   ├─ repository/StudentRepository.java    ✅ complete example
   ├─ repository/SubjectRepository.java    ✅ given (JSON persistence)
   ├─ service/StudentService.java          ✅ complete example
   ├─ service/SubjectService.java          ← YOU implement it
   ├─ controller/StudentController.java    ✅ complete example
   ├─ controller/SubjectController.java    ← YOU implement it
   └─ exception/                           ✅ error handling (404 / 400)
```

> The **Student** resource is finished: use it as a template. The **Subject** resource is your task.

---

## 🛠 Task

### Part 1 — Core: implement the `Subject` CRUD

Mirroring the `Student` branch:

1. **`SubjectService`**: implement `getAll`, `getById`, `create`, `update`, `delete` (currently stubs). When something doesn't exist, throw `NotFoundException`.
2. **`SubjectController`**: add the five REST endpoints:
   - `GET /api/v1/subjects` → `200`
   - `GET /api/v1/subjects/{id}` → `200` or `404`
   - `POST /api/v1/subjects` → `201` with `Location` header
   - `PUT /api/v1/subjects/{id}` → `200` or `404`
   - `DELETE /api/v1/subjects/{id}` → `204` or `404`
   - Validate the body with `@Valid`.

Test each endpoint and check the status codes.

### Part 2 — Extension (if you have time)

3. Check that **validation** works: create a `Subject` with `credits` out of range or without `code` and verify it returns `400` with the error body.
4. Add an extra endpoint: `GET /api/v1/subjects/search?code=...` that filters by code.

---

## ✅ Submission

```bash
git add .
git commit -m "P7: Subject CRUD implemented"
git push origin main      # or 'master'
```

The `Subject` resource must work with its service and controller, with the correct status codes. Don't push `target/` or `data/`.

---

## ✅ How it is graded

Pushing triggers the automatic grader. **Your code is not read: your actual endpoints are called**
and the responses are checked. It does not matter what you named your methods.

| Check | Points |
| --- | --- |
| The application starts and you did not break the `Student` resource | 1 |
| `GET /api/v1/subjects` → `200` with the list | 1 |
| `POST /api/v1/subjects` → `201` with a `Location` header | 2 |
| `GET /api/v1/subjects/{id}` → `200`, and `404` if it does not exist | 1 |
| `PUT /api/v1/subjects/{id}` saves the change, and `404` if it does not exist | 1 |
| `DELETE /api/v1/subjects/{id}` → `204`, and `404` if it does not exist | 1 |
| The logic lives in the service; the controller only orchestrates HTTP | 1 |
| Extension: validation rejects bad data with `400` | 1 |
| Extension: `GET /api/v1/subjects/search?code=...` filters | 1 |

Worth knowing:

- **Each item is tested independently.** If your `GET` by id works but your `POST` does not, you
  still get the `GET`: the grader never uses your `POST` to set up the other checks.
- **A `POST` returning `200` costs you 2 points.** Creating is `201` and carries `Location`:
  `ResponseEntity.created(URI.create(...)).body(created)`.
- **The service must not know about HTTP.** A `ResponseEntity` or a `@GetMapping` inside
  `SubjectService` costs you the architecture point: that is the controller's job.
- **Grading runs with no internet access.** Do not change the versions in `pom.xml`.

**To see what failed**: *Actions* tab → latest run.

You can push as many times as you like: **your best mark is always kept**.

## 💡 Common mistakes
- **Putting logic in the Controller** → logic goes in the Service; the Controller only orchestrates HTTP.
- **Forgetting `@Valid`** → input data isn't validated.
- **`POST` returning `200`** → creating is `201`; use `ResponseEntity.created(...)`.
- **Port 8080 already in use** → change `server.port` in `application.properties`.
