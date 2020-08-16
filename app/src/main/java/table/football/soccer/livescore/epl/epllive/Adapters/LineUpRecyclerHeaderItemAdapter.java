package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.Fragments_lineups.Fragment_away_team;
import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.player_model;

/**
 * Created by Ran on 20-Aug-18.
 */

public class LineUpRecyclerHeaderItemAdapter extends LineUpRecyclerItemAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<player_model> mList ;
    static Context context;


    public LineUpRecyclerHeaderItemAdapter(List<player_model> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_player_lineup, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final player_model itemModel = mList.get(position);
        final String id = itemModel.getPlayerId();
        ((ItemViewHolder)holder).player_name.setText(itemModel.getPlayerName());
        ((ItemViewHolder)holder).player_country.setText(itemModel.getPlayerCountry());
        ((ItemViewHolder)holder).player_no.setText(itemModel.getPlayerNo());
        ((ItemViewHolder)holder).player_position.setText(itemModel.getPlayerPosition());


        ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPlayerActivity(id,itemModel.getPlayerName());
            }
        });

        //.execute();
        StartAsyncTaskInParallel(new getImage(holder,id,new WebView(context).getSettings().getUserAgentString(),context));

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getHeaderId(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        //  Log.d("HasCode"," " + getItem(position));
        return getItem(position).getPlayerType();
    }

    public player_model getItem(int position) {
        return mList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.linupe_header, parent, false);
        return new ItemHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHeaderViewHolder) {
            if (getItem(position).getMatchPostion() != null) {
                player_model itemModel = mList.get(position);
                int header = itemModel.getPlayerType();
                if (header==4){
                    ((ItemHeaderViewHolder) holder).header.setText("Goalkeeper");
                }

                if (header==3){
                    ((ItemHeaderViewHolder) holder).header.setText("Forwards");
                }

                if (header==2){
                    ((ItemHeaderViewHolder) holder).header.setText("Midfielders");
                }

                if (header==1){
                    ((ItemHeaderViewHolder) holder).header.setText("Defenders");
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView playerPic;
        TextView player_no,player_name,player_country,player_position;
       // ProgressBar playerImag_progrees;
        public ItemViewHolder(View itemView) {
            super(itemView);

            playerPic = itemView.findViewById(R.id.playerPic);
            player_no = itemView.findViewById(R.id.player_no);
            player_name = itemView.findViewById(R.id.player_name);
            player_position = itemView.findViewById(R.id.player_position);
            player_country = itemView.findViewById(R.id.player_country);

         //   playerImag_progrees = itemView.findViewById(R.id.player_image_progress);

        }
    }

    public static class ItemHeaderViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public static class getImage extends AsyncTask<String,Void,String> {

        RecyclerView.ViewHolder  holder;
        String id;
        String UserAgent;
        private Context context;

        public getImage(RecyclerView.ViewHolder holder, String id, String userAgent, Context context) {
            this.holder = holder;
            this.id = id;
            UserAgent = userAgent;
            this.context = context;
        }

        public getImage() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  ((ItemViewHolder)holder).playerImag_progrees.setVisibility(View.VISIBLE);
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
                // System.out.println(dataId);
                //System.out.println();
                //Log.d("LineUpData", "doInBackground: "+Link);

            }catch(Exception e ){
               // Log.d("LineUpData", "doInBackground: "+ e);
                //   https://premierleague-static-files.s3.amazonaws.com/premierleague/photos/players/250x250/p37572.png
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
                     Picasso.with(context).load(R.drawable.missing_thumb).into(((ItemViewHolder) holder).playerPic, new Callback() {
                         @Override
                         public void onSuccess() {
                            // ((ItemViewHolder)holder).playerImag_progrees.setVisibility(View.GONE);
                             ((ItemViewHolder)holder).playerPic.setVisibility(View.VISIBLE);
                         }

                         @Override
                         public void onError() {

                         }
                     });
                       // Glide.with(context).load(R.drawable.missing_thumb).into(((ItemViewHolder)holder).playerPic);
                       return false;
                   }

                   @Override
                   public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                      // ((ItemViewHolder)holder).playerImag_progrees.setVisibility(View.GONE);
                       ((ItemViewHolder)holder).playerPic.setVisibility(View.VISIBLE);
                       return false;
                   }
               }).into(((ItemViewHolder)holder).playerPic);
                //requestManager.load(s).into(((ItemViewHolder)holder).playerPic);
            }catch (Exception e){
                System.out.print(e);
            }
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
