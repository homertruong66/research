package com.rms.rms.persistence;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.exception.DataAccessException;
import com.rms.rms.common.util.MyDateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * homertruong
 */

@Component("genericDao")
public class GenericDaoImpl implements GenericDao {

    // Hibernate data writing APIs: http://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate
    // Create:
    //  'save' returns id, if instance is detached -> new id returned (duplicated db rows)
    //  'persist' (JPA) returns void and ID IS NOT GUARANTEED after call, if instance is detached -> exception
    // Update:
    //  'updateAdmin' returns void, changes an instance from detached to persistence, if instance is transient -> exception
    //  'merge' (JPA) loads/updates a persistent instance with new field values from a detached instance
    //           and returns the persistence instance
    // Create/Update:
    //  'saveOrUpdate' returns void, makes an object persistent regardless of its state transient or detached
    //
    // -> Selection: 'save' & 'updateAdmin' for create/updateAdmin methods respectively

    public static final String[] supportedFormats = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd"
    };
    protected Pattern leftOp = Pattern.compile("^([a-zA-Z_]+[a-zA-Z0-9_\\-]*)\\s*(>|>=|<|<=|!=|<>|=|~=)\\s*");

    private Logger logger = Logger.getLogger(GenericDaoImpl.class);
    private SessionFactory sessionFactory;

    @Override
    public <T> List<T> bulkCreate(List<T> pdos) {
        for (T pdo : pdos) {
            create(pdo);
        }

        return pdos;
    }

    @Override
    public <T> long count(Class<T> clazz) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT COUNT (*) FROM ")
                   .append(clazz.getName());

        Session session = getCurrentSession();
        Query query = session.createQuery(queryString.toString());
        Long result = (Long) query.uniqueResult();

        return result;
    }

    /**
     * @param pdo: transient instance (id == null)
     * @return: persistence instance with newly created id
     */
    @Override
    public <T> T create(T pdo)  {
        // pdo must be transient
        BaseEntity baseEntity = (BaseEntity) pdo;
        if (baseEntity.getId() != null) {
            throw new RuntimeException("can not create a detached/persistence object: " + pdo.toString());
        }

        // set created at
        if (baseEntity.getCreatedAt() == null) {
            baseEntity.setCreatedAt(MyDateUtil.convertToUTCDate(new Date()));
        }

        // save transient instance and set id to persistence instance if it does not have
        String id = (String) getCurrentSession().save(pdo);
        if (baseEntity.getId() == null) {
            baseEntity.setId(id);
        }

        return pdo;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.MANDATORY)
    public <T> void delete(T pdo) {
        getCurrentSession().delete(pdo);
    }

    public <T> SearchResult<T> generateEmptySearchResult(SearchCriteria<T> searchCriteria) {
        return new SearchResult<T>() {{
            setList(new ArrayList<>());
            setPageIndex(searchCriteria.getPageIndex());
            setPageSize(searchCriteria.getPageSize());
            setTotalRows(0);
            setHasNextPage(false);
            setHasNextPage(false);
            setNumOfPages(0);
        }};
    }

    @Override
    public <T> List<T> list(Class<T> clazz, Map<String, Object> params) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("FROM ")
                .append(clazz.getName())
                .append(" WHERE ");

        for (String columnName : params.keySet()) {
            queryString.append(columnName)
                    .append(" = :")
                    .append(columnName).append(" AND ");
        }
        queryString.append(" 1 = 1");

        Session session = getCurrentSession();
        Query query = session.createQuery(queryString.toString());
        for (String columnName : params.keySet()) {
            query.setParameter(columnName, params.get(columnName));
        }

        return query.list();
    }

    @Override
    public <T> T read(Class<T> clazz, String id, boolean forUpdate) {
        T result;
        if (forUpdate) {
            // TODO: use versioning to avoid locking db row
            result = getCurrentSession().get(clazz, id, new LockOptions(LockMode.PESSIMISTIC_WRITE));
        }
        else {
            result = getCurrentSession().get(clazz, id);
        }

        return result;
    }

    @Override
    public <T> List<T> readAll(Class<T> clazz) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("FROM ").append(clazz.getName());

        Session session = getCurrentSession();
        Query query = session.createQuery(queryString.toString());

        return query.list();
    }

    @Override
    public <T> T readFirst(Class<T> clazz, Map<String, Object> params) {
        List<T> list = this.list(clazz, params);

        return list.size() > 0? list.get(0) : null;
    }

    @Override
    public <T> T readUnique(Class<T> clazz, Map<String, Object> params, boolean isForUpdate) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("FROM ")
                   .append(clazz.getName())
                   .append(" WHERE ");

        for (String columnName : params.keySet()) {
            queryString.append(columnName)
                    .append(" = :")
                    .append(columnName).append(" AND ");
        }
        queryString.append(" 1 = 1");

        Session session = getCurrentSession();
        Query query = session.createQuery(queryString.toString());
        for (String columnName : params.keySet()) {
            query.setParameter(columnName, params.get(columnName));
        }

        if (isForUpdate) {
            query.setLockMode("this", LockMode.PESSIMISTIC_WRITE);
        }

        T result = (T) query.uniqueResult();

        return result;
    }

    @Override
    public <T> SearchResult<T> search(SearchCriteria<T> searchCriteria) {
        return doSearch(searchCriteria, null);
    }

    /**
     * @param pdo: persistence/detached instance
     * @param <T>: persistence instance
     * @return
     */
    @Override
    public <T> T update(T pdo) {
        // set updated at
        BaseEntity baseEntity = (BaseEntity) pdo;
        baseEntity.setUpdatedAt(MyDateUtil.convertToUTCDate(new Date()));

        // save persistence instance
        getCurrentSession().update(pdo);

        return pdo;
    }

    @Override
    public <T> List<T> update(List<T> pdos) {
        for (T instance : pdos) {
            update(instance);
        }
        return pdos;
    }


    // Utilities //
    protected <T> Object autoBoxValue(Class<T> clazz, String property, Object value) {
        Object result = value;

        try {
            Type type = getSessionFactory().getClassMetadata(clazz).getPropertyType(property);
            if (value != null && value instanceof Double) {
                Double doubleValue = (Double) value;
                if (type instanceof LongType) {
                    result = doubleValue.longValue();
                }
                else if (type instanceof IntegerType) {
                    result = doubleValue.intValue();
                }
            }
            else if (type instanceof TimestampType || type instanceof DateType) {
                if (value != null && value instanceof String) {
                    for (String format : supportedFormats) {
                        DateFormat df = new SimpleDateFormat(format);
                        try {
                            result = df.parse(value.toString());
                            break;
                        }
                        catch (ParseException pe) {
                            logger.debug("can not parse date string", pe);
                        }
                    }
                }

            }
            return result;
        }
        catch (QueryException e) {
            throw new QueryException(ErrorCode.UNSUPPORTED_PARAM.value(property), e);
        }
    }

    protected <T> Criterion createCriterion(Class<T> clazz, String key, Object value) {
        // custom comparison?
        String[] parseResult = parseFieldAndOp(key);
        Object val = value;
        if (parseResult != null) {
            String field = parseResult[0];
            String operator = parseResult[1];
            val = autoBoxValue(clazz, field, value);
            if (">".equals(operator)) {
                return Restrictions.gt(field, val);
            }
            else if (">=".equals(operator)) {
                return Restrictions.ge(field, val);
            }
            else if ("<".equals(operator)) {
                return Restrictions.lt(field, val);
            }
            else if ("<=".equals(operator)) {
                return Restrictions.le(field, val);
            }
            else if ("=".equals(operator)) {
                return Restrictions.eq(field, val);
            }
            else if ("<>".equals(operator) || "!=".equals(operator)) {
                return Restrictions.ne(field, val);
            }
            else if ("~=".equals(operator)) {
                return Restrictions.like(field, val);
            }
        }
        else {
            // convert collection to boxed value collection
            if (value instanceof Collection || value instanceof Object[]) {
                List<Object> list = new ArrayList<>();
                for (Object it : (Iterable<?>) value) {
                    list.add(autoBoxValue(clazz, key, it));
                }
                val = list;
            }
        }

        // simple comparison
        try {
            getSessionFactory().getClassMetadata(clazz).getPropertyType(key);
        }
        catch (QueryException e) {
            throw new QueryException(ErrorCode.UNSUPPORTED_PARAM.value(key), e);
        }

        if (value == null) {
            return Restrictions.isNull(key);
        }
        else if (value instanceof Object[]) {
            return Restrictions.in(key, (Object[]) val);
        }
        else if (value instanceof Collection) {
            return Restrictions.in(key, (Collection<?>) val);
        }
        else {
            return Restrictions.eq(key, val);
        }
    }

    protected <T> SearchResult<T> doSearch(final SearchCriteria<T> searchCriteria, CustomFieldFilter customFieldFilter) {
        // add all search input to customCriteria
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        T criteria = searchCriteria.getCriteria();
        Class criteriaClass = criteria.getClass();
        if (criteria != null) {
            // implement ReflectionUtils.FieldCallback
            ReflectionUtils.FieldCallback fieldCallback = (field) -> {
                try {
                    // check unsupported mapped field
                    getSessionFactory().getClassMetadata(criteriaClass).getPropertyType(field.getName());

                    // check field value
                    Object fieldValue = field.get(criteria);
                    if (fieldValue == null) {  // do not search on this field
                        return;
                    }

                    if (!(fieldValue instanceof HashSet)) {     // search on a field
                        customCriteria.setValue(field.getName(), fieldValue);
                    } else {  // search on a set of custom fields
                        int size = ((HashSet) fieldValue).size();
                        if (size != 0) {
                            customCriteria.setValue(field.getName(), fieldValue);
                        }
                    }
                }
                catch (QueryException qe) {
                    // ignore when users want to search on unmapped fields
                }
            };

            // implement ReflectionUtils.FieldFilter
            ReflectionUtils.FieldFilter fieldFilter = (field) -> {
                int modifiers = field.getModifiers();
                if ( Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) ) {
                    return false;
                }

                if (!field.isAccessible()) {
                    ReflectionUtils.makeAccessible(field);
                }

                return !CustomCriteria.class.isAssignableFrom(field.getType());
            };

            // check mapped properties and add to search criteria
            ReflectionUtils.doWithFields(criteriaClass, fieldCallback, fieldFilter);
        }

        // convert customCriteria to Hibernate Criteria
        List<Criterion> hibernateCriteria = new ArrayList<Criterion>();
        for (Entry<String, Object> entry : customCriteria.entrySet()) {
            if (entry.getKey().matches("^[a-zA-Z_]+.*$")) {
                if (customFieldFilter != null) {
                    Collection<? extends Criterion> filterRes = customFieldFilter.filter(entry.getKey(), entry.getValue());
                    if (filterRes != null) {
                        hibernateCriteria.addAll(filterRes);
                    }
                }
                else {
                    Criterion hibernateCriterion = createCriterion(criteriaClass, entry.getKey(), entry.getValue());
                    if (hibernateCriterion != null) {
                        hibernateCriteria.add(hibernateCriterion);
                    }
                }

            }
        }

        SearchSortPageCriteria searchSortPageCriteria =
            new SearchSortPageCriteria(hibernateCriteria, searchCriteria.getSortName(), searchCriteria.isSortAsc(),
                                       searchCriteria.getPageIndex(), searchCriteria.getPageSize());

        SearchResult<T> searchResult = doSearch(criteriaClass, searchSortPageCriteria, true);

        return searchResult;
    }

    protected <T> SearchResult<T> doSearch(Class<T> clazz, SearchSortPageCriteria searchSortPageCriteria, boolean countTotal) {
        // validate input params
        int pageSize = searchSortPageCriteria.getPageSize();
        int pageIndex = searchSortPageCriteria.getPageIndex();
        if (pageSize < 1 || pageSize > SearchCriteria.MAX_PAGE_SIZE) {
            throw new DataAccessException("Invalid limit page size (" + pageSize + ")");
        }

        // build search query and count query
        List<Criterion> criteria = searchSortPageCriteria.getSearchCriteria();
        Session session = getCurrentSession();
        Criteria searchQuery = session.createCriteria(clazz);
        Criteria countQuery = session.createCriteria(clazz);
        for (Criterion criterion : criteria) {
            searchQuery.add(criterion);
            countQuery.add(criterion);
        }
        countQuery.setProjection(Projections.rowCount());

        // sort
        String orderBy = searchSortPageCriteria.getSortName();
        boolean isOrderAsc = searchSortPageCriteria.isSortAsc();
        if (orderBy != null) {
            if (orderBy.trim().equals("")) {
                throw new DataAccessException("sortName can not be empty !");
            }
            searchQuery.addOrder(isOrderAsc ? Order.asc(orderBy) : Order.desc(orderBy));
        }
        else {
            // default: order by id, asc
            searchQuery.addOrder(Order.asc("id"));
        }

        // page
        int offset = (pageIndex - 1) * pageSize;
        if (pageSize > 0) {
            if (offset < 0) {
                offset = 0;
            }

            searchQuery.setFirstResult(offset).setMaxResults(pageSize);
        }

        int count = 0;
        if (countTotal) {
            List<Number> countResult = countQuery.list();
            if (countResult != null && !countResult.isEmpty()) {
                count = (countResult.get(0)).intValue();
            }
        }

        // do search
        List<T> list = searchQuery.list();

        // build search result
        SearchResult<T> searchResult = new SearchResult<>();
        searchResult.setPageIndex(offset);
        searchResult.setPageSize(pageSize);
        searchResult.setTotalRows(count);
        searchResult.buildPagingParams(pageIndex, pageSize, count);
        searchResult.setList(list);

        return searchResult;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected String[] parseFieldAndOp(String fieldAndOp) {
        Matcher matcher = leftOp.matcher(fieldAndOp);
        if (matcher.matches()) {
            String field = matcher.group(1);
            String op = matcher.group(2);
            return new String[]{field, op};
        }

        return null;
    }

    @Autowired      // allow using custom SessionFactory when needed
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
