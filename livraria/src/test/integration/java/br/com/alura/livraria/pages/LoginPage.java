package br.com.alura.livraria.pages;

import br.com.alura.livraria.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public LoginPage visita() {
        driver.get(Util.getUrlBase() + "/login.xhtml");
        return this;
    }

    public LivroPage realizarLoginCom(String login, String senha) {
        //logar
        WebElement txtLogin = driver.findElement(By.id("login:email"));
        WebElement txtSenha = driver.findElement(By.id("login:senha"));
        WebElement btnEfetuaLogin = driver.findElement(By.id("login:btnEfetuaLogin"));

        txtLogin.sendKeys(login);
        txtSenha.sendKeys(senha);
        btnEfetuaLogin.click();
        return new LivroPage(driver);
    }

    public boolean validacaoDeUsuarioNaoEncontrado() {
        //aguarda ajax executar
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"msg-alert\"]/div/ul/li/span"), "Usuário não encontrado"));
    }

    public boolean validacaoDeEmailObrigatorio() {
        //aguarda ajax executar
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .textToBePresentInElement(By.xpath("//*[@id=\"login:messageEmail\"]/span[2]"), "Email: Validation Error: Value is required."));
    }

    public boolean validacaoDeSenhaObrigatorio() {login:
    //aguarda ajax executar
    return new WebDriverWait(driver, 10)
            .until(ExpectedConditions
                    .textToBePresentInElement(By.xpath("//*[@id=\"login:messageSenha\"]/span[2]"), "Senha: Validation Error: Value is required."));
    }

    public LivroPage logar() {
        return this.visita()
                .realizarLoginCom("admin@admin.com", "admin");
    }
}
