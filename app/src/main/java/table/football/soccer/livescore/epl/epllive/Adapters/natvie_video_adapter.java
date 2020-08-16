package table.football.soccer.livescore.epl.epllive.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdsManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.video_model;



public class natvie_video_adapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    NativeAdsManager mNativeAdsManager;
    List<video_model> videoModels;
    Context context;


    private static final int VIDEO = 0;
    private static final int NATIVE_AD = 1;
    private static final int AD_DISPLAY_FREQUENCY = 3;

    public natvie_video_adapter(NativeAdsManager mNativeAdsManager, List<video_model> videoModels, Context context) {
        this.mNativeAdsManager = mNativeAdsManager;
        this.videoModels = videoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


         Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIDEO) {
            View recipeItem = inflater.inflate(R.layout.rec_video_layout, parent, false);
            return new Itemview(recipeItem);
        } else if (viewType == NATIVE_AD) {
            View nativeAdItem = inflater.inflate(R.layout.rec_video_natvie_ads, parent, false);
            return new AdView(nativeAdItem);
        } else {
            View recipeItem = inflater.inflate(R.layout.rec_video_layout, parent, false);
            return new Itemview(recipeItem);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Calculate where the next postItem index is by subtracting ads we've shown.
        int index = position - (position / AD_DISPLAY_FREQUENCY) + 1;
        int itemType = getItemViewType(position);
        //System.out.println(index + " position  " + position);
      //  Log.d("Fragment_video"," index   " + index  +  " position " + position);
        NativeAd ad;
        ad = mNativeAdsManager.nextNativeAd();
        if(itemType == NATIVE_AD){
            AdView nativeAdViewHolder = (AdView) holder;




            if (ad!=null){
                ad.unregisterView();
                AdIconView adIconView = nativeAdViewHolder.adIconView;
                TextView tvAdTitle = nativeAdViewHolder.tvAdTitle;
                TextView tvAdBody = nativeAdViewHolder.tvAdBody;
                Button btnCTA = nativeAdViewHolder.btnCTA;
                LinearLayout adChoicesContainer = nativeAdViewHolder.adChoicesContainer;
                MediaView mediaView = nativeAdViewHolder.mediaView;
                TextView sponsorLabel = nativeAdViewHolder.sponsorLabel;

                tvAdTitle.setText(ad.getAdvertiserName());
                tvAdBody.setText(ad.getAdBodyText());
                btnCTA.setText(ad.getAdCallToAction());
                sponsorLabel.setText(ad.getSponsoredTranslation());

                adChoicesContainer.removeAllViews();
                AdChoicesView adChoicesView = new AdChoicesView(context, ad, true);
                adChoicesContainer.addView(adChoicesView);

                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(btnCTA);
                clickableViews.add(mediaView);
                ad.registerViewForInteraction(nativeAdViewHolder.container, mediaView, adIconView, clickableViews);
            }


        }else {

            //  final commentry_model  commentryModel = commentryModelList.get(position);
            video_model getvideoModels = videoModels.get(index+1);
            Itemview ItemviewHolder = (Itemview) holder;

            String VideoID = getvideoModels.getVideID();
            ItemviewHolder.video_title.setText(getvideoModels.getVideoTitle());
           Picasso.with(context).load(getvideoModels.getImageLink()).into(ItemviewHolder.image_view);
           // Log.d("Fragment_video"," " + VideoID);

        }

    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }


    @Override
    public int getItemViewType(int position) {
       // if (!mNativeAdsManager.isLoaded()){
          //  Toast.makeText(context, " " + position + " false  " + mNativeAdsManager.nextNativeAd(), Toast.LENGTH_SHORT).show();
      //      return VIDEO;
      //  }
        return position % AD_DISPLAY_FREQUENCY == 0 ? NATIVE_AD : VIDEO;
    }


    public class Itemview extends RecyclerView.ViewHolder{

        ImageView image_view;
        TextView video_title;
        public Itemview(View itemView) {
            super(itemView);

            image_view = itemView.findViewById(R.id.image_view);
            video_title = itemView.findViewById(R.id.video_title);


        }



    }



    public class AdView extends RecyclerView.ViewHolder{

        AdIconView adIconView;
        TextView tvAdTitle;
        TextView tvAdBody;
        Button btnCTA;
        View container;
        TextView sponsorLabel;
        LinearLayout adChoicesContainer;
        MediaView mediaView;
        LinearLayout Ad_linear_layout;
        public AdView(View itemView) {
            super(itemView);
            container = this.itemView;
            Ad_linear_layout = itemView.findViewById(R.id.Ad_layout);
            adIconView = itemView.findViewById(R.id.adIconView);
            tvAdTitle = itemView.findViewById(R.id.tvAdTitle);
            tvAdBody = itemView.findViewById(R.id.tvAdBody);
            btnCTA = itemView.findViewById(R.id.btnCTA);
            sponsorLabel = itemView.findViewById(R.id.sponsored_label);
            adChoicesContainer = itemView.findViewById(R.id.adChoicesContainer);
            mediaView = itemView.findViewById(R.id.mediaView);



        }
    }




}
