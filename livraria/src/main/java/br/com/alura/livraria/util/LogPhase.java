package br.com.alura.livraria.util;

import br.com.livrarialib.jsf.annotation.Before;
import br.com.livrarialib.jsf.annotation.Phase;

import javax.enterprise.event.Observes;
import javax.faces.event.PhaseEvent;

public class LogPhase {

    public void log( @Observes @Before @Phase(Phase.Phases.RESTORE_VIEW) PhaseEvent phaseEvent){
        System.out.println("FASE: " + phaseEvent.getPhaseId());
    }
}
