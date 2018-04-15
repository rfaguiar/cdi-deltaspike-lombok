package br.com.livrarialib.tx;

import javax.interceptor.InvocationContext;
import java.io.Serializable;

public interface Transacionado extends Serializable{
    Object executaComTransacao(InvocationContext context);
}
