package com.soap.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.soap.dto.EmployeedDTO;
import com.soap.dto.PersonDTO;
import com.soap.dto.RoleDTO;
import com.soap.dto.TypeDocumentDTO;
import com.soap.service.EmployeeService;
import com.soap.service.PersonService;

import demo.soap.pojos.PersonEmployeePojo;
import demo.soap.pojos.SaveEmployeeResponse;
import demo.soap.util.ApiMessages;

class CreateEmployeeServiceImplTest {

    private final PersonService personService = mock(PersonService.class);
    private final EmployeeService employeeService = mock(EmployeeService.class);
    private final CreateEmployeeServiceImpl service =
            new CreateEmployeeServiceImpl(personService, employeeService);

    @Test
    void returnsNotFoundWhenDniDoesNotBelongToAPerson() {
        PersonEmployeePojo request = new PersonEmployeePojo();
        request.setDni("999999999");
        when(personService.findPersonByDni(request.getDni())).thenReturn(Optional.empty());

        SaveEmployeeResponse response = service.createEmployee(request).orElseThrow();

        assertEquals(ApiMessages.NOT_FOUND, response.getStatus());
        assertEquals("La persona con DNI 999999999 no se encuentra registrada", response.getMessage());
    }

    @Test
    void returnsConflictWhenPersonAlreadyHasAnEmployeeRecord() {
        PersonEmployeePojo request = new PersonEmployeePojo();
        request.setDni("123456789");
        PersonDTO person = new PersonDTO(7L, request.getDni(), "Ana", "Prueba",
                new TypeDocumentDTO(1L, "CC", "Cédula"), LocalDate.of(1990, 1, 1));
        when(personService.findPersonByDni(request.getDni())).thenReturn(Optional.of(person));

        EmployeedDTO existingEmployee = new EmployeedDTO(3L, 7L, LocalDate.of(2024, 1, 1),
                3500000, new RoleDTO(1L, "Desarrollador"));
        when(employeeService.findEmployeeByIdperson(7L)).thenReturn(Optional.of(existingEmployee));

        SaveEmployeeResponse response = service.createEmployee(request).orElseThrow();

        assertEquals(ApiMessages.CONFLICT, response.getStatus());
    }
}
