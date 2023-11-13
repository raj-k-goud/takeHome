package com.raj.takehome.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raj.takehome.R
import com.raj.takehome.service.ApiResponse
import com.raj.takehome.databinding.FragmentUserReposBinding
import com.raj.takehome.ui.adapter.GitRepoAdapter
import com.raj.takehome.utils.Utility.animateView
import com.raj.takehome.utils.Utility.loadProfileImage
import com.raj.takehome.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A Search Git User and Display few Details  [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class UserReposFragment : Fragment() {

    private var _binding: FragmentUserReposBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels()

    private lateinit var gitRepoAdapter: GitRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserReposBinding.inflate(inflater, container, false)
        gitRepoAdapter = GitRepoAdapter()
        binding.rv.apply {
            adapter = gitRepoAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(), LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInputLayout.button.setOnClickListener {
            val inputText = binding.textInputLayout.userInputTextLayout.editText?.text.toString()
            if (inputText.isNotEmpty()) {
                viewModel.fetchData(inputText)
                clearViews()
            } else {
                Toast.makeText(requireActivity(), getString(R.string.hint), Toast.LENGTH_LONG).show()
            }
        }
        observerRepoDetails()
        observerUserDetails()
    }

    /*
    * View model Observe the  Git Repos details Api status and Update UI
     */
    private fun observerRepoDetails() {
        viewModel.repoDetails.observe(requireActivity()) {
            when (it) {
                is ApiResponse.Success -> {
                    binding.loading.text = ""
                    it.apiResponseData?.let { repos ->
                        gitRepoAdapter.userRepos = repos
                    }
                    gitRepoAdapter.notifyDataSetChanged()
                    animateView(requireActivity(), binding.rv)
                }
                is ApiResponse.Loading -> {
                    binding.loading.text = getString(R.string.loading)
                }
                is ApiResponse.Error -> {
                    binding.loading.text = getString(R.string.error_code, it.msg)
                }
                else -> {}
            }
        }
    }

    /*
   * View model Observe the  Git User details Api response and Update UI State
    */
    private fun observerUserDetails() {
        viewModel.userDetails.observe(requireActivity()) {
            when (it) {
                //Api onSuccess State
                is ApiResponse.Success -> {
                    binding.loading.text = ""
                    binding.textviewFirst.text = it.apiResponseData?.name
                    loadProfileImage(it.apiResponseData?.avatar_url, requireContext(), binding.profilePic)
                    animateView(requireActivity(),binding.textviewFirst)
                }
                //Api Loading State
                is ApiResponse.Loading -> {
                    binding.loading.text = getString(R.string.loading)
                }
                //Api Error State
                is ApiResponse.Error -> {
                    binding.loading.text = getString(R.string.error_code, it.msg)
                }
                else -> {}
            }
        }
    }

    /*
    * clear all the UI views when user search for new Git User
     */
    private fun clearViews() {
        binding.loading.text = ""
        binding.textviewFirst.text = ""
        binding.profilePic.setImageDrawable(null)
        gitRepoAdapter.userRepos = emptyList()
        gitRepoAdapter.notifyItemRangeRemoved(0, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}