package dev.ivandyagilev.bittestapp.Presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import dev.ivandyagilev.bittestapp.Adapter.MyAdapter;
import dev.ivandyagilev.bittestapp.Interface.ListFragmentInterface;
import dev.ivandyagilev.bittestapp.Interface.LoaderInterface;
import dev.ivandyagilev.bittestapp.Model.ResultsList;
import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListPresenter extends BasePresenter<ListFragmentInterface>{

    private LoaderInterface loadFromApi;
    private RecyclerView recyclerView;

    private ResultsList resultHolder = ResultsList.getInstance();
    private MyAdapter adapter = new MyAdapter();

    public ListPresenter(RecyclerView recyclerView){
        this.recyclerView = recyclerView;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://78.47.155.201:81/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        loadFromApi = retrofit.create(LoaderInterface.class);

    }


    @Override
    public void attachView(ListFragmentInterface mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadItems() {

        recyclerView.setAdapter(adapter);
        getMvpView().startLoading();


        loadFromApi.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<SearchResults>() {

                    @Override
                    public void onSuccess(@NonNull SearchResults users) {

                        resultHolder.setUserList(users.mUserList);

                        loadFromApi.getCars()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableSingleObserver<SearchResultsForCar>() {

                                    @Override
                                    public void onSuccess(@NonNull SearchResultsForCar cars) {
                                        resultHolder.setCarList(cars.mCarList);

                                        adapter.notifyDataSetChanged();
//                                        getMvpView().stopLoading();

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        System.out.printf("API ERROR : " + e.getMessage());
                                    }

                                });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.printf("API ERROR : " + e.getMessage());
                    }

                });

    }

}
