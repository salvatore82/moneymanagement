/**
 * Operazioni comuni per i DAO, con generics in modo da istanziarsi
 * dinamicamente in base al chiamante
 */
package it.moneymanagement.business.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author s.deluca
 * 
 */
public class GenericDao<Entity, IdType> extends HibernateDaoSupport {

	/**
	 * Inserisce l'oggetto passato in ingresso
	 * @param entity
	 */
	@Transactional
	public void insert(Entity entity) {
		getHibernateTemplate().save(entity);
	}
	
	/**
	 * Aggiorna l'oggetto passato in ingresso
	 * @param entity
	 */
	@Transactional
	public void update(Entity entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * Il metodo necessita dell'entit� correttamente popolata, quindi bisogna eseguire una ricerca dell'oggetto specifico e poi passarlo al metodo
	 * @param entity
	 */
	@Transactional
	public void delete(Entity entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * Il metodo esegue una ricerca mirata, per ID dell'oggetto che si intende cercare
	 * @param entity
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Entity findByID(Entity entity, IdType id) {
		Example example = Example.create(entity);
		example.enableLike(MatchMode.EXACT);

		DetachedCriteria c = DetachedCriteria.forClass(entity.getClass());
		c.add(example);
		c.add(Restrictions.eq("id", id));

		List<Entity> list = (List<Entity>) getHibernateTemplate().findByCriteria(c);
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * Il metodo esegue una ricerca su tutta al tabella, senza considerare alcun filtro
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> findAll(Entity entity) {
		Example example = Example.create(entity);

		DetachedCriteria c = DetachedCriteria.forClass(entity.getClass());
		c.add(example);
		
		return (List<Entity>) getHibernateTemplate().findByCriteria(c);
	}

}
