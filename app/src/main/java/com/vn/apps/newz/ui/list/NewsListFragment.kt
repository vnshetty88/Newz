package com.vn.apps.newz.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vn.apps.newz.R
import com.vn.apps.newz.databinding.FragmentNewsListBinding
import com.vn.apps.newz.utils.Resource
import com.vn.apps.newz.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(), NewsListAdapter.NewsItemListener {

    private var binding: FragmentNewsListBinding by autoCleared()
    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = NewsListAdapter(this)
        binding.rvNewsList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.topHeadLines.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onSeeFullNewsButtonClicked(url: String, title: String) {
        findNavController().navigate(
            R.id.action_newsListFragment_to_newsDetailFragment,
            bundleOf("url" to url, "title" to title)
        )
    }
}
