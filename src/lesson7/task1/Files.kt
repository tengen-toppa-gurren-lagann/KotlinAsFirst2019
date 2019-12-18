@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import kotlin.math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        for (checkString in substrings.toSet().toList()) {
            var counter = 0
            for (i in 0..(line.length - checkString.length))
                if (line.substring(i, i + checkString.length).equals(checkString, true)) {
                    counter++
                }
            if (result[checkString] != null) result[checkString] = result[checkString]!! + counter
            else result[checkString] = counter
        }
    }
    return result
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val marker = listOf('ж', 'ч', 'ш', 'щ', 'Ж', 'Ч', 'Ш', 'Щ')
    val right = listOf('и', 'а', 'у', 'И', 'А', 'У')
    val wrong = listOf('ы', 'я', 'ю', 'Ы', 'Я', 'Ю')
    for (line in File(inputName).readLines()) {
        if (line.isNotEmpty()) {
            val newLine = line.toCharArray()
            for (i in 0 until line.length - 1) {
                if (marker.contains(line[i]) && wrong.contains(line[i + 1]))
                    newLine[i + 1] = right[wrong.indexOf(line[i + 1])]
            }
            result.write(newLine)
            result.newLine()
        } else result.newLine()
    }
    result.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLength = 0
    val result = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines().toList()
    val outLines = mutableListOf<String>()
    for (line in lines) {
        if (line.trim().length > maxLength) maxLength = line.trim().length
        outLines.add(line.trim()) // Probably need list to save
    }
    for ((k, line) in outLines.withIndex()) {
        result.write(line.padStart((maxLength + line.length) / 2))
        if (k != outLines.size - 1) result.newLine()
    }
    result.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val result = File(outputName).bufferedWriter()
    var maxLength = 0
    val wordsList = mutableListOf<String>()
    var lettersList: List<Char>
    var lettersSet: Set<Char>
    for (line in File(inputName).readLines()) {  // Проходим по всем строкам словаря
        lettersList = line.toLowerCase().toCharArray().toList()
        lettersSet = line.toLowerCase().toCharArray().toSet()
        if (lettersList.size == lettersSet.size) {
            if (line.length == maxLength) wordsList.add(line)
            else if (line.length > maxLength) {
                wordsList.clear()
                wordsList.add(line)
                maxLength = line.length
            }
        }
    }
    for (i in wordsList.indices) {
        result.write(wordsList[i])
        if (i < wordsList.size - 1) result.write(", ") // Если слово не последнее в списке, то выводим запятую
    }
    result.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val htmlStrings = mutableListOf<String>()
    val htmlText = mutableListOf<String>()
    var boldCnt = 0
    var italicCnt = 0
    var crossCnt = 0
    var pLineIndex: Int
    val asterisk = '*'
    val tilda = '~'
    htmlText.addAll(listOf("<html>", "<body>"))
    var lettersList: MutableList<Char>
    for (line in File(inputName).readLines()) {
        lettersList = line.toCharArray().toMutableList()
        // Обрабатываем "*" и "**"
        var i = lettersList.indexOf(asterisk)
        while (i >= 0) {
            if (i < lettersList.size - 1 && lettersList[i + 1] == asterisk) { // Не последний символ в строке, двойная звездочка
                if (boldCnt > 0) {
                    lettersList.removeAt(i)
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "</b>".toList())
                    boldCnt--
                } else {
                    lettersList.removeAt(i)
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "<b>".toList())
                    boldCnt++
                }
            } else {
                if (italicCnt > 0) {
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "</i>".toList())
                    italicCnt--
                } else {
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "<i>".toList())
                    italicCnt++
                }
            }
            i = lettersList.indexOf(asterisk)
        }
        // Обрабатываем "~~"
        i = lettersList.indexOf(tilda)
        while (i >= 0) {
            if (i < lettersList.size - 1 && lettersList[i + 1] == tilda) {
                if (crossCnt > 0) {
                    lettersList.removeAt(i)
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "</s>".toList())
                    crossCnt--
                } else {
                    lettersList.removeAt(i)
                    lettersList.removeAt(i)
                    lettersList.addAll(i, "<s>".toList())
                    crossCnt++
                }
            }
            i = lettersList.indexOf(tilda)
        }
        htmlStrings.add(String(lettersList.toCharArray()))
    }
    // Расставляем абзацы <p></p> в html-тексте
    pLineIndex = 2 // =2, т.к. в начале файла <html> и <body>
    for (i in htmlStrings.indices) {
        if (htmlStrings[i] == "") {
            htmlText.add(pLineIndex, "<p>")
            htmlText.add("</p>")
            pLineIndex =
                i + 4 // <html> + <body> + <p> + переход
        } else htmlText.add(htmlStrings[i])
    }
    if (pLineIndex != 2 && pLineIndex < htmlText.size) { // Были абзацы и есть ещё строки в конце текста, которые надо обернуть в <p>..</p>
        htmlText.add(pLineIndex, "<p>")
        htmlText.add("</p>")
    }
    htmlText.addAll(listOf("</body>", "</html>"))
    // Выводим результат
    for (i in htmlText.indices) {
        result.write(htmlText[i])
        if (i < htmlText.size - 1) result.newLine()
    }
    result.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val sProduct = (lhv * rhv).toString()
    val width = sProduct.length + 1 // Ширина всех выводимых строк (один символ на знаки)
    val sLhv = lhv.toString()
    val sRhv = rhv.toString()
    result.write(sLhv.padStart(width))
    result.newLine()
    result.write("*" + sRhv.padStart(width - 1))
    result.newLine()
    result.write("".padStart(width, '-'))
    result.newLine()
    var div = rhv
    var cnt = 0
    var line: String
    do {
        val mod = div % 10 // Очередная цифра множителя
        div /= 10
        val i = lhv * mod
        line =
            if (cnt == 0) i.toString().padStart(width - cnt)
            else "+" + i.toString().padStart(width - cnt - 1)
        result.write(line)
        result.newLine()
        cnt++
    } while (div > 0) // Пока не перебрали все цифры множителя
    result.write("".padStart(width, '-'))
    result.newLine()
    result.write(sProduct.padStart(width))
    result.newLine()
    result.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val result = File(outputName).bufferedWriter()
    val quot = lhv / rhv // Частное
    val digitsLhv = getDigits(lhv)
    val digitsQuot = getDigits(quot)
    val firstLine: String
    val secondLine: String
    var lhvDigitIndex = 0
    var rhvDigitIndex = 1
    var spacesCnt = 0
    val sList = mutableListOf<String>()
    // Из цифр делимого формируем первое число, из которого будем вычитать (уменьшаемое)
    var y = 0
    val l = mutableListOf<Int>()
    for (digit in digitsLhv) { //Подбираем первое уменьшаемое
        l.add(digit)
        y = getNumber(l)
        lhvDigitIndex++
        if (y / rhv > 0) break // Нашли число, делящееся на делимое
        else y = lhv
    }
    var x = rhv * digitsQuot[0] // Находим вычитаемое число

    var xLength = getDigits(x).size // Длина вычитаемого
    var yLength = getDigits(y).size // Длина уменьшаемого

    // Формируем первую и вторую строки (они отличаются от остальных)
    var s1 = lhv.toString()
    if (l.size == getDigits(x).size) { // При равном количестве цифр в уменьшаемом и вычитаемом добавляем пробел перед уменьшаемым
        s1 = " $s1"
        xLength++
    }
    firstLine = "$s1 | $rhv"
    var s2 = "-$x"
    s2 = s2.padStart(yLength, ' ')
    secondLine = s2.padEnd(s1.length) + "   " + quot.toString()
    sList.add(firstLine)
    sList.add(secondLine)
    do { // Цикл формирования строк
        // Выводим черту под вычитаемым
        val xyLength = if (xLength > yLength) xLength else yLength
        var line = "".padStart(xyLength, '-') // Формируем черту из дефисов
        line = line.padStart(spacesCnt + line.length, ' ') // Добавляем нужное число пробелов
        sList.add(line) // Добавляем черту

        // Находим разность
        val z = y - x
        line = z.toString().padStart(line.length, ' ') // Выравниваем разность по правому краю черты
        // Проверяем, не закончились ли цифры - если закончились, то заканчиваем
        if (lhvDigitIndex >= digitsLhv.size) {
            sList.add(line)
            break
        }
        // Добавляем следующую цифру к строке
        line += digitsLhv[lhvDigitIndex].toString()
        sList.add(line)
        // Формируем новые уменьшаемое и вычитаемое для следующего цикла
        y = z * 10 + digitsLhv[lhvDigitIndex] // Новое уменьшаемое
        x = rhv * digitsQuot[rhvDigitIndex] // Новое вычитаемое
        line = ("-$x").padStart(line.length, ' ')  // Добавляем нужное количество пробелов в начале строки
        sList.add(line)

        spacesCnt = line.length - y.toString().length // Определяем количество пробелов для следующей строки
        if (x.toString().length == y.toString().length) spacesCnt--

        lhvDigitIndex++
        rhvDigitIndex++

        xLength = getDigits(x).size + 1 // Длина вычитаемого (+1 - на знак '-')
        yLength = getDigits(y).size   // Длина уменьшаемого

    } while (lhvDigitIndex <= digitsLhv.size) // Пока не закончатся цифры в делимом

    for (i in sList.indices) { // Выводим список строк
        result.write(sList[i])
        if (i < sList.size - 1) result.newLine()
    }
    result.close()
}

// Получение списка цифр целого неотрицательного десятичного числа
fun getDigits(number: Int): List<Int> {
    var i = number
    if (i < 0) throw IllegalArgumentException("Допустимы только неотрицательные числа!")
    val list = mutableListOf<Int>()
    do {
        val digit = i % 10
        list.add(0, digit)
        i /= 10
    } while (i > 0)
    return list
}

// Сформировать целое неотрицательное десятичное число из списка цифр
fun getNumber(digitsList: List<Int>): Int {
    if (digitsList.isEmpty()) throw IllegalArgumentException("В числе должна быть хотя бы одна цифра!")
    var s = ""
    for (digit in digitsList) s += digit // Вычисляем число по цифрам
    return s.toInt()
}

