# Ferramentas

> Esta página é dedicada às ferramentas para a construção do sistema e aos testes de software. Dessa forma, haverá uma breve identificação do uso, seguida de instruções de instalação e configuração.

## ⚙️ Ferramentas utilizadas

### Astah
> Ferramenta para criação dos diagramas

1. **Instalação do Astah UML:**
   - Faça o download do Astah UML em [Astah UML Downloads](https://astah.net/downloads/);
   - Siga as instruções de instalação fornecidas pelo instalador.

### Java e JDK
> Ferramentas de apoio

#### Configuração do Java JDK 21
**Pré Requisito:** Ter o Java instalado no ambiente
- **Instalação do Java:**
   - Faça o download do Java versão 8 em [Java Downloads](https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR);
   - Siga as instruções de instalação para o seu sistema operacional.

1. **Instalação do JDK 21:**
   - Faça o download e instale o JDK 21 [Oracle JDK Downloads](https://www.oracle.com/br/java/technologies/downloads/#jdk21-windows);
   - Siga as instruções de instalação fornecidas pelo instalador.

### Eclipse IDE
> Ambiente de desenvolvimento integrado

1. **Instalação do Eclipse:**
   - Faça o download do Eclipse IDE em [Eclipse Downloads](https://www.eclipse.org/downloads/);
   - Execute o arquivo e escolha a opção: **Eclipse IDE for Enterprise Java and Web Developers**;
   - Siga as instruções de instalação fornecidas pelo instalador.

### MySQL Workbench
> Ambiente de gerenciamento do banco de dados MySQL

1. **Instalação do MySQL:**
   - Faça o download do MySQL Workbench Community em [MySQL Downloads](https://dev.mysql.com/downloads/installer/);
   - Siga as instruções de instalação para o seu sistema operacional.

2. **Criação do Banco de Dados:**

```sql
CREATE DATABASE srs;
USE srs;

-- Tabela de usuários
CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(200),
    login VARCHAR(100),
    matricula INTEGER,
    role INTEGER,
    senha VARCHAR(100),
    status VARCHAR(100),
    codigoAtivacao VARCHAR(100),
    dataCadastro TIMESTAMP,
    dataUltimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (id, matricula, login, email)
);

-- Tabela de blocos
CREATE TABLE Bloco (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

-- Tabela de salas
CREATE TABLE Sala (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    idBloco INTEGER,
    nome VARCHAR(100),
    capacidade INTEGER,
    informacoes VARCHAR(200),
    status VARCHAR(100),
    dataCadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dataUltimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR(100)
);

ALTER TABLE Sala ADD CONSTRAINT FK_Sala_3
    FOREIGN KEY (idBloco)
    REFERENCES Bloco (id);

-- Tabela de reservas
CREATE TABLE reserva (
    id INTEGER PRIMARY KEY UNIQUE AUTO_INCREMENT,
    idUsuario INTEGER,
    idSala INTEGER,
    dataReserva DATE,
    horaInicio TIME,
    horaFim TIME,
    finalidade VARCHAR(200),
    dataHoraCadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idPool INTEGER,
    idAdmin INTEGER,
    dataInicioPool DATE,
    dataFimPool DATE,
    passoPool INTEGER,
    dataUltimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE reserva ADD CONSTRAINT FK_Reserva_3
    FOREIGN KEY (idUsuario)
    REFERENCES usuario (id);
 
ALTER TABLE reserva ADD CONSTRAINT FK_Reserva_4
    FOREIGN KEY (idSala)
    REFERENCES sala (id);

```

### TomCat
> Servidor Web Java

1. **Instalação do TomCat:**
   - Faça o download do TomCat 8.5.95 em [TomCat Downloads](https://tomcat.apache.org/download-80.cgi);
   - Siga as instruções de instalação para o seu sistema operacional.

2. **Configuração com o Eclipse:**
   - No menu do Eclipse selecione a opção Window / Preferences;
   - Clique em Server;
   - Clique em Runtime Environment e clique no botão 'Add...';
   - Selecione a opção Apache e selecione o Apache Tomcat v8.5;
   - Marque o checkbox Create a new local server, após clique em Next;
   - Clique no botão “Browse...”, selecione o diretório onde está a pasta tomcat e depois clique no botão Finish.

### JUnit
> Framework para criação de testes unitários em Java

1. **Configuração com o Eclipse:**
   - Selecione o projeto e clique no botão direito do mouse;
   - Escolha "Build Path" depois clique no botão "Add Libraries";
   - Selecione a opção JUnit, clique em Next;
   - Escolha a versão JUnit 5. 

### Selenium
> Framework para criação de teste em navegador web

1. **Instalação do Selenium:**
   - Faça o download dos jar do Selenium na linguagem java em [Selenium Downloads](https://www.selenium.dev/downloads/).

2. **Configuração com o Eclipse:**
   - Selecione o projeto e clique no botão direito do mouse;
   - Escolha "Build Path" depois clique no botão "Add External JARs";
   - Selecione todos os jar's do selenium, clique em Apply;
   - Escolha a versão JUnit 5.

### Google Lighthouse
> Ferramenta para medir qualidade de páginas web

1. **Instalação da extensão Lighthouse:**
   - Faça o download da extensão Lighthouse em [Lighthouse Downloads](https://chromewebstore.google.com/detail/lighthouse/blipmdconlkpinefehnmjammfjpmpbjk?hl=pt-BR);
   - Reinicie seu navegador;
  
2. **Instalação do DevTools Lighthouse:**
   - Clicar com o botão direito do mouse em qualquer lugar da página e selecione "Inspecionar";
   - Selecione o Lighthouse na barra de ferramentas.

### JMeter
> Ferramenta para criação de teste de carga e estresse em recursos estáticos ou dinâmicos

1. **Instalação do Apache JMeter:**
   - Faça o download e instale o Jmeter [Jmeter Downloads](https://jmeter.apache.org/download_jmeter.cgi);
   - Descompacte o pacote em sua máquina e execute o arquivo jar do JMeter.

### Git e GitHub
> Git ferramenta de versionamento e GitHub ferramenta de hospedagem de código-fonte

1. **Instalação do Git:**
   - Faça o download do Git em [Git Downloads](https://git-scm.com/downloads);
   - Siga as instruções de instalação para o seu sistema operacional.

2. **Configuração do Git:**
   - Abra o terminal 
   - git config --global user.name "Seu nome"
   - git config --global user.email "seuemail@github.com"

**Para o GitHub não foi realizada instalação**

---
[🔙 Voltar](../tests/introducao.md/#️-roteiro-de-teste)