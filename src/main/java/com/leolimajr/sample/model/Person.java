package com.leolimajr.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;;

@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(
			name = "default",
			tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
			filters = { 
					@TokenFilterDef(factory = LowerCaseFilterFactory.class),
					@TokenFilterDef(factory = SnowballPorterFilterFactory.class,
					params = {
							@Parameter(name = "language", value = "English")
					})
			}),
	@AnalyzerDef(
			name = "simple",
			tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class)
			)
})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO,analyzer=@Analyzer(definition="default"))
	private String firstName;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO,analyzer=@Analyzer(definition="default"))
	private String lastName;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO, analyzer=@Analyzer(definition="simple"))
	private String email;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO,analyzer=@Analyzer(definition="default"))
	private String gender;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO,analyzer=@Analyzer(definition="simple"))
	private String ip;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
