package ado.edu.pucmm.rancherasystem.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity()
public class HelpRequest {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @NonNull
    private String question;
    @NonNull
    private String comment;

    public HelpRequest(@NonNull String question, @NonNull String comment) {
        this.question = question;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }
}
