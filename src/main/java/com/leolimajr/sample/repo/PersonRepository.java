package com.leolimajr.sample.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.leolimajr.sample.model.Person;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {}