package com.example.loginapp.music.album.view

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
import com.example.loginapp.common.api.ApiTools
import com.example.loginapp.R
import com.example.loginapp.common.LoginAppApplication
import com.example.loginapp.disposeOnDestroy
import com.example.loginapp.music.album.AlbumDtoMapper
import com.example.loginapp.music.album.AlbumEntityMapper
import com.example.loginapp.music.albumPreview.dto.AlbumPreviewDto
import com.example.loginapp.music.song.SongEntityMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AlbumFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var album: AlbumPreviewDto

    private val songsAdapter: SongsAdapter = SongsAdapter()

    companion object {
        private const val ALBUM_KEY = "ALBUM_KEY"

        fun newInstance(newInstanceAlbum: AlbumPreviewDto): AlbumFragment {
            val arguments = Bundle()
            val fragment = AlbumFragment()
            arguments.putSerializable(ALBUM_KEY, newInstanceAlbum)
            fragment.arguments = arguments
            return fragment
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
        album = arguments!!.getSerializable(ALBUM_KEY) as AlbumPreviewDto
        activity!!.title = album.name
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = songsAdapter
        onRefresh()
    }

    override fun onRefresh() {
        refresher.post {
            getAlbums()
        }
    }

    private fun getAlbums() {
        ApiTools.getApiService()
            .getAlbumDetails(album.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> AlbumDtoMapper.mapDtoDomain(response.data) }
            .doOnSuccess { album ->
                val albumEntity = AlbumEntityMapper.mapDomainEntity(album)
                val songEntityList = album.songs.map { song ->
                    SongEntityMapper.mapDomainEntity(song, albumEntity.id)
                }
                getMusicDao()?.insertAlbum(albumEntity)
                getMusicDao()?.insertSongs(songEntityList)
            }
            .onErrorReturn { throwable ->
                if (ApiTools.NETWORK_EXCEPTIONS.contains(throwable::class)) {
                    val albumEntity = getMusicDao()?.getAlbumById(album.id)
                    val songsEntity = getMusicDao()?.getSongsForAlbum(album.id)
                    return@onErrorReturn AlbumEntityMapper.mapEntityDomain(
                        albumEntity!!,
                        songsEntity!!
                    )
                } else return@onErrorReturn null
            }
            .doOnSubscribe { refresher.isRefreshing = true }
            .doFinally { refresher.isRefreshing = false }
            .subscribeBy(
                onSuccess = { album ->
                    errorView.isVisible = false
                    recycler.isVisible = true
                    songsAdapter.addData(album.songs, true)
                },
                onError = {
                    errorView.isVisible = true
                    recycler.isVisible = false
                    Log.e(
                        "AlbumDetailsFragment",
                        "AlbumDetailsRequestError: ${it.localizedMessage}"
                    )
                })
            .disposeOnDestroy(viewLifecycleOwner)
    }

    private fun getMusicDao() = (activity?.application as? LoginAppApplication)?.dataBase?.musicDao

}
