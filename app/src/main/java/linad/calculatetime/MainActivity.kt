package linad.calculatetime

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var time1:EditText
    private lateinit var time2:EditText
    private lateinit var buttonSum:Button
    private lateinit var buttonDiff:Button
    private lateinit var timeResult:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        time1 = findViewById(R.id.firstTime)
        time2 = findViewById(R.id.secondTime)
        buttonSum = findViewById(R.id.buttonSum)
        buttonDiff = findViewById(R.id.buttonDiff)
        timeResult = findViewById(R.id.textResult)

        buttonSum(buttonSum)
        buttonDiff(buttonDiff)
    }
    fun buttonSum(view: View){
        timeResult.text = timeSum(time1.text.toString(), time2.text.toString())
    }
    fun buttonDiff(view: View){
        timeResult.text = timeDiff(time1.text.toString(), time2.text.toString())
    }
    private fun timeSum(time1: String, time2: String) = secToText(textToSec(time1) + textToSec(time2))
    private fun timeDiff(time1: String, time2: String) = secToText(textToSec(time1) - textToSec(time2))
    private fun textToSec(text: String): Int {
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

    private fun secToText(sec: Int): String {
        var buf = sec
        val hour = buf / (60 * 60)
        buf -= (hour * 60 * 60)
        val minute = buf / 60
        buf -= (minute * 60)
        val second = buf
        return "Результат: ${hour}h ${minute}m ${second}s"
    }

    private fun checkToInt(letter: Char): Boolean {
        try {
            letter.toString().toInt()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}