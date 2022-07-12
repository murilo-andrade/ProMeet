package br.com.promeet.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {
    protected ListView lista;
    protected EventoValue eventoValue;
    protected ArrayAdapter<EventoValue> adapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        int layout = android.R.layout.simple_list_item_1;
        FirebaseApp.initializeApp(AgendaActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(AgendaActivity.this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "onCreate");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.ITEM_ID, "Agenda");
        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Agenda Activity");
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Lista de eventos");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);


        SharedPreferences prefs = getSharedPreferences("syPrefs", MODE_PRIVATE);
        String cidade = prefs.getString("cidade","");
        String temperatura = prefs.getString("temperatura","");
        String tempo = prefs.getString("tempo","");
        TextView rodape = findViewById(R.id.textViewBotton);
        rodape.setText(cidade+" - "+tempo+" - "+temperatura+"C");
        EventoDAO dao = new EventoDAO(this);

        ArrayList<EventoValue> eventos= (ArrayList<EventoValue>) new ArrayList(dao.getLista());
        dao.close();
        adapter =
                new ArrayAdapter<EventoValue>(this,layout, eventos);
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adapter);
        lista.setOnCreateContextMenuListener(this);
        registerForContextMenu(lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_eventos, menu);
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_evento, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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

    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int layout = android.R.layout.simple_list_item_1;
        EventoDAO dao = new EventoDAO(this);
        ArrayList<EventoValue> eventos = new ArrayList(dao.getLista());
        dao.close();
        ArrayAdapter<EventoValue> adapter =
                new ArrayAdapter<EventoValue>(this,layout, eventos);

        eventoValue = this.adapter.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        int id = item.getItemId();
        if (id == R.id.action_new) {
            Intent intent = new Intent(this, EventoActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(this, EventoActivity.class);
            intent.putExtra("eventoSelecionado", eventoValue);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_cancel) {
            dao.deletar(eventoValue);
            dao.close();
            this.onResume();
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int codigo,int reultado,Intent it){
        super.onActivityResult(codigo, reultado, it);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO dao = new EventoDAO(this);
        ArrayList<EventoValue> disciplinas = new ArrayList(dao.getLista());
        dao.close();
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<EventoValue> adapter =
                (ArrayAdapter<EventoValue>) new ArrayAdapter<>(this,layout, disciplinas);
        ListView lista = findViewById(R.id.listView);
        lista.setAdapter(adapter);
        SharedPreferences prefs = getSharedPreferences("syPrefs", MODE_PRIVATE);
        String cidade = prefs.getString("cidade","");
        String temperatura = prefs.getString("temperatura","");
        String tempo = prefs.getString("tempo","");
        TextView rodape = findViewById(R.id.textViewBotton);
        rodape.setText(cidade+" - "+tempo+" - "+temperatura+"C");
    }
}