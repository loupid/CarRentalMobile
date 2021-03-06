package com.example.carrentalmobile.recyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.example.carrentalmobile.Database.Api;
import com.example.carrentalmobile.Database.RetroFitInstance;
import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    List<AnnoucedCars> annoucedCarsList;
    CarCallback callback;

    public AdapterList(List<AnnoucedCars> annoucedCarsList, CarCallback callback) {
        this.annoucedCarsList = annoucedCarsList;
        this.callback = callback;
    }

    public AdapterList(List<AnnoucedCars> annoucedCarsList, CarCallback callback, Context context) {
        this.annoucedCarsList = annoucedCarsList;
        this.callback = callback;
    }

    public void setAnnoucedCarsList(List<AnnoucedCars> annoucedCarsList) {
        this.annoucedCarsList = annoucedCarsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflater.inflate(R.layout.car_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.carName.setText(annoucedCarsList.get(position).getCarname());
        holder.title.setText(annoucedCarsList.get(position).getTitle());
        holder.numberPlace.setText(annoucedCarsList.get(position).getSeatcount());
        //todo: check category
//        holder.category.setText(annoucedCarsList.get(position).getCategory());
        holder.pricePerDay.setText(annoucedCarsList.get(position).getPrice());
        holder.location.setText(annoucedCarsList.get(position).getLocation());
        holder.brand.setText(annoucedCarsList.get(position).getBrandname());
        Api server = RetroFitInstance.getInstance().create(Api.class);

        Call<ResponseBody> call = server.download(annoucedCarsList.get(position).getFilepath());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        holder.imageViewCar.setImageBitmap(bmp);
                    } else {
                        holder.imageViewCar.setImageResource(R.drawable.default_car);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                holder.imageViewCar.setImageResource(R.drawable.default_car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return annoucedCarsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carName, title, numberPlace, pricePerDay, location, brand, description, category;
        ImageView imageViewCar, imgContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.tvCarName);
            category = itemView.findViewById(R.id.tvCategory);
            title = itemView.findViewById(R.id.tvTitle);
            numberPlace = itemView.findViewById(R.id.tvSeatCount);
            pricePerDay = itemView.findViewById(R.id.tvPrice);
            location = itemView.findViewById(R.id.tvTown);
            brand = itemView.findViewById(R.id.tvBrand);
            description = itemView.findViewById(R.id.tvDescription);
            imageViewCar = itemView.findViewById(R.id.list_car_image);
            imgContainer = itemView.findViewById(R.id.ivCarInfos);

            itemView.setOnClickListener(v -> callback.onCarItemClick(getAdapterPosition(), imgContainer, imageViewCar, title, brand, carName, pricePerDay, numberPlace, location, description));

            itemView.setOnLongClickListener(v ->
            {
                callback.onCarLongPressClick(getAdapterPosition());
                return false;
            });
        }
    }
}