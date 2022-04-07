package com.example.projectuas

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AddDataAct : AppCompatActivity() {
    lateinit var spiner : Spinner
    lateinit var _btnAddData : Button
    lateinit var _btnBack : Button

    lateinit var _etInfo : EditText
    lateinit var _etNominal : EditText

    //getDate
    var cal = Calendar.getInstance()
    lateinit var _btnDate: Button
    lateinit var _tvDate: TextView

    lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        db = FirebaseFirestore.getInstance()

        _btnAddData = findViewById(R.id.btnOut)
        _btnBack = findViewById(R.id.btnBack)
        _etInfo = findViewById(R.id.etInfo)
        _etNominal = findViewById(R.id.etNominal)

        //getDate
        _tvDate = findViewById(R.id.tvDate)
        _btnDate = findViewById(R.id.btnDate)

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
                DatePickerDialog(this@AddDataAct,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        //spinner for Category
        spiner = findViewById(R.id.spCategory)
        val AdapterSPCategory = ArrayAdapter.createFromResource(this,
            R.array.category, android.R.layout.simple_spinner_item)
        AdapterSPCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spiner.adapter = AdapterSPCategory

        //back to MainAct
        _btnBack.setOnClickListener {
            startActivity(Intent(this@AddDataAct, MainActivity::class.java))
            finish()
        }

        _btnAddData.setOnClickListener {
            val cat = getValues(spiner)
            addDataToDB(db, _etInfo.text.toString(), _tvDate.text.toString(), _etNominal.text.toString(), cat)
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        _tvDate!!.text = sdf.format(cal.getTime())
    }

    fun getValues(view: View): String {
        return spiner.selectedItem.toString()
        //Toast.makeText(this, spiner.selectedItem.toString(), Toast.LENGTH_LONG).show()
    }

    fun addDataToDB(db:FirebaseFirestore, judul: String, tanggal: String, nominal: String, kategori: String) {
        val newData = itemList(judul, tanggal, nominal, kategori)
        db.collection("dbData")
            //.document(id)
            .add(newData)
            //.set(namaBaru)
            .addOnSuccessListener {
                Toast.makeText(this@AddDataAct,"Data Berhasil disimpan",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AddDataAct, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@AddDataAct,"Data Gagal disimpan",Toast.LENGTH_SHORT).show()
            }
    }
}