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

public class ExcluirReservaEmLoteTest {
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
            		+ "(1, 2, '2024-09-20', '07:55:00', '09:45:00', 'Reunião de equipe', 1, 1, '2024-09-20', '2024-09-21', 1),"
            		+ "(1, 3, '2024-07-02', '07:55:00', '09:45:00', 'Reunião de equipe', 1, 1, '2024-07-02', '2024-07-03', 1),"
            		+ "(1, 4, '2024-08-12', '11:55:00', '15:45:00', 'Reunião de equipe', 1, 1, '2024-08-12', '2024-08-21', 1);");
            
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

		driver.findElement(By.linkText("Listar Lotes")).click();

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
	public void excluirReservaEmLote() throws InterruptedException {		
		driver.findElement(By.id("dt-reservas:0:j_idt61")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("dt-reservas:2:j_idt64")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Lote de reservas excluído com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EXCLUIR LOTE JÁ REALIZADO
	@Test
	public void excluirReservaEmLoteJaRealizada() throws InterruptedException {        
		driver.findElement(By.id("dt-reservas:1:j_idt61")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("dt-reservas:2:j_idt64")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Lote de reservas excluído com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EXCLUIR LOTE EM ANDAMENTO
	@Test
	public void excluirReservaEmLoteEmAndamento() throws InterruptedException {
		driver.findElement(By.id("dt-reservas:2:j_idt61")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("dt-reservas:2:j_idt64")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Lote de reservas excluído com sucesso!", mensagem.getText());
	}
	
	// CASO ALTERNTIVO - BOTÃO CANCELAR
	@Test
	public void excluirReservaEmLoteBotaoCancelar() throws InterruptedException {
		driver.findElement(By.id("dt-reservas:2:j_idt61")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("dt-reservas:2:j_idt63")).click();
		Thread.sleep(3000);

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/list_lotes.jsf", driver.getCurrentUrl());
	}
}
