# escola-curso-api

API REST para gestão escolar com Alunos, Professores e Cursos.

## Stack
- Java 17 + Spring Boot 3.4.5
- Spring Security (Basic Auth)
- Spring Data JPA + Hibernate
- PostgreSQL 16 (Docker) / H2 (local)
- Swagger/OpenAPI 3 via springdoc

## Como rodar

### Com Docker (recomendado)
```bash
docker compose up --build
```
- API: http://localhost:8080/swagger-ui.html
- pgAdmin: http://localhost:5051 (admin@admin.com / admin)

### Sem Docker (perfil H2)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```
- API: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

## Autenticação
Basic Auth: **admin / 123456**  
No Swagger, clique em "Authorize" e informe as credenciais.

## Endpoints

### Alunos `/api/alunos`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | /api/alunos | Listar todos |
| GET | /api/alunos/{id} | Buscar por ID |
| POST | /api/alunos | Cadastrar aluno |
| PUT | /api/alunos/{id} | Atualizar aluno |
| PATCH | /api/alunos/{id}/nota | **Aplicar nota** (situação calculada automaticamente) |
| DELETE | /api/alunos/{id} | Deletar aluno |

### Professores `/api/professores`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | /api/professores | Listar todos |
| GET | /api/professores/{id} | Buscar por ID |
| POST | /api/professores | Cadastrar professor |
| PUT | /api/professores/{id} | Atualizar professor |
| DELETE | /api/professores/{id} | Deletar professor |

### Cursos `/api/cursos`
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | /api/cursos | Listar todos |
| GET | /api/cursos/{id} | Buscar por ID (com lista de alunos) |
| POST | /api/cursos | Criar curso (requer professorId) |
| PUT | /api/cursos/{id} | Atualizar curso |
| POST | /api/cursos/{cursoId}/alunos/{alunoId} | **Matricular aluno** |
| DELETE | /api/cursos/{cursoId}/alunos/{alunoId} | **Desmatricular aluno** |
| DELETE | /api/cursos/{id} | Deletar curso |

## Regras de negócio
- Nota >= 7.0 → situação `APROVADO`; < 7.0 → `REPROVADO`; sem nota → `EM_ANDAMENTO`
- E-mail e RA do aluno são únicos
- E-mail do professor é único
- Um curso tem exatamente 1 professor (OneToOne)
- Um curso pode ter N alunos (ManyToMany via tabela `curso_alunos`)

## Estrutura de camadas
```
config/       → SecurityConfig, OpenApiConfig
controller/   → REST + ApiExceptionHandler
service/      → Regras de negócio, orquestração
repository/   → Spring Data JPA
domain/       → Entidades JPA + Enums
  vo/         → Value Objects (NomeAluno, EmailAluno, RaAluno, ...)
dto/          → Request e Response DTOs
```
