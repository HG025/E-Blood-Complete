///*
// * Copyright (C) 2016 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.example.mt_2016.marathon_blood.Adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.mt_2016.marathon_blood.R;
//
///**
// * {@link ForecastAdapter} exposes a list of weather forecasts to a
// * {@link android.support.v7.widget.RecyclerView}
// */
//public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
//
//    private String[] mWeatherData;
//final private  ForecastAdapterOnClickHandler mClickHandler;
//
//    public interface ForecastAdapterOnClickHandler{
//        void onclick(String s);
//    }
//
//    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler ) {
//        mClickHandler = clickHandler;
//
//    }
//
//
//    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final TextView mWeatherTextView;
//
//        public ForecastAdapterViewHolder(View view) {
//            super(view);
//            mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
//            view.setOnClickListener(this);
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            int adapter_position = getAdapterPosition();
//            String weather_od_day = mWeatherData[adapter_position];
//            mClickHandler.onclick(weather_od_day);
//
//        }
//
//
//    }
//
//    @Override
//    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.forecast_list_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToParentImmediately = false;
//
//        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
//        return new ForecastAdapterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
//        String weatherForThisDay = mWeatherData[position];
//        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (null == mWeatherData) return 0;
//        return mWeatherData.length;
//    }
//
//    /**
//     * This method is used to set the weather forecast on a ForecastAdapter if we've already
//     * created one. This is handy when we get new data from the web but don't want to create a
//     * new ForecastAdapter to display it.
//     *
//     * @param weatherData The new weather data to be displayed.
//     */
//    public void setWeatherData(String[] weatherData) {
//        mWeatherData = weatherData;
//        notifyDataSetChanged();
//    }
//}