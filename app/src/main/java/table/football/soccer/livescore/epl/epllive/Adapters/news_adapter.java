package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.news_model;
import table.football.soccer.livescore.epl.epllive.news_display_activity;

/**
 * Created by Ran on 15-Aug-18.
 */

public class news_adapter extends  RecyclerView.Adapter<news_adapter.Viewholder>  {

    List<news_model> newsModelList;
    Context context;

    public news_adapter(List<news_model> newsModelList, Context context) {
        this.newsModelList = newsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_news,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.headTextview.setText(newsModelList.get(position).getHeadText());
        String imageThumbLinke = newsModelList.get(position).getThumburl();
        if (!imageThumbLinke.isEmpty()){
            Picasso.with(context).load(imageThumbLinke).into(holder.newsImage);
            /*


            Picasso.get().load(imageThumbLinke).into(holder.newsImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e);
                }
            });*/
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.getContext().startActivity(i);
                Intent i = new Intent(context,news_display_activity.class);
                 i.putExtra("News_Link",newsModelList.get(position).getNewsUrl());
                  context.startActivity(i);

            }
        });






    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView headTextview;
        ImageView newsImage;
        public Viewholder(View itemView) {
            super(itemView);

            headTextview = itemView.findViewById(R.id.text_news);
            newsImage = itemView.findViewById(R.id.thumb_news);


        }
    }


}
