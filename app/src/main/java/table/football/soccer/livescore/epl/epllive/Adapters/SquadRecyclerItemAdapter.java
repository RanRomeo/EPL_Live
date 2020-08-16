package table.football.soccer.livescore.epl.epllive.Adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.model.player_model;

/**
 * Created by Ran on 09-Sep-18.
 */

public abstract class SquadRecyclerItemAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>  {


    List<player_model> items = new ArrayList<>();
    SquadRecyclerItemAdapter(){
        setHasStableIds(true);
    }


    public void add(player_model object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, player_model object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends player_model> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(player_model... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(player_model object) {
        items.remove(object);
        notifyDataSetChanged();
    }















}
