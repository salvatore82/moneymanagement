/**
 * 
 */
package it.moneymanagement.business.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.moneymanagement.business.dao.entity.OutcomeEntity;
import it.moneymanagement.business.dao.intf.Calculable;
import it.moneymanagement.business.dao.intf.Findable;

/**
 * @author s.deluca
 *
 */
public class OutcomeDao extends GenericDao<OutcomeEntity, Integer> implements Findable<OutcomeEntity>,Calculable<OutcomeEntity> {

	@SuppressWarnings("unchecked")
	public List<OutcomeEntity> findByIntervalDate(OutcomeEntity outcomeEntity, Date[] interval, Integer idTypeNot) {
		Example example = Example.create(outcomeEntity);
		example.enableLike(MatchMode.ANYWHERE);
		example.ignoreCase();

		DetachedCriteria c = DetachedCriteria.forClass(outcomeEntity.getClass());
		c.add(example);
		c.createAlias("transaxion", "tx");
		c.createAlias("tx.transaxionType", "txtype");
		if(interval[0]!=null)
			c.add(Restrictions.ge("tx.date", interval[0]));
		if(interval[1]!=null)
			c.add(Restrictions.le("tx.date", interval[1]));
		if(outcomeEntity.getTransaxion().getDescription()!=null)
			c.add(Restrictions.like("tx.description", outcomeEntity.getTransaxion().getDescription(), MatchMode.ANYWHERE));
		if(outcomeEntity.getTransaxion().getValue() > 0)
			c.add(Restrictions.eq("tx.value", outcomeEntity.getTransaxion().getValue()));
		if(outcomeEntity.getTransaxion().getTransaxionType()!=null) {
			if(outcomeEntity.getTransaxion().getTransaxionType().getId()!=null) {
				c.add(Restrictions.eq("txtype.id", outcomeEntity.getTransaxion().getTransaxionType().getId()));
			}
		}
		if(idTypeNot!=null) {
			c.add(Restrictions.ne("txtype.id", idTypeNot));
		}
		c.addOrder(Order.desc("tx.date"));
		
		return (List<OutcomeEntity>) getHibernateTemplate().findByCriteria(c);
	}
	
	@SuppressWarnings("rawtypes")
	public Double calculate(OutcomeEntity outcomeEntity, Date[] interval, Integer idTypeNot) {
		Example example = Example.create(outcomeEntity);
		example.enableLike(MatchMode.ANYWHERE);
		example.ignoreCase();

		DetachedCriteria c = DetachedCriteria.forClass(outcomeEntity.getClass());
		c.add(example);
		c.createAlias("transaxion", "tx");
		c.createAlias("tx.transaxionType", "txtype");
		if(interval[0]!=null)
			c.add(Restrictions.ge("tx.date", interval[0]));
		if(interval[1]!=null)
			c.add(Restrictions.le("tx.date", interval[1]));
		if(idTypeNot!=null)
			c.add(Restrictions.ne("txtype.id", idTypeNot));
		if(outcomeEntity.getTransaxion()!=null && outcomeEntity.getTransaxion().getTransaxionType()!=null) {
			if(outcomeEntity.getTransaxion().getTransaxionType().getId()!=null) {
				c.add(Restrictions.eq("txtype.id", outcomeEntity.getTransaxion().getTransaxionType().getId()));
			}
		}
		c.setProjection(Projections.sum("tx.value"));
		
		List sumResult = getHibernateTemplate().findByCriteria(c);
		if(sumResult.get(0)==null)
			return new Double(0);
		else
			return (Double) sumResult.get(0);
	}
	
}
