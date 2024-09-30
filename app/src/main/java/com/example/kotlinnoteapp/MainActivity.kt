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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinnoteapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var database: Note_Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = Note_Database.getdatabase(this)
        val Array = ArrayList<Note_DataClass>()


        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->



                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    if (data == null){
                        Toast.makeText(this, "hii", Toast.LENGTH_SHORT).show()
                    }

                    val ids = data?.getIntExtra("id", -1)
                    var title = data?.getStringExtra("title")
                    var content = data?.getStringExtra("content")


                    Log.d("priya", "" + ids)

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
                            lifecycleScope.launch {
                            database.notedao().insert(note)
                        }

                    } else if (title != null) {
                        val note = Note_DataClass(
                            title = title
                        )

                      lifecycleScope.launch {

                            database.notedao().insert(note)

                        }

                    } else if (content != null) {
                        val note = Note_DataClass(
                            content = content
                        )

                        lifecycleScope.launch {

                            database.notedao().insert(note)

                        }


                    }
                }
            }





        binding.addcontent.setOnClickListener {

            Toast.makeText(this, "Write your heart out :)", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity2::class.java)
            resultLauncher.launch(intent)

        }

        val noteadapter = Note_Adapter(this, Array)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteadapter
        }

            database.notedao().getreadablenote()
                .observe(this) { notelist: List<Note_DataClass> ->
                    Array.clear()
                    Array.addAll(notelist)
                    noteadapter.notifyItemInserted(notelist.size)
                }
        }





    }














