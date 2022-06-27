package com.example.projectstarsgithub.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.projectstarsgithub.R
import com.example.projectstarsgithub.models.GitHubRepositoriesInfoModel
import com.example.projectstarsgithub.models.OwnerModel

class CustomAdapter(private val dataSet: MutableList<GitHubRepositoriesInfoModel?>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  companion object {
    private const val VIEW_TYPE_ITEM = 0
    private const val VIEW_TYPE_LOADING = 1
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val repositoryName: TextView
    val position: TextView
    val ownerName: TextView
    val stargazersCount: TextView
    val forkCount: TextView
    val imgAvatar: ImageView
    var context: Context

    init {
      context = view.context
      repositoryName = view.findViewById(R.id.repositoryName)
      position = view.findViewById(R.id.position)
      ownerName = view.findViewById(R.id.ownerName)
      stargazersCount = view.findViewById(R.id.stargazersCount)
      forkCount = view.findViewById(R.id.forkCount)
      imgAvatar = view.findViewById(R.id.imgAvatar)
    }
  }

  class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var progressBar: ProgressBar

    init {
      progressBar = itemView.findViewById(R.id.progressBar);
    }
  }

  fun updateList(item: List<GitHubRepositoriesInfoModel>?) {
    insertItem(item)
  }

  fun clearList() {
    dataSet.clear()
    notifyDataSetChanged()
  }

  private fun insertItem(item: List<GitHubRepositoriesInfoModel>?) {
    if (item == null) {
      dataSet.add(itemCount, null)
    } else {
      dataSet.addAll(itemCount, item)
    }
  }

  fun removeLoading() {
    if (dataSet.contains(null)) {
      dataSet.remove(null)
      notifyDataSetChanged()
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (dataSet[position] == null) {
      VIEW_TYPE_LOADING
    } else {
      VIEW_TYPE_ITEM
    }
  }

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    lateinit var view: View

    if (viewType == VIEW_TYPE_ITEM) {
      view = LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.item_list, viewGroup, false)

      return ViewHolder(view)
    } else {
      view = LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.item_loading, viewGroup, false)

      return LoadingViewHolder(view)
    }
  }

  fun populateItemRows(viewHolder: ViewHolder, model: GitHubRepositoriesInfoModel?, position: Int) {
    viewHolder.repositoryName.text = model?.name
    viewHolder.ownerName.text = model?.owner?.login
    viewHolder.stargazersCount.text = model?.stargazersCount.toString()
    viewHolder.forkCount.text = model?.forksCount.toString()
    viewHolder.position.text = position.plus(1).toString()
    loadImage(model?.owner, viewHolder.imgAvatar, viewHolder.context)
  }

  private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
    //ProgressBar would be displayed
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
    if (viewHolder is ViewHolder) {
      populateItemRows(viewHolder, dataSet[position], position)
    } else if (viewHolder is LoadingViewHolder) {
      showLoadingView(viewHolder, position)
    }
  }

  override fun getItemCount() = dataSet.size

  private fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
  }

  private fun loadImage(owner: OwnerModel?, resourceLayout: ImageView, context: Context) {
    val circularProgressDrawable = getCircularProgressDrawable(context)
    if (owner?.imgBlob != null) {
      Glide
        .with(context)
        .load(owner.imgBlob)
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .into(resourceLayout)

    } else {
      Glide
        .with(context)
        .load(owner?.avatarUrl)
        .centerCrop()
        .placeholder(circularProgressDrawable)
        .into(resourceLayout)
    }

  }

}
