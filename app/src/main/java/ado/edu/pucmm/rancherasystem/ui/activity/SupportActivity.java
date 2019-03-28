package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.Faq;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;
import ado.edu.pucmm.rancherasystem.ui.adapter.FaqListAdapter;
import ado.edu.pucmm.rancherasystem.viewmodel.FaqViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.HelpRequestViewModel;

public class SupportActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final int INSERT_Q = 10;
    private HelpRequestViewModel helpRequestViewModel;
    private FaqViewModel faqViewModel;
    private FaqListAdapter faqListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        helpRequestViewModel = ViewModelProviders.of(this).get(HelpRequestViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewfaq);
        faqListAdapter = new FaqListAdapter(this);
        recyclerView.setAdapter(faqListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        faqViewModel = ViewModelProviders.of(this).get(FaqViewModel.class);
        faqViewModel.getAllFaqs().observe(this, new Observer<List<Faq>>() {
            @Override
            public void onChanged(@Nullable List<Faq> faqs) {
                faqListAdapter.setFaqs(faqs);
            }
        });

    }

    public void supportMe(View view) {
        Intent supportIntent = new Intent(this, HelpRequestActivity.class);
        //login logic
        startActivityForResult(supportIntent,INSERT_Q);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String fine = "Request have been sent";
        String bad = "Request not sent";

        if(requestCode == INSERT_Q && resultCode == RESULT_OK) {

            assert data != null;
            HelpRequest helpRequest = new HelpRequest
                    (
                    data.getStringExtra(HelpRequestActivity.QUESTION_ADDED),
                    data.getStringExtra(HelpRequestActivity.COMMENT_ADDED)
                    );
            helpRequestViewModel.insert(helpRequest);

            Toast.makeText(
                    getApplicationContext(),
                    fine,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    bad,
                    Toast.LENGTH_LONG).show();
        }
    }
}
