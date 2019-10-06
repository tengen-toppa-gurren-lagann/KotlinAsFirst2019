@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.firstDigit
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var num: Int = n / 10
    var s = 1
    while (abs(num) > 0) {
        num /= 10
        s++
    }

    return s
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var totalFib: Int
    var fibPrev = 0
    var fibN = 1
    for (i in 0 until (n - 1)) {
        totalFib = fibPrev + fibN
        fibPrev = fibN
        fibN = totalFib
    }
    return fibN
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = 1
    while ((k % m != 0) || (k % n != 0)) {
        k++
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var k = 2
    while (n % k != 0) {
        k++
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxdiv = 1
    for (k in 2 until n) {
        if (n % k == 0)
            maxdiv = k
    }
    return maxdiv
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = (n % minDivisor(m) != 0) && (m % minDivisor(n) != 0) || (n == 1) || (m == 1)

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var doubleK: Double
    for (i in m..n) {
        doubleK = i.toDouble()
        if (sqrt(doubleK) % 1.0 == 0.0) {
            return true
        }
    }
    return false
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var nextX = x
    var s = 0
    while (nextX != 1)
        if (nextX % 2 == 0) {
            nextX /= 2
            s++
        } else {
            nextX = (3 * nextX + 1)
            s++

        }
    return s
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double {
    var currentTerm: Double
    var xx: Double = x
    var sum = 0.0
    var power = 1
    var plusminus = -1
    while (xx > (2 * PI)) {
        xx -= (2 * PI)
    }
    while (xx < (-2 * PI)) {
        xx += (2 * PI)
    }
    currentTerm = xx
    while (abs(currentTerm) >= abs(eps)) {
        sum += currentTerm
        power += 2
        currentTerm = ((plusminus) * xx.pow(power) / factorial(power))
        plusminus *= (-1)
    }
    return sum
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double {
    var nextTerm: Double = (x)
    var sum = 1.0
    var power = 0
    var plusminus = -1
    val simpleX = simpliar(x)
    while (abs(nextTerm) >= abs(eps)) {
        power += 2
        nextTerm = ((plusminus) * simpleX.pow(power) / factorial(power))
        plusminus *= (-1)
        sum += nextTerm
    }
    return sum
}

fun simpliar(nextTerm: Double): Double {
    var x = nextTerm
    while (x > (2 * PI)) {
        x -= (2 * PI)
    }
    while (x < (-2 * PI)) {
        x += (2 * PI)
    }
    return x
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var fromFinish = n
    var swap = 0
    var rule = digitCount(n)
    var digit: Int
    while (rule > 0) {
        rule -= 1
        digit = fromFinish % 10
        fromFinish /= 10
        swap += digit * (10.0.pow(rule)).toInt()
    }
    return swap
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = (n == revert(n))

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var dig = n
    val someDigit = firstDigit(n)
    while ((dig % 10 == someDigit) && (dig != 0)) {
        dig /= 10
    }
    return dig != 0
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var prevNum: Int
    var num: Int
    var s = 0
    var raise = 1
    do {
        num = (raise * raise)
        raise++
        s += digitCount(num)
        prevNum = num
    } while (s < n)
    s = s - n + 1
    for (i in 1 until s) {
        prevNum /= 10
    }
    return prevNum % 10
}

fun digitCount(x: Int): Int {
    var counter = 1
    var number = x
    while (number / 10 > 0) {
        number /= 10
        counter++
    }
    return counter
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var prevNum: Int
    var num: Int
    var s = 0
    var raise = 1
    do {
        num = fib(raise)
        raise++
        s += digitCount(num)
        prevNum = num
    } while (s < n)
    s = s - n + 1
    for (i in 1 until s) {
        prevNum /= 10
    }
    return prevNum % 10
}