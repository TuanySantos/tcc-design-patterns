# API CRUD – Versão Com Design Patterns

Este módulo contém a implementação de uma API REST do tipo CRUD desenvolvida com a aplicação explícita de **Design Patterns**, com o objetivo de melhorar a organização estrutural, a separação de responsabilidades e a manutenibilidade do código.

Essa versão é funcionalmente equivalente à versão sem padrões, sendo utilizada para fins de **comparação arquitetural e análise de qualidade de código** no contexto de um estudo acadêmico.

---

## 🎯 Objetivo deste Módulo

Demonstrar como a aplicação de **padrões de projeto** contribui para:

- Redução de acoplamento
- Aumento da coesão
- Maior flexibilidade e extensibilidade
- Melhor organização das responsabilidades
- Facilidade de manutenção e testes

Este módulo representa a **versão arquiteturalmente refinada** do sistema.

---

## 🧱 Visão Geral da Arquitetura

A arquitetura do sistema foi reorganizada por meio da introdução de camadas e abstrações, adotando padrões de projeto clássicos.

Principais camadas e componentes:

- **Controller**
  - Exposição dos endpoints REST
- **Service**
  - Orquestração das regras de negócio
  - Coordenação entre validações, persistência e notificações
- **Repository**
  - Abstração da camada de persistência
  - Implementação concreta via JDBC
- **Strategies**
  - Encapsulamento das regras de validação
- **Observers**
  - Reação a eventos do domínio (ex.: criação de usuário)
- **Configuração**
  - Gerenciamento da conexão com o banco de dados

---

## ♻️ Design Patterns Aplicados

### 🔹 Strategy Pattern
Utilizado para encapsular regras de validação independentes:

- Validação de e-mail
- Validação de senha
- Validação de CPF

Cada validação é implementada por uma estratégia específica, permitindo:
- Substituição ou extensão sem modificar o código existente
- Isolamento das regras de validação

---

### 🔹 Repository Pattern
Aplicado para separar a lógica de acesso a dados da lógica de negócio:

- Interface `UserRepository`
- Implementação concreta `JdbcUserRepository`

Benefícios:
- Redução do acoplamento entre service e persistência
- Facilita alterações na forma de armazenamento
- Melhora a testabilidade da camada de serviço

---

### 🔹 Observer Pattern
Utilizado para reagir a eventos relacionados ao usuário:

- Registro de auditoria
- Simulação de envio de notificações

Observers implementados:
- Auditoria de ações
- Notificação por e-mail (simulada)

Esse padrão permite adicionar novos comportamentos sem impactar a lógica principal.

---

### 🔹 Singleton Pattern
Utilizado para garantir uma única instância de configuração da conexão com o banco de dados:

- Centraliza o gerenciamento do `DataSource`
- Evita múltiplas inicializações desnecessárias

---

## 🧪 Garantia de Equivalência Funcional

Para assegurar uma comparação justa com a versão **sem Design Patterns**, este módulo mantém:

- As mesmas regras de negócio
- As mesmas validações funcionais
- Os mesmos endpoints REST
- O mesmo domínio de dados
- A mesma base de dados (H2)

As diferenças entre as versões são **exclusivamente arquiteturais**.

---

## ✅ Benefícios Observados

Com a aplicação dos padrões de projeto, observa‑se:

- Menor concentração de responsabilidades
- Código mais modular e organizado
- Melhor aderência aos princípios SOLID
- Facilidade de evolução do sistema
- Melhor alinhamento com boas práticas de engenharia de software

Esses aspectos são posteriormente validados por meio de métricas estáticas de qualidade.

---

## 🛠 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring JDBC
- Banco de dados H2 (em memória)
- Maven
- JUnit / Spring Test
- JaCoCo

---

## 📚 Contexto Acadêmico

Este módulo faz parte de um Trabalho de Conclusão de Curso (TCC) cujo objetivo é analisar, de forma prática, os impactos da aplicação de Design Patterns na qualidade do código de sistemas orientados a objetos.

Os resultados desta versão são comparados diretamente com a versão sem padrões, utilizando análise estrutural e métricas fornecidas por ferramentas de qualidade.

---

## 👩‍💻 Autora

**Tuany Aparecida Brito dos Santos**  
MBA em Engenharia de Software  

---

## 📄 Licença

Projeto desenvolvido exclusivamente para fins educacionais e acadêmicos.
