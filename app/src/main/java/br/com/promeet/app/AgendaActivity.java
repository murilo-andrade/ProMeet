package br.com.promeet.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
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
    }
}