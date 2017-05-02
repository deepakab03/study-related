package com.deepak.questions.gedcom.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenealogyDataExtractorUtils {

    private static final Logger logger = Logger.getLogger(GenealogyDataExtractorUtils.class.getName());

    public List<String> readClassPathFile(String classPathFile) throws IOException {
        logger.info("Loading file from classpath: " + classPathFile);
        // now I appreciate Apache's IOUtils...
        URL resource = getClass().getResource(classPathFile);
        if (resource == null) {
            throw new IllegalArgumentException(String.format(
                    "classPathFile '%s' either does not exist or probably should have a leading '/'",
                    classPathFile));
        }
        return readDataFromFile(resource.getFile());
    }

    private List<String> readDataFromFile(String filePath) throws FileNotFoundException, IOException {
        List<String> dataList = new ArrayList<String>(100);
        FileReader gedcomDataFileReader = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(gedcomDataFileReader);
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Line read: " + line);
                }
                dataList.add(line);
            }
        } finally {
            gedcomDataFileReader.close();
            reader.close();
        }
        return dataList;
    }

    public List<String> readFileSystemFile(String path) throws IOException {
        logger.info("Reading file contents from file-system: " + path);
        return readDataFromFile(path);
    }

    public File writeFileSystemFile(String path, String data) throws IOException {
        final File file = new File(path);
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        try {
            String[] lines = data.split("\n");
            for (String line : lines) {
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            bufferedWriter.close();
            writer.close();
        }
        logger.info("Wrote generated output file at: " + file.getAbsolutePath());
        return file;
    }
}
