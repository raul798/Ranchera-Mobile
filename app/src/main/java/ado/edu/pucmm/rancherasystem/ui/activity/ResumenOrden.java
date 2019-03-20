package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ProductsDetailsRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;

public class ResumenOrden extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int bill_id;
    private Bill bill;
    private Client client;
    private float total;
    private List<Integer> products_ids;
    private List<Integer> selected_amounts;
    private List<Product> products;
    private ProductsDetailsRecyclerViewAdapter recylerAdapter;
    private RanchDatabaseRepo ranchDatabaseRepo = new RanchDatabaseRepo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recylerAdapter = new ProductsDetailsRecyclerViewAdapter(this);
        recyclerView.setAdapter(recylerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bill_id = extras.getInt("bill_id");
        }

        total = 0;
        products = new ArrayList<Product>();
        selected_amounts = new ArrayList<Integer>();
        bill = ranchDatabaseRepo.getBill(this, bill_id);

        client = ranchDatabaseRepo.getSingleClient(this,bill.getId());

        products_ids = ranchDatabaseRepo.getProductsFromDetail(this, bill_id);

        for(Integer id : products_ids){
            Product current = ranchDatabaseRepo.getOrderProduct(this,id);
            Integer amount = ranchDatabaseRepo.getSelectedProductAmount(this,id);
            total += current.getPrice() * amount;
            products.add(current);
            selected_amounts.add(amount);
        }

        bill.setTotal(total);
        ranchDatabaseRepo.updateBillTotal(this, bill_id, total);
        String total_message = "Total: RD$" + String.valueOf(total);
        setText(R.id.name_clientes_text, client.getName());
        setText(R.id.phone_clientes_text, client.getPhoneNumber());
        setText(R.id.email_clientes_text, client.getEmail());
        setText(R.id.total_textView,total_message) ;
        recylerAdapter.setProducts(products);
        recylerAdapter.setAmounts(selected_amounts);

    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }


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
        getMenuInflater().inflate(R.menu.resumen_orden, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_resumen) {
            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);
        } else if (id == R.id.nav_productos) {

        } else if (id == R.id.nav_consultar_cliente) {

        } else if (id == R.id.nav_ordenes) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toSelectProducto(View view) {

        Intent intent = new Intent(this, SeleccionarProducto.class);
        startActivity(intent);
    }

    public void toConfirmacion(View view) {

        Intent intent = new Intent(this, ConfirmacionOrden.class);
        intent.putExtra("bill_id", bill_id);
        startActivity(intent);
    }
}
