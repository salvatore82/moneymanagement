package it.moneymanagement.UI.filters;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentSizeFilter extends DocumentFilter {
	int maxCharacters;
	private Logger logger = LoggerFactory.getLogger(DocumentSizeFilter.class);

	public DocumentSizeFilter(int maxChars) {
		maxCharacters = maxChars;
	}

	public void insertString(FilterBypass fb, int offs, String str,
			AttributeSet a) throws BadLocationException {
		int lunghezza = fb.getDocument().getLength() + str.length();
		if (lunghezza <= maxCharacters) {
			super.insertString(fb, offs, str, a);
		} else
		{
			logger.debug("Lunghezza testo non valida, " + lunghezza + " è maggiore di " + maxCharacters);
			Toolkit.getDefaultToolkit().beep();
		}
			
	}

	public void replace(FilterBypass fb, int offs, int length, String str,
			AttributeSet a) throws BadLocationException {
		int lunghezza = fb.getDocument().getLength() + str.length() - length;
		if (lunghezza <= maxCharacters) {
			super.replace(fb, offs, length, str, a);
		} else
		{
			logger.debug("Lunghezza testo non valida, " + lunghezza + " è maggiore di " + maxCharacters);
			Toolkit.getDefaultToolkit().beep();
		}
	}

}
