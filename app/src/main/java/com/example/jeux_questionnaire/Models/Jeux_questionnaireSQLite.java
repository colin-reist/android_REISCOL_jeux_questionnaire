package com.example.jeux_questionnaire.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jeux_questionnaire.Question;

import java.sql.ResultSet;

public class Jeux_questionnaireSQLite extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    static String DB_NAME = "Jeux_questionnaire.db";
    static int DB_VERSION = 1;

    public Jeux_questionnaireSQLite(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //S'execute uniquement lors du tout premier lancement de l'application après son installation
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String sqlCreateDataBase = "CREATE TABLE quiz(idQuiz INTEGER PRIMARY KEY, question TEXT, reponse INTEGER);";
        db.execSQL(sqlCreateDataBase);
        db.execSQL("INSERT INTO quiz VALUES(1,\"La capital de l'Australie est Sydney\",1)");
        db.execSQL("INSERT INTO quiz VALUES(2,\"La terre est ronde\",1)");
        db.execSQL("INSERT INTO quiz VALUES(3,'La terre est plate',0)");
        db.execSQL("INSERT INTO quiz VALUES(4,\"La Suisse est un pays de l'union européenne\",0)");
        db.execSQL("INSERT INTO quiz VALUES(5,\"Tobias a six de moyenne\",1)");
        db.execSQL("INSERT INTO quiz VALUES(6,\"Colin est le créateur de cette application\",0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
