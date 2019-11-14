package com.indev.fsklider.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Getter @Setter
public abstract class Node{
//    private String id;
//    protected String jId;
////    protected List<Relation> edgeList;
//    protected Integer repeatCount = 0;
//    protected Context context;
//
//    public abstract String run();
//
//    public void insert(String varName, String value) {
//        getContext().getContextMap().put(varName, value);
//    }
//
//    public static void main(String[] args) {
//        String text = "Тебя зовут @name#\\, ты хочешь @howmuch# @kind# любви?";
//        Pattern pattern = Pattern.compile("@" + "[a-zA-Z]+" + "#");
//        Matcher matcher = pattern.matcher(text);
//        String key = null;
//        HashMap<String, String> keys = new HashMap<>();
//        keys.put("name", "Максим");
//        keys.put("kind", "большой");
//        keys.put("howmuch", "много");
//        String result;
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find()) {
//            key = matcher.group(0).substring(matcher.group(0).indexOf('@') + 1, matcher.group(0).indexOf('#'));
//            matcher.appendReplacement(sb, keys.getOrDefault(key, ""));
//        }
//
//        System.out.println(sb.toString());
//    }
//
//    String replaceVar(String text) {
//        String temp = text.replace("\\", "");
////        System.out.println(temp);
//        Pattern pattern = Pattern.compile("@" + "[a-zA-Z]+" + "#");
//        Matcher matcher = pattern.matcher(temp);
//        String key;
//        StringBuffer sb = new StringBuffer();
//        while (matcher.find()) {
//            key = matcher.group(0).substring(matcher.group(0).indexOf('@') + 1, matcher.group(0).indexOf('#'));
//            System.out.println("Looking for key: " + key);
//            if (getContext().getContextMap().containsKey(key)) {
//                System.out.println("Key contains! This key is: " + key);
//                matcher.appendReplacement(sb, getContext().getContextMap().get(key));
//            } else {
//                matcher.appendReplacement(sb, "");
//            }
//        }
//        System.out.println("Строка: " + sb.toString());
//        matcher.appendTail(sb);
//        return sb.toString().replace(",", "\\,");
//    }
//    String replaceVar(String text) {
//        Pattern pattern = Pattern.compile("@" + "[a-zA-Z]+" + "#");
//        Matcher matcher = pattern.matcher(text);
//        String key;
//        String result = null;
//        while (matcher.find()) {
//            key = matcher.group(0).substring(matcher.group(0).indexOf('@') + 1, matcher.group(0).indexOf('#'));
//            if (getContext().getContextMap().containsKey(key)) {
//                result = text.replaceAll(pattern.toString(), getContext().getContextMap().get(key));
//            } else
//                result = text.replaceAll(pattern.toString(), "");
//        }
//        System.out.println(result);
//        return result;
//    }

}
