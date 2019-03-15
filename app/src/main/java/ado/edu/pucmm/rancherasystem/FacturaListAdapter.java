package ado.edu.pucmm.rancherasystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Factura;

public class FacturaListAdapter extends RecyclerView.Adapter<FacturaListAdapter.FacturaViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Factura> mFacturas;


    public FacturaListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        FacturaViewHolder viewHolder = new FacturaViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaViewHolder holder, int position) {
        if (mFacturas != null) {
            Factura factura = mFacturas.get(position);
            holder.setData(factura.getId(), position);
        } else {
            // Covers the case of data not being ready yet.
        }
    }

    @Override
    public int getItemCount() {
        if(mFacturas != null) {
            return mFacturas.size();
        }
        else return 0;
    }

    public void setFacturas(List<Factura> facturas) {
        mFacturas = facturas;
        notifyDataSetChanged();
    }

    public class FacturaViewHolder extends RecyclerView.ViewHolder {
        private TextView facturaItemView;
        private int mPosition;

        public FacturaViewHolder(@NonNull View itemView) {
            super(itemView);
            facturaItemView = itemView.findViewById(R.id.txvNote);
        }

        public void setData(int id, int position) {
            facturaItemView.setText(String.valueOf(id));
            mPosition = position;
        }
    }
}
