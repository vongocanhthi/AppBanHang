package com.vnat.appbanhang.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.Service.ApiService;
import com.vnat.appbanhang.Service.CheckConnection;
import com.vnat.appbanhang.Service.DataService;
import com.vnat.appbanhang.adapter.LoaiSanPhamAdapter;
import com.vnat.appbanhang.adapter.SanPhamAdapter;
import com.vnat.appbanhang.model.GioHang;
import com.vnat.appbanhang.model.LoaiSanPham;
import com.vnat.appbanhang.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static ArrayList<LoaiSanPham> loaiSanPhamArrayList;
    static ArrayList<SanPham> sanPhamArrayList;
    static SanPhamAdapter sanPhamAdapter;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    DrawerLayout drawerLayout;
    Animation in, out;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView rccSanPhamMoiNhat;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    public static DataService dataService;
    public static ArrayList<GioHang> gioHangArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
            anhXa();
            actionBar();
            addEventViewFlipper();
            getdulieuloaisp();
            getdulieuSpmoinhat();
            catchOnItemListView();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Không có kết nối intenret");
            finish();
        }
    }

    private void catchOnItemListView() {
        lvManHinhChinh.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0: {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                case 1: {
                    if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                        intent.putExtra("iddienthoai", 1);
                        startActivity(intent);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                case 2: {
                    if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, LapTopActivity.class);
                        intent.putExtra("idlaptop", 2);
                        startActivity(intent);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                case 3: {
                    if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(intent);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
                case 4: {
                    if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                        startActivity(intent);
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                }
            }
        });
    }

    private void getdulieuloaisp() {
        Call<List<LoaiSanPham>> call = dataService.getLoaiSanPham();
        call.enqueue(new Callback<List<LoaiSanPham>>() {
            @Override
            public void onResponse(Call<List<LoaiSanPham>> call, Response<List<LoaiSanPham>> response) {
                if (response.body() != null) {
                    loaiSanPhamArrayList.addAll(response.body());
                }
                loaiSanPhamArrayList.add(new LoaiSanPham(0, "Liên Hệ", "https://shopvnat.000webhostapp.com/HinhAnh/Icon/ic_contact.png"));
                loaiSanPhamArrayList.add(new LoaiSanPham(0, "Thông Tin", "https://shopvnat.000webhostapp.com/HinhAnh/Icon/ic_info.png"));
                loaiSanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LoaiSanPham>> call, Throwable t) {

            }
        });
    }
    private void getdulieuSpmoinhat() {
        Call<List<SanPham>> call = dataService.getSanPhamMoiNhat();
        call.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response.body() != null) {
                    sanPhamArrayList.addAll(response.body());
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });

    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang chủ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void anhXa() {
        dataService = ApiService.getService();
        in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        toolbar = findViewById(R.id.toolbar_manhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        rccSanPhamMoiNhat = findViewById(R.id.rccSanPhamMoiNhat);
        navigationView = findViewById(R.id.navigationview);
        lvManHinhChinh = findViewById(R.id.lv_manhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        loaiSanPhamArrayList = new ArrayList<>();
        loaiSanPhamArrayList.add(new LoaiSanPham(0, "Trang chủ", "https://shopvnat.000webhostapp.com/HinhAnh/Icon/ic_home.png"));
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(getApplicationContext(), loaiSanPhamArrayList);
        lvManHinhChinh.setAdapter(loaiSanPhamAdapter);
        sanPhamArrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanPhamArrayList);

        rccSanPhamMoiNhat.setHasFixedSize(true);
        rccSanPhamMoiNhat.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rccSanPhamMoiNhat.setAdapter(sanPhamAdapter);
        if (gioHangArrayList == null) {
            gioHangArrayList = new ArrayList<>();
        }
    }

    private void addEventViewFlipper() {
        ArrayList<String> arrQuangCao = new ArrayList<>();
        arrQuangCao.add("https://shopvnat.000webhostapp.com/HinhAnh/QuangCao/qc1.png");
        arrQuangCao.add("https://shopvnat.000webhostapp.com/HinhAnh/QuangCao/qc2.png");
        arrQuangCao.add("https://shopvnat.000webhostapp.com/HinhAnh/QuangCao/qc3.png");
        arrQuangCao.add("https://shopvnat.000webhostapp.com/HinhAnh/QuangCao/qc4.png");
        for (int i = 0; i < arrQuangCao.size(); i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            Picasso.with(MainActivity.this).load(arrQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setAutoStart(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_giohang) {
            Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public class readJson extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder=new StringBuilder();
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sanPhamArrayList.add(new SanPham(object.getInt("idsp")
                                ,object.getString("tensp")
                                ,object.getInt("giasp")
                                ,object.getString("hinhanhsp")
                                ,object.getString("motasp")
                                ,object.getInt("idloaisp")
                                ,object.getInt("idhang")));
                        sanPhamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
