package br.edu.ifpb.mestrado.openplanner.api.application.factory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;

import br.edu.ifpb.mestrado.openplanner.api.application.controller.PermissaoController;

public class PermissaoLinkFactory {

    public static List<Link> create(Long id) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(PermissaoController.class)
                .findAll())
                .withSelfRel());

        return links;
    }

}
