# API CRUD – Versão Sem Design Patterns

Este módulo representa a implementação de uma API REST simples do tipo CRUD **sem a aplicação explícita de Design Patterns**, desenvolvida para fins de comparação arquitetural em um estudo acadêmico.

Ele serve como **baseline** para análise dos impactos da utilização (ou não) de padrões de projeto na qualidade do código, manutenção e organização estrutural do sistema.

---

## 🎯 Objetivo deste Módulo

Demonstrar uma abordagem **direta e simplificada** de desenvolvimento de uma API REST, sem a utilização de padrões de projeto clássicos, mantendo:

- Regras de negócio completas
- Validações funcionais
- Persistência de dados
- Funcionamento equivalente à versão com padrões

Essa versão permite evidenciar limitações arquiteturais e estruturais que motivam a aplicação de Design Patterns.

---

## 🧱 Visão Geral da Arquitetura

Nesta versão, a arquitetura do sistema apresenta uma **centralização de responsabilidades**, principalmente na camada de serviço.

Estrutura principal:

- **Controller**
  - Responsável por expor os endpoints REST
- **Service**
  - Concentra regras de negócio
  - Realiza validações de dados
  - Executa operações de persistência
  - Manipula SQL diretamente
- **Banco de Dados**
  - Acesso realizado via `JdbcTemplate`
- **Tratamento de Exceções**
  - Centralizado via `@RestControllerAdvice`

Não há separação explícita de camadas como Repository, Validators ou abstrações intermediárias.

---

## ⚠️ Características Arquiteturais

Principais características observadas nesta versão:

- Concentração de múltiplas responsabilidades em uma única classe de serviço
- Forte acoplamento entre regras de negócio e persistência
- Validações implementadas diretamente na camada de serviço
- Mapeamento manual de dados
- Arquitetura funcional, porém pouco extensível

Essa estrutura caracteriza um **antipadrão do tipo _God Class_**, comum em aplicações de pequeno porte ou em implementações iniciais.

---

## 🧪 Garantia de Equivalência Funcional

Para assegurar uma comparação justa com a versão **com Design Patterns**, este módulo mantém:

- As mesmas regras de negócio
- As mesmas validações (CPF, e-mail, senha, etc.)
- O mesmo domínio de dados
- A mesma base de dados (H2)
- Os mesmos endpoints REST

As diferenças entre as versões são **exclusivamente arquiteturais e estruturais**.

---

## 🛠 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring JDBC
- JdbcTemplate
- Banco de dados H2 (em memória)
- Maven
- JUnit / Spring Test
- JaCoCo

---

## 📚 Contexto Acadêmico

Este módulo faz parte de um estudo comparativo desenvolvido como Trabalho de Conclusão de Curso (TCC), cujo foco é analisar o impacto da aplicação de Design Patterns na qualidade do código, utilizando métricas estáticas e análise estrutural.

Os resultados desta versão são utilizados como base para comparação com a versão arquiteturalmente refinada.

---

## 📌 Observação Importante

Este módulo **não representa uma abordagem recomendada para sistemas de médio ou grande porte**, sendo intencionalmente mantido simples para fins didáticos e experimentais.

---

## 👩‍💻 Autora

**Tuany Aparecida Brito dos Santos**  
MBA em Engenharia de Software  

---

## 📄 Licença

Projeto desenvolvido exclusivamente para fins educacionais e acadêmicos.
