package br.com.promeet.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class EventoActivity extends AppCompatActivity {
    protected TextInputEditText editTextEvento;
    protected TextInputEditText editTextData;
    protected TextInputEditText editTextHora;
    protected TextInputEditText editTextLocal;
    protected String email;
    protected EventoValue eventoSelecionado;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("syPrefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        Button button = findViewById(R.id.button);
        FirebaseApp.initializeApp(EventoActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(EventoActivity.this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "onCreate");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.ITEM_ID, "Evento");
        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Evento Activity");
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Cadasto ou Edicao de Evento");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);

        this.editTextEvento = (TextInputEditText) findViewById(R.id.textEvento);
        this.editTextEvento.setText(R.string.novo_evento);
        this.editTextData = (TextInputEditText) findViewById(R.id.textData);
        this.editTextHora = (TextInputEditText) findViewById(R.id.textHora);
        this.editTextLocal = (TextInputEditText) findViewById(R.id.textLocal);
        this.email = prefs.getString("email","");
        Intent intent = getIntent();

        eventoSelecionado = (EventoValue) intent.getSerializableExtra("eventoSelecionado");
        if(eventoSelecionado!=null){
            button.setText(R.string.editar_evento);
            editTextEvento.setText(eventoSelecionado.getEvento());
            editTextData.setText(eventoSelecionado.getData());
            editTextHora.setText(eventoSelecionado.getHora());
            editTextLocal.setText(eventoSelecionado.getLocal());
            email = prefs.getString("email","");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventoDAO dao = new EventoDAO(EventoActivity.this);
                if (eventoSelecionado==null) {
                    EventoValue eventoValue = new EventoValue();
                    eventoValue.setEvento(editTextEvento.getText().toString());
                    eventoValue.setData(editTextData.getText().toString());
                    eventoValue.setHora(editTextHora.getText().toString());
                    eventoValue.setLocal(editTextLocal.getText().toString());
                    eventoValue.setUsuario(prefs.getString("email",""));
                    dao.salvar(eventoValue);
                }else{
                    eventoSelecionado.setEvento(editTextEvento.getText().toString());
                    eventoSelecionado.setData(editTextData.getText().toString());
                    eventoSelecionado.setHora(editTextHora.getText().toString());
                    eventoSelecionado.setLocal(editTextLocal.getText().toString());
                    eventoSelecionado.setUsuario(prefs.getString("email",""));
                    dao.alterar(eventoSelecionado);
                }
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_eventos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, EventoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}