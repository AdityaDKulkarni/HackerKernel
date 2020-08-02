package com.hackerkernel.androidtask.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.api.RetrofitConfig;
import com.hackerkernel.androidtask.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> posts;

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle, tvBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_post_title);
            tvBody = itemView.findViewById(R.id.tv_post_body);
        }

        void bindView(int position){
            tvTitle.setText(posts.get(position).getTitle());
            tvBody.setText(posts.get(position).getBody());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage(context.getString(R.string.getting_details));
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Call<Post> postCall = RetrofitConfig.photosConfig().getPost(posts.get(position).getId());
                    postCall.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            progressDialog.dismiss();
                            if(response.code() == 200){
                                try {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle(response.body().getTitle());
                                    builder.setMessage(response.body().getBody());
                                    builder.create().show();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(context, context.getString(R.string.failed_to_fetch_details), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}
