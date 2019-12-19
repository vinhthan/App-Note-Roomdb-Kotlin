package com.example.appnoteroomdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnoteroomdb.R
import com.example.appnoteroomdb.data.Note
import com.example.appnoteroomdb.data.NoteRoomDatabase
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteListAdapter internal constructor(context: Context, val noteDB: NoteRoomDatabase): RecyclerView.Adapter<NoteListAdapter.ViewHolder>(){

    //private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listNote = emptyList<Note>()

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(listNote: List<Note>){
        this.listNote = listNote
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = listNote[position]

        holder.contentItemView.text = currentNote.content
        holder.deleteItemView.setOnClickListener {
            uiScope.launch {
                //delete currentNote
                noteDB?.noteDao()?.delete(currentNote)

                //get all noted again
                listNote = noteDB?.noteDao()?.getAllNotes()
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val contentItemView = itemView.note_content
        val deleteItemView = itemView.button_delete
    }
}