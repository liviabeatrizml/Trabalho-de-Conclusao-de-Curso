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

public class GerenciarUsuarioEditarTest {
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

	// CASO DE SUCESSO EDITAR
	@Test
	public void gerenciarUsuarioEditar() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Alterei o nome");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");
		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR COM NOME EM BRANCO
	@Test
	public void gerenciarUsuarioEditarNomeEmBranco() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagemErro = driver.findElement(By.className("ui-growl-item-container"));
		WebElement mensagemNome = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Nome: Campo Obrigatório!')]"));
		
		assertEquals("Nome: Campo Obrigatório!", mensagemNome.getText());
	}

	// CASO DE FRACASSO - EDITAR COM SENHA EM BRANCO
	@Test
	public void gerenciarUsuarioEditarSenhaEmBranco() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Livia_Geísa");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagemErro = driver.findElement(By.className("ui-growl-item-container"));
		WebElement mensagemSenha = mensagemErro.findElement(By.xpath("//span[contains(text(), 'Senha: Campo Obrigatório!')]"));

		assertEquals("Senha: Campo Obrigatório!", mensagemSenha.getText());
	}

	// CASO DE FRACASSO - EDITAR COM INFORMAÇÕES REPETIDAS
	@Test
	public void gerenciarUsuarioEditarComInformacoesRepetidas() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Livia");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR NOME GRANDE (TAMANHO 101)
	@Test
	public void gerenciarUsuarioEditarNomeGrande() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Semper faucibus risus, et tincidunt turpis porta imperdiet. In congue neque a fringilla suscipit corp");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR SENHA GRANDE (TAMANHO 101)
	@Test
	public void gerenciarUsuarioEditarSenhaGrande() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Lívia_Geísa");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("Maecenas dapibus augue eget ante pretium, at mollis justo elementum. Sed cursus euismod maximus termo");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR NOME PEQUENO (TAMAHO 1)
	@Test
	public void gerenciarUsuarioEditarNomePequeno() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("L");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO FRACASSO - EDITAR SENHA PEQUENA (TAMANHO 1)
	@Test
	public void gerenciarUsuarioEditarSenhaPequena() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Geísa_Lívia");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("t");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - NOME COM CARACTERE ESPECIAL
	@Test
	public void gerenciarUsuarioEditarNomeEspecial() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("*@3");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("t");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - SENHA COM CARACTERE ESPECIAL
	@Test
	public void gerenciarUsuarioEditarSenhaEspecial() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Lívia_e_Geísa");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("%$¨%$");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR COM NOME APENAS NÚMEROS
	@Test
	public void gerenciarUsuarioEditarNomeApenasNumero() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("123456");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("teste");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}
	
	// CASO DE FRACASSO - EDITAR COM NOME APENAS NÚMEROS
	@Test
	public void gerenciarUsuarioEditarSenhaApenasNumero() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Livia_e_Geísa");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("123456");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}

	// CASO DE FRACASSO - EDITAR USUÁRIO COM STATUS INATIVO
	@Test
	public void gerenciarUsuarioEditarStatusInativo() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:2:j_idt55")).click();
		driver.findElement(By.id("j_idt42:nome")).click();
		driver.findElement(By.id("j_idt42:nome")).clear();
		driver.findElement(By.id("j_idt42:nome")).sendKeys("Joana");
		driver.findElement(By.id("j_idt42:senha")).click();
		driver.findElement(By.id("j_idt42:senha")).sendKeys("relogio");

		driver.findElement(By.cssSelector("#j_idt42\\3Aj_idt58 > .ui-button-text")).click();

		Thread.sleep(5000);

		WebElement mensagem = driver.findElement(By.cssSelector(".ui-growl-message p"));
		assertEquals("Usuário atualizado com sucesso!", mensagem.getText());
	}
	
	// CASO ALTERNATIVO - BOTÃO CANCELAR EDITAR
	@Test
	public void gerenciarUsuarioEditarCancelar() throws InterruptedException {
		driver.findElement(By.id("dt-usuarios:0:j_idt55")).click();
		driver.findElement(By.id("j_idt42:j_idt56")).click();

		Thread.sleep(5000);

		assertEquals("http://localhost:8080/Sistema_Reserva_de_Salas/views/admin/list_usuarios.jsf", driver.getCurrentUrl());
	}
}
