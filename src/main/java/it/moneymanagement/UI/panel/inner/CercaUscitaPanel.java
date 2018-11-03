/**
 * 
 */
package it.moneymanagement.UI.panel.inner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.Costanti;
import it.moneymanagement.UI.actions.DeleteFromTable;
import it.moneymanagement.UI.component.ButtonColumn;
import it.moneymanagement.UI.component.DatePicker;
import it.moneymanagement.UI.component.ImageColumn;
import it.moneymanagement.UI.component.ObservingTextField;
import it.moneymanagement.UI.filters.DocumentSizeFilter;
import it.moneymanagement.UI.models.TransazioniUscitaTableModel;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.OutcomeEntity;
import it.moneymanagement.business.dao.entity.TransaxionEntity;
import it.moneymanagement.business.dao.entity.TransaxionSubtypeEntity;
import it.moneymanagement.business.dao.entity.TransaxionTypeEntity;
import it.moneymanagement.utils.CheckRegularText;
import it.moneymanagement.utils.format.MoneyFormat;
import it.moneymanagement.utils.format.WebDateFormat;

public class CercaUscitaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(CercaUscitaPanel.class);

	private JButton jButton0;
	private JButton jButton1;
	private ObservingTextField dataDa;
	private ObservingTextField dataA;
	private DatePicker dataPickerDa;
	private DatePicker dataPickerA;
	private JTextField descrizione;
	private JTextField valore;
	private DefaultComboBoxModel tipoTransazioneComboModel;
	private JComboBox tipiTransazione;
	private JTable tabella;
	private JScrollPane tabellaPanel;
	private DeleteFromTable deleteFromTable;
	private TransazioniUscitaTableModel transazioniTableModel;

	private WebDateFormat webDateFormat;
	private MoneyFormat moneyFormat;

	private BusinessDelegate businessDelegate;
	private ResourceBundleMessageSource messageSource;
	private CheckRegularText checkRegularText;

	public CercaUscitaPanel() {
	}

	public void initComponents() {
		setBackground(Color.WHITE);
		// CAMPI FORM
		JPanel fieldsUp = new JPanel();
		JLabel daLabel = new JLabel(getMessageSource().getMessage("label.datada", null, "Da" , Locale.getDefault())); 
		setLabelStyle(daLabel);
		fieldsUp.add(daLabel);
		fieldsUp.add(getDataDa());
		fieldsUp.add(getDatePickerDa());
		JLabel aLabel = new JLabel(getMessageSource().getMessage("label.dataa", null, "A" , Locale.getDefault())); 
		setLabelStyle(aLabel);
		fieldsUp.add(aLabel);
		fieldsUp.add(getDataA());
		fieldsUp.add(getDatePickerA());
		JLabel descrizioneLabel = new JLabel(getMessageSource().getMessage("label.descrizione", null, "Descrizione" , Locale.getDefault()),JLabel.RIGHT);
		setLabelStyle(descrizioneLabel);
		fieldsUp.add(descrizioneLabel);
		fieldsUp.add(getDescrizione());
		fieldsUp.setBackground(Color.WHITE);
		
		JPanel fieldsDown = new JPanel();
		JLabel valoreLabel = new JLabel(getMessageSource().getMessage("label.valore", null, "Valore" , Locale.getDefault()),JLabel.RIGHT); 
		setLabelStyle(valoreLabel);
		fieldsDown.add(valoreLabel);
		fieldsDown.add(getValore());
		JLabel tipoLabel = new JLabel(getMessageSource().getMessage("label.tipo", null, "Tipo" , Locale.getDefault()),JLabel.RIGHT); 
		setLabelStyle(tipoLabel);
		fieldsDown.add(tipoLabel);
		fieldsDown.add(getTipiTransazione(false));
		fieldsDown.add(getJButton1());
		fieldsDown.add(getJButton0());
		fieldsDown.setBackground(Color.WHITE);
		
		// BOX CAMPI FORM
		Box verticalFormGroup = Box.createVerticalBox();
		verticalFormGroup.add(fieldsUp);
		verticalFormGroup.add(fieldsDown);

		// PANNELLO TABELLA
		tabellaPanel = getTabellaPanel();
		getTabellaPanel().setViewportView(getTabella());
		getTabellaPanel().setBackground(Color.WHITE);

		// BOX CONTENITORE TABELLA
		Box horizontalTabellaGroup = Box.createHorizontalBox();
		horizontalTabellaGroup.add(getTabellaPanel());

		Box verticalMainGroup = Box.createVerticalBox();
		verticalMainGroup.add(verticalFormGroup);
		verticalMainGroup.add(horizontalTabellaGroup);

		setLayout(new BorderLayout());
		add(verticalMainGroup);
	}
	
	private void setLabelStyle(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(new Color(195, 37, 37));
	}
	
	private JButton getDatePickerDa() {
        JButton btn = new JButton(new ImageIcon("resources/images/calendar.png"));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getDataPickerDa().setObserver(getDataDa());
                getDataPickerDa().init();
                Date selectedDate = getDataPickerDa().parseDate(getDataDa().getText());
                getDataPickerDa().setSelectedDate(selectedDate);
                getDataPickerDa().start(getDataDa());
            };
        });
		return btn;
	}
	
	private JButton getDatePickerA() {
        JButton btn = new JButton(new ImageIcon("resources/images/calendar.png"));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getDataPickerA().setObserver(getDataA());
                getDataPickerA().init();
                Date selectedDate = getDataPickerA().parseDate(getDataA().getText());
                getDataPickerA().setSelectedDate(selectedDate);
                getDataPickerA().start(getDataA());
            };
        });
		return btn;
	}

	public JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(getMessageSource().getMessage("button.cerca", null, "Cerca" , Locale.getDefault()));
			jButton0.setIcon(new ImageIcon("resources/images/cerca.png"));
			jButton0.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					jButton0ActionActionPerformed(e);
				}
			});
		}
		return jButton0;
	}

	public JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText(getMessageSource().getMessage("button.annulla", null, "Annulla" , Locale.getDefault()));
			jButton1.setIcon(new ImageIcon("resources/images/annulla.png"));
			jButton1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					jButton1ActionActionPerformed(e);
				}
			});
		}
		return jButton1;
	}

	protected void jButton0ActionActionPerformed(ActionEvent event) {
		TransaxionEntity transaxionEntity = new TransaxionEntity();
		Date[] interval = new Date[2];
		
		// Controllo data transazione
		try {
			interval[0] = getWebDateFormat().parse(getDataDa().getText());
			if (!getWebDateFormat().format(interval[0]).equals(getDataDa().getText())) {
				throw new ParseException("", 0);
			}
		} catch (ParseException e) {
			if (!"".equals(getDataDa().getText())) {
				logger.error("Errore nella conversione della data da, input " + getDataDa().getText(), e);
			}
			getDataDa().setText("");
		}
		
		// Controllo data transazione
		try {
			interval[1] = getWebDateFormat().parse(getDataA().getText());
			if (!getWebDateFormat().format(interval[1]).equals(getDataDa().getText())) {
				throw new ParseException("", 0);
			}
		} catch (ParseException e) {
			if (!"".equals(getDataA().getText())) {
				logger.error("Errore nella conversione della data a, input " + getDataA().getText(), e);
			}
			getDataA().setText("");
		}

		// Controllo descrizione transazione
		if ("".equals(getDescrizione().getText().trim()) || !getCheckRegularText().testValidDbString(getDescrizione().getText())) {
			getDescrizione().setText("");
		} else {
			transaxionEntity.setDescription(getDescrizione().getText());
		}

		// Controllo importo transazione
		try {
			transaxionEntity.setValue(new Double(getValore().getText()));
		} catch (Exception e) {
			if (!"".equals(getValore().getText())) {
				logger.error("Errore nella conversione del valore, " + e);
			}
			getValore().setText("");
		}

		// Controllo Tipo transazione
		if ("".equals(((String) getTipiTransazione().getSelectedItem()).trim())) {
		} else {
			TransaxionTypeEntity transaxionTypeEntity = new TransaxionTypeEntity(null, (String) getTipiTransazione().getSelectedItem());
			transaxionEntity.setTransaxionType(getBusinessDelegate().getTransaxionType(transaxionTypeEntity));
		}

		OutcomeEntity outcomeEntity = new OutcomeEntity(transaxionEntity);
		List<OutcomeEntity> uscite = getBusinessDelegate().getOutcome(outcomeEntity, interval);
		
		getTransazioniTableModel().flush();
		
		String immagine = "";
		for (OutcomeEntity uscita : uscite) {
			immagine = uscita.getMandatory().compareTo(new Short("1"))==0 ? "resources/images/obblig.png" : "resources/images/nonobblig.png";
			getTransazioniTableModel().addRow(new Object[] { uscita.getId(), Costanti.USCITA_KEY, immagine,
					getWebDateFormat().format(uscita.getTransaxion().getDate()),
					uscita.getTransaxion().getTransaxionType().getDescription(),
					uscita.getTransaxion().getDescription(),
					getMoneyFormat().format(uscita.getTransaxion().getValue()) });
		}
		
		getTabella().setModel(getTransazioniTableModel());
		if(getTabella().getColumnModel().getColumnCount()==9) {
			getTabella().removeColumn(getTabella().getColumnModel().getColumn(2));
			getTabella().removeColumn(getTabella().getColumnModel().getColumn(1));
			getTabella().removeColumn(getTabella().getColumnModel().getColumn(0));
		}
		
		getTabellaPanel().setViewportView(getTabella());
		
		ButtonColumn buttonColumn = new ButtonColumn(getTabella(), getDeleteFromTable(), 5, new ImageIcon("resources/images/cross.png"));
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		new ImageColumn(getTabella(), 4, 2);
		
		SwingUtilities.updateComponentTreeUI(this);
	}

	protected void jButton1ActionActionPerformed(ActionEvent event) {
		resetFormComponents();
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void resetFormComponents() {
		getDataDa().setText("");
		getDataA().setText("");
		getDescrizione().setText("");
		getValore().setText("");
		getTipiTransazione().setSelectedIndex(0);
	}
	
	private void reloadFormComponents() {
		getDataDa().setText("");
		getDataA().setText("");
		getDescrizione().setText("");
		getValore().setText("");
		getTipiTransazione().setModel(getTipoTransazioneComboModel());
	}

	private void resetTable() {
		getTransazioniTableModel().flush();
		getTabella().setModel(getTransazioniTableModel());
		getTabellaPanel().setViewportView(getTabella());
	}

	public void resetAllComponents() {
		reloadFormComponents();
		resetTable();
	}

	public DefaultComboBoxModel getTipoTransazioneComboModel() {
		tipoTransazioneComboModel = new DefaultComboBoxModel();
		tipoTransazioneComboModel.addElement("");
		TransaxionSubtypeEntity transaxionSubtypeEntityIn = new TransaxionSubtypeEntity();
		transaxionSubtypeEntityIn.setId(Costanti.USCITA_KEY);
		List<TransaxionTypeEntity> lista = getBusinessDelegate().getTransaxionTypeBySubtypeId(transaxionSubtypeEntityIn);
		for (TransaxionTypeEntity tipoTransazione : lista) {
			tipoTransazioneComboModel.addElement(tipoTransazione.getDescription());
		}
		return tipoTransazioneComboModel;
	}

	public JTextField getDescrizione() {
		if (descrizione == null) {
			descrizione = new JTextField();
			((AbstractDocument) descrizione.getDocument())
					.setDocumentFilter(new DocumentSizeFilter(100));
			descrizione.setColumns(8);
		}
		return descrizione;
	}

	public JTextField getValore() {
		if (valore == null) {
			valore = new JTextField();
			((AbstractDocument) valore.getDocument())
					.setDocumentFilter(new DocumentSizeFilter(10));
			valore.setColumns(6);
		}
		return valore;
	}

	public JComboBox getTipiTransazione() {
		return tipiTransazione;
	}
	
	public JComboBox getTipiTransazione(boolean load) {
		if (tipiTransazione == null && load) {
			tipiTransazione = new JComboBox(getTipoTransazioneComboModel());
			tipiTransazione.setBackground(Color.WHITE);
		} else
			tipiTransazione = new JComboBox();
		return tipiTransazione;
	}

	private JTable getTabella() {
		if (tabella == null) {
			getTransazioniTableModel().flush();
			tabella = new JTable(getTransazioniTableModel());
			if(tabella.getColumnModel().getColumnCount()==9) {
				tabella.removeColumn(tabella.getColumnModel().getColumn(2));
				tabella.removeColumn(tabella.getColumnModel().getColumn(1));
				tabella.removeColumn(tabella.getColumnModel().getColumn(0));
			}
			tabella.getColumnModel().getColumn(0).setResizable(false);
			tabella.getColumnModel().getColumn(0).setMaxWidth(76);
			tabella.getColumnModel().getColumn(0).setPreferredWidth(76);
			tabella.getColumnModel().getColumn(3).setResizable(false);
			tabella.getColumnModel().getColumn(4).setResizable(false);
			tabella.getColumnModel().getColumn(4).setMaxWidth(20);
			tabella.getColumnModel().getColumn(4).setPreferredWidth(20);
			tabella.getColumnModel().getColumn(5).setResizable(false);
			tabella.getColumnModel().getColumn(5).setMaxWidth(10);
			tabella.getColumnModel().getColumn(5).setPreferredWidth(10);
		}
		return tabella;
	}

	public void setDescrizione(JTextField descrizione) {
		this.descrizione = descrizione;
	}

	public JScrollPane getTabellaPanel() {
		if (tabellaPanel == null) {
			tabellaPanel = new JScrollPane();
		}
		return tabellaPanel;
	}

	public WebDateFormat getWebDateFormat() {
		return webDateFormat;
	}

	public void setWebDateFormat(WebDateFormat webDateFormat) {
		this.webDateFormat = webDateFormat;
	}

	public DeleteFromTable getDeleteFromTable() {
		return deleteFromTable;
	}

	public void setDeleteFromTable(DeleteFromTable deleteFromTable) {
		this.deleteFromTable = deleteFromTable;
	}

	public ObservingTextField getDataDa() {
		dataDa.setColumns(10);
		return dataDa;
	}

	public void setDataDa(ObservingTextField dataDa) {
		this.dataDa = dataDa;
	}

	public ObservingTextField getDataA() {
		dataA.setColumns(10);
		return dataA;
	}

	public void setDataA(ObservingTextField dataA) {
		this.dataA = dataA;
	}

	public DatePicker getDataPickerDa() {
		return dataPickerDa;
	}

	public void setDataPickerDa(DatePicker dataPickerDa) {
		this.dataPickerDa = dataPickerDa;
	}

	public DatePicker getDataPickerA() {
		return dataPickerA;
	}

	public void setDataPickerA(DatePicker dataPickerA) {
		this.dataPickerA = dataPickerA;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public TransazioniUscitaTableModel getTransazioniTableModel() {
		return transazioniTableModel;
	}

	public void setTransazioniTableModel(
			TransazioniUscitaTableModel transazioniTableModel) {
		this.transazioniTableModel = transazioniTableModel;
	}

	public CheckRegularText getCheckRegularText() {
		return checkRegularText;
	}

	public void setCheckRegularText(CheckRegularText checkRegularText) {
		this.checkRegularText = checkRegularText;
	}

	public MoneyFormat getMoneyFormat() {
		return moneyFormat;
	}

	public void setMoneyFormat(MoneyFormat moneyFormat) {
		this.moneyFormat = moneyFormat;
	}

	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
	
}
