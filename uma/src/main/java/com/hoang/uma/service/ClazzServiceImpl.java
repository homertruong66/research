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
public class ClazzServiceImpl implements ClazzService {

    private Logger logger = Logger.getLogger(ClazzServiceImpl.class);

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
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cancel (String id) throws BusinessException {
        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.ENDED) {
            throw new BusinessException(BusinessException.CLAZZ_CANCEL_ENDED,
                                        String.format(BusinessException.CLAZZ_CANCEL_ENDED_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.CANCELLED ) {
            throw new BusinessException(BusinessException.CLAZZ_ALREADY_CANCELLED,
                                        String.format(BusinessException.CLAZZ_ALREADY_CANCELLED_MESSAGE, id));
        }

        // cancel clazz
        clazz.setStatus(ClazzStatus.CANCELLED);
        clazz.setUpdatedAt(new Date());

        // update entity
        genericDao.update(clazz);

        return id;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String create (String courseId, ClazzCreateModel createModel) throws BusinessException {
        logger.info(createModel.toString());

        // validate business rules
        Course course = genericDao.read(Course.class, courseId);
        if ( course == null ) {
            throw new BusinessException(BusinessException.COURSE_NOT_FOUND,
                                        String.format(BusinessException.COURSE_NOT_FOUND_MESSAGE, courseId));
        }

        // set data
        createModel.escapeHtml();
        Clazz clazz = beanMapper.map(createModel, Clazz.class);
        clazz.setId(UUID.randomUUID().toString());
        clazz.setCreatedAt(new Date());
        clazz.setUpdatedAt(new Date());
        clazz.setCourse(course);
        if (!StringUtils.isEmpty(createModel.getTeacherId())) {
            Teacher teacher = genericDao.read(Teacher.class, createModel.getTeacherId());
            clazz.setTeacher(teacher);
        }

        // create entity
        genericDao.create(clazz);

        return clazz.getId();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete (String id) throws BusinessException {
        logger.info("delete a Clazz: " + id);

        // get entity
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        // delete entity
        genericDao.delete(clazz);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String end (String id) throws BusinessException {
        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.NEW ) {
            throw new BusinessException(BusinessException.CLAZZ_END_NEW,
                                        String.format(BusinessException.CLAZZ_END_NEW_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.ENDED) {
            throw new BusinessException(BusinessException.CLAZZ_ALREADY_ENDED,
                                        String.format(BusinessException.CLAZZ_ALREADY_ENDED_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.CANCELLED ) {
            throw new BusinessException(BusinessException.CLAZZ_END_CANCELLED,
                                        String.format(BusinessException.CLAZZ_END_CANCELLED_MESSAGE, id));
        }

        // end clazz
        clazz.setStatus(ClazzStatus.ENDED);
        clazz.setUpdatedAt(new Date());

        // update entity
        genericDao.update(clazz);

        return clazz.getId();
    }

    @Override
    public ClazzDto get (String id) throws BusinessException {
        logger.info("get a Clazz: " + id);

        // get entity
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        // create dto to return
        ClazzDto clazzDto = new ClazzDto();
        beanMapper.map(clazz, clazzDto);

        return clazzDto;
    }

    @Override
    @Secured("ROLE_STUDENT")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String register (String id, String email) throws BusinessException {
        logger.info("Student with email '" + email + "' registers the Class: " + id);

        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if (clazz == null) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        Student student = appDao.findByEmail(Student.class, email);
        if ( student == null ) {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND,
                                        String.format(BusinessException.STUDENT_NOT_FOUND_MESSAGE, email));
        }

        List<ClazzStudent> clazzStudents = clazz.getClazzStudents();
        for (ClazzStudent clazzStudent : clazzStudents) {
            if (clazzStudent.getStudent().getEmail().equals(email)) {
                throw new BusinessException(BusinessException.STUDENT_ALREADY_REGISTERED,
                                            String.format(BusinessException.STUDENT_ALREADY_REGISTERED_MESSAGE, email));
            }
        }

        // add Student to Class
        ClazzStudent clazzStudent = new ClazzStudent();
        clazzStudent.setId(UUID.randomUUID().toString());
        clazzStudent.setClazz(clazz);
        clazzStudent.setStudent(student);
        clazzStudents.add(clazzStudent);
        clazz.setUpdatedAt(new Date());

        // update entity
        genericDao.update(clazz);

        return id;
    }

    @Override
    @Secured({"ROLE_USER", "ROLE_STUDENT", "ROLE_TEACHER"})
    public SearchResultDto<ClazzDto> search (ClazzSearchCriteria searchCriteria) throws BusinessException {
        logger.info(searchCriteria.toString());

        SearchResultDto<ClazzDto> result = new SearchResultDto<>();

        // build predicates
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Clazz> searchCriteriaQuery = criteriaBuilder.createQuery(Clazz.class);
        Root<Clazz> root = searchCriteriaQuery.from(Clazz.class);
        List<Predicate> predicates = new ArrayList();
        // startedAt criterion
        Date startedAt = searchCriteria.getStartedAt();
        if (startedAt != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(
                root.get(Clazz_.startedAt).as(Date.class),
                startedAt
            );
            predicates.add(predicate);
        }
        // endedAt criterion
        Date endedAt = searchCriteria.getEndedAt();
        if (endedAt != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(
                root.get(Clazz_.endedAt).as(Date.class),
                endedAt
            );
            predicates.add(predicate);
        }
        // course criterion
        String courseId = searchCriteria.getCourseId();
        if (!StringUtils.isEmpty(courseId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(Clazz_.courseId), courseId);
            predicates.add(predicate);
        }
        // teacher criterion
        String teacherId = searchCriteria.getTeacherId();
        if (!StringUtils.isEmpty(teacherId)) {
            Predicate predicate = criteriaBuilder.equal(root.get(Clazz_.teacherId), teacherId);
            predicates.add(predicate);
        }

        // build search query
        searchCriteriaQuery.select(root)
                           .where(predicates.toArray(new Predicate[]{}));

        // build count query
        CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        countCriteriaQuery.select(criteriaBuilder.count(countCriteriaQuery.from(Clazz.class)))
                          .where(predicates.toArray(new Predicate[]{}));

        // search, sort, and page
        List<Clazz> entities = genericDao.search(criteriaBuilder, searchCriteriaQuery, root, searchCriteria);

        // count
        long rowCount = entityManager.createQuery(countCriteriaQuery).getSingleResult();

        // build result
        List<ClazzDto> dtos = new ArrayList<>();
        entities.stream().forEach(entity -> {
            ClazzDto clazzDto = new ClazzDto();
            beanMapper.map(entity, clazzDto);
            dtos.add(clazzDto);
        });
        result.setList(dtos);
        result.buildPagingParams(searchCriteria.getPageIndex(), searchCriteria.getPageSize(), rowCount);

        return result;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String start (String id) throws BusinessException {
        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.STARTED ) {
            throw new BusinessException(BusinessException.CLAZZ_ALREADY_STARTED,
                                        String.format(BusinessException.CLAZZ_ALREADY_STARTED_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.ENDED) {
            throw new BusinessException(BusinessException.CLAZZ_START_ENDED,
                                        String.format(BusinessException.CLAZZ_START_ENDED_MESSAGE, id));
        }

        if ( clazz.getStatus() == ClazzStatus.CANCELLED ) {
            throw new BusinessException(BusinessException.CLAZZ_START_CANCELLED,
                                        String.format(BusinessException.CLAZZ_START_CANCELLED_MESSAGE, id));
        }

        // cancel clazz
        clazz.setStatus(ClazzStatus.STARTED);
        clazz.setUpdatedAt(new Date());

        // update entity
        genericDao.update(clazz);

        return id;
    }

    @Override
    @Secured("ROLE_STUDENT")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String unregister (String id, String email) throws BusinessException {
        logger.info("Student with email '" + email + "' unregisters the Class: " + id);

        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        Student student = appDao.findByEmail(Student.class, email);
        if ( student == null ) {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND,
                                        String.format(BusinessException.STUDENT_NOT_FOUND_MESSAGE, email));
        }

        // set data
        clazz.setUpdatedAt(new Date());
        int index = findStudentInClazzStudentList(student.getId(), clazz.getClazzStudents());
        if (index == -1) {
            throw new BusinessException(BusinessException.STUDENT_NOT_YET_REGISTERED,
                                        String.format(BusinessException.STUDENT_NOT_YET_REGISTERED_MESSAGE, email));
        }

        // remove Student from Class
        clazz.getClazzStudents().remove(index);

        // update entity
        genericDao.update(clazz);

        return id;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String update (String id, ClazzUpdateModel updateModel) throws BusinessException {
        logger.info(updateModel.toString());

        // validate business rules
        Clazz clazz = genericDao.read(Clazz.class, id);
        if ( clazz == null ) {
            throw new BusinessException(BusinessException.CLAZZ_NOT_FOUND,
                                        String.format(BusinessException.CLAZZ_NOT_FOUND_MESSAGE, id));
        }

        // set data
        updateModel.escapeHtml();
        if (updateModel.getDuration() > 0) {
            if ( updateModel.getDuration() > 3 ) {
                throw new BusinessException(BusinessException.CLAZZ_DURATION_INVALID,
                                            String.format(BusinessException.CLAZZ_DURATION_INVALID_MESSAGE, id));
            }
            clazz.setDuration(updateModel.getDuration());
        }

        if (!StringUtils.isEmpty(updateModel.getRoom())) {
            clazz.setRoom(updateModel.getRoom());
        }

        if (updateModel.getStartedAt() != null) {
            clazz.setStartedAt(updateModel.getStartedAt());
        }

        if (updateModel.getEndedAt() != null) {
            clazz.setEndedAt(updateModel.getEndedAt());
        }

        if (!StringUtils.isEmpty(updateModel.getTeacherId())) {
            Teacher teacher = genericDao.read(Teacher.class, updateModel.getTeacherId());
            if (teacher != null) {
                clazz.setTeacher(teacher);
            }
        }
        clazz.setVersion(updateModel.getVersion());
        clazz.setUpdatedAt(new Date());

        // update entity
        entityManager.detach(clazz);
        genericDao.update(clazz);

        return id;
    }


    ////// Utilities //////
    private int findStudentInClazzStudentList(String studentId, List<ClazzStudent> clazzStudents) {
        for (int i = 0; i < clazzStudents.size(); i++) {
            ClazzStudent clazzStudent = clazzStudents.get(i);
            if (studentId.equals(clazzStudent.getStudent().getId())) {
                return i;
            }
        }

        return -1;
    }

}
