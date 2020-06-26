package es.iessaladillo.pedrojoya.tipcalculator.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import es.iessaladillo.pedrojoya.tipcalculator.R
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    // Initial state

    @Test
    fun should_txtTip_txtTotal_txtPerDiner_and_txtPerDinerRounded_not_be_focusable() {
        onView(withId(R.id.txtTip))
            .check(matches(not(isFocusable())))
        onView(withId(R.id.txtTotal))
            .check(matches(not(isFocusable())))
        onView(withId(R.id.txtPerDiner))
            .check(matches(not(isFocusable())))
        onView(withId(R.id.txtPerDinerRounded))
            .check(matches(not(isFocusable())))
    }

    @Test
    fun should_txtBill_have_focus_initially() {
        onView(withId(R.id.txtBill))
            .check(matches(hasFocus()))
    }

    @Test
    fun should_txtBill_txtPercentage_and_txtDiners_have_default_initially() {
        onView(withId(R.id.txtBill))
            .check(matches(withText("0.00")))
        onView(withId(R.id.txtPercentage))
            .check(matches(withText("10.00")))
        onView(withId(R.id.txtDiners))
            .check(matches(withText("1")))
    }

    // txtBill

    @Test
    fun should_txtBill_have_default_when_invalid_input() {
        onView(withId(R.id.txtBill))
            .perform(replaceText(""), closeSoftKeyboard())

        onView(withId(R.id.txtBill))
            .check(matches(withText("0.00")))
    }

    @Test
    fun should_txtBill_change_in_text_affect_txtTip_txtTotal_txtPerDiner_txtPerDinerRounded() {
        onView(withId(R.id.txtBill))
            .perform(replaceText("100"), closeSoftKeyboard())

        onView(withId(R.id.txtTip))
            .check(matches(withText("10.00")))
        onView(withId(R.id.txtTotal))
            .check(matches(withText("110.00")))
        onView(withId(R.id.txtPerDiner))
            .check(matches(withText("110.00")))
        onView(withId(R.id.txtPerDinerRounded))
            .check(matches(withText("110.00")))
    }

    // txtPercentage

    @Test
    fun should_txtPercentaje_have_default_when_invalid_input() {
        onView(withId(R.id.txtPercentage))
            .perform(replaceText(""), closeSoftKeyboard())

        onView(withId(R.id.txtPercentage))
            .check(matches(withText("10.00")))
    }

    @Test
    fun should_txtPercentage_change_in_text_affect_txtTip_txtTotal_txtPerDiner_txtPerDinerRounded() {
        onView(withId(R.id.txtBill))
            .perform(replaceText("100"), closeSoftKeyboard())
        onView(withId(R.id.txtPercentage))
            .perform(replaceText("20"), closeSoftKeyboard())

        onView(withId(R.id.txtTip))
            .check(matches(withText("20.00")))
        onView(withId(R.id.txtTotal))
            .check(matches(withText("120.00")))
        onView(withId(R.id.txtPerDiner))
            .check(matches(withText("120.00")))
        onView(withId(R.id.txtPerDinerRounded))
            .check(matches(withText("120.00")))
    }

    // txtDiners

    @Test
    fun should_txtDiners_have_default_when_invalid_input() {
        onView(withId(R.id.txtDiners))
            .perform(replaceText(""), closeSoftKeyboard())

        onView(withId(R.id.txtDiners))
            .check(matches(withText("1")))
    }

    @Test
    fun should_txtDiners_change_in_text_affect_txtPerDiner_txtPerDinerRounded() {
        onView(withId(R.id.txtBill))
            .perform(replaceText("100"), closeSoftKeyboard())
        onView(withId(R.id.txtPercentage))
            .perform(replaceText("10"), closeSoftKeyboard())
        onView(withId(R.id.txtDiners))
            .perform(replaceText("2"), closeSoftKeyboard())

        onView(withId(R.id.txtTip))
            .check(matches(withText("10.00")))
        onView(withId(R.id.txtTotal))
            .check(matches(withText("110.00")))
        onView(withId(R.id.txtPerDiner))
            .check(matches(withText("55.00")))
        onView(withId(R.id.txtPerDinerRounded))
            .check(matches(withText("55.00")))
    }

    // btnResetTip works properly

    @Test
    fun should_btnResetTip_click_set_txtBill_and_txtPercetage_to_default() {
        onView(withId(R.id.txtBill))
            .perform(replaceText("100"), closeSoftKeyboard())
        onView(withId(R.id.btnResetTip)).perform(click())

        onView(withId(R.id.txtBill))
            .check(matches(withText("0.00")))
        onView(withId(R.id.txtPercentage))
            .check(matches(withText("10.00")))
        onView(withId(R.id.txtBill))
            .check(matches(hasFocus()))
    }

    // btnResetDiners works properly

    @Test
    fun should_btnResetDiners_click_set_txtDiners_to_default() {
        onView(withId(R.id.txtDiners))
            .perform(replaceText("2"), closeSoftKeyboard())
        onView(withId(R.id.btnResetDiners)).perform(click())

        onView(withId(R.id.txtDiners))
            .check(matches(withText("1")))
        onView(withId(R.id.txtDiners))
            .check(matches(hasFocus()))

    }

}