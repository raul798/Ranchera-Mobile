package ado.edu.pucmm.rancherasystem.ui.adapter;

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

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;

public class ClientSearchAdapter extends ArrayAdapter<Client> {
    private List<Client> dataList;
    private Context mContext;
    private int itemLayout;

    private RanchDatabaseRepo ranchDatabaseRepo;

    private ClientSearchAdapter.ListFilter listFilter = new ClientSearchAdapter.ListFilter();

    public ClientSearchAdapter(Context context, int resource, List<Client> clientDataList) {
        super(context, resource, clientDataList);
        ranchDatabaseRepo = new RanchDatabaseRepo(context);
        dataList = clientDataList;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Client getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getPosition(Client item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.textViewClientName);
        strName.setText(getItem(position).getName());

        return view;
    }



    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();


        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList<String>();
                    results.count = 0;
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                //Call to database to get matching records using room
                List<Client> matchValues =
                        ranchDatabaseRepo.getClient(mContext, searchStrLowerCase);

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<Client>)results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
