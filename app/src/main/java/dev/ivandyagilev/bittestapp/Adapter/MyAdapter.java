package dev.ivandyagilev.bittestapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ivandyagilev.bittestapp.Model.User;
import dev.ivandyagilev.bittestapp.Model.ResultsList;
import dev.ivandyagilev.bittestapp.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private ResultsList resultHolder = ResultsList.getInstance();

    public MyAdapter(){
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = resultHolder.getUserList().get(position);

        holder.setUserName(user.getName());
        holder.setUerCarModel(user.getCarId());

    }

    @Override
    public int getItemCount() {
        return resultHolder.getUserList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        @BindView(R.id.userNameTxt)
        TextView mUserNameTxt;

        @BindView(R.id.carModelTxt)
        TextView mCarModelTxt;


        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void setUserName(String userName){
            mUserNameTxt.setText(userName);
        }

        private void setUerCarModel(int userCarId){

            for (int i = 0; i <resultHolder.getCarList().size(); i++){
                if (resultHolder.getCarList().get(i).getId()==userCarId){
                    mCarModelTxt.setText(resultHolder.getCarList().get(i).getName());
                }
            }
        }

    }



}
