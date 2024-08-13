package linad.calculatetime

import java.util.concurrent.Executor

fun main() {
    var time1 = "8h 68m 82s"
    var time2 = "2h 68m 82s"
    print(secToText(textToSec(time1)))
    print(" + ")
    print(secToText(textToSec(time2)))
    print(" = ")
    println(timeSum(time1, time2))
    print(secToText(textToSec(time1)))
    print(" - ")
    print(secToText(textToSec(time2)))
    print(" = ")
    println(timeDiff(time1, time2))


}

fun timeSum(time1: String, time2: String) = secToText(textToSec(time1) + textToSec(time2))
fun timeDiff(time1: String, time2: String) = secToText(textToSec(time1) - textToSec(time2))
fun textToSec(text: String): Int {
    val num = mutableMapOf(
        "h" to 0,
        "m" to 0,
        "s" to 0
    )
    var buf: Int? = null
    for (char in text) {
        if (char == ' ') {
            continue
        }
        if (checkToInt(char)) {
            if (buf == null) {
                buf = char.toString().toInt()
            } else {
                buf *= 10
                buf += char.toString().toInt()
            }
        } else {
            num[char.toString()] = buf!!
            buf = null
        }
    }
    return num["h"]!! * 60 * 60 + num["m"]!! * 60 + num["s"]!!
}

fun checkToInt(letter: Char): Boolean {
    try {
        letter.toString().toInt()
        return true
    } catch (e: Exception) {
        return false
    }
}

fun secToText(sec: Int): String {
    var buf = sec
    val hour = buf / (60 * 60)
    buf -= (hour * 60 * 60)
    val minute = buf / 60
    buf -= (minute * 60)
    val second = buf
    return "${hour}h ${minute}m ${second}s"
}