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

public class CadastrarReservaEmLoteTest {
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
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();

		Thread.sleep(1000);

		{
			WebElement element = driver.findElement(By.linkText("Reservas"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}

		driver.findElement(By.linkText("Reserva em Lote")).click();
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
	public void cadastrarReservaEmLote() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");

		// UTILIZAÇÃO DA AÇÃO DE MOVER O CURSOR DO MOUSE UM POUCO PARA CONTINUAR O TESTE
		// 10 PIXELS HORIZONTAL E VERTICAL
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - DATA INICIAL EM BRANCO
	@Test
	public void cadastrarReservaEmLoteEmBrancoDataInicial() throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Data Inicial: Campo Obrigatório!", mensagem.getText());
	}

	// CASO DE FRACASSO - DATA FINAL EM BRANCO
	@Test
	public void cadastrarReservaEmLoteEmBrancoDataFinal() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Data Final: Campo Obrigatório!", mensagem.getText());
	}

	// CASO DE FRACASSO - HORA INICIO EM BRANCO
	@Test
	public void cadastrarReservaEmLoteEmBrancoHoraInicio() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();
		
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Horário Inicial: Campo Obrigatório!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - HORA FINAL EM BRANCO
	@Test
	public void cadastrarReservaEmLoteEmBrancoHoraFinal() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();
		
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Horário Final: Campo Obrigatório!", mensagem.getText());

	}	
	
	// CASO DE FRACASSO - FINALIDADE EM BRANCO
	@Test
	public void cadastrarReservaEmLoteEmBrancoFinalidade() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();
		
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Finalidade: Campo Obrigatório!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - FINALIDADE GRANDE (TAMANHO 201)
	@Test
	public void cadastrarReservaEmLoteFinalidadeGrande() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("21/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("22/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ - PEX1271 - TESTE DE SOFTWARE - GEISA MORAIS GABRIEL - PEX1271 - TESTE DE SOFTWARE - LIVIA BEATRIZ MAIA DE LIMA - PEX1271 - TESTE DE SOFTWARE - GEISA LIVIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - FINALIDADE GRANDE (TAMANHO 200) - VALOR LIMITE
	@Test
	public void cadastrarReservaEmLoteFinalidadeGrandeLimite() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("21/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("22/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ - PEX1271 - TESTE DE SOFTWARE - GEISA MORAIS GABRIEL - PEX1271 - TESTE DE SOFTWARE - LIVIA BEATRIZ MAIA DE LIMA - PEX1271 - TESTE DE SOFTWARE - GEISALIVIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - FINALIDADE PEQUENA (TAMANHO 1)
	@Test
	public void cadastrarReservaEmLoteFinalidadePequena() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("21/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("22/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("P");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - FINALIDADE COM APENAS CARACTERE ESPECIAL
	@Test
	public void cadastrarReservaEmLoteFinalidadeEspecial() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("22/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("27/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("%$#¨@$#");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - FINALIDADE COM APENAS NÚMEROS
	@Test
	public void cadastrarReservaEmLoteFinalidadeNumeros() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("22/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("27/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("20:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("123");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - FORMATO DE DATA INICIAL INCORRETO
	@Test
	public void cadastrarReservaEmLoteFormatoDataInicial() throws InterruptedException {
		WebElement dataInicio = driver.findElement(By.id("formReserva:dataInicio_input"));
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("00/09/2024");
		String valorDataInicial = dataInicio.getAttribute("value");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("28/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("19:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
	    
		assertEquals("Data Inicial: não foi possível reconhecer '" + valorDataInicial + "' como uma data.", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - FORMATO DE DATA FINAL INCORRETO
	@Test
	public void cadastrarReservaEmLoteFormatoDataFinal() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("02/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		WebElement dataFinal = driver.findElement(By.id("formReserva:dataFim_input"));
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("28//09/2024");
		String valorDataFinal = dataFinal.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("19:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
	    
		assertEquals("Data Final: não foi possível reconhecer '" + valorDataFinal + "' como uma data.", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - FORMATO DE HORARIO INICIAL INCORRETO
	@Test
	public void cadastrarReservaEmLoteFormatoHoraInicio() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("08808");
		String valorHoraInicial = horaInicio.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("15:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Horário Inicial: não foi possível reconhecer '" + valorHoraInicial + "' como uma hora.", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - FORMATO DE HORARIO FINAL INCORRETO
	@Test
	public void cadastrarReservaEmLoteFormatoHoraFinal() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("13:55");
		actions.moveByOffset(0, 0).click().build().perform();

		WebElement horaFim = driver.findElement(By.id("formReserva:horaFim_input"));
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("15::45");
		actions.moveByOffset(0, 0).click().build().perform();
		String valorHoraFinal = horaFim.getAttribute("value");

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Horário Final: não foi possível reconhecer '" + valorHoraFinal + "' como uma hora.", mensagensErro.get(0).getText());
	}
	
	// CASO DE FRACASSO - DATA INICIAL COM APENAS LETRAS
	@Test
	public void cadastrarReservaEmLoteFormatoDataInicialLetras() throws InterruptedException {
		WebElement dataInicio = driver.findElement(By.id("formReserva:dataInicio_input"));
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("inicial");
		String valorDataInicial = dataInicio.getAttribute("value");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("20:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Data Inicial: não foi possível reconhecer '" + valorDataInicial + "' como uma data.", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - DATA COM APENAS LETRAS
	@Test
	public void cadastrarReservaEmLoteFormatoDataFinalLetras() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		WebElement dataFinal = driver.findElement(By.id("formReserva:dataFim_input"));
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("final");
		actions.moveByOffset(0, 0).click().build().perform();
		String valorDataFinal = dataFinal.getAttribute("value");

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("20:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));

		assertEquals("Data Final: não foi possível reconhecer '" + valorDataFinal + "' como uma data.", mensagensErro.get(0).getText());
	}	

	// CASO FRACASSO - HORA INICIAL COM APENAS LETRAS
	@Test
	public void cadastrarReservaEmLoteFormatoHoraInicialLetra() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("ABC");
		String valorHoraInicial = horaInicio.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("20:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Horário Inicial: não foi possível reconhecer '" + valorHoraInicial + "' como uma hora.", mensagensErro.get(0).getText());
	}	
	
	// CASO FRACASSO - HORA FINAL COM APENAS LETRAS
	@Test
	public void cadastrarReservaEmLoteFormatoHoraFinalLetra() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:45");
		actions.moveByOffset(0, 0).click().build().perform();

		WebElement horaFim = driver.findElement(By.id("formReserva:horaFim_input"));
		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("def");
		actions.moveByOffset(0, 0).click().build().perform();
		String valorHoraFinal = horaFim.getAttribute("value");

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Horário Final: não foi possível reconhecer '" + valorHoraFinal + "' como uma hora.", mensagensErro.get(0).getText());
	}
	
	// CASO DE FRACASSO - DATA INICIAL NEGATIVA
	@Test
	public void cadastrarReservaEmLoteDataInicialNegativa() throws InterruptedException {
		WebElement dataInicio = driver.findElement(By.id("formReserva:dataInicio_input"));
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("-01/-07/2024");
		String valorDataInicial = dataInicio.getAttribute("value");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("01/07/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:40");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("22:30");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE - Geísa");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Data Inicial: não foi possível reconhecer '" + valorDataInicial + "' como uma data.", mensagensErro.get(0).getText());
	}
	
	// CASO DE FRACASSO - DATA FINAL NEGATIVA
	@Test
	public void cadastrarReservaEmLoteDataFinalNegativa() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/10/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		WebElement dataFim = driver.findElement(By.id("formReserva:dataFim_input"));
		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("01/10/-2024");
		String valorDataFinal = dataFim.getAttribute("value");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:40");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("22:30");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE - Geísa");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagemContainer = driver.findElement(By.id("message_container"));
	    List<WebElement> mensagensErro = mensagemContainer.findElements(By.className("ui-growl-title"));
		
		assertEquals("Data Final: não foi possível reconhecer '" + valorDataFinal + "' como uma data.", mensagensErro.get(0).getText());
	}

	// CASO DE FRACASSO - HORA INICIAL NEGATIVA
	@Test
	public void cadastrarReservaEmLoteHoraInicialNegativa() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("18/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("-9:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("22:30");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();
		
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
		// O SISTEMA FAZ UM CALCULO ALEATÓRIO E REGISTRA EM ALGUM HORÁRIO
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_reserva_lote.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - HORA FINAL NEGATIVA
	@Test
	public void cadastrarReservaEmLoteHoraFinalNegativa() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("29/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("11:-55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();
		
		//WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		//assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
		// O SISTEMA FAZ UM CALCULO ALEATÓRIO E REGISTRA EM ALGUM HORÁRIO
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_reserva_lote.jsf", driver.getCurrentUrl());
	}
	
	// CASO DE FRACASSO - DATA PASSADA
	@Test
	public void cadastrarReservaEmLoteDataPassado() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/07/2023");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("04/07/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:40");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("22:30");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("A data de reserva é menor que a data atual!", mensagem.getText());
	}

	// CASO DE FRACASSO - DATA FINAL MENOR
	@Test
	public void cadastrarReservaEmLoteDataFinalMenor() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("18/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("18:40");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("22:30");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Data final menor que a data inicial!", mensagem.getText());
	}	
	
	// CASO DE FRACASSO - HORARIO FINAL ANTERIOR QUE O INICIAL
	@Test
	public void cadastrarReservaEmLoteHoraPassado() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("01/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys(" 04/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("11:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("08:50");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - MONITORIA Geísa");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		// VERIFICA SE APARECE A MENSAGEM DE ERRO
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertTrue(mensagem.getText().contains("A hora fim da reserva é anterior a hora inicial!"));
	}
	
	// CASO DE FRACASSO - JÁ REGISTRADA
	@Test
	public void cadastrarReservaEmLoteJaRegistrada() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - SALA INDISPONIVEL
	@Test
	public void cadastrarReservaEmLoteSalaIndisponivel() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/10/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/10/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt72 > .ui-button-text")).click();

		Thread.sleep(3000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Todas as reservas foram realizadas com sucesso!", mensagem.getText());
	}
	
	// CASO DE ALTERNATIVO - BOTAO LIMPAR
	@Test
	public void testCadastrarReservaLimpar() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/10/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/10/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.id("formReserva:j_idt54")).click();
		
		Thread.sleep(5000);
		
		WebElement dataInicio = driver.findElement(By.id("formReserva:dataInicio_input"));
		WebElement dataFim = driver.findElement(By.id("formReserva:dataFim_input"));
	    WebElement horaInicio = driver.findElement(By.id("formReserva:horaInicio_input"));
	    WebElement horaFim = driver.findElement(By.id("formReserva:horaFim_input"));
	    WebElement finalidade = driver.findElement(By.id("formReserva:finalidade"));

		assertTrue(dataInicio.getAttribute("value").isEmpty());
		assertTrue(dataFim.getAttribute("value").isEmpty());
     	assertTrue(horaInicio.getAttribute("value").isEmpty());
     	assertTrue(horaFim.getAttribute("value").isEmpty());
    	assertTrue(finalidade.getAttribute("value").isEmpty());
	}	

	// CASO DE ALTERNATIVO - BOTAO CANCELAR
	@Test
	public void testCadastrarReservaCancelar() throws InterruptedException {
		driver.findElement(By.id("formReserva:dataInicio_input")).click();
		driver.findElement(By.id("formReserva:dataInicio_input")).sendKeys("20/09/2024");
		Actions actions = new Actions(driver);
		actions.moveByOffset(10, 10).click().build().perform();

		driver.findElement(By.id("formReserva:dataFim_input")).click();
		driver.findElement(By.id("formReserva:dataFim_input")).sendKeys("21/09/2024");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaInicio_input")).click();
		driver.findElement(By.id("formReserva:horaInicio_input")).sendKeys("07:55");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:horaFim_input")).click();
		driver.findElement(By.id("formReserva:horaFim_input")).sendKeys("09:45");
		actions.moveByOffset(0, 0).click().build().perform();

		driver.findElement(By.id("formReserva:finalidade")).click();
		driver.findElement(By.id("formReserva:finalidade")).sendKeys("PEX1271 - TESTE DE SOFTWARE - ALYSSON MILANEZ");
		driver.findElement(By.cssSelector("#formReserva\\3Aj_idt56 > .ui-button-text")).click();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#dt-salas\\3A 0\\3Aj_idt68 > .ui-button-text")).click();
		driver.findElement(By.cssSelector("#dt-salas\\3A 9\\3Aj_idt71 > .ui-button-text")).click();

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/form_reserva_lote.jsf", driver.getCurrentUrl());
	}		
}