package ado.edu.pucmm.rancherasystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Bill;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    class BillViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private BillViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Bill> mBills; // Cached copy of words

    BillListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.bills_list_item, parent, false);
        return new BillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        if (mBills != null) {
            Bill current = mBills.get(position);
            holder.wordItemView.setText(current.getId());

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setBills(List<Bill> bills){
        mBills = bills;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mBills != null)
            return mBills.size();
        else return 0;
    }
}