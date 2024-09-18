package br.edu.uniritter.primeirade24_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Tela4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout da tela de boas-vindas
        setContentView(R.layout.tela4_activity);

        // Obtém o Intent que iniciou esta Activity e recupera o username
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Busca a TextView no layout e define o texto de boas-vindas com o username
        TextView textViewBoasVindas = findViewById(R.id.textViewBoasVindas);
        textViewBoasVindas.setText("Bem-vindo, " + username + "!");
    }
}
