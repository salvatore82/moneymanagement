/**
 * 
 */
package it.moneymanagement.UI.models;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.utils.CheckRegularText;
import it.moneymanagement.utils.format.MoneyFormat;
import it.moneymanagement.utils.format.WebDateFormat;

/**
 * @author Salvatore
 *
 */
public class TransazioniEntrataTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private ResourceBundleMessageSource messageSource;
	private BusinessDelegate businessDelegate;
	private WebDateFormat webDateFormat;
	private MoneyFormat moneyFormat;
	private CheckRegularText checkRegularText;

	public TransazioniEntrataTableModel() {
	}
	
	public void initComponents() {
		addColumn(getMessageSource().getMessage("label.id", null, "ID" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.tipotrans", null, "TipoTransazione" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.data", null, "Data" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.tipo", null, "Tipo" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.descrizione", null, "Descrizione" , Locale.getDefault()));
		addColumn(getMessageSource().getMessage("label.valore", null, "Valore" , Locale.getDefault()));
		addColumn("");
		flush();
	}
	
	public void flush() {
		while(getRowCount()>0) {
			removeRow(getRowCount()-1);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		boolean editabile = false;
		if(column == 6 || column == 2 || column == 4 || column == 5)
			editabile = true;
		return editabile;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void fireTableChanged(TableModelEvent e) {
		if(e.getType()==0 && dataVector.size()!=0) {
			boolean esito = true;
			Vector data = (Vector) dataVector.get(e.getFirstRow());
			Integer id = (Integer) data.get(0);
			String dataTransazione = (String) data.get(2);
			String descrizione = (String) data.get(4);
			String importo = (String) data.get(5);
			
			IncomeEntity incomeEntity = new IncomeEntity();
			incomeEntity.setId(id);
			incomeEntity = getBusinessDelegate().getIncomeByID(incomeEntity);
			
			try {
				Date dataFormattata = getWebDateFormat().parse(dataTransazione);
				if (!getWebDateFormat().format(dataFormattata).equals(dataTransazione)) {
					throw new ParseException("", 0);
				}
				incomeEntity.getTransaxion().setDate(dataFormattata);
			} catch (ParseException e1) {
				esito = false;
			}
			if(descrizione!=null && !descrizione.trim().equals("") && getCheckRegularText().testValidDbString(descrizione)) {
				incomeEntity.getTransaxion().setDescription(descrizione.toUpperCase());
			} else {
				esito = false;
			}
			try {
				incomeEntity.getTransaxion().setValue(new Double(importo));
			} catch (NumberFormatException nfe) {
				esito = false;
			}
			if(esito) {
				getBusinessDelegate().updateTransaxion(incomeEntity.getTransaxion());
			}
			
			//Aggiorno la tabella mostrata a video in ogni caso: se tutto è andato bene setto i nuovo valori, altrimenti riassegno i vecchi valori
			//perchè il dataVector è cambiato e mostra anche i valori scorretti, ma solo a video (l'update sul DB non verrebbe comunque effettuata)
			data.setElementAt(getWebDateFormat().format(incomeEntity.getTransaxion().getDate()), 2);
			data.setElementAt(incomeEntity.getTransaxion().getDescription(), 4);
			data.setElementAt(getMoneyFormat().format(incomeEntity.getTransaxion().getValue()), 5);
		}
		super.fireTableChanged(e);
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public WebDateFormat getWebDateFormat() {
		return webDateFormat;
	}

	public void setWebDateFormat(WebDateFormat webDateFormat) {
		this.webDateFormat = webDateFormat;
	}

	public MoneyFormat getMoneyFormat() {
		return moneyFormat;
	}

	public void setMoneyFormat(MoneyFormat moneyFormat) {
		this.moneyFormat = moneyFormat;
	}

	public CheckRegularText getCheckRegularText() {
		return checkRegularText;
	}

	public void setCheckRegularText(CheckRegularText checkRegularText) {
		this.checkRegularText = checkRegularText;
	}
}
