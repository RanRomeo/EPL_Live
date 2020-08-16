package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.photo_model;

/**
 * Created by Ran on 21-Aug-18.
 */

public class photo_adapters extends RecyclerView.Adapter<photo_adapters.Viewholder> {

    List<photo_model> photoModelList;
    Context context;


    public photo_adapters(List<photo_model> photoModelList, Context context) {
        this.photoModelList = photoModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_photo_view,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final photo_model  Pmodel = photoModelList.get(position);

        holder.photDesc.setText(Pmodel.getPhotoDesc());

        Glide.with(context).asBitmap().load(Pmodel.getPhotoThumb()).into(holder.photoimage);
//Glide.with(mContext).load(imgID).asBitmap().override(1080, 600).into(mImageView);
      Picasso.with(context).load("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/11/22557340-5075-477f-b9a9-0cb8def7822c/2018-08-11T155429Z_509465062_RC1F21D758E0_RTRMADP_3_SOCCER-ENGLAND-HDD-CHE.JPG").into(holder.photoimage);
      //  Log.d("ImageLink"," " + Pmodel.getPhotoThumb() );
    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView photDesc;
        ImageView photoimage;
        public Viewholder(View itemView) {
            super(itemView);

            photDesc = itemView.findViewById(R.id.moment_status);
            photoimage= itemView.findViewById(R.id.moment_pic);

        }
    }

}
