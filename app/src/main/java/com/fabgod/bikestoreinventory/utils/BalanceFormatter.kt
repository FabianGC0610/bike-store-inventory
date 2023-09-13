package com.fabgod.bikestoreinventory.utils

import java.text.NumberFormat
import java.util.Currency

fun String.toBalanceFormat(): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("USD")
    return format.format(this.toDouble())
}
