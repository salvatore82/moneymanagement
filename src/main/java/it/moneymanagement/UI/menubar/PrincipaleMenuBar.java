/**
 * 
 */
package it.moneymanagement.UI.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.UI.panel.ContentPanel;
import it.moneymanagement.UI.panel.HeaderPanel;
import it.moneymanagement.UI.panel.PrincipalePanel;
import it.moneymanagement.UI.panel.inner.CercaEntrataPanel;
import it.moneymanagement.UI.panel.inner.CercaUscitaPanel;
import it.moneymanagement.UI.panel.inner.ImpostazioniDBPanel;
import it.moneymanagement.UI.panel.inner.InsertEntrataPanel;
import it.moneymanagement.UI.panel.inner.InsertTipoTransazionePanel;
import it.moneymanagement.UI.panel.inner.InsertUscitaPanel;
import it.moneymanagement.UI.panel.inner.RiepilogoPanel;

/**
 * @author Salvatore
 * 
 */
public class PrincipaleMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private JMenu entrateMenu;
	private JMenu usciteMenu;
	private JMenu riepilogoMenu;
	private JMenu impostazioniMenu;
	private JMenu aboutMenu;
	private JMenuItem nuovaEntrataMenuItem;
	private JMenuItem nuovaUscitaMenuItem;
	private JMenuItem nuovaTransazioneMenuItem;
	private JMenuItem cercaUscitaMenuItem;
	private JMenuItem cercaEntrataMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem impostazioniMenuItem;
	private JMenuItem riepilogoMenuItem;
	
	private ResourceBundleMessageSource messageSource;
	private PrincipalePanel principalePanel;
	private HeaderPanel headerPanel;
	private ContentPanel contentPanel;
	private InsertEntrataPanel insertEntrataPanel;
	private CercaEntrataPanel cercaEntrataPanel;
	private InsertUscitaPanel insertUscitaPanel;
	private CercaUscitaPanel cercaUscitaPanel;
	private InsertTipoTransazionePanel insertTipoTransazionePanel;
	private RiepilogoPanel riepilogoPanel;
	private ImpostazioniDBPanel impostazioniDBPanel;

	public PrincipaleMenuBar() {
	}

	public void initComponents() {
		add(getEntrateMenu());
		add(getUsciteMenu());
		add(getRiepilogoMenu());
		add(getImpostazioniMenu());
		add(getAboutMenu());
	}

	/**
	 * GESTIONE VOCE MENU ENTRATE
	 * 
	 * @return
	 */
	private JMenu getEntrateMenu() {
		if (entrateMenu == null) {
			entrateMenu = new JMenu();
			entrateMenu.setText(getMessageSource().getMessage("menubar.entrate", null, "Entrate" , Locale.getDefault()));
			entrateMenu.add(getNuovaEntrataMenuItem());
			entrateMenu.add(getCercaEntrataMenuItem());
		}
		return entrateMenu;
	}

	private JMenuItem getNuovaEntrataMenuItem() {
		if (nuovaEntrataMenuItem == null) {
			nuovaEntrataMenuItem = new JMenuItem();
			nuovaEntrataMenuItem.setText(getMessageSource().getMessage("menubar.nuovo", null, "Nuovo" , Locale.getDefault()));
			nuovaEntrataMenuItem.setIcon(new ImageIcon("resources/images/add.png"));
			nuovaEntrataMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					nuovaEntrataAction(event);
				}
			});
		}
		return nuovaEntrataMenuItem;
	}

	private JMenuItem getCercaEntrataMenuItem() {
		if (cercaEntrataMenuItem == null) {
			cercaEntrataMenuItem = new JMenuItem();
			cercaEntrataMenuItem.setText(getMessageSource().getMessage("menubar.cerca", null, "Cerca" , Locale.getDefault()));
			cercaEntrataMenuItem.setIcon(new ImageIcon("resources/images/cerca.png"));
			cercaEntrataMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					cercaEntrataAction(event);
				}
			});
		}
		return cercaEntrataMenuItem;
	}

	/**
	 * GESTIONE VOCE MENU USCITE
	 * 
	 * @return
	 */
	private JMenu getUsciteMenu() {
		if (usciteMenu == null) {
			usciteMenu = new JMenu();
			usciteMenu.setText(getMessageSource().getMessage("menubar.uscite", null, "Uscite" , Locale.getDefault()));
			usciteMenu.add(getNuovaUscitaMenuItem());
			usciteMenu.add(getCercaUscitaMenuItem());
		}
		return usciteMenu;
	}

	private JMenuItem getNuovaUscitaMenuItem() {
		if (nuovaUscitaMenuItem == null) {
			nuovaUscitaMenuItem = new JMenuItem();
			nuovaUscitaMenuItem.setText(getMessageSource().getMessage("menubar.nuovo", null, "Nuovo" , Locale.getDefault()));
			nuovaUscitaMenuItem.setIcon(new ImageIcon("resources/images/add.png"));
			nuovaUscitaMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					nuovaUscitaAction(event);
				}
			});
		}
		return nuovaUscitaMenuItem;
	}

	private JMenuItem getCercaUscitaMenuItem() {
		if (cercaUscitaMenuItem == null) {
			cercaUscitaMenuItem = new JMenuItem();
			cercaUscitaMenuItem.setText(getMessageSource().getMessage("menubar.cerca", null, "Cerca" , Locale.getDefault()));
			cercaUscitaMenuItem.setIcon(new ImageIcon("resources/images/cerca.png"));
			cercaUscitaMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					cercaUscitaAction(event);
				}
			});
		}
		return cercaUscitaMenuItem;
	}

	/**
	 * GESTIONE VOCE MENU TRANSAZIONI
	 * 
	 * @return
	 */

	
	/**
	 * GESTIONE VOCE MENU RIEPILOGO
	 * 
	 * @return
	 */
	public JMenu getRiepilogoMenu() {
		if (riepilogoMenu == null) {
			riepilogoMenu = new JMenu();
			riepilogoMenu.setText(getMessageSource().getMessage("menubar.visualizza", null, "Visualizza" , Locale.getDefault()));
			riepilogoMenu.add(getRiepilogoMenuItem());
		}
		return riepilogoMenu;
	}
	
	public JMenuItem getRiepilogoMenuItem() {
		if (riepilogoMenuItem == null) {
			riepilogoMenuItem = new JMenuItem();
			riepilogoMenuItem.setText(getMessageSource().getMessage("menubar.riepilogo", null, "Riepilogo" , Locale.getDefault()));
			riepilogoMenuItem.setIcon(new ImageIcon("resources/images/papiro.png"));
			riepilogoMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					riepilogoAction(event);
				}
			});
		}
		return riepilogoMenuItem;
	}
	
	/**
	 * GESTIONE VOCE MENU IMPOSTAZIONI
	 * 
	 * @return
	 */
	public JMenu getImpostazioniMenu() {
		if (impostazioniMenu == null) {
			impostazioniMenu = new JMenu();
			impostazioniMenu.setText(getMessageSource().getMessage("menubar.impostazioni", null, "Impostazioni" , Locale.getDefault()));
			impostazioniMenu.add(getImpostazioniMenuItem());
			impostazioniMenu.add(getNuovaTransazioneMenuItem());
		}
		return impostazioniMenu;
	}
	
	public JMenuItem getImpostazioniMenuItem() {
		if (impostazioniMenuItem == null) {
			impostazioniMenuItem = new JMenuItem();
			impostazioniMenuItem.setText(getMessageSource().getMessage("menubar.impostazionidb", null, "Database" , Locale.getDefault()));
			impostazioniMenuItem.setIcon(new ImageIcon("resources/images/dbsettings.png"));
			impostazioniMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					impostazioniDBAction(event);
				}
			});
		}
		return impostazioniMenuItem;
	}
	
	private JMenuItem getNuovaTransazioneMenuItem() {
		if (nuovaTransazioneMenuItem == null) {
			nuovaTransazioneMenuItem = new JMenuItem();
			nuovaTransazioneMenuItem.setText(getMessageSource().getMessage("menubar.nuovotipotx", null, "Tipo transazione" , Locale.getDefault()));
			nuovaTransazioneMenuItem.setIcon(new ImageIcon("resources/images/frecciaright.png"));
			nuovaTransazioneMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					nuovaTransazioneAction(event);
				}
			});
		}
		return nuovaTransazioneMenuItem;
	}

	/**
	 * GESTIONE VOCE MENU HELP
	 * 
	 * @return
	 */
	private JMenu getAboutMenu() {
		if (aboutMenu == null) {
			aboutMenu = new JMenu();
			aboutMenu.setText(getMessageSource().getMessage("menubar.help", null, "?" , Locale.getDefault()));
			aboutMenu.add(getAboutMenuItem());
		}
		return aboutMenu;
	}

	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText(getMessageSource().getMessage("menubar.about", null, "Riguardo a" , Locale.getDefault()));
			aboutMenuItem.setIcon(new ImageIcon("resources/images/information.png"));
			aboutMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					aboutAction(event);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * LISTENER VOCE MENU ENTRATE->NUOVO
	 * 
	 * @param event
	 */
	private void nuovaEntrataAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/ins_entrata_head.png"));
		
		InsertEntrataPanel insertEntrataPanel = getInsertEntrataPanel();
		insertEntrataPanel.resetAllComponents();
		getContentPanel().add(insertEntrataPanel);
		
		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}

	/**
	 * LISTENER VOCE MENU ENTRATE->CERCA
	 * 
	 * @param event
	 */
	private void cercaEntrataAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/cerca_entrata_head.png"));
		
		CercaEntrataPanel cercaEntrataPanel = getCercaEntrataPanel();
		cercaEntrataPanel.resetAllComponents();
		getContentPanel().add(cercaEntrataPanel);
		
		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}

	/**
	 * LISTENER VOCE MENU USCITE->NUOVO
	 * 
	 * @param event
	 */
	private void nuovaUscitaAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/ins_uscita_head.png"));
		
		InsertUscitaPanel insertUscitaPanel = getInsertUscitaPanel();
		insertUscitaPanel.resetAllComponents();
		getContentPanel().add(insertUscitaPanel);
		
		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}

	/**
	 * LISTENER VOCE MENU USCITE->CERCA
	 * 
	 * @param event
	 */
	private void cercaUscitaAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/cerca_uscita_head.png"));
		
		CercaUscitaPanel cercaUscitaPanel = getCercaUscitaPanel();
		cercaUscitaPanel.resetAllComponents();
		getContentPanel().add(cercaUscitaPanel);
		
		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}

	/**
	 * LISTENER VOCE MENU TRANSAZIONI->NUOVO
	 * 
	 * @param event
	 */
	private void nuovaTransazioneAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/ins_tip_tran_head.png"));
		
		InsertTipoTransazionePanel insertTipoTransazionePanel = getInsertTipoTransazionePanel();
		insertTipoTransazionePanel.resetAllComponents();
		getContentPanel().add(insertTipoTransazionePanel);

		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}

	/**
	 * LISTENER VOCE MENU VISUALIZZA->RIEPILOGO
	 * 
	 * @param event
	 */
	private void riepilogoAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/riepilogo_head.png"));
		
		RiepilogoPanel riepilogoPanel = getRiepilogoPanel();
		riepilogoPanel.resetAllComponents();
		getContentPanel().add(riepilogoPanel);

		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}
	
	/**
	 * LISTENER VOCE MENU IMPOSTAZIONI->DB
	 * 
	 * @param event
	 */
	private void impostazioniDBAction(ActionEvent event) {
		getContentPanel().removeAll();

		getHeaderPanel().getHeaderIcon().setIcon(new ImageIcon("resources/images/gest_db_head.png"));
		
		ImpostazioniDBPanel impostazioniDBPanel = getImpostazioniDBPanel();
		impostazioniDBPanel.resetFormMessages();
		getContentPanel().add(impostazioniDBPanel);

		SwingUtilities.updateComponentTreeUI(getPrincipalePanel());
	}
	
	/**
	 * LISTENER VOCE MENU HELP->ABOUT
	 * 
	 * @param event
	 */
	private void aboutAction(ActionEvent event) {
		StringBuilder testo = new StringBuilder();
		testo.append("Money Management � un'applicazione nata per gestire l'economia del singolo/famiglia.");
		testo.append("\n");
		testo.append("Version: 4.0");
		testo.append("\n");
		testo.append("�Salvatore De Luca 2011.  Tutti i diritti riservati.");
		
		JOptionPane.showMessageDialog(getPrincipalePanel(), testo, getMessageSource().getMessage("app.title", null, "Money Management" , Locale.getDefault()), JOptionPane.DEFAULT_OPTION, new ImageIcon("resources/images/header_l.png"));
	}

	public PrincipalePanel getPrincipalePanel() {
		return principalePanel;
	}

	public void setPrincipalePanel(PrincipalePanel principalePanel) {
		this.principalePanel = principalePanel;
	}

	public HeaderPanel getHeaderPanel() {
		return headerPanel;
	}

	public void setHeaderPanel(HeaderPanel headerPanel) {
		this.headerPanel = headerPanel;
	}

	public ContentPanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	public InsertUscitaPanel getInsertUscitaPanel() {
		return insertUscitaPanel;
	}

	public void setInsertUscitaPanel(InsertUscitaPanel insertUscitaPanel) {
		this.insertUscitaPanel = insertUscitaPanel;
	}

	public CercaUscitaPanel getCercaUscitaPanel() {
		return cercaUscitaPanel;
	}

	public void setCercaUscitaPanel(CercaUscitaPanel cercaUscitaPanel) {
		this.cercaUscitaPanel = cercaUscitaPanel;
	}

	public InsertTipoTransazionePanel getInsertTipoTransazionePanel() {
		return insertTipoTransazionePanel;
	}

	public void setInsertTipoTransazionePanel(
			InsertTipoTransazionePanel insertTipoTransazionePanel) {
		this.insertTipoTransazionePanel = insertTipoTransazionePanel;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public InsertEntrataPanel getInsertEntrataPanel() {
		return insertEntrataPanel;
	}

	public void setInsertEntrataPanel(InsertEntrataPanel insertEntrataPanel) {
		this.insertEntrataPanel = insertEntrataPanel;
	}

	public CercaEntrataPanel getCercaEntrataPanel() {
		return cercaEntrataPanel;
	}

	public void setCercaEntrataPanel(CercaEntrataPanel cercaEntrataPanel) {
		this.cercaEntrataPanel = cercaEntrataPanel;
	}

	public ImpostazioniDBPanel getImpostazioniDBPanel() {
		return impostazioniDBPanel;
	}

	public void setImpostazioniDBPanel(ImpostazioniDBPanel impostazioniDBPanel) {
		this.impostazioniDBPanel = impostazioniDBPanel;
	}

	public RiepilogoPanel getRiepilogoPanel() {
		return riepilogoPanel;
	}

	public void setRiepilogoPanel(RiepilogoPanel riepilogoPanel) {
		this.riepilogoPanel = riepilogoPanel;
	}

}
