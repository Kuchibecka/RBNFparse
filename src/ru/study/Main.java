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
        // формируем reader, который будет читать наш файл с конфигурациями классов текстов, указывая путь до файла
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/ru/study/config.txt"))) {
            String configLine;
            // создаём коллекцию классов текстов
            ArrayList<TemplateClass> templateClasses = new ArrayList<>();
            System.out.println("Входные данные классов текстов с ещё неготовыми уникальными ключевыми словами:");
            // считываем строки из текстового файла с классами текстов до конца файла
            while ((configLine = reader.readLine()) != null) {
                // с помощью функции split() разбиваем строку через разделитель - "," на массив слов
                ArrayList<String> words = new ArrayList<>(Arrays.asList(configLine.split(",")));
                // первое слово - название класса текста; записываем его
                String className = words.get(0);
                // и убираем из списка слов
                words.remove(0);
                // с помощью имени класса текста и остальных слов (в качестве ключевых) создаём класс класса текста
                TemplateClass tc = new TemplateClass(className, words);
                // и добавляем его в коллекцию классов текстов
                templateClasses.add(tc);
                // контрольный вывод
                System.out.println(tc);
            }

            // нахождение в классах текстов повторящихся ключевых слов
            ArrayList<String> nonUnique = new ArrayList<>();
            // для каждого класса текста
            for (TemplateClass tc : templateClasses) {
                // и для каждого другого класса текста
                for (TemplateClass tc1 : templateClasses) {
                    // (с отличающимся именем класса текста)
                    if (!tc.getClassName().equals(tc1.getClassName())) {
                        // берём каждое ключевое слово другого класса текста
                        for (String s : tc1.getKeyword()) {
                            // и если оно есть в первом классе текста
                            if (tc.getKeyword().contains(s))
                                // и если его ещё нет в неуникальных слловах
                                if (!nonUnique.contains(s))
                                    // добавляем его
                                    nonUnique.add(s);
                        }
                    }
                }
            }
            System.out.println("\nНеуникальные ключевые слова по всем классам текстов:\n" + nonUnique + "\n");

            // удаление из уникальных ключевых слов каждого класса текста неуникальных слов
            // для каждого ключевого слова в списке неуникальных
            for (String s : nonUnique) {
                // для каждого класса текста
                for (TemplateClass tc : templateClasses) {
                    // инициализируем будущий список уникальных слов (изначально он содержит вообще все ключевые слова)
                    ArrayList<String> tcUnique = tc.getUnique();
                    // и если список уникальных слов содержит неуникальное слово
                    if (tcUnique.contains(s)) {
                        // удаляем его из сформированного списка
                        tcUnique.remove(s);
                        // и сохраняем изменённый список уникальных ключевых слов в нашем классе текста
                        tc.setUnique(tcUnique);
                    }
                }
            }

            // контрольный вывод классов текстов
            System.out.println("Сформированные классы текстов:");
            for (TemplateClass tc : templateClasses)
                System.out.println(tc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(sb);
    }
}
