package com.example.apirestretrofit;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apirestretrofit.models.Coffe;

import java.util.ArrayList;

public class ListaCoffeAdapter extends RecyclerView.Adapter<ListaCoffeAdapter.ViewHolder> {



    private ArrayList<Coffe> dataset;
    private Context context;


    public ListaCoffeAdapter (Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Coffe c = dataset.get(position);
        holder.txtname.setText(c.getName());

        //Esta es la url de la imagen
        String imagen = "https://coffee.alexflipnote.dev/random";

        Glide.with(context)
                .load(imagen)
                .into(holder.foto);



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void addicionarListaCoffe(ArrayList<Coffe> listaCoffe){
        dataset.addAll(listaCoffe);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView foto;
        private TextView txtname;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            foto = (ImageView) itemView.findViewById(R.id.imageView);
            txtname = (TextView) itemView.findViewById(R.id.txtTitulo);

        }


    }

}
