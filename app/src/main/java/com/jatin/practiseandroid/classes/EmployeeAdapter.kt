package com.jatin.practiseandroid.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.jatin.practiseandroid.R

class EmployeeAdapter(private val employeeList: ArrayList<Employee>, private val itemClick: OnItemClick) : BaseAdapter(){
    override fun getCount(): Int {
        return employeeList.size
    }

    override fun getItem(position: Int): Any {
        return employeeList[position]
    }

    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }

    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_layout,parent,false)


        var name = view.findViewById<TextView>(R.id.name_tv)
        var surName = view.findViewById<TextView>(R.id.surname_tv)

        name.text = employeeList[position].name
        surName.text = employeeList[position].surName

        var update = view.findViewById<Button>(R.id.btn_update)

        var delete = view.findViewById<Button>(R.id.btn_delete)
        update.setOnClickListener {
            itemClick.update(position)
        }

        delete.setOnClickListener {
            itemClick.delete(position)
        }



        return view
    }
}
interface OnItemClick {
    fun update(position: Int)
    fun delete(position: Int)

}