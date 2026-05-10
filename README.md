# TCC – Análise do Uso de Design Patterns em uma API CRUD com Spring Boot

Este repositório contém os projetos desenvolvidos como parte do Trabalho de Conclusão de Curso (TCC), cujo objetivo é **analisar o impacto da aplicação de padrões de projeto (Design Patterns) na qualidade do código**, utilizando uma API REST simples do tipo CRUD como estudo de caso.

O trabalho compara duas implementações funcionalmente equivalentes:
- uma **sem aplicação de padrões de projeto**
- outra **com aplicação de padrões de projeto**

---

## 🎯 Objetivo do Projeto

Avaliar como a aplicação de **Design Patterns** influencia atributos de qualidade de software, tais como:

- Manutenibilidade
- Organização estrutural
- Separação de responsabilidades
- Complexidade do código
- Facilidade de testes
- Métricas estáticas de qualidade (via SonarQube)

---

## 🧩 Estrutura do Repositório

O repositório está organizado da seguinte forma:

## 🌿 Organização por Branches

As duas versões do sistema estão separadas por **branches** neste repositório:

- **branch `without-patterns`**  
  Contém a implementação da API **sem aplicação de Design Patterns**, utilizada como baseline para comparação.

- **branch `with-patterns`**  
  Contém a implementação da mesma API **com aplicação de Design Patterns**, mantendo equivalência funcional com a versão sem padrões.

A separação por branches foi adotada para facilitar a análise comparativa entre as abordagens, permitindo isolar claramente as diferenças arquiteturais sem duplicação desnecessária de código.

Cada versão representa uma abordagem arquitetural distinta, mantendo:
- Mesma regra de negócio
- Mesmo domínio
- Mesmas validações funcionais
- Mesma base de dados

---

## 🧪 Descrição dos Módulos

### 🔹 Module Without Patterns

Neste módulo, a aplicação foi desenvolvida **sem o uso explícito de padrões de projeto**, seguindo uma abordagem mais direta e simplificada.

Características principais:
- Lógica de negócio, validação e persistência concentradas na camada de serviço
- Acesso ao banco de dados realizado diretamente via `JdbcTemplate`
- Forte acoplamento entre responsabilidades
- Estrutura propositalmente simples para evidenciar limitações arquiteturais

Este módulo serve como **baseline** para comparação.

---

### 🔹 Module With Patterns

Neste módulo, a mesma API foi reimplementada aplicando **padrões de projeto e boas práticas de design orientado a objetos**.

Características principais:
- Separação clara de responsabilidades
- Uso de camadas dedicadas (Controller, Service, Repository, Validators, etc.)
- Aplicação de Design Patterns (ex.: Repository, Strategy, Factory, Facade)
- Estrutura voltada à extensibilidade, manutenção e testabilidade

Este módulo representa a **versão refatorada e orientada a padrões** do sistema.

---

## 📊 Metodologia de Avaliação

A comparação entre os módulos é realizada a partir de:

- Análise estrutural do código
- Identificação de antipadrões
- Métricas estáticas de qualidade
  - Complexidade ciclomática
  - Code Smells
  - Cobertura de testes
- Ferramenta de apoio: **SonarQube**

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
- SonarQube

---

## 📚 Contexto Acadêmico

Este projeto foi desenvolvido como parte do Trabalho de Conclusão de Curso do **MBA em Engenharia de Software**, tendo como foco o estudo prático de **Design Patterns aplicados a sistemas reais**, com ênfase em qualidade de código e arquitetura de software.

---

## 📌 Observações Importantes

- Ambos os módulos são funcionalmente equivalentes.
- As validações e regras de negócio foram mantidas iguais, garantindo comparação justa.
- Diferenças observadas nos resultados estão diretamente relacionadas à abordagem arquitetural adotada.

---

## 👩‍💻 Autora

**Tuany Aparecida Brito dos Santos**  
MBA em Engenharia de Software  

---

## 📄 Licença

Projeto desenvolvido para fins educacionais e acadêmicos.

