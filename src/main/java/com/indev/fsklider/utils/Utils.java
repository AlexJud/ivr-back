package com.indev.fsklider.utils;

import com.indev.fsklider.models.Edge;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class Utils {

    public static String replaceVar(String srcText, Map<String, Edge> map) {
        while (srcText.indexOf('@') > -1 && srcText.indexOf('#') > -1) {
            String var = srcText.substring(srcText.indexOf('@') + 1, srcText.indexOf('#'));
            String answer = map.get(var) == null ? "" : map.get(var).getMatchWord();
            srcText = srcText.substring(0, srcText.indexOf('@')) + answer + srcText.substring(srcText.indexOf('#') + 1);
        }
        return srcText;
    }

    public static String getMessage(String regonizedXML) {
        String result = null;
        if (regonizedXML != null && !regonizedXML.equals("")) {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(regonizedXML));
                Document document = builder.parse(is);
                Element root = document.getDocumentElement();
                NodeList childNodes = root.getChildNodes().item(0).getChildNodes();
                result = childNodes.item(0).getTextContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @NotNull
    public static String removeBackslash(String text) {
        StringBuilder sb = new StringBuilder(text);
        int index = -1;
        while ((index = sb.indexOf("\\")) != -1) {
            sb.deleteCharAt(index);
        }
        return sb.toString();
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

    public static String wordToInt(String numbers) {
        HashMap<String, Integer> mapNumbers = new HashMap<>();
        mapNumbers.put("ноль", 0);
        mapNumbers.put("один", 1);
        mapNumbers.put("два", 2);
        mapNumbers.put("три", 3);
        mapNumbers.put("четыре", 4);
        mapNumbers.put("пять", 5);
        mapNumbers.put("шесть", 6);
        mapNumbers.put("семь", 7);
        mapNumbers.put("восемь", 8);
        mapNumbers.put("девять", 9);
        mapNumbers.put("десять", 10);
        mapNumbers.put("одиннадцать", 11);
        mapNumbers.put("двенадцать", 12);
        mapNumbers.put("тринадцать", 13);
        mapNumbers.put("четырнадцать", 14);
        mapNumbers.put("пятнадцать", 15);
        mapNumbers.put("шестнадцать", 16);
        mapNumbers.put("семнадцать", 17);
        mapNumbers.put("восемнадцать", 18);
        mapNumbers.put("девятнадцать", 19);
        mapNumbers.put("двадцать", 20);
        mapNumbers.put("тридцать", 30);
        mapNumbers.put("сорок", 40);
        mapNumbers.put("пятьдесят", 50);
        mapNumbers.put("шестьдесят", 60);
        mapNumbers.put("семьдесят", 70);
        mapNumbers.put("восемьдесят", 80);
        mapNumbers.put("девяносто", 90);
        mapNumbers.put("сто", 100);
        mapNumbers.put("двести", 200);
        mapNumbers.put("триста", 300);
        mapNumbers.put("четыреста", 400);
        mapNumbers.put("пятьсот", 500);
        mapNumbers.put("шестьсот", 600);
        mapNumbers.put("семьсот", 700);
        mapNumbers.put("восемьсот", 800);
        mapNumbers.put("девятьсот", 900);

        String[] nArr = numbers.split(" ");
        StringBuffer builder = new StringBuffer();
        for (String number : nArr) {
            builder.append(mapNumbers.get(number));
        }
        return builder.toString();
    }
}
