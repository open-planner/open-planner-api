package br.edu.ifpb.mestrado.openplanner.api.application.factory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;

import br.edu.ifpb.mestrado.openplanner.api.application.controller.GrupoController;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.grupo.GrupoFilterRequestTO;

public class GrupoLinkFactory {

    public static List<Link> create(Long id) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GrupoController.class)
                .findById(id))
                .withSelfRel());
        links.add(linkTo(methodOn(GrupoController.class)
                .findAll(new GrupoFilterRequestTO(), PageRequest.of(0, 10)))
                .withRel("grupos"));
        links.add(linkTo(methodOn(GrupoController.class)
                .findAllActive())
                .withRel("ativos"));

        return links;
    }

}
