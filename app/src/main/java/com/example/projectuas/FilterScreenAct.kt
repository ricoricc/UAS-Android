package com.example.projectuas

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class FilterScreenAct : AppCompatActivity() {

    lateinit var _btnBack: Button
    lateinit var _btnApply: Button


    var cal = Calendar.getInstance()
    lateinit var _btnDate: Button
    lateinit var _tvDate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_screen)

        _btnBack = findViewById(R.id.btnBackF)
        _btnApply = findViewById(R.id.btnApply)


        //getDate
        _tvDate = findViewById(R.id.tvDateF)
        _btnDate = findViewById(R.id.btnDateF)

        _tvDate!!.text = "--/--/----"
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        _btnDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@FilterScreenAct,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        _btnBack.setOnClickListener {
            startActivity(Intent(this@FilterScreenAct, MainActivity::class.java))
            finish()
        }

        _btnApply.setOnClickListener {
            if(_tvDate.text.toString() == "--/--/----"){
                Toast.makeText(this@FilterScreenAct,
                "Isi data tanggal terlebih dahulu",
                Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                val intent = Intent(this@FilterScreenAct, MainActivity::class.java)
                intent.putExtra("DateFilter", _tvDate.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        _tvDate!!.text = sdf.format(cal.getTime())
    }
}