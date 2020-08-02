package com.hackerkernel.androidtask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.model.Article;
import com.hackerkernel.androidtask.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Article> media;
    private Context context;

    public MediaAdapter(ArrayList<Article> media, Context context) {
        this.media = media;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.ARTICLE_VIEW_TYPE) {
            return new ArticleViewHoler(LayoutInflater.from(context).inflate(R.layout.article_row_layout, parent, false));
        }else{
            return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_row_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Constants.ARTICLE_VIEW_TYPE) {
            ((ArticleViewHoler) holder).bindView(position);
        } else {
            ((VideoViewHolder) holder).bindView(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (media.get(position).getDuration() == null) {
            return Constants.ARTICLE_VIEW_TYPE;
        } else {
            return Constants.VIDEO_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return media.size();
    }

    class ArticleViewHoler extends RecyclerView.ViewHolder {

        private ImageView imgThumbnail;
        private ImageButton imgBtnFav, imgBtnPremium;
        private TextView tvBody;

        public ArticleViewHoler(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.img_media);
            imgBtnFav = itemView.findViewById(R.id.img_fav);
            imgBtnPremium = itemView.findViewById(R.id.img_premium);
            tvBody = itemView.findViewById(R.id.tv_media_body);
        }

        void bindView(int position) {

            Picasso.get()
                    .load(media.get(position).getThumbnail())
                    .into(imgThumbnail);

            tvBody.setText(media.get(position).getBody());

            if (media.get(position).isFav()) {
                imgBtnFav.setImageResource(R.drawable.ic_fav_star_enabled);
            } else {
                imgBtnFav.setImageResource(R.drawable.ic_fav_star_disabled);
            }

            if (media.get(position).isPremium()) {
                imgBtnPremium.setVisibility(View.VISIBLE);
                imgBtnPremium.setImageResource(R.drawable.ic_premium);
            } else {
                imgBtnPremium.setVisibility(View.GONE);
            }
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgThumbnail;
        private ImageButton imgBtnFav;
        private TextView tvDuration;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDuration = itemView.findViewById(R.id.tv_media_duration);
            imgThumbnail = itemView.findViewById(R.id.img_media);
            imgBtnFav = itemView.findViewById(R.id.img_fav);
        }

        void bindView(int position){
            Picasso.get()
                    .load(media.get(position).getThumbnail())
                    .into(imgThumbnail);

            tvDuration.setText(media.get(position).getDuration());

            if (media.get(position).isFav()) {
                imgBtnFav.setImageResource(R.drawable.ic_fav_star_enabled);
            } else {
                imgBtnFav.setImageResource(R.drawable.ic_fav_star_disabled);
            }

        }
    }
}
