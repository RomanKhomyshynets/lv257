package com.softserve.edu.Resources.service.impl;

import com.softserve.edu.Resources.dao.ResourceDao;
import com.softserve.edu.Resources.dao.ResourceTypeDAO;
import com.softserve.edu.Resources.dto.GenericResourceDTO;
import com.softserve.edu.Resources.entity.ConstrainedProperty;
import com.softserve.edu.Resources.entity.GenericResource;
import com.softserve.edu.Resources.entity.ResourceType;
import com.softserve.edu.Resources.exception.ResourceNotFoundException;
import com.softserve.edu.Resources.service.ResourceService;
import com.softserve.edu.Resources.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    ResourceTypeDAO resourceTypeDAO;

    @Autowired
    ResourceDao resourceDao;

    @Autowired
    QueryBuilder queryBuilder;

    @Transactional
    @Override
    public List<GenericResource> findResourcesByResourceType(GenericResourceDTO resourceDTO) throws ResourceNotFoundException{

        long resourceTypeId = resourceDTO.getId();

        ResourceType resourceType = resourceTypeDAO.findWithPropertiesByID(resourceTypeId);
        
        if (resourceType == null){
            throw new ResourceNotFoundException("No infromation was found by your request");
        }

        String tableName = resourceType.getTableName();

        List<ConstrainedProperty> resourceProperties = new ArrayList<>(resourceType.getProperties());

        Map<String, String> valuesToSearch = resourceDTO.getResourcePropertyValue();
        
        Set <String> propertyColumnNames = valuesToSearch.keySet();
        
        boolean columnNameExist = false;
        for (String columnName : propertyColumnNames) {
            for (ConstrainedProperty constrainedProperty : resourceProperties) {
                if (columnName.equalsIgnoreCase(constrainedProperty.getProperty().getColumnName())) {
                    columnNameExist = true;
                    break;
                } else{
                    columnNameExist = false;
                }
            }
            if (!columnNameExist){
                throw new ResourceNotFoundException("You`ve requested wrong data.");
            }
        }
        

        String queryForDao = queryBuilder.lookUpByResouceType(tableName, valuesToSearch, resourceProperties);

        return resourceDao.findResourcesByResourceType(queryForDao, valuesToSearch, resourceProperties);
    }

}