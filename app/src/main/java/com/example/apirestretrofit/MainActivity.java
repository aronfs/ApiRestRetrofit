package com.example.apirestretrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.apirestretrofit.ApiService.MyApiService;
import com.example.apirestretrofit.models.Coffe;
import com.example.apirestretrofit.models.CoffeRespuesta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RetroFit";

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaCoffeAdapter listaCoffeAdapter;

    private boolean aptoParaCargar;
    private int offset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        listaCoffeAdapter = new ListaCoffeAdapter(this);
        recyclerView.setAdapter(listaCoffeAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar){
                        if ((visibleItemCount+pastVisibleItems) >= totalItemCount){
                            Log.i(TAG, "Llegamos al final");
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }

                }

            }
        });
        retrofit = new Retrofit.Builder().baseUrl("https://github.com/AlexFlipnote/CoffeeAPI").addConverterFactory(GsonConverterFactory.create()).build();


        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);

    }


    private void obtenerDatos(int offset){
        MyApiService service = retrofit.create(MyApiService.class);
        Call<CoffeRespuesta> coffeRespuestaCall = service.obtenerListaCoffe(20, 40);


        coffeRespuestaCall.enqueue(new Callback<CoffeRespuesta>() {
            @Override
            public void onResponse(Call<CoffeRespuesta> call, Response<CoffeRespuesta> response) {
                aptoParaCargar = true;

                if (response.isSuccessful()){

                    CoffeRespuesta coffeRespuesta = response.body();
                    ArrayList<Coffe> listaCoffe = coffeRespuesta.getResults();

                    listaCoffeAdapter.addicionarListaCoffe(listaCoffe);
                }else{
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }



            }

            @Override
            public void onFailure(Call<CoffeRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });






    }


}