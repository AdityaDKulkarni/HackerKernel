package com.hackerkernel.androidtask.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.adapter.PostsAdapter;
import com.hackerkernel.androidtask.api.RetrofitConfig;
import com.hackerkernel.androidtask.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Post> posts;
    private RecyclerView rvPosts;
    private PostsAdapter postsAdapter;

    public PostsFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(getActivity(), posts);
        rvPosts.setAdapter(postsAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });

        getPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_posts);
        rvPosts = view.findViewById(R.id.rv_posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void getPosts(){
        swipeRefreshLayout.setRefreshing(true);
        posts.clear();
        Call<ArrayList<Post>> call = RetrofitConfig.photosConfig().getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++) {
                        Post post = new Post(response.body().get(i).getUserId(), response.body().get(i).getId(), response.body().get(i).getTitle(), response.body().get(i).getBody());
                        posts.add(post);
                        postsAdapter.notifyDataSetChanged();
                    }
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                t.printStackTrace();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        });
    }
}