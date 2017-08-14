package com.leolimajr.sample.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leolimajr.sample.model.Person;

@Repository
@SuppressWarnings("unchecked")
public class PersonSearchRepository {

	private final EntityManager entityManager;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public PersonSearchRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional(readOnly = true)
	public List<Person> search(final String query) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
		QueryParser parser = new QueryParser("firstName", searchFactory.getAnalyzer(Person.class));
		try {
			Query luceneQuery = parser.parse(query);
			return fullTextEntityManager.createFullTextQuery(luceneQuery, Person.class).getResultList();
		} catch (ParseException e) {
			logger.error("cannot parse lucene query",e);
		}
		return Collections.emptyList();
	}

	public void buildSearchIndex() {
		final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			logger.error("lucene indexing interrupted",e);
		}
	}
}
