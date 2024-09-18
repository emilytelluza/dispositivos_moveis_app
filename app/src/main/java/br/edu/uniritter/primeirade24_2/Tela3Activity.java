package br.edu.uniritter.primeirade24_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Tela3Activity extends AppCompatActivity {

    private EditText edEmailLogin, edSenhaLogin;
    private Button btnLogin;
    private String emailCadastrado, senhaCadastrada, usernameCadastrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Infla o layout da tela de login
        setContentView(R.layout.tela3_activity);

        // Obtendo referências dos campos de texto
        edEmailLogin = findViewById(R.id.edEmailLogin);
        edSenhaLogin = findViewById(R.id.edSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // Obtendo os dados de email, senha e username cadastrados enviados pela Tela 2 (CadastroActivity)
        Intent intent = getIntent();
        emailCadastrado = intent.getStringExtra("email");
        senhaCadastrada = intent.getStringExtra("senha");
        usernameCadastrado = intent.getStringExtra("username");

        // Ação de login ao clicar no botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se o email e a senha correspondem aos dados cadastrados
                String emailLogin = edEmailLogin.getText().toString();
                String senhaLogin = edSenhaLogin.getText().toString();

                if (emailLogin.equals(emailCadastrado) && senhaLogin.equals(senhaCadastrada)) {
                    // Login bem-sucedido, avança para a Tela 4
                    Intent intent = new Intent(Tela3Activity.this, Tela4Activity.class);

                    // Enviar o username para a Tela 4
                    intent.putExtra("username", usernameCadastrado);
                    startActivity(intent);
                } else {
                    // Mostra um erro se as credenciais forem inválidas
                    Toast.makeText(Tela3Activity.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
