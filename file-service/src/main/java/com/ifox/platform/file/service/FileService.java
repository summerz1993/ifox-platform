package com.ifox.platform.file.service;

import com.ifox.platform.file.enums.EnumFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FileService {

    @Autowired
    private Environment env;

    public boolean validateFileType(EnumFile.FileType fileType, String extension) {
        if (fileType == null)
            return false;
        switch (fileType) {
            case PICTURE:
                String[] pictureTypeArray = env.getProperty("file-service.file-type.picture").split(",");
                if (Arrays.asList(pictureTypeArray).contains(extension))
                    return true;
            case DOC:
                String[] docTypeArray = env.getProperty("file-service.file-type.doc").split(",");
                if (Arrays.asList(docTypeArray).contains(extension))
                    return true;
            case VIDEO:
                String[] videoTypeArray = env.getProperty("file-service.file-type.video").split(",");
                if (Arrays.asList(videoTypeArray).contains(extension))
                    return true;
            case AUDIO:
                String[] audioTypeArray = env.getProperty("file-service.file-type.audio").split(",");
                if (Arrays.asList(audioTypeArray).contains(extension))
                    return true;
            default:
                return false;
        }

    }

}
