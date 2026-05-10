# Görev Yönetimi REST API

Java ve Spring Boot ile geliştirilmiş, tam katmanlı bir Görev Yönetimi REST API projesi.

## Proje Yapısı

```
task-management-api/
├── src/
│   ├── main/java/com/taskmanager/
│   │   ├── TaskManagementApplication.java   ← Giriş noktası
│   │   ├── model/
│   │   │   ├── Task.java                    ← Entity (Katman 1)
│   │   │   └── TaskStatus.java              ← Enum: TODO, IN_PROGRESS, DONE
│   │   ├── repository/
│   │   │   └── TaskRepository.java          ← JpaRepository (Katman 2)
│   │   ├── service/
│   │   │   └── TaskService.java             ← İş Mantığı (Katman 3)
│   │   ├── controller/
│   │   │   └── TaskController.java          ← REST Endpoints (Katman 4)
│   │   ├── config/
│   │   │   └── SecurityConfig.java          ← Spring Security (Katman 5)
│   │   └── exception/
│   │       ├── TaskNotFoundException.java
│   │       ├── DuplicateTaskException.java
│   │       └── GlobalExceptionHandler.java
│   ├── main/resources/
│   │   ├── application.properties           ← H2 (geliştirme)
│   │   └── application-prod.properties      ← PostgreSQL (production)
│   └── test/java/com/taskmanager/
│       └── service/TaskServiceTest.java
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Uç Noktalar (Endpoints)

| Yöntem | URL              | Açıklama               | Auth     |
|--------|------------------|------------------------|----------|
| GET    | /api/tasks       | Tüm görevleri listele  | ✅ Basic |
| GET    | /api/tasks/{id}  | Görev detayı           | ✅ Basic |
| POST   | /api/tasks       | Yeni görev oluştur     | ✅ Basic |
| PUT    | /api/tasks/{id}  | Görevi güncelle        | ✅ Basic |
| DELETE | /api/tasks/{id}  | Görevi sil             | ✅ Basic |

## Kimlik Doğrulama

HTTP Basic Authentication kullanılmaktadır.

| Kullanıcı | Şifre    | Rol   |
|-----------|----------|-------|
| admin     | admin123 | ADMIN |
| user      | user123  | USER  |

## Hızlı Başlangıç

### Geliştirme (H2 veritabanı)
```bash
mvn spring-boot:run
```

### Production (Docker + PostgreSQL)
```bash
mvn clean package -DskipTests
docker-compose up --build
```

### Test
```bash
mvn test
```

## Örnek İstekler

### Görev Oluştur (POST)
```bash
curl -X POST http://localhost:8080/api/tasks \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Sprint Planlaması",
    "description": "Q3 sprint planlamasını hazırla",
    "status": "TODO",
    "dueDate": "2026-06-01"
  }'
```

### Tüm Görevleri Listele (GET)
```bash
curl http://localhost:8080/api/tasks -u admin:admin123
```

### Görev Güncelle (PUT)
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Sprint Planlaması",
    "description": "Tamamlandı",
    "status": "DONE",
    "dueDate": "2026-06-01"
  }'
```

### Görev Sil (DELETE)
```bash
curl -X DELETE http://localhost:8080/api/tasks/1 -u admin:admin123
```

## H2 Konsolu (Geliştirme)

Uygulama çalışırken: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:taskdb`

## Teknoloji Yığını

- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA** (Hibernate)
- **Spring Security** (HTTP Basic)
- **H2** (geliştirme) / **PostgreSQL** (production)
- **Lombok**
- **Docker & Docker Compose**
- **JUnit 5 + Mockito** (test)
