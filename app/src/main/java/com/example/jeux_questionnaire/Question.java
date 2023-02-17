package com.example.jeux_questionnaire;

import android.database.Cursor;

public class Question {

    private final String question;
    private final int reponse;

    public Question(Cursor cursor){
        question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
        reponse = cursor.getInt(cursor.getColumnIndexOrThrow("reponse"));
    }

    public int getReponse() {
        return reponse;
    }

    public String getQuestion() {
        return question;
    }

}
