package ado.edu.pucmm.rancherasystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.ui.activity.SupportActivity;

import ado.edu.pucmm.rancherasystem.R;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.FaqViewHolder> {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Faq> mFaqs;


    public FaqListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View) layoutInflater.inflate(R.layout.list_item_faq, parent, false);
        FaqViewHolder viewHolder = new FaqViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        if (mFaqs != null) {
            Faq faq = mFaqs.get(position);
            holder.setData(faq.getQuestion(), faq.getAnswer(), position);
        }
        else {}
    }

    @Override
    public int getItemCount() {
        if(mFaqs != null) return mFaqs.size();
        else return 0;
    }

    public void setFaqs(List<Faq> faqs) {
        mFaqs = faqs;
        notifyDataSetChanged();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {
        private TextView faqItemView;
        private int mPosition;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            faqItemView = itemView.findViewById(R.id.txvNote);
        }

        public void setData(String question, String answer, int position) {
            faqItemView.setText(question + "\n" + answer);
            mPosition = position;
        }
    }
}
