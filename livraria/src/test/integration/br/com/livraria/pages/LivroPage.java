package br.com.livraria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LivroPage {

    private WebDriver driver;

    public LivroPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean validarLoginRealizado() {
        //valida se esta logado
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        return driver.getPageSource().contains("Novo Livro");
    }

    public LivroPage cadastrarLivro(String nome, String isbn, double valor, String autor) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement txtNome = driver.findElement(By.name("formLivro:titulo"));
        WebElement txtIsbn = driver.findElement(By.name("formLivro:isbn"));
        WebElement txtValor = driver.findElement(By.name("formLivro:preco"));
        Select cbAutores = new Select(driver.findElement(By.id("formLivro:autor_select_input")));
        WebElement btnAddAutor = driver.findElement(By.id("formLivro:btnGravarAutor"));
        WebElement btnAdicionarLivro = driver.findElement(By.id("formLivro:btnGravarLivro"));

        txtNome.clear();
        txtNome.sendKeys(nome);
        txtIsbn.clear();
        txtIsbn.sendKeys(isbn);
        txtValor.clear();
        txtValor.sendKeys(String.valueOf(valor));
        cbAutores.selectByVisibleText(autor);
        btnAddAutor.click();
        btnAdicionarLivro.click();

        return this;
    }

    public boolean validarLivro(String nome, String isbn, double valor, String autor) {
        int linha = this.procurarLinhaPeloNome(nome);
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"formTabelaLivros:tabelaLivros_data\"]/tr[" + linha + "]/td[1]"), nome));

        return driver.getPageSource().contains(isbn);
    }

    public boolean existe(String nome) {
        return driver.getPageSource().contentEquals(nome);
    }

    public LivroPage removerLivro(String nome) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"formTabelaLivros:tabelaLivros_data\"]/tr[1]/td[1]"), nome));

        int linha = this.procurarLinhaPeloNome(nome);
        WebElement btnRemover = driver.findElement(By.id("formTabelaLivros:tabelaLivros:" + linha + ":btnDeletar"));
        btnRemover.click();
        return this;

    }

    public LivroPage selecionarLivro(String nome) {


        return this;
    }

    public LivroPage alterarLivro(String antigo, String nome, String isbn, double valor, String autor) {

        int linha = this.procurarLinhaPeloNome(antigo);
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"formTabelaLivros:tabelaLivros_data\"]/tr[" + linha + "]/td[1]"), antigo));

        WebElement btnAlterar = driver.findElement(By.id("formTabelaLivros:tabelaLivros:" + linha + ":btnAlterar"));
        btnAlterar.click();

//        new WebDriverWait(driver, 10)
//                .until(ExpectedConditions
//                        .textToBePresentInElement(By.id("formLivro:titulo"), antigo));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement txtNome = driver.findElement(By.name("formLivro:titulo"));
        WebElement txtIsbn = driver.findElement(By.name("formLivro:isbn"));
        WebElement txtValor = driver.findElement(By.name("formLivro:preco"));
        Select cbAutores = new Select(driver.findElement(By.id("formLivro:autor_select_input")));
        WebElement btnAddAutor = driver.findElement(By.id("formLivro:btnGravarAutor"));
        WebElement btnAdicionarLivro = driver.findElement(By.id("formLivro:btnGravarLivro"));

        txtNome.clear();
        txtNome.sendKeys(nome);
        txtIsbn.clear();
        txtIsbn.sendKeys(isbn);
        txtValor.clear();
        txtValor.sendKeys(String.valueOf(valor));

        btnAdicionarLivro.click();
        return this;
    }

    private int procurarLinhaPeloNome(String nome) {
        int id = -1;
        WebElement tabelaLinhas = driver.findElement(By.id("formTabelaLivros:tabelaLivros_data"));
        List<WebElement> tr = tabelaLinhas.findElements(By.cssSelector("tr"));
        for (int i = 0; i < tr.size(); i++) {
            WebElement t = tr.get(i);
            String texto = t.getText();
            if (texto.contains(nome)) {
                id = i + 1;
                break;
            }
        }
        return id;
    }

    public AutorPage navegarAutores() {

        WebElement btnMenu = driver.findElement(By.id("menuForm:btnMenu_button"));
        WebElement btnAutores = driver.findElement(By.id("menuForm:btnAutores"));
        btnMenu.click();
        btnAutores.click();
        return new AutorPage(driver);
    }
}
