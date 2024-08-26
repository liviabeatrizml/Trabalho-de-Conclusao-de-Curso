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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GerenciarEspacosBlocoEditarTest {
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
		driver.quit();
	}
	
	// CASO DE SUCESSO - EDIÇÃO
	@Test
	public void gerenciarEspacosEditarBloco() throws InterruptedException {
	    driver.findElement(By.id("j_idt41:j_idt53")).click();
	    
	    WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
	    Select select = new Select(dropdown);
	    select.selectByValue("17");

	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("Central de Aulas Novo");
	    driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

	    Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}
	

	// CASO DE FRACASSO EDITAR - BLOCO EM BRANCO
	@Test
	public void gerenciarEspacosEditarBlocoEmBranco() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();
	    
	    WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
	    Select select = new Select(dropdown);
	    select.selectByValue("17");

	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("");
	    driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

	    Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO EDITAR - COM INFORMAÇÕES JÁ REGISTRADAS
	@Test
	public void gerenciarEspacosEditarBlocoJaRegistrado() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();
	    
	    WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
	    Select select = new Select(dropdown);
	    select.selectByValue("17");

	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
	    driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("Central de Aulas II");
	    driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

	    Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO EDITAR - NOME GRANDE (TAMANHO 101)
	@Test
	public void gerenciarEspacosEditarBlocoNomeGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("17");

		driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
		driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("Semper faucibus risus, et tincidunt turpis porta imperdiet. In congue neque a fringilla suscipit corp");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO EDITAR - NOME PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosEditarBlocoNomePequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("17");

		driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
		driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("C");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO EDITAR - NOME COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosEditarBlocoNomeEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("17");

		driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
		driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("*@3543&¨%&");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO EDITAR - NOME COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosEditarBlocoApenasNumeros() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("17");

		driver.findElement(By.id("j_idt41:nome_dialog_edit")).click();
		driver.findElement(By.id("j_idt41:nome_dialog_edit")).sendKeys("123456");
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt90 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco editado com sucesso!", mensagem.getText());
	}
	
	// CASO DE ALTENATIVO EDITAR - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosEditarBlocoCancelar() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();
	    driver.findElement(By.id("j_idt41:j_idt89")).click();
	    
	    assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_sala.jsf", driver.getCurrentUrl());
	}	
}