# @BackEnd - ServiFacil

  API da plataforma ServiFacil contem as regras e lógicas de negócio do projeto final do Bootcamp organizado pela +Prati e \Codifica.

# @Tecnologias / Ferramentas

- Java (jdk 21)
- Maven
- Spring Boot (Framework)
- JWT (Autenticação)
- Bcrypt
- MySQL (Banco de dados utilizado)

# @Arquitetura / Pastas

  O projeto foi pensado e desenvolvido utilizando principalmente a arquitetura MVC (Model - View - Controller) junto de algumas camadas adicionais para organização do software.
  Adicionalmente utilizando Services, Repositories, DTOs, Utils, Exceptions dentre outras camadas.

- src/ main/ java/com/servifacil/SF_BackEnd/ - Pasta principal da aplicação

  ├── config/ (Configurações de segurança e Bean do Java/Spring)
  
  ├── controllers/ (Controladores para receber e gerenciar requisições e respostas)
  
  ├── converters/ (Conversores de dados da aplicação para o banco)
  
  ├── dto/ (Modelos de dados a serem enviados via requisição)
  
  ├── exceptions/ (Lançamento de erros personalizados)
  
  ├── models/ (Modelos das entidades do banco de dados)
  
  ├── repositories/ (Ponte de comunição entre cada model e sua entidade no banco de dados)
  
  ├── responses/ (Respostas customizadas a serem retornadas)
  
  ├── security/ (Configurações de segurança JWT)
  
  ├── services/ (Regras de negocio da aplicação)
  
  ├── utils/ (Utilitários para facilitar tarefas)
  
# @Rotas / End Points

  A aplicação foi hospedada no serviço de hosting Railway, sua URL em produção <https://servifacil-backend-production.up.railway.app>

- Rota base /api


- Dados a serem enviados pelos headers ou params:

  ├── {id} - Refere ao id do usuário em questão.
  
  ├── {serviceId} - Refere ao id do serviço em questão.
  
  ├── {category} - Refere a categoria do serviço para filtragem delimitados no banco.
  
  ├── {appointmentId} - Refere ao id do agendamento em questão.
  
  ├── {apStatus} - Refere ao status do agendamento para filtragem (Pendente, Concluido, Cancelado).
  
  ├── {assessmentId} - Refere ao id da avalição em questão.

  ├── Authorization - Bearer <TOKEN JWT> - Deve ser enviado em rotas privadas via headers

  
- Rotas Usuários - base /users
  
  ├── Cadastro - POST : /register -> ROTA PUBLICA
  
  ├── Login - POST : /login -> ROTA PUBLICA
  
  ├── Busca dados GET : /{id} -> ROTA PRIVADA
  
  ├── Atualização - PATCH : /{id} -> ROTA PRIVADA


- Rotas Serviços - base /services

  ├── Buscar todos GET : /getall -> ROTA PRIVADA
  
  ├── Buscar por categoria GET : /filter/{category} -> ROTA PRIVADA
  
  @As 3 rotas seguintes exigem que o usuário seja do tipo profissional
  
  ├── Criar Serviço POST : /{id} -> ROTA PRIVADA
  
  ├── Atualizar serviço PATCH : /{id}/{serviceId} -> ROTA PRIVADA
  
  ├── Deletar serviço DELET : /{id}/{serviceId} -> ROTA PRIVADA


- Rotas Agendamentos - base /appointments

  ├── Criar agendamento POST : /{id}/{serviceId} -> ROTA PRIVADA
  
  ├── Listar agendamentos por usuário GET : /user/{id}/{apStatus} -> ROTA PRIVADA

  @Esta rota exige que o usuário seja do tipo profissional
  
  ├── Listar agendamentos por serviços GET : /service/{id}/{serviceId}/{apStatus} -> ROTA PRIVADA
  
  ├── Cancelar agendamento PATCH : /cancel/{id}/{appointmentId} -> ROTA PRIVADA
  
  ├── Concluir agendamento PATCH : /conclude/{id}/{appointmentId} -> ROTA PRIVADA


- Rotas Avaliações - base /assessments

  ├── Criar avaliação POST : /{id}/{serviceId} -> ROTA PRIVADA
  
  ├── Listar avaliações por serviço GET : /service/{id}/{serviceId} -> ROTA PRIVADA
  
  ├── Editar avaliação PACTH : /{id}/{serviceId}/{assessmentId} -> ROTA PRIVADA
  
  ├── Apagar avaliação DELETE : /{id}/{serviceId}/{assessmentId} -> ROTA PRIVADA
  

© 2025 Servi_Facil - Todos os Direitos Reservados.
