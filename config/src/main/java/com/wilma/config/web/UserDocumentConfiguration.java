package com.wilma.config.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
public class UserDocumentConfiguration {

    @Value("${spring.http.multipart.upload-path}")
    private String uploadPath;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String uploadSizeLimit;
    public static Map<String, String> mimeMap = new LinkedHashMap<>();

    /**
     * Get the matching media type for a file based on its file extension
     * @param filename The fully qualified filename
     * @return A string version of the file media type based on its extension
     */
    public String getMediaType(String filename){
        var extension = filename.substring(filename.lastIndexOf('.') + 1);
        return mimeMap.getOrDefault(extension, "application/octet-stream");
    }

    /**
     * Sets up the system to match media types to files
     */
    @PostConstruct
    public void loadMimeMap(){
        //MS Office
        mimeMap.putAll((Map.of(
                "doc", "application/msword",
                "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "xls", "application/vnd.ms-excel",
                "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )));
        //Images
        mimeMap.putAll(Map.of(
                "jpeg", "image/jpeg",
                "jpg", "image/jpeg",
                "png", "image/png"
        ));
        //Text
        mimeMap.putAll(Map.of(
                "pdf", "application/pdf",
                "txt", "text/plain",
                "md", "text/markdown"
        ));
        //Code
        mimeMap.putAll(Map.of(
                "json", "application/json",
                "xml", "application/xml"
        ));
    }
}
