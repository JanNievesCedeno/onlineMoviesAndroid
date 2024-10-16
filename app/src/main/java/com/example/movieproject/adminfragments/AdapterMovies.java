package com.example.movieproject.adminfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieproject.R;
import com.example.movieproject.database.tables.movies;

import java.util.ArrayList;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.ViewHolderMovies> implements View.OnClickListener {

    ArrayList<movies> listMovies;
    private View.OnClickListener listener;

    public AdapterMovies(ArrayList<movies> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public ViewHolderMovies onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_movies_view_admin,null,false);
        view.setOnClickListener(this);
        return new ViewHolderMovies(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovies holder, int position) {

        holder.dataId.setText(listMovies.get(position).getMovieId().toString());
        holder.dataAmount.setText(listMovies.get(position).getMovieAmount().toString());
        holder.dataName.setText(listMovies.get(position).getMovieName());
        holder.dataYear.setText(listMovies.get(position).getMovieYear());
        holder.dataGenre.setText(listMovies.get(position).getMovieGenre());
        holder.dataDescription.setText(listMovies.get(position).getMovieDescription());
        holder.dataCost.setText(listMovies.get(position).getMovieCost().toString());
        holder.dataPicture.setText(listMovies.get(position).getMoviePicture());
        holder.dataTrailer.setText(listMovies.get(position).getMovieTrailer());
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

    public class ViewHolderMovies extends RecyclerView.ViewHolder {

        TextView dataId,dataAmount,dataName,dataYear,dataGenre,dataDescription,dataCost,dataPicture,dataTrailer;

        public ViewHolderMovies(@NonNull View itemView) {
            super(itemView);

            dataId = (TextView) itemView.findViewById(R.id.dataMovieId);
            dataAmount = (TextView) itemView.findViewById(R.id.dataMovieAmount);
            dataName = (TextView) itemView.findViewById(R.id.dataMovieName);
            dataYear = (TextView) itemView.findViewById(R.id.dataMovieYear);
            dataGenre = (TextView) itemView.findViewById(R.id.dataMovieGenre);
            dataDescription = (TextView) itemView.findViewById(R.id.dataMovieDescription);
            dataCost = (TextView) itemView.findViewById(R.id.dataMovieCost);
            dataPicture = (TextView) itemView.findViewById(R.id.dataMoviePicture);
            dataTrailer = (TextView) itemView.findViewById(R.id.dataMovieTrailer);


        }
    }
}
