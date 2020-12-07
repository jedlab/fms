package com.fms.app.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.fms.app.domain.DocumentEntity;
import com.fms.app.service.DocumentService;
import com.jedlab.framework.spring.security.AuthenticationUtil;
import com.jedlab.framework.spring.service.AbstractCrudService;
import com.jedlab.framework.spring.service.JPARestriction;
import com.jedlab.framework.spring.web.SpringViewScope;
import com.jedlab.framework.web.AbstractQueryActionBean;
import com.jedlab.framework.web.SortProperty;

import lombok.extern.slf4j.Slf4j;

@Component("fileQuery")
@ELBeanName("fileQuery")
@SpringViewScope
@Slf4j
@Join(path = "/secure/fileList", to = "/secure/fileList.xhtml")
public class FileQuery extends AbstractQueryActionBean<DocumentEntity>
{
	
	
	@Autowired
	DocumentService documentService;
	
	@Override
	public AbstractCrudService<DocumentEntity> getService() {
		return documentService;
	}
	
	@Override
	protected JPARestriction getRestriction() {
		return new FileRestriction();
	}
	
	
	private class FileRestriction implements JPARestriction
    {

        @Override
        public Specification countSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root)
        {
            return (rootEntity, query, criteriaBuilder) ->
            {
//            	rootEntity.join("owner", JoinType.LEFT);
                return applyFilter(rootEntity, query, criteriaBuilder);
            };
        }

        @Override
        public Specification listSpec(CriteriaBuilder builder, CriteriaQuery criteria, Root root)
        {
            return (rootEntity, query, criteriaBuilder) ->
            {
            	rootEntity.fetch("owner", JoinType.LEFT);
                return applyFilter(rootEntity, query, criteriaBuilder);
            };
        }

        private Predicate applyFilter(Root rootEntity, CriteriaQuery query, CriteriaBuilder criteriaBuilder)
        {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootEntity.get("owner").get("id"), AuthenticationUtil.getUserId()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }

    }
	
	@Override
	protected List<SortProperty> getSortProperties() {
		return Arrays.asList(new SortProperty("createdDate", SortOrder.DESCENDING));
	}
	
	

}
