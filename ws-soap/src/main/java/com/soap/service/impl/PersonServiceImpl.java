package com.soap.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.soap.dto.PersonDTO;
import com.soap.dto.TypeDocumentDTO;
import com.soap.entity.PersonEntity;
import com.soap.repository.PersonRepository;
import com.soap.service.PersonService;

import demo.soap.util.LogHelper;

@Service
public class PersonServiceImpl implements PersonService {

	private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	private final PersonRepository repository;

	public PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<PersonDTO> findPersonByDni(String dni) {
		logger.info(LogHelper.start(getClass(), "findPersonByDni"));
		Optional<PersonEntity> person = repository.findByDni(dni);
		logger.info(LogHelper.end(getClass(), "findPersonByDni"));
		return person.map(entity -> new PersonDTO(entity.getId(),
				entity.getDni(),
				entity.getNames(),
				entity.getLastNames(),
				new TypeDocumentDTO(entity.getTypeDocument().getId(),
						entity.getTypeDocument().getCode(),
						entity.getTypeDocument().getDocument()),
				entity.getDateOfBirth()));
	}
}
