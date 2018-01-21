/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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

    private File loadRegFile() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "extra");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir.getAbsoluteFile() + System.getProperty("file.separator") + "extra.properties");
        return file;
    }

    private Properties loadFileProperties() {
        Properties myProp = loadProperties(loadRegFile());
        return myProp;
    }

    public String getProperties(String key) {
        try {
            Properties myProp = this.loadFileProperties();
            String value = myProp.getProperty(key);
            if (value != null) {
                return value;
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void saveProperties(String key, String value) {
        java.io.OutputStream propsFile;
        Properties props = this.loadFileProperties();
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

    public boolean removeFile(String file) {
        return new File(file).delete();
    }

    public void removeDir(String dir) {
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

    public String createTempPath() {
        File dir = new File(System.getProperty("netbeans.user") + System.getProperty("file.separator") + "temp");
        if (!dir.exists()) {
            dir.mkdir();
        }

        return dir.getAbsolutePath();
    }
}
