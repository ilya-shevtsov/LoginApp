package com.example.loginapp.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.loginapp.R
import com.example.loginapp.albums.AlbumsFragment
import com.example.loginapp.model.AlbumsPreview


class AlbumDetailsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var album: AlbumsPreview

    private val mSongsAdapter = SongsAdapter()
    companion object{
        private const val ALBUM_KEY = "ALBUM_KEY"

        fun newInstance(newInstanceAlbum: AlbumsPreview): AlbumDetailsFragment {
            val args = Bundle()
            args.putSerializable(ALBUM_KEY, newInstanceAlbum)
            val fragment = AlbumDetailsFragment()
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
        activity!!.setTitle(R.string.albums)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = albumsAdapter

        onRefresh()

    }

    override fun onRefresh() {
        refresher.post {
            refresher.isRefreshing = true
            getAlbums()
        }
    }
}
