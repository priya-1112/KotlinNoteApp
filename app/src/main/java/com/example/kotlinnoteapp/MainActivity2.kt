package com.example.kotlinnoteapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

            GlobalScope.launch {
                val note = database.notedao().getnotebyid(id)
                if (note != null) {
                    binding.notetitle.setText(note.title)
                    binding.notetype.setText(note.content)
                }
            }

        }

        binding.savebutton.setOnClickListener {

            val title = binding.notetitle.text.toString()
            val content = binding.notetype.text.toString()

            val dataclass = Note_DataClass(
                title = title,
                content = content
            )

            if (id != -1 && title.isNotEmpty() && content.isNotEmpty()) {
                val dataclass=Note_DataClass(
                    id = id
                )
                val intent = Intent()

                    intent.putExtra("id", dataclass.id)
                    intent.putExtra("title", dataclass.title)
                    intent.putExtra("content", dataclass.content)

                setResult(RESULT_OK, intent)
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                finish()

            }
            else if (id != -1 && title.isNotEmpty() && content.isEmpty()) {
                val dataclass =Note_DataClass(
                    id = id
                )

                intent.putExtra("id", dataclass.id)
                intent.putExtra("title", dataclass.title)

                setResult(RESULT_OK, intent)
                finish()

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()

            }
            else if (id != -1 && title.isEmpty() && content.isNotEmpty()) {
                val dataclass =Note_DataClass(
                    id = id
                )

                intent.putExtra("id", dataclass.id)
                intent.putExtra("content", dataclass.content)

                setResult(RESULT_OK, intent)
                finish()
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()

            } else if(id == -1 && title.isEmpty() && content.isEmpty()){

                Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show()

                finish()
            }
            else if ( id != -1 && title.isEmpty() && content.isEmpty()){
                Toast.makeText(this, "Cannot save empty data", Toast.LENGTH_SHORT).show()

            }

            else if (id == -1 && title.isNotEmpty() && content.isNotEmpty()){

                intent.putExtra("title", dataclass.title)
                intent.putExtra("content", dataclass.content)

                setResult(1, intent)
                finish()

                Toast.makeText(this, "Saved Note", Toast.LENGTH_SHORT).show()

            }
            else if (id == -1 && title.isNotEmpty()){

                intent.putExtra("title", dataclass.title)
                setResult(1, intent)
                finish()

                Toast.makeText(this, "Saved Note", Toast.LENGTH_SHORT).show()


            }
            else if ( id ==-1 && content.isNotEmpty()){

                intent.putExtra("content", dataclass.content)

                setResult(1, intent)
                finish()

                Toast.makeText(this, "Saved Note", Toast.LENGTH_SHORT).show()

            }









        }

    }
}