package com.example.crudroom.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudroom.R
import com.example.crudroom.data.entities.Student
import com.example.crudroom.data.viewmodel.StudentViewModel
import com.example.crudroom.databinding.FragmentHomeBinding
import com.example.crudroom.ui.adapter.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: StudentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setUpView()
        setUpObserver()
    }

    private fun setUpView() {

        binding.buttonAdd.setOnClickListener {

            val action = HomeFragmentDirections.addData()
            findNavController().navigate(action)

        }

        itemAdapter = ItemAdapter()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }

    }

    private fun setUpObserver() {

        viewModel.listItemsLiveData.observe(viewLifecycleOwner) { response ->

            if (response.isEmpty()) {
                binding.recyclerView.isVisible = false
                binding.noDataText.isVisible = true
            } else {
                binding.recyclerView.isVisible = true
                binding.noDataText.isVisible = false
                itemAdapter.addData(response as ArrayList<Student>)

            }

        }

    }

    private fun deleteAllEntry() {

        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot delete all entries")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAll()
                itemAdapter.notifyDataSetChanged()
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_delete -> deleteAllEntry()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}