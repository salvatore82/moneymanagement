package it.moneymanagement.dbmgmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBManagement {

	private Logger logger = LoggerFactory.getLogger(DBManagement.class);
	public static String DB_NAME = "data";
	
	public DBManagement() {
	}
	
	public void backupDatabase(File file) throws IOException {
		String nomeBackup = cloneDatabase(DB_NAME, file);
		logger.info("Backup DB effettuato, creata la directory " + nomeBackup);
	}
	
	public String cloneDatabase(String dbName, File fileOut) throws IOException {
		String nomeFileZippato = fileOut + ".zip";
		File fileIn = new File(dbName);
		if (fileIn.exists()) {
			zipDir(nomeFileZippato,fileIn);
			return nomeFileZippato;
		} else
			return null;
	}

	/*
	 * Utility (workaround) che permette di non creare il file di default del driver di derby (derby.log)
	 * In questo modo il log viene controllato dall'applicazione e non dal driver
	 */
	public static final OutputStream DEV_NULL = new OutputStream() {         
	    public void write(int b) { }     
	};


	public void zipDir(String outFilename, File zipFolder) throws IOException {
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
			int len = zipFolder.getAbsolutePath().lastIndexOf(File.separator);
			String baseName = zipFolder.getAbsolutePath().substring(0,len+1);
			addFolderToZip(zipFolder, out, baseName);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addFolderToZip(File folder, ZipOutputStream zip, String baseName) throws IOException {
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToZip(file, zip, baseName);
			} else {
				String name = file.getAbsolutePath().substring(baseName.length());
				ZipEntry zipEntry = new ZipEntry(name);
				zip.putNextEntry(zipEntry);
				IOUtils.copy(new FileInputStream(file), zip);
				zip.closeEntry();
			}
		}
	}

	
}