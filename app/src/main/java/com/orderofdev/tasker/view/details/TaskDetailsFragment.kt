package com.orderofdev.tasker.view.details

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.orderofdev.tasker.R
import com.orderofdev.tasker.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {
    lateinit var viewModel: TaskDetailsViewModel
    lateinit var binding: FragmentTaskDetailsBinding

    companion object {
        const val TAG = "TaskDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application =
            this.activity!!.application ?: inflater.context.applicationContext as Application
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        val provider = ViewModelProvider(this, factory)
        viewModel = provider.get(TaskDetailsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        binding.mainVM = viewModel
        binding.executePendingBindings()
        return binding.root
    }
}