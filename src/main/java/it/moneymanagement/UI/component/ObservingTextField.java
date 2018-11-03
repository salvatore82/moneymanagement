package it.moneymanagement.UI.component;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObservingTextField extends JTextField implements Observer {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(ObservingTextField.class);
	
	public void update(Observable o, Object arg) {
        Calendar calendar = (Calendar) arg;
        DatePicker dp = (DatePicker) o;
        logger.debug("Date picked = " + dp.formatDate(calendar));
        setText(dp.formatDate(calendar));
    }
	
}