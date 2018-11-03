/**
 * 
 */
package it.moneymanagement.utils;

import java.util.regex.Pattern;

import it.moneymanagement.Costanti;

/**
 * @author IG07453
 * 
 */
public class CheckRegularText {

	public boolean testValidDbString(String stringa) {
		return Pattern.matches(Costanti.PATTERN_DESCRIZIONE_TESTO, stringa);
	}
	
}
