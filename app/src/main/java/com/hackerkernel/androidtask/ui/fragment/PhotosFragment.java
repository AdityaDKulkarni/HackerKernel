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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.adapter.PhotosAdapter;
import com.hackerkernel.androidtask.api.RetrofitConfig;
import com.hackerkernel.androidtask.model.Photo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Photo> photos;
    private RecyclerView rvPhotos;
    private PhotosAdapter photosAdapter;

    public PhotosFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photos = new ArrayList<>();
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        rvPhotos.setAdapter(photosAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPhotos();
            }
        });

        getPhotos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_photos);
        rvPhotos = view.findViewById(R.id.rv_photos);
        rvPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        return view;
    }

    private void getPhotos() {
        swipeRefreshLayout.setRefreshing(true);
        photos.clear();
        Call<ArrayList<Photo>> call = RetrofitConfig.photosConfig().getPhotos();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                try {
                    for (int i = 0; i < response.body().size(); i++) {
                        Photo photo = new Photo(response.body().get(i).getAlbumId(), response.body().get(i).getId(),
                                response.body().get(i).getTitle(), response.body().get(i).getUrl(), response.body().get(i).getThumbnailUrl());
                        photos.add(photo);
                        photosAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {
                t.printStackTrace();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
            }
        });
    }
}
