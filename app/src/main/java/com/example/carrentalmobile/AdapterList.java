package com.example.carrentalmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.carrentalmobile.Model.AnnoucedCars;

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
        holder.description.setText(annoucedCarsList.get(position).getDescription());
        holder.numberPlace.setText(annoucedCarsList.get(position).getSeatcount());
        holder.pricePerDay.setText(annoucedCarsList.get(position).getTypename());
        holder.location.setText(annoucedCarsList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return annoucedCarsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carName, description, numberPlace, pricePerDay, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.CarName);
            description = itemView.findViewById(R.id.Description);
            numberPlace = itemView.findViewById(R.id.NumberPlace);
            pricePerDay = itemView.findViewById(R.id.PricePerDay);
            location = itemView.findViewById(R.id.Location);
        }
    }
}