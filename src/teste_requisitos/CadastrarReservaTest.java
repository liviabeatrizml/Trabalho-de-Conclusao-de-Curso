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
import java.sql.Statement;

public class CadastrarReservaTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() throws InterruptedException {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
       
        try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

            stmt.executeUpdate("INSERT INTO usuario (id, nome, email, login, matricula, role, senha, status, codigoAtivacao, dataCadastro)"
            		+ "VALUES"
            		+ "(1, 'usuario_admin', 'usuario_admin@ufersa.edu.br', 'usuario_admin', 10, 1, '$2a$10$UMnwfEEo8Z/Jg3B6wM6I6uLdKdjFqwxsv6OIMh0OHIXSI/N1PWorK', 'ativo', 'WSPWKR0H3Z6N7U4', '2024-05-18 00:35:36'),"
            		+ "(2, 'usuario_teste', 'teste@ufersa.edu.br', 'usuario_teste', 20, 2, '$2a$10$I6IQav2/ugV.XvZl/tryn.IzEOUYV4lT9FRh5VR7sV106isU2PiQ6', 'inativo', 'QX5OTJB2HBCUMSO', '2024-05-18 12:56:36'),"
            		+ "(3, 'usuario_padrao', 'usuario_padrao@ufersa.edu.br', 'usuario_padrao', 30, 2, '$2a$10$nNI3CFoQU.PHoabmphUFB.h3rJC8GCll6WjnhyL0d1Nphrn3nRNwq', 'ativo', 'IQ8S8NQAWNHD4S0', '2024-05-18 00:37:57'),"
            		+ "(4, 'usuario_inativo', 'inativo@ufersa.edu.br', 'usuario_inativo', 40, 2, '$2a$10$75ckCTV8Qa/dPYEuKu0LbOs.zWWCh1m8N3olvOJ.5xup6cuR5Jdu.', 'inativo', 'M0Y8IK9TYIHF78T', '2024-05-18 00:38:49'),"
            		+ "(5, 'Livia e Geisa', 'livia_geisa@ufersa.edu.br', 'livia_geisa', 50, 1, '$2a$10$IxASABo0ZTG0GpTiTmG.BuIvBuXlXg4vjsqR6cPBtsfIjTYX33KXa', 'ativo', '22TOPNEXIIT1HU5', '2024-05-18 00:40:18');");
            
            stmt2.executeUpdate("INSERT INTO reserva (idUsuario, idSala, dataReserva, horaInicio, horaFim, finalidade, idPool, idAdmin, dataInicioPool, dataFimPool, passoPool)"
            		+ "VALUES"
            		+ "(1, 2, '2024-09-20', '07:55:00', '09:45:00', 'Reunião de equipe', 1, 1, '2024-09-20', '2024-09-21', 1),"
            		+ "(2, 3, '2024-09-21', '10:00:00', '12:00:00', 'Treinamento de software', 2, 1, '2024-09-21', '2024-09-22', 1),"
            		+ "(3, 4, '2024-09-22', '14:00:00', '16:00:00', 'Planejamento estratégico', 3, 1, '2024-09-22', '2024-09-23', 1);");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
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

		driver.findElement(By.linkText("Nova Reserva")).click();
	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
       
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

