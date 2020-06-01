package br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;

public interface NotificacaoRepository extends BaseManyByUsuarioRepository<Notificacao> {

    List<Notificacao> findByDataHoraLessThanEqualAndEmailOrderByDataHoraAscTipoAsc(LocalDateTime dataHora, Boolean email);

}
