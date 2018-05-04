package br.com.livraria.util;

import br.com.livrarialib.jsf.annotation.Before;
import br.com.livrarialib.jsf.annotation.Phase;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.event.Observes;
import javax.faces.event.PhaseEvent;

@Slf4j
public class LogPhase {

    public void log( @Observes @Before @Phase(Phase.Phases.RESTORE_VIEW) PhaseEvent phaseEvent){
        log.debug("FASE: " + phaseEvent.getPhaseId());
    }
}
