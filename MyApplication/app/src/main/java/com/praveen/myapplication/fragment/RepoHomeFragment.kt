package com.praveen.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.praveen.myapplication.MainRepoViewModel
import com.praveen.myapplication.R
import com.praveen.myapplication.adapter.RepoListAdapter
import com.praveen.myapplication.adapter.RepoLoadStateAdapter
import com.praveen.myapplication.databinding.FragmentRepoHomeBinding
import com.praveen.myapplication.service.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepoHomeFragment : Fragment(), RepoListAdapter.RepoItemClick {

    private lateinit var binding: FragmentRepoHomeBinding
    private val mainRepoViewModel: MainRepoViewModel by viewModel()
    private lateinit var repoListAdapter: RepoListAdapter
    private var mCallBack: NavigateToDetailFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo_home, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarBtn.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        setRepoAdapter()
    }

    private fun setRepoAdapter() {
        repoListAdapter = RepoListAdapter(this)
        binding.rvRepo.setHasFixedSize(true)
        binding.rvRepo.adapter = repoListAdapter

        binding.rvRepo.adapter = repoListAdapter.withLoadStateHeaderAndFooter(
            header = RepoLoadStateAdapter { repoListAdapter.retry() },
            footer = RepoLoadStateAdapter { repoListAdapter.retry() }
        )

        lifecycleScope.launch {

            mainRepoViewModel.repoList.collectLatest {
                delay(1500)
                binding.progress.visibility = View.GONE
                repoListAdapter.submitData(it)
            }
        }
    }

    override fun onRepoItemClick(userComments: String, position: Int) {
        try {
            val bundle = Bundle()
            if (userComments.isNotEmpty()) {
                bundle.putString(Constants.USER_COMMENTS, userComments)
                bundle.putInt(Constants.USER_COMMENTS_POSITION, position)
            }
            val detailedRepoFragment = DetailedRepoFragment()
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            detailedRepoFragment.arguments = bundle
            fragmentTransaction?.replace(R.id.container, detailedRepoFragment)
            fragmentTransaction?.addToBackStack("")
            fragmentTransaction?.commit()
            (activity?.findViewById<View>(R.id.nav_bottom) as BottomNavigationView).selectedItemId =
                R.id.navigation_detail_repo

            /*  if (findNavController().currentDestination?.id == R.id.navigation_home) {
                  findNavController().navigate(R.id.navigation_detail_repo,bundle)
              }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface NavigateToDetailFragment {
        fun onNavigationToDetailFragment()
    }
}

