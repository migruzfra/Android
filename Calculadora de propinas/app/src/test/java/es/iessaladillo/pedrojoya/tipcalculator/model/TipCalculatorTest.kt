package es.iessaladillo.pedrojoya.tipcalculator.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TipCalculatorTest {

    @DisplayName("Should throw IllegalArgumentException when bill is negative")
    @Test
    fun `should throw IllegalArgumentException when bill is negative`() {
        assertThrows(IllegalArgumentException::class.java) { TipCalculator(-1f, 10f, 1) }
    }

    @DisplayName("Should throw IllegalArgumentException when percentage is negative")
    @Test
    fun `should throw IllegalArgumentException when percentage is negative`() {
        assertThrows(IllegalArgumentException::class.java) { TipCalculator(1f, -1f, 1) }
    }

    @DisplayName("Should throw IllegalArgumentException when diners is not positive")
    @ParameterizedTest
    @ValueSource(ints = [-1, 0])
    fun `should throw IllegalArgumentException when diners is not positive`(diners: Int) {
        assertThrows(IllegalArgumentException::class.java) { TipCalculator(1f, 10f, -1) }
        assertThrows(IllegalArgumentException::class.java) { TipCalculator(1f, 10f, 0) }
    }

    @DisplayName("Should calculate total properly")
    @Test
    fun `should calculate total properly`() {
        val sut = TipCalculator(120f, 6f, 5)
        var actual = sut.calculateTotal()
        assertEquals(127.2f, actual)
    }

    @DisplayName("Should calculate total properly with bill 0")
    @Test
    fun `should calculate total properly with bill 0`() {
        val sut = TipCalculator(0f, 20f, 3)
        var actual = sut.calculateTotal()
        assertEquals(0f, actual)
    }

    @DisplayName("Should calculate total properly with percentage 0")
    @Test
    fun `should calculate total properly with percentage 0`() {
        val sut = TipCalculator(120f, 0f, 5)
        var actual = sut.calculateTotal()
        assertEquals(120f, actual)
    }

    @DisplayName("Should calculate perDiner properly")
    @Test
    fun `should calculate perDiner properly`() {
        val sut = TipCalculator(120f, 20f, 5)
        var actual = sut.calculatePerDiner()
        assertEquals(28.8f, actual)
    }

    @DisplayName("Should calculate perDinerRounded properly")
    @Test
    fun `should calculate perDinerRounded properly`() {
        val sut = TipCalculator(120f, 20f, 5)
        var actual = sut.calculatePerDinerRounded()
        assertEquals(29f, actual)
    }

    @DisplayName("Should calculate perDinerRounded properly when perDinerRounded has 00 as cents")
    @Test
    fun `should calculate perDinerRounded properly when perDinerRounded has 00 as cents`() {
        val sut = TipCalculator(120f, 20f, 4)
        var actual = sut.calculatePerDinerRounded()
        assertEquals(36f, actual)
    }

}