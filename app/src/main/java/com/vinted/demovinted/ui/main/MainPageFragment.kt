package com.vinted.demovinted.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinted.demovinted.R
import com.vinted.demovinted.base.BaseFragment
import com.vinted.demovinted.databinding.FragmentMainPageBinding
import com.vinted.demovinted.ui.main.adapter.ItemAdapter
import com.vinted.demovinted.ui.main.adapter.ItemStateLoadAdapter
import com.vinted.demovinted.utils.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : BaseFragment() {

    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var binding: FragmentMainPageBinding

    private val itemsAdapter by lazy {
        ItemAdapter(
            onItemClicked = {
                findNavController().navigate(
                    MainPageFragmentDirections.actionMainPageFragmentToDetailsPageFragment(it)
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupViews(rootView: View) {
        binding.apply {
            //Configuring custom toolbar
            (requireActivity() as AppCompatActivity).apply {
                setSupportActionBar(toolbar)
                supportActionBar?.title = ""
            }
            setHasOptionsMenu(true)

            //Observing items to affect on recycle view
            viewModel.items.observe(viewLifecycleOwner, {
                itemsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            })

            //Implement retry button
            btnRetry.setSafeOnClickListener { itemsAdapter.retry() }

            //Configure swipeRefreshLayout
            swipeRefreshLayout.setOnRefreshListener { itemsAdapter.refresh() }
            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)

            //Configure load states
            itemsAdapter.addLoadStateListener { loadState ->
                swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
                rvItems.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                //empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    itemsAdapter.itemCount < 1) {
                    rvItems.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }

            //Configure recycle view
            rvItems.apply {
                itemAnimator = null
                layoutManager = LinearLayoutManager(context)
                adapter = itemsAdapter.withLoadStateHeaderAndFooter(
                    header = ItemStateLoadAdapter { itemsAdapter.retry() },
                    footer = ItemStateLoadAdapter { itemsAdapter.retry() }
                )
            }
        }
    }

    //Configure menu for the search action
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        //Seach action
        fun callSearch(query: String) {
            binding.rvItems.scrollToPosition(0)
            viewModel.searchItems(query)
            searchView.clearFocus()
        }

        //Send text changes to the viewmodel
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    callSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    callSearch("")
                }
                return true
            }
        })
    }
}
