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
import org.openqa.selenium.support.ui.Select;
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

public class GerenciarEspacosBlocoTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() throws InterruptedException {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(1000);

		{
			WebElement element = driver.findElement(By.linkText("Salas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		driver.findElement(By.linkText("Nova Sala")).click();
		
		Thread.sleep(1000);

	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM bloco WHERE id >= 11");

        } catch (Exception e) {
            e.printStackTrace();
        }
		driver.quit();
	}

	// CASO DE SUCESSO
	@Test
	public void gerenciarEspacosCadastrarBloco() throws InterruptedException {	
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("Central de Aulas III");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO CADASTRO - NOME EM BRANCO
	@Test
	public void gerenciarEspacosCadastrarBlocoEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();


		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO CADASTRO - NOME GRANDE (TAMANHO 101)
	@Test
	public void gerenciarEspacosCadastrarBlocoNomeGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("Integer dictum tincidnt arcu at autor. Mauris ex arcu, suscipit sit amet elefend in, elefend necc exx");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO CADASTRO - NOME GRANDE (TAMANHO 100) - VALOR LIMITE
	@Test
	public void gerenciarEspacosCadastrarBlocoNomeGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("Integer dictum tincidnt arcu at autor. Mauris ex arcu, suscipit sit amet elefend in, elefend necc ex");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}		

	// CASO DE FRACASSO CADASTRO - NOME PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosCadastrarBlocoNomePequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("B");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO CADASTRO - NOME COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosCadastrarBlocoNomeEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("%¨$¨@#%");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO CADASTRO - NOME COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosCadastrarBlocoApenasNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("1234");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - COM AS INFORMAÇÕES JÁ REGISTRADAS
	@Test
	public void gerenciarEspacosCadastrarBlocoJaRegistrado() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:nome_dialog")).sendKeys("Central de Aulas II");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco cadastrado com sucesso!", mensagem.getText());
	}
	
	// CASO DE ALTENATIVO CADASTRO - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosCadastrarBlocoCancelar() throws InterruptedException {	
		driver.findElement(By.id("j_idt41:j_idt52")).click();
		driver.findElement(By.id("j_idt41:j_idt71")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_sala.jsf", driver.getCurrentUrl());
	}	
}