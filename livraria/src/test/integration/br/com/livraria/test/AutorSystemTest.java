package br.com.livraria.test;

import br.com.livraria.pages.AutorPage;
import br.com.livraria.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;

public class AutorSystemTest {

    private WebDriver driver;
    private AutorPage autorPage;

    @Before
    public void init() {
        driver = new ChromeDriver();
        autorPage = new LoginPage(driver)
                .logar()
                .navegarAutores();
    }

    @After
    public void end() {
        driver.close();
    }

    @Test
    public void deveAdicionarUmAutor() {
        autorPage.cadastrarAutor("TesteAutorNovo", "teste@teste.com");

        boolean adicionado = autorPage.existe("TesteAutorNovo");
        assertTrue(adicionado);

        autorPage.remover("TesteAutorNovo");
    }

}
