package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.adapter.LapTopAdapter;
import com.vnat.appbanhang.model.SanPham;
import com.vnat.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LapTopActivity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView lvLapTop;
    LapTopAdapter laptopAdapter;
    ArrayList<SanPham> mangLaptop;
    int idLapTop=0;
    int dodai=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_top);

        anhXa();
        getIdLaptop();
        actionToolbar();
        readJsonData();
        clickListView();
    }

    private void clickListView() {
        lvLapTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(LapTopActivity.this,ChiTietActivity.class);
                intent.putExtra("chitietsanpham",mangLaptop.get(i));
                startActivity(intent);
            }
        });
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setTitle("Máy Tính");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void readJsonData() {
        RequestQueue requestQueue= Volley.newRequestQueue(LapTopActivity.this);
        String duongdan= Server.duongdanlaptop;
        StringRequest str=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int id=0;
                    String tensp="";
                    int giasp=0;
                    String hinhanhsp="",motasp="";
                    int idsp=0;
                    JSONArray jsonArray=new JSONArray(response);
                    dodai=jsonArray.length();
                    for(int i=0;i<dodai;i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        id=object.getInt("id");
                        tensp=object.getString("tensp");
                        giasp=object.getInt("giasp");
                        hinhanhsp=object.getString("hinhanhsp");
                        motasp=object.getString("motasp");
                        idsp=object.getInt("idsanpham");
                        SanPham sanPham=new SanPham(id,tensp,giasp,hinhanhsp,motasp,idsp);
                        mangLaptop.add(sanPham);
                        laptopAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LapTopActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<>();
                param.put("idsanpham", String.valueOf(idLapTop));
                return param;
            }
        };
        requestQueue.add(str);
    }

    private void getIdLaptop() {
        idLapTop=getIntent().getIntExtra("idloaisanpham",-1);
    }

    private void anhXa() {
        toolbarLaptop= (Toolbar) findViewById(R.id.toolbar_laptop);
        lvLapTop= (ListView) findViewById(R.id.lv_laptop);
        mangLaptop=new ArrayList<>();
        laptopAdapter=new LapTopAdapter(LapTopActivity.this,mangLaptop);
        lvLapTop.setAdapter(laptopAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_giohang){
            Intent intent=new Intent(LapTopActivity.this,GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
