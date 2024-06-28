# Testes de Sistema

## 💡 Introdução
Os testes de sistema realizados neste estudo estão ligados à Interface Gráfica do Usuário (GUI), visando verificar as funcionalidades do sistema como um todo. Em outras palavras, se o sistema atende as expectativas dos requisitos do sistema. Os testes de sistema são realizados sob a abordagem de caixa preta, ou seja, a verificação dos resultados tem como foco o design externo do comportamento do software, sem se preocupar com o código-fonte propriamente dito. 

Dessa forma, foi escolhido validar todos os requisitos funcionais do sistema em questão. Nessa abordagem, utilizou-se a ferramenta “Eclipse IDE for Enterprise Java and Web Developers” na versão 2023–09, junto com as ferramentas Selenium e JUnit, para automatizar a realização dos testes em navegador web e automatizar a execução e cobertura do teste, respectivamente.

## 📑 Análise Pré-Teste
Foram realizados duas análises prévias à elaboração dos casos de teste para o sistema: Particionamento por Classes de Equivalência e Análise de Valor Limite.

O Particionamento por Classes de Equivalência foi empregado para lidar com conjuntos de dados de entrada que compartilham o mesmo comportamento. Esta abordagem permitiu identificar tanto partições válidas quanto inválidas, para que posteriormente sejam implementadas na definição dos testes. A análise revelou restrições em alguns atributos, além de esclarecer os formatos aceitos.

Por outro lado, a Análise de Valor Limite apresentou desafios devido à falta de limites explícitos nas interfaces do sistema. Isso exigiu uma abordagem mais observacional para identificar as fronteiras do software. Nesse contexto, será necessário considerar outras estratégias de teste para assegurar eficácia em ambientes com limites variáveis.

## 🧩 Estrutura dos Casos de Teste
Após a análise realizada, para os testes de sistema, foram declarados testes de caso de sucesso, em que todas as entradas e ações são executadas conforme o esperado, resultando nos resultados desejados. Análogo, são conduzidos testes de caso de fracasso, onde identificam situações em que o sistema não se comporta conforme o esperado, expondo limitações, falhas e erros na interface. Toda essa abordagem foi realizada nos requisitos funcionais do sistema.

Para abrir o documento contendo todos os casos de teste implementados, utilize `Ctrl + clique` (ou `Cmd + clique` no Mac). Link do documento: [Roteiro Teste de Sistema](../docs/Casos_de_Teste_Requisitos.pdf).

## 📝 Detalhamento do Teste

- **@Before** e **@After**

<table>
<tr>
<th>@Before</th>
<th>@After</th>
</tr>
<tr>
<td>
<pre lang="java">
public  void setUp() {
	driver = new ChromeDriver();
	js = (JavascriptExecutor) driver;
	vars = new HashMap&ltString, Object&gt();
	driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
	driver.findElement(By.linkText("Cadastre-se.")).click();
</pre>
</td>
<td>
<pre lang="java">
public  void tearDown() {
Connection  conn = null;
Statement  stmt = null;
try {
	conn = GerenciadorConexao.getConexao();
	stmt = conn.createStatement();
	stmt.executeUpdate("DELETE FROM usuario WHERE id &gt 0");
} catch (Exception e) {
	e.printStackTrace();
}
</pre>
</td>
</tr>
</table>

- **@Test**

```java
// CASO DE SUCESSO
	@Test
	public void cadastrarse() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia de Lima");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30332@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010871");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin2");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
```

## 📊 Resultados dos Testes

A tabela apresenta o número total de testes realizados e seu respectivo script executado. Ao todo, foram realizados 228 testes nos requisitos funcionais do sistema.

| Requisito | Quantidade | Script |
|---|---|---|
| REQ001 | 6 | [Area Cadastro](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/AreaCadastroTest.java) |
| REQ002 | 38 | [Conta Cadastro](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ContaCadastroTest.java) |
| REQ003 | 7 | [Login](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/LoginTest.java) |
| REQ004 | 5 | [Recuperar Senha](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/RecuperarSenhaTest.java) |
| REQ005 | 14 | [Alterar Dados](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/AlterarDadosTest.java) |
| REQ006 | 23 | [Cadastrar Reserva](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/CadastrarReservaTest.java) |
| REQ007 | 4 | [Excluir Reserva](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ExcluirReservaTest.java) |
| REQ008 | 1 | [Visualizar Detalhes da Reserva](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/VisualizarReservaTest.java) |
| REQ009 | 1 | Validação realizada manualmente |
| REQ010 | 1 | [Listar Reserva](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ListarReservaTest.java) |
| REQ011 | 1 | [Listar por Data](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ListarReservaPorDataTest.java) |
| REQ012 | 1 | [Visualizar por Sala](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/VisualizarCalendarioPorSalaTest.java) |
| REQ013 | 18 | [Gerenciar Usuário - Editar](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarUsuarioEditarTest.java) <br/> [Gerenciar Usuário - Excluir](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarUsuarioExcluirTest.java) <br/> [Gerenciar Usuário - Listar](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarUsuarioListarTest.java) |
| REQ014 | 72 | [Gerenciar Espaços - Cadastro (Bloco)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosBlocoTest.java) <br/> [Gerenciar Espaços - Editar (Bloco)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosBlocoEditarTest.java) <br/> [Gerenciar Espaços - Excluir (Bloco)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosBlocoExcluirTest.java) <br/> [Gerenciar Espaços - Cadastrar (Sala)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosSalaTest.java) <br/> [Gerenciar Espaços - Editar (Sala)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosSalaEditarTest.java) <br/> [Gerenciar Espaços - Excluir (Sala)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosSalaExcluirTest.java) <br/> [Gerenciar Espaços - Listar (Sala)](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/GerenciarEspacosSalaListarTest.java) |
| REQ015 | 30 | [Cadastrar Reserva em Lote](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/CadastrarReservaEmLoteTest.java) |
| REQ016 | 4 | [Excluir Reserva em Lote](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ExcluirReservaEmLoteTest.java) |
| REQ017 | 1 | [Listar Todas as Reservas](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ListarTodasReservasTest.java) |
| REQ018 | 1 | [Listar Reservas em Lote](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/ListarReservasEmLoteTest.java) |

  
- **Extras**
| Funcionalidade | Script |
|---|---|
| Ativação de Conta | [Ativar Conta](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/AtivacaoTest.java) |
| Sair do Sistema | [Logout](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/src/teste_requisitos/LogoutTest.java) |


Dessa forma, os principais resultados identificados durante a realização desses testes foram:

- **Principais problemas identificados:**
    - **Problema 1:** Erro de validação na entrada de dados durante o registro de usuários, espaços e reservas;
        - Não existe validação de formato e tamanho de atributos como nome, login, senha, e-mail e finalidade.
    - **Problema 2:** Erro de cadastro de atributo como capacidade e matrícula de forma negativa;
    - **Problema 3:** A interface não corresponde aos limites implementados no banco;
        - Alterações e cadastros são feitos na interface com status de sucesso, porém no banco não é registrado.
    - **Problema 4:** Erro de duplicidade de espaços;
        - Duas salas/blocos diferentes podem ter o nome dificultando a identificação.

---
[🔙 Voltar](../tests/introducao.md/#️-roteiro-de-teste)