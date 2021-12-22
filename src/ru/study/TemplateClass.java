package ru.study;

import java.util.ArrayList;
import java.util.Arrays;

public class TemplateClass {
    private String className;
    private String[] keywordTemplates;
    private ArrayList<Integer> uniqueIndex;

    public TemplateClass(String className, String[] keywordTemplates) {
        this.className = className;
        this.keywordTemplates = keywordTemplates;
        this.uniqueIndex = new ArrayList<>();
        for (int i = 0; i < keywordTemplates.length; i++)
            this.uniqueIndex.add(i);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getKeywordTemplates() {
        return keywordTemplates;
    }

    public void setKeywordTemplates(String[] keywordTemplates) {
        this.keywordTemplates = keywordTemplates;
    }

    public ArrayList<Integer> getUniqueIndex() {
        return uniqueIndex;
    }

    public void setUniqueIndex(ArrayList<Integer> uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
    }

    @Override
    public String toString() {
        return "TemplateClass{" +
                "className='" + className + '\'' +
                ", keywordTemplates=" + Arrays.toString(keywordTemplates) +
                ", uniqueIndex=" + uniqueIndex +
                '}';
    }
}
