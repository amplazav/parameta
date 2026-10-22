package com.soap.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.soap.repository.PersonRepository;

class PersonServiceImplTest {

    private final PersonRepository repository = mock(PersonRepository.class);
    private final PersonServiceImpl service = new PersonServiceImpl(repository);

    @Test
    void databaseFailureIsNotMisreportedAsMissingPerson() {
        when(repository.findByDni("123")).thenThrow(new IllegalStateException("database unavailable"));

        assertThrows(IllegalStateException.class, () -> service.findPersonByDni("123"));
    }
}
