package br.com.fabiomiyasato.divideconta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import br.com.fabiomiyasato.divideconta.dao.UsuarioDAO;
import br.com.fabiomiyasato.divideconta.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private final String KEY_APP_PREFERENCES = "login";
    private final String KEY_LOGIN = "login";

    private TextInputLayout tilLogin;
    private TextInputLayout tilSenha;
    private CheckBox cbManterConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);

        if(isConectado())
            iniciarApp();
    }

    private void manterConectado(){
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConectado() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES,MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if(login.equals(""))
            return false;
        else
            return true;
    }

    //Método que será chamado no onclick do botao
    public void logar(View v){
        if(isLoginValido()){
            if(cbManterConectado.isChecked()){
                manterConectado();
            }
            iniciarApp();
        }
    }

    // Valida o login
    private boolean isLoginValido() {
        String login = tilLogin.getEditText().getText().toString();
        String senha = tilSenha.getEditText().getText().toString();
        UsuarioDAO dao = new UsuarioDAO(LoginActivity.this);
        Usuario usuarioCadastrado = dao.getByNome(login);
        boolean isLoginOk = false;
        if (usuarioCadastrado == null){
            Toast.makeText(LoginActivity.this, R.string.user_not_found, Toast.LENGTH_LONG).show();
        }else{
            if(usuarioCadastrado.getSenha().equals(senha)){
                isLoginOk = true;
            }else{
                Toast.makeText(LoginActivity.this, R.string.password_incorrect, Toast.LENGTH_LONG).show();
            }
        }
        return isLoginOk;
    }



    private void iniciarApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}