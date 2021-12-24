# Классификатор текста на основе РБНФ описаний
![made by Kuchibecka](https://img.shields.io/badge/made%20by-Kuchibecka-blueviolet)
![java.util.regex](https://img.shields.io/badge/java.util-regex-red)

## Описание
Программа для классификации заданного текстовым файлом текста. Классы текстов задаются отдельным текстовым файлом, составленным в следующем формате (РБНФ):
- **Файл** = Описание_класса"\n" {Описание_класса"\n"}
- **Описание_класса** = Имя_класса "," Шаблон_ключевых_слов
- **Шаблон_ключевых_слов** = Шаблон_ключевого_слова {"," Шаблон_ключевого_слова}
- **Шаблон_ключевого_слова** = Символ_шаблона {Символ_шаблона}
- **Символ_шаблона** = Символ | Подстановочный_знак
- **Имя_класса** = Символ {Символ}
- **Символ** = "А".."я" | "0".."9"
- **Подстановочный_знак** = "%"

Класс текста определяется равным классу class, который обладает наибольшим среди всех классов значением функции:  
```F(class) = W(class) * C(class)```, где 
- **W(class)** - число уникальных ключевых слов класса class, найденных в тексте 
- **C(class)** - срденее количество вхождений ключевого слова класса class по всем ключевым словам этого класса.

-------
## Входные данные
| Файл | Назначение |
|:----:|:----------:|
| config.txt | Файл конфигурации классов текста |
| test.txt | Файл с входным классифицируемым текстом |

-------
## Результат
Соотношение вероятностей принадлежности тому или иному классу текста согласно заданной в условии функции оценки

### Пример
```
С вероятностью 85% анализируемый текста относится к классу Образование
С вероятностью 12% анализируемый текста относится к классу Наука
С вероятностью 0% анализируемый текста относится к классу Кино
С вероятностью 3% анализируемый текста относится к классу Музыка
```

-------
## Дополнительная выходная информация
Запустив программу с заданными в проекте входными данными в выходной поток также выводится нижеизложенная дополнительная информация.

### Данные о сформированных классах текстов с соответствующими полями для классификации
```
Сформированные классы текстов:
Класс текста: {имя класса='Образование', ключевые слова=[экзамен%, студент%, информац%, ЕГЭ, знан%, ВУЗ%, школьник%, школ%, %хим, русск% яз%, геогр%, сдача], уникальные ключевые слова=[студент%, информац%, ЕГЭ, знан%, ВУЗ%, школьник%, школ%, %хим, русск% яз%, геогр%, сдача], оценка классификации=1.0}
Класс текста: {имя класса='Наука', ключевые слова=[искуственн% интеллект%, исслед%, лаборатор%, сист%, %мозг%, электр%, хим%, экзамен%, вселенн%, прилож%], уникальные ключевые слова=[искуственн% интеллект%, исслед%, лаборатор%, сист%, %мозг%, электр%, хим%, прилож%], оценка классификации=1.0}
Класс текста: {имя класса='Кино', ключевые слова=[автор%, сюжет%, вселенн%, %сказ%, кинемат%, кино%, геро%, исполн%, истор%, характер, %пользов%, сборник, классик%, персонаж%, %жанр%, сцен%], уникальные ключевые слова=[автор%, сюжет%, %сказ%, кинемат%, кино%, геро%, %пользов%, сборник, персонаж%], оценка классификации=1.0}
Класс текста: {имя класса='Музыка', ключевые слова=[%музык%, сцен%, песн%, побед%, шоу%, дуэт%, исполн%, истор%, характер, классик%, %пою%, %жанр%, рок%, %хип-хоп%, поп%, сцен%], уникальные ключевые слова=[%музык%, песн%, побед%, шоу%, дуэт%, %пою%, рок%, %хип-хоп%, поп%, сцен%], оценка классификации=1.0}
```

### Результаты проверки на принадлежность текста каждому из сконфигурированных классов
- Количество найденных в тексте уникальных ключевых слов класса;
- Общее количество найденных ключевых слов класса;
- Среднее количество вхождений ключевых слов;
- Функция соответствия классу текста.

На примере класса "Образование":
```
Для класса Образование:
Нашлось уникальных ключевых слов: 10
Всего нашлось ключевых слов: 20.0
Среднее количество вхождений ключевых слов: 1.6666666
Функция соответствия для типа текста Образование = 16.666666
```
