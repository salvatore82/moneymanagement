/**
 * 
 */
package it.moneymanagement.business.dao.intf;

import java.util.Date;

/**
 * @author IG07453
 *
 */
public interface Calculable<Entity> {

	public Double calculate(Entity entity, Date[] interval, Integer idNot);
}
