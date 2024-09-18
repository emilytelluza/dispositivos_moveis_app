package br.edu.uniritter.primeirade24_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText edUsername, edEmail, edSenha, edConfirmarSenha;
    private Button btnAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout da tela de cadastro
        setContentView(R.layout.cadastro_activity);

        // Referências para os campos de texto
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        edConfirmarSenha = findViewById(R.id.edConfirmarSenha);

        // Botão para avançar para a próxima tela
        btnAvancar = findViewById(R.id.btnAvancarCadastro);
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se os campos de senha e confirmar senha são iguais
                String senha = edSenha.getText().toString().trim();
                String confirmarSenha = edConfirmarSenha.getText().toString().trim();

                if (senha.equals(confirmarSenha)) {
                    // Se as senhas forem iguais, avançamos para a tela de login (Tela 3)
                    Intent intent = new Intent(CadastroActivity.this, Tela3Activity.class);

                    // Enviando os dados de username, email e senha para a Tela 3
                    intent.putExtra("username", edUsername.getText().toString().trim()); // Remover espaços extras
                    intent.putExtra("email", edEmail.getText().toString().trim()); // Remover espaços extras
                    intent.putExtra("senha", senha); // Senha já está trim()

                    // Iniciar a Tela3Activity
                    startActivity(intent);
                } else {
                    // Mostra um alerta se as senhas não forem iguais
                    Toast.makeText(CadastroActivity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
