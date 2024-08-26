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

public class GerenciarEspacosBlocoExcluirTest {
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
	
	// CASO DE SUCESSO - DELETAR
	@Test
	public void gerenciarEspacosExcluirBloco() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("18");

		driver.findElement(By.id("j_idt41:j_idt78")).click();
		driver.findElement(By.id("j_idt41:j_idt85")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Bloco deletado com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO EXCUIR - BLOCO COM SALAS REGISTRADAS
	@Test
	public void gerenciarEspacosExcluirBlocoComSalas() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("1");

		driver.findElement(By.id("j_idt41:j_idt78")).click();
		driver.findElement(By.id("j_idt41:j_idt85")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Não foi possível deletar o bloco!", mensagem.getText());
	}	

	// CASO DE ALTERNATIVO EXCLUIR - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosExcluirBlocoCancelar() throws InterruptedException {
		driver.findElement(By.id("j_idt41:j_idt53")).click();

		WebElement dropdown = driver.findElement(By.id("j_idt41:bloco_edit"));
		Select select = new Select(dropdown);
		select.selectByValue("18");

		driver.findElement(By.id("j_idt41:j_idt78")).click();
		driver.findElement(By.id("j_idt41:j_idt84")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_sala.jsf", driver.getCurrentUrl());
	}	
}