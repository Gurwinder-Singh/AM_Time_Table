/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.mac.common.messages.companyInfoClass;

/**
 *
 * @author Gurwinder Singh
 */
public class FileUtility {

    public static FileUtility instance = null;

    public static FileUtility getDefault() {
        if (instance == null) {
            instance = new FileUtility();
        }
        return instance;
    }

    public Properties loadProperties(File file) {
        java.io.InputStream propsFile;
        Properties tempProp = new Properties();
        try {
            if (file.exists()) {
                propsFile = new FileInputStream(file);
                tempProp.load(propsFile);
                propsFile.close();
            }
        } catch (IOException ioe) {
            System.out.println("I/O Exception.");
            ioe.printStackTrace();

        }
        return tempProp;
    }

    public void saveProperties(File f, String value, Properties p, String key) {
        java.io.OutputStream propsFile;
        try {
            propsFile = new FileOutputStream(f);
            p.setProperty(key, value);
            p.save(propsFile, "");
            propsFile.close();
        } catch (IOException ioe) {
            System.out.println("I/O Exception.");
            ioe.printStackTrace();
        }

    }

    public File loadFile(File dir, String fileName) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + fileName);
        return file;
    }

    private File loadTableSetingsFile() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "TableSetting");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + "TableBundle.properties");
        return file;
    }

    private File loadRegFile() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "extra");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + "extra.properties");
        return file;
    }

    private Properties loadRegFileProperties() {
        Properties myProp = loadProperties(loadRegFile());
        return myProp;
    }

    public String getRegProperties(String key) {
        try {
            Properties myProp = this.loadRegFileProperties();
            String value = myProp.getProperty(key);
            if (value != null) {
                return value;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void saveRegProperties(String key, String value) {
        java.io.OutputStream propsFile;
        Properties props = this.loadRegFileProperties();
        try {
            propsFile = new FileOutputStream(loadRegFile());
            props.setProperty(key, value);
            props.save(propsFile, "");
            propsFile.close();
        } catch (IOException ioe) {
            System.out.println("I/O Exception.");
            ioe.printStackTrace();
        }
    }

    private File loadCompanyInfoFile() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "info");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + "info.properties");
        return file;
    }

    public void saveCompanyInfo(companyInfoClass data) {
        try {

            FileOutputStream fileOut =
                    new FileOutputStream(loadCompanyInfoFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public companyInfoClass getCompanyInfo() {
        companyInfoClass data = null;
        try {
            FileInputStream fileIn = new FileInputStream(loadCompanyInfoFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (companyInfoClass) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return data;
    }

    private File loadPassFile() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "key");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + "key.properties");
        return file;
    }

    private Properties loadPassProperties() {
        Properties myProp = loadProperties(loadPassFile());
        return myProp;
    }

    public String getPassProperties(String key) {
        try {
            Properties myProp = this.loadPassProperties();
            String value = myProp.getProperty(key);
            if (value != null) {
                return value;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void savePassProperties(String key, String value) {
        java.io.OutputStream propsFile;
        Properties props = this.loadPassProperties();
        try {
            propsFile = new FileOutputStream(loadPassFile());
            props.setProperty(key, value);
            props.save(propsFile, "");
            propsFile.close();
        } catch (IOException ioe) {
            System.out.println("I/O Exception.");
            ioe.printStackTrace();
        }
    }

    public boolean removeFile(String file) {
        return new File(file).delete();
    }

    public void removeDir(String dir){
        removeDir(new File(dir));
    }
    
    public void removeDir(File dir) {
        if (dir.isFile()) {
            dir.delete();
        }
        if (dir.isDirectory()) {
            String[] files = dir.list();
            for (String s : files) {
                File currentFile = new File(dir.getPath(), s);

                if (currentFile.isDirectory()) {
                    removeDir(currentFile);
                }
                if (currentFile.isFile()) {
                    currentFile.delete();
                }
            }
            dir.delete();
        }
    }
    
    public String createTempPath(){
      File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "temp");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        return dir.getAbsolutePath();
    }
}
