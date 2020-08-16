package table.football.soccer.livescore.epl.epllive.Fragments_match;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import table.football.soccer.livescore.epl.epllive.Adapters.photo_adapters;
import table.football.soccer.livescore.epl.epllive.R;
import table.football.soccer.livescore.epl.epllive.model.photo_model;


public class Fragment_photos extends Fragment {


    View v;
    RecyclerView photoRecyclerView;
    List<photo_model> photoModelList;
    photo_adapters photoAdapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_photos, container, false);

        photoRecyclerView = v.findViewById(R.id.photo_rec);
        photoModelList = new ArrayList<>();
        photoAdapters = new photo_adapters(photoModelList,getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        photoRecyclerView.setLayoutManager(layoutManager);
        photoRecyclerView.setAdapter(photoAdapters);
        photoRecyclerView.setHasFixedSize(true);

        getPhoto("https://footballapi.pulselive.com/content/PremierLeague/photo/EN/?references=FOOTBALL_FIXTURE:38325&pageSize=250");

        return v;
    }


    public  void getPhoto(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ArrayList<photo_model> data = new ArrayList<>();
                try{
                    JSONObject obj = new JSONObject(response);
                    JSONArray ContentArray = obj.getJSONArray("content");
                    photo_model item;
                    for(int i=0;i<ContentArray.length();i++){
                        JSONObject photoObj = ContentArray.getJSONObject(i);
                        String PhotDesvc = photoObj.getString("description");
                        String PhotoUrl = photoObj.getString("imageUrl");
                        JSONArray photoArra = photoObj.getJSONArray("variants");
                        JSONObject photooSmallObj = photoArra.getJSONObject(0);
                        String thumbUrl = photooSmallObj.getString("url");
                        item = new photo_model();
                        item.setPhotoDesc(PhotDesvc);
                        item.setPhotoLink(PhotoUrl);
                        item.setPhotoThumb(thumbUrl);
                        data.add(item);
                    }

                    photoModelList.addAll(data);
                    photoAdapters.notifyDataSetChanged();

                }catch (Exception e){
                    Log.d("Fragment_photo" , " " + e );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
                params.put("Referer", "https://www.premierleague.com/fixtures");
                params.put("Origin", "https://www.premierleague.com");
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


}
