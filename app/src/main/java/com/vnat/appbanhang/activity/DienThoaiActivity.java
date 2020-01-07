package com.vnat.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
import com.vnat.appbanhang.adapter.DienThoaiAdapter;
import com.vnat.appbanhang.model.SanPham;
import com.vnat.appbanhang.ultil.CheckConnection;
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
    boolean isLoading=false, limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhXa();
            getIdloaisp();
            actionToolbar();
            getData(page);
            loadMoreData();
            clickItemListView();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(), "Vui lòng kiểm tra kết nối");
        }
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
                if(firstItem + visiableItem == totalItem && totalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });

        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietActivity.class);
                intent.putExtra("thongtinsanpham", mangdt.get(position));
                startActivity(intent);
            }
        });
    }



    private void getData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.duongdandienthoai+ Page;
        StringRequest str=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null && response.length() != 2){
                    lvdt.removeFooterView(footerView);
                    try {
                        JSONArray json=new JSONArray(response);
                        for(int i=0;i<json.length();i++){
                            JSONObject object=json.getJSONObject(i);
                            mangdt.add(new SanPham(object.getInt("idsp")
                                    ,object.getString("tensp")
                                    ,object.getInt("giasp")
                                    ,object.getString("hinhanhsp")
                                    ,object.getString("motasp")
                                    ,object.getInt("idloaisp")));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitData = true;
                    lvdt.removeFooterView(footerView);
                    CheckConnection.showToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> param= new HashMap<>();
                param.put("idloaisp", String.valueOf(iddt));
                return param;
            }
        };
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
        iddt=getIntent().getIntExtra("iddienthoai",-1);
    }

    private void anhXa() {
        toolbarDt= findViewById(R.id.toolbar_dienthoai);
        lvdt= findViewById(R.id.lv_dienthoai);
        mangdt=new ArrayList<>();
        dienThoaiAdapter=new DienThoaiAdapter(DienThoaiActivity.this,mangdt);
        lvdt.setAdapter(dienThoaiAdapter);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
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

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            super.run();
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
        }
    }
}
