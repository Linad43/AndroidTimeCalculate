package linad.calculatetime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var time1: EditText
    private lateinit var time2: EditText
    private lateinit var buttonSum: Button
    private lateinit var buttonDiff: Button
    private lateinit var timeResult: TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

        //buttonSum.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBtn))

        /*buttonSum(buttonSum)
        buttonDiff(buttonDiff)*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearMenuMain -> {
                time1.text.clear()
                time2.text.clear()
                timeResult.setText(R.string.resultNull)
                timeResult.setTextColor(ContextCompat.getColor(this, R.color.black))
                //timeResult.setTextColor(getResources().getColor(R.color.black))
                //timeResult.text = "Результат: 00h00m00s"
                val toast = Toast.makeText(
                    applicationContext,
                    "Произведена очистка",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.exitMenuMain -> {
                val toast = Toast.makeText(
                    applicationContext,
                    "Программа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun buttonSum(view: View) {
        timeResult.text = timeSum(time1.text.toString(), time2.text.toString())
        changeColorAndToast()
        //time1.hint = "123 123"
        //timeResult.textColors = R.color.colorTextResult
        //timeResult.setTextColor(R.color.colorTextResult)
        //timeResult.setTextColor(ContextCompat.getColor(this, R.color.colorTextResult))
        //timeResult.setTextColor(getResources().getColor(R.color.colorTextResult))

    }

    fun buttonDiff(view: View) {
        timeResult.text = timeDiff(time1.text.toString(), time2.text.toString())
        changeColorAndToast()
        //timeResult.setTextColor(ContextCompat.getColor(this, R.color.colorTextResult))
    }
    private fun changeColorAndToast(){
        timeResult.setTextColor(ContextCompat.getColor(this, R.color.colorTextResult))
        val toast = Toast.makeText(
            applicationContext,
            "Результат ${timeResult.text}",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun timeSum(time1: String, time2: String) =
        secToText(textToSec(time1) + textToSec(time2))

    private fun timeDiff(time1: String, time2: String) =
        secToText(textToSec(time1) - textToSec(time2))

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
        val flag = if (sec < 0) " -" else ""
        var buf = abs(sec)
        val hour = buf / (60 * 60)
        buf -= (hour * 60 * 60)
        val minute = buf / 60
        buf -= (minute * 60)
        val second = buf
        return "Результат:$flag ${hour}h ${minute}m ${second}s"
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