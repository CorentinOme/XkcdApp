package com.dev.ome.corentin.xkcdapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;


public class ComicCardActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView safeTitle;
    private TextView year;
    private TextView transcript;
    private TextView url;
    private byte[] img;
    private String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_card);

        this.imageView = findViewById(R.id.comicView);
        this.safeTitle = findViewById(R.id.safeTitle);
        this.year = findViewById(R.id.year);
        this.transcript = findViewById(R.id.transcript);
        this.url = findViewById(R.id.url);

        this.urlString = getIntent().getStringExtra("url");
        this.img = getIntent().getByteArrayExtra("image");

        this.imageView.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(img)));
        this.safeTitle.setText(getIntent().getStringExtra("title"));
        this.year.setText(getIntent().getStringExtra("year"));
        this.transcript.setText(getIntent().getStringExtra("transcript"));
        this.url.setText(this.urlString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comic_card, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intent = new Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, this.urlString);

        myShareActionProvider.setShareIntent(intent);

        return true;
    }

}
