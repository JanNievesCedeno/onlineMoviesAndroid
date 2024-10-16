package com.example.movieproject.adminfragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieproject.R;
import com.example.movieproject.database.tables.Sales;

import java.util.ArrayList;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.ViewHolderSales> implements View.OnClickListener{

    ArrayList<Sales> listSales;
    private View.OnClickListener listener;

    public AdapterSales(ArrayList<Sales> listSales) {
        this.listSales = listSales;
    }

    @NonNull
    @Override
    public ViewHolderSales onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_sales_view_admin, null,false);

        view.setOnClickListener(this);
        return new ViewHolderSales(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSales.ViewHolderSales holder, int position) {

        holder.dataId.setText(listSales.get(position).getSalesId().toString());
        holder.dataUserId.setText(listSales.get(position).getUserId().toString());
        holder.dataMovieId.setText(listSales.get(position).getMovieId().toString());
        holder.dataDate.setText(listSales.get(position).getSalesDate());
        holder.dataPaymentMethod.setText(listSales.get(position).getPaymentMethod());
        holder.dataCardNumber.setText(listSales.get(position).getCardNumber());
        holder.dataCardExpiration.setText(listSales.get(position).getCardExpiration());
        holder.dataCardName.setText(listSales.get(position).getCardName());
        holder.dataAmount.setText(listSales.get(position).getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return listSales.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null) {
            listener.onClick(view);
        }
    }

    public class ViewHolderSales extends RecyclerView.ViewHolder {

        TextView dataId,dataUserId,dataMovieId,dataDate,dataPaymentMethod,dataCardNumber,dataCardExpiration,dataCardName,dataAmount;
        public ViewHolderSales(@NonNull View itemView) {
            super(itemView);

            dataId = (TextView) itemView.findViewById(R.id.dataSaleId);
            dataUserId = (TextView) itemView.findViewById(R.id.dataSaleUserId);
            dataMovieId = (TextView) itemView.findViewById(R.id.dataSaleMovieId);
            dataDate = (TextView) itemView.findViewById(R.id.dataSaleDate);
            dataPaymentMethod = (TextView) itemView.findViewById(R.id.dataSalePaymentMethod);
            dataCardNumber = (TextView) itemView.findViewById(R.id.dataSaleCardNumber);
            dataCardExpiration = (TextView) itemView.findViewById(R.id.dataSaleCardExpiration);
            dataCardName = (TextView) itemView.findViewById(R.id.dataSaleCardName);
            dataAmount = (TextView) itemView.findViewById(R.id.dataSaleAmount);
        }
    }
}
