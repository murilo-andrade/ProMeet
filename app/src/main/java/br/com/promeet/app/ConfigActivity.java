package br.com.promeet.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {
    private EditText editText = null;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextCidade);
        prefs = getSharedPreferences("syPrefs", MODE_PRIVATE);
        String cidade = prefs.getString("cidade","");

        if(cidade!=null){
            editText.setText(cidade);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SharedPreferences.Editor editPreferences = prefs.edit();
                editPreferences.putString("cidade",ConfigActivity.this.editText.getText().toString());
                editPreferences.apply();
                finish();
            }
        });
    }
}