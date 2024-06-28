package test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class AreaCadastroTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	// CHROME
	@Test
	public void areaCadastroChrome() throws InterruptedException {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
	}
	
    // FIREFOX  
	@Test
	public void areaCadastroFirefox() throws InterruptedException {
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
	}

	// EDGE  
	@Test
	public void areaCadastroEdge() throws InterruptedException {
		driver = new EdgeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
	}
	
	// VERIFICA SE OS CAMPOS ESTÃO ACESSIVEIS
	@Test
	public void areaCadastroChromeCampos() throws InterruptedException {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
		
		WebElement nome = driver.findElement(By.id("j_idt13:nome"));
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia de Lima");
		
		WebElement email = driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30332@alunos.ufersa.edu.br");
		
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010871");
		
		WebElement login = driver.findElement(By.id("j_idt13:login"));
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia");
		
		WebElement senha = driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		
		assertEquals("Lívia Beatriz Maia de Lima", nome.getAttribute("value"));
		assertEquals("livia.lima30332@alunos.ufersa.edu.br", email.getAttribute("value"));
		assertEquals("2021010871", matricula.getAttribute("value"));
		assertEquals("livia", login.getAttribute("value"));
		assertEquals("admin", senha.getAttribute("value"));
	}
	
	// VERIFICA SE OS CAMPOS ESTÃO ACESSIVEIS
	@Test
	public void areaCadastroFirefoxCampos() throws InterruptedException {
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
		
		WebElement nome = driver.findElement(By.id("j_idt13:nome"));
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia de Lima");
		
		WebElement email = driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30332@alunos.ufersa.edu.br");
		
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010871");
		
		WebElement login = driver.findElement(By.id("j_idt13:login"));
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia");
		
		WebElement senha = driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		
		assertEquals("Lívia Beatriz Maia de Lima", nome.getAttribute("value"));
		assertEquals("livia.lima30332@alunos.ufersa.edu.br", email.getAttribute("value"));
		assertEquals("2021010871", matricula.getAttribute("value"));
		assertEquals("livia", login.getAttribute("value"));
		assertEquals("admin", senha.getAttribute("value"));
	}
	
	// VERIFICA SE OS CAMPOS ESTÃO ACESSIVEIS
	@Test
	public void areaCadastroEdgeCampos() throws InterruptedException {
		driver = new EdgeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		driver.findElement(By.linkText("Cadastre-se.")).click();
		
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/cadastro.jsf", driver.getCurrentUrl());
		
		WebElement nome = driver.findElement(By.id("j_idt13:nome"));
		driver.findElement(By.id("j_idt13:nome")).click();
		driver.findElement(By.id("j_idt13:nome")).sendKeys("Lívia Beatriz Maia de Lima");
		
		WebElement email = driver.findElement(By.id("j_idt13:email"));
		driver.findElement(By.id("j_idt13:email")).click();
		driver.findElement(By.id("j_idt13:email")).sendKeys("livia.lima30332@alunos.ufersa.edu.br");
		
		WebElement matricula = driver.findElement(By.id("j_idt13:matricula"));
		driver.findElement(By.id("j_idt13:matricula")).click();
		driver.findElement(By.id("j_idt13:matricula")).clear();
		driver.findElement(By.id("j_idt13:matricula")).sendKeys("2021010871");
		
		WebElement login = driver.findElement(By.id("j_idt13:login"));
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("livia");
		
		WebElement senha = driver.findElement(By.id("j_idt13:senha"));
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		
		assertEquals("Lívia Beatriz Maia de Lima", nome.getAttribute("value"));
		assertEquals("livia.lima30332@alunos.ufersa.edu.br", email.getAttribute("value"));
		assertEquals("2021010871", matricula.getAttribute("value"));
		assertEquals("livia", login.getAttribute("value"));
		assertEquals("admin", senha.getAttribute("value"));
	}
	
}
