package com.leolimajr.sample.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk())
		.andExpect(
				jsonPath("$._links.persons").exists()
		)
		.andExpect(
				jsonPath("$._links.profile").exists()
		);
	}
	
	@Test
	public void shouldReturnResourceIndex() throws Exception {
		mockMvc.perform(get("/persons")).andExpect(status().isOk())
		.andExpect(
				jsonPath("$._links.self").exists()
		)
		.andExpect(
				jsonPath("$._links.search").exists()
		);
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {
		mockMvc.perform(get("/persons/1/")).andExpect(status().isOk())
		.andExpect(
				jsonPath("$.firstName").value("Foo"))
		.andExpect(
				jsonPath("$.lastName").value("Bar"))
		.andExpect(
				jsonPath("$.email").value("foo@bar.com"))
		.andExpect(
				jsonPath("$.gender").value("Female"))
		.andExpect(
				jsonPath("$.ip").value("127.0.0.1"))
		.andExpect(
				jsonPath("$._links.self.href").value("http://localhost/persons/1")
		);
	}
	
	@Test
	public void shouldRetrieveEntities() throws Exception {
		mockMvc.perform(get("/persons/")).andExpect(status().isOk())
		.andExpect(
				jsonPath("$._embedded.personResources[0].firstName").value("Foo"))
		.andExpect(
				jsonPath("$._embedded.personResources[0]._links.self.href").value("http://localhost/persons/1"))
		.andExpect(
				jsonPath("$._embedded.personResources[1].firstName").value("John"))
		.andExpect(
				jsonPath("$._embedded.personResources[1]._links.self.href").value("http://localhost/persons/2"))
		.andExpect(
				jsonPath("$.page.totalPages").value("1"))
		.andExpect(
				jsonPath("$.page.totalElements").value("2"));
	}

	@Test
	public void shouldSearchEntity() throws Exception {
		mockMvc.perform(
				get("/persons/search?query={query}", "firstName:Fo?")).andExpect(
						status().isOk())
				.andExpect(
						jsonPath("$._embedded.personResources[0].firstName").value("Foo"));
	}
}