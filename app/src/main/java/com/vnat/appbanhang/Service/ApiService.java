package com.vnat.appbanhang.Service;

public class ApiService {
    private static String base_url = "https://shopvnat.000webhostapp.com/Server/";

    public static DataService getService(){
        return ApiRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
