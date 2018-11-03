/**
 * 
 */
package it.moneymanagement.business.dao.intf;

import java.util.Date;
import java.util.List;

/**
 * @author IG07453
 *
 */
public interface Findable<Entity> {

	public List<Entity> findByIntervalDate(Entity entity, Date[] interval, Integer idNot);
}
