package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ProductRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.adapters.RouteRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Client;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    private RouteRecyclerViewAdapter recyclerAdapter;
    private RanchDatabaseRepo rancheraDatabaseRepo;
    List<Client> clients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new RouteRecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {

            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.products:
                        intent = new Intent(MenuActivity.this, SearchProductActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.clients:
                        intent = new Intent(MenuActivity.this, SearchClientActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.inventory:
                        Toast.makeText(MenuActivity.this, "inventory", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.order:
                        intent = new Intent(MenuActivity.this, SelectClientActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.bill:
                        intent = new Intent(MenuActivity.this, PagarFacturaActivity.class);
                        startActivity(intent);
                        Toast.makeText(MenuActivity.this, "bill", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //rancheraDatabaseRepo = new RanchDatabaseRepo(getApplication());
        //clients = repos.get
        //recyclerAdapter.setClients();
    }

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.helpButton) {
            Intent intent = new Intent(this, SupportActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
