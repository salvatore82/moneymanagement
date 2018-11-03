/**
 * 
 */
package it.moneymanagement.utils.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import it.moneymanagement.Costanti;

/**
 * @author s.deluca
 *
 */
public class MoneyFormat extends DecimalFormat {

	private static final long serialVersionUID = 1L;

	public MoneyFormat() {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		setDecimalFormatSymbols(dfs);
		this.applyPattern(Costanti.FORMATO_SOLDI_PREDEFINITO);
	}
	
}
