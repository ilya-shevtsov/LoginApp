package com.example.loginapp.music.albumPreview.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.loginapp.*
import com.example.loginapp.music.album.view.AlbumFragment
import com.example.loginapp.common.api.ApiTools
import com.example.loginapp.common.view.SingleFragmentActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AlbumPreviewFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private val albumsPreviewAdapter: AlbumPreviewAdapter =
        AlbumPreviewAdapter(onItemClicked = { album ->
            (activity as SingleFragmentActivity).openFragment(
                fragment = AlbumFragment.newInstance(album)
            )
        })

    private lateinit var refresher: SwipeRefreshLayout

    companion object {
        fun newInstance(): AlbumPreviewFragment {
            return AlbumPreviewFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.fragmentAlbumsPreviewRecycler)
        refresher = view.findViewById(R.id.fragmentAlbumsPreviewRefresher)
        refresher.setOnRefreshListener(this)
        errorView = view.findViewById(R.id.errorView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.setTitle(R.string.albums)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = albumsPreviewAdapter
        onRefresh()
    }

    override fun onRefresh() {
        refresher.post {
            getAlbums()
        }
    }

    private fun getAlbums() {
        ApiTools.getApiService()
            .getAlbumsPreview()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { refresher.isRefreshing = true }
            .doFinally { refresher.isRefreshing = false }
            .subscribeBy(
                onSuccess = { albumsPreviewResponse ->
                    errorView.isVisible = false
                    recycler.isVisible = true
                    albumsPreviewAdapter.addData(albumsPreviewResponse.data, true)
                },
                onError = {
                    errorView.isVisible = true
                    recycler.isVisible = false
                    Log.e("AlbumsFragment", "AlbumsRequestError: ${it.localizedMessage}")
                })
            .disposeOnDestroy(viewLifecycleOwner)
    }

}
