package com.example.movieproject.adminfragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieproject.CrudSales;
import com.example.movieproject.CrudUser;
import com.example.movieproject.R;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.Sales;
import com.example.movieproject.database.tables.users;

import java.util.ArrayList;


public class TableSales extends Fragment {
    ArrayList<Sales> listSale;
    RecyclerView recyclerSale;

    ConexionSQLiteHelper conn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_table_sales, container, false);

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);

        listSale = new ArrayList<>();

        recyclerSale = (RecyclerView) vista.findViewById(R.id.tableSalesRecyclerView);
        recyclerSale.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        consultarSales();

        AdapterSales adapter = new AdapterSales(listSale);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sales venta = listSale.get(recyclerSale.getChildAdapterPosition(view));

                Intent intentCrudSales = new Intent(getContext(), CrudSales.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sale",venta);
                intentCrudSales.putExtras(bundle);
                startActivity(intentCrudSales);
            }
        });
        recyclerSale.setAdapter(adapter);

        return vista;
    }

    private void consultarSales() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Sales venta=null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Constants.TABLE_SALES,null);

        while (cursor.moveToNext()){
            venta = new Sales();
            venta.setSalesId(cursor.getInt(0));
            venta.setUserId(cursor.getInt(1));
            venta.setMovieId(cursor.getInt(2));
            venta.setSalesDate(cursor.getString(3));
            venta.setPaymentMethod(cursor.getString(4));
            venta.setCardNumber(cursor.getString(5));
            venta.setCardExpiration(cursor.getString(6));
            venta.setCardName(cursor.getString(7));
            venta.setAmount(cursor.getFloat(8));

            listSale.add(venta);

        }

        db.close();
        conn.close();
    }
}