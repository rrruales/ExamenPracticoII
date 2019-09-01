package com.example.myheroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grafica extends AppCompatActivity {

    public BarChart graficoBarra;
    private RequestQueue listaRequest = null;
    private LinearLayout contHeroes;
    private Map<String, TextView> heroes;

    TextView txtNombre, txtFullNombre;

    String idHeroe;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        heroes = new HashMap<String, TextView>();
        listaRequest = Volley.newRequestQueue(this);
        mQueue = Volley.newRequestQueue(this);

        txtNombre = findViewById(R.id.txtNombre);
        txtFullNombre = findViewById(R.id.txtFullNombre);

        Intent intent = getIntent();
        this.idHeroe = (String) intent.getExtras().get("id_heroe");

        grafico();
        capturarDatos(idHeroe);
    }

    private void capturarDatos(String idHeroe) {

        String url = "https://www.superheroapi.com/api.php/2167533850019629/"+idHeroe;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            String nombre = response.getString("name");
                            txtNombre.setText(nombre);

                            JSONObject completo = response.getJSONObject("biography");
                            String nombreCompleto = completo.getString("full-name");
                            txtFullNombre.setText(nombreCompleto);

                            JSONObject poderes = response.getJSONObject("powerstats");
                            actualizar(poderes);
                        }catch (JSONException e){

                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        mQueue.add(request);
    }

    private void actualizar(JSONObject poderes){

        JSONObject registro_temp;
        String velocidad;
        String durabilidad;
        String poder;
        String combate;
        String fuerza;
        String inteligencia;

        ArrayList<BarEntry> datoHeroe = new ArrayList<>();

        try{

            fuerza = poderes.getString("strength");
            velocidad = poderes.getString("speed");
            durabilidad = poderes.getString("durability");
            poder = poderes.getString("power");
            combate = poderes.getString("combat");
            inteligencia = poderes.getString("intelligence");

            datoHeroe.add(new BarEntry(1,Float.parseFloat(inteligencia)));
            datoHeroe.add(new BarEntry(2,Float.parseFloat(fuerza)));
            datoHeroe.add(new BarEntry(3,Float.parseFloat(velocidad)));
            datoHeroe.add(new BarEntry(4,Float.parseFloat(durabilidad)));
            datoHeroe.add(new BarEntry(5,Float.parseFloat(poder)));
            datoHeroe.add(new BarEntry(6,Float.parseFloat(combate)));

        }catch (JSONException e){

            e.printStackTrace();

        }

        llenar(datoHeroe);

    }

    private void llenar(ArrayList<BarEntry> datoHeroe){

        BarDataSet heroesData;

        if(graficoBarra.getData() != null && graficoBarra.getData().getDataSetCount() > 0){

            heroesData = (BarDataSet) graficoBarra.getData().getDataSetByIndex(0);
            heroesData.setValues(datoHeroe);
            graficoBarra.getData().notifyDataChanged();
            graficoBarra.notifyDataSetChanged();

        }else{

            heroesData = new BarDataSet(datoHeroe, "Establecer Dato");
            heroesData.setColors(ColorTemplate.VORDIPLOM_COLORS);
            heroesData.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(heroesData);
            BarData data = new BarData(dataSets);
            graficoBarra.setData(data);
            graficoBarra.setFitBars(true);

        }

        graficoBarra.invalidate();

    }


    private void grafico() {

        graficoBarra = findViewById(R.id.barChart);
        graficoBarra.getDescription().setEnabled(false);
        graficoBarra.setMaxVisibleValueCount(60);
        graficoBarra.setPinchZoom(false);
        graficoBarra.setDrawBarShadow(false);
        graficoBarra.setDrawGridBackground(false);

        XAxis xAxis = graficoBarra.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);

        graficoBarra.getAxisLeft().setDrawGridLines(false);
        graficoBarra.animateY(1500);
        graficoBarra.getLegend().setEnabled(false);

    }
}
