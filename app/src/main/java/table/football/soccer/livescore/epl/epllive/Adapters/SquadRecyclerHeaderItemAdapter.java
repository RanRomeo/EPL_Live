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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.Match_Activity;
import table.football.soccer.livescore.epl.epllive.Player_activity;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.player_model;

/**
 * Created by Ran on 09-Sep-18.
 */

public class SquadRecyclerHeaderItemAdapter extends SquadRecyclerItemAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<player_model> mList ;
    public Context context;


    public SquadRecyclerHeaderItemAdapter(List<player_model> mList, Context context) {
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
        ((SquadRecyclerHeaderItemAdapter.ItemViewHolder)holder).player_name.setText(itemModel.getPlayerName());
        ((SquadRecyclerHeaderItemAdapter.ItemViewHolder)holder).player_country.setText(itemModel.getPlayerCountry());
        ((SquadRecyclerHeaderItemAdapter.ItemViewHolder)holder).player_no.setText(itemModel.getPlayerNo());
        ((SquadRecyclerHeaderItemAdapter.ItemViewHolder)holder).player_position.setText(itemModel.getPlayerPosition());
       // ((ItemViewHolder)holder)
       // new LineUpRecyclerHeaderItemAdapter.getImage(holder,id).execute();
        Picasso.with(context).load(itemModel.getPlayerImageLink()).placeholder(R.drawable.missing_thumb).into(((ItemViewHolder)holder).playerPic);
        ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPlayerActivity(id,itemModel.getPlayerName(),context);
               // Toast.makeText(context, "Hell", Toast.LENGTH_SHORT).show();
            }
        });

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
                    ((SquadRecyclerHeaderItemAdapter.ItemHeaderViewHolder) holder).header.setText("Goalkeeper");
                }

                if (header==3){
                    ((SquadRecyclerHeaderItemAdapter.ItemHeaderViewHolder) holder).header.setText("Defenders");
                }

                if (header==2){
                    ((SquadRecyclerHeaderItemAdapter.ItemHeaderViewHolder) holder).header.setText("Midfielders");
                }

                if (header==1){
                    ((SquadRecyclerHeaderItemAdapter.ItemHeaderViewHolder) holder).header.setText("Forwards");
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
        public ItemViewHolder(View itemView) {
            super(itemView);

            playerPic = itemView.findViewById(R.id.playerPic);
            player_no = itemView.findViewById(R.id.player_no);
            player_name = itemView.findViewById(R.id.player_name);
            player_position = itemView.findViewById(R.id.player_position);
            player_country = itemView.findViewById(R.id.player_country);

        }
    }

    public static class ItemHeaderViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
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
