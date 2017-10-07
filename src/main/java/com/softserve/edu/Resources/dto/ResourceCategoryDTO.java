package com.softserve.edu.Resources.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@JsonIdentityInfo(generator = ObjectIdGenerat4ors.PropertyGenerator.class, property = "id")
public class ResourceCategoryDTO{

    @JsonView(Views.Categories.class)
    private Long id;

    @JsonProperty("categoryname")
    @JsonView(Views.Categories.class)
    private String categoryName;

    @JsonBackReference
//    @JsonProperty("parent_id")
//    @JsonIdentityReference(alwaysAsId = true)
    @JsonView(Views.Categories.class)
    private ResourceCategoryDTO parentCategory;

    @JsonManagedReference
    @JsonProperty("children")
    @JsonView(Views.Categories.class)
    private Set<ResourceCategoryDTO> childrenCategories = new HashSet<>();

//    @JsonView(Views.CategorySelecting.class)
    private Integer depth;

//    @JsonView(Views.CategorySelecting.class)
    private String treePath;

    @JsonManagedReference
    @JsonProperty("restypes")
    @JsonView(Views.CategoriesWithTypes.class)
    private Set<ResourceTypeDTO> instantiatedResourceTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ResourceCategoryDTO getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ResourceCategoryDTO parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<ResourceCategoryDTO> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(Set<ResourceCategoryDTO> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Set<ResourceTypeDTO> getInstantiatedResourceTypes() {
        return instantiatedResourceTypes;
    }

    public void setInstantiatedResourceTypes(Set<ResourceTypeDTO> instantiatedResourceTypes) {
        this.instantiatedResourceTypes = instantiatedResourceTypes;
    }

    @Override
    public String toString() {
        return "ResourceCategoryDTO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceCategoryDTO that = (ResourceCategoryDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return categoryName.equals(that.categoryName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + categoryName.hashCode();
        return result;
    }
}

