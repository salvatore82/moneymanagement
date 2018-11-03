/**
 * 
 */
package it.moneymanagement.UI.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jfree.ui.RefineryUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import it.moneymanagement.UI.menubar.PrincipaleMenuBar;
import it.moneymanagement.UI.panel.PrincipalePanel;
import it.moneymanagement.dbmgmt.DBManagement;

public class PrincipaleFrame extends JFrame {

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PrincipaleFrame.class);
	private BeanFactory ctx;
	private static DBManagement dbManagement;
	private static PrincipalePanel principalePanel;
	private static PrincipaleMenuBar principaleMenuBar;
	private static SplashScreen splash = new SplashScreen();
	
	public PrincipaleFrame() {
		//init();
	}

	private void init() {
		installLnF();
		disableDerbyLog();
		ctx = new XmlBeanFactory(new ClassPathResource("/mm_conf.xml"));
		principalePanel = (PrincipalePanel) ctx.getBean("principalePanel");
		principaleMenuBar = (PrincipaleMenuBar) ctx.getBean("principaleMenuBar");
		setJMenuBar(principaleMenuBar);
	}

	private static void installLnF() {
		try {
			UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
			logger.debug("Look and Feel impostato correttamente");
		} catch (Exception e) {
			logger.error("Problemi nell'impostazione del Look and Feel su questa piattaforma, setto default", e);
		}
	}
	
	private static void disableDerbyLog() {
		try {
			System.setProperty("derby.stream.error.field", "it.moneymanagement.dbmgmt.DBManagement.DEV_NULL");
		} catch (Exception e) {
			logger.error("Si � verificato un errore durante l'inizializzazione del database.",e);
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args) {
		runLoader();
		runApp();
	}

	private static void runApp() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				logger.info("STARTING APP...");
				PrincipaleFrame frame = new PrincipaleFrame();
				frame.setName("principaleFrame");
				frame.setDefaultCloseOperation(PrincipaleFrame.EXIT_ON_CLOSE);
				frame.setTitle("Money Management");
				frame.setIconImage(new ImageIcon("resources/images/money.png").getImage());
				frame.setLayout(new BorderLayout());
				frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				frame.pack();
				frame.setSize(new Dimension(1000, 650));
				RefineryUtilities.centerFrameOnScreen(frame);
				frame.setResizable(false);
				frame.setVisible(true);
				frame.init();
				frame.add(getPrincipalePanel(), BorderLayout.CENTER);
				SwingUtilities.updateComponentTreeUI(frame);
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				splash.dispose();
				splash.setVisible(false);
				logger.info("APP STARTED!");
			}
		});
	}

	private static void runLoader() {
		logger.info("STARTING LOADER...");
        splash.showSplash();
		logger.info("LOADER STARTED!");
	}

	public static PrincipalePanel getPrincipalePanel() {
		return principalePanel;
	}

	public static void setPrincipalePanel(PrincipalePanel principalePanel) {
		PrincipaleFrame.principalePanel = principalePanel;
	}

	public static PrincipaleMenuBar getPrincipaleMenuBar() {
		return principaleMenuBar;
	}

	public static void setPrincipaleMenuBar(PrincipaleMenuBar principaleMenuBar) {
		PrincipaleFrame.principaleMenuBar = principaleMenuBar;
	}

	public static DBManagement getDbManagement() {
		return dbManagement;
	}

	public static void setDbManagement(DBManagement dbManagement) {
		PrincipaleFrame.dbManagement = dbManagement;
	}

}
