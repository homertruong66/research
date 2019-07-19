package com.hoang.uma.service;

import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.persistence.UniversityDao;
import com.hoang.uma.service.model.*;
import com.hoang.uma.common.dto.CourseDto;
import com.hoang.uma.common.dto.DepartmentDto;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.dto.UniversityDto;
import com.hoang.uma.persistence.AppDao;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * homertruong
 */

@Service
@Secured("ROLE_USER")
public class UniversityServiceImpl implements UniversityService {

    private Logger logger = Logger.getLogger(UniversityServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;
    //private DozerBeanMapper beanMapper;

    @PersistenceContext(unitName = "uma")
    private EntityManager entityManager;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private AppDao appDao;

    @Autowired
    private UniversityDao universityDao;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String create (UniversityCreateModel createModel) throws BusinessException {
        logger.info(createModel.toString());

        // validate business rules
        if (appDao.isNameExistent(University.class, createModel.getName())) {
            throw new BusinessException(BusinessException.UNIVERSITY_NAME_EXISTS,
                                        String.format(BusinessException.UNIVERSITY_NAME_EXISTS_MESSAGE, createModel.getName()));
        }

        Domain country = genericDao.read(Domain.class, createModel.getCountryId());
        if (country == null) {
            throw new BusinessException(BusinessException.COUNTRY_NOT_FOUND,
                          String.format(BusinessException.COUNTRY_NOT_FOUND_MESSAGE, createModel.getCountryId()));
        }

        // set data
        createModel.escapeHtml();
        University university = beanMapper.map(createModel, University.class);
        university.setId(UUID.randomUUID().toString());
        university.setCreatedAt(new Date());
        university.setUpdatedAt(new Date());
        university.setCountry(country);

        // create entity
        genericDao.create(university);

        return university.getId();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createDepartment (String id, DepartmentCreateModel createModel) throws BusinessException {
        logger.info("createDepartment: University Id = " + id + " - " + createModel.toString());

        // validate business rules
        University university = genericDao.read(University.class, id);
        if (university == null) {
            throw new BusinessException(BusinessException.UNIVERSITY_NOT_FOUND,
                                        String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, id));
        }

        if (universityDao.isDepartmentNameExistent(createModel.getName(), id)) {
            throw new BusinessException(BusinessException.DEPARTMENT_NAME_EXISTS,
                                        String.format(BusinessException.DEPARTMENT_NAME_EXISTS_MESSAGE, createModel.getName()));
        }

        // set data
        createModel.escapeHtml();
        Department department = beanMapper.map(createModel, Department.class);
        department.setId(UUID.randomUUID().toString());
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setUniversity(university);

        // create entity
        genericDao.create(department);

        return department.getId();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createCourse (String departmentId, CourseCreateModel createModel) throws BusinessException {
        logger.info("createCourse: Department Id = " + departmentId + " - " + createModel.toString());

        // validate business rules
        Department department = genericDao.read(Department.class, departmentId);
        if (department == null) {
            throw new BusinessException(BusinessException.DEPARTMENT_NOT_FOUND,
                                        String.format(BusinessException.DEPARTMENT_NOT_FOUND_MESSAGE, departmentId));
        }

        if (universityDao.isCourseNameExistent(createModel.getName(), departmentId)) {
            throw new BusinessException(BusinessException.COURSE_NAME_EXISTS,
                                        String.format(BusinessException.COURSE_NAME_EXISTS_MESSAGE, createModel.getName()));
        }

        // set data
        createModel.escapeHtml();
        Course course = beanMapper.map(createModel, Course.class);
        course.setId(UUID.randomUUID().toString());
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        course.setDepartment(department);

        // create entity
        genericDao.create(course);

        return course.getId();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete a University: " + id);

        // get entity
        University university = genericDao.read(University.class, id);
        if ( university == null ) {
            throw new BusinessException(BusinessException.UNIVERSITY_NOT_FOUND,
                                        String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, id));
        }

        // delete entity
        genericDao.delete(university);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDepartment (String departmentId) throws BusinessException {
        logger.info("delete a Department: " + departmentId);

        // get entity
        Department department = genericDao.read(Department.class, departmentId);
        if ( department == null ) {
            throw new BusinessException(BusinessException.DEPARTMENT_NOT_FOUND,
                                        String.format(BusinessException.DEPARTMENT_NOT_FOUND_MESSAGE, departmentId));
        }

        // delete entity
        genericDao.delete(department);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCourse (String courseId) throws BusinessException {
        logger.info("delete a Course: " + courseId);

        // get entity
        Course course = genericDao.read(Course.class, courseId);
        if ( course == null ) {
            throw new BusinessException(BusinessException.COURSE_NOT_FOUND,
                                        String.format(BusinessException.COURSE_NOT_FOUND_MESSAGE, courseId));
        }

        // delete entity
        genericDao.delete(course);
    }

    @Override
    public UniversityDto get (String id) throws BusinessException {
        logger.info("get a University: " + id);

        // get entity
        University university = genericDao.read(University.class, id);
        if ( university == null ) {
            throw new BusinessException(BusinessException.UNIVERSITY_NOT_FOUND,
                                        String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, id));
        }

        // return dto
        UniversityDto universityDto = new UniversityDto();
        beanMapper.map(university, universityDto);

        return universityDto;
    }

    @Override
    public DepartmentDto getDepartment (String departmentId) throws BusinessException {
        logger.info("get a Department: " + departmentId);

        // get entity
        Department department = genericDao.read(Department.class, departmentId);
        if ( department == null ) {
            throw new BusinessException(BusinessException.DEPARTMENT_NOT_FOUND,
                                        String.format(BusinessException.DEPARTMENT_NOT_FOUND_MESSAGE, departmentId));
        }

        // create dto to return
        DepartmentDto departmentDto = new DepartmentDto();
        beanMapper.map(department, departmentDto);

        return departmentDto;
    }

    @Override
    public CourseDto getCourse (String courseId) throws BusinessException {
        logger.info("get a Course: " + courseId);

        // get entity
        Course course = genericDao.read(Course.class, courseId);
        if ( course == null ) {
            throw new BusinessException(BusinessException.COURSE_NOT_FOUND,
                                        String.format(BusinessException.COURSE_NOT_FOUND_MESSAGE, courseId));
        }

        // create dto to return
        CourseDto courseDto = new CourseDto();
        beanMapper.map(course, courseDto);

        return courseDto;
    }

    @Override
    public SearchResultDto<UniversityDto> search (UniversitySearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<UniversityDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<University> searchCriteriaQuery = criteriaBuilder.createQuery(University.class);
        Root<University> root = searchCriteriaQuery.from(University.class);
        List<Predicate> predicates = new ArrayList();
          // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate namePredicate = criteriaBuilder.like(root.get(University_.name), "%" + name + "%");
            predicates.add(namePredicate);
        }
        // country criterion
        String countryId = searchCriteria.getCountryId();
        if (!StringUtils.isEmpty(countryId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(University_.countryId), countryId);
            predicates.add(predicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(University.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<University> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<UniversityDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            UniversityDto universityDto = new UniversityDto();
            beanMapper.map(entity, universityDto);
            dtos.add(universityDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    public SearchResultDto<DepartmentDto> searchDepartments (DepartmentSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<DepartmentDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Department> searchCriteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> root = searchCriteriaQuery.from(Department.class);
        List<Predicate> predicates = new ArrayList();
        // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate predicate = criteriaBuilder.like(root.get(Department_.name), "%" + name + "%");
            predicates.add(predicate);
        }
        // university criterion
        String universityId = searchCriteria.getUniversityId();
        if (!StringUtils.isEmpty(universityId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(Department_.universityId), universityId);
            predicates.add(predicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(Department.class)))
                .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<Department> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<DepartmentDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            DepartmentDto departmentDto = new DepartmentDto();
            beanMapper.map(entity, departmentDto);
            dtos.add(departmentDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    public SearchResultDto<CourseDto> searchCourses (CourseSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<CourseDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> searchCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = searchCriteriaQuery.from(Course.class);
        List<Predicate> predicates = new ArrayList();
        // name criterion
        String name = searchCriteria.getName();
        if (!StringUtils.isEmpty(name)) {
            Predicate namePredicate = criteriaBuilder.like(root.get(Course_.name), "%" + name + "%");
            predicates.add(namePredicate);
        }
        // department criterion
        String departmentId = searchCriteria.getDepartmentId();
        if (!StringUtils.isEmpty(departmentId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(Course_.departmentId), departmentId);
            predicates.add(predicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(Course.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<Course> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<CourseDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            CourseDto courseDto = new CourseDto();
            beanMapper.map(entity, courseDto);
            dtos.add(courseDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String update(String id, UniversityUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate business rules
        University university = genericDao.read(University.class, id);
        if ( university == null ) {
            throw new BusinessException(BusinessException.UNIVERSITY_NOT_FOUND,
                          String.format(BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE, id));
        }

        // set data
        updateModel.escapeHtml();
        String newName = updateModel.getName();
        if (!StringUtils.isEmpty(newName) && !newName.equals(university.getName())) {
            if (appDao.isNameExistent(University.class, newName)) {
                throw new BusinessException(BusinessException.UNIVERSITY_NAME_EXISTS,
                              String.format(BusinessException.UNIVERSITY_NAME_EXISTS_MESSAGE, newName));
            }

            university.setName(newName);
        }

        String newCountryId = updateModel.getCountryId();
        if (!StringUtils.isEmpty(newCountryId) && !newCountryId.equals(university.getCountry().getId())) {
            Domain country = genericDao.read(Domain.class, newCountryId);
            if (country == null) {
                throw new BusinessException(BusinessException.COUNTRY_NOT_FOUND,
                              String.format(BusinessException.COUNTRY_NOT_FOUND_MESSAGE, newCountryId));
            }
            university.setCountry(country);
        }

        university.setVersion(updateModel.getVersion());
        university.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(university);
        genericDao.update(university);

        return id;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updateDepartment (String departmentId, DepartmentUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate business rules
        Department department = genericDao.read(Department.class, departmentId);
        if ( department == null ) {
            throw new BusinessException(BusinessException.DEPARTMENT_NOT_FOUND,
                                        String.format(BusinessException.DEPARTMENT_NOT_FOUND_MESSAGE, departmentId));
        }

        // set data
        updateModel.escapeHtml();
        String newName = updateModel.getName();
        if (!StringUtils.isEmpty(newName) && !newName.equals(department.getName())) {
            if (universityDao.isDepartmentNameExistent(newName, department.getUniversity().getId())) {
                throw new BusinessException(BusinessException.DEPARTMENT_NAME_EXISTS,
                              String.format(BusinessException.DEPARTMENT_NAME_EXISTS_MESSAGE, newName));
            }

            department.setName(newName);
        }
        department.setVersion(updateModel.getVersion());
        department.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(department);
        genericDao.update(department);

        return departmentId;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String updateCourse (String courseId, CourseUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate business rules
        Course course = genericDao.read(Course.class, courseId);
        if ( course == null ) {
            throw new BusinessException(BusinessException.COURSE_NOT_FOUND,
                                        String.format(BusinessException.COURSE_NOT_FOUND_MESSAGE, courseId));
        }

        // set data
        updateModel.escapeHtml();
        String newName = updateModel.getName();
        if (!StringUtils.isEmpty(newName) && !newName.equals(course.getName())) {
            if (universityDao.isCourseNameExistent(newName, course.getDepartment().getId())) {
                throw new BusinessException(BusinessException.COURSE_NAME_EXISTS,
                              String.format(BusinessException.COURSE_NAME_EXISTS_MESSAGE, newName));
            }

            course.setName(newName);
        }

        if (updateModel.getNumberOfCredits() > 0) {
            if (updateModel.getNumberOfCredits() > 3) {
                throw new BusinessException(BusinessException.COURSE_CREDITS_INVALID,
                                            BusinessException.COURSE_CREDITS_INVALID_MESSAGE);
            }

            course.setNumberOfCredits(updateModel.getNumberOfCredits());
        }
        course.setVersion(updateModel.getVersion());
        course.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(course);
        genericDao.update(course);

        return courseId;
    }

    ////// Utilities //////

}
