package com.deepak.questions.gedcom.util;

import java.io.IOException;
import java.util.List;

public class TestUtils {

    public static String readDataFromTestFile(String classPathFile) throws IOException {
        final List<String> xmlDataList = new GenealogyDataExtractorUtils().readClassPathFile(classPathFile);
        StringBuilder xmlSb = new StringBuilder(100);
        for (String line : xmlDataList) {
            xmlSb.append(line);
        }
        return xmlSb.toString();
    }
}
