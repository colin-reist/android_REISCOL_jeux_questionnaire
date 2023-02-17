package com.example.jeux_questionnaire;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText TXT_Joueur1;
    private EditText TXT_Joueur2;
    public static String Joueur1;
    public static String Joueur2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar MainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(MainToolBar);

        Button BT_Jouer = findViewById(R.id.main_button_jouer);

        BT_Jouer.setOnClickListener(view -> {
            lauchGame();
        });
    }
    private void lauchGame() {
        TXT_Joueur1 = findViewById(R.id.main_joueur1_et);
        TXT_Joueur2 = findViewById(R.id.main_joueur2_et);

        Joueur1 = TXT_Joueur1.getText().toString();
        Joueur2 = TXT_Joueur2.getText().toString();
        if (Joueur1.isEmpty() || Joueur2.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un nom pour les deux joueurs", Toast.LENGTH_SHORT).show();
        } else {
            Intent jeux = new Intent(MainActivity.this, jeux.class);
            startActivity(jeux);
        }
    }
    private void resetFields() {
        TXT_Joueur1.setText("");
        TXT_Joueur2.setText("");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent parametres = new Intent(MainActivity.this, Parametres.class);
                startActivity(parametres);
                return true;
            case R.id.menu_delete:
                resetFields();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}