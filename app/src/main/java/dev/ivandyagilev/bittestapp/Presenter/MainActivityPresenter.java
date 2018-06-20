package dev.ivandyagilev.bittestapp.Presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import dev.ivandyagilev.bittestapp.Adapter.MyAdapter;
import dev.ivandyagilev.bittestapp.Interface.LoaderInterface;
import dev.ivandyagilev.bittestapp.Interface.MainActivityInterface;
import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import dev.ivandyagilev.bittestapp.Model.ResultsList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPresenter extends BasePresenter<MainActivityInterface>{

    public MainActivityPresenter(){

    }

}
