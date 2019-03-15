package ado.edu.pucmm.rancherasystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Detalle;
import ado.edu.pucmm.rancherasystem.db.DetalleViewModel;
import ado.edu.pucmm.rancherasystem.db.Factura;

public class DetalleListAdapter extends RecyclerView.Adapter<DetalleListAdapter.DetalleViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Detalle> mDetalles;

    public DetalleListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public DetalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        DetalleViewHolder viewHolder = new DetalleViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleViewHolder holder, int position) {
        if (mDetalles != null) {
            Detalle detalle = mDetalles.get(position);
            holder.setData(detalle.getId_producto(), detalle.getCantidad(), position);
        } else {
            // Covers the case of data not being ready yet.
        }
    }

    @Override
    public int getItemCount() {
        if(mDetalles != null) {
            return mDetalles.size();
        }
        else return 0;
    }

    public void setDetalles(List<Detalle> detalles) {
        mDetalles = detalles;
        notifyDataSetChanged();
    }

    public class DetalleViewHolder extends RecyclerView.ViewHolder {
        private TextView detalleItemView;
        private int mPosition;

        public DetalleViewHolder(@NonNull View itemView) {
            super(itemView);
            detalleItemView = itemView.findViewById(R.id.txvNote);
        }

        public void setData(int id, int cant, int position) {
            detalleItemView.setText("id: " + String.valueOf(id) + ", cant: " + String.valueOf(cant));
            mPosition = position;
        }

    }
}
