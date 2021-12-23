package ru.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
public class Main {
    public static void main(String[] args) {
        // относительный путь к файлу с описанием классов текстов
        String configPath = "src/ru/study/config.txt";
        // относительный путь к файлу с анализируемым текстом
        String testPath = "src/ru/study/test.txt";
        StringBuilder sb = new StringBuilder();
        // формируем reader, который будет читать наш файл с конфигурациями классов текстов, указывая путь до файла
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(configPath))) {
            String configLine;
            // создаём коллекцию классов текстов
            ArrayList<TemplateClass> templateClasses = new ArrayList<>();
            System.out.println("Входные данные классов текстов с ещё неготовыми уникальными ключевыми словами:");
            // считываем строки из текстового файла с классами текстов до конца файла
            while ((configLine = reader.readLine()) != null) {
                // если будут лишние пробелы в файле с конфигурацией классов текстов:
                // убрать лишние подряд идущие пробелы
                configLine = configLine.replaceAll("[\\s]+", " ");
                // убрать пробелы рядом с запятыми, чтобы пробелов не было в ключевых словах
                configLine = configLine.replaceAll(",[\\s]+", ",");
                configLine = configLine.replaceAll("[\\s]+,", ",");
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

            // считываем всё содержиое входного файла с анализируемым текстом
            String analysingText = new String(Files.readAllBytes(Paths.get(testPath)));
            analysingText = analysingText.replaceAll("[\\s]+", " ");
            System.out.println("\n Входной текст: \n" + analysingText + "\n");

            // переменная чтобы потом проценты вероятности принадлежности классам посчитать
            float percentage = 0;
            // Поиск кол-ва вхождений ключевых слов и уникальных ключевых слов
            // для каждого класса текста
            for (TemplateClass tc : templateClasses) {
                // кол-во вхождения уникальных слов неправильно считается
                int uniqueCount = 0;
                float keywordCount = 0;
                // для каждого ключевого слова класса текста
                for (String keyWord : tc.getKeyword()) {
                    // устанавливаем начало анализирования текста на 1 символ
                    int lastIndex = 1;
                    // запоминаем ключевое слово
                    String wordPattern = keyWord;
                    // если в ключевом слове есть сивол %
                    if (wordPattern.indexOf("%") != 0)
                        // заменяем его на регулярное выражение [А-я0-9]* (ищет 0 или бесконечность вхождений подряд идущих букв или цифр)
                        wordPattern = wordPattern.replace("%", "[А-я0-9]*");
                    // формируем паттерн поиска по регулярному выражению искомого ключевого слова
                    Pattern pattern = Pattern.compile(wordPattern);
                    // объявляем поисковик по регулярному выражению
                    Matcher matcher = pattern.matcher(analysingText);
                    // находим первый символ после позиции lastIndex, соответствующий регулярному выражению
                    while (matcher.find(lastIndex)) {
                        // если нашли ключевое слово, инкрементируем кол-во
                        keywordCount++;
                        //  если оно ещё уникальное для класса текста
                        if (tc.getUnique().contains(keyWord))
                            // инкрементируем кол-во уникальных ключевых слов
                            uniqueCount++;
                        // перемещаем начало анализируемого текста на 1 символ дальше (чтобы не стоять на месте и искать дальше по тексту)
                        lastIndex = matcher.start() + 1;
                    }
                }
                // итоговый вывод
                System.out.println("Для класса " + tc.getClassName() + ":");
                System.out.println("Нашлось уникальных ключевых слов: " + uniqueCount);
                System.out.println("Всего нашлось ключевых слов: " + keywordCount);
                // Среднее количество вхождений ключевых слов = (кол-во вхождений ключевых слов) / (кол-во ключевых слов класса текста)
                keywordCount = keywordCount / tc.getKeyword().size();
                System.out.println("Среднее количество вхождений ключевых слов: " + keywordCount);
                // сама функция: (кол-во найденных ключевых слов) * (число уникальных ключевых слов класса текста)
                tc.setClassificationMark(keywordCount * uniqueCount);
                System.out.println("Функция соответствия для типа текста " + tc.getClassName() + " = " + tc.getClassificationMark() + "\n");
                // сумма значений функции для всех классов (для подсчёта % вероятности)
                percentage += tc.getClassificationMark();
            }
            System.out.println("________________________________________________________________________");
            // вывод % вероятностей отношения текста к тому или иному классу текста
            // вероятность = [(оценка функции класса текста) / (сумму функции оценок класса текста)] * 100
            for (TemplateClass tc : templateClasses)
                System.out.println("С вероятностью " + Math.round((tc.getClassificationMark() / percentage) * 100) + "% анализируемый текста относится к классу " + tc.getClassName());
            System.out.println("________________________________________________________________________");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
