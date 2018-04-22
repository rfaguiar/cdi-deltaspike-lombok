package br.com.livraria.bean;

import br.com.livraria.modelo.Livro;
import br.com.livraria.modelo.Venda;
import br.com.livraria.repository.LivroRepository;
import br.com.livrarialib.jsf.annotation.ViewModel;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ViewModel
public class VendasBean implements Serializable{


	private static final long serialVersionUID = 1L;

	private LivroRepository livroRepo;

	@Inject
    public VendasBean(LivroRepository livroRepo) {
        this.livroRepo = livroRepo;
    }

    public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");

		List<Venda> vendas = getVendas(1234);
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		ChartSeries vendaSerie2015 = new ChartSeries();
		vendaSerie2015.setLabel("Vendas 2015");
		
		vendas = getVendas(4321);
		for (Venda venda : vendas) {
			vendaSerie2015.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);
		model.addSeries(vendaSerie2015);

		return model;
	}

	public List<Venda> getVendas(long seed) {

		List<Livro> livros = livroRepo.findAll();
		List<Venda> vendas = new ArrayList<>();

		Random random = new Random(seed);

		for (Livro livro : livros) {
			Integer quantidade = random.nextInt(500);
			vendas.add(new Venda(livro, quantidade));
		}

		return vendas;
	}
}
