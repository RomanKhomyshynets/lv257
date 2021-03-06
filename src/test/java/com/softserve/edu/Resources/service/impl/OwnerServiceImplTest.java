package com.softserve.edu.Resources.service.impl;

import com.softserve.edu.Resources.dao.OwnerDAO;
import com.softserve.edu.Resources.dto.OwnerDTO;
import com.softserve.edu.Resources.dto.SearchDTO;
import com.softserve.edu.Resources.dto.ValidationErrorDTO;
import com.softserve.edu.Resources.entity.Address;
import com.softserve.edu.Resources.entity.Company;
import com.softserve.edu.Resources.entity.Owner;
import com.softserve.edu.Resources.entity.Person;
import com.softserve.edu.Resources.util.QueryBuilder;
import com.softserve.edu.Resources.util.ValidationDTOUtility;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OwnerServiceImplTest {

    private static long ownerId;
    private static long nonExistingOwnerId;
    private static Owner transientOrEmptyOwner;
    private static Owner persistentOwner;
    private static List<Owner> allOwners;
    private static List<Owner> allCompanies;
    private static List<Owner> allPersons;
    private static Optional findByIdOwner;
    private static ValidationErrorDTO validationErrorDTO;
    private static SearchDTO searchDTO;
    private static SearchDTO searchNonExistingOwnerDTO;
    private static String expectedQuery;
    private static String expectedQueryWithEmptyValues;

    @Mock
    private OwnerDAO ownerDAO;

    @Mock
    private ValidationDTOUtility utility;

    @Mock
    private QueryBuilder queryBuilder;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private OwnerServiceImpl ownerService;


    @BeforeClass
    public static void setUp() {
        ownerId = 5L;
        nonExistingOwnerId = 105L;
        transientOrEmptyOwner = new Person();
        persistentOwner = new Person()
                .setFirstName("Oleh")
                .setLastName("Tsebak")
                .setAddress(mock(Address.class))
                .setId(ownerId);
        allOwners = Arrays.asList(new Person()
                .setFirstName("Oleh")
                .setLastName("Tsebak")
                .setAddress(mock(Address.class)));
        allCompanies = Arrays.asList(new Company(), new Company());
        allPersons = Arrays.asList(new Person(), new Person());
        findByIdOwner = Optional.ofNullable(transientOrEmptyOwner);
        validationErrorDTO = new ValidationErrorDTO();

        Map<String, String> fieldsANdValues = new TreeMap<>();
        fieldsANdValues.put("first_name", "Oleh");
        fieldsANdValues.put("last_name", "Tsebak");
        searchDTO = new SearchDTO();
        searchDTO.setEntityType("Person");
        searchDTO.setFieldsAndValues(fieldsANdValues);

        expectedQuery = "SELECT p FROM Person p WHERE " +
                "first_name=\'Oleh\' AND " +
                "last_name=\'Tsebak\' ";

        Map<String, String> fieldsANdValues_2 = new TreeMap<>();
        fieldsANdValues.put("first_name", "");
        fieldsANdValues.put("last_name", "");
        searchNonExistingOwnerDTO = new SearchDTO();
        searchDTO.setEntityType("Person");
        searchDTO.setFieldsAndValues(fieldsANdValues);

        expectedQueryWithEmptyValues = "SELECT p FROM Person p WHERE " +
                "first_name=\'\' AND " +
                "last_name=\'\' ";
    }

    @Test
    public void saveOwner() {
        when(ownerDAO.makePersistent(any(Owner.class))).thenReturn(persistentOwner);

        Owner savedOwner = ownerService.addOwner(transientOrEmptyOwner);

        assertEquals(persistentOwner, savedOwner);
        assertEquals(savedOwner.getId(), ownerId);
        verify(ownerDAO, times(1)).makePersistent(transientOrEmptyOwner);
    }

    @Test
    public void getById() {
        when(ownerDAO.findById(ownerId)).thenReturn(findByIdOwner);

        assertEquals(ownerService.getOwnerById(ownerId), findByIdOwner.get());
        verify(ownerDAO, times(1)).findById(ownerId);
    }

    @Test
    public void getByIdNull() {
        Optional<Owner> emptyOwner = Optional.ofNullable(new Person());
        when(ownerDAO.findById(nonExistingOwnerId)).thenReturn(emptyOwner);

        assertEquals(ownerService.getOwnerById(nonExistingOwnerId), emptyOwner.get());
        verify(ownerDAO, times(1)).findById(nonExistingOwnerId);
    }

    @Test
    public void updateOwner() {
        ownerService.updateOwner(persistentOwner);

        verify(ownerDAO, times(1)).makePersistent(persistentOwner);
    }

    @Test
    public void deleteAddress(){
        ownerService.deleteOwnerById(ownerId);

        verify(ownerDAO, times(1)).deleteOwnerById(ownerId);
    }

    @Test
    public void getAllOwners(){
        when(ownerDAO.findAll()).thenReturn(allOwners);

        List<Owner> ownersFromDB = ownerService.getAllOwners();

        assertEquals(allOwners, ownersFromDB);
        verify(ownerDAO, times(1)).findAll();
    }

    @Test
    public void getAllCompanies(){
        when(ownerDAO.getAllCompanies()).thenReturn(allCompanies);

        List<Owner> companiesFromDB = ownerService.getAllCompanies();

        assertEquals(allCompanies, companiesFromDB);
        assertEquals(allCompanies.get(0).getClass(), Company.class);
        verify(ownerDAO, times(1)).getAllCompanies();
    }


    @Test
    public void getAllPersons(){
        when(ownerDAO.getAllPersons()).thenReturn(allPersons);

        List<Owner> personsFromDB = ownerService.getAllPersons();

        assertEquals(allPersons, personsFromDB);
        assertEquals(allPersons.get(0).getClass(), Person.class);
        verify(ownerDAO, times(1)).getAllPersons();
    }

    @Test
    public void fromOwnerToDTOTest(){
        List<OwnerDTO> ownerDTOS = ownerService.fromOwnerToOwnerDto(allOwners);

        assertEquals(ownerDTOS.size(), allOwners.size());
    }

    @Test
    public void validationDTOTest(){
        ArrayList<FieldError> fieldErrors = new ArrayList<>();
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        when(utility.getErrorDTO(bindingResult)).thenReturn(validationErrorDTO);

        ValidationErrorDTO resultValidationErrorDTO = ownerService.getValidationDTO(bindingResult);

        assertEquals(resultValidationErrorDTO, validationErrorDTO);
        verify(utility, times(1)).getErrorDTO(bindingResult);
    }
}

