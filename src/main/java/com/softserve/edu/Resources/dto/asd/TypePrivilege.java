package com.softserve.edu.Resources.dto.asd;

import java.util.List;

public class TypePrivilege {
    private String name;
    private boolean create;
    private boolean read;
    private boolean update;
    private boolean delete;
    private List<PropertyPrivilege> properties;

    public TypePrivilege() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public List<PropertyPrivilege> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyPrivilege> properties) {
        this.properties = properties;
    }
}
