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

public class VisualizarCalendarioPorSalaTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	//CASO DE SUCESSO
	@Test
	public void VisualizarCalendarioPorSala() throws InterruptedException {
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia_geisa");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(1000);

		{
			WebElement element = driver.findElement(By.linkText("Reservas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}

		driver.findElement(By.linkText("Calendário por Sala")).click();
		driver.findElement(By.id("j_idt13:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt13:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'Central de Aulas I - Sala de aula 04']")).click();
		}

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/calendario.jsf", driver.getCurrentUrl());
	}
}
