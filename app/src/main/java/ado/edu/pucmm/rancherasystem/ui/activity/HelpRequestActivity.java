package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ado.edu.pucmm.rancherasystem.R;

public class HelpRequestActivity extends AppCompatActivity {

    private EditText question, comment;
    public static final String QUESTION_ADDED = "new_question";
    public static final String COMMENT_ADDED = "new_comment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request);

        question = findViewById(R.id.PreguntaEditText);
        comment = findViewById(R.id.ComentarioEditText);

        Button button = findViewById(R.id.EnviarButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent resultIntent = new Intent();

                if(TextUtils.isEmpty(question.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                }
                else {
                    String questionText = question.getText().toString();
                    String commentText = comment.getText().toString();

                    resultIntent.putExtra(QUESTION_ADDED, questionText);
                    resultIntent.putExtra(COMMENT_ADDED, commentText);

                    setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }
}
