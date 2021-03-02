package com.example.loginapp.music.albums.view

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
import com.example.loginapp.music.album.view.AlbumDetailsFragment
import com.example.loginapp.music.album.domain.AlbumEntity
import com.example.loginapp.music.albums.dto.AlbumsPreviewResponse
import com.example.loginapp.common.api.ApiTools
import com.example.loginapp.common.LoginAppApplication
import com.example.loginapp.common.view.SingleFragmentActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AlbumsPreviewFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private val albumsPreviewAdapter: AlbumsPreviewAdapter =
        AlbumsPreviewAdapter(onItemClicked = { album ->
            (activity as SingleFragmentActivity).openFragment(
                fragment = AlbumDetailsFragment.newInstance(album)
            )
        })

    private lateinit var refresher: SwipeRefreshLayout

    companion object {
        fun newInstance(): AlbumsPreviewFragment {
            return AlbumsPreviewFragment()
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
            .map {response -> mapAlbumsToEntity(response) }
            .doOnSuccess { albumsEntity ->
                getMusicDao()?.insertAlbums(albumsEntity)
            }
            .onErrorReturn {throwable ->
                if (ApiTools.NETWORK_EXCEPTIONS.contains(throwable::class)){
                    return@onErrorReturn getMusicDao()?.getAlbums()
                }else return@onErrorReturn null
            }
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


    private fun getMusicDao() =  (activity?.application as? LoginAppApplication)?.dataBase?.musicDao

    private fun mapAlbumsToEntity(albumsPreview: AlbumsPreviewResponse): List<AlbumEntity> {
        return albumsPreview.data.map { album ->
            return@map AlbumEntity(
                id = album.id,
                name = album.name,
                releaseDate = album.releaseDate
            )
        }
    }
}
