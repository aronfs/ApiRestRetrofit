package com.example.apirestretrofit.ApiService;

import com.example.apirestretrofit.models.CoffeRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApiService {


    @GET("coffee")
    Call<CoffeRespuesta> obtenerListaCoffe(@Query("limit") int limit, @Query("offset") int offset);
}
