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
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.ui.activity.BillDetailActivity;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    private final Context context;
    private RanchDatabaseRepo ranchDatabaseRepo;

    public static final String EXTRA_NUMBER = "billId";

    class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView id;
        private final TextView total;
        public Bill bill;

        private BillViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_factura);
            total = itemView.findViewById(R.id.text_total);
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
    private List<Bill> billList; // Cached copy of words

    public BillListAdapter(Context context) {
        this.context = context;
        ranchDatabaseRepo = new RanchDatabaseRepo();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.bills_list_item, parent, false);
        return new BillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        if (billList != null) {
            Bill current = billList.get(position);

            float payedAmount = ranchDatabaseRepo.getBillAmount(context, current.getId());
            float total = current.getTotal();
            float owed = total - payedAmount;
            if(owed <= 0){
                billList.remove(current);
            }
            else {
                String idMessage = "Factura #" + String.valueOf(current.getId());
                String totalMessage = "Monto: RD$ " + owed;
                holder.id.setText(idMessage);
                holder.total.setText(totalMessage);
                holder.bill = current;
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.id.setText("No Data for the Bill");
        }
    }

    public void setBills(List<Bill> bills){
        billList = bills;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mBillsesta  has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (billList != null)
            return billList.size();
        else return 0;
    }
}

