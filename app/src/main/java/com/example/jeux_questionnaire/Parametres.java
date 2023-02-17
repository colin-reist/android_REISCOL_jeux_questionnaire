package com.example.jeux_questionnaire;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class Parametres extends AppCompatActivity {

    String question;
    int reponse = 0;

    private Slider timeSlider;
    private EditText Question;
    private CheckBox checkBox;
    private Context context;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        timeSlider = findViewById(R.id.param_slider);
        Button BT_Quitter = findViewById(R.id.parametre_bouton_quitter);
        Button BT_Ajouter = findViewById(R.id.parametre_bouton_ajouter_question);
        Question = findViewById(R.id.parametre_question_et);
        checkBox = findViewById(R.id.parametre_checkbox);
        context = getApplicationContext();

        BT_Quitter.setOnClickListener(view -> {
            setTime();
            finish();
        });

        BT_Ajouter.setOnClickListener(view -> addQuestion());
    }

    /**
     * Ajoute une question à la base de données
     */
    private void addQuestion() {
        question = Question.getText().toString();
        CharSequence text;

        // Vérification si la question est vrai ou fausse
        if (checkBox.isChecked()) {
            reponse = 1;
        }

        // Vérification si la question est vide
        // Si la question est vide, on affiche un message d'erreur
        // Sinon, on ajoute la question à la base de données
        if (!question.isEmpty()) {
            text = getText(R.string.Add_question);
        } else {
            text = getText(R.string.Add_question_error);
        }

        // Affichage du message
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        QuestionManager.addQuestion(this, question, reponse);
    }

    /**
     * Capture la valeur du slider et la convertit en millisecondes
     * pour la passer à la classe jeux
     */
    private void setTime() {
        jeux.temps = (long) timeSlider.getValue() * 1000 + 1000;
    }
}
