/**
 * 
 */
package it.moneymanagement.business.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import it.moneymanagement.business.dao.entity.TransaxionSubtypeEntity;
import it.moneymanagement.business.dao.entity.TransaxionTypeEntity;

/**
 * @author s.deluca
 *
 */
public class TransaxionTypeDao extends GenericDao<TransaxionTypeEntity, Integer> {

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TransaxionTypeEntity> findBySubtypeId(TransaxionSubtypeEntity transaxionSubtypeEntity) {
		TransaxionTypeEntity transaxionTypeEntity = new TransaxionTypeEntity();
		Example example = Example.create(transaxionTypeEntity);

		DetachedCriteria c = DetachedCriteria.forClass(transaxionTypeEntity.getClass());
		c.add(example);
    	c.add(Restrictions.like("transaxionSubtype.id", transaxionSubtypeEntity.getId()));

		return (List<TransaxionTypeEntity>) getHibernateTemplate().findByCriteria(c);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public TransaxionTypeEntity findByType(TransaxionTypeEntity transaxionTypeEntity) {
		Example example = Example.create(transaxionTypeEntity);
		example.enableLike(MatchMode.EXACT);

		DetachedCriteria c = DetachedCriteria.forClass(transaxionTypeEntity.getClass());
		c.add(example);

		List<TransaxionTypeEntity> list = (List<TransaxionTypeEntity>) getHibernateTemplate().findByCriteria(c);
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}
}
