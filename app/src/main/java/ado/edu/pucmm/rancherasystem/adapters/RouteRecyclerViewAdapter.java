package ado.edu.pucmm.rancherasystem.adapters;

import android.content.Context;
<<<<<<< HEAD
import android.content.Intent;
import android.net.Uri;
=======
>>>>>>> added faq
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Route;
import ado.edu.pucmm.rancherasystem.ui.activity.ClientInformationActivity;
import ado.edu.pucmm.rancherasystem.ui.activity.MenuActivity;
import ado.edu.pucmm.rancherasystem.ui.activity.SelectClientActivity;
import ado.edu.pucmm.rancherasystem.ui.activity.SelectProductsActivity;

public class RouteRecyclerViewAdapter extends RecyclerView.Adapter<RouteRecyclerViewAdapter.RouteViewHolder> {

    private RanchDatabaseRepo ranchDatabaseRepo = new RanchDatabaseRepo();
    Context context;
    Route route;
    String statusText;

    class RouteViewHolder extends RecyclerView.ViewHolder {

        private final TextView clientNameView;
        private final TextView addressView;
        private final ImageButton toMap;
        private final AdapterListener onClickListener;
        private  final TextView statusView;

        private RouteViewHolder(View itemView) {
            super(itemView);
            clientNameView = itemView.findViewById(R.id.text_view_name);
            addressView = itemView.findViewById(R.id.address_clientes_text);
            toMap = itemView.findViewById(R.id.btn_toMap);
            onClickListener = new AdapterListener();
            statusView = itemView.findViewById(R.id.statusTextView);
            context = itemView.getContext();
            route = null;
            statusText = null;
            toMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater inflater;
    private List<Route> routes;
    private List<Client> clients = new ArrayList<>();

    public RouteRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
=======
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.Client;

public class RouteRecyclerViewAdapter extends RecyclerView.Adapter<RouteRecyclerViewAdapter.RouteViewHolder> {
    private final LayoutInflater inflater;
    private List<Client> clients;

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
>>>>>>> added faq
        View itemView = inflater.inflate(R.layout.recyclerview_route, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
<<<<<<< HEAD
    public void onBindViewHolder(RouteViewHolder holder, int position) {
       if(clients != null) {
           Client current = clients.get(position);
           holder.clientNameView.setText(current.getName());
           holder.addressView.setText(current.getAddress());
           route = ranchDatabaseRepo.getRouteByClientId(context, current.getId());
           if(route.isStatus() == true) statusText = "(Listo)";
                   else statusText = "(Pendiente)";
           holder.statusView.setText(String.valueOf(statusText));
       } else {
           holder.clientNameView.setText("No tiene rutas pendientes");
=======
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int i) {
       if(clients!= null) {
           Client current = clients.get(i);
           holder.clientNameView.setText(current.getName());
           holder.addressView.setText(current.getAddress());
       }
       else {
           // Covers the case of data not being ready yet.
           holder.clientNameView.setText("No tirene rutas pendientes");
>>>>>>> added faq
       }
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return clients.size();
=======
        if (clients != null)
            return clients.size();
        else return 0;
>>>>>>> added faq
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
        notifyDataSetChanged();
    }

<<<<<<< HEAD
    public class AdapterListener {

        void onClick(View view, int position){

           Context context = view.getContext();
            Intent intent = new Intent(context, ClientInformationActivity.class);
            intent.putExtra("clientId", clients.get(position).getId());
            context.startActivity(intent);

            /*
            Context context = view.getContext();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
            context.startActivity(intent);
            */
        }
=======
    public RouteRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    class RouteViewHolder extends RecyclerView.ViewHolder {

        private final TextView clientNameView;
        private final TextView addressView;


        private RouteViewHolder(View itemView) {
            super(itemView);
            clientNameView = itemView.findViewById(R.id.text_view_name);
            addressView = itemView.findViewById(R.id.address_clientes_text);
        }

    }



    public class RouteAdapterListener {


>>>>>>> added faq
    }
}
