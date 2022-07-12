package br.com.promeet.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ConfigActivity extends AppCompatActivity {
    private TextInputEditText editTextCidade = null;
    private TextInputEditText editTextEmail = null;
    private SharedPreferences prefs = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        FirebaseApp.initializeApp(ConfigActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ConfigActivity.this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "onCreate");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.ITEM_ID, "Config");
        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "Config Activity");
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Configurações do App");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);

        Button button = findViewById(R.id.button);
        editTextCidade = findViewById(R.id.textCidade);
        editTextEmail = findViewById(R.id.textEmail);
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