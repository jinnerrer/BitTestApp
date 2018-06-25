package dev.ivandyagilev.bittestapp.LoadManager;


import dev.ivandyagilev.bittestapp.Interface.LoaderInterface;
import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadManager {

    private LoaderInterface loadFromApi;
    private static LoadManager mManager;

    private LoadManager(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://78.47.155.201:81/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        loadFromApi = retrofit.create(LoaderInterface.class);

    }

    public static LoadManager get() {
        if (mManager == null) {
            mManager = new LoadManager();
        }
        return mManager;
    }


    public Observable<SearchResults> getUsers() {
        return loadFromApi.getUsers();
    }

    public Observable<SearchResultsForCar> getCars() {
        return loadFromApi.getCars();
    }
}
