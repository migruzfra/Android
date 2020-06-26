package es.iessaladillo.pedrojoya.tipcalculator.model

import java.lang.IllegalArgumentException
import kotlin.math.ceil

class TipCalculator (private var bill : Float, private var percentage : Float, private var diners : Int) {



    init {
        if (bill<0.0f || percentage < 0.0f || diners <= 0) {
            throw IllegalArgumentException()
        }
    }

    fun calculateTip(): Float  {
        return bill*percentage/100
    }

    fun calculateTotal(): Float {
        return bill + calculateTip()
    }

    fun calculatePerDiner(): Float {
        return calculateTotal()/diners
    }

    fun calculatePerDinerRounded(): Float {
        return ceil(calculatePerDiner().toDouble()).toFloat()
    }

    fun setBill(bill: Float) {
        this.bill = bill
    }

    fun setPercentage(percentage: Float) {
        this.percentage = percentage
    }

    fun setDiners(diners: Int) {
        this.diners = diners
    }
}