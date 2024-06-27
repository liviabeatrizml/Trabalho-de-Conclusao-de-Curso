# Relatório Técnico

## 📌 Sumário
- [💡 Introdução](#-introdução)
	- [✏️ Descrição do Projeto](#️-descrição-do-projeto)
	- [📑 Plano de Teste](#-plano-de-teste)
	- [🗺️ Roteiro de Teste](#️-roteiro-de-teste)
      - [🎯 Objetivos dos Testes](#-objetivos-dos-testes)
      - [📊 Estratégia de Teste](#-estratégia-de-teste)
      - [🖥️ Ambiente de Teste](#️-ambiente-de-teste)
      - [🚀 Execução dos Testes](#-execução-dos-testes)
- [📈 Análise de Resultados](#-análise-de-resultados)
- [📝 Considerações Finais e Trabalhos Futuros](#-considerações-finais-e-trabalhos-futuros)

## 💡 Introdução
Em linhas gerais, a abordagem de teste escolhida foi a de caixa-preta ou também chamada de testes funcionais, que tem como objetivo avaliar o comportamento externo do sistema, para garantir que as funcionalidades atendam aos requisitos especificados. Para isso, foi utilizado o Sistema de Reservas de Salas da UFERSA campus Pau dos Ferros.

## ✏️ Descrição do Projeto
Este documento descreve a abordagem de teste, os objetivos, o escopo, metodologia e os resultados do projeto de implementação de testes funcionais no Sistema de Reserva de Salas.

Fazendo a contextualização, a Universidade Federal Rural do Semi-Árido - UFERSA, disponibiliza um sistema de gerenciamento de alocação de salas, denominado de Sistema de Reservas de Salas (SRS).

Assim, com a demanda a cada novo semestre pelo cadastramento das reservas de salas, foi construído um sistema para agilizar tal ação. O sistema é simples, e apenas docentes e técnicos administrativos tem acesso direto a ele, porém discentes e pessoas externas tem acesso à visualização das reservas vinculadas as salas da instituição.

O SRS é um sistema web desenvolvido na linguagem _Java_ com propósito de simplificar o processo de agendamento de salas. Com o SRS, os servidores da UFERSA têm capacidade de efetuar reservas de salas de aulas, laboratórios e auditórios, por meio de uma interface gráfica.

Porém, como todo software existe a necessidade de manutenção no sistema diante das novas demandas, assim, as informações dispostas a seguir tem como intuito servir como uma documentação que oferta uma visão sistemática e abrangente da execução de testes no sistema informado.

## 📑 Plano de Teste
**🎯 Escopo do Teste:** O teste abrangerá todas as funcionalidades principais do software, não se limitando apenas essas, mas incluindo:

1. Atualização do Manual do Usuário;
2. Criação de Documentação do Sistema;
3. CRUD (Creat, Read, Update e Delete) dos usuários;
4. CRD (Creat, Read e Delete) das reservas;
5. Visualização do Calendário de Reservas;
6. Reserva de salas para datas específicas;
7. Notificações por e-mail sobre reservas realizadas.

**✔️ Cobertura de Teste:** Os testes serão realizados em diferentes aspectos, incluindo funcionalidade, desempenho, interface, responsividade e afins. Serão executados testes de unidade e de testes de sistema, bem como testes de aceitação do usuário.

**⚠️ Funcionalidades a serem testadas:** Todas as funcionalidades listadas nos requisitos funcionais do sistema serão testadas.

**⚠️ Funcionalidades não testadas e motivo:** Não serão testadas as funcionalidades de integração com o funcionamento interno, pois essas dependem da disponibilidade do código fonte diretamente, o que está fora do escopo deste trabalho.

## 🗺️ Roteiro de Teste
Para acessar o roteiro completo dos testes realizados, clique no seguinte link: [Roteiro dos Testes](Colocar_O_Link_Depois).

Você também pode acessar cada roteiro individualmente em suas respectivas páginas:
- [Teste de Sistema](../tests/sistema.md)
- [Teste Exploratórios](../tests/exploratório.md)
- [Teste de Responsividade](../tests/responsividade.md)
- [Teste de Desempenho](../tests/desempenho.md)

Também estão disponíveis informações sobre as ferramentas utilizadas e configurações realizadas para os testes:
- [Ferramentas e Configurações](../tests/ferramentas.md)

### 🎯 Objetivos dos Testes
Os principais objetivos dos testes realizados incluem:

- Verificar a conformidade com os requisitos especificados.
- Validar as funcionalidades esperadas do sistema.
- Verificar os aspectos não funcional do sistema.
- Identificar gargalos na interface do sistema.
- Identificar os erros e falhas frequentes que afetam à experiência do usuário.

### 📊 Estratégia de Teste
A estratégia de teste incluiu a execução de testes de:

-  **Unidade**: Validar os conjuntos de dados semelhantes e limites do sistema.
-  **Sistema**: Verificação da interação entre módulos do sistema.
-  **Exploratório**: Verificar inconsistências não atendidas em testes com escopo fechado. 
-  **Responsividade**: Validar se o sistema atende as adaptações em diferentes dispositivos.
-  **Desempenho**: Verificar a consistência do sistema em grandes cargas.
-  **Aceitação**: Validar se o sistema atende aos critérios de aceitação do usuário, por meio de um questionário.

### 🖥️ Ambiente de Teste
Os testes foram realizados no seguinte ambiente:

-  **Hardware**:
	- **Processador**: AMD Ryzen 7 4800H with Radeon Graphics 2.90 GHz
	- **Memória RAM**: 16GB
	- **Memória Interna**: 1TB de HD e 256 SSD
	-  **Tela de computador**: 15.6 polegadas;
	-  **Resolução da tela**: 1920x1080 pixels.

-  **Software**:
	-  **Sistema Operacional**: Windows 10 Home;
	-  **Navegador da Web**: Google Chrome;
	-  **Ambiente de Desenvolvimento Integrado (IDE)**: Eclipse IDE for Enterprise Java and Web Developers - 2023-09.

-  **Ferramentas**:
	-  **Selenium**: Teste de aplicações web, automatização de interações em navegadores.
	-  **JUnit**: Testes de comportamento e cobertura de funcionalidade.
	-  **JMeter**: Teste de carga e estresse.
	-  **Google Lighthouse**: Teste de performance, acessibilidade e boas práticas.
	-  **Git e GitHub**: Hospedagem e versionamento dos códigos de testes.

### 🚀 Execução dos Testes

Os testes foram realizados entre `02/02/2024` e `XX/XX/XXXX`, envolvendo apenas um testador a autora do trabalho.

Durante o período de testes, foram utilizadas as seguintes metodologias: testes de sistema, testes de desempenho, teste de responsividade e testes exploratórios. A cobertura de testes alcançou `100%` das funcionalidades principais do sistema.

Foram realizados aproximadamente `XXX` casos de teste durante as várias fases de teste. 

Os testes foram conduzidos em um ambiente controlado, simulando condições reais do uso do sistema para garantir a confiabilidade nos resultados dos testes. 

Os resultados foram documentados e estão disponíveis para consulta diante do repositório criado.

## 📈 Análise de Resultados

Os resultados dos testes trabalhados, segue os principais:

- **Principais problemas identificados:**
  - **Problema 1:** Erro de validação na entrada de dados durante o registro de usuários e registro de reservas.
  - **Problema 2:** Lentidão na resposta de consultas em grande volume de dados.
  - **Problema 3:** Inconsistências na exibição da página de calendário em dispositivos móveis.

- **Ações corretivas recomendadas:**
  - Refinar as validações de entrada para evitar erros durante o registro.
  - Otimizar consultas de banco de dados para melhorar a performance em cenários de alta carga.
  - Implementar ajustes na renderização de elementos na interface para garantir consistência em todas as plataformas.

- **Conclusão:** XXX

## 📝 Considerações Finais e Trabalhos Futuros