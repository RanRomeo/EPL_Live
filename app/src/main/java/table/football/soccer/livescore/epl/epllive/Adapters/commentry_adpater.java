package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;

import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.commentry_model;



public class commentry_adpater extends RecyclerView.Adapter<commentry_adpater.ViewHodler>  {

    List<commentry_model> commentryModelList;
    static Context context;
    String MatchId;


    //Handler mHandlerThread;


    public commentry_adpater(List<commentry_model> commentryModelList, Context context, String matchId) {
        this.commentryModelList = commentryModelList;
        this.context = context;
        MatchId = matchId;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_commentry,parent,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        final commentry_model  commentryModel = commentryModelList.get(position);
        holder.CommentTime.setText(commentryModel.getTime());
        holder.CommentText.setText(commentryModel.getCommentryText());
        String playerid = commentryModel.getPlayerID();
        String img = commentryModelList.get(position).getImageType();

      //  Log.d("CommentryAdapter ",img);
        if (img!=null){

            if (Objects.equals(img, "goal")||Objects.equals(img, "own goal"))
            {
               holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.colorBox));

                holder.Goal_view.setVisibility(View.VISIBLE);
              //  new getImage(holder,playerid,new WebView(context).getSettings().getUserAgentString()).execute();
                StartAsyncTaskInParallel(new getImage(holder,playerid,new WebView(context).getSettings().getUserAgentString(),MatchId));

//                holder.Goal_view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //OpenPlayerActivity();
//                        Toast.makeText(context, " " , Toast.LENGTH_SHORT).show();
//                        //Toast.makeText(context, "Goal", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            else if (Objects.equals(img, "red card")){
                holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.foulcolorRed));
              //   new getImage(holder,playerid,new WebView(context).getSettings().getUserAgentString()).execute();
                StartAsyncTaskInParallel(new getImage(holder,playerid,new WebView(context).getSettings().getUserAgentString(),MatchId));

                holder.Goal_view.setVisibility(View.VISIBLE);
//                holder.Goal_view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "Goal", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            else if (Objects.equals(img, "yellow card")){
                holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.foulcolor));
                StartAsyncTaskInParallel(new getImage(holder,playerid,new WebView(context).getSettings().getUserAgentString(),MatchId));
              //  .execute();
                holder.Goal_view.setVisibility(View.VISIBLE);
//                holder.Goal_view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "Goal", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            else if (Objects.equals(img, "end 2")||Objects.equals(img, "end 1")){
                holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.colorStart));
                holder.Goal_view.setVisibility(View.GONE);
            }
            else if (Objects.equals(img, "lineup")){
                holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.colorStart));
                holder.Goal_view.setVisibility(View.GONE);
            }
            else {
                holder.CommentBox.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.Goal_view.setVisibility(View.GONE);
            }

        }

        if(Objects.equals(img, "miss")){
           Picasso.with(context).load(R.drawable.attemptmiss).into(holder.CommentImage);
           // Picasso.with(context).load(R.drawable.attemptmiss).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "attempt saved")){
            Picasso.with(context).load(R.drawable.attemtsaved).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "corner")){
            Picasso.with(context).load(R.drawable.cornerpng).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "yellow card")){
           Picasso.with(context).load(R.drawable.foalpng).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "goal")){
            Picasso.with(context).load(R.drawable.goalpng).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "start")){
           Picasso.with(context).load(R.drawable.seconhalfpng).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "substitution")){
          Picasso.with(context).load(R.drawable.subpng).into(holder.CommentImage);
        }
        else if(Objects.equals(img, "red card")){
           Picasso.with(context).load(R.drawable.redpng).into(holder.CommentImage);
        }
       else
         Picasso.with(context).load(R.drawable.blank).into(holder.CommentImage);


    }

    @Override
    public int getItemCount() {
        return commentryModelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        TextView CommentText,CommentTime,goal_player_text;
        ImageView CommentImage,goal_player_view;
        CardView CommentBox;
        LinearLayout Goal_view;
        ProgressBar player_progress;
        public ViewHodler(View itemView) {
            super(itemView);

            CommentText = itemView.findViewById(R.id.comment_text);
            CommentTime = itemView.findViewById(R.id.comment_time);
            CommentImage= itemView.findViewById(R.id.comment_img);


            CommentBox = itemView.findViewById(R.id.Comment_box);
            Goal_view = itemView.findViewById(R.id.Goal_view);

            goal_player_view =  itemView.findViewById(R.id.goal_playerView);
            goal_player_text = itemView.findViewById(R.id.goal_player_text);
            player_progress = itemView.findViewById(R.id.player_progress);

        }



    }


    public static class getImage extends AsyncTask<String,Void,String>{

        ViewHodler holder;
        String id;
        String User_Agent;
        String MatchID;



        public getImage(ViewHodler holder, String id, String user_Agent, String matchID) {
            this.holder = holder;
            this.id = id;
            User_Agent = user_Agent;
            MatchID = matchID;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //Log.d("canelAsync", "onCancelled: Was Called "  );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            holder.goal_player_view.setVisibility(View.GONE);
            holder.goal_player_text.setVisibility(View.GONE);
            holder.player_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            //String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document;
            String Link = null;
            try{
             //   Log.d("Commentry", "doInBackground: " + "STarted");
                //https://www.premierleague.com/match/38495
                document = Jsoup.connect("https://www.premierleague.com/players/"+id).referrer("https://www.premierleague.com/match/"+MatchID).userAgent(User_Agent).get();
                Elements LastPage = document.select(".imgContainer");
                String dataId = LastPage.select("img").attr("data-player");
                final String playername = document.select(".playerDetails").select(".name").text();
           //     Log.d("Commentry", "doInBackground: " + "STarted" + dataId);
                Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";
                    // System.out.println(dataId);
                //System.out.println();
              //  Log.d("ImagePlayer", "doInBackground: " + Link);
                ((Match_Activity)context).runOnUiThread(new Runnable() {
                    public void run() {
                        holder.goal_player_text.setText(playername);
                        holder.Goal_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // Toast.makeText(context, "HelloWoeld", Toast.LENGTH_SHORT).show();
                                OpenPlayerActivity(id,playername);
                            }
                        });
                    }
                });

            }catch(Exception e ){
              //  Log.d("ImagePlayer", "doInBackgrounde: " + e);
                //   https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/p37572.png
            }

            return Link;
        }


        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

//         Glide.with(context).load(s).into(holder.goal_player_view);
            Picasso.with(context).load(s).into(holder.goal_player_view, new Callback() {
                @Override
                public void onSuccess() {
                    holder.player_progress.setVisibility(View.GONE);
                    holder.goal_player_view.setVisibility(View.VISIBLE);
                    holder.goal_player_text.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                Picasso.with(context).load(s).into(holder.goal_player_view);
                }
            });
          //Picasso.with(context).load(imageThumbLinke).into(holder.newsImage);
        }
    }

   private void StartAsyncTaskInParallel(getImage task) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static void OpenPlayerActivity(String PlayerID,String PlayeName){
//        String PlayerID = Objects.requireNonNull(getIntent().getExtras()).getString("PlayerID");
//        String PlayerName = Objects.requireNonNull(getIntent().getExtras().getString("PlayerName")).replace(" ","-");
        Intent i = new Intent(context,Player_activity.class);
        i.putExtra("PlayerName",PlayeName);
        i.putExtra("PlayerID",PlayerID);
        context.startActivity(i);



    }


}
