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

public class GerenciarUsuarioExcluirTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		driver.findElement(By.linkText("Usuários")).click();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	// CASO DE SUCESSO EXCLUIR
	@Test
	public void gerenciarUsuarioExcluir() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:2:j_idt57")).click();
		driver.findElement(By.id("dt-usuarios:2:j_idt60")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário deletado com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - EXCLUIR USUÁRIO ADMINISTRADOR
	@Test
	public void gerenciarUsuarioExcluirAdmin() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:1:j_idt57")).click();
		driver.findElement(By.id("dt-usuarios:2:j_idt60")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário deletado com sucesso!", mensagem.getText());
	}
	// CASO ALTERNATIVO - BOTÃO CANCELAR EXCLUIR
	@Test
	public void gerenciarUsuarioExcluirCancelar() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:1:j_idt57")).click();
		driver.findElement(By.id("dt-usuarios:2:j_idt59")).click();

		Thread.sleep(5000);

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/list_usuarios.jsf", driver.getCurrentUrl());
	}
}
