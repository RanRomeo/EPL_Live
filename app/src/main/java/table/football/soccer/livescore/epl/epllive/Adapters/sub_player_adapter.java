package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.player_model;

/**
 * Created by Ran on 09-Sep-18.
 */

public class sub_player_adapter extends  RecyclerView.Adapter<sub_player_adapter.ViewHolder> {


    List<player_model> playerModelList;
    Context context;


    public sub_player_adapter(List<player_model> playerModelList, Context context) {
        this.playerModelList = playerModelList;
        this.context = context;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_player_lineup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final player_model itemModel = playerModelList.get(position);
        final String id = itemModel.getPlayerId();
        holder.player_name.setText(itemModel.getPlayerName());
        holder.player_country.setText(itemModel.getPlayerCountry());
        holder.player_no.setText(itemModel.getPlayerNo());
        holder.player_position.setText(itemModel.getPlayerPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPlayerActivity(id,itemModel.getPlayerName(),context);
            }
        });
        //.execute();
        StartAsyncTaskInParallel(new getImage(holder,id,new WebView(context).getSettings().getUserAgentString()));
    }

    @Override
    public int getItemCount() {
        return playerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView playerPic;
        TextView player_no,player_name,player_country,player_position;
        public ViewHolder(View itemView) {
            super(itemView);

            playerPic = itemView.findViewById(R.id.playerPic);
            player_no = itemView.findViewById(R.id.player_no);
            player_name = itemView.findViewById(R.id.player_name);
            player_position = itemView.findViewById(R.id.player_position);
            player_country = itemView.findViewById(R.id.player_country);



        }

    }



    public class getImage extends AsyncTask<String,Void,String> {

        ViewHolder holder ;
        String id;
        String UserAgent;

        public getImage(ViewHolder holder, String id, String userAgent) {
            this.holder = holder;
            this.id = id;
            UserAgent = userAgent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // holder.goal_player_view.setVisibility(View.INVISIBLE);
            // holder.goal_player_text.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
          //  String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
            Document document;
            String Link = null;
            try{
                document = Jsoup.connect("https://www.premierleague.com/players/"+id).userAgent(UserAgent).get();
                Elements LastPage = document.select(".imgContainer");
                String dataId = LastPage.select("img").attr("data-player");
                final String playername = document.select(".playerDetails").select(".name").text();
                Link = "https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/"+dataId+".png";

            }catch(Exception e ){

            }

            return Link;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                Glide.with(context).load(s).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                      //  Glide.with(context).load(R.drawable.missing_thumb).into(holder.playerPic);
                       Picasso.with(context).load(R.drawable.missing_thumb).into(holder.playerPic);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.playerPic);
                //requestManager.load(s).into(((ItemViewHolder)holder).playerPic);
            }catch (Exception e){
                System.out.print(e);
            }
        }
    }

    private void StartAsyncTaskInParallel(getImage task) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private static void OpenPlayerActivity(String PlayerID,String PlayeName,Context context){
//        String PlayerID = Objects.requireNonNull(getIntent().getExtras()).getString("PlayerID");
//        String PlayerName = Objects.requireNonNull(getIntent().getExtras().getString("PlayerName")).replace(" ","-");
        Intent i = new Intent(context,Player_activity.class);
        i.putExtra("PlayerName",PlayeName);
        i.putExtra("PlayerID",PlayerID);
        context.startActivity(i);



    }

}
