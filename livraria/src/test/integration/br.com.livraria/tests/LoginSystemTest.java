package br.com.livraria.tests;

import br.com.livraria.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LoginSystemTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void init() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
    }

    @After
    public void end() {
        driver.close();
    }

    @Test
    public void deveValidarUsuarioIncorreto() {

        boolean logado = loginPage.visita()
                .realizarLoginCom("loginErrado", "senhaErrada")
                .validarLoginRealizado();

        assertTrue(loginPage.validacaoDeUsuarioNaoEncontrado());
        assertFalse(logado);

    }

    @Test
    public void deveValidarUsuarioESenhaVazio() {
        boolean logado = loginPage.visita()
                .realizarLoginCom("", "")
                .validarLoginRealizado();
        assertFalse(logado);
        assertTrue(loginPage.validacaoDeEmailObrigatorio());
        assertTrue(loginPage.validacaoDeSenhaObrigatorio());
    }


    @Test
    public void deveEntrarComUsuarioValido() {

        boolean logado = loginPage.visita()
                .realizarLoginCom("admin@admin.com", "admin")
                .validarLoginRealizado();

        assertTrue(logado);
    }
}
