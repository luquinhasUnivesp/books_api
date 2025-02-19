
# API de Livros 📚
Este repositório contém a implementação de uma API para gerenciamento de livros. A API permite realizar operações básicas como adicionar, listar e buscar livros por ID, autor e genêro.

## 📂 Arquitetura da Solução e Arquitetura Técnica

### 🔹 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot** (Framework principal para desenvolvimento da API)
- **Spring Data JPA** (Para interação com banco de dados)
- **MySQL** (Banco de dados relacional)
- **JUnit 5 & Mockito** (Para testes automatizados)
- **Maven** (Gerenciador de dependências)


### 🔹 Como a API foi implementada
A implementação  segue o padrão **MVC (Model-View-Controller)**, onde:
- **Model**: Representa a entidade `Book` e os DTOs.
- **Controller**: Responsável por receber as requisições HTTP e encaminhá-las para a camada de serviço.
- **Service**: Contém a lógica de negócios e intermedia a comunicação entre o controller e o repository.
- **Repository**: Interface responsável pela comunicação com o banco de dados através do Spring Data JPA.


### 🔹 Estrutura do Projeto
```
books-api/
│-- src/
│   ├── main/
│   │   ├── java/br/com/gesseff/books_api/
│   │   │   ├── controller/  # Controllers da API
│   │   │   ├── service/      # Regras de negócio
│   │   │   ├── repository/   # Interação com o banco de dados 
│   │   │   ├── model/        # Entidades do sistema
│   │   │   ├── dto/          # Objetos de transferência de dados
│   │   ├── resources/
│   │       ├── application.properties  # Configurações da API
│   ├── test/java/br/com/gesseff/books_api/ # Testes unitários
```

## 🚀 Explicação sobre o Case Desenvolvido (Plano de Implementação)


### Endpoints Implementados:

| Método | Rota | Descrição |
|---------|------|------------|
| `POST`  | `/books` | Adiciona um novo livro |
| `GET`   | `/books` | Lista todos os livros |
| `GET`   | `/books/{id}` | Busca um livro pelo ID |
| `GET`   | `/books/genre/{genre}` | Busca livros pelo gênero |
| `GET`   | `/books/author/{author}` | Busca livros pelo autor |


## 💡 Melhorias e Considerações Finais

### 🔹 Melhorias Futuras
- Criar uma interface frontend para consumir a API
- Docker para facilitar a execução da aplicação
- Implementar Cache com Redis 
- Criar testes mais completos, incluindo testes de integração
- Migrar a configuração da aplicação de application.properties para application.yml para melhor organização e leitura.


### 🔹 Desafios Encontrados



## 🛠 Arquitetura Reprodutível

### 📌 Requisitos
- **JDK 17+**
- **Maven 3+**
- **MySQL 8+**
- **Postman ou Insomnia (para testar a API)**


### 🔹 Como Rodar o Projeto

### **1. Clonar o repositório:**
```bash
git clone https://github.com/luquinhasUnivesp/books_api.git
cd books-api
```

### **2. Configurar o Banco de Dados:**
- Criar um banco no MySQL:
```sql
CREATE DATABASE books_api;
```
- Atualizar as configurações do `application.properties` com suas credenciais:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/books_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### **3. Executar a aplicação:**
```bash
mvn spring-boot:run
```

A API estará rodando em `http://localhost:8080`.

## 6. Testes
Os testes unitários podem ser executados com:
```bash
mvn test
```