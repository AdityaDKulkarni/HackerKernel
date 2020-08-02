package com.hackerkernel.androidtask.api;

import com.hackerkernel.androidtask.model.LoginModel;
import com.hackerkernel.androidtask.model.Photo;
import com.hackerkernel.androidtask.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    @POST("demo/shree_sai_darshan/public/api/user-login")
    @FormUrlEncoded
    Call<LoginModel> login(@Field("email") String email,
                           @Field("password") String password,
                           @Field("type") String type);

    @GET("photos/")
    Call<ArrayList<Photo>> getPhotos();

    @GET("posts/")
    Call<ArrayList<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int id);
}
