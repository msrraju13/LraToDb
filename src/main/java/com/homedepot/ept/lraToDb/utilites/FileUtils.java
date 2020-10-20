package com.homedepot.ept.lraToDb.utilites;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    /**
     *
     * @param fName full path to file
     * @return Document==content of file
     * @throws IOException
     */
    public static Document ReadFile(String fName) throws IOException {
        File input = new File(fName);
        Document document = Jsoup.parse(input, "UTF-8", "http://example.com/");
        return document;
    }
}
