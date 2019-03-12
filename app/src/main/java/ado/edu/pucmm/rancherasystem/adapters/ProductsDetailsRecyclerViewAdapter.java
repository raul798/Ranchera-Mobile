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

public class ProductsDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsDetailsRecyclerViewAdapter.ProductDetailsViewHolder> {

    class ProductDetailsViewHolder extends RecyclerView.ViewHolder {
        private final TextView productItemView;
        private final TextView productQuantityView;
        private final TextView productPriceView;

        private ProductDetailsViewHolder(View itemView) {
            super(itemView);
            productPriceView = itemView.findViewById(R.id.productPriceRecyclerView);
            productItemView = itemView.findViewById(R.id.productNameRecyclerView);
            productQuantityView = itemView.findViewById(R.id.quantityView);
        }
    }

    private final LayoutInflater inflater;
    private List<Product> products; // Cached copy of products
    private List<Integer> amounts;
    public ProductsDetailsRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public ProductDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_products_details, parent, false);
        return new ProductDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductDetailsViewHolder holder, int position) {
        if (products != null) {
            Product current = products.get(position);
            int amount = amounts.get(position);
            String priceString = "Precio: $" + String.valueOf(current.getPrice());
            holder.productItemView.setText(current.getName());
            holder.productQuantityView.setText(String.valueOf(amount));
            holder.productPriceView.setText(priceString);

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
    // getItemCount() is called many times, and when it is first called,
    // mProducts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else return 0;
    }
}
