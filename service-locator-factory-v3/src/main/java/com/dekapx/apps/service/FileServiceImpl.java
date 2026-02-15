package com.dekapx.apps.service;

import com.dekapx.apps.factory.FileWriterFactory;
import com.dekapx.apps.model.FileModel;
import com.dekapx.apps.model.FileType;
import com.dekapx.apps.writer.FileWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileWriterFactory fileWriterFactory;

    @Override
    public void write(FileModel fileModel) {
        Optional.ofNullable(fileModel)
                .orElseThrow(() -> new IllegalArgumentException("FileModel cannot be null..."));

        log.info("Writing file of type: {}", fileModel.getFileType());
        FileType fileType = fileModel.getFileType();
        String beanName = fileType.getFactoryBeanName();
        log.info("FileType: {}, Factory Bean Name: {}", fileType, beanName);

        FileWriter fileWriter = this.fileWriterFactory
                .getFileWriter(beanName);
        String contents = fileModel.getContents();
        fileWriter.write(contents);
    }
}
