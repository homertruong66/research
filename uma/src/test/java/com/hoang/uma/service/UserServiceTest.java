package com.hoang.uma.service;

import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.dto.StudentDto;
import com.hoang.uma.common.dto.TeacherDto;
import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.persistence.AppDao;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.*;
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

import static org.mockito.Matchers.*;

/**
 * homertruong
 */

@RunWith(SpringRunner.class)
public class UserServiceTest {

    private UserService service = new UserServiceImpl();

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

    private User user1;
    private User user2;
    private User user3;
    private Teacher teacher1;
    private Teacher teacher2;
    private Teacher teacher3;
    private Department department;
    private Student student1;
    private Student student2;
    private Student student3;
    private String notFoundId;
    Role roleUser;
    Role roleAdmin;
    Role roleTeacher;
    Role roleStudent;
    private Date now = new Date();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "beanMapper", ModelMapperFactory.create(), ModelMapper.class);
        //ReflectionTestUtils.setField(service, "beanMapper", DozerFactory.create(), DozerBeanMapper.class);
        ReflectionTestUtils.setField(service, "entityManager", entityManager, EntityManager.class);
        ReflectionTestUtils.setField(service, "genericDao", genericDao, GenericDao.class);
        ReflectionTestUtils.setField(service, "appDao", appDao, AppDao.class);

        roleUser = new Role();
        roleUser.setName("ROLE_USER");
        UserRole userRoleUser1 = new UserRole();
        userRoleUser1.setRole(roleUser);
        UserRole userRoleUser3 = new UserRole();
        userRoleUser3.setRole(roleUser);

        roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        UserRole userRoleAdmin2 = new UserRole();
        userRoleAdmin2.setRole(roleAdmin);
        UserRole userRoleAdmin3 = new UserRole();
        userRoleAdmin3.setRole(roleAdmin);

        user1 = new User();
        user2 = new User();
        user3 = new User();
        this.setData(user1, user2, user3);
        user1.getUserRoles().add(userRoleUser1);
        user2.getUserRoles().add(userRoleAdmin2);
        user3.getUserRoles().add(userRoleUser3);
        user3.getUserRoles().add(userRoleAdmin3);

        // ------
        roleTeacher = new Role();
        roleTeacher.setName("ROLE_TEACHER");
        UserRole userRoleTeacher1 = new UserRole();
        userRoleTeacher1.setRole(roleTeacher);
        UserRole userRoleTeacher2 = new UserRole();
        userRoleTeacher2.setRole(roleTeacher);
        UserRole userRoleTeacher3 = new UserRole();
        userRoleTeacher3.setRole(roleTeacher);

        teacher1 = new Teacher();
        teacher2 = new Teacher();
        teacher3 = new Teacher();
        this.setData(teacher1, teacher2, teacher3);
        teacher1.setDegree("degree1");
        teacher2.setDegree("degree2");
        teacher3.setDegree("degree3");
        teacher1.getUserRoles().add(userRoleTeacher1);
        teacher2.getUserRoles().add(userRoleTeacher2);
        teacher3.getUserRoles().add(userRoleTeacher3);

        // ------
        roleStudent = new Role();
        roleStudent.setName("ROLE_STUDENT");
        UserRole userRoleStudent1 = new UserRole();
        userRoleStudent1.setRole(roleStudent);
        UserRole userRoleStudent2 = new UserRole();
        userRoleStudent2.setRole(roleStudent);
        UserRole userRoleStudent3 = new UserRole();
        userRoleStudent3.setRole(roleStudent);

        department = new Department();
        department.setId("1");
        department.setName("Dept1");
        department.setVersion(1);
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        this.setData(student1, student2, student3);
        student1.setYear(1);
        student2.setYear(2);
        student3.setYear(3);
        student1.getUserRoles().add(userRoleStudent1);
        student2.getUserRoles().add(userRoleStudent2);
        student3.getUserRoles().add(userRoleStudent3);
    }

    @Test
    public void testAssignTeacher () throws BusinessException {
        List<String> departmentIds = new ArrayList<>();
        departmentIds.add("1");
        departmentIds.add("2");
        departmentIds.add("3");
        teacher1.setDepartmentTeachers(new ArrayList<>());

        BDDMockito.given(genericDao.read(Teacher.class, teacher1.getId())).willReturn(teacher1);

        service.assignTeacher(teacher1.getId(), departmentIds);
    }

    @Test
    public void testCreateStudent () throws BusinessException {
        StudentCreateModel createModel = new StudentCreateModel();
        createModel.setEmail("email1");
        createModel.setName("name1");
        createModel.setPassword("pass1");
        createModel.setAge(11);
        createModel.setYear(1);

        BDDMockito.given(genericDao.read(Department.class, department.getId())).willReturn(department);
        BDDMockito.given(appDao.isEmailExistent(Student.class, createModel.getEmail())).willReturn(false);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_STUDENT")).willReturn(roleStudent);

        Assert.assertNotNull(service.createStudent(department.getId(), createModel));
    }

    @Test
    public void testCreateTeacher () throws BusinessException {
        TeacherCreateModel createModel = new TeacherCreateModel();
        createModel.setEmail("email1");
        createModel.setName("name1");
        createModel.setPassword("pass1");
        createModel.setAge(11);
        createModel.setDegree("Bachelor");

        BDDMockito.given(appDao.isEmailExistent(Teacher.class, createModel.getEmail())).willReturn(false);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_TEACHER")).willReturn(roleTeacher);

        Assert.assertNotNull(service.createTeacher(createModel));
    }

    @Test
    public void testCreateTeachers () throws BusinessException {
        TeacherCreateModel createModel1 = new TeacherCreateModel();
        createModel1.setEmail("email1");
        createModel1.setName("name1");
        createModel1.setPassword("pass1");
        createModel1.setAge(11);
        createModel1.setDegree("degree1");

        TeacherCreateModel createModel2 = new TeacherCreateModel();
        createModel1.setEmail("email2");
        createModel1.setName("name2");
        createModel1.setPassword("pass2");
        createModel1.setAge(22);
        createModel1.setDegree("degree2");

        TeacherCreateModel createModel3 = new TeacherCreateModel();
        createModel1.setEmail("email3");
        createModel1.setName("name3");
        createModel1.setPassword("pass3");
        createModel1.setAge(33);
        createModel1.setDegree("degree3");

        List<TeacherCreateModel> createModels = new ArrayList<>();
        createModels.add(createModel1);
        createModels.add(createModel2);
        createModels.add(createModel3);

        BDDMockito.given(appDao.isEmailExistent(Teacher.class, eq(anyString()))).willReturn(false);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_TEACHER")).willReturn(roleTeacher);

        service.createTeachers(createModels);
    }

    @Test
    public void testCreateUser () throws BusinessException {
        UserCreateModel createModel = new UserCreateModel();
        createModel.setEmail("email1");
        createModel.setName("name1");
        createModel.setPassword("pass1");
        createModel.setAge(11);
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        createModel.setRoles(roles);

        BDDMockito.given(appDao.isEmailExistent(User.class, createModel.getEmail())).willReturn(false);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_USER")).willReturn(roleUser);

        Assert.assertNotNull(service.create(createModel));
    }

    @Test
    public void testDeleteStudent () throws BusinessException {
        BDDMockito.given(genericDao.read(Student.class, student1.getId())).willReturn(student1);

        service.deleteStudent(student1.getId());
    }

    @Test
    public void testDeleteTeacher () throws BusinessException {
        BDDMockito.given(genericDao.read(Teacher.class, teacher1.getId())).willReturn(teacher1);

        service.deleteTeacher(teacher1.getId());
    }

    @Test
    public void testDeleteUser () throws BusinessException {
        BDDMockito.given(genericDao.read(User.class, user1.getId())).willReturn(user1);

        service.delete(user1.getId());
    }

    @Test
    public void testGetStudent () throws BusinessException {
        BDDMockito.given(genericDao.read(Student.class, student1.getId())).willReturn(student1);

        StudentDto studentDto = service.getStudent(student1.getId());
        Assert.assertEquals(student1.getId(), studentDto.getId());
        Assert.assertEquals(student1.getEmail(), studentDto.getEmail());
        Assert.assertEquals(student1.getName(), studentDto.getName());
        Assert.assertEquals(student1.getPassword(), studentDto.getPassword());
        Assert.assertEquals(student1.getYear(), studentDto.getYear());
        Assert.assertEquals(student1.getCreatedAt().toString(), studentDto.getCreatedAt());
        Assert.assertEquals(student1.getUpdatedAt().toString(), studentDto.getUpdatedAt());
    }

    @Test
    public void testGetTeacher () throws BusinessException {
        BDDMockito.given(genericDao.read(Teacher.class, teacher1.getId())).willReturn(teacher1);

        TeacherDto teacherDto = service.getTeacher(teacher1.getId());
        Assert.assertEquals(teacher1.getId(), teacherDto.getId());
        Assert.assertEquals(teacher1.getEmail(), teacherDto.getEmail());
        Assert.assertEquals(teacher1.getName(), teacherDto.getName());
        Assert.assertEquals(teacher1.getDegree(), teacherDto.getDegree());
        Assert.assertEquals(teacher1.getCreatedAt().toString(), teacherDto.getCreatedAt());
        Assert.assertEquals(teacher1.getUpdatedAt().toString(), teacherDto.getUpdatedAt());
    }

    @Test
    public void testGetUser () throws BusinessException {
        BDDMockito.given(genericDao.read(User.class, user1.getId())).willReturn(user1);

        UserDto userDto = service.get(user1.getId());
        Assert.assertEquals(user1.getId(), userDto.getId());
        Assert.assertEquals(user1.getEmail(), userDto.getEmail());
        Assert.assertEquals(user1.getName(), userDto.getName());
        Assert.assertEquals(user1.getPassword(), userDto.getPassword());
        Assert.assertEquals(user1.getCreatedAt().toString(), userDto.getCreatedAt());
        Assert.assertEquals(user1.getUpdatedAt().toString(), userDto.getUpdatedAt());
        Assert.assertEquals(user1.getUserRoles().size(), userDto.getRoles().size());
    }

    @Test
    public void testSearchStudents () throws BusinessException {
        List students = new ArrayList();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(Student.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(students);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<StudentDto> searchResult = service.searchStudents(new StudentSearchCriteria());
        List<StudentDto> studentDtos = searchResult.getList();
        for (int i = 1; i <= studentDtos.size(); i++) {
            StudentDto studentDto = studentDtos.get(i-1);
            Assert.assertEquals("" + i, studentDto.getId());
            Assert.assertEquals("email" + i, studentDto.getEmail());
            Assert.assertEquals("name" + i, studentDto.getName());
            Assert.assertEquals("pass" + i, studentDto.getPassword());
            Assert.assertEquals(i, studentDto.getYear());
            Assert.assertEquals(now.toString(), studentDto.getCreatedAt());
            Assert.assertEquals(now.toString(), studentDto.getUpdatedAt());
        }
    }

    @Test
    public void testSearchTeachers () throws BusinessException {
        List teachers = new ArrayList();
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(Teacher.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(teachers);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<TeacherDto> searchResult = service.searchTeachers(new TeacherSearchCriteria());
        List<TeacherDto> teacherDtos = searchResult.getList();
        for (int i = 1; i <= teacherDtos.size(); i++) {
            TeacherDto teacherDto = teacherDtos.get(i-1);
            Assert.assertEquals("" + i, teacherDto.getId());
            Assert.assertEquals("email" + i, teacherDto.getEmail());
            Assert.assertEquals("name" + i, teacherDto.getName());
            Assert.assertEquals("degree" + i, teacherDto.getDegree());
            Assert.assertEquals(now.toString(), teacherDto.getCreatedAt());
            Assert.assertEquals(now.toString(), teacherDto.getUpdatedAt());
        }
    }

    @Test
    public void testSearchUsers () throws BusinessException {
        List users = new ArrayList();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        BDDMockito.given(entityManager.getCriteriaBuilder()).willReturn(criteriaBuilder);
        BDDMockito.given(criteriaBuilder.createQuery(User.class)).willReturn(criteriaQuery);
        BDDMockito.given(criteriaQuery.select(any())).willReturn(criteriaQuery);
        BDDMockito.given(genericDao.search(any(), any(), any(), any())).willReturn(users);

        BDDMockito.given(criteriaBuilder.createQuery(Long.class)).willReturn(countCriteriaQuery);
        BDDMockito.given(entityManager.createQuery(countCriteriaQuery)).willReturn(query);
        BDDMockito.given(query.getSingleResult()).willReturn(3l);

        SearchResultDto<UserDto> searchResult = service.search(new UserSearchCriteria());
        List<UserDto> userDtos = searchResult.getList();
        for (int i = 1; i <= userDtos.size(); i++) {
            UserDto userDto = userDtos.get(i-1);
            Assert.assertEquals("" + i, userDto.getId());
            Assert.assertEquals("email" + i, userDto.getEmail());
            Assert.assertEquals("name" + i, userDto.getName());
            Assert.assertEquals("pass" + i, userDto.getPassword());
            Assert.assertEquals(now.toString(), userDto.getCreatedAt());
            Assert.assertEquals(now.toString(), userDto.getUpdatedAt());
        }
    }

    @Test
    public void testUpdateStudent () throws BusinessException {
        StudentUpdateModel updateModel = new StudentUpdateModel();
        updateModel.setName("name1u");
        updateModel.setAge(99);
        updateModel.setYear(2);

        BDDMockito.given(genericDao.read(Student.class, student1.getId())).willReturn(student1);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_STUDENT")).willReturn(roleStudent);
        BDDMockito.given(genericDao.update(student1)).willReturn(student1);

        Assert.assertNotNull(service.updateStudent(student1.getId(), updateModel));
    }

    @Test
    public void testUpdateTeacher () throws BusinessException {
        TeacherUpdateModel updateModel = new TeacherUpdateModel();
        updateModel.setName("name1u");
        updateModel.setAge(99);
        updateModel.setDegree("degreeu");

        BDDMockito.given(genericDao.read(Teacher.class, teacher1.getId())).willReturn(teacher1);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_TEACHER")).willReturn(roleTeacher);
        BDDMockito.given(genericDao.update(teacher1)).willReturn(teacher1);

        Assert.assertNotNull(service.updateTeacher(teacher1.getId(), updateModel));
    }

    @Test
    public void testUpdateUser () throws BusinessException {
        UserUpdateModel updateModel = new UserUpdateModel();
        updateModel.setName("name1u");
        updateModel.setAge(99);

        UserUpdate userUpdate1 = new UserUpdate();
        userUpdate1.setId(user1.getId());
        userUpdate1.setUserRoleUpdates(new ArrayList<>());

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        updateModel.setRoles(roles);

        BDDMockito.given(genericDao.read(UserUpdate.class, user1.getId())).willReturn(userUpdate1);
        BDDMockito.given(appDao.findByName(Role.class, "ROLE_ADMIN")).willReturn(roleAdmin);
        BDDMockito.given(genericDao.update(userUpdate1)).willReturn(userUpdate1);

        Assert.assertNotNull(service.update(user1.getId(), updateModel));
    }


    // Utilities
    private void setData (User user1, User user2, User user3) {
        user1.setId("1");
        user1.setEmail("email1");
        user1.setName("name1");
        user1.setPassword("pass1");
        user1.setAge(11);
        user1.setCreatedAt(now);
        user1.setUpdatedAt(now);
        user1.setVersion(1);
        user1.setUserRoles(new ArrayList<>());

        user2.setId("2");
        user2.setEmail("email2");
        user2.setName("name2");
        user2.setPassword("pass2");
        user2.setAge(22);
        user2.setCreatedAt(now);
        user2.setUpdatedAt(now);
        user2.setVersion(1);
        user2.setUserRoles(new ArrayList<>());

        user3.setId("3");
        user3.setEmail("email3");
        user3.setName("name3");
        user3.setPassword("pass3");
        user3.setAge(33);
        user3.setCreatedAt(now);
        user3.setUpdatedAt(now);
        user3.setVersion(1);
        user3.setUserRoles(new ArrayList<>());
    }

}
