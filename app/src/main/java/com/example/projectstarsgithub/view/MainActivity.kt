package com.example.projectstarsgithub.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectstarsgithub.R
import com.example.projectstarsgithub.costants.Constants
import com.example.projectstarsgithub.databinding.ActivityMainBinding
import com.example.projectstarsgithub.models.GitHubRepositoriesInfoModel
import com.example.projectstarsgithub.view.adapter.CustomAdapter
import com.example.projectstarsgithub.view.viewmodel.GitHubRepositoriesViewModel
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
  private lateinit var viewBinding: ActivityMainBinding
  private val mCompositeDisposable = CompositeDisposable()
  private val viewModel = GitHubRepositoriesViewModel(this)
  private var mAdapter: CustomAdapter? = null
  private var page = 1
  private var loading = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)
    initView()
  }

  private fun initView() {
    setupRecycler()
    setupSwipeRefresh()
    updateData(true)
    viewBinding.listRepositories.scrollToPosition(0)
  }

  private fun setupRecycler() {
    val layoutManager = LinearLayoutManager(this)
    viewBinding.listRepositories.layoutManager = layoutManager

    mAdapter = CustomAdapter(ArrayList(0))
    viewBinding.listRepositories.adapter = mAdapter

    viewBinding.listRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!loading) {
          if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
          }
        }
      }
    })
  }

  private fun setupSwipeRefresh() {
    viewBinding.swipeRefresh.setOnRefreshListener {
      clearData()
    }
  }

  private fun clearData() {
    mCompositeDisposable.add(viewModel.clearData().subscribe {
      mAdapter?.clearList()
      page = 1
      updateData(true)
    })
  }

  private fun onScrolledToBottom() {
    if (viewModel.dataInfo != null) {
      page = viewModel.dataInfo?.lastPage?.plus(1) ?: page
    } else {
      page++
    }
    updateData(true)
  }

  private fun updateDataInfo() {
    if (viewModel.dataInfo != null) {
      viewBinding.textLastUpdate.visibility = View.VISIBLE
      viewBinding.textLastUpdate.text =
        getString(R.string.swipe_down_to_update, viewModel.dataInfo?.initData)
    }
  }

  private fun updateData(callRepository: Boolean) {
    mCompositeDisposable.addAll(
      viewModel.getDataInfo()
        .subscribe({
          updateDataInfo()

          if (callRepository) {
            getRepositories()
          }
        }, {
          val message = it.message.toString()
          Log.e(Constants.REPOSITORIES_VIEW, message)
          if (message.contains("empty")) {
            page = 1
            getRepositories()
          }
        })
    )
  }

  private fun getRepositories() {
    loading = true
    mCompositeDisposable.clear()
    mAdapter?.updateList(null)

    viewBinding.listRepositories.scrollToPosition(mAdapter?.itemCount!!)

    mCompositeDisposable.add(
      viewModel.searchGitHubRepositories(page)
        .subscribe({
          mAdapter?.updateList(it)
          loadingFinish()
        }, {
          loadingFinish()
          this.page--
          val message = it.message.toString()
          Log.e(Constants.REPOSITORIES_VIEW, message)
          showToast(message)
        })
    )
  }

  private fun loadingFinish() {
    loading = false
    mAdapter?.removeLoading()
    viewBinding.swipeRefresh.isRefreshing = false

    updateData(false)
  }

  private fun showToast(message: String) {
    if (message.contains("403") || message.contains("401")) {
      Toast.makeText(
        this,
        Constants.QUERY_LIMIT_EXCEEDED,
        Toast.LENGTH_LONG
      ).show()

      mAdapter?.removeLoading()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
    super.onSaveInstanceState(outState)
    Log.i("MyTag", "onSaveInstanceState")

    val pair = Pair(Constants.LIST_REPOSITORIES, viewModel.list)

    outState.putBundle(Constants.LIST_REPOSITORIES, bundleOf(pair))
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    Log.i("MyTag", "onRestoreInstanceState")

    val bundle: Bundle? = savedInstanceState.getBundle(Constants.LIST_REPOSITORIES)
    viewModel.list = bundle?.get(Constants.LIST_REPOSITORIES) as List<GitHubRepositoriesInfoModel>
  }

  override fun onDestroy() {
    super.onDestroy()
    mCompositeDisposable.dispose()
  }
}