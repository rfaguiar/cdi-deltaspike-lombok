package br.com.livraria.bean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class TemaBeanTest {

    private TemaBean temaBean;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.temaBean = new TemaBean();
    }

    @Test
    public void testeMetodoGetTemasDeveRetornarListaDeTemas() {
        String[] result = temaBean.getTemas();

        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}