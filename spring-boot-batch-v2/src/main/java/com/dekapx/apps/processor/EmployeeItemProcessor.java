package com.dekapx.apps.processor;

import com.dekapx.apps.model.Employee;
import com.dekapx.apps.model.EmployeeRedisDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * Spring Batch ItemProcessor.
 *
 * Responsibilities:
 *  1. Validate incoming EmployeeRedisDto
 *  2. Transform / enrich data as needed
 *  3. Map DTO → JPA Entity
 *
 * Returning null from process() tells Spring Batch to skip this item (not write it).
 */
@Slf4j
@Component
public class EmployeeItemProcessor implements ItemProcessor<EmployeeRedisDto, Employee> {

    @Override
    public Employee process(EmployeeRedisDto dto) throws Exception {

        // --- Validation ---
        if (!StringUtils.hasText(dto.getFirstName()) || !StringUtils.hasText(dto.getLastName())) {
            log.warn("Skipping employee [{}] — missing required name fields", dto.getId());
            return null; // filtered out
        }

        if (!isValidEmail(dto.getEmail())) {
            log.warn("Skipping employee [{}] — invalid email: '{}'", dto.getId(), dto.getEmail());
            return null;
        }

        if (dto.getDateOfBirth() != null && dto.getDateOfBirth().isAfter(LocalDate.now())) {
            log.warn("Skipping employee [{}] — future date of birth: {}", dto.getId(), dto.getDateOfBirth());
            return null;
        }

        // --- Transformation ---
        String firstName = capitalize(dto.getFirstName().trim());
        String lastName  = capitalize(dto.getLastName().trim());
        String email     = dto.getEmail().trim().toLowerCase();

        Employee employee = Employee.builder()
                .redisId(dto.getId())
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dto.getDateOfBirth())
                .email(email)
                .department(dto.getDepartment())
                .jobTitle(dto.getJobTitle())
                .build();

        log.debug("Processed employee: {} {} ({})", firstName, lastName, email);
        return employee;
    }

    private boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) return value;
        return Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase();
    }

}
