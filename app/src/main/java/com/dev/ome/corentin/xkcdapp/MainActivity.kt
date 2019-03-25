package com.dev.ome.corentin.xkcdapp

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), MainView {

    private var presenter: MainPresenter? = null
    private var pool: ExecutorService? = null
    private var adapter: ComicsAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.presenter = MainPresenter()
        this.presenter!!.setView(this)
        this.presenter!!.setXkcdAPI(XkcdAPI())

        val db = XkcdDatabase.getInstance(this)
        this.presenter!!.setDao(db.dao)

        this.pool = Executors.newSingleThreadExecutor()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val act = this
        fab.setOnClickListener { view ->
            val cm = act.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
            if (isConnected) {
                pool!!.submit { presenter!!.onAdd() }
            } else {
                Toast.makeText(act, R.string.internetConnection, Toast.LENGTH_LONG).show()
            }
        }

        this.recyclerView = findViewById(R.id.recyclerView)
        this.recyclerView!!.layoutManager = LinearLayoutManager(this)
        this.adapter = ComicsAdapter(this.presenter!!)
        val nameObserver = Observer<PagedList<Comic>> {  projects ->
            adapter!!.submitList(projects)
        }
        this.presenter!!.comicsList!!.observe(this, nameObserver)
        this.recyclerView!!.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        this.pool = Executors.newSingleThreadExecutor()

        if (id == R.id.action_delete_all) {
            pool!!.submit { this.presenter!!.onDeleteAll() }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun log(message: String) {
        Log.i("MESSAGE", message)
    }

    override fun openOnClickItem(comic: Comic) {
        val intent = Intent(this, ComicCardActivity::class.java)
        intent.putExtra("image", comic.image)
        intent.putExtra("title", comic.safe_title)
        intent.putExtra("year", Integer.toString(comic.year))
        intent.putExtra("transcript", comic.transcript)
        intent.putExtra("url", comic.img)
        this.pool!!.submit { startActivity(intent) }
    }
}
