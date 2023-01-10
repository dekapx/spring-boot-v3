package com.dekapx.apps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public final class CsvFileWriter implements FileWriter {
    @Override
    public void write(final String contents) {
        log.info("Writing CSV: [{}]", contents);
    }
}
