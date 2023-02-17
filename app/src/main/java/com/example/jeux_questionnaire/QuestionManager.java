package com.example.jeux_questionnaire;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeux_questionnaire.Models.Jeux_questionnaireSQLite;

import java.util.ArrayList;

public class QuestionManager {

    public ArrayList<Question> mesQuestions;
    private int index = 0;

    // Rajoute les questions dans la liste
    public QuestionManager(Context context)  { mesQuestions = initQuestionList(context);}

    public static void addQuestion(String question, int reponse) {
    }

    public String getQuestion() {
        return mesQuestions.get(index).getQuestion();
    }

    public int getReponse() {
        return mesQuestions.get(index).getReponse();
    }

    public void prochaineQuestion() {
        this.index++;
    }

    public boolean isLastQuestion() {
        int finListe = mesQuestions.size() -1;
        return index == finListe;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private ArrayList<Question> initQuestionList(Context context){
        ArrayList<Question> InitQuestion = new ArrayList<>();// crée une nouvel liste
        Jeux_questionnaireSQLite helper = new Jeux_questionnaireSQLite(context);// Instancie la base de données
        SQLiteDatabase db = helper.getReadableDatabase();//Accède à la base de données en lecture seul

        //la base de donnée retourne les information en cursor
        //Crée une requete avec tout les parametres
        Cursor cursor = db.query(true,"quiz",new String[]{"idQuiz", "question","reponse"}
                ,null,null,null,null,"idQuiz",null);

        while (cursor.moveToNext()){
            InitQuestion.add(new Question(cursor));
        }

        cursor.close();
        db.close();
        return InitQuestion;
    }

    public static void addQuestion(Context context, String question, int reponse){
        SQLiteDatabase db = new Jeux_questionnaireSQLite(context).getWritableDatabase();
        db.execSQL("INSERT INTO quiz VALUES(null,\" " + question + "\", " + reponse + ");");
        db.close();
    }
}


