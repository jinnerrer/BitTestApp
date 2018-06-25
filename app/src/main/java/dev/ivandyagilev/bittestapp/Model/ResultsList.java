package dev.ivandyagilev.bittestapp.Model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class ResultsList {

    @SerializedName("data")
    private List<User> userList = new ArrayList<>();

    @SerializedName("data")
    private List<Car> carList = new ArrayList<>();

    private final static ResultsList ourInstance = new ResultsList();
    public static ResultsList getInstance() {
        return ourInstance;
    }


    private ResultsList() {

    }


    public void clearResults(){
        userList.clear();
        carList.clear();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

}
