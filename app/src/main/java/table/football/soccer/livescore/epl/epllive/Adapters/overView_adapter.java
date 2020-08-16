package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.overviewmodel;

/**
 * Created by Ran on 25-Aug-18.
 */

public class overView_adapter extends RecyclerView.Adapter<overView_adapter.Viewholder>{

    private List<overviewmodel> overviewmodelList;
    Context context;


    public overView_adapter(List<overviewmodel> overviewmodelList, Context context) {
        this.overviewmodelList = overviewmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_overview,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       // final commentry_model  commentryModel = commentryModelList.get(position); Own Goal
        overviewmodel overviewModel = overviewmodelList.get(position);

        String type = overviewModel.getType();
        String team = overviewModel.getClassName();

        holder.eventTime.setText(overviewModel.getTime());
        holder.event_score.setText(overviewModel.getScore());


       if (team.equals("event home")){
           if(type.equals("Goal With Assit")){
               String AssitPlayerLink =overviewModel.getAssitplayerlink();

               holder.home_event2.setVisibility(View.VISIBLE);
               //"https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png"
               holder.home_playerName.setText(overviewModel.getPlayerName());
               holder.home_playerName2.setText(overviewModel.getAssitplayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_player_pic2.setImageResource(R.drawable.missing_thumb);
               holder.home_event_type_pic.setImageResource(R.drawable.goal_);
               new getImage(holder,AssitPlayerLink,team,new WebView(context).getSettings().getUserAgentString()
               ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
               holder.home_event_type_pic2.setImageResource(R.drawable.assist);
           }else if (type.equals("Goal")){
               holder.home_event2.setVisibility(View.GONE);
               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.goal_);
              // OpenActivityhome_player_pic(holder.home_player_pic,overviewModel.getPlayerImageID(),overviewModel.getPlayerName());

           }else if (type.equals("Own Goal")){
               holder.home_event2.setVisibility(View.GONE);
               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.own_goal);

           }else if (type.equals("Yellow Card")){
               holder.home_event2.setVisibility(View.GONE);
               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.foalpng);

           }else if (type.equals("Red Card")){
               holder.home_event2.setVisibility(View.GONE);
               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.redpng);

           }else if (type.equals("label.penalty.scored")){
               holder.home_event2.setVisibility(View.GONE);
               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.pengoal);

           }else if (type.equals("Substitution Substitution Off Substitution On")){
               holder.home_event2.setVisibility(View.VISIBLE);

               String subPlayerLink = overviewModel.getSubplayerlink();

               holder.home_playerName.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.home_player_pic);
               holder.home_event_type_pic.setImageResource(R.drawable.sub_out);
               //subplayer

               holder.home_playerName2.setText(overviewModel.getSubplayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getSubplayerImageID()+".png").into(holder.home_player_pic2);
               holder.home_event_type_pic2.setImageResource(R.drawable.missing_thumb);
               holder.home_event_type_pic2.setImageResource(R.drawable.sub2);
           }
       }else {
           if(type.equals("Goal With Assit")){
               String AssitPlayerLink =overviewModel.getAssitplayerlink();

               holder.away_event2.setVisibility(View.VISIBLE);
               //"https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png"
               holder.away_player_name.setText(overviewModel.getPlayerName());
               holder.away_player_name2.setText(overviewModel.getAssitplayerName());
             Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_player_pic2.setImageResource(R.drawable.missing_thumb);
               holder.away_event_type_pic.setImageResource(R.drawable.goal_);
               new getImage(holder,AssitPlayerLink,team,new WebView(context).getSettings().getUserAgentString()
               ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
               holder.away_event_type_pic2.setImageResource(R.drawable.assist);
           }else if (type.equals("Goal")){
               holder.away_event2.setVisibility(View.GONE);
               holder.away_player_name.setText(overviewModel.getPlayerName());
             Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.goal_);

           }else if (type.equals("Own Goal")){
               holder.away_event2.setVisibility(View.GONE);
               holder.away_player_name.setText(overviewModel.getPlayerName());
             Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.own_goal);

           }else if (type.equals("Yellow Card")){
               holder.away_event2.setVisibility(View.GONE);
               holder.away_player_name.setText(overviewModel.getPlayerName());
              Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.foalpng);

           }else if (type.equals("Red Card")){
               holder.away_event2.setVisibility(View.GONE);
               holder.away_player_name.setText(overviewModel.getPlayerName());
                Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.redpng);

           }else if (type.equals("label.penalty.scored")){
               holder.away_event2.setVisibility(View.GONE);
               holder.away_player_name.setText(overviewModel.getPlayerName());
            Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.pengoal);

           }else if (type.equals("Substitution Substitution Off Substitution On")){
               holder.away_event2.setVisibility(View.VISIBLE);

               String subPlayerLink = overviewModel.getSubplayerlink();

               holder.away_player_name.setText(overviewModel.getPlayerName());
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getPlayerImageID()+".png").into(holder.away_player_pic);
               holder.away_event_type_pic.setImageResource(R.drawable.sub_out);
               //subplayer
              // Log.d("overView_adapterDotasub","https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/" + overviewModel.getSubplayerImageID()+".png");
               holder.away_player_name2.setText(overviewModel.getSubplayerName());
               holder.away_player_pic2.setImageResource(R.drawable.missing_thumb);
               Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+overviewModel.getSubplayerImageID()+".png").into(holder.away_player_pic2);
               holder.away_event_type_pic2.setImageResource(R.drawable.sub2);
           }
       }



    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    private void OpenActivityhome_player_pic(ImageView imageView, final String PlayerId, final String PlayerName){
        Log.d("clicklisnte", "OpenActivityhome_player_pic: " + PlayerId + " NAme " + PlayerName);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicklisnte", "onClickOpenActivityhome_player_pic: " + PlayerId + " NAme " + PlayerName);
                Intent i = new Intent(context,Player_activity.class);
                i.putExtra("PlayerName",PlayerName);
                i.putExtra("PlayerID",PlayerId);
                context.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return overviewmodelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        //home
        TextView  home_playerName,
                  home_playerName2;
        ImageView home_player_pic,
                  home_event_type_pic,
                  home_event_type_pic2,
                  home_player_pic2;
        //away
        TextView     away_player_name,
                     away_player_name2;
        ImageView    away_player_pic,
                     away_event_type_pic,
                     away_event_type_pic2,
                     away_player_pic2;
        LinearLayout home_event2,
                     away_event2;
        //Middle
        TextView eventTime,event_score;

        public Viewholder(View itemView) {
            super(itemView);

            home_playerName = itemView.findViewById(R.id.home_player_name);
            home_playerName2 = itemView.findViewById(R.id.home_player_name2);
            home_player_pic = itemView.findViewById(R.id.home_player_pic);
            home_player_pic2 = itemView.findViewById(R.id.home_player_pic2);
            home_event_type_pic = itemView.findViewById(R.id.home_event_type_pic);
            home_event_type_pic2 = itemView.findViewById(R.id.home_event_type_pic2);


            //away
            away_player_name = itemView.findViewById(R.id.away_player_name);
            away_player_name2 = itemView.findViewById(R.id.away_player_name2);
            away_player_pic = itemView.findViewById(R.id.away_player_pic);
            away_event_type_pic = itemView.findViewById(R.id.away_event_type_pic);
            away_event_type_pic2 = itemView.findViewById(R.id.away_event_type_pic2);
            away_player_pic2 = itemView.findViewById(R.id.away_player_pic2);


            home_event2 = itemView.findViewById(R.id.home_event2);
            away_event2 = itemView.findViewById(R.id.away_event2);


            eventTime = itemView.findViewById(R.id.event_time);
            event_score = itemView.findViewById(R.id.event_score);
        }



    }


    public static class getImage extends AsyncTask<String,Void,String> {

        Viewholder holder;
        String id;
        String Team;
        String UserAgent;
        private Context context;

        public getImage(Viewholder holder, String id, String team, String userAgent) {
            this.holder = holder;
            this.id = id;
            Team = team;
            UserAgent = userAgent;
        }

        public getImage() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  holder.goal_player_view.setVisibility(View.INVISIBLE);
          //  holder.goal_player_text.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
          //  String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document;
            String Link = null;
            while (isCancelled()){
                try{
                    document = Jsoup.connect("https://www.premierleague.com"+id).referrer("https://www.premierleague.com").userAgent(UserAgent).get();
                    Elements LastPage = document.select(".imgContainer");
                    String dataId = LastPage.select("img").attr("data-player");
                    Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";
                    // System.out.println(dataId);
                    //System.out.println();
                }catch(Exception e ){
                   // Log.d("overView_adapterDota","ERRO " + e);
                    //   https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/p37572.png
                }
                break;
            }

            return Link;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        //    Log.d("overView_adapterDota",s);
            //   Glide.with(context).load(s).into(holder.goal_player_view);
            if (Team.equals("event home")){
                Picasso.with(context).load(s).placeholder(R.drawable.missing_thumb).into(holder.home_player_pic2);
            }

            if (Team.equals("event away")){
                Picasso.with(context).load(s).placeholder(R.drawable.missing_thumb).into(holder.away_player_pic2);
            }


        }
    }
}
