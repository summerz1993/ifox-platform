package com.ifox.platform.file.service;

import com.ifox.platform.file.enums.EnumFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void testValidateFileType() {
        boolean validation = fileService.validateFileType(EnumFile.FileType.PICTURE, "jpg");
        System.out.println("validation:" + validation);
    }

}
