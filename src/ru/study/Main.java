package ru.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/ru/study/config.txt"))) {
            String configLine;
            // проход по строке конфиг файла
            ArrayList<TemplateClass> templateClasses = new ArrayList<>();
            while ((configLine = reader.readLine()) != null) {
                String[] words = configLine.split(",");
                String className = words[0];
                TemplateClass tc = new TemplateClass(className, Arrays.copyOfRange(words, 1, words.length));
                templateClasses.add(tc);
                System.out.println(tc.toString());
                // sb.append(config).append(System.lineSeparator());
            }
            for (TemplateClass tc:templateClasses) {
                for (int i=0; i<tc.getKeywordTemplates().length; i++) {
                    for (TemplateClass tc1:templateClasses) {
                        if (!tc.getClassName().equals(tc1.getClassName())) {
                            String[] tcArray = tc.getKeywordTemplates();
                            if (Arrays.asList(tc1.getKeywordTemplates()).contains(tcArray[i])) {
                                ArrayList<Integer> tcUniqueIndex = tc.getUniqueIndex();
                                //todo: тут OutOfBoundsException; проверить то ли я вообще удаляю
                                tcUniqueIndex.remove(i);
                                tc.setUniqueIndex(tcUniqueIndex);
                            }
                        }
                    }
                }
                for (Integer i:tc.getUniqueIndex()) {
                    System.out.println("Для класса" + tc.getClassName() + "уникальное ключ-слово:" + tc.getKeywordTemplates()[i]);
                }
                System.out.println(tc.getUniqueIndex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(sb);
    }
}
