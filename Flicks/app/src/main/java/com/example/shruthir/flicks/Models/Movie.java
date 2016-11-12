package com.example.shruthir.flicks.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shruthir on 11/11/16.
 */

public class Movie {

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w600/%s",backdropPath);
    }
    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public double getRating() {
        return rating;
    }

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overView;
    double rating;

    public Movie(JSONObject jsonObject) throws JSONException
    {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
    }

    public static ArrayList<Movie> parseMovieArray (JSONArray jsonArray)
    {
        ArrayList<Movie> results =  new ArrayList<Movie>();

        for(int i =0 ; i < jsonArray.length() ; i++)
        {
            try {
                Movie movie = new Movie(jsonArray.getJSONObject(i));
                if(!movie.getOriginalTitle().equals("Hacksaw Ridge"))
                    results.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
