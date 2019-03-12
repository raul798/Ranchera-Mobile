package ado.edu.pucmm.rancherasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ListIterator;

import ado.edu.pucmm.rancherasystem.adapters.ProductRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.adapters.ProductSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.Bill;
import ado.edu.pucmm.rancherasystem.db.Client;
import ado.edu.pucmm.rancherasystem.db.Detalle;
import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.Product;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;
import ado.edu.pucmm.rancherasystem.viewmodels.DetalleViewModel;
import ado.edu.pucmm.rancherasystem.viewmodels.FacturaViewModel;
import ado.edu.pucmm.rancherasystem.viewmodels.ProductViewModel;

public class SeleccionarProducto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DATABASE_NAME = "ranchera_database";
    private RancheraDB db;
    private List<Product> products;
    private List<Integer> amounts;
    private List<Integer> max;
    //private int amount;
    private AutoCompleteTextView productAutoComplete;
    private ProductViewModel productViewModel;
    private ProductRecyclerViewAdapter recylerAdapter;
    private int bill_id;
    private int client_id;
    private String client_name;
    private String client_phone;
    private String client_email;
    private String client_address;
    private Client client;
    private Detalle detalle;
    private DetalleViewModel detalleViewModel;
    final static int PRESET_AMOUNT = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recylerAdapter = new ProductRecyclerViewAdapter(this);
        recyclerView.setAdapter(recylerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //live data for recyclerView

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        productViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                // Update the cached copy of the words in the adapter.
            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        productAutoComplete = findViewById(R.id.search_producto);
        products = new ArrayList<Product>();
        amounts = new ArrayList<Integer>();
        max = new ArrayList<Integer>();
        ProductSearchAdapter adapter = new ProductSearchAdapter(this,
                R.layout.product_search_dropdown, products);
        productAutoComplete.setAdapter(adapter);
        productAutoComplete.setOnItemClickListener(onItemClickListener);

        detalleViewModel = ViewModelProviders.of(this).get(DetalleViewModel.class);

        //get factura id and client from client activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bill_id = extras.getInt("bill_id");
        }
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Product product = (Product) adapterView.getItemAtPosition(i);

                    boolean isThere = false;
                    for (Product product1 : products) {
                        if(product1.getName().equals(product.getName())){
                            isThere = true;
                        }
                    }

                    if(!isThere) {
                        products.add(product);
                        amounts.add(PRESET_AMOUNT);
                        max.add(PRESET_AMOUNT);
                        recylerAdapter.setProducts(products);
                        recylerAdapter.setAmounts(amounts);
                        recylerAdapter.setMax(max);
                    }
                }
            };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toConsultarClientes(View view) {

        Intent intent = new Intent(this, ConsultarClientes.class);
        startActivity(intent);
    }

    public void toOrderResumen(View view) {

        int index = 0;
        for (Product product1 : products){
            detalle = new Detalle(bill_id, product1.getId(), amounts.get(index));
            detalleViewModel.insert(detalle);
            index++;
        }

        Intent intent = new Intent(this, ResumenOrden.class);
        intent.putExtra("bill_id", bill_id);
        startActivity(intent);

    }
}
