package com.evgeniykim.mvvmdaggerretrofitrxandroid.ui.post

import android.database.Observable
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.evgeniykim.mvvmdaggerretrofitrxandroid.R
import com.evgeniykim.mvvmdaggerretrofitrxandroid.base.BaseViewModel
import com.evgeniykim.mvvmdaggerretrofitrxandroid.model.Post
import com.evgeniykim.mvvmdaggerretrofitrxandroid.model.PostDao
import com.evgeniykim.mvvmdaggerretrofitrxandroid.network.PostApi
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao): BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    val postListAdapter: PostListAdapter = PostListAdapter()

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {
        loadPosts()
    }

    private fun loadPosts() {
        subscription = io.reactivex.Observable.fromCallable { postDao.all }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty())
                    postApi.getPosts().concatMap {
                        apiPostList -> postDao.insertAll(*apiPostList.toTypedArray())
                        io.reactivex.Observable.just(apiPostList)
                    }
                else
                    io.reactivex.Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }






}