# 🏋️ FitTrack API

API REST para tracking de treinos, construída com Java e Spring Boot.

## 🛠️ Tecnologias

- ☕ Java 21
- 🍃 Spring Boot 3.3.4
- 🔐 Spring Security + JWT
- 🐘 PostgreSQL 16
- 🚀 Flyway (migrations)
- 🐳 Docker
- 🧪 JUnit 5 + Mockito (testes)

## ▶️ Como rodar localmente

**Pré-requisitos:** Java 21, Docker

```bash
# Sobe o banco de dados
docker-compose up -d

# Roda a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 📡 Endpoints

### 🔑 Auth
| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/register` | Cadastro de usuário |
| POST | `/auth/login` | Login e retorno do JWT |

### 👤 Usuários
| Método | Rota | Descrição |
|---|---|---|
| GET | `/users/me` | Perfil do usuário logado |
| PUT | `/users/me` | Atualiza username |
| PUT | `/users/me/password` | Troca senha |

### 💪 Exercícios
| Método | Rota | Descrição |
|---|---|---|
| POST | `/exercises` | Cadastra exercício |
| GET | `/exercises` | Lista todos |
| GET | `/exercises/{id}` | Busca por ID |
| GET | `/exercises/category/{category}` | Filtra por categoria |
| GET | `/exercises/{id}/history` | Histórico do exercício |

### 📋 Fichas de Treino
| Método | Rota | Descrição |
|---|---|---|
| POST | `/workout-plans/` | Cria ficha |
| GET | `/workout-plans/` | Lista fichas do usuário |
| GET | `/workout-plans/{id}` | Busca ficha |
| PUT | `/workout-plans/{id}` | Atualiza ficha |
| DELETE | `/workout-plans/{id}` | Desativa ficha |

### 🏃 Exercícios na Ficha
| Método | Rota | Descrição |
|---|---|---|
| POST | `/workout-plans/{planId}/exercises/{exerciseId}` | Adiciona exercício |
| DELETE | `/workout-plans/{planId}/exercises/{exerciseId}` | Remove exercício |
| GET | `/workout-plans/{planId}/exercises` | Lista exercícios |

### 📝 Registro de Treino
| Método | Rota | Descrição |
|---|---|---|
| POST | `/workout-plans/{planId}/logs` | Registra sessão de treino |
| GET | `/workout-plans/{planId}/logs` | Histórico de treinos |

## 🔒 Autenticação

Todos os endpoints exceto `/auth/**` exigem o header:

```
Authorization: Bearer {token}
```