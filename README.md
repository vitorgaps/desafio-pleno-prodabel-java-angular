# Sistema de Gestão de Atendimentos Municipais

Este projeto é uma API RESTful para um sistema de gestão de atendimentos municipais. A aplicação permite que cidadãos abram solicitações de serviço, que essas solicitações sejam atribuídas a funcionários da prefeitura e que métricas de atendimento sejam visualizadas.

Este backend foi desenvolvido como parte de um desafio técnico, utilizando Java, Spring Boot e boas práticas de arquitetura de software.

## Pré-requisitos

São necessárias as seguintes ferramentas para a execução do projeto:
-   [Java JDK 17 ou superior](https://www.oracle.com/java/technologies/downloads/)
-   [Apache Maven](https://maven.apache.org/download.cgi)
-   [Docker e Docker Compose](https://www.docker.com/products/docker-desktop/)
-   Um cliente de API, como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/), para testar os endpoints.

## Instalação e Execução

Siga os passos abaixo para executar a aplicação localmente.

**1. Inicie o banco de dados com Docker:**
Na raiz do projeto, execute o comando:

```bash
docker-compose up -d
```
Ele irá baixar a imagem do PostgreSQL e iniciar o container em segundo plano.
O banco de dados estará acessível em `localhost:5432`.

**2. Execute a aplicação Spring Boot:**
A aplicação pode ser iniciada de duas formas:

* **Via IDE:**:
    Abra o projeto na sua IDE e execute a classe principal `GestaoAtendimentosApplication.java`.

* **Via Wrapper do Maven:**:
Navegue até a pasta raiz do projeto. Conceda permissões de execução para o script executável mvnw (Maven Wrapper) com o comando:
    ```bash
	chmod +x mvnw
    ```
    Em seguida, execute-o com o comando:
    ```bash
	./mvnw spring-boot:run
    ```

A API estará disponível em `http://localhost:8080`.

## Como Rodar os Testes

Para executar os testes unitários e de integração do projeto, utilize o seguinte comando Maven na raiz do projeto:

```bash
./mvnw test
```

## Documentação da API

A seguir, os principais endpoints da API.

---
### Cidadãos (`/api/citizens`)

| Método | Rota               | Descrição                 | Exemplo de Corpo (Request)                               |
| :----- | :----------------- | :------------------------ | :------------------------------------------------------- |
| `POST` | `/api/citizens`      | Cria um novo cidadão.     | `{"name": "Ana Silva", "cpf": "11122233344"}`             |
| `GET`  | `/api/citizens`      | Lista todos os cidadãos.  | -                                                        |
| `GET`  | `/api/citizens/{id}` | Busca um cidadão por ID.  | -                                                        |
| `PUT`  | `/api/citizens/{id}` | Atualiza um cidadão.      | `{"name": "Ana Silva Souza", "cpf": "11122233344"}`       |
| `DELETE`|`/api/citizens/{id}`| Deleta um cidadão.        | -                                                        |

---
### Funcionários (`/api/employees`)

| Método | Rota                | Descrição                   | Exemplo de Corpo (Request)                                 |
| :----- | :------------------ | :-------------------------- | :--------------------------------------------------------- |
| `POST` | `/api/employees`      | Cria um novo funcionário.   | `{"name": "João Lima", "registrationNumber": "98765"}`    |
| `GET`  | `/api/employees`      | Lista todos os funcionários.| -                                                          |
| `GET`  | `/api/employees/{id}` | Busca um funcionário por ID.| -                                                          |
| `PUT`  | `/api/employees/{id}` | Atualiza um funcionário.    | `{"name": "João Lima Costa", "registrationNumber": "98765"}`|
| `DELETE`|`/api/employees/{id}`| Deleta um funcionário.      | -                                                          |

---
### Solicitações de Serviço (`/api/service-requests`)

| Método | Rota                                  | Descrição                                         | Exemplo de Corpo (Request)                                                                            |
| :----- | :------------------------------------ | :------------------------------------------------ | :---------------------------------------------------------------------------------------------------- |
| `POST` | `/api/service-requests`                 | Cria uma nova solicitação.                      | `{"title": "Lâmpada queimada", "description": "Poste na Rua A, 123", "neighborhood": "Centro", "citizenId": 1}` |
| `GET`  | `/api/service-requests`                 | Lista todas as solicitações.                    | -                                                                                                     |
| `GET`  | `/api/service-requests/{id}`            | Busca uma solicitação por ID.                   | -                                                                                                     |
| `PATCH`| `/api/service-requests/{id}/assignment` | Atribui uma solicitação a um funcionário.       | `{"employeeId": 1}`                                                                                   |
| `PATCH`| `/api/service-requests/{id}/status`     | Atualiza o status de uma solicitação.           | `{"status": "CLOSED"}`                                                                                |

---
### Métricas (`/api/metricas`)

| Método | Rota                                    | Descrição                                 | Exemplo de Corpo (Response)                                    |
| :----- | :-------------------------------------- | :---------------------------------------- | :------------------------------------------------------------- |
| `GET`  | `/api/metricas/atendimentos-por-bairro` | Retorna o total de solicitações por bairro. | `[{"neighborhood": "Centro", "requestCount": 15}, ...]` |
