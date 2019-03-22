package com.dev.ome.corentin.xkcdapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;
    private ExecutorService pool;
    private ComicsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.presenter = new MainPresenter();
        this.presenter.setView(this);
        this.presenter.setXkcdAPI(new XkcdAPI());

        XkcdDatabase db = XkcdDatabase.getInstance(this);
        this.presenter.setDao(db.getDao());

        this.pool = Executors.newSingleThreadExecutor();

        FloatingActionButton fab = findViewById(R.id.fab);
        MainActivity act = this;
        fab.setOnClickListener(view -> {
            ConnectivityManager cm =
                    (ConnectivityManager)act.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(isConnected){
                pool.submit(() -> presenter.onAdd());
            }else{
                Toast.makeText(act, R.string.internetConnection, Toast.LENGTH_LONG).show();
            }
        });

        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new ComicsAdapter(this.presenter);
        this.presenter.getComicsList().observe(this,
                projects -> adapter.submitList(projects));
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        this.pool = Executors.newSingleThreadExecutor();

       if (id == R.id.action_delete_all) {
           pool.submit(() -> this.presenter.onDeleteAll());
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void log(String message) {
        Log.i("MESSAGE", message);
    }

    @Override
    public void openOnClickItem(Comic comic) {
        Intent intent = new Intent(this, ComicCardActivity.class);
        intent.putExtra("image", comic.getImage());
        intent.putExtra("title", comic.getSafe_title());
        intent.putExtra("year", Integer.toString(comic.getYear()));
        intent.putExtra("transcript", comic.getTranscript());
        intent.putExtra("url", comic.getImg());
        this.pool.submit(() -> startActivity(intent));
    }
}
