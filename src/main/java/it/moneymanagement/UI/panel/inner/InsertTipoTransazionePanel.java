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
import it.moneymanagement.UI.filters.DocumentSizeFilter;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.TransaxionSubtypeEntity;
import it.moneymanagement.business.dao.entity.TransaxionTypeEntity;
import it.moneymanagement.utils.CheckRegularText;

public class InsertTipoTransazionePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory
			.getLogger(InsertTipoTransazionePanel.class);

	private JButton jButton0;
	private JButton jButton1;
	private JComboBox tipoTransazione;
	private JTextField descrizione;
	private JLabel errorDescrizioneMessage;
	private JLabel errorTipoTransazioneMessage;
	private JLabel esito;

	private BusinessDelegate businessDelegate;
	private ResourceBundleMessageSource messageSource;
	private CheckRegularText checkRegularText;
	
	public InsertTipoTransazionePanel() {
	}

	public void initComponents() {
		setBackground(Color.WHITE);
		//FORM DI INSERIMENTO
		JPanel form = new JPanel(new GridLayout(2, 3));
		JLabel descrizioneLabel = new JLabel(getMessageSource().getMessage("label.descrizione", null, "Descrizione" , Locale.getDefault()),JLabel.RIGHT);
		setLabelStyle(descrizioneLabel);
		form.add(descrizioneLabel);
		form.add(getDescrizione());
		form.add(getErrorDescrizioneMessage());
		JLabel tipoLabel = new JLabel(getMessageSource().getMessage("label.tipo", null, "Tipo" , Locale.getDefault()),JLabel.RIGHT); 
		setLabelStyle(tipoLabel);
		form.add(tipoLabel);
		form.add(getTipoTransazione());
		form.add(getErrorTipoTransazioneMessage());
		form.setBackground(Color.WHITE);
		
		// BOX CAMPI FORM
		Box horizontalFormGroup = Box.createHorizontalBox();
		horizontalFormGroup.add(form);

		// BOX PULSANTI FORM
		Box horizontalButtonsGroup = Box.createHorizontalBox();
		horizontalButtonsGroup.add(getJButton1());
		horizontalButtonsGroup.add(getJButton0());

		// PANNELLO MESSAGGI E NOTIFICHE
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

		// BOX CONTENITORE
		Box verticalGroup = Box.createVerticalBox();
		verticalGroup.add(horizontalFormGroup);
		verticalGroup.add(horizontalButtonsGroup);
		verticalGroup.add(horizontalMessageGroup);

		JPanel content = new JPanel();
		content.add(verticalGroup);
		content.setBackground(Color.WHITE);
		
		JPanel south = new JPanel();
		south.setBackground(new Color(79, 162, 225));
		setLayout(new BorderLayout());
		add(content, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
		setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
	}
	
	private void setLabelStyle(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(new Color(79, 162, 225));
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
		TransaxionTypeEntity transaxionTypeEntity = new TransaxionTypeEntity();
		String message = "";
		boolean esitoOperazione = Boolean.FALSE;

		// Controllo descrizione transazione
		if ("".equals(getDescrizione().getText().trim())) {
			getErrorDescrizioneMessage().setForeground(new Color(195, 37, 37));
			getErrorDescrizioneMessage().setText("*");
			esitoOperazione = Boolean.FALSE;
		} else {
			transaxionTypeEntity.setDescription(getDescrizione().getText().toUpperCase());
			esitoOperazione = Boolean.TRUE;
		}

		// Controllo descrizione transazione
		String tipoTransazioneCombo = ((String) getTipoTransazione().getSelectedItem()).trim();
		TransaxionSubtypeEntity transaxionSubtypeEntity = new TransaxionSubtypeEntity();
		transaxionTypeEntity.setTransaxionSubtype(transaxionSubtypeEntity);
		if (Costanti.USCITA_LABEL.equals(tipoTransazioneCombo))
			transaxionSubtypeEntity.setId('O');
		else if (Costanti.ENTRATA_LABEL.equals(tipoTransazioneCombo))
			transaxionSubtypeEntity.setId('I');
		else {
			getErrorTipoTransazioneMessage().setForeground(new Color(195, 37, 37));
			getErrorTipoTransazioneMessage().setText("*");
			esitoOperazione = Boolean.FALSE;
		}

		// Insert transazione
		if (esitoOperazione) {
			try {
				int selected = JOptionPane.showConfirmDialog(this, getMessageSource().getMessage("message.confirmins", null, "Confermi l'inserimento?" , Locale.getDefault()), getMessageSource().getMessage("message.confirminspaneltitle", null, "Conferma inserimento" , Locale.getDefault()), JOptionPane.OK_CANCEL_OPTION);
				if(selected == 0) {
					getBusinessDelegate().insertTransaxionType(transaxionTypeEntity);
					message = "Tipo transazione inserito con successo!";
					esitoOperazione = true;
					resetFormComponents();
					getEsito().setIcon(new ImageIcon("resources/images/ok.png"));
					getEsito().setForeground(new Color(58, 134, 197));
				}
			} catch (Exception e) {
				logger
						.error(
								"Si � verificato un errore durante l'inserimento del tipo transazione",
								e);
				message = "Si � verificato un errore durante l'inserimento del tipo transazione";
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
		getDescrizione().setText("");
		getTipoTransazione().setSelectedIndex(0);
		resetFormMessages();
	}

	private void resetFormMessages() {
		getErrorDescrizioneMessage().setText("");
		getErrorTipoTransazioneMessage().setText("");
		getEsito().setText("");
		getEsito().setIcon(new ImageIcon());
	}

	public void resetAllComponents() {
		resetFormComponents();
		resetFormMessages();
		getEsito().setText("");
	}

	public JTextField getDescrizione() {
		if (descrizione == null) {
			descrizione = new JTextField();
			((AbstractDocument) descrizione.getDocument())
					.setDocumentFilter(new DocumentSizeFilter(50));
			descrizione.setColumns(15);
		}
		return descrizione;
	}

	public JComboBox getTipoTransazione() {
		if (tipoTransazione == null) {
			DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
			defaultComboBoxModel.addElement("");
			defaultComboBoxModel.addElement(Costanti.USCITA_LABEL);
			defaultComboBoxModel.addElement(Costanti.ENTRATA_LABEL);
			tipoTransazione = new JComboBox(defaultComboBoxModel);
			tipoTransazione.setBackground(Color.WHITE);
		}
		return tipoTransazione;
	}

	public void setDescrizione(JTextField descrizione) {
		this.descrizione = descrizione;
	}

	public JLabel getErrorDescrizioneMessage() {
		if (errorDescrizioneMessage == null)
			errorDescrizioneMessage = new JLabel("");
		return errorDescrizioneMessage;
	}

	public JLabel getErrorTipoTransazioneMessage() {
		if (errorTipoTransazioneMessage == null)
			errorTipoTransazioneMessage = new JLabel("");
		return errorTipoTransazioneMessage;
	}

	public JLabel getEsito() {
		if (esito == null) {
			esito = new JLabel("");
			esito.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return esito;
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
