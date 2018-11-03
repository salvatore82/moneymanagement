/**
 * Implementazione dell'interfaccia per la facade che fa da broker per tutte le interrogazioni al database.
 */
package it.moneymanagement.business;

import java.util.Date;
import java.util.List;

import it.moneymanagement.Costanti;
import it.moneymanagement.business.dao.IncomeDao;
import it.moneymanagement.business.dao.OutcomeDao;
import it.moneymanagement.business.dao.TransaxionDao;
import it.moneymanagement.business.dao.TransaxionSubtypeDao;
import it.moneymanagement.business.dao.TransaxionTypeDao;
import it.moneymanagement.business.dao.entity.IncomeEntity;
import it.moneymanagement.business.dao.entity.OutcomeEntity;
import it.moneymanagement.business.dao.entity.TransaxionEntity;
import it.moneymanagement.business.dao.entity.TransaxionSubtypeEntity;
import it.moneymanagement.business.dao.entity.TransaxionTypeEntity;

/**
 * @author s.deluca
 * 
 */
public class BusinessDelegate {

	private IncomeDao incomeDao;
	private OutcomeDao outcomeDao;
	private TransaxionDao transaxionDao;
	private TransaxionSubtypeDao transaxionSubtypeDao;
	private TransaxionTypeDao transaxionTypeDao;

	public void insertTransaxion(TransaxionEntity transaxionEntity) {
		getTransaxionDao().insert(transaxionEntity);
	}
	
	public List<IncomeEntity> getIncome(IncomeEntity incomeEntity,
			Date[] interval) {
		return getIncomeDao().findByIntervalDate(incomeEntity,interval,null);
	}
	
	public IncomeEntity getIncomeByID(IncomeEntity incomeEntity) {
		return getIncomeDao().findByID(incomeEntity, incomeEntity.getId());
	}

	public void insertIncome(IncomeEntity incomeEntity) {
		getIncomeDao().insert(incomeEntity);
	}

	public void deleteIncome(IncomeEntity incomeEntity) {
		incomeEntity = getIncomeDao().findByID(incomeEntity, incomeEntity.getId());
		getIncomeDao().delete(incomeEntity);
		getTransaxionDao().delete(incomeEntity.getTransaxion());
	}

	public List<OutcomeEntity> getOutcome(OutcomeEntity outcomeEntity,
			Date[] interval) {
		return getOutcomeDao().findByIntervalDate(outcomeEntity,interval,null);
	}
	
	public OutcomeEntity getOutcomeByID(OutcomeEntity outcomeEntity) {
		return getOutcomeDao().findByID(outcomeEntity, outcomeEntity.getId());
	}

	public List<OutcomeEntity> getOutcomeWithoutWithdrawal(OutcomeEntity outcomeEntity, Date[] interval) {
		TransaxionTypeEntity transaxionTypeEntity = getTransaxionTypeDao().findByType(new TransaxionTypeEntity(null,Costanti.PRELIEVO));
		return getOutcomeDao().findByIntervalDate(outcomeEntity, interval, transaxionTypeEntity.getId());
	}
	
	public List<OutcomeEntity> getOutcomeWithdrawal(
			OutcomeEntity outcomeEntity, Date[] interval) {
		TransaxionTypeEntity transaxionTypeEntity = getTransaxionTypeDao().findByType(new TransaxionTypeEntity(null,Costanti.PRELIEVO));
		TransaxionEntity transaxionEntity = new TransaxionEntity();
		transaxionEntity.setTransaxionType(transaxionTypeEntity);
		outcomeEntity.setTransaxion(transaxionEntity);
		return getOutcomeDao().findByIntervalDate(outcomeEntity,interval,null);
	}

	public void insertOutcome(OutcomeEntity outcomeEntity) {
		getOutcomeDao().insert(outcomeEntity);
	}
	
	public void updateTransaxion(TransaxionEntity transaxionEntity) {
		getTransaxionDao().update(transaxionEntity);
	}

	public void deleteOutcome(OutcomeEntity outcomeEntity) {
		outcomeEntity = getOutcomeDao().findByID(outcomeEntity, outcomeEntity.getId());
		getOutcomeDao().delete(outcomeEntity);
		getTransaxionDao().delete(outcomeEntity.getTransaxion());
	}

	public TransaxionTypeEntity getTransaxionType(TransaxionTypeEntity transaxionTypeEntity) {
		return getTransaxionTypeDao().findByType(transaxionTypeEntity);
	}
	
	public List<TransaxionTypeEntity> getTransaxionTypeBySubtypeId(TransaxionSubtypeEntity transaxionSubtypeEntity) {
		return getTransaxionTypeDao().findBySubtypeId(transaxionSubtypeEntity);
	}

	public void insertTransaxionType(TransaxionTypeEntity transaxionTypeEntity) {
		getTransaxionTypeDao().insert(transaxionTypeEntity);
	}
	
	public Double calculateOutcomeWithoutWithdrawal(OutcomeEntity outcomeEntity, Date[] interval) {
		TransaxionTypeEntity transaxionTypeEntity = getTransaxionTypeDao().findByType(new TransaxionTypeEntity(null,Costanti.PRELIEVO));
		return getOutcomeDao().calculate(outcomeEntity, interval, transaxionTypeEntity.getId());
	}
	
	public Double calculateOutcomeWithdrawal(OutcomeEntity outcomeEntity, Date[] interval) {
		TransaxionTypeEntity transaxionTypeEntity = getTransaxionTypeDao().findByType(new TransaxionTypeEntity(null,Costanti.PRELIEVO));
		TransaxionEntity transaxionEntity = new TransaxionEntity();
		transaxionEntity.setTransaxionType(transaxionTypeEntity);
		outcomeEntity.setTransaxion(transaxionEntity);
		return getOutcomeDao().calculate(outcomeEntity, interval, null);
	}
	
	public Double calculateIncome(IncomeEntity incomeEntity, Date[] interval) {
		return getIncomeDao().calculate(incomeEntity, interval, null);
	}
	
	public IncomeDao getIncomeDao() {
		return incomeDao;
	}

	public void setIncomeDao(IncomeDao incomeDao) {
		this.incomeDao = incomeDao;
	}

	public OutcomeDao getOutcomeDao() {
		return outcomeDao;
	}

	public void setOutcomeDao(OutcomeDao outcomeDao) {
		this.outcomeDao = outcomeDao;
	}

	public TransaxionDao getTransaxionDao() {
		return transaxionDao;
	}

	public void setTransaxionDao(TransaxionDao transaxionDao) {
		this.transaxionDao = transaxionDao;
	}

	public TransaxionSubtypeDao getTransaxionSubtypeDao() {
		return transaxionSubtypeDao;
	}

	public void setTransaxionSubtypeDao(TransaxionSubtypeDao transaxionSubtypeDao) {
		this.transaxionSubtypeDao = transaxionSubtypeDao;
	}

	public TransaxionTypeDao getTransaxionTypeDao() {
		return transaxionTypeDao;
	}

	public void setTransaxionTypeDao(TransaxionTypeDao transaxionTypeDao) {
		this.transaxionTypeDao = transaxionTypeDao;
	}

}
