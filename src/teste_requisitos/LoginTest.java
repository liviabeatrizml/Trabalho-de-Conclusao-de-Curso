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

import org.openqa.selenium.support.ui.FluentWait;

public class LoginTest {
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

		// ENTRAR NO SISTEMA
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
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
	public void testRealizarLogin() {

		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// USUÁRIO ENTROU NA PÁGINA DESEJADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - LOGIN E SENHA EM BRANCO
	@Test
	public void loginSenhaEmBranco() {

		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE OS ELEMENTOS ESTÃO VAZIOS
		assertEquals("", driver.findElement(By.id("j_idt13:login")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("j_idt13:senha")).getAttribute("value"));

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("É necessário inserir o login!", mensagensErro.get(0).getText());
		assertEquals("É necessário inserir a senha!", mensagensErro.get(1).getText());
	}

	// CASO DE FRACASSO - LOGIN EM BRANCO
	@Test
	public void loginEmBranco() {

		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("É necessário inserir o login!", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - SENHA EM BRANCO
	@Test
	public void senhaEmBranco() {

		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("É necessário inserir a senha!", mensagensErro.get(0).getText());

	}

	// CASO DE FRACASSO - LOGIN INEXISTENTE NO BANCO DE DADOS
	@Test
	public void loginUsuarioInexistente() {

		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("pedro");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("123");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("Login ou senha incorretos!", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - LOGIN INCORRETO
	@Test
	public void loginIncorreto() {

		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("Login ou senha incorretos!", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - SENHA INCORRETA
	@Test
	public void senhaIncorreto() {

		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senha?");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		// VERIFICA SE APARECE A MENSAGEM DE ALERTA
		WebElement mensagemErro = driver.findElement(By.className("ui-messages-error"));

		List<WebElement> mensagensErro = mensagemErro.findElements(By.className("ui-messages-error-summary"));

		assertEquals("Login ou senha incorretos!", mensagensErro.get(0).getText());
	}
}