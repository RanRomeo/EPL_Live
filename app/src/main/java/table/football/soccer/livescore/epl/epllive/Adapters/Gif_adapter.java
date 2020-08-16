package table.football.soccer.livescore.epl.epllive.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.gif_model;

public class Gif_adapter extends RecyclerView.Adapter<Gif_adapter.ViewHolder> {

    List<gif_model> GifList;
    Context context;

    public Gif_adapter(List<gif_model> gifList, Context context) {
        GifList = gifList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_gif,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return GifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView gifTumb;
       // TextView  gifText;
        public ViewHolder(View itemView) {
            super(itemView);


          gifTumb = itemView.findViewById(R.id.gif_pic_thum);
          //  gifText = itemView.findViewById(R.id.gif_head_text);

        }




    }

}
