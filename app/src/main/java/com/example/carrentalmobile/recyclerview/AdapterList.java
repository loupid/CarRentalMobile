package com.example.carrentalmobile.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.carrentalmobile.Model.AnnoucedCars;
import com.example.carrentalmobile.R;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MyViewHolder> {

    List<AnnoucedCars> annoucedCarsList;


    public AdapterList(List<AnnoucedCars> annoucedCarsList) {
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
        holder.pricePerDay.setText(annoucedCarsList.get(position).getTypename());
        holder.location.setText(annoucedCarsList.get(position).getUsername());
        holder.brand.setText(annoucedCarsList.get(position).getBrandname());
//        holder.imageViewCar.setImageDrawable(annoucedCarsList.get(position).getFilepath());
    }

    @Override
    public int getItemCount() {
        return annoucedCarsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carName, title, numberPlace, pricePerDay, location, brand;
        ImageView imageViewCar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.tvCarName);
            title = itemView.findViewById(R.id.tvTitle);
            numberPlace = itemView.findViewById(R.id.tvSeatCount);
            pricePerDay = itemView.findViewById(R.id.tvPrice);
            location = itemView.findViewById(R.id.tvTown);
            brand = itemView.findViewById(R.id.tvBrand);
            imageViewCar = itemView.findViewById(R.id.car_image);
        }
    }
}