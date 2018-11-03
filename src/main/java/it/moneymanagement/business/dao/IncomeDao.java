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

import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.intf.Calculable;
import it.moneymanagement.business.dao.intf.Findable;

/**
 * @author s.deluca
 *
 */
public class IncomeDao extends GenericDao<IncomeEntity, Integer> implements Findable<IncomeEntity>,Calculable<IncomeEntity> {

	@SuppressWarnings("unchecked")
	public List<IncomeEntity> findByIntervalDate(IncomeEntity incomeEntity, Date[] interval, Integer idTypeNot) {
		Example example = Example.create(incomeEntity);
		example.enableLike(MatchMode.ANYWHERE);
		example.ignoreCase();

		DetachedCriteria c = DetachedCriteria.forClass(incomeEntity.getClass());
		c.add(example);
		c.createAlias("transaxion", "tx");
		if(interval[0]!=null)
			c.add(Restrictions.ge("tx.date", interval[0]));
		if(interval[1]!=null)
			c.add(Restrictions.le("tx.date", interval[1]));
		if(incomeEntity.getTransaxion().getDescription()!=null)
			c.add(Restrictions.like("tx.description", incomeEntity.getTransaxion().getDescription(), MatchMode.ANYWHERE));
		if(incomeEntity.getTransaxion().getValue() > 0)
			c.add(Restrictions.eq("tx.value", incomeEntity.getTransaxion().getValue()));
		if(incomeEntity.getTransaxion().getTransaxionType()!=null) {
			if(incomeEntity.getTransaxion().getTransaxionType().getId()!=null) {
				c.createAlias("tx.transaxionType", "txtype");
				c.add(Restrictions.eq("txtype.id", incomeEntity.getTransaxion().getTransaxionType().getId()));
			}
		}
		c.addOrder(Order.desc("tx.date"));
			
		return (List<IncomeEntity>) getHibernateTemplate().findByCriteria(c);
	}
	
	@SuppressWarnings("rawtypes")
	public Double calculate(IncomeEntity incomeEntity, Date[] interval, Integer idTypeNot) {
		Example example = Example.create(incomeEntity);
		example.enableLike(MatchMode.ANYWHERE);
		example.ignoreCase();

		DetachedCriteria c = DetachedCriteria.forClass(incomeEntity.getClass());
		c.add(example);
		c.createAlias("transaxion", "tx");
		if(interval[0]!=null)
			c.add(Restrictions.ge("tx.date", interval[0]));
		if(interval[1]!=null)
			c.add(Restrictions.le("tx.date", interval[1]));
		c.setProjection(Projections.sum("tx.value"));
		
		List sumResult = getHibernateTemplate().findByCriteria(c);
		if(sumResult.get(0)==null)
			return new Double(0);
		else
			return (Double) sumResult.get(0);
	}

}
