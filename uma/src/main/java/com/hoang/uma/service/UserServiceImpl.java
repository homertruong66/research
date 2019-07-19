package com.hoang.uma.service;

import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.dto.StudentDto;
import com.hoang.uma.common.dto.TeacherDto;
import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.common.enu.SystemRole;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.persistence.AppDao;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.*;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * homertruong
 */

@Service
@Secured("ROLE_ADMIN")
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;
    //private DozerBeanMapper beanMapper;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private AppDao appDao;

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String assignTeacher (String teacherId, List<String> departmentIds) throws BusinessException {
        logger.info("assign a Teacher: " + teacherId + " to Department(s) " + departmentIds.toString());

        // validate input
        if (departmentIds == null || departmentIds.size() == 0) {
            throw new BusinessException(BusinessException.TEACHER_ASSIGN_DEPARTMENT_REQUIRED,
                                        BusinessException.TEACHER_ASSIGN_DEPARTMENT_REQUIRED_MESSAGE);
        }

        // get entity
        Teacher teacher = genericDao.read(Teacher.class, teacherId);
        if ( teacher == null ) {
            throw new BusinessException(BusinessException.TEACHER_NOT_FOUND,
                                        String.format(BusinessException.TEACHER_NOT_FOUND_MESSAGE, teacherId));
        }

        // assign Teacher to Departments
        this.removeUnassignedDepartments(teacher.getDepartmentTeachers(), departmentIds);
        this.assignNewDepartments(teacher, departmentIds);
        genericDao.update(teacher);

        return teacherId;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String create (UserCreateModel createModel) throws BusinessException {
        logger.info(createModel.toString());

        // validate business rules on createModel
        if (appDao.isEmailExistent(User.class, createModel.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                                        String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, createModel.getEmail()));
        }

        if (createModel.getRoles() == null || createModel.getRoles().size() == 0) {
            throw new BusinessException(BusinessException.USER_ROLES_EMPTY,
                                        BusinessException.USER_ROLES_EMPTY_MESSAGE);
        }

        // set data
        createModel.escapeHtml();
        User user = beanMapper.map(createModel, User.class);
        user.setId(UUID.randomUUID().toString());
        // encrypt password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        List<UserRole> userRoles = new ArrayList<>();
        createModel.getRoles().forEach(roleName -> {
            Role role = appDao.findByName(Role.class, roleName);
            if (role != null) {
                UserRole userRole = new UserRole();
                userRole.setId(UUID.randomUUID().toString());
                userRole.setUser(user);
                userRole.setRole(role);
                userRoles.add(userRole);
            }
        });
        if (userRoles.size() == 0) {
            throw new BusinessException(BusinessException.USER_NO_ROLES_FOUND,
                                        BusinessException.USER_NO_ROLES_FOUND_MESSAGE);
        }

        // set Roles
        user.setUserRoles(userRoles);

        // create entity
        genericDao.create(user);

        return user.getId();
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createStudent (String departmentId, StudentCreateModel createModel) throws BusinessException {
        logger.info("createStudent: Department Id = " + departmentId + " - " + createModel.toString());

        // validate business rules
        Department department = genericDao.read(Department.class, departmentId);
        if (department == null) {
            throw new BusinessException(BusinessException.DEPARTMENT_NOT_FOUND,
                                        String.format(BusinessException.DEPARTMENT_NOT_FOUND_MESSAGE, departmentId));
        }

        if (appDao.isEmailExistent(User.class, createModel.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                                        String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, createModel.getEmail()));
        }

        // set data
        createModel.escapeHtml();
        Student student = beanMapper.map(createModel, Student.class);
        student.setId(UUID.randomUUID().toString());
        // encrypt password
        String hashedPassword = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(12));
        student.setPassword(hashedPassword);
        student.setCreatedAt(new Date());
        student.setUpdatedAt(new Date());
        student.setDepartment(department);

        List<UserRole> userRoles = new ArrayList<>();
        Role roleStudent = appDao.findByName(Role.class, SystemRole.ROLE_STUDENT.toString());
        UserRole userRoleStudent = new UserRole();
        userRoleStudent.setId(UUID.randomUUID().toString());
        userRoleStudent.setUser(student);
        userRoleStudent.setRole(roleStudent);
        userRoles.add(userRoleStudent);
        student.setUserRoles(userRoles);

        // create entity
        genericDao.create(student);

        return student.getId();
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createTeacher (TeacherCreateModel createModel) throws BusinessException {
        logger.info("createTeacher: " + createModel.toString());

        // validate business rules
        if (appDao.isEmailExistent(User.class, createModel.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                                        String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, createModel.getEmail()));
        }

        // set data
        createModel.escapeHtml();
        Teacher teacher = beanMapper.map(createModel, Teacher.class);
        teacher.setId(UUID.randomUUID().toString());
        // encrypt password
        String hashedPassword = BCrypt.hashpw(teacher.getPassword(), BCrypt.gensalt(12));
        teacher.setPassword(hashedPassword);
        teacher.setCreatedAt(new Date());
        teacher.setUpdatedAt(new Date());

        List<UserRole> userRoles = new ArrayList<>();
        Role roleTeacher = appDao.findByName(Role.class, SystemRole.ROLE_TEACHER.toString());
        UserRole userRoleTeacher = new UserRole();
        userRoleTeacher.setId(UUID.randomUUID().toString());
        userRoleTeacher.setUser(teacher);
        userRoleTeacher.setRole(roleTeacher);
        userRoles.add(userRoleTeacher);
        teacher.setUserRoles(userRoles);

        // create entity
        genericDao.create(teacher);

        return teacher.getId();
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<String> createTeachers(List<TeacherCreateModel> createModels) throws BusinessException {
        List<Teacher> teachers = new ArrayList<>();
        Role roleTeacher = appDao.findByName(Role.class, SystemRole.ROLE_TEACHER.toString());
        createModels.stream().forEach(createModel -> {
            if (appDao.isEmailExistent(Teacher.class, createModel.getEmail())) {
                // TODO: process invalid Teacher
                return;
            }

            createModel.escapeHtml();
            Teacher teacher = beanMapper.map(createModel, Teacher.class);
            // encrypt password
            String hashedPassword = BCrypt.hashpw(teacher.getPassword(), BCrypt.gensalt(12));
            teacher.setPassword(hashedPassword);
            teacher.setId(UUID.randomUUID().toString());
            teacher.setCreatedAt(new Date());
            teacher.setUpdatedAt(new Date());

            UserRole userRoleTeacher = new UserRole();
            userRoleTeacher.setId(UUID.randomUUID().toString());
            userRoleTeacher.setUser(teacher);
            userRoleTeacher.setRole(roleTeacher);
            List<UserRole> userRoles = new ArrayList<>();
            userRoles.add(userRoleTeacher);
            teacher.setUserRoles(userRoles);

            teachers.add(teacher);
        });
        genericDao.bulkCreate(teachers);

        List<String> teacherIds = new ArrayList<>();
        teachers.stream().forEach(teacher -> teacherIds.add(teacher.getId()));

        return teacherIds;
    }

    @Override
    @CacheEvict(value = "common-java-app", key = "'User-'.concat(#id)")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete a User: " + id);

        // get entity
        User user = genericDao.read(User.class, id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        String.format(BusinessException.USER_NOT_FOUND_MESSAGE, id));
        }

        // delete entity
        genericDao.delete(user);
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteStudent(String studentId) throws BusinessException {
        logger.info("delete a Student: " + studentId);

        // get entity
        Student student = genericDao.read(Student.class, studentId);
        if ( student == null ) {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND,
                                        String.format(BusinessException.STUDENT_NOT_FOUND_MESSAGE, studentId));
        }

        // delete entity
        genericDao.delete(student);
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteTeacher(String teacherId) throws BusinessException {
        logger.info("delete a Teacher: " + teacherId);

        // get entity
        Teacher teacher = genericDao.read(Teacher.class, teacherId);
        if ( teacher == null ) {
            throw new BusinessException(BusinessException.TEACHER_NOT_FOUND,
                                        String.format(BusinessException.TEACHER_NOT_FOUND_MESSAGE, teacherId));
        }

        // delete entity
        genericDao.delete(teacher);
    }

    @Override
    //@Cacheable(value = "common-java-app", key = "'User-'.concat(#id)")    TODO: store object as json string
    public UserDto get (String id) throws BusinessException {
        logger.info("get a User: " + id);

        // get entity
        User user = genericDao.read(User.class, id);
        if ( user == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        String.format(BusinessException.USER_NOT_FOUND_MESSAGE, id));
        }

        // return dto
        UserDto userDto = new UserDto();
        beanMapper.map(user, userDto);

        return userDto;
    }

    @Override
    public List<UserDto> get(int fieldNum) throws BusinessException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        if (fieldNum == 1) {
            CompoundSelection<User> projection = criteriaBuilder.construct(User.class, root.get(User_.id));
            criteriaQuery.select(projection);
        }
        else if (fieldNum == 2) {
            CompoundSelection<User> projection = criteriaBuilder.construct(User.class,
                                                                           root.get(User_.id), root.get(User_.email));
            criteriaQuery.select(projection);
        }
        else {
            criteriaQuery.select(root);
        }

        Query query = entityManager.createQuery(criteriaQuery);
        List<User> users = query.getResultList();
        List<UserDto> userDtos = new ArrayList<>();
        users.stream().forEach(user -> {
            UserDto userDto = new UserDto();
            beanMapper.map(user, userDto);
            userDtos.add(userDto);
        });

        return userDtos;
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_TEACHER"})
    public String getIdByEmail(String email) throws BusinessException {
        return appDao.findIdByEmail(email);
    }

    @Override
    @Secured("ROLE_USER")
    public StudentDto getStudent (String studentId) throws BusinessException {
        logger.info("get a Student: " + studentId);

        // get entity
        Student student = genericDao.read(Student.class, studentId);
        if ( student == null ) {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND,
                                        String.format(BusinessException.STUDENT_NOT_FOUND_MESSAGE, studentId));
        }

        // create dto to return
        StudentDto studentDto = new StudentDto();
        beanMapper.map(student, studentDto);

        return studentDto;
    }

    @Override
    @Secured("ROLE_USER")
    public TeacherDto getTeacher (String teacherId) throws BusinessException {
        logger.info("get a Teacher: " + teacherId);

        // get entity
        Teacher teacher = genericDao.read(Teacher.class, teacherId);
        if ( teacher == null ) {
            throw new BusinessException(BusinessException.TEACHER_NOT_FOUND,
                                        String.format(BusinessException.TEACHER_NOT_FOUND_MESSAGE, teacherId));
        }

        // create dto to return
        TeacherDto teacherDto = new TeacherDto();
        beanMapper.map(teacher, teacherDto);

        return teacherDto;
    }

    @Override
    public SearchResultDto<UserDto> search (UserSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<UserDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> searchCriteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = searchCriteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList();
          // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate namePredicate = criteriaBuilder.like(root.get(User_.name), "%" + name + "%");
            predicates.add(namePredicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(User.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<User> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<UserDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            UserDto userDto = new UserDto();
            beanMapper.map(entity, userDto);
            dtos.add(userDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    @Secured("ROLE_USER")
    public SearchResultDto<StudentDto> searchStudents (StudentSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<StudentDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> searchCriteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = searchCriteriaQuery.from(Student.class);
        List<Predicate> predicates = new ArrayList();
        // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate namePredicate = criteriaBuilder.like(root.get(Student_.name), "%" + name + "%");
            predicates.add(namePredicate);
        }
        // department criterion
        String departmentId = searchCriteria.getDepartmentId();
        if (!StringUtils.isEmpty(departmentId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(Student_.departmentId), departmentId);
            predicates.add(predicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(Student.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<Student> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<StudentDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            StudentDto studentDto = new StudentDto();
            beanMapper.map(entity, studentDto);
            dtos.add(studentDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    @Secured("ROLE_USER")
    public SearchResultDto<TeacherDto> searchTeachers (TeacherSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<TeacherDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> searchCriteriaQuery = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> root = searchCriteriaQuery.from(Teacher.class);
        List<Predicate> predicates = new ArrayList();
        // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate namePredicate = criteriaBuilder.like(root.get(Teacher_.name), "%" + name + "%");
            predicates.add(namePredicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(Teacher.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<Teacher> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<TeacherDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            TeacherDto teacherDto = new TeacherDto();
            beanMapper.map(entity, teacherDto);
            dtos.add(teacherDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    //@CachePut(value = "common-java-app", key="'User-'.concat(#result)")
    @Secured({ "ROLE_ADMIN", "ROLE_USER", "ROLE_STUDENT", "ROLE_TEACHER" })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String update (String id, UserUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate biz rule
        UserUpdate userUpdate = genericDao.read(UserUpdate.class, id);
        if ( userUpdate == null ) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                                        String.format(BusinessException.USER_NOT_FOUND_MESSAGE, id));
        }

        // set data
        updateModel.escapeHtml();
        if (!StringUtils.isEmpty(updateModel.getName())) {
            userUpdate.setName(updateModel.getName());
        }
        if (updateModel.getAge() > 0) {
            userUpdate.setAge(updateModel.getAge());
        }
        userUpdate.setVersion(updateModel.getVersion());
        userUpdate.setUpdatedAt(new Date());

        // set Roles
        List<String> newRoles = updateModel.getRoles();
        if (newRoles != null && newRoles.size() > 0) {
            this.removeUnassignedRoles(userUpdate.getUserRoleUpdates(), newRoles);
            this.assignNewRoles(userUpdate, newRoles);
        }

        // update entity
        entityManager.detach(userUpdate);
        genericDao.update(userUpdate); // this returns persistent entity, 'userUpdate' is still detached

        return id;
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updateStudent (String studentId, StudentUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate biz rules
        Student student = genericDao.read(Student.class, studentId);
        if ( student == null ) {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND,
                                        String.format(BusinessException.STUDENT_NOT_FOUND_MESSAGE, studentId));
        }

        // set data
        updateModel.escapeHtml();
        if (!StringUtils.isEmpty(updateModel.getName())) {
            student.setName(updateModel.getName());
        }
        if (updateModel.getAge() > 0) {
            if (updateModel.getAge() > 100) {
                throw new BusinessException(BusinessException.STUDENT_AGE_INVALID,
                                            String.format(BusinessException.STUDENT_AGE_INVALID_MESSAGE, studentId));
            }
            student.setAge(updateModel.getAge());
        }
        if (updateModel.getYear() > 0) {
            if (updateModel.getYear() > 5) {
                throw new BusinessException(BusinessException.STUDENT_YEAR_INVALID,
                                            String.format(BusinessException.STUDENT_YEAR_INVALID_MESSAGE, studentId));
            }
            student.setYear(updateModel.getYear());
        }
        student.setVersion(updateModel.getVersion());
        student.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(student);
        genericDao.update(student);

        return studentId;
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updateTeacher(String teacherId, TeacherUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate biz rule
        Teacher teacher = genericDao.read(Teacher.class, teacherId);
        if ( teacher == null ) {
            throw new BusinessException(BusinessException.TEACHER_NOT_FOUND,
                                        String.format(BusinessException.TEACHER_NOT_FOUND_MESSAGE, teacherId));
        }

        // set data
        updateModel.escapeHtml();
        if (!StringUtils.isEmpty(updateModel.getName())) {
            teacher.setName(updateModel.getName());
        }
        if (updateModel.getAge() > 0) {
            if (updateModel.getAge() > 100) {
                throw new BusinessException(BusinessException.TEACHER_AGE_INVALID,
                                            String.format(BusinessException.TEACHER_AGE_INVALID_MESSAGE, teacherId));
            }
            teacher.setAge(updateModel.getAge());
        }
        if (!StringUtils.isEmpty(updateModel.getDegree())) {
            teacher.setDegree(updateModel.getDegree());
        }
        teacher.setVersion(updateModel.getVersion());
        teacher.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(teacher);
        genericDao.update(teacher);

        return teacherId;
    }


    ////// Utilities //////
    private void assignNewDepartments(Teacher teacher, List<String> newData) {
        List<DepartmentTeacher> currentData = teacher.getDepartmentTeachers();
        newData.stream().forEach(departmentId -> {
            if (this.isInDepartmentTeacherList(departmentId, currentData)) {
                return;
            }

            Department department = genericDao.read(Department.class, departmentId);
            if (department != null) {
                DepartmentTeacher departmentTeacher = new DepartmentTeacher();
                departmentTeacher.setId(UUID.randomUUID().toString());
                departmentTeacher.setDepartment(department);
                departmentTeacher.setTeacher(teacher);
                currentData.add(departmentTeacher);
            }
        });
    }

    private void assignNewRoles(UserUpdate userUpdate, List<String> newData) {
        List<UserRoleUpdate> currentData = userUpdate.getUserRoleUpdates();
        newData.stream().forEach(roleName -> {
            if (this.isInUserRoleUpdateList(roleName, currentData)) {
                return;
            }

            Role role = appDao.findByName(Role.class, roleName);
            if (role != null) {
                UserRoleUpdate userRole = new UserRoleUpdate();
                userRole.setId(UUID.randomUUID().toString());
                userRole.setUser(userUpdate);
                userRole.setRole(role);
                currentData.add(userRole);
            }
        });
    }

    private boolean isInDepartmentTeacherList (String departmentId, List<DepartmentTeacher> departmentTeachers) {
        for (DepartmentTeacher departmentTeacher : departmentTeachers) {
            if (departmentTeacher.getDepartment().getId().equals(departmentId)) {
                return true;
            }
        }

        return false;
    }

    private boolean isInUserRoleUpdateList(String roleName, List<UserRoleUpdate> userRoleUpdates) {
        for (UserRoleUpdate userRoleUpdate : userRoleUpdates) {
            if (userRoleUpdate.getRole().getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    private void removeUnassignedDepartments(List<DepartmentTeacher> currentData, List<String> newData) {
        for (int i = currentData.size() - 1; i >= 0; i--) {
            DepartmentTeacher departmentTeacher = currentData.get(i);
            if (!newData.contains(departmentTeacher.getDepartment().getId())) {
                currentData.remove(i);
            }
        }
    }

    private void removeUnassignedRoles(List<UserRoleUpdate> currentData, List<String> newData) {
        for (int i = currentData.size() - 1; i >= 0; i--) {
            UserRoleUpdate userRoleUpdate = currentData.get(i);
            if (!newData.contains(userRoleUpdate.getRole().getName())) {
                userRoleUpdate.setUser(null);
                userRoleUpdate.setRole(null);
                currentData.remove(i);
                genericDao.delete(userRoleUpdate);
            }
        }
    }
}
