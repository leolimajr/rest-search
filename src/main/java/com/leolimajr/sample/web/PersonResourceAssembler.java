package com.leolimajr.sample.web;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.leolimajr.sample.model.Person;

@Component
public class PersonResourceAssembler extends ResourceAssemblerSupport<Person,PersonResource> {

	public PersonResourceAssembler() {
		super(PersonController.class,PersonResource.class);
	}

	@Override
	public PersonResource toResource(Person person) {
		PersonResource resource = createResourceWithId(person.getId(), person);
		resource.setFirstName(person.getFirstName());
		resource.setLastName(person.getLastName());
		resource.setEmail(person.getEmail());
		resource.setGender(person.getGender());
		resource.setIp(person.getIp());
		return resource;
	}
}