/**
 * 
 */
package it.moneymanagement.UI.listeners;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.SwingUtilities;

import org.springframework.context.support.ResourceBundleMessageSource;

import it.moneymanagement.Costanti;
import it.moneymanagement.UI.chart.Diagramma;
import it.moneymanagement.UI.panel.inner.RiepilogoPanel;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.entity.OutcomeEntity;
import it.moneymanagement.business.dao.entity.TransaxionEntity;
import it.moneymanagement.utils.format.MoneyFormat;
import it.moneymanagement.utils.format.WebDateFormat;

/**
 * @author Salvatore
 *
 */
public class ImageMouseListener implements MouseListener {

	private BusinessDelegate businessDelegate;
	private RiepilogoPanel riepilogoPanel;
	private WebDateFormat webDateFormat;
	private MoneyFormat moneyFormat;
	private ResourceBundleMessageSource messageSource;
	private Diagramma diagramma;
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		getRiepilogoPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void mouseExited(MouseEvent e) {
		getRiepilogoPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		String name = e.getComponent().getName();
		if(Costanti.KEY_LISTENER_ENTRATA_TOTALE.equals(name)) {
			IncomeEntity incomeEntity = new IncomeEntity();
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			incomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<IncomeEntity> entrate = getBusinessDelegate().getIncome(incomeEntity, interval);
			aggiornaRiepilogoPanelEntrate(entrate);
		} else if(Costanti.KEY_LISTENER_USCITA_TOTALE.equals(name)) {
			OutcomeEntity outcomeEntity = new OutcomeEntity();
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			outcomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<OutcomeEntity> uscite = getBusinessDelegate().getOutcomeWithoutWithdrawal(outcomeEntity, interval);
			aggiornaRiepilogoPanelUscite(uscite);
			
			//Mostra il grafico
			getRiepilogoPanel().getDatiGrafico().clear();
			String labelUscite = getMessageSource().getMessage("menubar.uscite", null, "Uscite" , Locale.getDefault());
			String labelResiduo = getMessageSource().getMessage("frame.label.residuo", null, "Residuo", Locale.getDefault());
			String labelEntrate = getMessageSource().getMessage("menubar.entrate", null, "Entrate", Locale.getDefault());
			getRiepilogoPanel().getDatiGrafico().put(labelUscite, getRiepilogoPanel().getTotaleUscita());
			getRiepilogoPanel().getDatiGrafico().put(labelResiduo, getRiepilogoPanel().getTotaleEntrata() - getRiepilogoPanel().getTotaleUscita());
			String titoloDiagramma = labelUscite + "/" + labelEntrate;
			Diagramma diagramma = getDiagramma();
	        diagramma.settaDiagramma(getMessageSource().getMessage("frame.title.grafico", null, "Grafico" , Locale.getDefault()), titoloDiagramma, getRiepilogoPanel().getDatiGrafico());
			diagramma.pack();
	        //RefineryUtilities.centerFrameOnScreen(diagramma);
	        diagramma.setVisible(true);
		} else if(Costanti.KEY_LISTENER_USCITA_NON_OBB_TOTALE.equals(name)) {
			OutcomeEntity outcomeEntity = new OutcomeEntity();
			outcomeEntity.setMandatory(Short.valueOf("0"));
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			outcomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<OutcomeEntity> uscite = getBusinessDelegate().getOutcomeWithoutWithdrawal(outcomeEntity, interval);
			aggiornaRiepilogoPanelUscite(uscite);
			
			//Mostra il grafico
			getRiepilogoPanel().getDatiGrafico().clear();
			String labelUsciteNonObbligatorie = getMessageSource().getMessage("riepilogo.usciteNonObb", null, "Uscite non obbligatorie", Locale.getDefault());
			String labelUsciteObbligatorie = getMessageSource().getMessage("riepilogo.usciteObb", null, "Uscite obbligatorie" , Locale.getDefault());
			getRiepilogoPanel().getDatiGrafico().put(labelUsciteNonObbligatorie, getRiepilogoPanel().getTotaleUscitaNonObbligatoria());
			getRiepilogoPanel().getDatiGrafico().put(labelUsciteObbligatorie, getRiepilogoPanel().getTotaleUscita() - getRiepilogoPanel().getTotaleUscitaNonObbligatoria());
			String titoloDiagramma = labelUsciteNonObbligatorie + "/" + labelUsciteObbligatorie;
			Diagramma diagramma = getDiagramma();
	        diagramma.settaDiagramma(getMessageSource().getMessage("frame.title.grafico", null, "Grafico" , Locale.getDefault()), titoloDiagramma, getRiepilogoPanel().getDatiGrafico());
			diagramma.pack();
	        //RefineryUtilities.centerFrameOnScreen(diagramma);
	        diagramma.setVisible(true);
		} else if(Costanti.KEY_LISTENER_USCITA_OBB_TOTALE.equals(name)) {
			OutcomeEntity outcomeEntity = new OutcomeEntity();
			outcomeEntity.setMandatory(Short.valueOf("1"));
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			outcomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<OutcomeEntity> uscite = getBusinessDelegate().getOutcome(outcomeEntity, interval);
			aggiornaRiepilogoPanelUscite(uscite);
			
			//Mostra il grafico
			getRiepilogoPanel().getDatiGrafico().clear();
			String labelUsciteObbligatorie = getMessageSource().getMessage("riepilogo.usciteObb", null, "Uscite obbligatorie" , Locale.getDefault());
			String labelUsciteNonObbligatorie = getMessageSource().getMessage("riepilogo.usciteNonObb", null, "Uscite non obbligatorie", Locale.getDefault());
			getRiepilogoPanel().getDatiGrafico().put(labelUsciteObbligatorie, getRiepilogoPanel().getTotaleUscitaObbligatoria());
			getRiepilogoPanel().getDatiGrafico().put(labelUsciteNonObbligatorie, getRiepilogoPanel().getTotaleUscita() - getRiepilogoPanel().getTotaleUscitaObbligatoria());
			String titoloDiagramma = labelUsciteObbligatorie + "/" + labelUsciteNonObbligatorie;
			Diagramma diagramma = getDiagramma();
	        diagramma.settaDiagramma(getMessageSource().getMessage("frame.title.grafico", null, "Grafico" , Locale.getDefault()), titoloDiagramma, getRiepilogoPanel().getDatiGrafico());
			diagramma.pack();
	        //RefineryUtilities.centerFrameOnScreen(diagramma);
	        diagramma.setVisible(true);
		} else if(Costanti.KEY_LISTENER_EXTRA_TOTALE.equals(name)) {
			OutcomeEntity outcomeEntity = new OutcomeEntity();
			outcomeEntity.setExtra(Short.valueOf("1"));
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			outcomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<OutcomeEntity> uscite = getBusinessDelegate().getOutcome(outcomeEntity, interval);
			aggiornaRiepilogoPanelUscite(uscite);
			
			//Mostra il grafico
			getRiepilogoPanel().getDatiGrafico().clear();
			String labelExtra = getMessageSource().getMessage("riepilogo.extra", null, "Extra", Locale.getDefault());
			String labelUscite = getMessageSource().getMessage("menubar.uscite", null, "Uscite" , Locale.getDefault());
			getRiepilogoPanel().getDatiGrafico().put(labelExtra, getRiepilogoPanel().getTotaleExtra());
			getRiepilogoPanel().getDatiGrafico().put(labelUscite, getRiepilogoPanel().getTotaleUscita() - getRiepilogoPanel().getTotaleExtra());
			String titoloDiagramma = labelExtra + "/" + labelUscite;
			Diagramma diagramma = getDiagramma();
	        diagramma.settaDiagramma(getMessageSource().getMessage("frame.title.grafico", null, "Grafico" , Locale.getDefault()), titoloDiagramma, getRiepilogoPanel().getDatiGrafico());
			diagramma.pack();
	        //RefineryUtilities.centerFrameOnScreen(diagramma);
	        diagramma.setVisible(true);
		} else if(Costanti.KEY_LISTENER_PRELIEVO_TOTALE.equals(name)) {
			OutcomeEntity outcomeEntity = new OutcomeEntity();
			TransaxionEntity transaxionEntity = new TransaxionEntity();
			Date[] interval = new Date[2];
			outcomeEntity.setTransaxion(transaxionEntity);
			interval[0] = getRiepilogoPanel().getDa();
			interval[1] = getRiepilogoPanel().getA();
			List<OutcomeEntity> uscite = getBusinessDelegate().getOutcomeWithdrawal(outcomeEntity, interval);
			aggiornaRiepilogoPanelUscite(uscite);
			
			//Mostra il grafico
			getRiepilogoPanel().getDatiGrafico().clear();
			String labelPrelievi = getMessageSource().getMessage("riepilogo.prelievi", null, "Prelievo", Locale.getDefault());
			String labelUscite = getMessageSource().getMessage("menubar.uscite", null, "Uscite" , Locale.getDefault());
			getRiepilogoPanel().getDatiGrafico().put(labelPrelievi, getRiepilogoPanel().getTotalePrelievo());
			getRiepilogoPanel().getDatiGrafico().put(labelUscite, getRiepilogoPanel().getTotaleUscita() - getRiepilogoPanel().getTotalePrelievo());
			String titoloDiagramma = labelPrelievi + "/" + labelUscite;
			Diagramma diagramma = getDiagramma();
	        diagramma.settaDiagramma(getMessageSource().getMessage("frame.title.grafico", null, "Grafico" , Locale.getDefault()), titoloDiagramma, getRiepilogoPanel().getDatiGrafico());
			diagramma.pack();
	        //RefineryUtilities.centerFrameOnScreen(diagramma);
	        diagramma.setVisible(true);
		}
		
		SwingUtilities.updateComponentTreeUI(getRiepilogoPanel());
	}

