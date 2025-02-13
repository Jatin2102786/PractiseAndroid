package com.jatin.practiseandroid.RoomDatabase.foreign_key

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.jatin.practiseandroid.R

class NotesRecycler(val notes: ArrayList<Department>): RecyclerView.Adapter<NotesRecycler.ViewHolder>() {
    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {


        val notesTitle = view.findViewById<TextView>(R.id.note)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            notesTitle.text = notes[position].departmentName
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}