package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.season_model;

public class snipper_season_adapter extends ArrayAdapter<season_model> {


    public snipper_season_adapter(@NonNull Context context, ArrayList<season_model> seasonList) {
        super(context, 0,seasonList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return intiView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return intiView(position,convertView,parent);
    }




    private View intiView(int position,View convertView,ViewGroup parent){
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.snipper_item_layout,parent,false);
        }


        TextView DateTextView = convertView.findViewById(R.id.date_text);

        season_model seasonModel = getItem(position);

        if (seasonModel!=null){
            DateTextView.setText(seasonModel.getDate());
        }





        return convertView;
    }





















}
