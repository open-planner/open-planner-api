package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.util.List;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.evento.Evento;

public interface EventoRepository extends BaseManyByUsuarioRepository<Evento> {

    List<Evento> findByRelacaoId(Long id);

}
