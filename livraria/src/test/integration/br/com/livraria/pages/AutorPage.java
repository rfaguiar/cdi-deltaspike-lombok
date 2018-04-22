package br.com.livraria.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class AutorPage {

    private WebDriver driver;

    public AutorPage(WebDriver driver) {

        this.driver = driver;
    }

    public AutorPage remover(String nome) {
        return this;
    }

    public boolean existe(String nome) {
        return driver.getPageSource().contentEquals(nome);
    }

    public AutorPage cadastrarAutor(String nome, String email) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement txtNome = driver.findElement(By.id("autor:nome"));
        WebElement txtEmail = driver.findElement(By.id("autor:email"));
        WebElement btnGravar = driver.findElement(By.id("autor:btnGravarAutor"));
        txtNome.sendKeys(nome);
        txtEmail.sendKeys(email);
        btnGravar.click();
        return this;
    }
}
