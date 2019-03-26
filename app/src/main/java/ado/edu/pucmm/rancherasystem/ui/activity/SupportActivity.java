package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.HelpRequest;
import ado.edu.pucmm.rancherasystem.viewmodel.HelpRequestViewModel;

public class SupportActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private static final int INSERT_Q = 10;
    private HelpRequestViewModel helpRequestViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        helpRequestViewModel = ViewModelProviders.of(this).get(HelpRequestViewModel.class);
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