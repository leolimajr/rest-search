package com.leolimajr.sample.web;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collections;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leolimajr.sample.service.PersonService;

@RestController
@RequestMapping("/persons")
@ExposesResourceFor(PersonResource.class)
public class PersonController {

	private final PersonService personService;
	private final EntityLinks entityLinks;

	public PersonController(PersonService personService, EntityLinks entityLinks) {
		this.personService = personService;
		this.entityLinks = entityLinks;
	}

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resources<PersonResource>> root() {
		return ResponseEntity.ok(
				new Resources<>(Collections.emptySet(), 
						entityLinks.linkToCollectionResource(PersonResource.class),
						linkTo(methodOn(PersonController.class).search(null)).withRel("search")));
	}

	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<PagedResources<Resource<PersonResource>>> list(Pageable pageable) {
		return ResponseEntity.ok().body(personService.findAll(pageable));
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<PersonResource> get(@PathVariable Long id) {
		System.out.println(personService.findOne(id));
		return ResponseEntity.ok().body(personService.findOne(id));
	}

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	HttpEntity<Resources<PersonResource>> search(@RequestParam String query) {
		Resources<PersonResource> persons = personService.search(query);
		persons.add(linkTo(methodOn(PersonController.class).search(query)).withSelfRel());
		return new ResponseEntity<>(persons, HttpStatus.OK);
	}
}