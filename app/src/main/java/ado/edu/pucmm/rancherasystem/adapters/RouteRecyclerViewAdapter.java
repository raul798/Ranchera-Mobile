package ado.edu.pucmm.rancherasystem.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        View itemView = inflater.inflate(R.layout.recyclerview_route, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int i) {
       if(clients!= null) {
           Client current = clients.get(i);
           holder.clientNameView.setText(current.getName());
           holder.addressView.setText(current.getAddress());
       }
       else {
           // Covers the case of data not being ready yet.
           holder.clientNameView.setText("No tirene rutas pendientes");
       }
    }

    @Override
    public int getItemCount() {
        if (clients != null)
            return clients.size();
        else return 0;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
        notifyDataSetChanged();
    }

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


    }
}
