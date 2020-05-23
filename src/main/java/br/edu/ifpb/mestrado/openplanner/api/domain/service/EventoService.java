package br.edu.ifpb.mestrado.openplanner.api.domain.service;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;

public interface EventoService extends BaseManyByUsuarioService<Evento> {

    void deleteByRelacaoId(Long id);

}
