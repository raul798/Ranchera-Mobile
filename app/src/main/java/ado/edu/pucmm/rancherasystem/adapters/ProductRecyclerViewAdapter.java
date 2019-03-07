package ado.edu.pucmm.rancherasystem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.Product;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productItemView;
        private final TextView productQuantityView;

        private ProductViewHolder(View itemView) {
            super(itemView);
            productItemView = itemView.findViewById(R.id.textRecyclerProduct);
            productQuantityView = itemView.findViewById(R.id.quantityView);
        }
    }

    private final LayoutInflater inflater;
    private List<Product> products; // Cached copy of products
    private int amount;

    public ProductRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_product, parent, false);
        amount = 100;
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (products != null) {
            Product current = products.get(position);
            holder.productItemView.setText(current.getName());
            holder.productQuantityView.setText(String.valueOf(amount));
        } else {
            // Covers the case of data not being ready yet.
            holder.productItemView.setText("No Product");
        }
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public void setAmount(int amount){
        this.amount = amount;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mProducts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else return 0;
    }

    public int getAmount(){
        return amount;
    }

}
