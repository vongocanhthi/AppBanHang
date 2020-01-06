package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.adapter.DienThoaiAdapter;
import com.vnat.appbanhang.model.SanPham;
import com.vnat.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPham> mangdt;
    int iddt=0;
    int page=1;
    View footerView;
    boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        anhXa();
        getIdloaisp();
        actionToolbar();
        getData(page);
        clickItemListView();
    }

    private void clickItemListView() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(DienThoaiActivity.this,ChiTietActivity.class);
                intent.putExtra("chitietsanpham",mangdt.get(i));
                startActivity(intent);
            }
        });
    }

    private void loadMoreData() {
        //Sự kiện kéo listview
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visiableItem, int totalItem) {

            }
        });
    }



    private void getData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(DienThoaiActivity.this);
        String duongdan= Server.duongdandienthoai+Page;
        StringRequest str=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null){
                    try {
                        JSONArray json=new JSONArray(response);
                        int dodai=json.length();
                        for(int i=0;i<json.length();i++){
                            JSONObject object=json.getJSONObject(i);
                            mangdt.add(new SanPham(object.getInt("id"),object.getString("tensp"),object.getInt("giasp"),object.getString("hinhanhsp"),object.getString("motasp"),object.getInt("idsanpham")));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(iddt));
                return param;
            }
        }
                ;
        requestQueue.add(str);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarDt);
        getSupportActionBar().setTitle("Điện Thoại");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIdloaisp() {
        iddt=getIntent().getIntExtra("idloaisanpham",-1);
    }

    private void anhXa() {
        toolbarDt= (Toolbar) findViewById(R.id.toolbar_dienthoai);
        lvdt= (ListView) findViewById(R.id.lv_dienthoai);
        mangdt=new ArrayList<>();
        dienThoaiAdapter=new DienThoaiAdapter(DienThoaiActivity.this,mangdt);
        lvdt.setAdapter(dienThoaiAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_giohang){
            Intent intent=new Intent(DienThoaiActivity.this,GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
