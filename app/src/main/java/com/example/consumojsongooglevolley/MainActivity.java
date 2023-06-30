package com.example.consumojsongooglevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView txtProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProductos = (TextView) findViewById(R.id.txtProdutos);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/productos/search";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> lstProductos = new ArrayList<String> ();
                        String lstpro="";
                        try {
                            JSONObject lstProducts= new JSONObject(response);
                            JSONArray JSONlista = lstProducts.getJSONArray("productos");
                            for(int i=0; i< JSONlista.length();i++) {
                                JSONObject producto = JSONlista.getJSONObject(i);
                                //lstProductos.add(producto.getString("id").toString());
                                //lstProductos.add(producto.getString("descripcion").toString());
                                lstpro = lstpro + "id: " + producto.getString("id").toString() + "\n" +
                                        "Descripcion: " + producto.getString("descripcion").toString() +"\n"+
                                        "Precio: " + producto.getString("precio_unidad").toString()+ "\n"+
                                        "Descuento  : "+ producto.getString("descuento").toString()+ "\n"+
                                        "Categoría  : "+ producto.getString("p_categoria").toString()+ "\n";;
                            }
                            txtProductos = findViewById(R.id.txtProdutos);
                            //txtProductos.setText(lstProductos.toString());
                            txtProductos.setText(lstpro);
                            //Log.i("Productos",lstProductos.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),  error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZHVzciI6NDYsImVtYWlsIjoiY2FybG9zQGdtYWlsLmNvbSIsInRpcG9fdXNlciI6MywiZXN0YWJsZWNpbWllbnRvX2lkIjoxLCJiZF9ub21icmUiOiJ1Nzg3OTU2NDQyX2FwaSIsImJkX3VzdWFyaW8iOiJ1Nzg3OTU2NDQyX2FwaSIsImJkX2NsYXZlIjoiVXRlcTIwMjIqIiwiYmRfaG9zdCI6ImxvY2FsaG9zdCIsImJkX2lwIjoiIiwiaWF0IjoxNjg4MTMxMDkyLCJleHAiOjE2ODg0OTEwOTJ9.wSd43Nh1ct3qRKNaf6rQs_weWjoK-sgozAoXthvQkbg");
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("fuente", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}