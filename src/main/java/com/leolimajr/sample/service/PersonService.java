package com.leolimajr.sample.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import com.leolimajr.sample.model.Person;
import com.leolimajr.sample.repo.PersonRepository;
import com.leolimajr.sample.repo.PersonSearchRepository;
import com.leolimajr.sample.web.PersonResource;

@Service
public class PersonService {

	private final PersonRepository personRepository;
	private final PersonSearchRepository personSearchRepository;
	private final ResourceAssemblerSupport<Person,PersonResource> personAssembler;
	private final PagedResourcesAssembler<PersonResource> pagedAssembler;
	
	public PersonService(PersonRepository personRepository, PersonSearchRepository personSearchRepository,
			ResourceAssemblerSupport<Person, PersonResource> personAssembler,
			PagedResourcesAssembler<PersonResource> pagedAssembler) {
		this.personRepository = personRepository;
		this.personSearchRepository = personSearchRepository;
		this.personAssembler = personAssembler;
		this.pagedAssembler = pagedAssembler;
	}

	public PersonResource findOne(Long id){
		return personAssembler.toResource(personRepository.findOne(id));
	}
	
	public PagedResources<Resource<PersonResource>> findAll(Pageable page){
		Page<Person> personPage = personRepository.findAll(page);
		
		Page<PersonResource> personResourcePage  = personPage.map(new Converter<Person, PersonResource>() {
            public PersonResource convert(Person person) {
                return personAssembler.toResource(person);
            }
        });
		
		return pagedAssembler.toResource(personResourcePage);
	}
	
	public Resources<PersonResource> search(final String query) {
		return new Resources<PersonResource>(personAssembler.toResources(personSearchRepository.search(query)));
	}
}
