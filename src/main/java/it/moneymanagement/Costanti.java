/**
 * Costanti di utilizzo comune ai tre livelli dell'applicazione
 */
package it.moneymanagement;

/**
 * @author s.deluca
 *
 */
public class Costanti {
	
	/*
	 * Costanti (label) che mappano una precisa tabella di configurazione del database, sulla quale si appoggia e si basa l'intero sistema
	 * in base a quanto riportato sulla documentazione del progetto
	 */
	public final static char ENTRATA_KEY = 'I';
	public final static char USCITA_KEY = 'O';
	public static final String USCITA_LABEL = "USCITA";
	public static final String ENTRATA_LABEL = "ENTRATA";
	
	/*
	 * Costanti varie per l'applicazione
	 */
	public final static String PRELIEVO = "PRELIEVO";
	public final static String PATTERN_DESCRIZIONE_TESTO = "([a-zA-Z_0-9 ]+)";
	public final static int NUMERO_MASSIMO_BACKUP_DB = 3;
	
	/*
	 * Costanti per la formattazione delle date
	 */
	public final static String FORMATO_DATA_WEB_PREDEFINITO = "dd/MM/yyyy";
	public final static String FORMATO_DATA_BKP_PREDEFINITO = "dd-MM-yyyy_HH-mm-ss";
	
	/*
	 * Costanti per la formattazione dei soldi
	 */
	public final static String FORMATO_SOLDI_PREDEFINITO = "#0.00";
	
	/*
	 * Costanti per i listener degli eventi della UI
	 */
	public final static String KEY_LISTENER_ENTRATA_TOTALE = "KL_ENTRATA_TOTALE";
	public final static String KEY_LISTENER_USCITA_TOTALE = "KL_USCITA_TOTALE";
	public final static String KEY_LISTENER_USCITA_OBB_TOTALE = "KL_USCITA_OBB_TOTALE";
	public final static String KEY_LISTENER_USCITA_NON_OBB_TOTALE = "KL_USCITA_NON_OBB_TOTALE";
	public final static String KEY_LISTENER_PRELIEVO_TOTALE = "KL_PRELIEVO_TOTALE";
	public final static String KEY_LISTENER_EXTRA_TOTALE = "KL_EXTRA_TOTALE";
	public final static String KEY_LISTENER_GRAFICO_EXTRA_TOTALE = "KL_GRAFICO_EXTRA_TOTALE";
}
