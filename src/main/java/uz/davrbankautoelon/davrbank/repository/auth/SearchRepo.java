package uz.davrbankautoelon.davrbank.repository.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.davrbankautoelon.davrbank.dto.auth.SearchDto;
import uz.davrbankautoelon.davrbank.model.auth._Physical;
import uz.davrbankautoelon.davrbank.repository.SearchUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class SearchRepo extends SearchUtil<SearchDto, _Physical> {

    @Autowired
    protected EntityManager entityManager;

    @Override
    protected void defineCriteriaOnQuerying(SearchDto criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        if (!criteria.getBranch().equals("")) {
            whereCause.add("t.sub_branch = :branch");
            params.put("branch", criteria.getBranch());
        }

        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

    @Override
    protected Query defineQuerySelect(SearchDto criteria, StringBuilder queryBuilder) {
        String queryStr;
        queryStr = " select t from _Physical t " +
                joinStringOnQuerying(criteria) +
                " where " + queryBuilder.toString();
        return entityManager.createQuery(queryStr);

    }
}
