# Challenge 4 - Projeto Clyvo

Sistema de gestão da operação de clínicas e veterinários.

Nesta Sprint, a aplicação é publicada no **Azure Web App** por uma pipeline de
**CI/CD no Azure DevOps**, com banco **Oracle em nuvem**.

- **Aplicação no ar:** https://sprint4-clyvo-rm565698.azurewebsites.net
- **Link Vídeo:** [https://www.youtube.com/watch?v=MfKprPYroL0](https://www.youtube.com/watch?v=MfKprPYroL0)
- **Repositório de DevOps** (script de infraestrutura, YAML, arquitetura e How-To): [devops-sprint4-clyvo](https://github.com/ClyvoVet-Challenge-org/devops-sprint4-clyvo)

## Integrantes

* Eduardo Batista Locaspi - RM561713 - 2TDSPI
* Victor Alves Lopes - RM561833 - 2TDSPI
* Liana Lyumi Morisita Fujisima - RM565698 - 2TDSPI
* Letícia Santiago e Silva - RM565799 - 2TDSPW

## Desafio apresentado (e solução)

A falta de recorrência de responsáveis em consultas de check-up pode acabar agravando casos de determinadas doenças. Para ser mais preciso, os responsáveis podem ter dificuldades para analisar o contexto quando não possuem dados para se basear.

Nossa plataforma possui uma base de dados unificada que permite a gestão das atividades tanto da clínica quanto do veterinário, reunindo em um só lugar os tutores, os animais, as consultas, as vacinas e o histórico de cada paciente.

Dessa forma, o veterinário acompanha seus pacientes de forma contínua e a clínica gerencia sua equipe, o que ajuda a melhorar os serviços e a personalizar os avisos e lembretes enviados aos responsáveis.

## Funcionalidades

Esse é um projeto pensado para clínicas e veterinários associados à empresa Clyvo. O sistema permite:

* Autenticação de Clínicas;
* Autenticação de Veterinários;
* Cadastro de nova Clínica e de novo Veterinário;
* Página inicial do Veterinário com indicadores (tutores atendidos, pacientes, consultas e próximas consultas), lista de tutores com seus animais e próximas consultas;
* **CRUD de Animal:** cadastrar, consultar (ficha do animal), editar e excluir;
* Visualização e edição dos dados do Responsável (tutor) e lista dos seus animais;
* Edição dos dados de perfil da Clínica logada;
* Edição dos dados de perfil do Veterinário logado;
* Visualização dos veterinários contratados pela Clínica logada;
* Associação de um novo veterinário a uma Clínica logada;
* Visualização das consultas do Veterinário logado.

**Regras de negócio:**

* O veterinário só acessa os tutores e animais que já atendeu em alguma consulta;
* Animais que já possuem consultas não podem ser excluídos, para preservar o histórico clínico;
* As senhas dos usuários são armazenadas com hash BCrypt.

## Dependências e Tecnologias Utilizadas

* Java 21
* Spring Boot 4
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* Validation
* Flyway
* Thymeleaf
* Bootstrap
* Oracle JDBC Driver (ojdbc11)
* JUnit 5 + Mockito
* Maven

## Configuração do Banco de Dados

O projeto utiliza o **Oracle** (servidor da FIAP). Não é necessário criar tabelas manualmente: ao iniciar a aplicação, o **Flyway** cria as tabelas (prefixo `T_CLYVOJ_`) e insere os dados de exemplo.

1. Crie um arquivo `.env` na raiz do projeto (mesmo nível do `pom.xml`), com o seguinte conteúdo:

```
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL
SPRING_DATASOURCE_USERNAME=SEU_RM
SPRING_DATASOURCE_PASSWORD=SUA_SENHA
```

Substitua:

* `SEU_RM` pelo seu usuário do Oracle da FIAP (ex.: rm000000);
* `SUA_SENHA` pela sua senha do Oracle da FIAP.

O arquivo `.env` está no `.gitignore`, então a senha não vai para o repositório. No Azure, essas mesmas variáveis são gravadas no Web App pela pipeline.

2. Rode a aplicação:

```
mvn spring-boot:run
```

3. Acesse `http://localhost:8080` e entre com um dos logins de teste:

| Perfil | Login | Senha |
|---|---|---|
| Veterinária | `camila@email.com` | `senha123` |
| Clínica | `contato@amigofiel.com` | `senha123` |

## Testes Automatizados

São **20 testes JUnit**, executados com:

```
mvn test
```

* `ClyvoChallenge3ApplicationTests` → a aplicação sobe por completo, conectando no Oracle;
* `AnimalValidacaoTest` → validações do Animal (nome, nascimento, peso, responsável);
* `MedicamentoValidacaoTest` → validações do Medicamento (dose, dias de tratamento, forma farmacêutica);
* `VeterinarioValidacaoTest` → formato do CRMV e do e-mail;
* `AcessoServiceTest` → regra de acesso do veterinário aos tutores e animais.

Na pipeline do Azure DevOps, esses testes rodam no stage de CI a cada commit na branch `main`.

## Arquitetura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/example/clyvo_challenge3/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   │
│   └── resources/
│       ├── db/migration/
│       ├── templates/
│       └── application.properties
│
└── test/
    └── java/
        └── com/example/clyvo_challenge3/
            ├── model/
            └── service/
```

* config/ → configurações do Spring Security.
* controller/ → recebe as requisições e controla as páginas.
* dto/ → objetos usados nos formulários de edição.
* model/ → entidades do sistema.
* repository/ → acesso aos dados do banco.
* service/ → regras de negócio (acesso do veterinário) e autenticação.
* db/migration/ → scripts do Flyway (V1 tabelas, V2 constraints, V3 e V4 dados de exemplo).
* templates/ → páginas HTML utilizando Thymeleaf e Bootstrap.
* application.properties → configurações da aplicação e do banco de dados.
* test/ → testes automatizados (JUnit e Mockito).
