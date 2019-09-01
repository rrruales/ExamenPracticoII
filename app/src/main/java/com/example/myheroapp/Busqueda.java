package com.example.myheroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Busqueda extends AppCompatActivity {

    TextView txtCantHeroes;
    String heroe;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        mQueue = Volley.newRequestQueue(this);
        txtCantHeroes = findViewById(R.id.txtCantHeroes);

        Intent intent = getIntent();
        this.heroe = (String) intent.getExtras().get("heroe");

        buscarHeroe(heroe);

    }

    private void buscarHeroe(String heroe) {

        String url = "https://www.superheroapi.com/api.php/2167533850019629/search/"+heroe;
        final LinearLayout heroes = (LinearLayout) findViewById(R.id.listaHeroes);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray cantidad = response.getJSONArray("results");
                            txtCantHeroes.setText("Resultado: " + cantidad.length());

                            for(int i = 0; i < cantidad.length(); i++){
                                JSONObject j = cantidad.getJSONObject(i);
                                final String id = j.getString("id");
                                String nombreHeroe = j.getString("name");

                                TextView nombreHeroeTV = new TextView(getBaseContext());

                                nombreHeroeTV.setId(Integer.parseInt(id));
                                nombreHeroeTV.setText(nombreHeroe);
                                nombreHeroeTV.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getBaseContext(), Grafica.class);
                                        intent.putExtra("id_heroe", id);
                                        startActivity(intent);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }
        );


    }
}
