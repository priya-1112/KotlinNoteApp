package com.example.kotlinnoteapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.RoomDatabase
import com.example.kotlinnoteapp.databinding.ActivityMain2Binding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    lateinit var database: Note_Database




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Note_Database.getdatabase(this)
        val id = intent.getIntExtra("ID", -1)

        if (id != -1) {

            lifecycleScope.launch {
                val note = database.notedao().getnotebyid(id)
                if (note != null) {
                    binding.notetitle.setText(note.title)
                    binding.notetype.setText(note.content)
                }
            }
        }

     fun saveandupdate() {


         val title = binding.notetitle.text.toString()
            val content = binding.notetype.text.toString()

            val intent = Intent(this, MainActivity::class.java)

            if (title.isEmpty() && content.isEmpty()) {
                Toast.makeText(this, "Nothing to save ", Toast.LENGTH_SHORT).show()
            }

            else if (id == -1) {
                val dataclass = Note_DataClass(
                    title = title,
                    content = content
                )
                lifecycleScope.launch {
                    database.notedao().insert(dataclass)
                }
                setResult(RESULT_OK, intent)
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

            }
            else {
                val dataclass = Note_DataClass(
                    id = id,
                    title = title,
                    content = content
                )
                lifecycleScope.launch {
                 database.notedao().update(dataclass)
                }
                intent.putExtra("id", dataclass.id)
                setResult(1, intent)

                  Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()

            }


            finish()
        }

        binding.navigation.setOnClickListener {

            saveandupdate()
            super.onBackPressed()
        }


        binding.savebutton.setOnClickListener {

            saveandupdate()


        }

    }





    override fun onBackPressed() {
//        saveandupdate()

        super.onBackPressed()
    }






}