package com.codmine.mukellef.domain.util

fun postDate(dayString : String) : String {
    return dayString

    val month = dayString.substring(4, 6)
    var day = dayString.substring(6, 8)
    day = day.toInt().toString()
    when(month) {
        "01" -> return ("$day Ocak")
        "02" -> return ("$day Şubat")
        "03" -> return ("$day Mart")
        "04" -> return ("$day Nisan")
        "05" -> return ("$day Mayıs")
        "06" -> return ("$day Haziran")
        "07" -> return ("$day Temmuz")
        "08" -> return ("$day Ağustos")
        "09" -> return ("$day Eylül")
        "10" -> return ("$day Ekim")
        "11" -> return ("$day Kasım")
        "12" -> return ("$day Aralık")
    }
    return "Invalid Date"
}

fun postTime(dayString : String) : String {
    return dayString

    val hour = dayString.substring(8, 10)
    val minute = dayString.substring(10,12)
    return "$hour:$minute"
}

fun dateAndTime(dayString : String) : String {
    return dayString

    val year = dayString.substring(0, 4)
    val month = dayString.substring(4, 6)
    val day = dayString.substring(6, 8)
    val hour = dayString.substring(8, 10)
    val minute = dayString.substring(10,12)
    return "$day.$month.$year $hour:$minute"
}