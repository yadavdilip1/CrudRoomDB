package com.example.crudroom.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudroom.R
import com.example.crudroom.data.entities.Student
import com.example.crudroom.databinding.FragmentAddDataBinding
import com.example.crudroom.data.viewmodel.StudentViewModel
import com.example.crudroom.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddDataFragment : Fragment() {

    private var _binding: FragmentAddDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by viewModels()
    private var data: Student? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val booksList = resources.getStringArray(R.array.books)

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item, booksList
        )

        adapter.setDropDownViewResource(R.layout.custom_spinner)

        binding.bookSpinner.adapter = adapter

        arguments?.let {
            data = AddDataFragmentArgs.fromBundle(it).data
            binding.edStudentName.setText(data?.name)
            binding.edStudentNum.setText(data?.mobileNum)
            binding.bookSpinner.setSelection(booksList.indexOf(data?.bookName))
            if(data != null)
            {
                binding.saveBtn.text = "UPDATE"
            }
        }

        binding.saveBtn.setOnClickListener {

            val studentName = binding.edStudentName.text.toString().trim()
            val studentNum = binding.edStudentNum.text.toString().trim()

            if (studentName.isEmpty()) {
                binding.edStudentName.error = "Name required"
                binding.edStudentName.requestFocus()
                return@setOnClickListener
            }
            if (studentNum.isEmpty()) {
                binding.edStudentNum.error = "Number required"
                binding.edStudentNum.requestFocus()
                return@setOnClickListener
            }
            if (binding.bookSpinner.selectedItemPosition == 0) {
                Toast.makeText(requireContext(), "Please select book to save", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val txtStudentName = binding.edStudentName.text.toString()
            val txtStudentNum = binding.edStudentNum.text.toString()
            val txtbookName = binding.bookSpinner.selectedItem.toString()

            val mData = Student(txtStudentName, txtStudentNum, txtbookName)

            if (data == null) {

                viewModel.insertItem(mData)
                requireContext().toast("Data Saved")

            } else {
                mData.id = data!!.id
                viewModel.updateItem(mData)
                requireContext().toast("Data Updated")
            }


            val directions = AddDataFragmentDirections.actionSaveData()
            findNavController().navigate(directions)


        }

    }


    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->

                viewModel.deleteItem(data!!)
                val action = AddDataFragmentDirections.actionSaveData()
                findNavController().navigate(action)
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> if (data != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}