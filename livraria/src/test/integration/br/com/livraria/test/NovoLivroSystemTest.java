package br.com.livraria.test;

import br.com.livraria.pages.LivroPage;
import br.com.livraria.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class NovoLivroSystemTest {

    private LivroPage livroPage;
    private WebDriver driver;

    @Before
    public void init() {
        driver = new ChromeDriver();
        livroPage = new LoginPage(driver)
                .logar();
    }

    @After
    public void end() {
        driver.close();
    }

    @Test
    public void deveCadastrarUmNovoLivro() {

        boolean livroAdicionado = livroPage
                .cadastrarLivro("TituloeTeste", "1234567892354", 10.0, "Sergio Lopes")
                .validarLivro("TituloeTeste", "123-4-56-789235-4", 10.0, "Sergio Lopes");

        assertTrue(livroAdicionado);

        livroPage.removerLivro("TituloeTeste");
    }

    @Test
    public void deveDeletarUmLivro() {
        livroPage.cadastrarLivro("TituloeTesteRemover", "1234567892354", 10.0, "Sergio Lopes")
                .removerLivro("TituloeTesteRemover");

        boolean livroAdicionado = livroPage
                .existe("TituloeTesteRemover");

        assertFalse(livroAdicionado);
    }

//    @Test
    public void alterarLivro() {

        livroPage.cadastrarLivro("TituloeTeste", "1234567892354", 10.0, "Sergio Lopes");

        livroPage = new LoginPage(driver).logar()
                .alterarLivro("TituloeTeste", "TituloeAlterado", "123-4-56-789235-4", 10.0, "Sergio Lopes");

        boolean livroAdicionado = livroPage
                .validarLivro("TituloeAlterado", "123-4-56-789235-4", 10.0, "Sergio Lopes");

        assertTrue(livroAdicionado);

        livroPage.removerLivro("TituloeAlterado");
    }
}
