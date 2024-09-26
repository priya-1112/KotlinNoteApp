package com.example.kotlinnoteapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinnoteapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var database : Note_Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = Note_Database.getdatabase(this)
        val Array = ArrayList<Note_DataClass>()


        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->

                if (result.resultCode== RESULT_OK){
                    val data = result.data
                    if (data!= null) {
                        Log.d("priya", "not null")
//                        var id = data?.getStringExtra("id")
//
//                        var title = data?.getStringExtra("title")
//                        var content = data?.getStringExtra("content")
                    }else{
                        Log.d("priya", "null")
                    }


                }


                 if (result.resultCode == 1) {

                     val data = result.data

                    var title = data?.getStringExtra("title")
                    var content = data?.getStringExtra("content")

                    if (title != null && content != null) {
                        val note = Note_DataClass(
                          title = title,
                            content = content
                        )

                        GlobalScope.launch {
                            database.notedao().insert(note)
                        }


                    }
                    else if (title!= null ) {
                        val note = Note_DataClass(
                            title = title
                        )

                        GlobalScope.launch {
                            database.notedao().insert(note)
                        }
                    }
                    else if( content != null){
                        val note = Note_DataClass(
                            content = content
                        )

                        GlobalScope.launch {
                            database.notedao().insert(note)
                        }
                    }



                }




            }



        binding.addcontent.setOnClickListener {

            Toast.makeText(this, "Write your heart out :)", Toast.LENGTH_SHORT)
            val intent = Intent(this, MainActivity2::class.java)
            resultLauncher.launch(intent)

        }

        val noteadapter = Note_Adapter(this, Array)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=noteadapter
        }


        database.notedao().getreadablenote().observe(this){ notelist: List<Note_DataClass> ->

            Array.clear()
            Array.addAll(notelist)
            noteadapter.notifyItemChanged(notelist.size)
        }






    }


}










