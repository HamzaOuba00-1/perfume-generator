
# 🌸 Perfuma — Smart Fragrance Composition App

Perfume Generator is a **full-stack** application designed to **automatically generate balanced perfume formulas** based on essential oils selected by the user.

The app relies on **real fragrance composition rules** (olfactory pyramid, 
strength, maximum dosage limits) and produces a **ready-to-use recipe**, along with a **premium PDF sheet** suitable for testing or professional use.

---


## 🎥 Demo Video

[![PARFUMA Demo](https://img.youtube.com/vi/EwiQrWufMAE/0.jpg)](https://www.youtube.com/watch?v=EwiQrWufMAE)

## 🚀 Main Features

### 👤 User

- Guided selection of essential oils  
- Automatic compliance with the olfactory pyramid:

  - top note  
  - heart note  
  - base note  

- Smart percentage calculation  
- Generation of a balanced formula (100%)  
- Volume visualization (5ml → 20ml)  
- Dilution table (Cologne, EDT, EDP, Extrait)  
- Download of a **premium perfume PDF sheet**

---

### 🔐 Administrator

- Secure access via **Basic Authentication**  
- Dedicated Admin interface  
- Full CRUD management of essential oils:

  - name  
  - note type  
  - strength  
  - maximum percentage  
  - image  

- Real image upload support  
- Centralized business data management  

---

## 🧠 Business Logic

The generation algorithm ensures:

- a valid olfactory structure  
- coherent dosages depending on strength  
- a total composition of **100%**  
- a realistic and testable perfume formula  

---

## 📄 PDF Generation

- Backend-side generation
    
- Premium HTML template
    
- Includes:
    
    - logo
        
    - date
        
    - composition
        
    - dilutions
        
- Direct download from the UI


---


## 🏗️ Architecture

### Backend — Spring Boot 3

- Clean architecture (Controller / Service / Repository / DTO)  
- Dedicated generation engine  
- Centralized business rule management  
- Server-side PDF generation (HTML → PDF)  
- Basic Auth security for admin access  
- Swagger API documentation  

---

### Frontend — Angular (Standalone)

- Standalone components  
- Simple state management (service-based)  
- Premium UX-oriented interface  
- Clear separation between pages / components / services  
- HTTP interceptor for admin authentication  
- Route guards for protection  
- Modern & responsive design  

---

## 🔒 Security (Applied Measures)

The project integrates several protections inspired by **OWASP** recommendations:

- Strict backend input validation (`@Valid`, DTO constraints)  
- Data normalization and business control (uniqueness, consistency)  
- Protection against XSS injections:

  - Angular automatically escapes displayed values  
  - HTML encoding in PDF templates (`escapeHtml`)  

- Secure image upload:

  - server-generated filenames (UUID)  
  - file allowlist + existence validation (`oilImageExists`)  

- Defense in depth with Spring Security:

  - protected admin endpoints (`ROLE_ADMIN`)  
  - recommended security headers (`nosniff`, optional CSP)  

---

## 📂 Project Structure

```bash
perfume-generator
├── perfume-generator-backend   # Spring Boot API
└── perfume-generator-frontend  # Angular application
````

---

## 🧪 Technologies Used

### Backend

- Java 17
    
- Spring Boot 3
    
- Spring Security
    
- JPA / Hibernate
    
- H2 
    
- OpenAPI / Swagger
    
- iText / OpenHTMLtoPDF (PDF generation)
    

---

### Frontend

- Angular 16+
    
- Standalone Components
    
- HttpClient
    
- Modern CSS (no UI framework)
    
- SVG icons
    
- HTML → PDF preview compatible
    

---

## ▶️ Running the Project

### Backend

```bash
cd perfume-generator-backend
./mvnw spring-boot:run
```

API available at:

```bash
http://localhost:8080
```

---

### Frontend

```bash
cd perfume-generator-frontend
npm install
ng serve
```

Application available at:

```bash
http://localhost:4200
```

---

## 🔐 Admin Access (Example)

```txt
Username: admin
Password: admin123
```

_(Configurable on the backend side)_

---


## 🐳 Running with Docker (Recommended)

To make the project easy to run on any machine (without installing Java or Node manually), the application can be started using **Docker Compose**.

---

###  Docker Strategy in This Project

The Docker configuration is intentionally different for backend and frontend:

####  Backend (Fully Dockerized)

The Spring Boot API is packaged inside a dedicated Docker image using a **multi-stage Dockerfile**:

- Maven build stage  
- Lightweight Java 17 runtime stage  
- Upload folder persistence via Docker volumes  

This guarantees a stable and reproducible backend environment.

---

####  Frontend (Development Mode Container)

The Angular frontend is currently started in **development mode** using a Node container:

- `ng serve` is executed inside Docker  
- Source code is mounted using volumes  
- Hot reload works without building a production image  

This approach keeps the setup simple.

---

### ▶️ Start Full Stack App

From the project root:

```bash
docker compose up --build
````

This will start:

```bash
- Frontend : http://localhost:4200
- Backend : http://localhost:808
- H2 Console: http://localhost:8080/h2-console 
```
---
### 🛑 Stop the Containers

```bash
docker compose down
```


---
## ✅ Testing & Continuous Integration

Testing is currently implemented on the **backend side**, following professional Spring Boot standards.

The backend includes a complete testing setup using:

- **JUnit 5**
- **Mockito**
- Spring Boot integration testing

Tests cover:

- business logic (generation engine)  
- service layer validation  
- controller integration endpoints  
- admin CRUD operations  

---

### ▶️ Run Backend Tests Locally

```bash
cd perfume-generator-backend
./mvnw test
````

---

### 🤖 GitHub Actions CI Pipeline (Backend)

Every push or pull request automatically triggers the backend CI workflow:

- Maven build
    
- Unit + integration tests
    
- Java 17 environment consistency
    

Workflow file:

```
.github/workflows/ci.yml
```

---
## 🎓 Academic Purpose

This project follows:

- **clean architecture** principles
    
- a clear frontend/backend separation
    
- an explainable business logic approach
    
- Docker
---
## 📌 Coming Soon

- JWT Authentication (optional)
    
- Recipe history
    
- Multi-format export
    
- Advanced customization
    
- Mobile version

---
## 🧑‍💻 Author

Developed by **Hamza Ouba**  

---





