package com.vnat.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vnat.appbanhang.R;
import com.vnat.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KhachHangActivity extends AppCompatActivity {
    EditText edttenkhachhang,edtemail,edtsdt;
    Button btnXacNhan,btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        anhXa();
        eventButton();
    }

    private void eventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten=edttenkhachhang.getText().toString();
                final String sdt=edtsdt.getText().toString();
                final String email=edtemail.getText().toString();
                if(ten.length()>0 && sdt.length()>0 && email.length()>0){
                    final RequestQueue requestQueue= Volley.newRequestQueue(KhachHangActivity.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(KhachHangActivity.this);
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.gioHangArrayList.clear();
                                            Toast.makeText(KhachHangActivity.this, "Bạn đã thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            Toast.makeText(KhachHangActivity.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(KhachHangActivity.this, "Lỗi không thêm giỏ hàng được", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() {
                                        JSONArray jsonArray=new JSONArray();
                                        for(int i=0;i<MainActivity.gioHangArrayList.size();i++){
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.gioHangArrayList.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.gioHangArrayList.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.gioHangArrayList.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.gioHangArrayList.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap= new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    Toast.makeText(KhachHangActivity.this, "Hãy kiểm tra dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        edttenkhachhang= findViewById(R.id.edt_tenkhachhang);
        edtemail= findViewById(R.id.edt_email);
        edtsdt= findViewById(R.id.edt_sdt);
        btnTroVe= findViewById(R.id.btn_trove);
        btnXacNhan= findViewById(R.id.btn_xacnhan);
    }
}
