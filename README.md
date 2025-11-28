# ⚙️ STOCKTOOL - BACKEND (API RESTful) 🚀

**Sistema Distribuído de Empréstimo de Ferramentas e Gestão de Estoque**

O Backend é o **Servidor central** que contém a lógica de negócio, controla as transações de estoque e gerencia a persistência dos dados via API RESTful.

---

### Integrantes:

| Nome | RA | GitHub |
| :--- | :--- | :--- |
| KAUE SANTANA DE OLIVEIRA | 10725116177 | @kaue-santana|
| MARIA EDUARDA SOUZA DOS SANTOS FERREIRA | 10724111943 | @dudahmesmo |
| MILLENA FERREIRA RODRIGUES | 10724112348 | @Miaunisul |

---

### 🛠️ Arquitetura e Tecnologias

* **📌 Arquitetura:** Cliente-Servidor (API RESTful)
* **Tecnologia Central:** Spring Boot 
* **Linguagem & Plataforma:** Java Development Kit (JDK) 21
* **Persistência:** Spring Data JPA (Hibernate)
* **Banco de Dados:** MySQL
* **Gerenciador de Dependências:** Apache Maven
* **IDE Recomendada:** **Visual Studio Code (VS Code)**  para desenvolvimento e depuração do Spring Boot.

---

### ✨ Funcionalidades Implementadas (Lógica de Negócio)

O Backend centraliza e gerencia todas as regras de negócio:

1. **Gestão de Ferramentas e Estoque**:
    - Cadastro de Ferramentas com parâmetros de **Quantidade Mínima** e **Máxima** de Estoque.
    - Cadastro de **Categorias**.
    - Cálculo do **Custo Total de Aquisição** do Estoque.
2. **Controle de Empréstimo Transacional**:
    - Registro de Empréstimo: Decrementa automaticamente a quantidade em estoque via transação[cite: 60].
    - Registro de Devolução: Incrementa automaticamente a quantidade em estoque via transação[cite: 62].
    - **Data de Devolução:** Calculada automaticamente para **7 dias após o empréstimo**.
3. **Alertas e Relatórios Gerenciais**:
    - Relatório de **Ferramentas Abaixo ou Igual ao Estoque Mínimo** (alerta de reposição).
    - **Relatório de Devedores:** Lista colaboradores com data de devolução ultrapassada.
    - **Notificação de Atraso** ao tentar registrar novo empréstimo.

---

## 🚀 Guia de Configuração e Execução

### ⚠️ Ordem de Execução

**É obrigatório iniciar o Backend antes do Frontend!**

### 1. Clonagem dos Repositórios

Você precisa clonar os dois repositórios (Backend e Frontend):

```bash
git clone [https://github.com/dudahmesmo/gestao-estoque-backend.git](https://github.com/dudahmesmo/gestao-estoque-backend.git)
git clone [https://github.com/dudahmesmo/gestao-estoque-frontend.git](https://github.com/dudahmesmo/gestao-estoque-frontend.git)
```
### 2. Configurações do Banco de Dados

O sistema utiliza o **MySQL**. Configure o banco de dados com as seguintes credenciais:

| Configuração | Valor |
| :--- | :--- |
| Nome do Banco | `gestao_estoque` |
| Usuário | `root` |
| Senha | `DataBaseA3` |

Confirme as credenciais no arquivo `src/main/resources/application.properties` do Backend.

### 3. Execução do Backend (VS Code)

1.  Abra o projeto `gestao-estoque-backend` no **Visual Studio Code**.
2.  Compile o projeto com Maven: `mvn clean install`
3.  Execute a classe principal: **`GestaoEstoqueBackendApplication.java`**. (Geralmente, clicando em 'Run' ou usando o plugin Spring Boot do VS Code).

A API estará rodando em `http://localhost:8080`.

---

## ✅ Requisitos Funcionais (RFs)

* **RF01** - Cadastro de Ferramentas com dados de estoque (nome, marca, custo, Categoria, Qtd. em Estoque, Qtd. Mínima e Qtd. Máxima).
* **RF02** - Cadastro de Colaboradores (Amigos) com nome e telefone.
* **RF03 / RF04** - Registro de empréstimos e devoluções com controle transacional de estoque.
* **RF05** - Relatório de ferramentas com o Custo Total do Estoque.
* **RF05.01** - Relatório de empréstimos ativos.
* **RF05.02** - Relatório de histórico de empréstimos e identificação da ferramenta mais emprestada/devolvida.
* **RF05.03** - Relatório de Ferramentas Abaixo ou Igual ao Estoque Mínimo.
* **RF05.05** - Relatório de Devedores.
* **RF06** - Notificação se um colaborador tem ferramentas em atraso ao realizar um novo empréstimo.
* **RF07** - Exclusão de ferramentas e colaboradores.
* **RF08** - Geração automática da data de devolução (7 dias).
* **RF09** - Cadastro de Categorias.

---

## 🔒 Requisitos Não Funcionais (RNFs)

* **RNF01** - O sistema deve ser acessível a partir de um cliente desktop (Java Swing) que se comunica com o Servidor (Spring Boot).
* **RNF02** - Garantia de integridade transacional dos dados no Servidor.
* **RNF04** - Bom desempenho no processamento das requisições RESTful.
* **RNF09** - Utiliza MySQL como SGBD relacional primário.

---

## 🔗 Repositórios e Licença

**Repositório do Frontend:** [gestao-estoque-frontend](https://github.com/dudahmesmo/gestao-estoque-frontend)

Este projeto está licenciado sob a [licença](https://github.com/dudahmesmo/gestao-estoque-backend/blob/main/LICENSE) MIT.
