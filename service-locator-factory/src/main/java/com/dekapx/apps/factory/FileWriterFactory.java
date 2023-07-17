package com.dekapx.apps.factory;

import com.dekapx.apps.service.FileWriter;

public interface FileWriterFactory {
    FileWriter getFileWriter(String beanName);
}
