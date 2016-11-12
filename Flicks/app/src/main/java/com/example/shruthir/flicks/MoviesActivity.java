package com.example.shruthir.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.shruthir.flicks.Adapter.MovieArrayAdapter;
import com.example.shruthir.flicks.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvItems;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
            }
        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        lvItems = (ListView)    findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);
        fetchTimelineAsync();

    }

    public void fetchTimelineAsync()
    {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient ();
        client.get (url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesResults = null;

                try {
                    moviesResults = response.getJSONArray("results");
                    Log.d("Results = %s", moviesResults.toString());
                    movies.addAll(Movie.parseMovieArray(moviesResults));
                    movieArrayAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, String responseString) {
                Log.d("Response = %s" ,responseString);
            }
        });
    }
}
