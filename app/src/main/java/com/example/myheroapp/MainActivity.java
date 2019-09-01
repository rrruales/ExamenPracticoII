package com.example.myheroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtBuscar;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busqueda(view);
            }
        });

    }

    public void busqueda(View view){
        String heroe = txtBuscar.getText().toString();
        Intent intent = new Intent(this.getBaseContext(), Busqueda.class);
        intent.putExtra("heroe", heroe);
        startActivity(intent);
    }
}
