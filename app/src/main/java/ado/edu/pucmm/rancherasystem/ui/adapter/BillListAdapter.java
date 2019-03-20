package ado.edu.pucmm.rancherasystem.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.ui.activity.BillDetailActivity;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    private final Context context;

    public static final String EXTRA_NUMBER = "id_client";

    class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView_id;
        private final TextView wordItemView2_debt;
        private final TextView wordItemView3_date;
        public Bill bill;

        private BillViewHolder(View itemView) {
            super(itemView);
            wordItemView_id = itemView.findViewById(R.id.text_id_factura);
            wordItemView2_debt = itemView.findViewById(R.id.text_debt);
            wordItemView3_date = itemView.findViewById(R.id.text_date);
            itemView.findViewById(R.id.view_id).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), BillDetailActivity.class);
            intent.putExtra(EXTRA_NUMBER, bill.getId());

            v.getContext().startActivity(intent);

        }

    }

    private final LayoutInflater mInflater;
    private List<Bill> mBills; // Cached copy of words

    public BillListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.bills_list_item, parent, false);
        return new BillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        if (mBills != null) {

            Bill current = mBills.get(position);
            holder.wordItemView_id.setText(String.valueOf(current.getId()));
            holder.wordItemView2_debt.setText(String.valueOf(current.getDebt()));
            holder.wordItemView3_date.setText(String.valueOf(current.getDescription()));
            holder.bill = current;

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView_id.setText("No Data for the Bill");
        }
    }

    public void setBills(List<Bill> bills){
        mBills = bills;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mBillsesta  has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mBills != null)
            return mBills.size();
        else return 0;
    }
}

