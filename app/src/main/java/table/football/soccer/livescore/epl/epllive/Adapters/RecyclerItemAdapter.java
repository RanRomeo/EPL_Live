package table.football.soccer.livescore.epl.epllive.Adapters;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.model.match_model;

/**
 * Created by Ran on 17-Aug-18.
 */


public abstract class RecyclerItemAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>  {


    List<match_model> items = new ArrayList<>();
    RecyclerItemAdapter(){
        setHasStableIds(true);
    }


    public void add(match_model object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, match_model object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends match_model> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(match_model... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(match_model object) {
        items.remove(object);
        notifyDataSetChanged();
    }







}
