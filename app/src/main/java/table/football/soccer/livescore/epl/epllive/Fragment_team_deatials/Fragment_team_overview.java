package table.football.soccer.livescore.epl.epllive.Fragment_team_deatials;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import table.football.soccer.livescore.epl.epllive.R;

public class Fragment_team_overview extends Fragment {
    View v;
    //AppBarlayou
    //ProgressBar progressBar;
    TextView TeamClubName,TeamStaduimName;
    ImageView Team_Back_image,Team_image;

    //TemOverView vist
    Button button_website,button_app,button_club_ticket,button_statidumt;
    //Social button
    ImageButton button_facebook,button_twiiter,button_instagram,button_youtube;

    //ClubKit
    Button home_button_buy,away_button_buy,third_button_buy;
    ImageView  image_view_home_kit,image_view_away_kit,image_view_third_kit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_team_overview, container, false);
        String TeamName = getActivity().getIntent().getExtras().getString("TeamName");
        String TeamID = getActivity().getIntent().getExtras().getString("TeamID");
       // progressBar        = getActivity().findViewById(R.id.Progress_bar);
        TeamClubName       = getActivity().findViewById(R.id.Team_club_name);
        TeamStaduimName    = getActivity().findViewById(R.id.Team_Statduim_name);
        Team_image         = getActivity().findViewById(R.id.team_image_view);
        Team_Back_image    = getActivity().findViewById(R.id.Team_Back_image);
        //Vist and social button
        button_website = v.findViewById(R.id.button_official_web);
        button_app = v.findViewById(R.id.button_official_app);
        button_club_ticket = v.findViewById(R.id.button_club_ticket_info);
        button_statidumt = v.findViewById(R.id.button_statdium);
        //socailbutt
        button_facebook = v.findViewById(R.id.button_facebook);
        button_twiiter = v.findViewById(R.id.button_twiiter);
        button_instagram = v.findViewById(R.id.button_instagram);
        button_youtube = v.findViewById(R.id.button_youtube);
        //button clubkit
        home_button_buy = v.findViewById(R.id.home_button_buy);
        away_button_buy = v.findViewById(R.id.away_button_buy);
        third_button_buy = v.findViewById(R.id.third_button_buy);
        //image clubkit
        image_view_home_kit = v.findViewById(R.id.image_view_home_kit);
        image_view_away_kit = v.findViewById(R.id.image_view_away_kit);
        image_view_third_kit = v.findViewById(R.id.image_view_third_kit);




        TeamData(TeamID);

        return v;
    }



    private void TeamData(String TeamID){
        //ButtonEvents("Hello");
        switch (TeamID){
            case "1":
                ButtonEvents("https://www.premierleague.com/clubs/1/Arsenal/overview",
                        "com.arsenal.official", "https://www.arsenal.com/tickets?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Emirates+Stadium/@51.5548885,-0.108438,17z/data=!3m1!4b1!4m2!3m1!1s0x48761b7645295e3b:0x3600713c8382cf90?hl=en",
                        "https://www.facebook.com/arsenal/",
                        "https://twitter.com/Arsenal/",
                        "http://instagram.com/arsenal/",
                        "https://www.youtube.com/arsenal/");

                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/05/9806787b-4dc7-4de7-a07f-7c5085c036d5/arsenal-hk-1819-516x680-2-.png",
                        "https://arsenaldirect.arsenal.com/Football-Shirts-and-Kit/Home/c/home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/09/c500f8fc-11b2-42b0-b97f-b00deb1ad9a9/arsenal-ak-1819-516x680.png",
                        "https://arsenaldirect.arsenal.com/Football-Shirts-and-Kit/Away/c/away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/03/1fd9e1fb-6737-46d2-948f-6450c9c32ce3/arsenal-tk-1819-516x690.png",
                        "https://arsenaldirect.arsenal.com/Football-Shirts-and-Kit/Third/c/third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );

                UpdateAppBarTeamInfo("Arsenal","Emirates Stadium"
                          ,"https://futaa.com/images/full/arsenal_9.jpg");
                Team_image.setImageResource(R.drawable.ic_logo_arsenal_club);
                break;
            case "127"://bour
                button_app.setVisibility(View.GONE);
                ButtonEvents("https://www.premierleague.com/clubs/127/Bournemouth/overview",
                        "",
                        "https://www.afcb.co.uk/tickets/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Goldsands+Stadium+at+Dean+Court/@50.7353289,-1.8386321,17z/data=!3m1!4b1!4m2!3m1!1s0x48739f479c5b9137:0x95f99d19f249ebd9?hl=en",
                        "https://www.facebook.com/afcbournemouth/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/afcbournemouth/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/officialafcb/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/afcbournemouth/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");

                UpdateAppBarTeamInfo("AFC Bournemouth","Vitality Stadium"
                        ,"https://www.thetimes.co.uk/imageserver/image/methode%2Ftimes%2Fprod%2Fweb%2Fbin%2F0bf06a48-2a06-11e8-bb7d-85110f4c5caa.jpg?crop=2250%2C1266%2C0%2C117&resize=685");


                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/14411239-f497-45cc-9faa-4e26d178b978/bournemouth-hk-1819-516x680-4-.png",
                        "https://superstore.afcb.co.uk/afc-bournemouth/home-kit-8?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/18c35f75-2e3f-443e-84e9-ae6c950bcf0f/bournemouth-ak-1819-516x680-2-.png",
                        "https://superstore.afcb.co.uk/afc-bournemouth/away-kit-9?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/56c7b7a4-db1e-4229-88c8-9132e33d8dd8/bournemouth-tk-1819-516x690-4-.png",
                        "https://superstore.afcb.co.uk/afc-bournemouth/third-kit-10?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );




                Team_image.setImageResource(R.drawable.ic_logo_afc_bournemiuth);
                break;
            case "131":
                ButtonEvents("https://www.brightonandhovealbion.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "uk.co.tribehive.fli.albion",
                        "https://www.brightonandhovealbion.com/tickets?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/American+Express+Community+Stadium/@50.8615651,-0.085905,17z/data=!3m1!4b1!4m5!3m4!1s0x487588b0cf7e6a99:0x865c543da10d7abc!8m2!3d50.8615651!4d-0.0837163",
                        "https://www.facebook.com/officialbhafc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/OfficialBHAFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.instagram.com/officialbhafc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/officialbhafc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo("Brighton and Hove Albion","Amex Stadium"
                        ,"https://d2x51gyc4ptf2q.cloudfront.net/content/uploads/2017/09/11084018/Pascal-Gross-Brighton.jpg");

                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/7cc81b1b-f2c1-46bb-9ddb-bcd2b5e9f15b/brighton-hk-1819-516x680-3-.png",
                        "https://www.seagullsdirect.co.uk/kit/home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/794d56b5-f919-47f8-bcea-cd34fcc3d849/brighton-ak-1819-516x680-2-.png",
                        "https://www.seagullsdirect.co.uk/kit/away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/9a142789-5d42-412e-bf4d-fc626d17331c/brighton-tk-1819-516x690-2-.png",
                        "https://www.seagullsdirect.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );

                Team_image.setImageResource(R.drawable.ic_logo_brighton_hove_albion);
                break;
            case "43":
                ButtonEvents("http://www.burnleyfootballclub.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.incrowdsports.football.burnley",
                        "https://www.burnleyfootballclub.com/tickets/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Turf+Moor/@53.7890244,-2.2323623,17z/data=!3m1!4b1!4m5!3m4!1s0x487b96d9615b89d9:0x407db5bf9409f3a0!8m2!3d53.7890244!4d-2.2301736",
                        "https://www.facebook.com/officialburnleyfc/",
                        "https://twitter.com/BurnleyOfficial/",
                        "https://www.instagram.com/burnleyofficial/",
                        "https://www.youtube.com/user/officialburnleyfc/");
                UpdateAppBarTeamInfo("Burnley","Turf Moor"
                        ,"https://media.minutemediacdn.com/process?url=http%3A%2F%2F90min-images-original.s3.amazonaws.com%2Fproduction%2F5a993d75197e8aa8db000001.jpg&filters%5Bcrop%5D%5Bw%5D=0.9986321195144724&filters%5Bcrop%5D%5Bh%5D=0.8405357142857143&filters%5Bcrop%5D%5Bo_x%5D=0.0&filters%5Bcrop%5D%5Bo_y%5D=0.08285714285714285&filters%5Bquality%5D%5Btarget%5D=80&type=.jpg&filters%5Bresize%5D%5Bw%5D=578&filters%5Bresize%5D%5Bh%5D=325");

                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/3aee127e-1b7a-4cbf-b8e1-aac9c43b1e0e/burnley-hk-1819-516x680.png",
                        "https://www.burnleyfc.talent-sport.co.uk/PagesPublic/ProductBrowse/browse04.aspx?group1=myteam&group2=BurnleyFCOfficalRepl&group3=HomeKit&utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/03/944357b2-da47-4ba7-aee6-2cc59f30ee7a/burnley-ak-1819-516x680.png",
                        "https://www.burnleyfc.talent-sport.co.uk/PagesPublic/ProductBrowse/browse04.aspx?group1=myteam&group2=BurnleyFCOfficalRepl&group3=AwayKit&utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/863559b8-4cf4-44ef-b32a-46e044303aa4/burnley-tk-1819-516x680.png",
                        "https://www.burnleyfc.talent-sport.co.uk/PagesPublic/UserControlled/UserDefined.aspx?page=claretsstore&utm_source=premier-league-website&utm_campaign=website&utm_medium=link&soalready=SJa9fi9feOLGjsQWfkeaAmJb2c0/VJG+NSNr4AJnYeg=" );
                Team_image.setImageResource(R.drawable.ic_logo_burnley);
                break;
            case "46":
                ButtonEvents("http://www.cardiffcityfc.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "uk.co.tribehive.fli.cardiff",
                        "https://www.eticketing.co.uk/cardiffcity?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Cardiff+City+Football+Club/@51.4728226,-3.2052009,17z/data=!3m1!4b1!4m5!3m4!1s0x486e04b49609ff53:0xe88c096f452e4380!8m2!3d51.4728226!4d-3.2030122",
                        "https://www.facebook.com/cardiffcityfc",
                        "https://twitter.com/CardiffCityFC",
                        "http://instagram.com/cardiffcityfc",
                        "http://www.youtube.com/user/BluebirdsPlayer");
                UpdateAppBarTeamInfo("Cardiff City","Cardiff City Stadium"
                        ,"https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/06/04/52c2debf-96f7-43ca-8905-3224cdb2f6a5/GettyImages-844911978.jpg");

                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/aa5ab8c7-2c2f-4adf-a58a-bf81d8208084/cardiff-hk-1819-516x680-3-.png",
                        "https://www.cardiffcityfcstore.com/replica-kit/home-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/8467e45d-60c9-466a-bed4-bcd1aee874bb/cardiff-ak-1819-516x680-2-.png",
                        "https://www.cardiffcityfcstore.com/replica-kit/away-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/4ca91039-86f4-4f83-ac10-b737286effcf/cardiff-tk-1819-516x690-3-.png",
                        "https://www.cardiffcityfcstore.com/replica-kit/third-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );

                Team_image.setImageResource(R.drawable.ic_logo_cardiff_city_fc);
                break;
            case "4"://chelease
                ButtonEvents("https://www.chelseafc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.chelseafc.the5thstand",
                        "http://www.chelseafc.com/tickets-membership/tickets-home.html/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Stamford+Bridge/@51.514902,-0.2302114,13.05z/data=!4m2!3m1!1s0x48760f864b976f3d:0x48aa38781ea565f8?hl=en",
                        "https://www.facebook.com/ChelseaFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/chelseafc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/chelseafc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/chelseafc/?utm_source=www.premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo("Chelsea","Stamford Bridge"
                        ,"https://cdn.images.express.co.uk/img/dynamic/67/590x/Chelsea-news-Marcos-Alonso-Eden-Hazard-Real-Madrid-1013209.jpg?r=1536135415834");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/b5ea3f73-6344-4609-90e4-7badc973f87d/chelsea-hk-1819-516x680-1-.png",
                        "http://www.chelseamegastore.com/stores/chelsea/en/c/kits/home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/31b26e6f-882d-4ab9-acd0-92e6721f133e/chelsea-ak-1819-516x680.png",
                        "http://www.chelseamegastore.com/stores/chelsea/en/c/kits/away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/09/05/24d8f3ef-9228-45e4-98db-459cf9413466/chelsea-tk-1819-516x690.png",
                        "http://www.chelseamegastore.com/stores/chelsea/en/c/kits/third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_chelsea);
                break;
            case "6":
                ButtonEvents("http://www.cpfc.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "uk.co.tribehive.fli.palace",
                        "https://www.cpfc.co.uk/tickets/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Selhurst+Park+Stadium/@51.3982802,-0.0854849,17z/data=!3m1!4b1!4m2!3m1!1s0x4876012e0f151107:0x4d1cac0798cb40c?hl=en",
                        "https://www.facebook.com/officialcpfc/",
                        "https://twitter.com/CPFC/",
                        "http://instagram.com/cpfc/",
                        "https://www.youtube.com/user/OfficialCPFC/");
                UpdateAppBarTeamInfo("Crystal Palace","Selhurst Park"
                        ,"https://i2-prod.mirror.co.uk/incoming/article6651185.ece/ALTERNATES/s615b/Crystal-Palace-vs-West-Ham.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/71041c89-e1ea-4800-9a81-805e1ba5f2a9/crystalpalace-hk-1819-516x680-3-.png",
                        "https://shop.cpfc.co.uk/1819-puma-kit/home-kit-1819/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/ae97843a-5d00-4662-acc9-957e77c99f03/crystalpalace-ak-1819-516x680-1-.png",
                        "https://shop.cpfc.co.uk/1819-puma-kit/away-kit-1819/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/06/01/35760cd0-c616-4388-8c80-a4075a39d310/PL-1819-Blank-516.png",
                        "https://shop.cpfc.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_crystal_palace);
                break;
            case "7"://everton
                ButtonEvents("http://www.evertonfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.urbanzoo.everton.release",
                        "https://www.premierleague.com/clubs/7/Everton/tickets",
                        "https://www.google.co.in/maps/place/Goodison+Park/@53.438787,-2.968508,17z/data=!3m1!4b1!4m5!3m4!1s0x487b216216d95ae1:0x3778ada7c9847359!8m2!3d53.438787!4d-2.9663193",
                        "https://www.facebook.com/Everton/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/everton/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://instagram.com/everton/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/OfficialEverton/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo("Everton","Goodison Park"
                        ,"https://i2-prod.dailypost.co.uk/incoming/article15100474.ece/ALTERNATES/s615b/0_JS161163693.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/91e3eb21-21a5-44d5-a8ac-ffb85c381628/everton-hk-1819-516x680-1-.png",
                        "http://evertondirect.evertonfc.com/stores/everton/en/c/football-kits/2018-19-home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/3e1daf91-49f6-4b2b-8f05-4ead6435b70c/everton-ak-1819-516x680.png",
                        "http://evertondirect.evertonfc.com/stores/everton/en/c/football-kits/2018-19-away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/24/edaf973d-884f-419e-bd78-f241408743c3/everton-tk-1819-516x690-2-.png",
                        "http://evertondirect.evertonfc.com/stores/everton/en/c/football-kits/2018-19-third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_everton);
                break;
            case "34"://fulham
                ButtonEvents("http://www.fulhamfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.fulhamfc.mobile",
                        "https://www.eticketing.co.uk/fulhamfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Craven+Cottage/@51.4748946,-0.2239686,17z/data=!3m1!4b1!4m5!3m4!1s0x48760fa7a4488157:0xa0377f648d942d05!8m2!3d51.4748946!4d-0.2217799",
                        "http://www.facebook.com/FulhamFC",
                        "https://twitter.com/FulhamFC",
                        "http://instagram.com/fulhamfc",
                        "http://www.youtube.com/user/FulhamFC1987Ltd");
                UpdateAppBarTeamInfo("Fulham","Craven Cottage"
                        ,"https://static.independent.co.uk/s3fs-public/thumbnails/image/2018/02/17/17/ryan-sessegnon.jpg?w968");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/38d870e3-f053-4c60-a3c0-0489f8f6c20c/fulham-hk-1819-516x680-4-.png",
                        "https://shop.fulhamfc.com/kits/home-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/c0a00b49-8377-4003-a797-f573dc1b4a73/fulham-ak-1819-516x680-2-.png",
                        "https://shop.fulhamfc.com/kits/away-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/09/04/865965a2-3235-4acd-9621-914ea31cc70b/fulham-tk-1819-516x690.png",
                        "https://shop.fulhamfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_fulham);
                break;
            case "159"://huddersfield
                ButtonEvents("https://www.htafc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "uk.co.tribehive.fli.huddersfield",
                        "https://www.htafc.com/tickets/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/John+Smith's+Stadium/@53.6542822,-1.7704404,17z/data=!3m1!4b1!4m5!3m4!1s0x487bdc16d80f7909:0xd270c7acf78e78a!8m2!3d53.6542822!4d-1.7682517",
                        "https://www.facebook.com/htafcdotcom/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/htafcdotcom/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.instagram.com/htafcinstagram/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/OfficialHTAFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo("Huddersfield Town","John Smith's Stadium"
                        ,"https://i2-prod.mirror.co.uk/incoming/article9865109.ece/ALTERNATES/s615/Huddersfield-Town-v-Reading-Sky-Bet-Championship.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/2046205a-b489-44d3-acbd-3da59838319d/huddersfield-hk-1819-516x680-1-.png",
                        "https://www.htafcmegastore.com/kit/home-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/12/fa57fe32-89a9-4613-8a65-85729de3d900/huddersfield-ak-1819-516x680.png",
                        "https://www.htafcmegastore.com/kit/alternative-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/4a2352f8-b3f4-4dde-8b40-ae1f20675f01/huddersfield-tk-1819-516x690.png",
                        "https://www.htafcmegastore.com/kit/alternative-kit2/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.logo_huddersfield);
                break;
            case "26"://Leincester
                button_app.setVisibility(View.GONE);
                ButtonEvents("http://www.lcfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "",
                        "https://tickets.lcfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/King+Power+Stadium/@52.6203662,-1.1443782,17z/data=!3m1!4b1!4m5!3m4!1s0x487760d73d562bcb:0xcd89e0170e2399aa!8m2!3d52.6203662!4d-1.1421895",
                        "http://www.facebook.com/lcfc/",
                        "https://twitter.com/LCFC/",
                        "https://www.instagram.com/lcfc/",
                        "https://www.youtube.com/user/LCFCOfficial/");
                UpdateAppBarTeamInfo("Leicester City","King Power Stadium"
                        ,"https://d2x51gyc4ptf2q.cloudfront.net/content/uploads/2017/04/18211243/Leicester-Football3651.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/0c36611a-8eae-4e0a-8a54-0ce183426132/Leicester-hk-1819-516x680-3-.png",
                        "https://shop.lcfc.com/kits/new-18-19-home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/317a71f6-3c1f-4ec6-b8d1-d9f4906171ab/leicester-ak-1819-516x680-1-.png",
                        "https://shop.lcfc.com/kits/new-18-19-away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/0a083a7d-6ff7-47f6-86a0-c0acf03ac6e5/leicester-tk-1819-516x690-1-.png",
                        "https://shop.lcfc.com/kits/new-18-19-third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_leicester_city);
                break;
            case "10"://Llevirpool

                ButtonEvents("http://www.liverpoolfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "tv.liverpoolfc",
                        "https://tickets.liverpoolfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Anfield/@53.4308294,-2.9630187,17z/data=!3m1!4b1!4m5!3m4!1s0x487b21654b02538b:0x84576a57e21973ff!8m2!3d53.4308294!4d-2.96083",
                        "https://www.facebook.com/LiverpoolFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/lfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://instagram.com/liverpoolfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://www.youtube.com/liverpoolfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                         "Liverpool",
                         "Anfield"
                        ,"https://cdn.images.express.co.uk/img/dynamic/67/590x/Liverpool-954060.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/bd40d0a8-6190-40e1-89e0-acf779c1ff81/Liverpool-hk-1819-516x680-2-.png",
                              "https://store.liverpoolfc.com/kit/home-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                              "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/09/3ab6b581-f4be-4744-ace1-6e57a3d29f66/Liverpool-ak-1819-516x680.png",
                              "https://store.liverpoolfc.com/kit/away-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                              "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/16/1181ded8-e5a6-4b25-a694-afbca512ab9d/liverpool-tk-1819-516x690.png",
                              "https://store.liverpoolfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_liverpool);
                break;
            case "11"://Manchester city
                //button_app.setVisibility(View.GONE);
                ButtonEvents("http://www.mancity.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.mancity",
                        "https://tickets.mancity.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Etihad+Stadium/@53.4831381,-2.202584,17z/data=!3m1!4b1!4m5!3m4!1s0x487bb10dcc950ae3:0x549a8dcce67a876a!8m2!3d53.4831381!4d-2.2003953",
                        "http://www.facebook.com/mancity/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/ManCity/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://instagram.com/mancity/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/mcfcofficial/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Manchester City",
                        "Etihad Stadium"
                        ,"https://www.sportstarlive.com/third-party/opta/article24842712.ece/alternates/FREE_550/manchestercity-cropped1ftix6xifwkqj1ady1uqqbud5bjpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/dfeb7038-9591-4c97-ad13-7d3a2f4f3f7c/mancity-hk-1819-516x680-2-.png",
                        "http://shop.mancity.com/stores/mancity/en/c/football-kits/home-kit-2018-19?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/24/74dcf066-2a58-453b-a753-555cbaefa74d/mancity-ak-1819-516x680-2-.png",
                        "http://shop.mancity.com/stores/mancity/en/c/football-kits/away-kit-2018-19?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/09/04/4bc0db89-c30c-4f03-a89f-980fcc1fdd38/mancity-tk-1819-516x690.png",
                        "http://shop.mancity.com/stores/mancity/en/c/football-kits/third-kit-2018-19?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_manchester_city);
                break;
            case "12"://Manchester United
               // button_app.setVisibility(View.GONE);
                ButtonEvents("http://www.manutd.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "com.mu.muclubapp",
                        "https://www.eticketing.co.uk/muticketsandmembership/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.uk/maps/place/Old+Trafford/@53.4630589,-2.2913401,17z/data=!3m1!4b1!4m2!3m1!1s0x487bae72e7e47f69:0x6c930e96df4455fe?hl=en",
                        "https://www.facebook.com/manchesterunited/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/ManUtd/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://instagram.com/manchesterunited/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/manutd/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Manchester United",
                        "Old Trafford"
                        ,"https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/01/15/df729da7-7cf3-46dd-9666-facd64bb8a80/GettyImages-905309606.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/17/ed44a9b6-79a5-4554-9d16-730d4ebf8761/manutd-hk-1819-516x680.png",
                        "http://store.manutd.com/stores/manutd/en/c/football-kits/home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/28/2b5c42be-55cc-4654-acd3-74d03cabc1cd/manutd-ak-1819-516x680-1-.png",
                        "http://store.manutd.com/stores/manutd/en/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/18/5308bdba-82b7-4d9f-b060-8d9334b03654/manutd-tk-1819-516x680-NewSponsor-1-.png",
                        "http://store.manutd.com/stores/manutd/en/c/football-kits/third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_manchester_united);
                break;
            case "23"://New Castle
                button_app.setVisibility(View.GONE);
                ButtonEvents("http://www.nufc.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "",
                        "https://www.premierleague.com/clubs/23/Newcastle-United/tickets",
                        "https://www.google.co.in/maps/place/St.+James'+Park/@54.9758844,-1.6211515,17.29z/data=!4m8!1m2!2m1!1sNewcastle+United!3m4!1s0x487e7734dc20d87b:0x2e911404fa537b88!8m2!3d54.975556!4d-1.621667",
                        "http://www.facebook.com/newcastleunited/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/NUFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/nufc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "http://www.youtube.com/NUFCOfficial1892/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Newcastle United",
                        "St. James' Park"
                        ,"https://upload.wikimedia.org/wikipedia/commons/c/ce/Metalist-Newcastl_%285%29.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/06/80e775d6-bb35-4ea0-b0b0-e4192cf916ec/newcastle-hk-1819-516x680.png",
                        "http://www.nufcdirect.com/replica-kits/home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/18/4e8a9243-618c-4462-8ba1-1b131b3ebddc/newcastle-ak-1819-516x680-1-.png",
                        "https://www.nufcdirect.com/replica-kits/change-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/ce0db5bf-d4c8-4861-bb34-04d6cb94cbaa/newcastle-tk-1819-516x690.png",
                        "https://www.nufcdirect.com/replica-kits/third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_newcastle_united);
                break;
            case "20"://Southampton
                button_app.setVisibility(View.GONE);
                ButtonEvents("https://www.southamptonfc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "",
                        "https://southamptonfc.com/tickets/match-tickets/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/St+Mary's+Stadium/@50.9058218,-1.3931427,17z/data=!3m1!4b1!4m5!3m4!1s0x4874714eaeec3fad:0x3c60fdf6c8f2e919!8m2!3d50.9058218!4d-1.390954",
                        "https://www.facebook.com/southamptonfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/southamptonfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/southamptonfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/theofficialsaints/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Southampton",
                        "St. Mary's Stadium"
                        ,"https://i2-prod.mirror.co.uk/incoming/article7924995.ece/ALTERNATES/s615/Tottenham-v-Southampton.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/86b1f3e3-dc84-4614-88dd-6076087e8015/southampton-hk-1819-516x680-3-.png",
                        "http://store.saintsfc.co.uk/Product/ProductList/53200?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/241c97df-6358-4259-9bc3-d52821858c20/southampton-ak-1819-516x680-2-.png",
                        "http://store.saintsfc.co.uk/Home/Index?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/03/44c97352-baa4-4bb5-876f-7ce348aef010/southampton-tk-1819-516x690.png",
                        "http://store.saintsfc.co.uk/Product/ProductList/54700?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_southampton);
                break;
            case "21"://tottenhamhotspur

                ButtonEvents("http://www.tottenhamhotspur.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "uk.co.spurs",
                        "https://www.eticketing.co.uk/tottenhamhotspur/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Wembley+Stadium/@51.5560208,-0.2817075,17z/data=!3m1!4b1!4m5!3m4!1s0x48761181d57a876d:0xa64f9f185de8e097!8m2!3d51.5560208!4d-0.2795188",
                        "https://www.facebook.com/TottenhamHotspur/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/SpursOfficial/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/spursofficial/?ref=badge/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/spursofficial/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Tottenham Hotspur",
                        "Tottenham Hotspur Stadium"
                        ,"https://s.hs-data.com/bilder/teamfotos/640x360/552.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/19/20c8305c-303a-4599-bfcd-7f020adb660d/spurs-hk-1819-516x680.png",
                        "http://shop.tottenhamhotspur.com/category/spurs-home-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/19/5e9da4c1-4114-40de-81fa-50f4a70e72c3/tottenham-ak-1819-516x680.png",
                        "http://shop.tottenhamhotspur.com/category/spurs-away-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/20/2f5b7076-a0ad-4023-b790-7f225d829a37/spurs-tk-1819-516x690-1-.png",
                        "https://shop.tottenhamhotspur.com/category/spurs-third-kit?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_tottenham_hotspur);
                break;
            case "33"://watford
                button_app.setVisibility(View.GONE);
                ButtonEvents("",
                        "",
                        "https://www.eticketing.co.uk/watfordfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Vicarage+Road+Stadium/@51.6508247,-0.4028811,17.83z/data=!4m8!1m2!2m1!1sVicarage+Road!3m4!1s0x48766add425fce2b:0xf9781a1803f93563!8m2!3d51.6499059!4d-0.401525",
                        "https://www.facebook.com/watfordfc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/WatfordFC/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.instagram.com/watfordfcofficial/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/user/WatfordFCofficial/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "Watford",
                        "Vicarage Road"
                        ,"https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2017/09/12/c0e8565b-b831-41e8-88f7-53aa0153e259/Watford-Digital-Membership-17-18.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/175c75a6-e7c2-4e5d-8ff0-b7ce0484f617/watford-hk-1819-516x680-4-.png",
                        "https://www.thehornetsshop.co.uk/kit/home-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/23/ad8b230b-6b03-4fe4-aa78-a72fbd284f2e/watford-ak-1819-516x680-2-.png",
                        "https://www.thehornetsshop.co.uk/kit/away-kit/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/06/01/35760cd0-c616-4388-8c80-a4075a39d310/PL-1819-Blank-516.png",
                        "https://www.thehornetsshop.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_watford);
                break;
            case "25"://west ham
                ButtonEvents("http://www.whufc.com/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "de.elasticbrains.whu",
                        "https://www.eticketing.co.uk/whufc/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/London+Stadium/@51.5387095,-0.0187924,17z/data=!3m1!4b1!4m5!3m4!1s0x48761d6975e8b559:0xe7fca44605b6ce94!8m2!3d51.5387095!4d-0.0166037",
                        "https://www.facebook.com/westhamunited/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://twitter.com/westhamutd/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://instagram.com/westham/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.youtube.com/channel/UCCNOsmurvpEit9paBOzWtUg/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link");
                UpdateAppBarTeamInfo(
                        "West Ham United",
                        "London Stadium"
                        ,"https://s.hs-data.com/bilder/teamfotos/640x360/553.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/18/12b2517a-c49e-4aad-8187-3a56f0adb5d9/westham-hk-1819-516x680-UpdatedSponsor-3-.png",
                        "https://www.officialwesthamstore.com/kits/home-kit-201819/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/19/48e2694b-a181-493f-8084-7991cf250152/westham-ak-1819-516x680.png",
                        "https://www.officialwesthamstore.com/kits/away-kit-201819/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/08/03/cfcb0a93-6f3f-46cc-999d-0ce08a56f3c4/Westham-tk-1819-516x690-1-.png",
                        "https://www.officialwesthamstore.com/kits/third-kit-201819/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_west_ham_united);
                break;
            case "38"://Leincester
                button_app.setVisibility(View.GONE);
                ButtonEvents("http://www.wolves.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "",
                        "https://www.wolvestickets.co.uk/?utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://www.google.co.in/maps/place/Molineux+Stadium/@52.5902362,-2.1326175,17z/data=!3m1!4b1!4m5!3m4!1s0x48709b8575eeb52b:0x784fa301fcde3bf1!8m2!3d52.5902362!4d-2.1304288",
                        "https://www.facebook.com/Wolves/",
                        "https://twitter.com/Wolves",
                        "https://www.instagram.com/wolves/?hl=en",
                        "https://www.youtube.com/user/OfficialWolvesVideo");
                UpdateAppBarTeamInfo(
                        "Wolverhampton Wanderers",
                        "Molineux Stadium"
                        ,"https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/06/04/ca61ad32-dbac-48e4-bb1a-fb71eaf43425/GettyImages-952397000.jpg");
                UpdateClubKit("https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/33f7e846-7b92-409b-ab24-2d79a836cb0f/wolves-hk-1819-516x680-3-.png",
                        "https://www.medocmall.co.uk/cgi-bin/live/ecommerce.pl?site=theclubshop_wolves&state=products&dept_id=10&sub_dept_id=10&utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/07/31/b7b764d8-7490-4e59-9ec3-ea1ff38ddff3/wolves-ak-1819-516x680-1-.png",
                        "https://www.medocmall.co.uk/cgi-bin/live/ecommerce.pl?site=theclubshop_wolves&state=products&dept_id=10&sub_dept_id=20&utm_source=premier-league-website&utm_campaign=website&utm_medium=link",
                        "https://premierleague-static-files.s3.amazonaws.com/premierleague/photo/2018/06/01/35760cd0-c616-4388-8c80-a4075a39d310/PL-1819-Blank-516.png",
                        "https://www.medocmall.co.uk/cgi-bin/live/ecommerce.pl?site=theclubshop_wolves&state=page&page=splash&suppress_header=yes&utm_source=premier-league-website&utm_campaign=website&utm_medium=link" );
                Team_image.setImageResource(R.drawable.ic_logo_wolverhampton);
                break;


        }

    }






    //Handle All buttonEvents
    public void ButtonEvents(final String webisteLink, final String appLink, final String clubTicket, final String StadiumLink,
                             final String FacebookLink, final String twitterLink
                           , final String instagramLink, final String youtubeLink){

        button_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getContext(), "Toast is working " + webisteLink , Toast.LENGTH_SHORT).show();
                String url = webisteLink;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });
        button_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //appLink
                // Toast.makeText(getContext(), "Toast is working " + "https://www.premierleague.com/clubs/1/Arsenal/overview", Toast.LENGTH_SHORT).show();
                launchMarket(appLink);
            }
        });
        button_club_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =clubTicket;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        button_statidumt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.google.co.uk/maps/place/Emirates+Stadium/@51.5548885,-0.108438,17z/data=!3m1!4b1!4m2!3m1!1s0x48761b7645295e3b:0x3600713c8382cf90?hl=en
                String strUri = StadiumLink;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        button_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = FacebookLink;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = instagramLink;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button_twiiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = twitterLink;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = youtubeLink;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



    }


    //Upate ClubKit info  ImageView  image_view_home_kit,image_view_away_kit,image_view_third_kit;
    public void UpdateClubKit(String HomeT_ShirtLink, final String BuyHomeT_Shirt, String AwayT_ShirtLink, final String BuyAwayT_Shirt,
                              String ThirdT_ShirtLink, final String BuyThirdT_Shirt){
        Picasso.with(getContext()).load(HomeT_ShirtLink).into(image_view_home_kit);
        Picasso.with(getContext()).load(AwayT_ShirtLink).into(image_view_away_kit);
        Picasso.with(getContext()).load(ThirdT_ShirtLink).into(image_view_third_kit);


        home_button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BuyHomeT_Shirt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        away_button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BuyAwayT_Shirt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        third_button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BuyThirdT_Shirt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }

    //Update TeamAppBar
    //TextView TeamClubName,TeamStaduimName;
//ImageView Team_Back_image,Team_image;
    public void UpdateAppBarTeamInfo(String TeamName,String  StadiumName,String TeamBackImageLink){
        TeamClubName.setText(TeamName);
        getActivity().setTitle(TeamName);
        TeamStaduimName.setText(StadiumName);
        Picasso.with(getContext()).load(TeamBackImageLink).into(Team_Back_image);
    }


    //Takes users to app location in googlePlay
    private void launchMarket(String Applink) {
        Uri uri = Uri.parse("market://details?id=" + Applink);
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
