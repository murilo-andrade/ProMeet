package br.com.promeet.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {
    private EditText editTextCidade = null;
    private EditText editTextEmail = null;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button button = findViewById(R.id.button);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextEmail = findViewById(R.id.editTextEmail);
        prefs = getSharedPreferences("syPrefs", MODE_PRIVATE);
        String cidade = prefs.getString("cidade","");
        String email = prefs.getString("email","");

        if(cidade!=null){
            editTextCidade.setText(cidade);
        }

        if(email!=null){
            editTextEmail.setText(email);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SharedPreferences.Editor editPreferences = prefs.edit();
                editPreferences.putString("cidade",ConfigActivity.this.editTextCidade.getText().toString());
                editPreferences.putString("email",ConfigActivity.this.editTextEmail.getText().toString());
                editPreferences.apply();
                finish();
            }
        });
    }
}