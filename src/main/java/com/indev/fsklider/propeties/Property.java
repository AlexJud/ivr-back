package com.indev.fsklider.propeties;

import java.util.ArrayList;

public class Property {

    private static ArrayList<String> estate = new ArrayList<>();

    public Property(){
        estate.add("Новогиреевский");
        estate.add("Балашиха");
        estate.add("Сколсковский");
        estate.add("Одинцово");
        estate.add("Римский");
        estate.add("Домодедовская");
        estate.add("Дыхание");
        estate.add("Мытищи");
        estate.add("Бибирево");
        estate.add("Раменском");
        estate.add("Раменский");
        estate.add("Фабричная");
        estate.add("Мытищах");
        estate.add("Скандинавский");
        estate.add("Медведково");
    }

    public static ArrayList<String> getEstate() {
        return estate;
    }

    public static void setEstate(ArrayList<String> estate) {
        Property.estate = estate;
    }
}
