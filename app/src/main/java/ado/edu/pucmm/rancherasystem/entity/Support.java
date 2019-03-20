package ado.edu.pucmm.rancherasystem.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Support {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String question;
    @NonNull
    private String answer;

    public Support(@NonNull String question, @NonNull String answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @NonNull
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(@NonNull String answer) {
        this.answer = answer;
    }
}
