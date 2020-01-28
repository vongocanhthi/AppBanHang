package com.vnat.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.adapter.DienThoaiAdapter;
import com.vnat.appbanhang.Service.CheckConnection;
import com.vnat.appbanhang.model.LoaiSanPham;
import com.vnat.appbanhang.model.SanPham;
import com.vnat.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDt;
    ListView lvdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPham> mangDienThoai;
    int iddt=0;
    int page=1;
    int idHang = -1;
    View footerView;
    boolean isLoading=false, limitData = false;
    mHandler mHandler;
    Spinner spinnerSapXep, spinnerHang;

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
            setSpinner();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(), "Vui lòng kiểm tra kết nối");
        }
    }

    private void setSpinner() {
        final String[] mangSapXep = {"Giá thấp", "Giá cao"};
        spinnerSapXep.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mangSapXep));

        String[] mangHang = {"Hãng", "iPhone", "SAMSUNG", "OPPO", "XIAOMI", "VIVO", "realme", "VSMART", "HUAWEI"
                , "NOKIA", "mobell", "itel", "coolpad", "BlackBerry", "Masstel"};

        spinnerHang.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mangHang));

        spinnerSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                switch (position){
                    case 0:
                        sapXepDienThoaiGiaThap();
                        break;
                    case 1:
                        sapXepDienThoaiGiaCao();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                idHang = position + 7;
                Call<List<SanPham>> call = MainActivity.dataService.getSanPhamTheoHang(idHang);
                call.enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        mangDienThoai.clear();
                        mangDienThoai.addAll(response.body());
                        dienThoaiAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void sapXepDienThoaiGiaCao() {
        Call<List<SanPham>> call = MainActivity.dataService.getDienThoaiGiaCao();
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                mangDienThoai.clear();
                mangDienThoai.addAll(response.body());
                dienThoaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }

    private void sapXepDienThoaiGiaThap() {
        Call<List<SanPham>> call = MainActivity.dataService.getDienThoaiGiaThap();
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                mangDienThoai.clear();
                mangDienThoai.addAll(response.body());
                dienThoaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }

    private void clickItemListView() {
        lvdt.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent=new Intent(DienThoaiActivity.this,ChiTietActivity.class);
            intent.putExtra("chitietsanpham",mangDienThoai.get(i));
            startActivity(intent);
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
                if(firstItem + visiableItem == totalItem && totalItem != 0 && !isLoading && !limitData){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });

        lvdt.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ChiTietActivity.class);
            intent.putExtra("thongtinsanpham", mangDienThoai.get(position));
            startActivity(intent);
        });
    }



    private void getData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.duongdansanpam+ Page;
        StringRequest str=new StringRequest(Request.Method.POST, duongdan, response -> {
            if(response!=null && response.length() != 2){
                lvdt.removeFooterView(footerView);
                try {
                    JSONArray json=new JSONArray(response);
                    for(int i=0;i<json.length();i++){
                        JSONObject object=json.getJSONObject(i);
                        mangDienThoai.add(new SanPham(object.getInt("idsp")
                                ,object.getString("tensp")
                                ,object.getInt("giasp")
                                ,object.getString("hinhanhsp")
                                ,object.getString("motasp")
                                ,object.getInt("idloaisp")
                                ,object.getInt("idhang")));
                        dienThoaiAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                limitData = true;
                lvdt.removeFooterView(footerView);
                CheckConnection.showToast_Short(getApplicationContext(), "Đang cập nhật sản phẩm...");
            }
        }, error -> {

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
        spinnerSapXep = findViewById(R.id.spinnerSapXep);
        spinnerHang = findViewById(R.id.spinnerHang);
        toolbarDt= findViewById(R.id.toolbar_dienthoai);
        lvdt= findViewById(R.id.lv_dienthoai);
        mangDienThoai = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangDienThoai);
        lvdt.setAdapter(dienThoaiAdapter);
        dienThoaiAdapter.notifyDataSetChanged();

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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
        }
    }
}
