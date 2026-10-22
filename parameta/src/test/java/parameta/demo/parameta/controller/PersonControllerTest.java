package parameta.demo.parameta.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import parameta.demo.parameta.dto.PersonDTO;
import parameta.demo.parameta.dto.TypeDocumentDTO;
import parameta.demo.parameta.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController controller;

    @Test
    void createPersonReturnsCreated() {
        PersonDTO person = new PersonDTO(1L, "Ana", "Prueba",
                new TypeDocumentDTO(1L, "CC", "Cédula"),
                "123456789", LocalDate.of(1990, 1, 1));
        when(personService.createPerson(any())).thenReturn(Optional.of(person));

        assertEquals(HttpStatus.CREATED, controller.createPerson(person).getStatusCode());
    }

    @Test
    void findPersonReturnsNotFoundForUnknownId() {
        when(personService.findPersonById(99L)).thenReturn(Optional.empty());

        assertEquals(HttpStatus.NOT_FOUND, controller.findPersonById(99L).getStatusCode());
    }
}
