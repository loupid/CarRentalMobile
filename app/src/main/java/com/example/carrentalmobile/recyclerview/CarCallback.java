package com.example.carrentalmobile.recyclerview;

import android.widget.ImageView;
import android.widget.TextView;

public interface CarCallback {

    void onCarItemClick(int pos,
                        ImageView imgContainer,
                        ImageView imgCar,
                        TextView title,
                        TextView brand,
                        TextView name,
                        TextView price,
                        TextView seatCount,
                        TextView town,
                        TextView description);

    void onCarLongPressClick(int pos);
}
