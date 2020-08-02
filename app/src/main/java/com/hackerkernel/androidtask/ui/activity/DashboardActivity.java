package com.hackerkernel.androidtask.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.adapter.MediaAdapter;
import com.hackerkernel.androidtask.model.Article;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private Toolbar toolbar;
    private ArrayList<Article> articles, videos;
    private RecyclerView rvArticles, rvVideos;
    private MediaAdapter articlesAdapter, videosAdapter;

    private String[] images = {
      "https://imagesvc.meredithcorp.io/v3/mm/image?q=85&c=sc&poi=face&w=1200&h=628&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F35%2F2019%2F04%2F16045736%2Fbenefits-yoga-fb1.jpg",
      "https://www.t-nation.com/system/publishing/articles/10005571/original/10-Rules-for-Building-Muscle-Without-Getting-Fat.jpg?1509661617",
      "https://media.npr.org/assets/img/2014/01/07/mindfulness_wide-b20c3525971d5796eba9ad993463fffe8faf2bcb.jpg?s=1400"
    };

    private String[] videoImages = {
            "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/reference_guide/the_health_benefits_of_yoga_ref_guide/650x350_the_health_benefits_of_yoga_ref_guide.jpg",
            "https://www.trainmag.com/wp-content/uploads/2017/04/wsi-imageoptim-Robert-Timms-Muscle-building-Workout-Plan.jpg",
            "https://cdn.pixabay.com/photo/2017/04/08/22/26/meditation-2214532__340.jpg"
    };

    private String[] articleBody = {
            "5 minutes of daily yoga, lifetime of strength",
            "Muscle-up your way to your strong self",
            "Need of meditaion"
    };

    private String[] durations = {
            "01:25:00",
            "01:25:00",
            "01:25:00"
    };

    private boolean[] favoriteArticles = {
            false,
            true,
            true
    };

    private boolean[] premiumArticles = {
            true,
            true,
            false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_dots));

        rvArticles = findViewById(R.id.rv_featured_articles);
        rvVideos = findViewById(R.id.rv_featured_videos);
        rvArticles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvVideos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setupArticles();
        setupVideos();
    }

    private void setupArticles(){
        articles = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Article article = new Article(articleBody[i], images[i],
                    favoriteArticles[i], premiumArticles[i], null);
            articles.add(article);
        }
        articlesAdapter = new MediaAdapter(articles, this);
        rvArticles.setAdapter(articlesAdapter);
    }

    private void setupVideos(){
        videos = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Article video = new Article(null, videoImages[i],
                    favoriteArticles[i], premiumArticles[i], durations[i]);
            videos.add(video);
        }
        videosAdapter = new MediaAdapter(videos, this);
        rvVideos.setAdapter(videosAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}