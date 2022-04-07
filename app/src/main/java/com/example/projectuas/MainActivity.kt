package com.example.projectuas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.auth.User

class MainActivity : AppCompatActivity() {

    lateinit var _btnAddData : Button
    lateinit var _btnResetFilter: Button
    var resetFilter = false

    private var arData = arrayListOf<itemList>()
    lateinit var _rvData : RecyclerView

    lateinit var db : FirebaseFirestore
    val adapter = adapterHistory(arData)

    lateinit var _btnFilter : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _btnFilter = findViewById(R.id.btnFilter)
        _btnResetFilter = findViewById(R.id.btnResetFilter)
        _btnResetFilter.visibility = View.INVISIBLE
        _btnResetFilter.visibility = View.INVISIBLE

        _btnFilter.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterScreenAct::class.java))
            finish()
        }

        _rvData = findViewById(R.id.rvDataList)
        _btnAddData = findViewById(R.id.btnAddData)
        _btnAddData.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddDataAct::class.java))
            finish()
        }
        val dateFilter = intent.getStringExtra("DateFilter")
        _btnResetFilter.setOnClickListener {
            _btnResetFilter.visibility = View.INVISIBLE
            resetFilter = true
            readData()
            viewData()
        }

        if(dateFilter == null) {
            _btnResetFilter.visibility = View.INVISIBLE
        }
        else {
            _btnResetFilter.visibility = View.VISIBLE
        }

        readData(db)
        viewData()
    }

    fun readData(db : FirebaseFirestore) {
        db.collection("dbData").get()
            .addOnSuccessListener { result ->
                arData.clear()
                val dateFilters = intent.getStringExtra("DateFilter")
                for (document in result){
                    val newData = itemList(
                        document.data.get("judul").toString(),
                        document.data.get("tanggal").toString(),
                        document.data.get("nominal").toString(),
                        document.data.get("kategory").toString()
                    )
                    if (dateFilters != null && !resetFilter){
                        if(document.data.get("tanggal").toString() == dateFilters){
                            arData.add(newData)
                        }
                    }
                    else {
                            arData.add(newData)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity,"Data Gagal dimuat", Toast.LENGTH_SHORT).show()
            }
    }

    private fun viewData() {
        _rvData.layoutManager = LinearLayoutManager(this)
        //_rvData.setHasFixedSize(true)
        //adapter = adapterHistory(arData)
        _rvData.adapter = adapter
    }
}
