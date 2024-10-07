package hu.cubix.hr.zoltan_sipeki.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.cubix.hr.zoltan_sipeki.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRestControllerIT {

    private static final String URI_EMPLOYEES = "/api/employees";

    private static final EmployeeDto[] VALID_EMPLOYEES = new EmployeeDto[] {
            new EmployeeDto(1L, "name1", "job1", 10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
            new EmployeeDto(2L, "name2", "job2", 20000, LocalDateTime.of(2020, 5, 3, 0, 0)),
            new EmployeeDto(3L, "name3", "job3", 30000, LocalDateTime.of(2013, 2, 21, 0, 0)),
            new EmployeeDto(4L, "name4", "job4", 40000, LocalDateTime.of(2000, 9, 15, 0, 0)),
            new EmployeeDto(5L, "name5", "job4", 40000, LocalDateTime.of(2017, 6, 7, 0, 0)),
            new EmployeeDto(6L, "name6", "job4", 40000, LocalDateTime.of(2024, 6, 7, 0, 0))
    };

    private static final EmployeeDto[] INVALID_EMPLOYEES = new EmployeeDto[] {
            // name is empty
            new EmployeeDto(1L, "", "Developer", 10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
            // job is empty
            new EmployeeDto(2L, "Jane Doe", "", 10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
            // salary is non-positive
            new EmployeeDto(3L, "Jane Doe", "Developer", -10000, LocalDateTime.of(2010, 10, 11, 0, 0)),
            // first day at work is in the future
            new EmployeeDto(4L, "Jane Doe", "Developer", 10000, LocalDateTime.of(2025, 10, 11, 0, 0)),
    };

    @Autowired
    WebTestClient client;

    @Test
    @Order(1)
    void createEmployeesWithInvalidData() {
        for (var employee : INVALID_EMPLOYEES) {
            createEmployee(employee).expectStatus().isBadRequest();
        }

        var employeesCreated = getEmployees();
        assertEquals(true, employeesCreated.isEmpty());
    }

    @Test
    @Order(2)
    void createEmployeesWithDifferentIDs() {
        for (var employee : VALID_EMPLOYEES) {
            createEmployee(employee).expectStatus().isCreated();
        }

        var employeesCreated = getEmployees();
        assertEquals(VALID_EMPLOYEES.length, employeesCreated.size());

        for (var employee : VALID_EMPLOYEES) {
            assertEquals(true, employeesCreated.contains(employee));
        }
    }

    @Test
    @Order(3)
    void createEmployeeWithExistingID() {
        var employee = new EmployeeDto(1L, "John Doe", "Tester", 20000, LocalDateTime.of(2011, 10, 11, 0, 0));
        createEmployee(employee).expectStatus().isBadRequest();

        var employeesCreated = getEmployees();
        assertEquals(VALID_EMPLOYEES.length, employeesCreated.size());

        assertEquals(false, employeesCreated.contains(employee));
    }

    @Test
    @Order(4)
    void updateEmployeeWithInvalidData() {
        for (var employee : INVALID_EMPLOYEES) {
            updateEmployee(employee).expectStatus().isBadRequest();
        }

        var employees = getEmployees();
        assertEquals(VALID_EMPLOYEES.length, employees.size());

        for (var employee : VALID_EMPLOYEES) {
            assertEquals(true, employees.contains(employee));
        }
    }

    @Test
    @Order(5)
    void updateEmployeeWithUnknownID() {
        var employee = new EmployeeDto(506L, "Jane Doe", "Developer", 10000, LocalDateTime.of(2010, 10, 11, 0, 0));
        updateEmployee(employee).expectStatus().isBadRequest();

        var employees = getEmployees();
        assertEquals(VALID_EMPLOYEES.length, employees.size());

        assertEquals(false, employees.contains(employee));
    }

    @Test
    @Order(6)
    void updateExistingEmployee() {
        var employee = new EmployeeDto(1L, "John Doe", "Tester", 20000, LocalDateTime.of(2015, 10, 11, 0, 0));
        updateEmployee(employee).expectStatus().isOk();

        var employees = getEmployees();
        assertEquals(VALID_EMPLOYEES.length, employees.size());

        assertEquals(true, employees.contains(employee));
    }

    private ResponseSpec updateEmployee(EmployeeDto employee) {
        return client.put().uri(URI_EMPLOYEES + "/" + employee.getId()).contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employee).exchange();
    }

    private ResponseSpec createEmployee(EmployeeDto employee) {
        return client.post().uri(URI_EMPLOYEES).contentType(MediaType.APPLICATION_JSON).bodyValue(employee).exchange();
    }

    private List<EmployeeDto> getEmployees() {
        return client.get().uri(URI_EMPLOYEES).exchange().expectBodyList(EmployeeDto.class).returnResult()
                .getResponseBody();
    }
}
