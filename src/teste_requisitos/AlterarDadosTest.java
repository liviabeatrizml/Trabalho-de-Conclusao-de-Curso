package test;

import org.junit.Test;

import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.dao.GerenciadorConexao;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class AlterarDadosTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		Connection conn = null;
        Statement stmt = null;
       
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO usuario (id, nome, email, login, matricula, role, senha, status, codigoAtivacao, dataCadastro)"
            		+ "VALUES"
            		+ "(1, 'usuario_admin', 'usuario_admin@ufersa.edu.br', 'usuario_admin', 10, 1, '$2a$10$UMnwfEEo8Z/Jg3B6wM6I6uLdKdjFqwxsv6OIMh0OHIXSI/N1PWorK', 'ativo', 'WSPWKR0H3Z6N7U4', '2024-05-18 00:35:36'),"
            		+ "(2, 'usuario_teste', 'teste@ufersa.edu.br', 'usuario_teste', 20, 2, '$2a$10$I6IQav2/ugV.XvZl/tryn.IzEOUYV4lT9FRh5VR7sV106isU2PiQ6', 'inativo', 'QX5OTJB2HBCUMSO', '2024-05-18 12:56:36'),"
            		+ "(3, 'usuario_padrao', 'usuario_padrao@ufersa.edu.br', 'usuario_padrao', 30, 2, '$2a$10$nNI3CFoQU.PHoabmphUFB.h3rJC8GCll6WjnhyL0d1Nphrn3nRNwq', 'ativo', 'IQ8S8NQAWNHD4S0', '2024-05-18 00:37:57'),"
            		+ "(4, 'usuario_inativo', 'inativo@ufersa.edu.br', 'usuario_inativo', 40, 2, '$2a$10$75ckCTV8Qa/dPYEuKu0LbOs.zWWCh1m8N3olvOJ.5xup6cuR5Jdu.', 'inativo', 'M0Y8IK9TYIHF78T', '2024-05-18 00:38:49'),"
            		+ "(5, 'Livia e Geisa', 'livia_geisa@ufersa.edu.br', 'livia_geisa', 50, 1, '$2a$10$IxASABo0ZTG0GpTiTmG.BuIvBuXlXg4vjsqR6cPBtsfIjTYX33KXa', 'ativo', '22TOPNEXIIT1HU5', '2024-05-18 00:40:18');");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia_geisa");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("teste");
	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
       
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM usuario WHERE id > 0");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.quit();
	}

	// CASO DE SUCESSO
	@Test
	public void testAlterarDados() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - ATRIBUTOS EM BRANCO
	@Test
	public void testAlterarDadosEmBranco() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		WebElement mensagemErro = driver.findElement(By.className("ui-growl-item-container"));
		WebElement mensagemNome = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Nome: Campo Obrigatório!')]"));
		WebElement mensagemSenha = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Senha: Campo Obrigatório!')]"));

		assertEquals("Nome: Campo Obrigatório!", mensagemNome.getText());
		assertEquals("Senha: Campo Obrigatório!", mensagemSenha.getText());
	}
	
	// CASO DE FRACASSO - NOME EM BRANCO
	@Test
	public void testAlterarDadosNomeEmBranco() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		WebElement mensagemErro = driver.findElement(By.className("ui-growl-item-container"));
		WebElement mensagemNome = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Nome: Campo Obrigatório!')]"));
		
		assertEquals("Nome: Campo Obrigatório!", mensagemNome.getText());
	}
	
	// CASO DE FRACASSO - SENHA EM BRANCO
	@Test
	public void testAlterarDadosSenhaEmBranco() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		WebElement mensagemErro = driver.findElement(By.className("ui-growl-item-container"));
		WebElement mensagemSenha = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Senha: Campo Obrigatório!')]"));

		assertEquals("Senha: Campo Obrigatório!", mensagemSenha.getText());
	}

	// CASO DE FRACASSO - DADOS JÁ REGISTRADOS NO BANCO DE DADOS
	@Test
	public void testAlterarDadosDuplicados() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 101)
	@Test
	public void testAlterarNomeGrande() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Semper faucibus risus, et tincidunt turpis porta imperdiet. In congue neque a fringilla suscipit corp");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - SENHA GRANDE (TAMANHO 101)
	@Test
	public void testAlterarSenhaGrande() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("Maecenas dapibus augue eget ante pretium, at mollis justo elementum. Sed cursus euismod maximus termo");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - NOME PEQUENO (TAMANHO 1)
	@Test
	public void testAlterarNomePequeno() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("L");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - SENHA PEQUENA (TAMANHO 1)
	@Test
	public void testAlterarSenhaPequena() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Geísa_e_Lívia");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("t");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - NOME COM CARACTERE ESPECIAL
	@Test
	public void testAlterarNomeEspecial() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("#*&¨*#");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - SENHA COM CARACTERE ESPECIAL
	@Test
	public void testAlterarSenhaEspecial() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Geísa_e_Lívia");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("#*&¨*#");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}	

	// CASO DE FRACASSO - NOME COM APENAS NUMEROS
	@Test
	public void testAlterarNomeNumero() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("123456");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - SENHA COM APENAS NUMEROS
	@Test
	public void testAlterarSenhaNumero() {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.id("j_idt41:senha")).click();
		driver.findElement(By.id("j_idt41:senha")).sendKeys("123456");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt57 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE ALTERNATIVO - BOTÃO CANCELAR
	@Test
	public void testAlterarDadosCancelar() throws InterruptedException {
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Meu Perfil")).click();
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt55 > .ui-button-text")).click();
		Thread.sleep(2000);

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
}