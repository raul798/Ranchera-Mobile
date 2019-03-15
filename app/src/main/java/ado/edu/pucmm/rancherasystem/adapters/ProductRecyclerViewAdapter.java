package ado.edu.pucmm.rancherasystem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ado.edu.pucmm.rancherasystem.ConsultarClientes;
import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.Product;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productItemView;
        private final TextView productQuantityView;
        private final TextView productPriceView;
        private final TextView productStockView;
        private final DetailsAdapterListener onClickListener;
        private final Button plusButton;
        private final Button minusButton;

        private ProductViewHolder(View itemView) {
            super(itemView);
            plusButton = itemView.findViewById(R.id.bttn_plus);
            minusButton = itemView.findViewById(R.id.bttn_minus);
            productPriceView = itemView.findViewById(R.id.productPriceRecyclerView);
            productStockView = itemView.findViewById(R.id.productStockRecyclerView);
            onClickListener = new DetailsAdapterListener();
            productItemView = itemView.findViewById(R.id.productNameRecyclerView);
            productQuantityView = itemView.findViewById(R.id.quantityView);
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickPlus(v, getAdapterPosition());
                }
            });
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickMinus(v, getAdapterPosition());
                }
            });
        }

    }

    private final LayoutInflater inflater;
    private List<Product> products; // Cached copy of products
    private List<Integer> amounts;
    private List<Integer> max;

    public ProductRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (products != null) {
            Product current = products.get(position);
            int stock = current.getQuantity();
            String priceString = "Precio: $" + String.valueOf(current.getPrice());
            String stockString = "En inventario: " + String.valueOf(stock);
            int currentAmount = amounts.get(position);
            max.set(position,stock);
            holder.productItemView.setText(current.getName());
            holder.productQuantityView.setText(String.valueOf(currentAmount));
            holder.productPriceView.setText(priceString);
            holder.productStockView.setText(stockString);

        } else {
            // Covers the case of data not being ready yet.
            holder.productItemView.setText("No Product");
        }
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public void setAmounts(List<Integer> amounts){
        this.amounts = amounts;
        notifyDataSetChanged();
    }

    public void setMax(List<Integer> max){
        this.max = max;
    }

    // getItemCount() is called many times, and when it is first called,
    // mProducts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else return 0;
    }

    public class DetailsAdapterListener {

        void onClickPlus(View view, int position){
            int current = amounts.get(position);
            int stock = max.get(position);
            if (current < stock) {
                amounts.set(position, ++current);
                notifyDataSetChanged();
            }
            else{
                Toast.makeText(view.getContext(),"Excede cantidad en inventario", Toast.LENGTH_SHORT).show();
            }
        }

        void onClickMinus(View view, int position){
            int current = amounts.get(position);
            if(current > 1) {
                amounts.set(position, --current);
                notifyDataSetChanged();
            }
        }
    }

}
