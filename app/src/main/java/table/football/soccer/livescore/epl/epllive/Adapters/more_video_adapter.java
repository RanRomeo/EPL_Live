package table.football.soccer.livescore.epl.epllive.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.activity_yt_player;
import table.football.soccer.livescore.epl.epllive.model.Top_videos;

public class more_video_adapter extends  RecyclerView.Adapter<more_video_adapter.Viewholder> {


    OnItemClickListener VideoListner;
    List<Top_videos> topVideosList;
    Context context;
  // YouTubePlayer player;


    public interface OnItemClickListener {
        void onItemClicked(String VideoId);
    }


    public more_video_adapter(OnItemClickListener videoListner, List<Top_videos> topVideosList, Context context) {
        VideoListner = videoListner;
        this.topVideosList = topVideosList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_more_vide_view, parent, false);
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
//                Intent i = new Intent(context, activity_yt_player.class);
//                i.putExtra("Video",topVideos.getYt_id());
//                context.startActivity(i);
//                Intent refresh = new Intent(context, activity_yt_player.class);
//                context.startActivity(refresh);
//                ((Activity)context).finish();
                 //   ((activity_yt_player)context).playVideo();
                //

                VideoListner.onItemClicked(topVideos.getYt_id());
               //player.cueVideo(topVideos.getYt_id());
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
