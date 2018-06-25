package dev.ivandyagilev.bittestapp.Presenter;

import android.support.v7.widget.RecyclerView;

import java.util.Collections;

import dev.ivandyagilev.bittestapp.Adapter.MyAdapter;
import dev.ivandyagilev.bittestapp.Interface.ListFragmentInterface;
import dev.ivandyagilev.bittestapp.LoadManager.LoadManager;
import dev.ivandyagilev.bittestapp.Model.ResultsList;
import dev.ivandyagilev.bittestapp.Model.SearchResults;
import dev.ivandyagilev.bittestapp.Model.SearchResultsForCar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListPresenter extends BasePresenter<ListFragmentInterface>{

    private ResultsList resultHolder = ResultsList.getInstance();
    private MyAdapter adapter = new MyAdapter();

    public ListPresenter(RecyclerView recyclerView){

        recyclerView.setAdapter(adapter);

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

        getMvpView().startLoading();

        LoadManager.get().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SearchResults>() {
                    @Override public void onComplete() {

                    }

                    @Override
                    public void onNext(SearchResults searchResults) {
                        if (searchResults.mUserList == null) {
                            resultHolder.setUserList(Collections.emptyList());
                        } else {
                            resultHolder.setUserList(searchResults.mUserList);

                            LoadManager.get().getCars()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableObserver<SearchResultsForCar>() {
                                                   @Override
                                                   public void onNext(SearchResultsForCar searchResults) {

                                                       if (searchResults.mCarList == null)
                                                       {
                                                           resultHolder.setCarList(Collections.emptyList());
                                                       } else {
                                                           resultHolder.setCarList(searchResults.mCarList);
                                                           adapter.notifyDataSetChanged();
                                                           getMvpView().stopLoading();
                                                       }
                                                   }

                                                   @Override
                                                   public void onError(Throwable e) {

                                                   }

                                                   @Override
                                                   public void onComplete() {

                                                   }
                                               });

                        }

                    }

                    @Override public void onError(Throwable t) {

                    }
                });

    }

}
