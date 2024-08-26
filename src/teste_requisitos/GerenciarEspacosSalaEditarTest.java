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

public class GerenciarEspacosSalaEditarTest {
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

		driver.findElement(By.linkText("Listar Salas")).click();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	// CASO DE SUCESSO DE EDITAR
	@Test
	public void gerenciarEspacosEditarSala() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Nova Sala de Aula - Teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("HDMI e VGA");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME EM BRANCO
	@Test
	public void gerenciarEspacosEditarSalaNomeEmBranco() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi e vga");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - CAPACIDADE EM BRANCO
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeEmBranco() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala Teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi e vga");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}

	// CASO DE FRACASSO - INFORMAÇÕES EM BRANCO
	@Test
	public void gerenciarEspacosEditarSalaInformacoesEmBranco() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala Teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		Thread.sleep(3000);
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 101)
	@Test
	public void gerenciarEspacosEditarSalaNomeGrande() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de aula teste nome gigantesco para testar se existe validação de tamanho de caracteres com nomes");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("HDMI e VGA");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 100)
	@Test
	public void gerenciarEspacosEditarSalaNomeGrande2() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de aula teste nome gigantesco para testar se existe validação de tamanho de caractere com nomes");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("HDMI e VGA");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - CAPACIDADE GRANDE (TAMANHO 11)
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeGrande() {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de aula teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("23516483987");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("HDMI e VGA");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}
	
	// CASO FRACASSO - CAPACIDADE DE TAMANHO GRANDE (10)
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeGrande2() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de aula teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("1999999910");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("HDMI e VGA");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - INFORMAÇÕES COM TAMANHO GRANDE (201)
	@Test
	public void gerenciarEspacosEditarSalaInformacoesGrande() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala Teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac interdum lacus, et egestas urna. Aenean vitae nulla sodales, ultrices nulla at, dignissim eros. Ut a ipsum nbh. Fusce n consequat");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		Thread.sleep(3000);
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - INFORMAÇÕES COM TAMANHO GRANDE (200) - VALOR LIMITE
	@Test
	public void gerenciarEspacosEditarSalaInformacoesGrande2() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala Teste");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac interdum lacus, et egestas urna. Aenean vitae nulla sodales, ultrices nulla at, dignissim eros. Ut a ipsum nh. Fusce n consequat");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		Thread.sleep(3000);
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - ATRIBUTO NOME PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosEditarSalaNomePequeno() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("S");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - ATRIBUTO CAPACIDADE ZERO
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeZero() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de Aula Zero Capacidade");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("0");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi e vga");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - ATRIBUTO CAPACIDADE PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosEditarSalaCapacidadePequeno() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de Aula");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("1");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi e vga");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	
	// CASO DE FRACASSO - ATRIBUTO INFORMACOES PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosEditarSalaInformacoesPequeno() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de Aula");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("I");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosEditarSalaNomeEspecial() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("¨$¨$%#$");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("hdmi");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO FRACASSO - CAPACIDADE COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeEspecial() {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("*");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Informações");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}	
	
	// CASO DE FRACASSO - INFORMACOES COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosEditarSalaInformacoesEspecial() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de Aula");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("¨%$#$%$");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosEditarSalaNomeApenasNumero() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("123456");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Informações");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - INFORMAÇÕES COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosEditarSalaInformacoesApenasNumero() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala de Aula");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("226");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - CAPACIDADE APENAS LETRAS
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeLetras() {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("abc");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Informacoes");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - CAPACIDADE COM LETRAS E NÚMEROS
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeLetrasNumeros() {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("123abc");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Informacoes");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Sala atualizada com sucesso!", mensagem.getText());
		// O FORMULÁRIO FICA VERMELHO PORÉM NÃO INFORMA NADA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/edit_sala.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - CAPACIDADE NEGATIVA
	@Test
	public void gerenciarEspacosEditarSalaCapacidadeNegativa() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Sala");
		driver.findElement(By.id("j_idt42:capacidade")).click();
		driver.findElement(By.id("j_idt42:capacidade")).clear();
		driver.findElement(By.id("j_idt42:capacidade")).sendKeys("-12");
		driver.findElement(By.id("j_idt42:informacoes")).click();
		driver.findElement(By.id("j_idt42:informacoes")).clear();
		driver.findElement(By.id("j_idt42:informacoes")).sendKeys("Informacoes");
		driver.findElement(By.id("j_idt42:status")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:status"));
			dropdown.findElement(By.xpath("//option[. = 'DISPONIVEL']")).click();
		}

		driver.findElement(By.id("j_idt42:bloco_list")).click();
		{
			WebElement dropdown = driver.findElement(By.id("j_idt42:bloco_list"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 1 - Central de Aulas I']")).click();
		}

		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}
	
	// CASO ALTERNATIVO - SALA COM AS MESMAS EDIÇÕES
	@Test
	public void gerenciarEspacosEditarSalaJaRegistrada() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:j_idt66")).click();

		Thread.sleep(3000);
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala atualizada com sucesso!", mensagem.getText());
	}

	// CASO ALTERNATIVO - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosEditarSalaCancelar() throws InterruptedException {
		driver.findElement(By.id("dt-salas:0:j_idt57")).click();
		driver.findElement(By.id("j_idt42:j_idt64")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/list_salas.jsf", driver.getCurrentUrl());
	}
}
