package com.indev.fsklider.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

public class Utils {
    public static String getMessage(String regonizedXML) {
        String result = null;
        if (regonizedXML != null && !regonizedXML.equals("")) {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(regonizedXML));
                System.out.println(regonizedXML);
                Document document = builder.parse(is);
                Element root = document.getDocumentElement();
                NodeList childNodes = root.getChildNodes().item(0).getChildNodes();
                System.out.println(childNodes.item(0).getTextContent());
                result = childNodes.item(0).getTextContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static String getProperties(String name) {
        Properties props = null;
        String result = null;
        try {
            FileInputStream pFile = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/application.properties");
//            FileInputStream pFile = new FileInputStream(System.getProperty("user.dir") + "/application.properties");
            props = new Properties();
            props.load(pFile);
            result = (String) props.get(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Property " + name + " doesn't exist");
        }
        return result;
    }

    public static String getName(String recognition, String filename) {
        ArrayList<String> strings = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/main/resources/" + filename));
            while (in.ready()) {
                String name = in.readLine();
                if (recognition.contains(name)) {
                    strings.add(name);
                    strings.sort(Comparator.comparingInt(s -> Math.abs(s.length() - "intelligent".length())));
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings.get(0);
    }
}
