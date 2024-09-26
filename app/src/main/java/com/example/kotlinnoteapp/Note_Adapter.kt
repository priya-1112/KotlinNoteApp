package com.example.kotlinnoteapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Note_Adapter(
    private val context: Context,
    val Notedatacalss: ArrayList<Note_DataClass>
) : RecyclerView.Adapter<Note_Adapter.Viewholder>() {

    val database: Note_Database = Note_Database.getdatabase(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return Viewholder(view)

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val note = Notedatacalss[position]
        holder.notetitext.text = note.title
        holder.notetytext.text = note.content

        holder.itemView.setOnClickListener{

            val intents = Intent(context, MainActivity2::class.java)
            intents.putExtra("ID", note.id)
            context.startActivity(intents)

        }

        holder.vert.setOnClickListener{ view ->

            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.vert_delte, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener{ item : MenuItem ->

                 val position = holder.adapterPosition
                when(item.itemId){
                    R.id.vertdelete->{
                        GlobalScope.launch {
                            database.notedao().delete(note)
                        }
                        Notedatacalss.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,Notedatacalss.size)
                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                        true
                    }


                    else -> false
                }
            }

            popupMenu.show()

        }
    }

    override fun getItemCount(): Int =

        Notedatacalss.size

        class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notetitext: TextView = itemView.findViewById(R.id.notetittext)
        val notetytext: TextView = itemView.findViewById(R.id.notetytext)
        val vert : View= itemView.findViewById(R.id.vert)

    }

}