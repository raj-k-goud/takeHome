package com.raj.takehome.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.raj.takehome.R
import com.raj.takehome.utils.Constants.GIT_ID
import com.raj.takehome.databinding.FragmentDetailsBinding
import com.raj.takehome.viewmodel.UserViewModel

/**
 * A Git Details Fragment -subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var gitId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
         gitId = arguments?.getLong(GIT_ID)?: 0
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewSecond.text = gitId.toString()
        val details = viewModel.getGitDetailsbyId(gitId)
        details?.let {it.id
            binding.textviewSecond.text = getString(R.string.details, it.name, it.description, it.updated_at, it.stargazers_count, it.forks )
        }
        if(viewModel.isTotalForks()) binding.badge.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}