package com.example.loginapp.albumsPreview

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
import com.example.loginapp.albumDetails.AlbumDetailsFragment
import com.example.loginapp.dataBase.AlbumEntity
import com.example.loginapp.modelClasses.AlbumsPreviewResponse
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
        ApiUtils.getApiService()
            .getAlbumsPreview()
            .subscribeOn(Schedulers.io())
            .map {response -> mapAlbumsToEntity(response) }
            .doOnSuccess { albumsEntity ->
                getMusicDao()?.insertAlbums(albumsEntity)
            }
            .onErrorReturn {throwable ->
                if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable::class)){
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


    private fun getMusicDao() =  (activity?.application as? App)?.dataBase?.musicDao

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
