package com.dev.ome.corentin.xkcdapp;

import android.arch.paging.PagedListAdapter;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;


public class ComicsAdapter extends PagedListAdapter<Comic, ComicsAdapter.ComicViewHolder> {

    private MainPresenter presenter;

    static DiffUtil.ItemCallback<Comic> diffCallback = new DiffUtil.ItemCallback<Comic>() {

        @Override public boolean areItemsTheSame(Comic oldItem, Comic newItem) {
            return oldItem.getNum() == newItem.getNum();
        }

        @Override public boolean areContentsTheSame(Comic oldItem, Comic newItem) {
            return oldItem.equals(newItem);
        }

    };

    public ComicsAdapter(MainPresenter mainPresenter) {
        super(diffCallback);
        this.presenter = mainPresenter; }

    @Override public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comic_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Comic comic = this.getItem(position);
        if (comic != null) holder.displayComic(comic);
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemName;
        ImageView itemThumbnail;

        public ComicViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemThumbnail = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
        }

        public void displayComic(Comic comic) {
            itemName.setText(comic.getSafe_title());
            if(comic.getImage() != null) {
                itemThumbnail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(comic.getImage())));
            }
        }

        @Override
        public void onClick(View v) {
            presenter.clickItem(getItem(getAdapterPosition()));
        }
    }
}
