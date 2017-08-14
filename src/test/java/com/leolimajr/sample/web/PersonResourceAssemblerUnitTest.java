package com.leolimajr.sample.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.leolimajr.sample.model.Person;
import com.leolimajr.sample.web.PersonResource;
import com.leolimajr.sample.web.PersonResourceAssembler;

@RunWith(MockitoJUnitRunner.class)
public class PersonResourceAssemblerUnitTest {

	@InjectMocks
	private PersonResourceAssembler underTest;

	@Before
	public void setup(){
		 RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
	}

	@Test
	public void shouldConvertResource() {
		
		Person person = new Person();
		person.setId(1L);
		person.setFirstName("foo");
		person.setLastName("bar");
		person.setEmail("foo@bar.com");
		person.setGender("male");
		person.setIp("127.0.0.1");
		
		PersonResource personResource = underTest.toResource(person);
		
		assertEquals(personResource.getFirstName(), person.getFirstName());
		assertEquals(personResource.getLastName(), person.getLastName());
		assertEquals(personResource.getEmail(), person.getEmail());
		assertEquals(personResource.getGender(), person.getGender());
		assertEquals(personResource.getIp(), person.getIp());
		
	}
}