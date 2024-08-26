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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GerenciarEspacosSalaExcluirTest {
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

	}

	@After
	public void tearDown() {
		driver.quit();
	}

	// CASO DE SUCESSO DE EXCLUIR SALA
	@Test
	public void gerenciarEspacosExcluir() throws InterruptedException {
	{
		WebElement element = driver.findElement(By.linkText("Salas"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
	}
		driver.findElement(By.linkText("Nova Sala")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala 00");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemCadastro = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagemCadastro.getText());
		
		Thread.sleep(1000);

		{
			WebElement element = driver.findElement(By.linkText("Salas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		
		driver.findElement(By.linkText("Listar Salas")).click();
		
		driver.findElement(By.id("dt-salas:1:j_idt58")).click();
		driver.findElement(By.id("dt-salas:9:j_idt61")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala deletada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - SALA VINCULADA A RESERVA
	@Test
	public void gerenciarEspacosExcluirSalaVinculada() throws InterruptedException {
		{
			WebElement element = driver.findElement(By.linkText("Reservas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}

		driver.findElement(By.linkText("Nova Reserva")).click();
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("16/07/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();
		
		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	

		{
			WebElement element = driver.findElement(By.linkText("Salas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		
		driver.findElement(By.linkText("Listar Salas")).click();
		
		driver.findElement(By.id("dt-salas:0:j_idt58")).click();
		driver.findElement(By.id("dt-salas:9:j_idt61")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Não é possível apagar a sala!", mensagem.getText());
	}
	
	// CASO DE ALTERNATIVO - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosExcluirBotaoCancelar() throws InterruptedException {
	{
		WebElement element = driver.findElement(By.linkText("Salas"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
	}
		driver.findElement(By.linkText("Nova Sala")).click();
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala 01");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemCadastro = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagemCadastro.getText());
		
		Thread.sleep(1000);

		{
			WebElement element = driver.findElement(By.linkText("Salas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		
		driver.findElement(By.linkText("Listar Salas")).click();
		
		driver.findElement(By.id("dt-salas:0:j_idt58")).click();
		driver.findElement(By.id("dt-salas:0:j_idt60")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/list_salas.jsf", driver.getCurrentUrl());
	}
}
