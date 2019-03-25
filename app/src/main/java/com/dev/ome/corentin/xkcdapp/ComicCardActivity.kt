package com.dev.ome.corentin.xkcdapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

import java.io.ByteArrayInputStream


class ComicCardActivity : AppCompatActivity() {

    private var imageView: ImageView? = null
    private var safeTitle: TextView? = null
    private var year: TextView? = null
    private var transcript: TextView? = null
    private var url: TextView? = null
    private var img: ByteArray? = null
    private var urlString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_card)

        this.imageView = findViewById(R.id.comicView)
        this.safeTitle = findViewById(R.id.safeTitle)
        this.year = findViewById(R.id.year)
        this.transcript = findViewById(R.id.transcript)
        this.url = findViewById(R.id.url)

        this.urlString = intent.getStringExtra("url")
        this.img = intent.getByteArrayExtra("image")

        this.imageView!!.setImageBitmap(BitmapFactory.decodeStream(ByteArrayInputStream(img)))
        this.safeTitle!!.text = intent.getStringExtra("title")
        this.year!!.text = intent.getStringExtra("year")
        this.transcript!!.text = intent.getStringExtra("transcript")
        this.url!!.text = this.urlString
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_comic_card, menu)

        val shareItem = menu.findItem(R.id.menu_item_share)
        val myShareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider

        val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, this.urlString)

        myShareActionProvider.setShareIntent(intent)

        return true
    }

}
