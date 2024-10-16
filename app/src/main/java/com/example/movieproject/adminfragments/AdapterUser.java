package com.example.movieproject.adminfragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieproject.R;
import com.example.movieproject.database.tables.users;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolderUsers> implements View.OnClickListener{

    ArrayList<users> listUsers;
    private View.OnClickListener listener;

    public AdapterUser(ArrayList<users> listUsers) {
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public ViewHolderUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_user_view_admin,null,false);

        view.setOnClickListener(this);
        return new ViewHolderUsers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUsers holder, int position) {

        holder.dataId.setText(listUsers.get(position).getUserId().toString());
        holder.dataName.setText(listUsers.get(position).getName());
        holder.dataLastName.setText(listUsers.get(position).getLastName());
        holder.dataUsername.setText(listUsers.get(position).getUsername());
        holder.dataPassword.setText(listUsers.get(position).getPassword());
        holder.dataMoviesOwned.setText(listUsers.get(position).getMoviesOwned().toString());
        holder.dataMoneySpent.setText(listUsers.get(position).getMoneySpent().toString());

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderUsers extends RecyclerView.ViewHolder {

        TextView textId,textName,textLastName,textUsername,textPassword,textMoviesOwned,textMoneySpent,
                 dataId,dataName,dataLastName,dataUsername,dataPassword,dataMoviesOwned,dataMoneySpent;

        public ViewHolderUsers(@NonNull View itemView) {
            super(itemView);

            textId = (TextView) itemView.findViewById(R.id.textId);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textLastName = (TextView) itemView.findViewById(R.id.textLastName);
            textUsername = (TextView) itemView.findViewById(R.id.textUsername);
            textPassword = (TextView) itemView.findViewById(R.id.textPassword);
            textMoviesOwned = (TextView) itemView.findViewById(R.id.textMoviesOwned);
            textMoneySpent = (TextView) itemView.findViewById(R.id.textMoneySpent);

            dataId = (TextView) itemView.findViewById(R.id.dataId);
            dataName = (TextView) itemView.findViewById(R.id.dataName);
            dataLastName = (TextView) itemView.findViewById(R.id.dataLastName);
            dataUsername = (TextView) itemView.findViewById(R.id.dataUsername);
            dataPassword = (TextView) itemView.findViewById(R.id.dataPassword);
            dataMoviesOwned = (TextView) itemView.findViewById(R.id.dataMoviesOwned);
            dataMoneySpent = (TextView) itemView.findViewById(R.id.dataMoneySpent);
        }

    }
}
