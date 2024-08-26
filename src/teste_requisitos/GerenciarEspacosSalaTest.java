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

public class GerenciarEspacosSalaTest {
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
	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM sala WHERE id >= 63");

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.quit();
	}

	// CASO DE SUCESSO DE CADASTRO DE SALA
	@Test
	public void gerenciarEspacosCadastrarSala() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula Teste 01");
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

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME EM BRANCO
	@Test
	public void gerenciarEspacosCadastrarSalaEmBrancoNome() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Aqui jas informações");

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Nome: Campo Obrigatório!", mensagem.getText());
	}

	// CASO DE FRACASSO - CAPACIDADE EM BRANCO
	@Test
	public void gerenciarEspacosCadastrarSalaEmBrancoCapacidade() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Salinha de Brinquedos");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Aqui jas informações");

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Capacidade: Campo Obrigatório!", mensagem.getText());
	}
	

	// CASO DE FRACASSO - INFORMAÇÕES EM BRANCO
	@Test
	public void gerenciarEspacosCadastrarSalaEmBrancoInformacao() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula Teste 01");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:bloco")).click();

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 101)
	@Test
	public void gerenciarEspacosCadastrarSalaNomeGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste nome gigantesco para testar se existe validação de tamanho de caracteres com nomes");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME GRANDE (TAMANHO 100) - VALOR LIMITE
	@Test
	public void gerenciarEspacosCadastrarSalaNomeGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste nome gigantesco para testar se existe validação de tamanho de caractere com nomes");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO FRACASSO - CAPACIDADE GRANDE (TAMANHO 11)
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste");
		WebElement capacidade = driver.findElement(By.id("j_idt41:capacidade"));
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("23516483987");
		String valorCapacidade = capacidade.getAttribute("value");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.className("ui-growl-title"));
		assertEquals("Capacidade: '"+ valorCapacidade +"' deve ser um número formado por um ou mais dígitos.", mensagem.getText());
	}
	
	// CASO FRACASSO - CAPACIDADE GRANDE (TAMANHO 10) - VALOR LIMITE
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste");
		WebElement capacidade = driver.findElement(By.id("j_idt41:capacidade"));
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("1999999910");
		String valorCapacidade = capacidade.getAttribute("value");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO FRACASSO - INFORMAÇÃO GRANDE (TAMANHO 201)
	@Test
	public void gerenciarEspacosCadastrarSalaInformacoesGrande() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste 02");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("235398");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac interdum lacus, et egestas urna. Aenean vitae nulla sodales, ultrices nulla at, dignissim eros. Ut a ipsum nbh. Fusce n consequat");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO FRACASSO - INFORMAÇÃO GRANDE (TAMANHO 200) - VALOR LIMITE
	@Test
	public void gerenciarEspacosCadastrarSalaInformacoesGrande2() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de aula teste 02");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("235398");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ac interdum lacus, et egestas urna. Aenean vitae nulla sodales, ultrices nulla at, dignissim eros. Ut a ipsum nh. Fusce n consequat");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME PEQUENO (TAMANHO 1)
	@Test
	public void gerenciarEspacosCadastrarSalaNomePequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("S");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("HDMI");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO FRACASSO - CAPACIDADE ZERO (0)
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeZero() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula Zero Capacidade");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("0");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("HDMI");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}

	// CASO FRACASSO - CAPACIDADE TAMANHO (1)
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadePequena() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("1");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("HDMI");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO FRACASSO - INFORMAÇÃO PEQUENA (TAMANHO 1)
	@Test
	public void gerenciarEspacosCadastrarSalaInformacoesPequeno() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula Uma Informação");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("55");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("I");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosCadastrarSalaNomeEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("%¨$¨@#%");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("HDMI");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO FRACASSO - CAPACIDADE COM CARACTERE ESPECIAL
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala");
		WebElement capacidade = driver.findElement(By.id("j_idt41:capacidade"));
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("*");
		String valorCapacidade = capacidade.getAttribute("value");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.className("ui-growl-title"));
		assertEquals("Capacidade: '"+ valorCapacidade +"' deve ser um número formado por um ou mais dígitos.", mensagem.getText());
	}
	
	// CASO FRACASSO - INFORMAÇÃO COM CARACTERES ESPECIAL
	@Test
	public void gerenciarEspacosCadastrarSalaInformacaoEspecial() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("$%#!!!");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - NOME COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosCadastrarSalaNomeNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("123456");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Aqui jas informacões");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();
		
		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO FRACASSO - INFORMAÇÕES COM APENAS NÚMEROS
	@Test
	public void gerenciarEspacosCadastrarSalaInformacoesNumero() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aulah");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("226");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - CAPACIDADE APENAS LETRAS
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeLetras() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aulah");
		WebElement capacidade = driver.findElement(By.id("j_idt41:capacidade"));
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("Abc");
		String valorCapacidade = capacidade.getAttribute("value");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.className("ui-growl-title"));
		assertEquals("Capacidade: '"+ valorCapacidade +"' deve ser um número formado por um ou mais dígitos.", mensagem.getText());
	}
	
	// CASO DE FRACASSO - CAPACIDADE COM LETRAS E NÚMEROS
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeLetrasNumeros() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aulah");
		WebElement capacidade = driver.findElement(By.id("j_idt41:capacidade"));
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("123abc");
		String valorCapacidade = capacidade.getAttribute("value");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.className("ui-growl-title"));
		assertEquals("Capacidade: '"+ valorCapacidade +"' deve ser um número formado por um ou mais dígitos.", mensagem.getText());
	}
	
	// CASO DE FRACASSO - CAPACIDADE NEGATIVA
	@Test
	public void gerenciarEspacosCadastrarSalaCapacidadeNegativa() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aulah");
		driver.findElement(By.id("j_idt41:capacidade")).click();
		driver.findElement(By.id("j_idt41:capacidade")).clear();
		driver.findElement(By.id("j_idt41:capacidade")).sendKeys("-15");
		driver.findElement(By.id("j_idt41:informacoes")).click();
		driver.findElement(By.id("j_idt41:informacoes")).clear();
		driver.findElement(By.id("j_idt41:informacoes")).sendKeys("Sala de Aula equipada com HDMI e VGA");
		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:bloco"));
			dropdown.findElement(By.xpath("//option[. = 'ID: 2 - Central de Aulas II']")).click();
		}

		driver.findElement(By.id("j_idt41:bloco")).click();

		{
			WebElement dropdown = driver.findElement(By.id("j_idt41:tipo"));
			dropdown.findElement(By.xpath("//option[. = 'SALA_AULA']")).click();
		}

		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt63 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - CADASTRAR SALA JÁ REGISTRADA
	@Test
	public void gerenciarEspacosCadastrarSalaJaRegistrada() throws InterruptedException {
		driver.findElement(By.id("j_idt41:nome")).click();
		driver.findElement(By.id("j_idt41:nome")).clear();
		driver.findElement(By.id("j_idt41:nome")).sendKeys("Sala de Aula Teste 01");
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

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Sala cadastrada com sucesso!", mensagem.getText());
	}
	
	// CASO DE ALTERNATIVO - BOTÃO CANCELAR
	@Test
	public void gerenciarEspacosCadastrarSalaCancelar() throws InterruptedException {
		driver.findElement(By.cssSelector("#j_idt41\\3Aj_idt61 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/home.jsf", driver.getCurrentUrl());
	}
}