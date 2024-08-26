# Task Manager API 📝

**Task Manager API** é uma aplicação RESTful desenvolvida para gerenciar tarefas e listas de tarefas. Com ela, é possível criar, visualizar, atualizar e deletar tarefas e listas, permitindo que os usuários mantenham suas atividades organizadas de maneira eficiente.

## 🚀 Funcionalidades

- **Listas de Tarefas**:
  - Criar novas listas de tarefas.
  - Visualizar todas as listas e suas tarefas associadas.
  - Editar e excluir listas de tarefas.

- **Tarefas**:
  - Adicionar tarefas a listas específicas.
  - Atualizar o status de uma tarefa (ex.: pendente, em progresso, concluída).
  - Excluir tarefas.

## 🛠️ Tecnologias Utilizadas

- **Java** com **Spring Boot** - Framework robusto para criação de APIs RESTful.
- **Hibernate/JPA** - Mapeamento objeto-relacional (ORM) para acesso ao banco de dados.
- **PostgreSQL** - Banco de dados relacional utilizado para persistência de dados.
- **Docker** - Gerenciamento de ambientes isolados.
- **JUnit** - Testes automatizados para garantir a qualidade do código.
- **Mockito** - Framework para criação de mocks em testes unitários.

## 🗂️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/taskmanager/
│   │   ├── config/       # Classes de configurações
│   │   ├── controller/   # Controladores RESTful
│   │   ├── enums/        # Classes enum
│   │   ├── exception/    # Classes de gerenciamento de excessões
│   │   ├── model/        # Entidades JPA (Task, TaskList)
│   │   ├── pattern/      # Classes padronizadoras do sistema
│   │   ├── repository/   # Repositórios para acesso ao banco de dados
│   │   ├── service/      # Lógica de negócios
|   |   |── utils/        # Classes úteis 
|   |   |── validator     # Classes validadoras 
│   └── resources/
│       └── application.properties  # Configurações de aplicação (DB, etc)
└── test/                 # Testes unitários e de integração
```
🔥 Instalação e Execução
Pré-requisitos
Java 22
Maven
Docker (para rodar o banco de dados PostgreSQL)

📝 Endpoints Principais

Listas de Tarefas
GET /task-list: Retorna todas as listas de tarefas.
POST /task-list: Cria uma nova lista de tarefas.
PUT /task-list/{id}: Atualiza uma lista de tarefas existente.
DELETE /task-list/{id}: Deleta uma lista de tarefas.
PUT /task-list/{id}/finish: Finaliza a lista de tarefas assim como todas as tarefas incluídas nessa lista.

Tarefas
GET /task: Retorna todas as tarefas.
POST /task: Cria uma nova tarefa em uma lista.
PUT /task/{id}: Atualiza uma tarefa existente.
DELETE /task/{id}: Deleta uma tarefa.
PUT /task/{id}/finish: Finaliza a tarefa com o id passado na requisição.

🌟 Filtro Genérico para Controllers
A API utiliza um filtro genérico aplicado a todas as classes que estendem a AbstractEntity. Isso significa que qualquer atributo presente nas entidades que herdam dessa classe e tenha uma controller que herde a classe AbstractController
pode ser usado como parâmetro de filtro em requisições HTTP.

Por exemplo, para listar as tarefas com o status IN_EXECUTION e prioridade AVERAGE, ordenadas por prioridade em ordem decrescente, basta fazer uma requisição como estas:

GET http://localhost:8081/task-list/15/task/list?status=IN_EXECUTION&priority=AVERAGE&orderBy=priority&sort=desc

Outros exemplos de utilização do filtro dinâmico:

GET http://localhost:8081/task-list/15/task/list?status=IN_EXECUTION&priority=AVERAGE

GET http://localhost:8081/task-list/15/task/list?status=CREATED&priority=VERY_HIGH

Esse mecanismo de filtragem é altamente flexível e permite combinar múltiplos parâmetros sem a necessidade de métodos específicos para cada tipo de consulta. Qualquer atributo de uma entidade que herde de AbstractEntity
pode ser utilizado no filtro da classe AbstractController simplesmente passando o nome do atributo como parâmetro na URL.

Caso não sejam passados parâmetros na requisição, por padrão o filtro ordena por id decrescente, começando pelo último registro criado.

Parâmetros:
orderBy: Campo pelo qual os resultados serão ordenados.
status: Filtro para status da tarefa.
priority: Filtro para a prioridade da tarefa.
sort: Direção da ordenação (asc ou desc).
Esse padrão simplifica a implementação de filtros complexos sem a necessidade de criar endpoints específicos, tornando o sistema mais eficiente e fácil de manter.

Os testes cobrem as operações principais da API, garantindo o correto funcionamento dos endpoints e validações.

🏗️ Próximos Passos
Autenticação e Autorização: Implementar OAuth2 com JWT para proteger os endpoints.
Notificações: Adicionar sistema de notificações para lembrar os usuários de tarefas pendentes.
Melhorias na Interface Web: Desenvolver um frontend utilizando React ou Angular.
