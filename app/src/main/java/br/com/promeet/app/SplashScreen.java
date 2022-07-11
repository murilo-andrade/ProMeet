package br.com.promeet.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    private final TextView textView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.branco));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new JSONParse().execute();
            }
        }, SPLASH_TIME_OUT);
    }

    public class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= new ProgressDialog(SplashScreen.this);
            pDialog.setMessage("Obtendo Dados");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject json;
            EventoDAO eventoDAO = new EventoDAO(SplashScreen.this);
            eventoDAO.dropAll();
            JSONArray link;
            json = Json();
            try {
                link = json.getJSONArray("Lista");
                for (int i = 0; i < link.length(); i++) {
                    String c = (String) link.get(i);
                    EventoValue eventoValue = new EventoValue();
                    eventoValue.setEvento(c);
                    eventoDAO.salvar(eventoValue);
                    eventoDAO.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            try {
                pDialog.dismiss();
                Intent i = new Intent(SplashScreen.this, AgendaActivity.class);
                startActivity(i);
                try {
                    finish();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private JSONObject Json() {
            JSONObject json;
            String resp;
            try {
                URL api = new URL("https://weatherapi-com.p.rapidapi.com/current.json?lang=pt&q=Salvador");
                HttpURLConnection conn = (HttpURLConnection) api.openConnection();
                conn.setRequestProperty("X-RapidAPI-Key","c80485ddd9msh0c7ec6e2e9e75cap125d71jsn8a800a1c38db");
                conn.setRequestProperty("X-RapidAPI-Host","weatherapi-com.p.rapidapi.com");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                InputStream inputStream = conn.getInputStream();
                resp = IOUtils.toString(inputStream);
                json = new JSONObject(resp);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}