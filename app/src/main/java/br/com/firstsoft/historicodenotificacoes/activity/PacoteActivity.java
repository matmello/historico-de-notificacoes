package br.com.firstsoft.historicodenotificacoes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.firstsoft.historicodenotificacoes.R;

public class PacoteActivity extends AppCompatActivity {

    private String pacote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacote);
        pacote = getIntent().getExtras().getString("PACOTE");
    }
}
