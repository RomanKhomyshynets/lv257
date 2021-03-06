package com.softserve.edu.Resources.entity;

import com.softserve.edu.Resources.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

/**
 * Represents legal document issued by authorities
 */

@Entity
public class Avatar {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id_document;

    @Column(name = "code")
    private String code;

    @Column(name = "extension")
    private String fileExtension;

    @Column(name = "URL")
    private String documentsURL;

    @Transient
    private MultipartFile file;

    public Avatar() {
        this.code = "file" + UUID.randomUUID().toString().substring(26).toUpperCase();

    }

    public long getId() {
        return id_document;
    }

    public void setId(long id_document) {
        this.id_document = id_document;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar)) return false;

        Avatar document = (Avatar) o;

        if (id_document != document.id_document) return false;
        if (getCode() != null ? !getCode().equals(document.getCode()) : document.getCode() != null) return false;
        return getFileExtension() != null ? getFileExtension().equals(document.getFileExtension()) : document.getFileExtension() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id_document ^ (id_document >>> 32));
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getFileExtension() != null ? getFileExtension().hashCode() : 0);
        return result;
    }

    public String getDocumentsURL() {
        return documentsURL;
    }

    public void setDocumentsURL(String documentsURL) {
        this.documentsURL = documentsURL;
    }
}
