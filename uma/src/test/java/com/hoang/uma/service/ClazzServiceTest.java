package com.hoang.uma.service;

import com.hoang.uma.common.dto.ClazzDto;
import com.hoang.uma.common.enu.ClazzStatus;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.ClazzCreateModel;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.*;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.view_model.ClazzSearchCriteria;
import com.hoang.uma.common.view_model.ClazzUpdateModel;
import com.hoang.uma.persistence.AppDao;
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
public class ClazzServiceTest {

    private ClazzService service = new ClazzServiceImpl();

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private GenericDao genericDao;

    @MockBean
    private AppDao appDao;

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
    private Department department1;
    private Course course1;
    private Teacher teacher1;
    private Teacher teacher2;
    private Clazz clazz1;
    private Clazz clazz2;
    private Clazz clazz3;
    private Student student1;
    private Student student2;
    private String notFoundId = "4";
    private Date now = new Date();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "beanMapper", ModelMapperFactory.create(), ModelMapper.class);
        //ReflectionTestUtils.setField(service, "beanMapper", DozerFactory.create(), DozerBeanMapper.class);
        ReflectionTestUtils.setField(service, "entityManager", entityManager, EntityManager.class);
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);
        ReflectionTestUtils.setField(service, "appDao", appDao, AppDao.class);

        country = new Domain();
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

        department1 = new Department();
        department1.setId("1");
        department1.setName("name1");
        department1.setVersion(1);
        department1.setUniversity(university1);

        course1 = new Course();
        course1.setId("1");
        course1.setName("name1");
        course1.setNumberOfCredits(1);
        course1.setVersion(1);
        course1.setDepartment(department1);

        Role roleTeacher = new Role();
        roleTeacher.setName("ROLE_TEACHER");
        UserRole userRoleTeacher1 = new UserRole();
        userRoleTeacher1.setRole(roleTeacher);
        UserRole userRoleTeacher2 = new UserRole();
        userRoleTeacher2.setRole(roleTeacher);
        teacher1 = new Teacher();
        teacher1.setId("1");
        teacher1.setEmail("email1");
        teacher1.setName("name1");
        teacher1.setPassword("pass1");
        teacher1.setAge(11);
        teacher1.setCreatedAt(now);
        teacher1.setUpdatedAt(now);
        teacher1.setVersion(1);
        teacher1.setUserRoles(new ArrayList<>());
        teacher1.getUserRoles().add(userRoleTeacher1);

        teacher2 = new Teacher();
        teacher2.setId("2");
        teacher2.setEmail("email2");
        teacher2.setName("name2");
        teacher2.setPassword("pass2");
        teacher2.setAge(22);
        teacher2.setCreatedAt(now);
        teacher2.setUpdatedAt(now);
        teacher2.setVersion(1);
        teacher2.setUserRoles(new ArrayList<>());
        teacher2.getUserRoles().add(userRoleTeacher2);

        clazz1 = new Clazz();
        clazz1.setId("1");
        clazz1.setDuration(1);
        clazz1.setRoom("1");
        clazz1.setStatus(ClazzStatus.NEW);
        clazz1.setStartedAt(now);
        clazz1.setEndedAt(now);
        clazz1.setVersion(1);
        clazz1.setCourse(course1);
        clazz1.setTeacher(teacher1);
        clazz1.setClazzStudents(new ArrayList<>());

        clazz2 = new Clazz();
        clazz2.setId("2");
        clazz2.setDuration(2);
        clazz2.setRoom("2");
        clazz2.setStatus(ClazzStatus.STARTED);
        clazz2.setStartedAt(now);
        clazz2.setEndedAt(now);
        clazz2.setVersion(1);
        clazz2.setCourse(course1);
        clazz2.setTeacher(teacher1);

        clazz3 = new Clazz();
        clazz3.setId("3");
        clazz3.setDuration(3);
        clazz3.setRoom("3");
        clazz3.setStatus(ClazzStatus.ENDED);
        clazz3.setStartedAt(now);
        clazz3.setEndedAt(now);
        clazz3.setVersion(1);
        clazz3.setCourse(course1);
        clazz3.setTeacher(teacher1);

        Role roleStudent = new Role();
        roleStudent.setName("ROLE_STUDENT");
        UserRole userRoleStudent1 = new UserRole();
        userRoleStudent1.setRole(roleStudent);
        UserRole userRoleStudent2 = new UserRole();
        userRoleStudent2.setRole(roleStudent);

        student1 = new Student();
        student1.setId("1");
        student1.setEmail("email1");
        student1.setName("name1");
        student1.setPassword("pass1");
        student1.setAge(11);
        student1.setCreatedAt(now);
        student1.setUpdatedAt(now);
        student1.setUserRoles(new ArrayList<>());
        student1.setYear(1);
        student1.setVersion(1);
        student1.getUserRoles().add(userRoleStudent1);

        student2 = new Student();
        student2.setId("2");
        student2.setEmail("email2");
        student2.setName("name2");
        student2.setPassword("pass2");
        student2.setAge(22);
        student2.setCreatedAt(now);
        student2.setUpdatedAt(now);
        student2.setUserRoles(new ArrayList<>());
        student2.setYear(2);
        student2.setVersion(1);
        student2.getUserRoles().add(userRoleStudent2);

        ClazzStudent clazzStudent = new ClazzStudent();
        clazzStudent.setClazz(clazz2);
        clazzStudent.setStudent(student2);
        clazz2.setClazzStudents(new ArrayList<>());
        clazz2.getClazzStudents().add(clazzStudent);
    }

    @Test
    public void testCancel () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, course1.getId())).willReturn(clazz1);

        Assert.assertNotNull(service.cancel(clazz1.getId()));
    }

    @Test
    public void testCreate () throws BusinessException {
        ClazzCreateModel createModel = new ClazzCreateModel();
        createModel.setDuration(1);
        createModel.setRoom("1");
        createModel.setStartedAt(now);
        createModel.setEndedAt(now);

        BDDMockito.given(genericDao.read(Course.class, course1.getId())).willReturn(course1);

        Assert.assertNotNull(service.create(course1.getId(), createModel));
    }

    @Test
    public void testCreateWithTeacher () throws BusinessException {
        ClazzCreateModel createModel = new ClazzCreateModel();
        createModel.setDuration(1);
        createModel.setRoom("1");
        createModel.setStartedAt(now);
        createModel.setEndedAt(now);
        createModel.setTeacherId(teacher1.getId());

        BDDMockito.given(genericDao.read(Course.class, course1.getId())).willReturn(course1);
        BDDMockito.given(genericDao.read(Teacher.class, teacher1.getId())).willReturn(teacher1);

        Assert.assertNotNull(service.create(course1.getId(), createModel));
    }

    @Test
    public void testDelete () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);

        service.delete(clazz1.getId());
    }

    @Test
    public void testEnd () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, clazz2.getId())).willReturn(clazz2);

        Assert.assertNotNull(service.end(clazz2.getId()));
    }

    @Test
    public void testGet () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);

        ClazzDto clazzDto = service.get(clazz1.getId());
        Assert.assertEquals(clazz1.getDuration(), clazzDto.getDuration());
        Assert.assertEquals(clazz1.getRoom(), clazzDto.getRoom());
        Assert.assertEquals(clazz1.getStartedAt().toString(), clazzDto.getStartedAt());
        Assert.assertEquals(clazz1.getEndedAt().toString(), clazzDto.getEndedAt());
        Assert.assertEquals(clazz1.getTeacher().getId(), clazzDto.getTeacher().getId());
    }

    @Test
    public void testRegister () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);
        BDDMockito.given(appDao.findByEmail(Student.class, student1.getEmail())).willReturn(student1);

        service.register(clazz1.getId(), student1.getEmail());
    }

    @Test
    public void testSearchCourses () throws BusinessException {
        List list = new ArrayList();
        list.add(clazz1);
        list.add(clazz2);
        list.add(clazz3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(Clazz.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(list);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<ClazzDto> searchResult = service.search(new ClazzSearchCriteria());
        List<ClazzDto> dtos = searchResult.getList();
        for (int i = 1; i <= dtos.size(); i++) {
            ClazzDto dto = dtos.get(i-1);
            Assert.assertEquals("" + i, dto.getId());
            Assert.assertEquals(i, dto.getDuration());
            Assert.assertEquals("" + i, dto.getRoom());
            Assert.assertEquals(now.toString(), dto.getStartedAt());
            Assert.assertEquals(now.toString(), dto.getEndedAt());
            Assert.assertEquals("1", dto.getTeacher().getId());
        }
    }

    @Test
    public void testStart () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, course1.getId())).willReturn(clazz1);

        Assert.assertNotNull(service.start(clazz1.getId()));
    }

    @Test
    public void testUnregister () throws BusinessException {
        BDDMockito.given(genericDao.read(Clazz.class, clazz2.getId())).willReturn(clazz2);
        BDDMockito.given(appDao.findByEmail(Student.class, student2.getEmail())).willReturn(student2);

        service.unregister(clazz2.getId(), student2.getEmail());
    }

    @Test
    public void testUpdate () throws BusinessException {
        Date otherNow = new Date();
        ClazzUpdateModel updateModel = new ClazzUpdateModel();
        updateModel.setDuration(2);
        updateModel.setRoom("22");
        updateModel.setStartedAt(otherNow);
        updateModel.setEndedAt(otherNow);

        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);
        BDDMockito.given(genericDao.update(clazz1)).willReturn(clazz1);

        Assert.assertNotNull(service.update(clazz1.getId(), updateModel));
    }

    @Test
    public void testUpdateWithTeacher () throws BusinessException {
        Date otherNow = new Date();
        ClazzUpdateModel updateModel = new ClazzUpdateModel();
        updateModel.setDuration(2);
        updateModel.setRoom("22");
        updateModel.setStartedAt(otherNow);
        updateModel.setEndedAt(otherNow);
        updateModel.setTeacherId(teacher2.getId());

        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);
        BDDMockito.given(genericDao.read(Teacher.class, teacher2.getId())).willReturn(teacher2);
        BDDMockito.given(genericDao.update(clazz1)).willReturn(clazz1);

        Assert.assertNotNull(service.update(clazz1.getId(), updateModel));
    }

    @Test
    public void shouldThrowExceptionIfCancelEndedClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, clazz3.getId())).willReturn(clazz3);

        try {
            service.cancel(clazz3.getId());
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_CANCEL_ENDED, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_CANCEL_ENDED_MESSAGE, clazz3.getId()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCancelNonExistentClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, notFoundId)).willReturn(null);

        try {
            service.cancel(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfCreateClazzWithNonExistentCourse () {
        BDDMockito.given(genericDao.read(Course.class, notFoundId)).willReturn(null);

        try {
            service.create(notFoundId, new ClazzCreateModel());
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.COURSE_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.COURSE_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfDeleteNonExistentClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, notFoundId)).willReturn(null);

        try {
            service.delete(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfEndNewClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);

        try {
            service.end(clazz1.getId());
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_END_NEW, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_END_NEW_MESSAGE, clazz1.getId()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfEndNonExistentClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, notFoundId)).willReturn(null);

        try {
            service.end(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfGetNonExistentClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, notFoundId)).willReturn(null);

        try {
            service.get(notFoundId);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfStartEndedClazz () {
        BDDMockito.given(genericDao.read(Clazz.class, clazz3.getId())).willReturn(clazz3);

        try {
            service.start(clazz3.getId());
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_START_ENDED, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_START_ENDED_MESSAGE, clazz3.getId()), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfUpdateNonExistentClazz () {
        ClazzUpdateModel updateModel = new ClazzUpdateModel();

        BDDMockito.given(genericDao.read(Clazz.class, notFoundId)).willReturn(null);

        try {
            service.update(notFoundId, updateModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_NOT_FOUND, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, notFoundId), be.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionIfUpdateInvalidDuration () {
        ClazzUpdateModel updateModel = new ClazzUpdateModel();
        updateModel.setDuration(4);

        BDDMockito.given(genericDao.read(Clazz.class, clazz1.getId())).willReturn(clazz1);

        try {
            service.update(clazz1.getId(), updateModel);
        }
        catch (BusinessException be) {
            Assert.assertEquals(BusinessException.CLAZZ_DURATION_INVALID, be.getCode());
            Assert.assertEquals(String.format(BusinessException.CLAZZ_DURATION_INVALID_MESSAGE, clazz1.getId()), be.getMessage());
        }
    }

}
