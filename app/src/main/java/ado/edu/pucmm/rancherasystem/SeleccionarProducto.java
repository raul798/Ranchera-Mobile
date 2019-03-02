package ado.edu.pucmm.rancherasystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.adapters.ProductSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.Product;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class SeleccionarProducto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DATABASE_NAME = "ranchera_database";
    private RancheraDB db;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AutoCompleteTextView productAutoComplete = findViewById(R.id.search_producto);
        products = new ArrayList<Product>();
        ProductSearchAdapter adapter = new ProductSearchAdapter(this,
                R.layout.product_search_dropdown, products);
        productAutoComplete.setAdapter(adapter);
        productAutoComplete.setOnItemClickListener(onItemClickListener);
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Product product = (Product) adapterView.getItemAtPosition(i);
                    //setText(R.id.name_clientes_text, client.getName());
                    //setText(R.id.phone_clientes_text, client.getPhoneNumber());
                    //setText(R.id.email_clientes_text, client.getEmail());
                    //setText(R.id.address_clientes_text, client.getAddress());
                    Toast.makeText(SeleccionarProducto.this, "", Toast.LENGTH_SHORT).show();
                }
            };



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seleccionar_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.helpButton) {

            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_resumen) {
            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);
        } else if (id == R.id.nav_productos) {

        } else if (id == R.id.nav_consultar_cliente) {

        } else if (id == R.id.nav_ordenes) {
            //do nothing
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toConsultarClientes(View view) {

        Intent intent = new Intent(this, ConsultarClientes.class);
        startActivity(intent);
    }

    public void toOrderResumen(View view) {

        Intent intent = new Intent(this, ResumenOrden.class);
        startActivity(intent);
    }
}
