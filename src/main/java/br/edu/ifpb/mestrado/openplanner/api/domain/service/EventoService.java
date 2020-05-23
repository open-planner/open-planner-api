package br.edu.ifpb.mestrado.openplanner.api.domain.service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared.RecorrenciaRequestTO;

public interface EventoService extends BaseManyByUsuarioService<Evento> {

    public Evento save(Evento evento, RecorrenciaRequestTO recorrencia);

}
