package com.example.loginapp.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.loginapp.ApiUtils
import com.example.loginapp.R
import com.example.loginapp.model.AlbumDetailsResponse
import com.example.loginapp.model.AlbumsPreview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class AlbumDetailsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var album: AlbumsPreview

    private val songsAdapter: SongsAdapter = SongsAdapter()

//    private val songsAdapter: SongsAdapter = SongsAdapter(onItemClicked = { song ->
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.fragmentContainer, AlbumDetailsFragment.newInstance(song))
//            .addToBackStack(AlbumDetailsFragment::class.java.simpleName)
//            .commit()
//    })

    companion object {
        private const val ALBUM_KEY = "ALBUM_KEY"

        fun newInstance(newInstanceAlbum: AlbumsPreview): AlbumDetailsFragment {
            val args = Bundle()
            val fragment = AlbumDetailsFragment()
            args.putSerializable(ALBUM_KEY, newInstanceAlbum)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler = view.findViewById(R.id.recycler)
        refresher = view.findViewById(R.id.refresher)
        refresher.setOnRefreshListener(this)
        errorView = view.findViewById(R.id.errorView)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        album = arguments!!.getSerializable(ALBUM_KEY) as AlbumsPreview // ARE YOU SURE ABOUT THAT?

        activity!!.title = album.name
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = songsAdapter
        onRefresh()
    }

    override fun onRefresh() {
        refresher.post {
            refresher.isRefreshing = true
            getAlbums()
        }
    }

    private fun getAlbums() {
        ApiUtils.getApiService().getAlbumDetails(album.id)
            .enqueue(object : Callback<AlbumDetailsResponse> {

                override fun onResponse(
                    call: Call<AlbumDetailsResponse>,
                    response: Response<AlbumDetailsResponse>
                ) {
                    try {
                        errorView.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                        songsAdapter.addData(response.body()!!.data.songs,true)
                    } catch (e: Exception) {
                        errorView.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                        Log.e(tag, "ERROR: ${e.localizedMessage}")
                    }
                    refresher.isRefreshing = false
                }

                override fun onFailure(call: Call<AlbumDetailsResponse>, t: Throwable) {
                    Log.e(tag, "error: ${t.message}")

                    errorView.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                    refresher.isRefreshing = false
                }
            })
    }
}
