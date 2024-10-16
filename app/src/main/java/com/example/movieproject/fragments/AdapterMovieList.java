package com.example.movieproject.fragments;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieproject.R;
import com.example.movieproject.database.tables.movies;
import com.google.android.material.math.MathUtils;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AdapterMovieList extends RecyclerView.Adapter<AdapterMovieList.ViewHolderMovieList>  implements View.OnClickListener{

    ArrayList<movies> listMovies;
    ArrayList<movies> listTitle;
    private View.OnClickListener listener;

    public AdapterMovieList(ArrayList<movies> listMovies) {
        this.listMovies = listMovies;
        listTitle = new ArrayList<>();
        listTitle.addAll(listMovies);
    }

    @NonNull
    @Override
    public ViewHolderMovieList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,null,false);
        view.setOnClickListener(this);

        return new ViewHolderMovieList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovieList holder, int position) {

        Glide.with(holder.itemView).load(listMovies.get(position).getMoviePicture()).into(holder.dataImage);
        holder.dataName.setText(listMovies.get(position).getMovieName());
        holder.dataYear.setText(listMovies.get(position).getMovieYear());
        holder.dataGenre.setText(listMovies.get(position).getMovieGenre());
        holder.dataCost.setText(listMovies.get(position).getMovieCost().toString());

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderMovieList extends RecyclerView.ViewHolder {

        ImageView dataImage;
        TextView dataName,dataYear,dataGenre,dataCost;

        public ViewHolderMovieList(@NonNull View itemView) {
            super(itemView);

            dataImage = (ImageView) itemView.findViewById(R.id.itemMovieImage);
            dataName = (TextView) itemView.findViewById(R.id.itemMovieName);
            dataYear = (TextView) itemView.findViewById(R.id.itemMovieYear);
            dataGenre = (TextView) itemView.findViewById(R.id.itemMovieGenre);
            dataCost = (TextView) itemView.findViewById(R.id.itemMovieCost);

        }
    }

    public void filtrado(String title) {
        Integer longitud = title.length();
        notifyDataSetChanged();
        if (longitud == 0) {
            listMovies.clear();
            listMovies.addAll(listTitle);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<movies> colleccion = listMovies.stream().filter(i -> i.getMovieName().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
                listMovies.clear();
                listMovies.addAll(colleccion);

            } else {
                for (movies c : listTitle
                ) {
                    if (c.getMovieName().toLowerCase().contains(title.toLowerCase())) {
                        listMovies.add(c);
                    }
                }
            }
        }
            notifyDataSetChanged();

    }

}
