package uz.davrbankautoelon.davrbank.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public abstract class SearchUtil<C, T> {

    @Autowired
    protected EntityManager entityManager;

    protected Class<T> persistentClass;
    protected JpaEntityInformation<T, ?> entityInformation;

    public SearchUtil() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        initEntityInformation();
    }

    private void initEntityInformation() {
        if (entityManager != null && entityInformation == null) {
            this.entityInformation = JpaEntityInformationSupport.getEntityInformation(persistentClass, entityManager);
        }
    }



    protected <G> List<G> findAllGeneric(C criteria) {
        Query query = findInit(criteria);
        return getResults(criteria, query);
    }
    public List<T> findAll(C criteria) {
        return findAllGeneric(criteria);
    }

    private Query findInit(C criteria) {
        Query query;
        Map<String, Object> params = new HashMap<>();
        List<String> whereCause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();

        defineCriteriaOnQuerying(criteria, whereCause, params, queryBuilder);

        query = defineQuerySelect(criteria, queryBuilder);

        log.info(query.toString());

        defineSetterParams(query, params);

        return query;
    }

    private void defineSetterParams(Query query, Map<String, Object> params) {
        params.keySet().forEach(t -> query.setParameter(t, params.get(t)));
    }


    protected void defineCriteriaOnQuerying(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

    protected void onDefineWhereCause(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        if (!whereCause.isEmpty()) {
            queryBuilder.append(" and ").append(StringUtils.join(whereCause, " and "));
            log.info(queryBuilder.toString());
        }
    }

    protected Query defineQuerySelect(C criteria, StringBuilder queryBuilder) {
        String queryStr = " select t from " + persistentClass.getSimpleName() + " t " +
                joinStringOnQuerying(criteria) +
                " where " + queryBuilder.toString();
        return entityManager.createQuery(queryStr);
    }


    protected StringBuilder joinStringOnQuerying(C criteria) {
        return new StringBuilder();
    }

    protected <G> List<G> getResults(C criteria, Query query) {
        return query.getResultList();
    }


}
