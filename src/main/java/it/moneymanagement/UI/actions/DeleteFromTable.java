/**
 * 
 */
package it.moneymanagement.UI.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.moneymanagement.Costanti;
import it.moneymanagement.UI.panel.inner.CercaUscitaPanel;
import it.moneymanagement.business.BusinessDelegate;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.entity.OutcomeEntity;

/**
 * @author Salvatore
 *
 */
public class DeleteFromTable implements Action {

	private Logger logger = LoggerFactory.getLogger(DeleteFromTable.class);
	private BusinessDelegate businessDelegate;
	private CercaUscitaPanel cercaUscitaPanel;
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	}

	public Object getValue(String key) {
		return null;
	}

	public boolean isEnabled() {
		return false;
	}

	public void putValue(String key, Object value) {

	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {

	}

	public void setEnabled(boolean b) {
	}

	public void actionPerformed(ActionEvent e) {
        JTable table = (JTable)e.getSource();
        int modelRow = Integer.valueOf( e.getActionCommand() );
        Integer id = (Integer) ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0);
        Character tipo = (Character) ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 1);
        logger.debug("Intercettata transazione da cancellare id: " + id) ;
        logger.debug("Intercettata transazione da cancellare tipo: " + tipo) ;
        
        int selected = JOptionPane.showConfirmDialog(getCercaUscitaPanel(), "Sicuro di voler cancellare?", "Cancella elemento", JOptionPane.OK_CANCEL_OPTION);
        
        logger.debug("Opzione scelta: " + selected);
        if(selected == 0) {
			if (Costanti.USCITA_KEY == tipo.charValue()) {
				OutcomeEntity outcomeEntity = new OutcomeEntity();
				outcomeEntity.setId(id);
				logger.debug("Cancellazione tansazione uscita id: " + outcomeEntity.getId()) ;
				getBusinessDelegate().deleteOutcome(outcomeEntity);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			} else if (Costanti.ENTRATA_KEY == tipo.charValue()) {
				IncomeEntity incomeEntity = new IncomeEntity();
				incomeEntity.setId(id);
				logger.debug("Cancellazione tansazione entrata id: " + incomeEntity.getId()) ;
				getBusinessDelegate().deleteIncome(incomeEntity);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
        }
	}

	public CercaUscitaPanel getCercaUscitaPanel() {
		return cercaUscitaPanel;
	}

	public void setCercaUscitaPanel(CercaUscitaPanel cercaUscitaPanel) {
		this.cercaUscitaPanel = cercaUscitaPanel;
	}

	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(BusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

}
