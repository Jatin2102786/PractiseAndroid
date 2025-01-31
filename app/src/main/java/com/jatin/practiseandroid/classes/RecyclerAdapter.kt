package com.jatin.practiseandroid.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jatin.practiseandroid.R

class RecyclerAdapter(val list: ArrayList<Employee>,val onClick: onClick): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {





    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {

        var name : TextView = view.findViewById(R.id.name_tv)
        var surname : TextView = view.findViewById(R.id.surname_tv)
        val update: Button = view.findViewById(R.id.btn_update)
        val delete: Button = view.findViewById(R.id.btn_delete)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            name.text = list[position].name
            surname.text = list[position].surName

            update.setOnClickListener {
                onClick.update(position)
            }

            delete.setOnClickListener {
                onClick.delete(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
interface onClick{
    fun update(position: Int)
    fun delete(position: Int)
}