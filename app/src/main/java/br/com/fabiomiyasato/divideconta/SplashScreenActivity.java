package br.com.fabiomiyasato.divideconta;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.fabiomiyasato.divideconta.dao.UsuarioDAO;
import br.com.fabiomiyasato.divideconta.model.Usuario;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4500;
    public static final int CONNECTION_TIMEOUT = 40000;
    public static final int READ_TIMEOUT = 45000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new AsyncFetch().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash);
        anim.reset();
        //Pegando o nosso objeto criado no layout
        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }

    }

    private void gravarDados(Usuario usuario) {
        UsuarioDAO dao = new UsuarioDAO(SplashScreenActivity.this);
        Usuario usuarioCadastrado = dao.getByNome(usuario.getNome());
        if(usuarioCadastrado == null){
            dao.add(usuario);
        }
    }

    public class AsyncFetch extends AsyncTask<String , Void ,String> {
        Usuario usuario;

        @Override
        protected void onPreExecute(){
            carregar();
        }

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String json = readStream(urlConnection.getInputStream());
                    return json;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            JSONObject jsonData = null;
            try {
                jsonData = new JSONObject(json);
                usuario = new Usuario();
                usuario.setNome(jsonData.getString("usuario"));
                usuario.setSenha(jsonData.getString("senha"));
                gravarDados(usuario);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Após o tempo definido irá executar a próxima
                        Intent intent = new Intent(SplashScreenActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
