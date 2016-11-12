package com.example.shruthir.flicks.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shruthir.flicks.Models.Movie;
import com.example.shruthir.flicks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by shruthir on 11/11/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context , List<Movie> movies)
    {
        super(context , android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);


        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (isLandspace() && isRatingLow(movie) )
                convertView = inflater.inflate(R.layout.item_movie_low_rating, parent, false);
            else
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        ivImage.setImageResource(0);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView textViewSummary = (TextView) convertView.findViewById(R.id.tvSummary);

        textViewTitle.setText(movie.getOriginalTitle());
        textViewSummary.setText(movie.getOverView());

        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        Picasso.with(getContext()).load(imageUri).into(ivImage);

        if (!isLandspace()) {
            with(getContext()).load(movie.getPosterPath()).into(ivImage);
        } else if (isLandspace()) {
            if (isRatingLow(movie))
                with(getContext()).load(movie.getPosterPath()).into(ivImage);
            else
                with(getContext()).load(movie.getBackdropPath()).into(ivImage);
        }
        return convertView;

    }

    public Boolean isLandspace()
    {
        int orientation = getContext().getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public Boolean isRatingLow(Movie movie)
    {
        return movie.getRating() <=5.0 ;
    }
}
