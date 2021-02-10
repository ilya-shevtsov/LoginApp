package com.example.loginapp.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.loginapp.ApiUtils
import com.example.loginapp.R
import com.example.loginapp.model.AlbumsPreviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recycler: RecyclerView
    private lateinit var errorView: View
    private val albumsAdapter: AlbumsAdapter = AlbumsAdapter(onItemClicked = {item ->

    })
    private lateinit var refresher: SwipeRefreshLayout

    companion object {
        fun newInstance(): AlbumsFragment {
            return AlbumsFragment()
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
        activity.setTitle()
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

    private fun getAlbums() {
        ApiUtils.getApiService().getAlbumsPreview()
            .enqueue(object : Callback<AlbumsPreviewResponse> {

                override fun onFailure(call: Call<AlbumsPreviewResponse>, t: Throwable?) {
                    errorView.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                    refresher.isRefreshing = false
                }

                override fun onResponse(
                    call: Call<AlbumsPreviewResponse>,
                    response: Response<AlbumsPreviewResponse>
                ) {
                    if (response.isSuccessful) {
                        errorView.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                        albumsAdapter.addData(response.body()!!.data, true)
                    } else {
                        errorView.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                    }
                    refresher.isRefreshing = false
                }
            })
    }
}