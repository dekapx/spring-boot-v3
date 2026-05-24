package com.dekapx.apps.writer;

import com.dekapx.apps.model.Employee;
import com.dekapx.apps.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Batch ItemWriter that persists Employee entities to PostgreSQL.
 *
 * Implements an upsert strategy:
 *  - If an employee with the same redisId already exists → update it.
 *  - Otherwise → insert a new record.
 *
 * Spring Batch wraps each chunk in a transaction automatically,
 * but we also annotate with @Transactional for clarity and to ensure
 * all saves in the chunk are committed together.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeePostgresItemWriter implements ItemWriter<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        List<? extends Employee> items = chunk.getItems();
        log.info("Writing chunk of {} employees to PostgreSQL", items.size());

        int inserted = 0;
        int updated  = 0;

        for (Employee incoming : items) {
            var existing = employeeRepository.findByRedisId(incoming.getRedisId());

            if (existing.isPresent()) {
                // Update existing record
                Employee record = existing.get();
                record.setFirstName(incoming.getFirstName());
                record.setLastName(incoming.getLastName());
                record.setDateOfBirth(incoming.getDateOfBirth());
                record.setEmail(incoming.getEmail());
                record.setDepartment(incoming.getDepartment());
                record.setJobTitle(incoming.getJobTitle());
                employeeRepository.save(record);
                updated++;
            } else {
                // Insert new record
                employeeRepository.save(incoming);
                inserted++;
            }
        }

        log.info("Chunk complete — inserted: {}, updated: {}", inserted, updated);
    }

}
