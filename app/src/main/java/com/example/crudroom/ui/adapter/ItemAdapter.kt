package com.example.crudroom.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.crudroom.data.entities.Student
import com.example.crudroom.databinding.StudentItemViewBinding
import com.example.crudroom.ui.fragments.HomeFragmentDirections

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var studentList: List<Student> = ArrayList()

    inner class ViewHolder(val binding: StudentItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = StudentItemViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(studentList[position]) {


                binding.itemName.text = this.name
                binding.itemNum.text = this.mobileNum
                binding.bookName.text = this.bookName
                holder.itemView.setOnClickListener {
                    val action = HomeFragmentDirections.addData()
                    action.data = this
                    it.findNavController().navigate(action)
                }


            }
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun addData(list: List<Student>) {
        studentList = list
        notifyDataSetChanged()
    }

}