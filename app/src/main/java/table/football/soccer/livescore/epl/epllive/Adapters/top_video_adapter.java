package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.activity_yt_player;
import table.football.soccer.livescore.epl.epllive.model.Top_videos;

/**
 * Created by Ran on 24-Oct-18.
 */

public class top_video_adapter extends  RecyclerView.Adapter<top_video_adapter.Viewholder> {

    List<Top_videos> topVideosList;
    Context context;

    public top_video_adapter(List<Top_videos> topVideosList, Context context) {
        this.topVideosList = topVideosList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_daily_videos, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Top_videos topVideos = topVideosList.get(position);
        //System.out.println(" hello eorld " + topVideos.getVideo_Title());
        holder.vid_title.setText(topVideos.getVideo_Title());
        Picasso.with(context).load(topVideos.getImageLink()).into(holder.thumb_img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, activity_yt_player.class);
                i.putExtra("Video",topVideos.getYt_id());
                i.putExtra("chanID",topVideos.getChannelId());
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return topVideosList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView vid_title;
        ImageView thumb_img;
        public Viewholder(View itemView) {
            super(itemView);


            vid_title = itemView.findViewById(R.id.text_view);
            thumb_img = itemView.findViewById(R.id.imageView);

        }
    }


}
