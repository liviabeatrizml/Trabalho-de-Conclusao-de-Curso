package test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import app.dao.GerenciadorConexao;

import org.openqa.selenium.JavascriptExecutor;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

public class LogoutTest {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		
		Connection conn = null;
        Statement stmt = null;
       
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO usuario (id, nome, email, login, matricula, role, senha, status, codigoAtivacao, dataCadastro)"
            		+ "VALUES"
            		+ "(1, 'usuario_admin', 'usuario_admin@ufersa.edu.br', 'usuario_admin', 10, 1, '$2a$10$UMnwfEEo8Z/Jg3B6wM6I6uLdKdjFqwxsv6OIMh0OHIXSI/N1PWorK', 'ativo', 'WSPWKR0H3Z6N7U4', '2024-05-18 00:35:36'),"
            		+ "(2, 'usuario_teste', 'teste@ufersa.edu.br', 'usuario_teste', 20, 2, '$2a$10$I6IQav2/ugV.XvZl/tryn.IzEOUYV4lT9FRh5VR7sV106isU2PiQ6', 'inativo', 'QX5OTJB2HBCUMSO', '2024-05-18 12:56:36'),"
            		+ "(3, 'usuario_padrao', 'usuario_padrao@ufersa.edu.br', 'usuario_padrao', 30, 2, '$2a$10$nNI3CFoQU.PHoabmphUFB.h3rJC8GCll6WjnhyL0d1Nphrn3nRNwq', 'ativo', 'IQ8S8NQAWNHD4S0', '2024-05-18 00:37:57'),"
            		+ "(4, 'usuario_inativo', 'inativo@ufersa.edu.br', 'usuario_inativo', 40, 2, '$2a$10$75ckCTV8Qa/dPYEuKu0LbOs.zWWCh1m8N3olvOJ.5xup6cuR5Jdu.', 'inativo', 'M0Y8IK9TYIHF78T', '2024-05-18 00:38:49'),"
            		+ "(5, 'Livia e Geisa', 'livia_geisa@ufersa.edu.br', 'livia_geisa', 50, 1, '$2a$10$IxASABo0ZTG0GpTiTmG.BuIvBuXlXg4vjsqR6cPBtsfIjTYX33KXa', 'ativo', '22TOPNEXIIT1HU5', '2024-05-18 00:40:18');");
        } catch (Exception e) {
            e.printStackTrace();
        }

		// ENTRAR NO SISTEMA
		driver.get("http://localhost:8080/Sistema_Reserva_de_Salas/");
		
	}

	@After
	public void tearDown() {
		Connection conn = null;
        Statement stmt = null;
       
		try {
            conn = GerenciadorConexao.getConexao();
            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM usuario WHERE id > 0");

        } catch (Exception e) {
            e.printStackTrace();
        }
		
		driver.quit();
	}

	// CASO DE SUCESSO
	@Test
	public void realizarLogout() throws InterruptedException {
		driver.findElement(By.id("j_idt13:login")).click();
		driver.findElement(By.id("j_idt13:login")).sendKeys("usuario_admin");
		driver.findElement(By.id("j_idt13:senha")).click();
		driver.findElement(By.id("j_idt13:senha")).sendKeys("admin");
		driver.findElement(By.cssSelector(".ui-button-text")).click();
		
		driver.findElement(By.linkText("Sair")).click();
		Thread.sleep(5000);
		
		// USUÁRIO SAIU DO SISTEMA
		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/index.jsf", driver.getCurrentUrl());
	}
}
