/**
 * 
 */
package it.moneymanagement.utils.format;

import java.text.SimpleDateFormat;

import it.moneymanagement.Costanti;

/**
 * @author s.deluca
 *
 */
public class WebDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 1L;

	public WebDateFormat() {
		applyPattern(Costanti.FORMATO_DATA_WEB_PREDEFINITO);
	}
}
