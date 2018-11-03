/**
 * 
 */
package it.moneymanagement.UI.panel.inner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.Costanti;
import it.moneymanagement.UI.component.DatePicker;
import it.moneymanagement.UI.component.ObservingTextField;
import it.moneymanagement.UI.filters.DocumentSizeFilter;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.entity.TransaxionEntity;
import it.moneymanagement.business.dao.entity.TransaxionSubtypeEntity;
import it.moneymanagement.business.dao.entity.TransaxionTypeEntity;
import it.moneymanagement.utils.CheckRegularText;
import it.moneymanagement.utils.format.WebDateFormat;

public class InsertEntrataPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(InsertEntrataPanel.class);

	private JButton jButton0;
	private JButton jButton1;
	private ObservingTextField data;
	private DatePicker dataPicker;
	private JTextField descrizione;
	private JTextField valore;
	private DefaultComboBoxModel tipoTransazioneComboModel;
	private JComboBox tipiTransazione;
	private JLabel errorDataMessage;
	private JLabel errorDescrizioneMessage;
	private JLabel errorValoreMessage;
	private JLabel errorTipiTransazioneMessage;
	private JLabel esito;

	private WebDateFormat webDateFormat;

	private BusinessDelegate businessDelegate;
	private ResourceBundleMessageSource messageSource;
	private CheckRegularText checkRegularText;

	public InsertEntrataPanel() {
	}

	public void initComponents() {
		setBackground(Color.WHITE);
		//FORM DI INSERIMENTO
		JPanel form = new JPanel(new GridLayout(4, 3));
		JLabel dataLabel = new JLabel(getMessageSource().getMessage("label.data", null, "Data" , Locale.getDefault()),JLabel.RIGHT);
		setLabelStyle(dataLabel);
		form.add(dataLabel);
		Box horizontaldataGroup = Box.createHorizontalBox();
		horizontaldataGroup.add(getData());
		horizontaldataGroup.add(getDatePicker());
		form.add(horizontaldataGroup);
		form.add(getErrorDataMessage());
		JLabel descrizioneLabel = new JLabel(getMessageSource().getMessage("label.descrizione", null, "Descrizione" , Locale.getDefault()),JLabel.RIGHT);
		setLabelStyle(descrizioneLabel);
		form.add(descrizioneLabel);
		form.add(getDescrizione());
		form.add(getErrorDescrizioneMessage());
		JLabel valoreLabel = new JLabel(getMessageSource().getMessage("label.valore", null, "Valore" , Locale.getDefault()),JLabel.RIGHT); 
		setLabelStyle(valoreLabel);
		form.add(valoreLabel);
		form.add(getValore());
		form.add(getErrorValoreMessage());
		JLabel tipoLabel = new JLabel(getMessageSource().getMessage("label.tipo", null, "Tipo" , Locale.getDefault()),JLabel.RIGHT); 
		setLabelStyle(tipoLabel);
		form.add(tipoLabel);
		form.add(getTipiTransazione(false));
		form.add(getErrorTipiTransazioneMessage());
		form.setBackground(Color.WHITE);
		
		// BOX CAMPI FORM
		Box horizontalFormGroup = Box.createHorizontalBox();
		horizontalFormGroup.add(form);

		// BOX PULSANTI FORM
		Box horizontalButtonsGroup = Box.createHorizontalBox();
		horizontalButtonsGroup.add(getJButton1());
		horizontalButtonsGroup.add(getJButton0());
		
		// BOX MESSAGGI E NOTIFICHE
		JPanel messagePanel = new JPanel(new BorderLayout());
		BoxLayout layout = new BoxLayout(messagePanel, BoxLayout.Y_AXIS);
		messagePanel.setLayout(layout);
		messagePanel.setBackground(Color.WHITE);
		//Aggiungo una spacer
		messagePanel.add(new JLabel(" "));
		messagePanel.add(getEsito());
		
		// BOX MESSAGGI E NOTIFICHE
		Box horizontalMessageGroup = Box.createHorizontalBox();
		horizontalMessageGroup.add(messagePanel);

		//BOX CONTENITORE FORM
		Box verticalFormGroup = Box.createVerticalBox();
		verticalFormGroup.add(horizontalFormGroup);
		verticalFormGroup.add(horizontalButtonsGroup);
		//verticalFormGroup.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		// BOX CONTENITORE
		Box verticalGroup = Box.createVerticalBox();
		verticalGroup.add(verticalFormGroup);
		verticalGroup.add(horizontalMessageGroup);

		//setLayout(new FlowLayout(FlowLayout.CENTER));
		//add(verticalGroup);
		
		JPanel content = new JPanel();
		content.add(verticalGroup);
		content.setBackground(Color.WHITE);
		
		JPanel south = new JPanel();
		south.setBackground(new Color(128, 195, 34));
		
		setLayout(new BorderLayout());
		add(content, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
		setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
	}
	
	private void setLabelStyle(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(new Color(128, 195, 34));
	}

	private JButton getDatePicker() {
        JButton btn = new JButton(new ImageIcon("resources/images/calendar.png"));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getDataPicker().setObserver(getData());
                getDataPicker().init();
                Date selectedDate = getDataPicker().parseDate(getData().getText());
                getDataPicker().setSelectedDate(selectedDate);
                getDataPicker().start(getData());
            };
        });
		return btn;
	}
	
	public JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(getMessageSource().getMessage("button.inserisci", null, "Inserisci" , Locale.getDefault()));
			jButton0.setIcon(new ImageIcon("resources/images/salva.png"));
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
		resetFormMessages();
		IncomeEntity incomeEntity = new IncomeEntity();
		TransaxionEntity transaxionEntity = new TransaxionEntity();
		incomeEntity.setTransaxion(transaxionEntity);
		String message = "";
		boolean esitoOperazione = Boolean.FALSE;

		// Controllo data transazione
		try {
			transaxionEntity.setDate(getWebDateFormat().parse(getData().getText()));
			if (!getWebDateFormat().format(transaxionEntity.getDate()).equals(getData().getText())) {
				throw new ParseException("", 0);
			} else {
				esitoOperazione = Boolean.TRUE;
			}
		} catch (ParseException e) {
			if (!"".equals(getData().getText())) {
				logger.error("Errore nella conversione della data, input " + getData().getText(), e);
			}

			getErrorDataMessage().setForeground(new Color(195, 37, 37));
			getErrorDataMessage().setText("(DD-MM-AAAA)*");
			esitoOperazione = Boolean.FALSE;
		}

		// Controllo descrizione transazione
		if ("".equals(getDescrizione().getText().trim()) || !getCheckRegularText().testValidDbString(getDescrizione().getText())) {
			getErrorDescrizioneMessage().setForeground(new Color(195, 37, 37));
			getErrorDescrizioneMessage().setText("*");
			esitoOperazione = Boolean.FALSE;
		} else {
			transaxionEntity.setDescription(getDescrizione().getText().toUpperCase());
		}

		// Controllo importo transazione
		try {
			Double valore = new Double(getValore().getText());
			if (valore.doubleValue() > 0)
				transaxionEntity.setValue(valore);
			else
				throw new Exception("Il valore deve essere maggiore di 1");
		} catch (Exception e) {
			if (!"".equals(getValore().getText())) {
				logger.error("Errore nella conversione del valore, " + e);
			}

			getErrorValoreMessage().setForeground(new Color(195, 37, 37));
			getErrorValoreMessage().setText("*");
			getErrorValoreMessage().setFont(new Font(null, Font.PLAIN, 11));
			getErrorValoreMessage().setText("(1-99999.99)*");
			esitoOperazione = Boolean.FALSE;
		}

		// Controllo Tipo transazione
		if ("".equals(((String) getTipiTransazione().getSelectedItem()).trim())) {
			getErrorTipiTransazioneMessage().setForeground(new Color(195, 37, 37));
			getErrorTipiTransazioneMessage().setText("*");
			esitoOperazione = Boolean.FALSE;
		} else {
			TransaxionTypeEntity transaxionTypeEntity = new TransaxionTypeEntity(null, (String) getTipiTransazione().getSelectedItem());
			transaxionEntity.setTransaxionType(getBusinessDelegate().getTransaxionType(transaxionTypeEntity));
		}

		// Insert transazione
		if (esitoOperazione) {
			try {
				int selected = JOptionPane.showConfirmDialog(this, getMessageSource().getMessage("message.confirmins", null, "Confermi l'inserimento?" , Locale.getDefault()), getMessageSource().getMessage("message.confirminspaneltitle", null, "Conferma inserimento" , Locale.getDefault()), JOptionPane.OK_CANCEL_OPTION);
				if(selected == 0) {
					getBusinessDelegate().insertTransaxion(incomeEntity.getTransaxion());
					getBusinessDelegate().insertIncome(incomeEntity);
					message = "Transazione inserita con successo!";
					esitoOperazione = true;
					resetFormComponents();
					getEsito().setIcon(new ImageIcon("resources/images/ok.png"));
					getEsito().setForeground(new Color(58, 134, 197));
				}
			} catch (Exception e) {
				logger
						.error(
								"Si � verificato un errore durante l'inserimento della transazione",
								e);
				message = "Si � verificato un errore durante l'inserimento della transazione";
				esitoOperazione = false;
				getEsito().setForeground(new Color(195, 37, 37));
				getEsito().setIcon(new ImageIcon("resources/images/cross.png"));
			}
		}

		getEsito().setText(message);
		SwingUtilities.updateComponentTreeUI(this);
	}

	protected void jButton1ActionActionPerformed(ActionEvent event) {
		resetFormComponents();
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void resetFormComponents() {
		getData().setText("");
		getDescrizione().setText("");
		getValore().setText("");
		getTipiTransazione().setSelectedIndex(0);
		resetFormMessages();
	}
	
	private void reloadFormComponents() {
		getData().setText("");
		getDescrizione().setText("");
		getValore().setText("");
		getTipiTransazione().setModel(getTipoTransazioneComboModel());
	}

	private void resetFormMessages() {
		getErrorDataMessage().setText("");
		getErrorDescrizioneMessage().setText("");
		getErrorValoreMessage().setText("");
		getErrorTipiTransazioneMessage().setText("");
		getEsito().setText("");
		getEsito().setIcon(new ImageIcon());
	}

	public void resetAllComponents() {
		reloadFormComponents();
		resetFormMessages();
	}

	public DefaultComboBoxModel getTipoTransazioneComboModel() {
		tipoTransazioneComboModel = new DefaultComboBoxModel();
		tipoTransazioneComboModel.addElement("");
		TransaxionSubtypeEntity transaxionSubtypeEntityIn = new TransaxionSubtypeEntity();
		transaxionSubtypeEntityIn.setId(Costanti.ENTRATA_KEY);
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
					.setDocumentFilter(new DocumentSizeFilter(9));
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

	public void setDescrizione(JTextField descrizione) {
		this.descrizione = descrizione;
	}

	public JLabel getErrorDataMessage() {
		if (errorDataMessage == null)
			errorDataMessage = new JLabel("");
		return errorDataMessage;
	}

	public JLabel getErrorDescrizioneMessage() {
		if (errorDescrizioneMessage == null)
			errorDescrizioneMessage = new JLabel("");
		return errorDescrizioneMessage;
	}

	public JLabel getErrorValoreMessage() {
		if (errorValoreMessage == null)
			errorValoreMessage = new JLabel("");
		return errorValoreMessage;
	}

	public JLabel getErrorTipiTransazioneMessage() {
		if (errorTipiTransazioneMessage == null)
			errorTipiTransazioneMessage = new JLabel("");
		return errorTipiTransazioneMessage;
	}

	public JLabel getEsito() {
		if (esito == null) {
			esito = new JLabel("");
			esito.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return esito;
	}

	public WebDateFormat getWebDateFormat() {
		return webDateFormat;
	}

	public void setWebDateFormat(WebDateFormat webDateFormat) {
		this.webDateFormat = webDateFormat;
	}

	public ObservingTextField getData() {
		return data;
	}

	public void setData(ObservingTextField data) {
		this.data = data;
	}

	public DatePicker getDataPicker() {
		return dataPicker;
	}

	public void setDataPicker(DatePicker dataPicker) {
		this.dataPicker = dataPicker;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public CheckRegularText getCheckRegularText() {
		return checkRegularText;
	}

	public void setCheckRegularText(CheckRegularText checkRegularText) {
		this.checkRegularText = checkRegularText;
	}

	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

}

