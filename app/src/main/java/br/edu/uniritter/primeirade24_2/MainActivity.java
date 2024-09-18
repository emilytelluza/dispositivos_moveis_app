package br.edu.uniritter.primeirade24_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout para a tela de boas-vindas
        setContentView(R.layout.main_activity);

        // Configuração da mensagem de boas-vindas
        TextView textViewBoasVindas = findViewById(R.id.textViewBoasVindas);
        textViewBoasVindas.setText("Bem-vindo!");

        // Configura o botão de cadastro
        Button btnCadastro = findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia a Activity de cadastro (Tela 2)
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
}
