package com.raj.takehome.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raj.takehome.R
import com.raj.takehome.utils.Constants.GIT_ID
import com.raj.takehome.databinding.ListItemLayoutBinding
import com.raj.takehome.datamodel.UserRepos

/**
 * Custom Adapter to Show all the Git Repos
 */
class GitRepoAdapter : RecyclerView.Adapter<GitRepoAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<UserRepos>() {
        override fun areItemsTheSame(oldItem: UserRepos, newItem: UserRepos): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserRepos, newItem: UserRepos): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var userRepos: List<UserRepos>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repo = userRepos[position]
        holder.binding.apply {
            gitName.text = repo.name
            gitDesc.text = repo.description
        }
        holder.binding.gitCard.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundleOf(GIT_ID to repo.id)
            )
        }
    }

    override fun getItemCount() = userRepos.size

}