/**
 * 
 */
package it.moneymanagement.UI.panel.inner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.Costanti;
import it.moneymanagement.UI.component.DatePicker;
import it.moneymanagement.UI.component.ObservingTextField;
import it.moneymanagement.UI.listeners.ImageMouseListener;
import it.moneymanagement.UI.models.TransazioniRiepilogoTableModel;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.entity.OutcomeEntity;
import it.moneymanagement.business.dao.entity.TransaxionEntity;
import it.moneymanagement.utils.format.MoneyFormat;
import it.moneymanagement.utils.format.WebDateFormat;

public class RiepilogoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(RiepilogoPanel.class);

	private JButton jButton0;
	private ObservingTextField dataDa;
	private ObservingTextField dataA;
	private DatePicker dataPickerDa;
	private DatePicker dataPickerA;
	private JLabel entrataTotale;
	private JLabel prelieviTotale;
	private JLabel extraTotale;
	private JLabel uscitaTotale;
	private JLabel uscitaObbligatoriaTotale;
	private JLabel uscitaNonObbligatoriaTotale;
	private JTable tabella;
	private JScrollPane tabellaPanel;
	private TransazioniRiepilogoTableModel transazioniTableModel;
	
	private WebDateFormat webDateFormat;
	private MoneyFormat moneyFormat;

	private BusinessDelegate businessDelegate;
	private ResourceBundleMessageSource messageSource;
	private ImageMouseListener imageMouseListener;
	
	private String moneySymbol;
	
	private Date da = null;
	private Date a = null;
	
	private double totaleEntrata = 0;
	private double totaleUscita = 0;
	private double totaleExtra = 0;
	private double totalePrelievo = 0;
	private double totaleUscitaNonObbligatoria = 0;
	private double totaleUscitaObbligatoria = 0;
	
	private Hashtable<String, Double> datiGrafico;

	public RiepilogoPanel() {
	}

	public void initComponents() {
		setBackground(Color.WHITE);
		setMoneySymbol(getMessageSource().getMessage("label.moneySymbol", null, "�", Locale.getDefault()));
		
		// CAMPI FORM
		JPanel fields = new JPanel();
		JLabel daLabel = new JLabel(getMessageSource().getMessage("label.datada", null, "Da" , Locale.getDefault())); 
		setLabelStyle(daLabel);
		fields.add(daLabel);
		fields.add(getDataDa());
		fields.add(getDatePickerDa());
		JLabel aLabel = new JLabel(getMessageSource().getMessage("label.dataa", null, "A" , Locale.getDefault())); 
		setLabelStyle(aLabel);
		fields.add(aLabel);
		fields.add(getDataA());
		fields.add(getDatePickerA());
		fields.add(getJButton0());
		fields.setBackground(Color.WHITE);

		Border borderLine = BorderFactory.createLineBorder(Color.GRAY);

		JPanel entrata = new JPanel();
		entrata.setBackground(Color.WHITE);
		entrata.add(getEntrataTotale());
		TitledBorder tb = new TitledBorder(borderLine, getMessageSource().getMessage("menubar.entrate",
				null, "Entrate", Locale.getDefault()));
		entrata.setBorder(tb);

		JPanel prelievi = new JPanel();
		prelievi.setBackground(Color.WHITE);
		prelievi.add(getPrelieviTotale());
		tb = new TitledBorder(borderLine, getMessageSource().getMessage("riepilogo.prelievi",
				null, "Prelievi", Locale.getDefault()));
		prelievi.setBorder(tb);

		JPanel extra = new JPanel();
		extra.setBackground(Color.WHITE);
		extra.add(getExtraTotale());
		tb = new TitledBorder(borderLine, getMessageSource().getMessage("riepilogo.extra",
				null, "Extra", Locale.getDefault()));
		extra.setBorder(tb);

		// BOX UP FIELDSET FORM
		JPanel pannelloSuperiore = new JPanel(new GridLayout(1, 3));
		pannelloSuperiore.add(entrata);
		pannelloSuperiore.add(prelievi);
		pannelloSuperiore.add(extra);

		JPanel uscita = new JPanel();
		uscita.setBackground(Color.WHITE);
		uscita.add(getUscitaTotale());
		tb = new TitledBorder(borderLine, getMessageSource().getMessage("menubar.uscite",
				null, "Uscite", Locale.getDefault()));
		uscita.setBorder(tb);

		JPanel uscitaObb = new JPanel();
		uscitaObb.setBackground(Color.WHITE);
		uscitaObb.add(getUscitaObbligatoriaTotale());
		tb = new TitledBorder(borderLine, getMessageSource().getMessage("riepilogo.usciteObb",
				null, "Uscite obbligatorie", Locale.getDefault()));
		uscitaObb.setBorder(tb);

		JPanel uscitaNonObb = new JPanel();
		uscitaNonObb.setBackground(Color.WHITE);
		uscitaNonObb.add(getUscitaNonObbligatoriaTotale());
		tb = new TitledBorder(borderLine, getMessageSource().getMessage("riepilogo.usciteNonObb",
				null, "Uscite non obbligatorie", Locale.getDefault()));
		uscitaNonObb.setBorder(tb);

		// BOX DOWN FIELDSET FORM
		JPanel pannelloInferiore = new JPanel(new GridLayout(1, 3));
		pannelloInferiore.add(uscita);
		pannelloInferiore.add(uscitaObb);
		pannelloInferiore.add(uscitaNonObb);

		// BOX CAMPI FORM
		Box verticalFormGroup = Box.createVerticalBox();
		verticalFormGroup.add(fields);
		verticalFormGroup.add(pannelloSuperiore);
		verticalFormGroup.add(pannelloInferiore);

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
		label.setForeground(new Color(157, 157, 255));
	}

	private JButton getDatePickerDa() {
		JButton btn = new JButton(
				new ImageIcon("resources/images/calendar.png"));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDataPickerDa().setObserver(getDataDa());
				getDataPickerDa().init();
				Date selectedDate = getDataPickerDa().parseDate(
						getDataDa().getText());
				getDataPickerDa().setSelectedDate(selectedDate);
				getDataPickerDa().start(getDataDa());
			};
		});
		return btn;
	}

	private JButton getDatePickerA() {
		JButton btn = new JButton(
				new ImageIcon("resources/images/calendar.png"));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDataPickerA().setObserver(getDataA());
				getDataPickerA().init();
				Date selectedDate = getDataPickerA().parseDate(
						getDataA().getText());
				getDataPickerA().setSelectedDate(selectedDate);
				getDataPickerA().start(getDataA());
			};
		});
		return btn;
	}

	public JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(getMessageSource().getMessage("button.cerca",
					null, "Cerca", Locale.getDefault()));
			jButton0.setIcon(new ImageIcon("resources/images/cerca.png"));
			jButton0.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					jButton0ActionActionPerformed(e);
				}
			});
		}
		return jButton0;
	}

	protected void jButton0ActionActionPerformed(ActionEvent event) {
		getTransazioniTableModel().flush();
		resetDate();
		
		// Controllo data da
		try {
			setDa(getWebDateFormat().parse(getDataDa().getText()));
			if (!getWebDateFormat().format(getDa()).equals(getDataDa().getText())) {
				throw new ParseException("", 0);
			}
		} catch (ParseException e) {
			if (!"".equals(getDataDa().getText())) {
				logger.error("Errore nella conversione della data da, input " + getDataDa().getText(), e);
			}
			getDataDa().setText("");
		}

		// Controllo data a
		try {
			setA(getWebDateFormat().parse(getDataA().getText()));
			if (!getWebDateFormat().format(getA()).equals(getDataA().getText())) {
				throw new ParseException("", 0);
			}
		} catch (ParseException e) {
			if (!"".equals(getDataA().getText())) {
				logger.error("Errore nella conversione della data a, input " + getDataA().getText(), e);
			}
			getDataA().setText("");
		}

		//E' importante che vengano calcolati prima l'entrata e l'uscita totale in modo da poter calcolare le percentuali
		//sulle successive voci. In caso contrario le percentuali non saranno corrette.
		resetConteggi();
		calcolaEntrataTotale();
		calcolaUscitaTotale();
		calcolaPrelieviTotale();
		calcolaExtraTotale();
		calcolaUscitaObbligatoriaTotale();
		calcolaUscitaNonObbligatoriaTotale();
		
		getTabella().setModel(getTransazioniTableModel());
		getTabellaPanel().setViewportView(getTabella());
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void calcolaEntrataTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		TransaxionEntity transaxionEntity = new TransaxionEntity();
		IncomeEntity incomeEntity = new IncomeEntity(transaxionEntity);
		Double valore = getBusinessDelegate().calculateIncome(incomeEntity, interval);
		if(valore>0) {
			setTotaleEntrata(valore);
			getEntrataTotale().setText(getMoneyFormat().format(valore) + getMoneySymbol());
			getEntrataTotale().setName(Costanti.KEY_LISTENER_ENTRATA_TOTALE);
			getEntrataTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getEntrataTotale().addMouseListener(getImageMouseListener());
		}
	}
	
	private void calcolaUscitaTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		OutcomeEntity outcomeEntity = new OutcomeEntity(new TransaxionEntity(), null, null);
		Double valore = getBusinessDelegate().calculateOutcomeWithoutWithdrawal(outcomeEntity, interval);
		if(valore>0) {
			setTotaleUscita(valore);
			double percentuale = calcolaPercentuale(valore,getTotaleEntrata());
			String percentile = getMoneyFormat().format(percentuale);
			StringBuilder testo = new StringBuilder();
			testo.append(getMoneyFormat().format(valore));
			testo.append(getMoneySymbol());
			testo.append(" ");
			testo.append("(");
			testo.append(Costanti.ENTRATA_KEY);
			testo.append(":");
			testo.append(percentile);
			testo.append("%");
			testo.append(")");
			getUscitaTotale().setText(testo.toString());
			getUscitaTotale().setName(Costanti.KEY_LISTENER_USCITA_TOTALE);
			getUscitaTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getUscitaTotale().addMouseListener(getImageMouseListener());
		}
	}
	
	private void calcolaUscitaNonObbligatoriaTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		OutcomeEntity outcomeEntity = new OutcomeEntity(null, Short.valueOf("0"), null);
		Double valore = getBusinessDelegate().calculateOutcomeWithoutWithdrawal(outcomeEntity, interval);
		if(valore>0) {
			setTotaleUscitaNonObbligatoria(valore);
			String testo = elaboraPercentualiUscite(valore);
			getUscitaNonObbligatoriaTotale().setText(testo);
			getUscitaNonObbligatoriaTotale().setName(Costanti.KEY_LISTENER_USCITA_NON_OBB_TOTALE);
			getUscitaNonObbligatoriaTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getUscitaNonObbligatoriaTotale().addMouseListener(getImageMouseListener());
		}
	}

	private void calcolaUscitaObbligatoriaTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		OutcomeEntity outcomeEntity = new OutcomeEntity(null, Short.valueOf("1"), null);
		Double valore = getBusinessDelegate().calculateOutcomeWithoutWithdrawal(outcomeEntity, interval);
		if(valore>0) {
			setTotaleUscitaObbligatoria(valore);
			String testo = elaboraPercentualiUscite(valore);
			getUscitaObbligatoriaTotale().setText(testo);
			getUscitaObbligatoriaTotale().setName(Costanti.KEY_LISTENER_USCITA_OBB_TOTALE);
			getUscitaObbligatoriaTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getUscitaObbligatoriaTotale().addMouseListener(getImageMouseListener());
		}
	}

	private void calcolaExtraTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		OutcomeEntity outcomeEntity = new OutcomeEntity(null, null, Short.valueOf("1"));
		Double valore = getBusinessDelegate().calculateOutcomeWithoutWithdrawal(outcomeEntity, interval);
		if(valore>0) {
			setTotaleExtra(valore);
			String testo = elaboraPercentualiUscite(valore);
			getExtraTotale().setText(testo);
			getExtraTotale().setName(Costanti.KEY_LISTENER_EXTRA_TOTALE);
			getExtraTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getExtraTotale().addMouseListener(getImageMouseListener());
		}
	}

	private void calcolaPrelieviTotale() {
		Date [] interval = new Date[2];
		interval[0] = getDa();
		interval[1] = getA();
		Double valore = getBusinessDelegate().calculateOutcomeWithdrawal(new OutcomeEntity(), interval);
		if(valore>0) {
			setTotalePrelievo(valore);
			String testo = elaboraPercentualiUscite(valore);
			getPrelieviTotale().setText(testo);
			getPrelieviTotale().setName(Costanti.KEY_LISTENER_PRELIEVO_TOTALE);
			getPrelieviTotale().setIcon(new ImageIcon("resources/images/dettaglio.png"));
			getPrelieviTotale().addMouseListener(getImageMouseListener());
		}
	}

	/**
	 * @param valore
	 * @return
	 */
	private String elaboraPercentualiUscite(Double valore) {
		double percentualeIn = calcolaPercentuale(valore,getTotaleEntrata());
		String percentileIn = getMoneyFormat().format(percentualeIn);
		double percentualeOut = calcolaPercentuale(valore,getTotaleUscita());
		String percentileOut = getMoneyFormat().format(percentualeOut);
		StringBuilder testo = new StringBuilder();
		testo.append(getMoneyFormat().format(valore));
		testo.append(getMoneySymbol());
		testo.append(" ");
		testo.append("(");
		testo.append(Costanti.ENTRATA_KEY);
		testo.append(":");
		testo.append(percentileIn);
		testo.append("%, ");
		testo.append(Costanti.USCITA_KEY);
		testo.append(":");
		testo.append(percentileOut);
		testo.append("% ");
		testo.append(")");
		return testo.toString();
	}
	
	private double calcolaPercentuale(Double valoreIn, Double valoreTot) {
		return (100 * valoreIn) / valoreTot;
	}

	private void resetFormComponents() {
		getDataDa().setText("");
		getDataA().setText("");
		resetConteggi();
		resetDate();
		resetTotali();
	}

	private void resetConteggi() {
		getUscitaNonObbligatoriaTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getUscitaNonObbligatoriaTotale().setIcon(new ImageIcon());
		getUscitaObbligatoriaTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getUscitaObbligatoriaTotale().setIcon(new ImageIcon());
		getExtraTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getExtraTotale().setIcon(new ImageIcon());
		getPrelieviTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getPrelieviTotale().setIcon(new ImageIcon());
		getUscitaTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getUscitaTotale().setIcon(new ImageIcon());
		getEntrataTotale().setText(getMoneyFormat().format(0)+getMoneySymbol());
		getEntrataTotale().setIcon(new ImageIcon());
		
	}
	
	private void resetDate() {
		setDa(null);
		setA(null);
	}
	
	private void resetTotali() {
		setTotaleEntrata(0);
		setTotaleUscita(0);
	}

	public void resetTable() {
		getTransazioniTableModel().flush();
		getTabella().setModel(getTransazioniTableModel());
		getTabellaPanel().setViewportView(getTabella());
	}

	public void resetAllComponents() {
		resetFormComponents();
		resetTable();
	}

	public JTable getTabella() {
		if (tabella == null) {
			getTransazioniTableModel().flush();
			tabella = new JTable(getTransazioniTableModel());
			tabella.setEnabled(false);
			tabella.getColumnModel().getColumn(0).setResizable(false);
			tabella.getColumnModel().getColumn(0).setMaxWidth(76);
			tabella.getColumnModel().getColumn(0).setPreferredWidth(76);
			tabella.getColumnModel().getColumn(3).setResizable(false);
		}
		return tabella;
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

	public TransazioniRiepilogoTableModel getTransazioniTableModel() {
		return transazioniTableModel;
	}

	public void setTransazioniTableModel(
			TransazioniRiepilogoTableModel transazioniTableModel) {
		this.transazioniTableModel = transazioniTableModel;
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

	public JLabel getEntrataTotale() {
		if (entrataTotale == null) {
			entrataTotale = new JLabel(getMoneyFormat().format(0));
		}
		return entrataTotale;
	}

	public JLabel getUscitaTotale() {
		if (uscitaTotale == null) {
			uscitaTotale = new JLabel(getMoneyFormat().format(0));
		}
		return uscitaTotale;
	}
	
	public JLabel getUscitaObbligatoriaTotale() {
		if (uscitaObbligatoriaTotale == null) {
			uscitaObbligatoriaTotale = new JLabel(getMoneyFormat().format(0));
		}
		return uscitaObbligatoriaTotale;
	}

	public JLabel getUscitaNonObbligatoriaTotale() {
		if (uscitaNonObbligatoriaTotale == null) {
			uscitaNonObbligatoriaTotale = new JLabel(getMoneyFormat().format(0));
		}
		return uscitaNonObbligatoriaTotale;
	}

	public JLabel getPrelieviTotale() {
		if (prelieviTotale == null) {
			prelieviTotale = new JLabel(getMoneyFormat().format(0));
		}
		return prelieviTotale;
	}

	public JLabel getExtraTotale() {
		if (extraTotale == null) {
			extraTotale = new JLabel(getMoneyFormat().format(0));
		}
		return extraTotale;
	}
	
	public Hashtable<String, Double> getDatiGrafico() {
		if (datiGrafico == null) {
			datiGrafico = new Hashtable<String, Double>();
		}
		return datiGrafico;
	}

	public String getMoneySymbol() {
		return moneySymbol;
	}

	public void setMoneySymbol(String moneySymbol) {
		this.moneySymbol = moneySymbol;
	}

	public Date getDa() {
		return da;
	}

	public void setDa(Date da) {
		this.da = da;
	}

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public ImageMouseListener getImageMouseListener() {
		return imageMouseListener;
	}

	public void setImageMouseListener(ImageMouseListener imageMouseListener) {
		this.imageMouseListener = imageMouseListener;
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

	public double getTotaleEntrata() {
		return totaleEntrata;
	}

	public void setTotaleEntrata(double totaleEntrata) {
		this.totaleEntrata = totaleEntrata;
	}

	public double getTotaleUscita() {
		return totaleUscita;
	}

	public void setTotaleUscita(double totaleUscita) {
		this.totaleUscita = totaleUscita;
	}

	public double getTotaleExtra() {
		return totaleExtra;
	}

	public void setTotaleExtra(double totaleExtra) {
		this.totaleExtra = totaleExtra;
	}

	public double getTotalePrelievo() {
		return totalePrelievo;
	}

	public void setTotalePrelievo(double totalePrelievo) {
		this.totalePrelievo = totalePrelievo;
	}

	public double getTotaleUscitaNonObbligatoria() {
		return totaleUscitaNonObbligatoria;
	}

	public void setTotaleUscitaNonObbligatoria(double totaleUscitaNonObbligatoria) {
		this.totaleUscitaNonObbligatoria = totaleUscitaNonObbligatoria;
	}

	public double getTotaleUscitaObbligatoria() {
		return totaleUscitaObbligatoria;
	}

	public void setTotaleUscitaObbligatoria(double totaleUscitaObbligatoria) {
		this.totaleUscitaObbligatoria = totaleUscitaObbligatoria;
	}
}