	private void aggiornaRiepilogoPanelUscite(List<OutcomeEntity> uscite) {
		getRiepilogoPanel().resetTable();
		for (OutcomeEntity uscita : uscite) {
			getRiepilogoPanel().getTransazioniTableModel().addRow(new Object[] { 
					getWebDateFormat().format(uscita.getTransaxion().getDate()),
					uscita.getTransaxion().getTransaxionType().getDescription(),
					uscita.getTransaxion().getDescription(),
					getMoneyFormat().format(uscita.getTransaxion().getValue()) });
		}
		
		getRiepilogoPanel().getTabella().setModel(getRiepilogoPanel().getTransazioniTableModel());
		getRiepilogoPanel().getTabellaPanel().setViewportView(getRiepilogoPanel().getTabella());
	}

	private void aggiornaRiepilogoPanelEntrate(List<IncomeEntity> entrate) {
		getRiepilogoPanel().resetTable();
		for (IncomeEntity entrata : entrate) {
			getRiepilogoPanel().getTransazioniTableModel().addRow(new Object[] { 
					getWebDateFormat().format(entrata.getTransaxion().getDate()),
					entrata.getTransaxion().getTransaxionType().getDescription(),
					entrata.getTransaxion().getDescription(),
					getMoneyFormat().format(entrata.getTransaxion().getValue()) });
		}
		
		getRiepilogoPanel().getTabella().setModel(getRiepilogoPanel().getTransazioniTableModel());
		getRiepilogoPanel().getTabellaPanel().setViewportView(getRiepilogoPanel().getTabella());
	}

	public RiepilogoPanel getRiepilogoPanel() {
		return riepilogoPanel;
	}

	public void setRiepilogoPanel(RiepilogoPanel riepilogoPanel) {
		this.riepilogoPanel = riepilogoPanel;
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

	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Diagramma getDiagramma() {
		return diagramma;
	}

	public void setDiagramma(Diagramma diagramma) {
		this.diagramma = diagramma;
	}

}
