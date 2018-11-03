/**
 * 
 */
package it.moneymanagement.UI.panel.inner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.Costanti;
import it.moneymanagement.dbmgmt.DBManagement;

public class ImpostazioniDBPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(ImpostazioniDBPanel.class);
	
	private JButton jButton0;
	private JLabel esito;
	private DBManagement dbManagement;
	private ResourceBundleMessageSource messageSource;
	//BOX CONTENITORE PULSANTI E NOTIFICHE
	private Box verticalFormGroup;
	//BOX CONTENITORE PULSANTI BACKUP
	private Box horizontalButtonsBackupGroup;
	
	public ImpostazioniDBPanel() {
	}

	public void initComponents() {
		setBackground(Color.WHITE);
		// BOX PULSANTI
		Box horizontalButtonsGroup = Box.createHorizontalBox();
		horizontalButtonsGroup.add(getjButton0());
		
		// PANEL PULSANTI
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		BoxLayout layout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
		buttonsPanel.setLayout(layout);
		buttonsPanel.setBackground(Color.WHITE);
		//Aggiungo una spacer
		buttonsPanel.add(new JLabel(" "));
		buttonsPanel.add(horizontalButtonsGroup);
		
		// BOX MESSAGGI E NOTIFICHE
		JPanel messagePanel = new JPanel(new BorderLayout());
		BoxLayout layoutMP = new BoxLayout(messagePanel, BoxLayout.Y_AXIS);
		messagePanel.setLayout(layoutMP);
		messagePanel.setBackground(Color.WHITE);
		//Aggiungo una spacer
		messagePanel.add(new JLabel(" "));
		messagePanel.add(getEsito());
		
		// BOX MESSAGGI E NOTIFICHE
		Box horizontalMessageGroup = Box.createHorizontalBox();
		horizontalMessageGroup.add(messagePanel);
		
		//BOX CONTENITORE PULSANTI E NOTIFICHE
		verticalFormGroup = Box.createVerticalBox();
		verticalFormGroup.add(buttonsPanel);
		verticalFormGroup.add(horizontalMessageGroup);
		
		JPanel south = new JPanel();
		south.setBackground(new Color(226, 192, 72));
		setLayout(new BorderLayout());
		add(verticalFormGroup, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
		setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
	}

	public JButton getjButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(getMessageSource().getMessage("button.backupdb", null, "Backup database" , Locale.getDefault()));
			jButton0.setIcon(new ImageIcon("resources/images/dbsave.png"));
			jButton0.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					Date today = new Date(Calendar.getInstance().getTimeInMillis());
					SimpleDateFormat sdf = new SimpleDateFormat(Costanti.FORMATO_DATA_BKP_PREDEFINITO);
					String dataFormattata = sdf.format(today);
					File file = new File(DBManagement.DB_NAME + "_" + dataFormattata);
					chooser.setSelectedFile(file);
					int option = chooser.showOpenDialog(null);
					if(option == JFileChooser.APPROVE_OPTION) {  
						if(chooser.getSelectedFile()!=null) {  
							File fileToSave = chooser.getSelectedFile();  
							backupDatabase(e,fileToSave);
						}
					}
				}
			});
		}
		return jButton0;
	}

	public void resetFormMessages() {
		getEsito().setText("");
		getEsito().setIcon(new ImageIcon());
		if(horizontalButtonsBackupGroup!=null) {
			horizontalButtonsBackupGroup.removeAll();
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
	
	private void backupDatabase(ActionEvent evt, File file) {
		resetFormMessages();
		int selected = JOptionPane.showConfirmDialog(this, getMessageSource().getMessage("message.confirmbkpdb", null, "Confermi il backup?" , Locale.getDefault()), getMessageSource().getMessage("message.confirmbkpdbpaneltitle", null, "Conferma backup" , Locale.getDefault()), JOptionPane.OK_CANCEL_OPTION);
		String message = "";
		if(selected == 0) {
			try {
				getDbManagement().backupDatabase(file);
				message = "Backup database eseguito!";
				getEsito().setIcon(new ImageIcon("resources/images/ok.png"));
				getEsito().setForeground(new Color(58, 134, 197));
			} catch (IOException e) {
				message = "Si � verificato un errore durante il backup del database";
				logger.error(message,e);
				getEsito().setForeground(new Color(195, 37, 37));
				getEsito().setIcon(new ImageIcon("resources/images/cross.png"));
			}
		}
		
		getEsito().setText(message);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public DBManagement getDbManagement() {
		return dbManagement;
	}

	public void setDbManagement(DBManagement dbManagement) {
		this.dbManagement = dbManagement;
	}
	
	public JLabel getEsito() {
		if (esito == null) {
			esito = new JLabel("");
			esito.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return esito;
	}

}
