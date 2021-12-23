package ru.study;

import java.util.ArrayList;

public class TemplateClass {
    private String className;
    private ArrayList<String> keyword;
    private ArrayList<String> unique = new ArrayList<>();

    public TemplateClass(String className, ArrayList<String> keyword) {
        this.className = className;
        this.keyword = keyword;
        this.unique.addAll(keyword);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(ArrayList<String> keyword) {
        this.keyword = keyword;
    }

    public ArrayList<String> getUnique() {
        return unique;
    }

    public void setUnique(ArrayList<String> unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "Класс текста: {" +
                "имя класса='" + className + '\'' +
                ", ключевые слова=" + keyword.toString() +
                ", уникальные ключевые слова=" + unique.toString() +
                '}';
    }
}
