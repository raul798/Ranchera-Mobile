package ado.edu.pucmm.rancherasystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClientSearchAdapter extends ArrayAdapter<Client> {

    private Context context;
    private List<Client> clients;

    public ClientSearchAdapter(Context context, List<Client> clients) {
        super(context, R.layout.client_search_layout, clients);
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_search_layout, null);
        Client client = clients.get(position);
        TextView textViewName = view.findViewById(R.id.textViewName);
        textViewName.setText(client.getName());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new ClientFilter(this, context);
    }

    private class ClientFilter extends  Filter {

        private ClientSearchAdapter clientSearchAdapter;
        private Context context;

        public ClientFilter(ClientSearchAdapter clientSearchAdapter, Context context) {
            this.clientSearchAdapter = clientSearchAdapter;
            this.context = context;

        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            clientSearchAdapter.clients.clear();
            FilterResults filterResults = new FilterResults();
            if(charSequence == null || charSequence.length() == 0) {
                filterResults.values = new ArrayList<Client>();
                filterResults.count = 0;
            }
            else {
                RancheraDB rancheraDB = new RancheraDB(context);
                List<Client> clients = rancheraDB.search(charSequence.toString());
                filterResults.values = clients;
                filterResults.count = clients.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clientSearchAdapter.clients.clear();
            clientSearchAdapter.clients.addAll( (List<Client>) filterResults.values);
            clientSearchAdapter.notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Client client = (Client) resultValue;
            return client.getName();
        }
    }
}