            stmt.executeUpdate("DELETE FROM reserva WHERE id > 0");
            stmt2.executeUpdate("DELETE FROM usuario WHERE id > 0");

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.quit();
	}

	// CASO DE SUCESSO
	@Test
	public void testCadastrarReserva() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("16/07/2025");

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
		
		Thread.sleep(2000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();
		
		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - ATRIBUTOS EM BRANCO
	@Test
	public void testCadastrarReservaEmBranco() throws InterruptedException {
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		Thread.sleep(2000);
		
		WebElement mensagemErro = driver.findElement(By.id("message_container"));
		
		WebElement mensagemData = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Data: Campo Obrigatório!')]"));
		WebElement mensagemHoraInicio = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Horário Inicial: Campo Obrigatório!')]"));
		WebElement mensagemHoraFim = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Horário Final: Campo Obrigatório!')]"));
		WebElement mensagemFinalidade = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Finalidade: Campo Obrigatório!')]"));

		assertEquals("Data: Campo Obrigatório!", mensagemData.getText());
    	assertEquals("Horário Inicial: Campo Obrigatório!", mensagemHoraInicio.getText());
    	assertEquals("Horário Final: Campo Obrigatório!", mensagemHoraFim.getText());
    	assertEquals("Finalidade: Campo Obrigatório!", mensagemFinalidade.getText()); 
	}

	// CASO DE FRACASSO - DATA EM BRANCO
	@Test
	public void testCadastrarReservaDataEmBranco() throws InterruptedException {
		Actions actions = new Actions(driver);

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		Thread.sleep(2000);
		
		WebElement mensagemErro = driver.findElement(By.id("message_container"));
		WebElement mensagemData = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Data: Campo Obrigatório!')]"));
		assertEquals("Data: Campo Obrigatório!", mensagemData.getText());
	}

	// CASO DE FRACASSO - HORÁRIO EM BRANCO
	@Test
	public void testCadastrarReservaHorarioEmBranco() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("16/07/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		Thread.sleep(2000);
		
		WebElement mensagemErro = driver.findElement(By.id("message_container"));
		
		WebElement mensagemHoraInicio = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Horário Inicial: Campo Obrigatório!')]"));
		WebElement mensagemHoraFim = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Horário Final: Campo Obrigatório!')]"));
     	assertEquals("Horário Inicial: Campo Obrigatório!", mensagemHoraInicio.getText());
    	assertEquals("Horário Final: Campo Obrigatório!", mensagemHoraFim.getText());
	}

	// CASO DE FRACASSO - FINALIDADE EM BRANCO
	@Test
	public void testCadastrarReservaFinalidadeEmBranco() throws InterruptedException {
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
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();
		Thread.sleep(2000);
		
		WebElement mensagemErro = driver.findElement(By.id("message_container"));
		WebElement mensagemFinalidade = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Finalidade: Campo Obrigatório!')]"));
     	assertEquals("Finalidade: Campo Obrigatório!", mensagemFinalidade.getText()); 
	}
	
	// CASO DE FRACASSO - FINALIDADE GRANDE (TAMANHO 300)
	@Test
	public void testCadastrarReservaComFinalidadeGrande() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("21/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ - PEX1271 - TESTE DE SOFTWARE - GEISA MORAIS GABRIEL - PEX1271 - TESTE DE SOFTWARE - LIVIA BEATRIZ MAIA DE LIMA - PEX1271 - TESTE DE SOFTWARE - GEISA LIVIA - PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ - LIVIA - GEISA - MIRAIS - MAIA - GABRIEL - ALYSSO");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();

		Thread.sleep(10000);
		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - FINALIDADE GRANDE (TAMANHO 200)
	@Test
	public void testCadastrarReservaComFinalidadeGrande2() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("21/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ - PEX1271 - TESTE DE SOFTWARE - GEISA MORAIS GABRIEL - PEX1271 - TESTE DE SOFTWARE - LIVIA BEATRIZ MAIA DE LIMA - PEX1271 - TESTE DE SOFTWARE - GEISA DIAS");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();

		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - FINALIDADE PEQUENA (TAMANHO 1)
	@Test
	public void testCadastrarReservaComFinalidadePequena() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("22/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("P");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();

		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - FINALIDADE COM CARACTERE ESPECIAL
	@Test
	public void testCadastrarReservaComFinalidadeEspecial() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("23/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("%$#¨@$#");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();

		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - FINALIDADE COM APENAS NÚMEROS
	@Test
	public void testCadastrarReservaComFinalidadeNumeros() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("25/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("123");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();

		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);	
	}

	// CASO DE FRACASSO - FORMATO DATA INCORRETO
	@Test
	public void testCadastrarReservaFormatoData() throws InterruptedException {
		WebElement data = driver.findElement(By.id("formReserva:data_input"));
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("28//2024");
		String valorData = data.getAttribute("value");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(2000);
		
	    WebElement mensagem = driver.findElement(By.xpath("//div[@role='alert']//div[@class='ui-growl-message']"));
	    assertEquals("Data: não foi possível reconhecer '" + valorData + "' como uma data.", mensagem.findElement(By.className("ui-growl-title")).getText());
	}
	// CASO DE FRACASSO - FORMATO HORA INCORRETO
	@Test
	public void testCadastrarReservaFormatoHora() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("28/10/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
		WebElement horaFim = driver.findElement(By.id("formReserva:horaFim_input"));
		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("0855");
		String valorHoraInicial = horaInicio.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11::45");
		String valorHoraFinal = horaFim.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.id("message_container"));
	    
	    List<WebElement> mensagensErro = mensagem.findElements(By.xpath("//div[@role='alert']"));
	    assertEquals("Horário Inicial: não foi possível reconhecer '" + valorHoraInicial +"' como uma hora.", mensagensErro.get(0).findElement(By.xpath(".//div[@class='ui-growl-message']/span[@class='ui-growl-title']")).getText());
	    assertEquals("Horário Final: não foi possível reconhecer '"+ valorHoraFinal +"' como uma hora.", mensagensErro.get(1).findElement(By.xpath(".//div[@class='ui-growl-message']/span[@class='ui-growl-title']")).getText());
	}

	// CASO DE FRACASSO - FORMATO DATA COM APENAS LETRAS
	@Test
	public void testCadastrarReservaDataLetra() throws InterruptedException {
		WebElement data = driver.findElement(By.id("formReserva:data_input"));
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("data");
		String valorData = data.getAttribute("value");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(2000);
		
	    WebElement mensagem = driver.findElement(By.xpath("//div[@role='alert']//div[@class='ui-growl-message']"));
	    assertEquals("Data: não foi possível reconhecer '" + valorData + "' como uma data.", mensagem.findElement(By.className("ui-growl-title")).getText());
	}

	// CASO DE FRACASSO - FORMATO HORA COM APENAS LETRAS
	@Test
	public void testCadastrarReservaHoraLetra() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("01/09/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();
		
		WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("abc");
		String valorHoraInicial = horaInicio.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();
		WebElement horaFim = driver.findElement(By.id("formReserva:horaInicio_input"));
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("abc");
		String valorHoraFinal = horaFim.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(2000);
		
		WebElement mensagem = driver.findElement(By.id("message_container"));
	    
	    List<WebElement> mensagensErro = mensagem.findElements(By.xpath("//div[@role='alert']"));
	    assertEquals("Horário Inicial: não foi possível reconhecer '" + valorHoraInicial +"' como uma hora.", mensagensErro.get(0).findElement(By.xpath(".//div[@class='ui-growl-message']/span[@class='ui-growl-title']")).getText());
	    assertEquals("Horário Final: não foi possível reconhecer '"+ valorHoraFinal +"' como uma hora.", mensagensErro.get(1).findElement(By.xpath(".//div[@class='ui-growl-message']/span[@class='ui-growl-title']")).getText());
	}

	// CASO DE FRACASSO - DATA NEGATIVA
	@Test
	public void testCadastrarReservaDataNegativa() throws InterruptedException {
		WebElement data = driver.findElement(By.id("formReserva:data_input"));
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("-01/-07/2024");
		String valorData = data.getAttribute("value");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(2000);
		
	    WebElement mensagem = driver.findElement(By.xpath("//div[@role='alert']//div[@class='ui-growl-message']"));
	    assertEquals("Data: não foi possível reconhecer '" + valorData + "' como uma data.", mensagem.findElement(By.className("ui-growl-title")).getText());
	}

	// CASO DE FRACASSO - HORA NEGATIVA
	@Test
	public void testCadastrarReservaHoraNegativa() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("01/07/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:-55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("-11:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE - LÍVIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();
		
		Thread.sleep(10000);
		
		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);
	}

	// CASO DE FRACASSO - DATA ANTERIOR A ATUAL
	@Test
	public void testCadastrarReservaDataPassado() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("01/07/2023");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemErro = driver.findElement(By.xpath("//div[@role='alert']"));
		assertEquals("A data de reserva é menor que a data atual!", mensagemErro.findElement(By.xpath(".//span[@class='ui-growl-title']")).getText());
	}

	// CASO DE FRACASSO - HORA INICIAL ANTERIOR QUE A ATUAL
	@Test
	public void testCadastrarReservaHoraInicialPassado() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("12/08/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("11:35");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("15:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA Geísa");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagemErro = driver.findElement(By.xpath("//div[@role='alert']"));
		assertEquals("A hora de início da reserva é anterior a hora atual!", mensagemErro.findElement(By.xpath(".//div[@class='ui-growl-message']//p")).getText());
	}

	// CASO DE FRACASSO - HORA FINAL ANTERIOR QUE A INICIAL
	@Test
	public void testCadastrarReservaHoraPassado() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("01/10/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("08:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA Geísa");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemErro = driver.findElement(By.xpath("//div[@role='alert']"));
		assertEquals("A hora fim da reserva é anterior a hora inicial!", mensagemErro.findElement(By.xpath(".//div[@class='ui-growl-message']//p")).getText());
	}

	// CASO DE FRACASSO - RESERVA DUPLICADA
	@Test
	public void testCadastrarReservaExistente() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("16/10/2024");

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
	}

	// CASO DE FRACASSO - SALA INDISPONÍVEL
	@Test
	public void testCadastrarReservaSalaIndisponivel() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("02/08/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("19:35");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(5000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt70 > .ui-button-text")).click();
		
		Thread.sleep(10000);

		WebElement mensagemAlerta = driver.findElement(By.xpath("//div[@role='alert']"));
		String textoMensagem = mensagemAlerta.findElement(By.className("ui-growl-message")).getText();
		assertEquals("Sala reservada!\nReserva cadastrada com sucesso!", textoMensagem);
	}
	
	// CASO DE ALTERNATIVO - BOTAO LIMPAR
	@Test
	public void testCadastrarReservaLimpar() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("02/08/2024");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();
		
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("19:35");
		actions.moveByOffset(0, 0).click().build().perform();
		
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.id("formReserva:j_idt52")).click();
		
		Thread.sleep(5000);
		
		WebElement data = driver.findElement(By.id("formReserva:data_input"));
	    WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
	    WebElement horaFim = driver.findElement(By.id("formReserva:horaFim_input"));
	    WebElement finalidade = driver.findElement(By.id("formReserva:finalidade"));
		
		assertTrue(data.getAttribute("value").isEmpty());
        assertTrue(horaInicio.getAttribute("value").isEmpty());
        assertTrue(horaFim.getAttribute("value").isEmpty());
        assertTrue(finalidade.getAttribute("value").isEmpty());
	}
	
	// CASO DE ALTERNATIVO - BOTAO CANCELAR
	@Test
	public void testCadastrarReservaCancelar() throws InterruptedException {
		driver.findElement(By.id("formReserva:data_input")).click();
		driver.findElement(By.id("formReserva:data_input")).sendKeys("02/08/2025");

		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("19:35");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt54 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 1\\3Aj_idt66 > .ui-button-text")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt69 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/form_reserva.jsf", driver.getCurrentUrl());
	}
}