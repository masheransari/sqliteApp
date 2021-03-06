package com.example.android.sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.android.sqlite.Adapter.ProductDisplayAdapter;
import com.example.android.sqlite.Database.ProductContract;
import com.example.android.sqlite.Database.Productdphelper;
import com.example.android.sqlite.Model.ProductDetail;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Productdphelper mproductdphelper = new Productdphelper(this);

    @Override
    protected void onStart() {

        super.onStart();

        ListView listView = (ListView) findViewById(R.id.list);

        ProductDisplayAdapter mproductDisplayAdapter = new ProductDisplayAdapter(this,displayDatabaseInfo());

        listView.setAdapter(mproductDisplayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"adsada",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, GetProductDetail.class);
                startActivity(intent);
            }
        });
        ListView listView = (ListView) findViewById(R.id.list);

        ProductDisplayAdapter mproductDisplayAdapter = new ProductDisplayAdapter(this,displayDatabaseInfo());

        listView.setAdapter(mproductDisplayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteConfirmationDialog();

                        }
        });

    }

    private ArrayList<ProductDetail> displayDatabaseInfo () {

        // Create and/or open a database to read from it

        SQLiteDatabase db = mproductdphelper.getReadableDatabase();

        ArrayList<ProductDetail> arrayList = new ArrayList<>();

        // Define a projection that specifies which columns from the database

        // you will actually use after this query.

        String[] projection = {
                ProductContract.ProductEntry.PRODUCT_TITLE,
                ProductContract.ProductEntry.PRODUCT_price,
                ProductContract.ProductEntry.PRODUCT_QUANTITY,
        };

        // Perform a query on the product table

        Cursor cursor = db.query(
                ProductContract.ProductEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        try {

            // Figure out the index of each column

            int product_titleColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_TITLE);

            int product_priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_price);

            int product_quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.PRODUCT_QUANTITY);


            // Iterate through all the returned rows in the cursor

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.

                String product_currenttitle = cursor.getString(product_titleColumnIndex);

                int product_currentprice = cursor.getInt(product_priceColumnIndex);

                int product_currentquantity = cursor.getInt(product_quantityColumnIndex);

                arrayList.add(new ProductDetail(product_currenttitle,product_currentprice,product_currentquantity));

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
        return arrayList;

    }

    private void showDeleteConfirmationDialog(){
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
    //            deletePet();
                Toast.makeText(MainActivity.this,"adsada",Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton(R.string.sale, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.sale_layout, null);
                    final EditText edt = (EditText) view.findViewById(R.id.edit_saleOrder);
                    builder.setTitle("Sale");
                    builder.setMessage("Write the Quantity to Sale ...?");
                    builder.setPositiveButton("Sale", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this,"sale",Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.setNegativeButton("Delete Item", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setView(view);
                    builder.create().show();
                }
               }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
