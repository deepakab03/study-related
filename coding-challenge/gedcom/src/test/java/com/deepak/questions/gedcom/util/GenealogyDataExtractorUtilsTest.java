package com.deepak.questions.gedcom.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class GenealogyDataExtractorUtilsTest {

    private GenealogyDataExtractorUtils gedcomUtils = new GenealogyDataExtractorUtils();

    @Test
    public void givenClasspathFile_whenFileIsRead_shouldReadItsContentsCorrectly() throws IOException {
        String classPathFile = "/com/deepak/questions/gedcom/util/classPathFile.txt";

        List<String> classPathFileContents = gedcomUtils.readClassPathFile(classPathFile);

        assertThat(classPathFileContents, is(notNullValue()));
        assertThat(classPathFileContents, hasItem("my name is"));
        assertThat(classPathFileContents, hasItem("Deepak"));
        assertThat(classPathFileContents, hasItem("Hello!"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenClasspathFileWithouALeadingSlash_whenFileIsRead_shouldThrowIllegalArgException()
            throws IOException {
        String classPathFile = "com/deepak/questions/gedcom/util/classPathFile.txt";

        gedcomUtils.readClassPathFile(classPathFile);

    }
}
