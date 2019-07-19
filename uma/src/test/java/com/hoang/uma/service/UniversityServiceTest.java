package com.hoang.uma.service;

import com.hoang.uma.common.dto.CourseDto;
import com.hoang.uma.common.dto.DepartmentDto;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.dto.UniversityDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.persistence.AppDao;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.persistence.UniversityDao;
import com.hoang.uma.service.model.Course;
import com.hoang.uma.service.model.Department;
import com.hoang.uma.service.model.Domain;
import com.hoang.uma.service.model.University;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class UniversityServiceTest {

    private UniversityService service = new UniversityServiceImpl();

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private GenericDao genericDao;

    @MockBean
    private AppDao appDao;

    @MockBean
    private UniversityDao universityDao;

    @MockBean
    private CriteriaBuilder criteriaBuilder;

    @MockBean
    private CriteriaQuery criteriaQuery;

    @MockBean
    private CriteriaQuery countCriteriaQuery;

    @MockBean
    private TypedQuery query;

    private Domain country;
    private University university1;
    private University university2;
    private University university3;
    private String notFoundId = "4";
    private Department department1;
    private Department department2;
    private Department department3;
    private Course course1;
    private Course course2;
    private Course course3;
    private Date now = new Date();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "beanMapper", ModelMapperFactory.create(), ModelMapper.class);
        //ReflectionTestUtils.setField(service, "beanMapper", DozerFactory.create(), DozerBeanMapper.class);
        ReflectionTestUtils.setField(service, "entityManager", entityManager, EntityManager.class);
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);
        ReflectionTestUtils.setField(service, "appDao", appDao, AppDao.class);
        ReflectionTestUtils.setField(service, "universityDao", universityDao, UniversityDao.class);

        country = new Domain();
        country.setId("1");
        country.setCode("VNI");
        country.setName("Vietnam");
        country.setType("Country");

        university1 = new University();
        university1.setId("1");
        university1.setName("name1");
        university1.setCreatedAt(now);
        university1.setUpdatedAt(now);
        university1.setVersion(1);
        university1.setCountry(country);

        university2 = new University();
        university2.setId("2");
        university2.setName("name2");
        university2.setCreatedAt(now);
        university2.setUpdatedAt(now);
        university2.setVersion(1);
        university2.setCountry(country);

        university3 = new University();
        university3.setId("3");
        university3.setName("name3");
        university3.setCreatedAt(now);
        university3.setUpdatedAt(now);
        university3.setVersion(1);
        university3.setCountry(country);

        department1 = new Department();
        department1.setId("1");
        department1.setName("name1");
        department1.setVersion(1);
        department1.setUniversity(university1);

        department2 = new Department();
        department2.setId("2");
        department2.setName("name2");
        department2.setVersion(1);
        department2.setUniversity(university1);

        department3 = new Department();
        department3.setId("3");
        department3.setName("name3");
        department3.setVersion(1);
        department3.setUniversity(university1);

        course1 = new Course();
        course1.setId("1");
        course1.setName("name1");
        course1.setNumberOfCredits(1);
        course1.setVersion(1);
        course1.setDepartment(department1);

        course2 = new Course();
        course2.setId("2");
        course2.setName("name2");
        course2.setNumberOfCredits(2);
        course2.setVersion(1);
        course2.setDepartment(department1);

        course3 = new Course();
        course3.setId("3");
        course3.setName("name3");
        course3.setNumberOfCredits(3);
        course3.setVersion(1);
        course3.setDepartment(department1);
    }

    @Test
    public void shouldThrowExceptionIfCreateDepartmentWithExistentName () {
        DepartmentCreateModel createModel = new DepartmentCreateModel();
        createModel.setName("name1");

        BDDMockito.given(genericDao.read(University.class, university1.getId())).willReturn(university1);
        BDDMockito.given(universityDao.isDepartmentNameExistent(createModel.getName(), university1.getId())).willReturn(true);

        try {
            service.createDepartment(university1.getId(), createModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.DEPARTMENT_NAME_EXISTS, be.getCode());
            Assert.assertEquals(String.format(BusinessException.DEPARTMENT_NAME_EXISTS_MESSAGE, createModel.getName()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreateUniversityWithNotFoundUniversity () {
        DepartmentCreateModel createModel = new DepartmentCreateModel();
        createModel.setName("name1");

        BDDMockito.given(genericDao.read(University.class, notFoundId)).willReturn(null);

        try {
            service.createDepartment(notFoundId, createModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.UNIVERSITY_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreateUniversityWithExistentName () {
        UniversityCreateModel createModel = new UniversityCreateModel();
        createModel.setName("name1");
        createModel.setCountryId("1");

        BDDMockito.given(appDao.isNameExistent(University.class, createModel.getName())).willReturn(true);

        try {
            service.create(createModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.UNIVERSITY_NAME_EXISTS, be.getCode());
            Assert.assertEquals(String.format(BusinessException.UNIVERSITY_NAME_EXISTS_MESSAGE, createModel.getName()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreateUniversityWithNotFoundCountry () {
        UniversityCreateModel createModel = new UniversityCreateModel();
        createModel.setName("name1");
        createModel.setCountryId("1");

        BDDMockito.given(genericDao.read(Domain.class, createModel.getCountryId())).willReturn(null);

        try {
            service.create(createModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.COUNTRY_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.COUNTRY_NOT_FOUND_MESSAGE, createModel.getCountryId()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfDeleteNonExistentUniversity () {
        BDDMockito.given(genericDao.read(University.class, notFoundId)).willReturn(null);

        try {
            service.delete(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.UNIVERSITY_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfGetNonExistentUniversity () {
        BDDMockito.given(genericDao.read(University.class, notFoundId)).willReturn(null);

        try {
            service.get(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.UNIVERSITY_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfUpdateNonExistentUniversity () {
        BDDMockito.given(genericDao.read(University.class, notFoundId)).willReturn(null);

        try {
            service.get(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.UNIVERSITY_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void testCreateCourse () throws BusinessException {
        CourseCreateModel createModel = new CourseCreateModel();
        createModel.setName("name1");
        createModel.setNumberOfCredits(1);

        BDDMockito.given(genericDao.read(Department.class, university1.getId())).willReturn(department1);
        BDDMockito.given(universityDao.isCourseNameExistent(createModel.getName(), department1.getId())).willReturn(false);

        Assert.assertNotNull(service.createCourse(department1.getId(), createModel));
    }

    @Test
    public void testCreateDepartment () throws BusinessException {
        DepartmentCreateModel createModel = new DepartmentCreateModel();
        createModel.setName("name1");

        BDDMockito.given(genericDao.read(University.class, university1.getId())).willReturn(university1);
        BDDMockito.given(universityDao.isDepartmentNameExistent(createModel.getName(), university1.getId())).willReturn(false);

        Assert.assertNotNull(service.createDepartment(university1.getId(), createModel));
    }

    @Test
    public void testCreateUniversity () throws BusinessException {
        UniversityCreateModel createModel = new UniversityCreateModel();
        createModel.setName("name1");
        createModel.setCountryId("1");

        BDDMockito.given(appDao.isNameExistent(University.class, createModel.getName())).willReturn(false);
        BDDMockito.given(genericDao.read(Domain.class, createModel.getCountryId())).willReturn(country);

        Assert.assertNotNull(service.create(createModel));
    }

    @Test
    public void testDeleteCourse () throws BusinessException {
        BDDMockito.given(genericDao.read(Course.class, course1.getId())).willReturn(course1);

        service.deleteCourse(course1.getId());
    }

    @Test
    public void testDeleteDepartment () throws BusinessException {
        BDDMockito.given(genericDao.read(Department.class, department1.getId())).willReturn(department1);

        service.deleteDepartment(department1.getId());
    }

    @Test
    public void testDeleteUniversity () throws BusinessException {
        BDDMockito.given(genericDao.read(University.class, university1.getId())).willReturn(university1);

        service.delete(university1.getId());
    }

    @Test
    public void testGetCourse () throws BusinessException {
        BDDMockito.given(genericDao.read(Course.class, course1.getId())).willReturn(course1);

        CourseDto courseDto = service.getCourse(course1.getId());
        Assert.assertEquals(course1.getId(), courseDto.getId());
        Assert.assertEquals(course1.getName(), courseDto.getName());
        Assert.assertEquals(course1.getNumberOfCredits(), courseDto.getNumberOfCredits());
    }

    @Test
    public void testGetDepartment () throws BusinessException {
        BDDMockito.given(genericDao.read(Department.class, department1.getId())).willReturn(department1);

        DepartmentDto departmentDto = service.getDepartment(department1.getId());
        Assert.assertEquals(department1.getId(), departmentDto.getId());
        Assert.assertEquals(department1.getName(), departmentDto.getName());
        Assert.assertEquals(department1.getUniversity().getName(), departmentDto.getUniversityName());
    }

    @Test
    public void testGetUniversity () throws BusinessException {
        BDDMockito.given(genericDao.read(University.class, university1.getId())).willReturn(university1);

        UniversityDto universityDto = service.get(university1.getId());
        Assert.assertEquals(university1.getId(), universityDto.getId());
        Assert.assertEquals(university1.getName(), universityDto.getName());
    }

    @Test
    public void testSearchCourses () throws BusinessException {
        List list = new ArrayList();
        list.add(course1);
        list.add(course2);
        list.add(course3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(Course.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(list);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<CourseDto> searchResult = service.searchCourses(new CourseSearchCriteria());
        List<CourseDto> dtos = searchResult.getList();
        for (int i = 1; i <= dtos.size(); i++) {
            CourseDto dto = dtos.get(i-1);
            Assert.assertEquals("" + i, dto.getId());
            Assert.assertEquals("name" + i, dto.getName());
            Assert.assertEquals(i, dto.getNumberOfCredits());
        }
    }

    @Test
    public void testSearchDepartments () throws BusinessException {
        List list = new ArrayList();
        list.add(department1);
        list.add(department2);
        list.add(department3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(Department.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(list);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<DepartmentDto> searchResult = service.searchDepartments(new DepartmentSearchCriteria());
        List<DepartmentDto> dtos = searchResult.getList();
        for (int i = 1; i <= dtos.size(); i++) {
            DepartmentDto dto = dtos.get(i-1);
            Assert.assertEquals("" + i, dto.getId());
            Assert.assertEquals("name" + i, dto.getName());
        }
    }

    @Test
    public void testSearchUniversities () throws BusinessException {
        List list = new ArrayList();
        list.add(university1);
        list.add(university2);
        list.add(university3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(University.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(list);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<UniversityDto> searchResult = service.search(new UniversitySearchCriteria());
        List<UniversityDto> dtos = searchResult.getList();
        for (int i = 1; i <= dtos.size(); i++) {
            UniversityDto dto = dtos.get(i-1);
            Assert.assertEquals("" + i, dto.getId());
            Assert.assertEquals("name" + i, dto.getName());
            Assert.assertEquals("VNI", dto.getCountry().getCode());
            Assert.assertEquals("Vietnam", dto.getCountry().getName());
            Assert.assertEquals("Country", dto.getCountry().getType());
        }
    }

    @Test
    public void testUpdateCourse () throws BusinessException {
        CourseUpdateModel updateModel = new CourseUpdateModel();
        updateModel.setName("name1u");
        updateModel.setNumberOfCredits(2);

        BDDMockito.given(genericDao.read(Course.class, course1.getId())).willReturn(course1);
        BDDMockito.given(genericDao.update(course1)).willReturn(course1);

        Assert.assertNotNull(service.updateCourse(course1.getId(), updateModel));
    }

    @Test
    public void testUpdateDepartment () throws BusinessException {
        DepartmentUpdateModel updateModel = new DepartmentUpdateModel();
        updateModel.setName("name1u");

        BDDMockito.given(genericDao.read(Department.class, department1.getId())).willReturn(department1);
        BDDMockito.given(genericDao.update(department1)).willReturn(department1);

        Assert.assertNotNull(service.updateDepartment(department1.getId(), updateModel));
    }

    @Test
    public void testUpdateUniversity () throws BusinessException {
        UniversityUpdateModel updateModel = new UniversityUpdateModel();
        updateModel.setName("name1u");

        BDDMockito.given(genericDao.read(University.class, university1.getId())).willReturn(university1);
        BDDMockito.given(genericDao.update(university1)).willReturn(university1);

        Assert.assertNotNull(service.update(university1.getId(), updateModel));
    }

}
