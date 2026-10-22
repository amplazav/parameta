package parameta.demo.parameta.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import parameta.demo.parameta.dto.EmployeeDTO;
import parameta.demo.parameta.dto.PersonEmployeeDTO;
import parameta.demo.parameta.dto.RoleDTO;
import parameta.demo.parameta.dto.TypeDocumentDTO;
import parameta.demo.parameta.service.EmployeeService;
import parameta.demo.ws.client.pojo.EmployeePojo;
import parameta.demo.ws.client.pojo.PersonEmployeePojo;
import parameta.demo.ws.client.pojo.SaveEmployeeResponse;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private static Validator validator;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController controller;

    @BeforeAll
    static void createValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void createReturnsCreatedOnlyWhenSoapReportsSuccess() {
        SaveEmployeeResponse response = response("success");
        when(employeeService.createEmployee(any())).thenReturn(Optional.of(response));

        assertEquals(HttpStatus.CREATED, controller.createEmployee(validEmployeeRequest()).getStatusCode());
    }

    @Test
    void createMapsSoapNotFoundToHttp404() {
        when(employeeService.createEmployee(any()))
                .thenReturn(Optional.of(response("not_found")));

        assertEquals(HttpStatus.NOT_FOUND, controller.createEmployee(validEmployeeRequest()).getStatusCode());
    }

    @Test
    void createMapsDuplicateEmployeeToHttp409() {
        when(employeeService.createEmployee(any()))
                .thenReturn(Optional.of(response("conflict")));

        assertEquals(HttpStatus.CONFLICT, controller.createEmployee(validEmployeeRequest()).getStatusCode());
    }

    @Test
    void createRejectsMissingNestedEmployeeData() {
        PersonEmployeeDTO request = validEmployeeRequest();
        request.setEmployeeDTO(null);

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void createRejectsAnUnderagePerson() {
        PersonEmployeeDTO request = validEmployeeRequest();
        request.setDateOfBirth(LocalDate.now().minusYears(17));

        assertFalse(validator.validate(request).isEmpty());
    }

    private SaveEmployeeResponse response(String status) {
        SaveEmployeeResponse response = new SaveEmployeeResponse();
        response.setStatus(status);
        response.setMessage("test");
        if ("success".equals(status)) {
            PersonEmployeePojo person = new PersonEmployeePojo();
            person.setEmployee(new EmployeePojo());
            response.setPersonEmployee(person);
        }
        return response;
    }

    private PersonEmployeeDTO validEmployeeRequest() {
        return new PersonEmployeeDTO(null, "Ana", "Prueba",
                new TypeDocumentDTO(1L, "CC", "Cédula"), "123456789",
                LocalDate.of(1990, 1, 1),
                new EmployeeDTO(null, new RoleDTO(1L, "Desarrollador"),
                        3500000D, LocalDate.of(2025, 1, 15)));
    }
}
