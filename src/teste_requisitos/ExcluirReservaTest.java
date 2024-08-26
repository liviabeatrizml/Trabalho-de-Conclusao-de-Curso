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

public class ExcluirReservaTest {
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
       
        try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();
            
            stmt.executeUpdate("INSERT INTO reserva (idUsuario, idSala, dataReserva, horaInicio, horaFim, finalidade, idPool, idAdmin, dataInicioPool, dataFimPool, passoPool)"
            		+ "VALUES"
            		+ "(5, 2, '2025-09-21', '07:55:00', '09:45:00', 'Reunião de equipe', 1, 1, '2024-09-21', '2024-09-21', 1),"
            		+ "(5, 3, '2025-09-21', '07:55:00', '09:45:00', 'Reunião de equipe', 1, 1, '2024-09-21', '2024-09-21', 1),"
            		+ "(5, 4, '2024-08-12', '11:55:00', '12:00:00', 'Reunião de equipe', 1, 1, '2024-08-12', '2024-08-12', 1);");
            
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
		
		driver.findElement(By.linkText("Minhas Reservas")).click();
		Thread.sleep(2000);
	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM reserva WHERE id > 0");

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.quit();
	}

	// CASO DE SUCESSO
	@Test
	public void testexcluirReserva() throws InterruptedException {
		driver.findElement(By.id("dt-reservas:1:j_idt60")).click();
		driver.findElement(By.id("dt-reservas:2:j_idt63")).click();
		Thread.sleep(1000);
		
		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Reserva deletada com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - EXCLUIR RESERVA JÁ REALIZADA / EM ANDAMENTO 
	@Test
	public void testexcluirReservaJaRealizada() throws InterruptedException {

		driver.findElement(By.id("dt-reservas:2:j_idt60")).click();
		driver.findElement(By.id("dt-reservas:2:j_idt63")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertTrue(mensagem.getText().contains("Não é possível apagar esta reserva!"));
	}
	
	// CASO DE ALTENATIVO - BOTÃO CANCELAR
	@Test
	public void testexcluirReservaCancelar() throws InterruptedException {

		driver.findElement(By.id("dt-reservas:2:j_idt60")).click();
		driver.findElement(By.id("dt-reservas:2:j_idt62")).click();

		Thread.sleep(2000);
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/list_reservas.jsf", driver.getCurrentUrl());
	}	
}
