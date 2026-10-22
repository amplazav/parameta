package parameta.demo.parameta.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import parameta.demo.parameta.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void findPersonReturnsEmptyForAnUnknownId() {
        when(personRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(personService.findPersonById(99L).isEmpty());
    }
}
