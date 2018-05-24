package br.com.livraria.bean;

import br.com.livraria.modelo.Livro;
import br.com.livraria.repository.LivroRepository;
import br.com.livraria.util.ModelsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.primefaces.model.chart.BarChartModel;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class VendasBeanTest {

    private VendasBean vendasBean;
    @Mock
    private LivroRepository mockLivroRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.vendasBean = new VendasBean(mockLivroRepo);
    }

    @Test
    public void testeMetodoGetVendasModelDeveRetornarGraficoDeVendas() {
        List<Livro> livros = ModelsBuilder.criarListaLivrosTeste();
        Mockito.when(mockLivroRepo.findAll()).thenReturn(livros);

        BarChartModel result = vendasBean.getVendasModel();

        assertNotNull(result);
    }


}