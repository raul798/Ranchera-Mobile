package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.RouteRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Route;
import ado.edu.pucmm.rancherasystem.remote.SessionService;
import ado.edu.pucmm.rancherasystem.viewmodel.RouteViewModel;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private RouteRecyclerViewAdapter recyclerAdapter;
    private RanchDatabaseRepo rancheraDatabaseRepo;
    private List<Client> clients;
    private List<Route> routes;
    private int clientId;
    private RouteViewModel routeViewModel;
    private List<Integer> clientsIds;
    private SessionService sessionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionService = SessionService.getInstance(getBaseContext());

        rancheraDatabaseRepo = new RanchDatabaseRepo();
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

        TextView displayName = navigationView.getHeaderView(0).findViewById(R.id.display_name);
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.email);

        navigationView.getHeaderView(0).findViewById(R.id.logout).setOnClickListener(v -> {
            sessionService.logout();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "Adios", Toast.LENGTH_SHORT).show();
        });

        displayName.setText(sessionService.getDisplay());
        email.setText(sessionService.getEmail());

        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setEnabled(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            //menuItem.//setEnabled(false);

            switch (menuItem.getItemId())
            {
                case R.id.products:
                    intent = new Intent(MenuActivity.this, SearchProductActivity.class);
                    break;
                case R.id.clients:
                    intent = new Intent(MenuActivity.this, SearchClientActivity.class);
                    break;
                case R.id.order:
                    intent = new Intent(MenuActivity.this, SelectClientActivity.class);
                    break;
            }
            startActivity(intent);
            menuItem.setEnabled(true);
            return true;
        });




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientId = extras.getInt("clientId");
        }

        clients = new ArrayList<Client>();
        routes = new ArrayList<Route>();
        clientsIds = new ArrayList<Integer>();
        routeViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);

        rancheraDatabaseRepo.updateRouteStatus(this, true, clientId);
        routes = rancheraDatabaseRepo.getAllRoutes();
        //client1 = rancheraDatabaseRepo.getSingleClient(this, 1);
        //client2 = rancheraDatabaseRepo.getSingleClient(this, 2);

        for(Route route : routes) {
            Client client = rancheraDatabaseRepo.getSingleClient(this, route.getClientId());
            clients.add(client);
        }

        //Toast.makeText(MenuActivity.this, String.valueOf(client1.getId()), Toast.LENGTH_SHORT).show();
        recyclerAdapter.setClients(clients);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
