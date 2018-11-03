/**
 * 
 */
package it.moneymanagement.UI.models;

import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author Salvatore
 *
 */
public class TransazioniRiepilogoTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private ResourceBundleMessageSource messageSource;

	public TransazioniRiepilogoTableModel() {
	}
	
	public void initComponents() {
		addColumn(getMessageSource().getMessage("label.data", null, "Data" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.tipo", null, "Tipo" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.descrizione", null, "Descrizione" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.valore", null, "Valore" , Locale.getDefault()));
		flush();
	}
	
	public void flush() {
		while(getRowCount()>0) {
			removeRow(getRowCount()-1);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return super.isCellEditable(row, column);
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
