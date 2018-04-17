package br.com.alura.livraria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        WebElement txtNome = driver.findElement(By.xpath("//*[@id=\"formLivro:titulo\"]"));
        WebElement txtIsbn = driver.findElement(By.xpath("//*[@id=\"formLivro:isbn\"]"));
        WebElement txtValor = driver.findElement(By.xpath("//*[@id=\"formLivro:preco\"]"));
        Select cbAutores = new Select(driver.findElement(By.xpath("//*[@id=\"formLivro:autor_input\"]")));
        WebElement btnAddAutor = driver.findElement(By.xpath("//*[@id=\"formLivro:j_idt32\"]/span"));
        WebElement btnAdicionarLivro = driver.findElement(By.xpath("//*[@id=\"formLivro:j_idt40\"]/span"));

        txtNome.sendKeys(nome);
        txtIsbn.sendKeys(isbn);
        txtValor.clear();
        txtValor.sendKeys(String.valueOf(valor));
        cbAutores.selectByVisibleText(autor);
        btnAddAutor.click();
        btnAdicionarLivro.click();

        return this;
    }

    public boolean validarLivro(String nome, String isbn, double valor, String autor) {

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"formTabelaLivros:tabelaLivros_data\"]/tr[1]/td[1]"), nome));

        return driver.getPageSource().contains(isbn);
    }

    public void removerLivro(String nome) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"formTabelaLivros:tabelaLivros_data\"]/tr[1]/td[1]"), nome));

        int id = 3;
        WebElement btnRemover = driver.findElement(By.id("formTabelaLivros:tabelaLivros:" + id + ":j_idt54"));
        btnRemover.click();

    }
}
