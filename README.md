# Budgeting API

API de controle financeiro pessoal (transações de compras e pagamentos) construída com **Spring Boot** e arquitetura **DDD / Hexagonal**, com autenticação e autorização via **JWT (OAuth2 Resource Server)**.

---

## 1. O que o projeto faz

O Budgeting é uma API REST que permite:

- **Cadastrar usuários** (`POST /users`) com perfil padrão `BASIC`.
- **Autenticar** (`POST /login`) e receber um `token JWT`.
- **Criar transações** (`POST /transactions`) associadas ao usuário autenticado.
- **Listar transações por tipo** (`GET /transactions/{type}`) — `BUYING` (compra) ou `PAYING` (pagamento).
- **Listar usuários** (`GET /users`) — restrito ao perfil `ADMIN`.
- **Transcrever áudio com IA** (`POST /transactions/ai`) — envia o áudio(formato wav) para um modelo via Spring AI + Ollama.

## 2. Como executar a aplicação

### Pré-requisitos

| Ferramenta | Versão |
|---|--------|
| Java | 21+    |
| Docker | 29.9.0 |
| Maven | 3.9.16 |
| Ollama | 0.40.1 |

### Passo a passo

```bash
# 1. Iniciar o docker
sudo systemctl start docker

# 2. Iniciar o ollama
sudo systemctl start ollama
sudo ollama pull gemma4:e2b

# 3. Configurar a chave pública e privada
# Entre na pasta src/main/resources 
# Gere a chave privada
openssl genrsa > app.key
# Gere a chave pública
openssl rsa -in app.key -pubout -out app.pub 

# 3. Suba a aplicação
mvn exec:java -Dexec.mainClass="dio.budgeting.BudgetingApplication"
```
A aplicação sobe em: **http://localhost:8080**.


## 3. Qual melhoria foi implementada

**Implementação completa de autenticação e autorização usando JWT + OAuth2 Resource Server.**

Para isso foi usado como referência o vídeo: https://www.youtube.com/watch?v=nDst-CRKt_k&t=3919s.

A melhoria incluiu:

- **Emissão de token JWT ** no `POST /login`, assinado com as chaves
  `app.key` / `app.pub` presentes no classpath:
  - `issuer`: `backend`
  - `sub`: **id do usuário** (UUID) — usado depois para identificar o dono das transações
  - `scope`: perfis do usuário (`ADMIN` / `BASIC`)
  - expiração: **300 segundos**
- **Resource Server stateless**: todos os endpoints exigem `Authorization: Bearer <token>`,
  exceto `POST /login` e `POST /users` (públicos).
- **Autorização por método**: `GET /users` protegido por
  `@PreAuthorize("hasAuthority('SCOPE_ADMIN')")` — só o perfil ADMIN acessa.
- **Inserção das Roles e do usuário admin**: `data.sql` (`INSERT IGNORE`) + `AdminUserConfig`
  recriam perfis e admin automaticamente a cada subida.
- **Integração com o contexto de transações**: o controller captura o usuário
  autenticado via `JwtAuthenticationToken` e associa cada transação ao dono,
  mantendo o domínio limpo.


---

## 4. Quais tecnologias foram usadas

| Camada | Tecnologia                                                     |
|---|----------------------------------------------------------------|
| Linguagem | Java 21                                                        |
| Framework | Spring Boot 4.1.1                                              |
| Segurança | Spring Security, OAuth2 Resource Server (JWT / Nimbus), BCrypt |
| Persistência | Spring Data JPA, Hibernate, MySQL 9.6                          |
| Infraestrutura local | Docker + Docker Compose                                        |
| IA (experimental) | Spring AI + Ollama + modelo gemma4:e2b                                  |
| Mapeamento/boilerplate | Lombok                                                         |



---

## 5. Como testar o fluxo principal

> Substitua `<TOKEN>` pelo token retornado no login. Pode usar curl, Insomnia, Postman ou Bruno.

### 5.1 Cadastrar um usuário (público)

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"fulano","password":"123"}'
```

### 5.2 Autenticar e obter o token

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123"}' // ja existe o admin, mas pode substituir pelo usuário criado
```

Resposta:

```json
{ "accessToken": "eyJhbGciOi...", "expiresIn": 300 }
```

### 5.3 Listar usuários (requer perfil ADMIN)

```bash
curl http://localhost:8080/users \
  -H "Authorization: Bearer <TOKEN>"
```

- ✅ `200` com a lista (token do admin)
- ❌ `401` sem o header `Authorization` (não autenticado)
- ❌ `403` com token de usuário sem o perfil `ADMIN` (autenticado, mas sem permissão)

### 5.4 Criar uma transação (requer autenticação)

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{"description":"Comprei 80 reais em bolsa","type":"BUYING"}'
```

Resposta `201`:

```json
{
  "id": "8f14e45f-...",
  "description": "Comprei 80 reais em bolsa",
  "type": "BUYING",
  "idUser": "id do usuário que criou a transação" 
}
```

### 5.5 Listar transações por tipo

```bash
curl http://localhost:8080/transactions/BUYING \
  -H "Authorization: Bearer <TOKEN>"
```

### 5.6 Transcrever áudio com IA

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -H "Authorization: Bearer <TOKEN>" \
  -F "file=@gasto.wav;type=audio/wav"
```

Requer o Ollama rodando localmente (`http://localhost:11434`) com o modelo
configurado em `application.properties`.

> ⏱️ O token expira em **5 minutos** — após esse período as chamadas retornam `401`.
> Faça login novamente para obter um novo token.

---

## 6. O que aprendi durante o desafio


- **DDD na prática**: Aprendi a estruturar uma aplicação seguindo os princípios do 
`Domain-Driven Design (DDD)`, compreendendo as responsabilidades das principais camadas: 
`domain`, responsável pelas regras de negócio; `application`, responsável por 
coordenar os casos de uso e o fluxo da aplicação; e `infrastructure`, responsável 
pelas integrações com tecnologias externas, como bancos de dados e APIs.

- **Spring Boot**: Aprendi a utilizar as principais anotações do `Spring Boot` e a 
compreender como funciona o fluxo de execução de uma aplicação.
- **API RESTful**: Aprendi a desenvolver `APIs RESTful` utilizando `Spring Boot`,
- implementando endpoints para receber requisições e retornar respostas HTTP.
 - **Docker**: Aprendi a utilizar o `Docker` para configurar e executar um banco de dados em um contêiner, facilitando a configuração do ambiente de desenvolvimento.
