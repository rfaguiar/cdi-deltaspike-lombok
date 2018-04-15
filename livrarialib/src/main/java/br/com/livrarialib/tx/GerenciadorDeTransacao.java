package br.com.livrarialib.tx;

import br.com.livrarialib.tx.annotation.Transacional;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@Transacional
public class GerenciadorDeTransacao implements Serializable {

    private static final long serialVersionUID = -1392064408660679407L;
    private Transacionado transacionado;


    @Inject
    public GerenciadorDeTransacao(Transacionado transacionado){
        this.transacionado = transacionado;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext context) {

        return transacionado.executaComTransacao(context);

    }

}
