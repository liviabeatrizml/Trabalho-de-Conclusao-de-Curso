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
import java.sql.SQLException;
import java.sql.Statement;

public class ContaCadastroTest {
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
		driver.findElement(By.linkText("Cadastre-se.")).click();
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

	// CASO DE FRACASSO - NOME, EMAIL, MATRICULA, LOGIN E SENHA EM BRANCO
	@Test
	public void atributosEmBranco() {
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Nome: Campo Obrigatório!", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		assertEquals("Email: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(1).getText());
		assertEquals("Matricula: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(2).getText());
		assertEquals("Login: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(3).getText());
		assertEquals("Senha: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(4).getText());
	}
	
	// CASO DE FRACASSO - NOME EM BRANCO
	@Test
	public void NomeEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.30332@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021060872");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin2");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin2");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Nome: Campo Obrigatório!", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	// CASO DE FRACASSO - EMAIL EM BRANCO
	@Test
	public void EmailEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021020871");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin2");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Email: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - MATRICULA EM BRANCO
	@Test
	public void MatriculaEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima3033@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("usuario");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Matricula: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - LOGIN EM BRANCO
	@Test
	public void LoginEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lim30332@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010877");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Login: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - SENHA EM BRANCO
	@Test
	public void SenhaEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima332@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021030871");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_adminss");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Senha: Campo Obrigatório!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 200)
	@Test
	public void nomeGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome"));
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Pedro de Alcântara Francisco Antônio João Carlos Xavier de Paula Miguel Rafael Joaquim José Gonzaga Pascoal Cipriano Serafim de Bragança Bourbon Maia de Lima Morais Gabriel Filgueira Milanez Goncalves");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("liviabeatrizmaia7@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234567890");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("nome_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("nome");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// NOME GRANDE (TAMANHO 100) - VALOR LIMITE
	@Test
	public void nomeGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome"));
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Pedro de Alcântara Francisco Antônio João Carlos Xavier de Paula Miguel Rafael Joaquim José Goncalve");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("liviabeatrizmaia7@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234567890");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("nome_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("nome");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - EMAIL GRANDE (TAMANHO 300)
	@Test
	public void emailGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia");
		driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Livia_Beatriz_Maia_Lima_Alysson_Filgueira_Milanez_Geisa_Morais_Gabriel_Livia_Geisa_Alysson_Millanez@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234567867");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("email_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("email");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	// EMAIL GRANDE (TAMANHO 200) - VALOR LIMITE
	@Test
	public void emailGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia");
		driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).sendKeys("Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234567867");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("email_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("email");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - MATRICULA GRANDE (TAMANHO 11)
	@Test
	public void matriculaGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Filgueira Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisamorais8@ufersa.edu.br");
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("20210103722");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("matricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Matricula: '" + matricula.getAttribute("value") + "' deve ser um número formado por um ou mais dígitos.", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	// MATRICULA GRANDE (TAMANHO 10) - VALOR LIMITE
	@Test
	public void matriculaGrande2() throws InterruptedException {
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Filgueira Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisamorais8@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010372");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula_grande");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("matricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// CASO DE FRACASSO - LOGIN GRANDE (TAMANHO 200)
	@Test
	public void loginGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Filgueira");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("22075463");
		driver.findElement(By.id("j_idt13:login"));
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Livia_Beatriz");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("login");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
      WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
      assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// LOGIN GRANDE (TAMANHO 100) - VALOR LIMITE
	@Test
	public void loginGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Filgueira");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("22075463");
		driver.findElement(By.id("j_idt13:login"));
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("Livia_Beatriz_Maia_Lima_Geisa_Morais_Gabriel_Alysson_Filgueira_Milanez_Livia_Beatriz_Maia_Lima_Geisa");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("login");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - SENHA GRANDE (TAMANHO 200)
	@Test
	public void senhaGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Geísa Morais Gabriel");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisa.gabriel@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010656");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("geisa");
		driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("pneumoultramicroscopicossilicovulcanoconiótico1234pneumoultramicroscopicossilicovulcanoconiótico5678pneumoultramicroscopicossilicovulcanoconiótico9101112pneumoultramicroscopicossilicovulcanoconiótico1");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// SENHA GRANDE (TAMANHO 100) - VALOR LIMITE
	@Test
	public void senhaGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Geísa Morais Gabriel");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisa.gabriel@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010656");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("geisa");
		driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("paraclorobenzilpirrolidinonetilbenzimidazol12345paraclorobenzilpirrolidinonetilbenzimidazol54321para");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - NOME PEQUENO (TAMANHO 1)
	@Test
	public void nomePequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("L");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234567");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("nome_pequeno");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("nome");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - EMAIL PEQUENO (TAMANHO 1)
	@Test
	public void emailPequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Livia Beatriz");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("l@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010877");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("email_pequeno");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("email");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - MATRICULA PEQUENA (TAMANHO 1)
	@Test
	public void matriculaPequena() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("GeísaMorais");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisa_123@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula_pequena");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("matricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - LOGIN PEQUENO (TAMANHO 1)
	@Test
	public void loginPequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Geísa Filgueira");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("GFIG@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("202101548");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("f");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("login");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - SENHA PEQUENA (TAMANHO 1)
	@Test
	public void senhaPequena() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Livia Filgueira");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("geisa_liv@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("123431");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia_fig");
		driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("1");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - NOME COM CARACTERE ESPECIAL
	@Test
	public void nomeEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("*@.3");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("11via_maia@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("010101011");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("nomeEspecial");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("nome");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - EMAIL COM CARACTERE ESPECIAL
	@Test
	public void emailEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("#@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("01");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("emailEspecial");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("email");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
	    WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
	    assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - MATRICULA COM CARACTERE ESPECIAL
	@Test
	public void matriculaEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson@ufersa.edu.br");
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("*%$#%$$");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula_especial");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("matricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
        assertEquals("Matricula: '" + matricula.getAttribute("value") + "' deve ser um número formado por um ou mais dígitos.", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	// CASO DE FRACASSO - LOGIN COM CARACTERE ESPECIAL
	@Test
	public void loginEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Leonardo");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("leonardo@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1035045050");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("**%$@$&%");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("login");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
    	WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
    	assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - SENHA COM CARACTERE ESPECIAL
	@Test
	public void senhaEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Inácio");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("lgdantas@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("1234325443");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("leo");
		driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys(")(:}{%$#");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
    	WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
    	assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - NOME COM APENAS NÚMEROS
	@Test
	public void nomeNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("123456");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010888");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("nome_numeros");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("numeros");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
    	WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
    	assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// CASO DE FRACASSO - LOGIN COM APENAS NÚMEROS
	@Test
	public void loginNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Livia");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010888");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("123456");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("numeros");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
    	WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
    	assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}
	
	// CASO DE FRACASSO - SENHA COM APENAS NÚMEROS
	@Test
	public void senhaNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Livia");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010888");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("senha_Numeros");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("123456");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
    	WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
    	assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - MATRICULA COM APENAS LETRAS
	@Test
	public void matriculaLetras() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Filgueira Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson_fil@ufersa.edu.br");
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("abc");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula_letras");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("letras");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Matricula: '" + matricula.getAttribute("value") + "' deve ser um número formado por um ou mais dígitos.", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	// CASO DE FRACASSO - MATRICULA COM LETRAS E NUMEROS
	@Test
	public void matriculaLetrasNumero() {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson_mil@ufersa.edu.br");
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("123abc456");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senhamatricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Matricula: '" + matricula.getAttribute("value") + "' deve ser um número formado por um ou mais dígitos.", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}
	
	// CASO DE FRACASSO - MATRICULA NEGATIVA
	@Test
	public void matriculaNegativa() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Alysson Milanez");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("alysson_mil@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("-12");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("matricula");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senhamatricula");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(10000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-info-summary"));
		assertEquals("Usuário cadastrado com sucesso!", alerta.getText());
	}

	// CASO DE FRACASSO - EMAIL SEM O DOMINIO @UFERSA.EDU.BR
	@Test
	public void emailSemDominio() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Livia Lima");
		driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("liviabeatrizmaia7@gmail.com");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2020010871");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia_email");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("email");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(5000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("O e-mail deve ter o seginte formato: xxxxxx@ufersa.edu.br", alerta.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	// CASO DE FRACASSO - USUÁRIO EXISTENTE
	@Test
	public void cadastrarUsuarioExistente() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("usuario_admin@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("10");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senha");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(5000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Já existe um usuário com esse e-mail!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
		assertEquals("Já existe um usuário com essa matrícula!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(1).getText());
		assertEquals("Já existe um usuário com esse login!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(2).getText());
	}
	
	// CASO DE FRACASSO - EMAIL EXISTENTE
	@Test
	public void cadastrarEmailExistente() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("usuario_admin@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("0871");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin23");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senha");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Já existe um usuário com esse e-mail!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - MATRICULA EXISTENTE
	@Test
	public void cadastrarMatriculaExistente() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("usuario_admin2");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima303@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("10");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin24");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senha");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(5000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Já existe um usuário com essa matrícula!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}

	// CASO DE FRACASSO - LOGIN EXISTENTE
	@Test
	public void cadastrarLoginExistente() throws InterruptedException {
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30@ufersa.edu.br");
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("senha");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(2000);
		WebElement alerta = driver.findElement(By.cssSelector(".ui-messages-error"));
		assertEquals("Já existe um usuário com esse login!", alerta.findElements(By.cssSelector("span.ui-messages-error-summary")).get(0).getText());
	}
}