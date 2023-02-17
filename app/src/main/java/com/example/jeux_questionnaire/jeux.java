package com.example.jeux_questionnaire;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class jeux extends AppCompatActivity {

    //Boutons
    private Button BT_Joueur1;
    private Button BT_Joueur2;
    private Button BT_Quitter;
    private Button BT_Rejouer;

    //TextView
    private TextView scoreJoueur1;
    private TextView scoreJoueur2;
    private TextView questionText1;
    private TextView questionText2;
    private TextView CountDownTimerText;
    private TextView CountDownTimerText2;

    public static long temps = 10000;

    //Commande
    QuestionManager questionManager;
    Runnable questionRunnable = null;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeux);

        questionManager = new QuestionManager(this);

        //Boutons
        BT_Joueur1 = findViewById(R.id.button_joueur_1);
        BT_Joueur2 = findViewById(R.id.button_joueur_2);
        BT_Quitter = findViewById(R.id.jeux_bouton_quitter);
        BT_Rejouer = findViewById(R.id.jeux_bouton_rejouer);

        //Score
        scoreJoueur1 = findViewById(R.id.jeux_score_joueur_1);
        scoreJoueur2 = findViewById(R.id.jeux_score_joueur_2);

        //Nom des joueurs
        TextView nomJoueur1 = findViewById(R.id.jeux_nom_joueur_1);
        TextView nomJoueur2 = findViewById(R.id.jeux_nom_joueur_2);
        nomJoueur1.setText(MainActivity.Joueur1);
        nomJoueur2.setText(MainActivity.Joueur2);

        //Question
        questionText1 = findViewById(R.id.jeux_question_joueur_1);
        questionText2 = findViewById(R.id.jeux_question_joueur_2);

        //Timer
        CountDownTimerText = findViewById(R.id.jeux_countdown);
        CountDownTimerText2 = findViewById(R.id.jeux_countdown_2);

        //Intialisation de la liste de question et affiche la première question
        String question = questionManager.getQuestion();
        questionText1.setText(question);
        questionText2.setText(question);

        //Désactive les boutons de réponse,
        //Rajoute un point au joueur 2 si la réponse est juste
        //et enlève un point si la réponse est fausse
        BT_Joueur1.setOnClickListener(view -> {
            joueurDonneReponse(1);
        });

        //Désactive les boutons de réponse,
        //Rajoute un point au joueur 2 si la réponse est juste
        //et enlève un point si la réponse est fausse
        BT_Joueur2.setOnClickListener(view -> {
            joueurDonneReponse(2);
        });

        BT_Quitter.setOnClickListener(view -> finish());

        BT_Rejouer.setOnClickListener(view -> {
            rejouer();
        });
        startCoutDownTimer();
    }

    //
    private void joueurDonneReponse(int joueur) {
        //Désactive les boutons de réponse
        BT_Joueur1.setEnabled(false);
        BT_Joueur2.setEnabled(false);

        //Regarde qui a répondu et rajoute ou enlève un point
        if (joueur == 1) {
            if (questionManager.getReponse() == 1) {
                scoreJoueur1.setText(String.valueOf(Integer.parseInt(scoreJoueur1.getText().toString()) + 1));
            } else {
                scoreJoueur1.setText(String.valueOf(Integer.parseInt(scoreJoueur1.getText().toString()) - 1));
            }
        } else {
            if (questionManager.getReponse() == 1) {
                scoreJoueur2.setText(String.valueOf(Integer.parseInt(scoreJoueur2.getText().toString()) + 1));
            } else {
                scoreJoueur2.setText(String.valueOf(Integer.parseInt(scoreJoueur2.getText().toString()) - 1));
            }
        }
    }

    private void rejouer() {
        scoreJoueur1.setText(String.valueOf(Integer.parseInt("0")));
        scoreJoueur2.setText(String.valueOf(Integer.parseInt("0")));
        questionManager.setIndex(0);
        startCoutDownTimer();
        BT_Quitter.setVisibility(View.GONE);
        BT_Rejouer.setVisibility(View.GONE);
        CountDownTimerText.setVisibility(View.VISIBLE);
        CountDownTimerText2.setVisibility(View.VISIBLE);
    }

    private void nextQuestion() {
        questionManager.prochaineQuestion();
        String question = questionManager.getQuestion();

        questionText1.setText(question);
        questionText2.setText(question);
        BT_Joueur1.setEnabled(true);
        BT_Joueur2.setEnabled(true);
    }

    /**
     * Affiche le gagnant et prospose de rejouer ou de quitter
     */
    private void finPartie() {
        //Désactive les boutons de réponse
        BT_Joueur1.setEnabled(false);
        BT_Joueur2.setEnabled(false);
        BT_Rejouer.setVisibility(View.VISIBLE);
        BT_Quitter.setVisibility(View.VISIBLE);

        //Capture les score des joueurs
        int score_joueur1 = Integer.parseInt(scoreJoueur1.getText().toString());
        int score_joueur2 = Integer.parseInt(scoreJoueur2.getText().toString());

        //Compare les scores pour savoir qui à gagner ou s'il y a égalité
        if (score_joueur1 > score_joueur2) {
            questionText1.setText(R.string.Gagne);
            questionText2.setText(R.string.Perdu);
        } else if (score_joueur1 < score_joueur2) {
            questionText1.setText(R.string.Perdu);
            questionText2.setText(R.string.Gagne);
        } else {
            questionText1.setText(R.string.Egalite);
            questionText2.setText(R.string.Egalite);
        }
    }

    private void startQuestionIterative() {
        handler = new Handler();

        questionRunnable = new Runnable() {
            @Override
            public void run() {
                if (questionManager.isLastQuestion()) {
                    //Executer le code de fin de partie
                    finPartie();
                    handler.removeCallbacks(this);
                } else {
                    //Code pour demander et afficher une question
                    nextQuestion();
                    handler.postDelayed(this, 2000);
                }
            }
        };

        handler.postDelayed(questionRunnable, 1000);
    }

    private void startCoutDownTimer() {
        new CountDownTimer(temps, 1000) {
            public void onTick(long milliUntilFinished) {
                //Afficher le conteur à l'utilisateur
                CountDownTimerText.setText(String.valueOf(milliUntilFinished / 1000));
                CountDownTimerText2.setText(String.valueOf(milliUntilFinished / 1000));
            }

            public void onFinish() {
                //Afficher le départ à l'utilisateur
                CountDownTimerText.setVisibility(View.GONE);
                CountDownTimerText2.setVisibility(View.GONE);
                startQuestionIterative();
            }
        }.start();
    }
}
