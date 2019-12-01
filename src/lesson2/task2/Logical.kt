@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.firstDigit
import lesson1.task1.secondDigit
import lesson1.task1.sqr
import lesson1.task1.thirdDigit
import kotlin.math.abs

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
    sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean =
    ((firstDigit(number) + secondDigit(number)) == (thirdDigit(number) + fourthDigit(number)))

fun fourthDigit(number: Int): Int = number / 1000
/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean =
    (x1 == x2) || (y1 == y2) || (abs(x1 - x2) == abs(y1 - y2))


/**
 * Простая
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    return if (((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) && (month == 2)) 29
    else when (month) {
        2 -> 28
        4 -> 30
        6 -> 30
        9 -> 30
        11 -> 30
        else -> 31
    }
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double,
    x2: Double, y2: Double, r2: Double
): Boolean = ((sqr(x1 - x2) + sqr(y1 - y2) <= sqr(r1 - r2)) && (r2 >= r1))

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    if ((a >= b) && (a >= c))
        if (((b <= r) && (c <= s)) || ((c <= r) && (b <= s))) return true
    if ((b >= a) && (b >= c))
        if (((a <= r) && (c <= s)) || ((c <= r) && (a <= s))) return true
    return ((c >= a) && (c >= b) && (((b <= r) && (a <= s)) || ((a <= r) && (b <= s))))
}
