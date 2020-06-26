package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.tipcalculator.R
import es.iessaladillo.pedrojoya.tipcalculator.model.TipCalculator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tipCalculator = createTipCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        //TextWatcher only affects the views that will take part in the calculator
        txtBill.addTextChangedListener(textWatcher)
        txtPercentage.addTextChangedListener(textWatcher)
        txtDiners.addTextChangedListener(textWatcher)

        //Buttons
        btnResetTip.setOnClickListener { btnResetBillAndPercentage() }
        btnResetDiners.setOnClickListener { btnResetDiners() }
    }

    private var textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isValidData()) {
                calculateTip()
            } else {
                setDefaultWrongData()
            }
        }
    }

    //Functions for not valid data

    private fun btnResetBillAndPercentage() {
        resetPercentage()
        resetBill()
    }

    private fun btnResetDiners() {
        resetDiners()
    }

    private fun resetBill() {
        txtBill.setText(getString(R.string.default_zero))
        txtBill.requestFocus()
    }

    private fun resetPercentage() {
        txtPercentage.setText(getString(R.string.default_percentage))
        txtPercentage.requestFocus()
    }

    private fun resetDiners() {
        txtDiners.setText(getString(R.string.default_diners))
        txtDiners.requestFocus()
    }

    private fun setDefaultWrongData() {
        if (!isValidBill()) {
            resetBill()
        }

        if (!isValidPercentage()) {
            resetPercentage()
        }

        if (!isValidDiners()) {
            resetDiners()
        }
    }

    //FUNCTIONS FOR VALID DATA
    //Calculates the tip with current data
    private fun calculateTip() {
        refreshCalculator()
        txtTip.setText(getString(R.string.format_required, tipCalculator.calculateTip()).replace(',', '.'))
        txtTotal.setText(getString(R.string.format_required, tipCalculator.calculateTotal()).replace(',', '.'))
        txtPerDiner.setText(getString(R.string.format_required, tipCalculator.calculatePerDiner()).replace(',', '.'))
        txtPerDinerRounded.setText(getString(R.string.format_required, tipCalculator.calculatePerDinerRounded()).replace(',', '.'))

    }

    //Instantiates the TipCalculator with initial values
    private fun createTipCalculator() : TipCalculator {
        //At this point I can not still make reference to the string resources
        return TipCalculator(0.00f, 10.00f, 1)
    }

    //Gives to the calculator the current values
    private fun refreshCalculator() {
        tipCalculator.setBill(txtBill.text.toString().toFloat())
        tipCalculator.setPercentage(txtPercentage.text.toString().toFloat())
        tipCalculator.setDiners(txtDiners.text.toString().toInt())
    }

    //VALIDATIONS

    private fun isValidData(): Boolean {
        return isValidBill() && isValidPercentage() && isValidDiners()
    }

    private fun isValidBill(): Boolean {
        var bill = txtBill.text.toString()
        return isFloat(bill) && !isNegativeFloat(bill.toFloat())
    }

    private fun isValidPercentage(): Boolean {
        var percentage = txtPercentage.text.toString()
        return isFloat(percentage) && !isNegativeFloat(percentage.toFloat())
    }

    private fun isValidDiners(): Boolean {
        var diners = txtDiners.text.toString()
        return isInt(diners) && isPositiveInt(diners.toInt())
    }

    private fun isFloat(num: String): Boolean {
        return try {
            num.toFloat()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isInt(num: String): Boolean {
        return try {
            num.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isNegativeFloat(num : Float): Boolean {
        return num < 0f
    }

    private fun isPositiveInt(num : Int): Boolean {
        return num > 0
    }
}
