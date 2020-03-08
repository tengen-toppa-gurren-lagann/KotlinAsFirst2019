@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow
import kotlin.math.abs


/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- сложная.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 0.0, 2.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {
    private val coeffsList = coeffs.toMutableList()
    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = coeffsList[coeffsList.size - (i + 1)]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var result = 0.0
        var power: Int
        for (i in 0 until coeffsList.size) {
            power = coeffsList.size - (i + 1)
            result += coeff(power) * x.pow(power)
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        for (i in 0 until coeffsList.size)
            if (abs(coeffsList[i]) > Double.MIN_VALUE) return coeffsList.size - (i + 1)
        return 0
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val maxList: MutableList<Double> = if (other.coeffsList.size > coeffsList.size)
            MutableList(other.coeffsList.size) { 0.0 }
        else MutableList(coeffsList.size) { 0.0 }
        for (i in 0 until coeffsList.size)
            maxList[maxList.size - (i + 1)] = coeffsList[coeffsList.size - (i + 1)]
        for (i in 0 until other.coeffsList.size)
            maxList[maxList.size - (i + 1)] += other.coeffsList[other.coeffsList.size - (i + 1)]
        return Polynom(*maxList.toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom {
        val invert = mutableListOf<Double>()
        for (i in 0 until coeffsList.size) {
            invert.add(coeffsList[i] * (-1))
        }
        return Polynom(*invert.toDoubleArray())
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this.plus(other.unaryMinus())

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val size = coeffsList.size + other.coeffsList.size
        val multiply = MutableList(size) { 0.0 }
        for (i in 0 until coeffsList.size)
            for (j in 0 until other.coeffsList.size)
                multiply[multiply.size - (i + j + 1)] += (coeffsList[coeffsList.size - (i + 1)] * other.coeffsList[other.coeffsList.size - (j + 1)])
        return Polynom(*multiply.toDoubleArray())
    }

    // Проверка, не нулевой ли полином
    private fun isZero(): Boolean {
        for (i in 0 until coeffsList.size) if (abs(coeffsList[i]) > Double.MIN_VALUE) return false
        return true
    }

    private fun divide(other: Polynom): Pair<Polynom, Polynom> {
        if (other.isZero()) throw IllegalArgumentException("Деление на нулевой полином")
        var quotient = Polynom(0.0) // Частное (quotient)
        var reminder = Polynom(*coeffsList.toDoubleArray()) // Остаток (reminder)
        while (!reminder.isZero() && (reminder.degree() >= other.degree())) {
            val deg = reminder.degree() - other.degree()
            val newList = MutableList(deg + 1) { 0.0 }
            newList[0] = reminder.coeff(reminder.degree()) / other.coeff(other.degree())
            val t = Polynom(*newList.toDoubleArray())
            quotient = quotient.plus(t)
            val min = t.times(other)
            reminder = reminder.minus(min)
        }
        return Pair(quotient, reminder)
    }


    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom = divide(other).first

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = divide(other).second

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is Polynom && coeffsList.dropWhile { it == 0.0 } == other.coeffsList.dropWhile { it == 0.0 }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int {
        var result = 1
        for (k in coeffsList.dropWhile { it == 0.0 }) {
            result += 31 * k.toInt()
        }
        return result + coeffsList.dropWhile { it == 0.0 }.size
    }
}
