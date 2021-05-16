package com.praveen.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.praveen.myapplication.MainRepoViewModel
import com.praveen.myapplication.R
import com.praveen.myapplication.adapter.DetailListAdapter
import com.praveen.myapplication.adapter.RepoLoadStateAdapter
import com.praveen.myapplication.databinding.FragmentDetailRepoBinding
import com.praveen.myapplication.service.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedRepoFragment : Fragment() {

    private lateinit var binding: FragmentDetailRepoBinding
    private val mainRepoViewModel: MainRepoViewModel by viewModel()
    private lateinit var detailListAdapter: DetailListAdapter
    private var userComments = ""
    private var position = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_repo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            userComments = arguments?.getString(Constants.USER_COMMENTS, "") ?: ""
            position = arguments?.getInt(Constants.USER_COMMENTS_POSITION) ?: 0
        }
        binding.toolbarBtn.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        setDetailRepoAdapter()
    }

    private fun setDetailRepoAdapter() {
        detailListAdapter = DetailListAdapter(userComments, position)
        binding.rvDetailed.setHasFixedSize(true)
        binding.rvDetailed.adapter = detailListAdapter.withLoadStateHeaderAndFooter(
            header = RepoLoadStateAdapter { detailListAdapter.retry() },
            footer = RepoLoadStateAdapter { detailListAdapter.retry() }
        )
        lifecycleScope.launch {
            mainRepoViewModel.repoList.collectLatest {
                delay(1500)
                binding.progress.visibility = View.GONE
                detailListAdapter.submitData(it)
            }
        }

    }
}