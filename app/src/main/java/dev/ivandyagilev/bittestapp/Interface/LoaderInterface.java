package dev.ivandyagilev.bittestapp.Interface;

import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface LoaderInterface {

    @GET("mobileAPI.php?action=getUsers")
    Observable<SearchResults> getUsers();

    @GET("mobileAPI.php?action=getCars")
    Observable<SearchResultsForCar> getCars();

}
