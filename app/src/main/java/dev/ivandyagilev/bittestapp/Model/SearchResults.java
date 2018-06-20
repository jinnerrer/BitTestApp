package dev.ivandyagilev.bittestapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {

    @SerializedName("data")
    public List<User> mUserList;

}
