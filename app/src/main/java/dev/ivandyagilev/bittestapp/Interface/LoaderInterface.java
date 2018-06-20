package dev.ivandyagilev.bittestapp.Interface;

import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface LoaderInterface {


    @GET("mobileAPI.php?action=getUsers")
    Single<SearchResults> getUsers();

    @GET("mobileAPI.php?action=getCars")
    Single<SearchResultsForCar> getCars();

}
