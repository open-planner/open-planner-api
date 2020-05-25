package br.edu.ifpb.mestrado.openplanner.api.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.notificacao.Notificacao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.BaseManyByUsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.NotificacaoService;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.BaseWithRecorrenciaEntity;
import br.edu.ifpb.mestrado.openplanner.api.domain.shared.Recorrencia;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.BaseWithRecorrenciaRepository;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.BeanUtils;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.util.DateUtils;

public abstract class BaseWithRecorrenciaServiceImpl<T extends BaseWithRecorrenciaEntity<T>>
        extends BaseWithNotificacoesServiceImpl<T> implements BaseManyByUsuarioService<T> {

    public BaseWithRecorrenciaServiceImpl(OAuth2UserDetailsService userDetailsService, NotificacaoService notificacaoService) {
        super(userDetailsService, notificacaoService);
    }

    @Override
    @Transactional
    public T save(T entity) {
        T savedEntity = super.save(entity);

        if (savedEntity.isRecorrente()) {
            saveRecorrencias(entity);
        }

        return savedEntity;
    }

    @Override
    @Transactional
    public T update(Long id, T entity) {
        T savedEntity = findById(id);

        if (savedEntity.getRelacao() != null) {
            entity.setRecorrencia(null);
            entity.setRelacao(savedEntity.getRelacao());
        }

        T updatedEntity = super.update(id, entity);

        if (updatedEntity.getRelacao() != null) {
            return updatedEntity;
        }

        deleteByRelacaoId(id);

        if (updatedEntity.isRecorrente()) {
            saveRecorrencias(updatedEntity);
        }

        return updatedEntity;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        T entity = findById(id);

        if (entity.getRelacao() == null) {
            deleteByRelacaoId(id);
        }

        super.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<T> entities) {
        entities.forEach(e -> deleteById(e.getId()));
    }

    @SuppressWarnings("unchecked")
    protected void saveRecorrencias(T entity) {
        Recorrencia recorrencia = entity.getRecorrencia();
        LocalDateTime dataHora = entity.getDataHora();

        if (!recorrencia.getDataLimite().isAfter(dataHora.toLocalDate())) {
            return;
        }

        dataHora = DateUtils.plusOne(dataHora, recorrencia.getUnidade());

        List<Notificacao> notificacoes = null;

        if (entity.getNotificacoes() != null) {
            notificacoes = plusOneDataNewNotificacoes(entity.getNotificacoes(), recorrencia);
        }

        while (!dataHora.toLocalDate().isAfter(recorrencia.getDataLimite())) {
            T entityCopy = (T) entity.newInstance();
            BeanUtils.copyProperties(entity, entityCopy, "id", "recorrencia", "notificacoes");
            entityCopy.setRelacao(entity);
            entityCopy.setDataHora(dataHora);
            entityCopy.setNotificacoes(notificacoes);

            super.save(entityCopy);

            dataHora = DateUtils.plusOne(dataHora, recorrencia.getUnidade());

            if (notificacoes != null) {
                notificacoes = plusOneDataNewNotificacoes(notificacoes, recorrencia);
            }
        }
    }

    /**
     * Cria novas notificações incrementando sua data de acordo com a recorrência
     *
     * @param notificacoes notificações originais
     * @param recorrencia
     * @return a nova lista de notificações
     */
    protected List<Notificacao> plusOneDataNewNotificacoes(List<Notificacao> notificacoes, Recorrencia recorrencia) {
        return notificacoes.stream()
                .map(n -> {
                    Notificacao notificacao = new Notificacao();
                    BeanUtils.copyProperties(n, notificacao, "id");
                    notificacao.setDataHora(DateUtils.plusOne(notificacao.getDataHora(), recorrencia.getUnidade()));

                    return notificacao;
                }).collect(Collectors.toList());
    }

    @Transactional
    protected void deleteByRelacaoId(Long id) {
        List<T> entities = getRepository().findByRelacaoId(id);

        if (entities == null) {
            return;
        }

        entities.stream().forEach(e -> deleteById(e.getId()));
    }

    @Override
    protected abstract BaseWithRecorrenciaRepository<T> getRepository();

}
