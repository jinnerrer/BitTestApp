package dev.ivandyagilev.bittestapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultsForCar {

    @SerializedName("data")
    public List<Car> mCarList;

}
