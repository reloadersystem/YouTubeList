package com.example.sistem03user.youtubeapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistem03user.youtubeapi.Adapter.MyCustomAdapter;
import com.example.sistem03user.youtubeapi.Model.VideoDetails;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    YouTubePlayerView youTubePlayerView;
    String claveyoutube= "AIzaSyBBoMapePV_AthrQPLWevncKB-RVw6QXtw";

    ListView listView;

    String API_KEY= "AIzaSyD9GZLfzFIdowg9dIJyb6jfgac3P6mRp1U";

    ArrayList<VideoDetails> videoDetailsArrayList;

    MyCustomAdapter myCustomAdapter;


    //String url="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLq_MGynXklvnlaed3VruS0dp1a7Qgpg4i&key=AIzaSyD9GZLfzFIdowg9dIJyb6jfgac3P6mRp1U";
    String url="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLq_MGynXklvnlaed3VruS0dp1a7Qgpg4i&key="+API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);

        videoDetailsArrayList=new ArrayList<>();


        myCustomAdapter=  new MyCustomAdapter(MainActivity.this, videoDetailsArrayList);

        displayVideos();

        listView.setOnItemClickListener(this);


    }

    private void displayVideos() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONObject jsonObject= new JSONObject((response));
                    JSONArray jsonArray= jsonObject.getJSONArray("items");


                   for(int i=0; i<jsonArray.length(); i++)
                   {
                       JSONObject jsonObject1= jsonArray.getJSONObject(i);
                       JSONObject jsonObjectSnippet=jsonObject1.getJSONObject("snippet");
                       JSONObject jsonObjectDefault= jsonObjectSnippet.getJSONObject("resourceId");
                       JSONObject thumbnails= jsonObjectSnippet.getJSONObject("thumbnails");
                       JSONObject medium= thumbnails.getJSONObject("medium");
                       String datourl= medium.getString("url");

                       String video_id= jsonObjectDefault.getString("videoId");
                       String title= jsonObjectSnippet.getString("title");
                       String description=jsonObjectSnippet.getString("description");


                       VideoDetails vd= new VideoDetails();
                       vd.setVideoId(video_id);
                       vd.setTitle(title);
                       vd.setDescription(description);
                       vd.setUrl(datourl);

                       videoDetailsArrayList.add(vd);


                   }

                   listView.setAdapter(myCustomAdapter);
                   myCustomAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

        );

        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


        {

            int  cantidadItemList= myCustomAdapter.getCount();

            VideoDetails videoDetails= (VideoDetails)myCustomAdapter.getItem(position);

            switch (position)
            {
                case 0:

                    Toast.makeText(MainActivity.this,position+" ID: " + videoDetails.getVideoId(),  Toast.LENGTH_SHORT).show();
                    break;

                case 1:

                    Toast.makeText(MainActivity.this,position+" ID: "+ videoDetails.getVideoId(),  Toast.LENGTH_SHORT).show();
                    break;

                case 2:

                    Toast.makeText(MainActivity.this,position+" ID: "+ videoDetails.getVideoId(),  Toast.LENGTH_SHORT).show();
                    break;

            }

        }

    }
}
