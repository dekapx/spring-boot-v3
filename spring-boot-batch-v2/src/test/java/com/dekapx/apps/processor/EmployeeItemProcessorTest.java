package com.dekapx.apps.processor;

import com.dekapx.apps.model.Employee;
import com.dekapx.apps.model.EmployeeRedisDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeItemProcessorTest {

    private EmployeeItemProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new EmployeeItemProcessor();
    }

    @Test
    @DisplayName("Valid DTO maps correctly to Employee entity")
    void process_validDto_returnsEmployee() throws Exception {
        EmployeeRedisDto dto = EmployeeRedisDto.builder()
                .id("emp-001")
                .firstName("john")
                .lastName("doe")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .email("john.doe@example.com")
                .department("Engineering")
                .jobTitle("Senior Developer")
                .build();

        Employee result = processor.process(dto);

        assertThat(result).isNotNull();
        assertThat(result.getRedisId()).isEqualTo("emp-001");
        assertThat(result.getFirstName()).isEqualTo("John");   // capitalized
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getDepartment()).isEqualTo("Engineering");
    }

    @Test
    @DisplayName("DTO with missing first name is filtered (returns null)")
    void process_missingFirstName_returnsNull() throws Exception {
        EmployeeRedisDto dto = EmployeeRedisDto.builder()
                .id("emp-002")
                .firstName("")
                .lastName("Doe")
                .email("test@example.com")
                .build();

        Employee result = processor.process(dto);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("DTO with invalid email is filtered (returns null)")
    void process_invalidEmail_returnsNull() throws Exception {
        EmployeeRedisDto dto = EmployeeRedisDto.builder()
                .id("emp-003")
                .firstName("Jane")
                .lastName("Smith")
                .email("not-an-email")
                .build();

        Employee result = processor.process(dto);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("DTO with future date of birth is filtered (returns null)")
    void process_futureDob_returnsNull() throws Exception {
        EmployeeRedisDto dto = EmployeeRedisDto.builder()
                .id("emp-004")
                .firstName("Future")
                .lastName("Person")
                .email("future@example.com")
                .dateOfBirth(LocalDate.now().plusYears(1))
                .build();

        Employee result = processor.process(dto);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Email is normalized to lower case")
    void process_emailNormalized() throws Exception {
        EmployeeRedisDto dto = EmployeeRedisDto.builder()
                .id("emp-005")
                .firstName("Alice")
                .lastName("Wonder")
                .email("Alice.Wonder@EXAMPLE.COM")
                .build();

        Employee result = processor.process(dto);
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("alice.wonder@example.com");
    }

}
