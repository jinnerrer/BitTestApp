package dev.ivandyagilev.bittestapp.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickClick;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.ivandyagilev.bittestapp.Interface.ListFragmentInterface;
import dev.ivandyagilev.bittestapp.Presenter.ListPresenter;
import dev.ivandyagilev.bittestapp.R;

import static android.app.Activity.RESULT_OK;


public class ListFragment extends Fragment implements ListFragmentInterface{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @OnClick(R.id.imageAddBtn)
    public void onClick() {
        addImage();
    }

    @BindView(R.id.imageView)
    ImageView mImageView;

    private ListPresenter presenter;

    private static final int GALLERY_REQUEST = 3119;
    private static final int CAMERA_REQUEST = 3121;

    private Uri imageUri;
    private PickImageDialog dialog;

    public ListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        presenter = new ListPresenter(mRecyclerView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!presenter.isViewAttached()){
            presenter.attachView(this);
            presenter.loadItems();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter.isViewAttached()){
            presenter.detachView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void startLoading() {
        //При больших коллекциях показать загрузку
    }

    @Override
    public void stopLoading() {
        Toast.makeText(getContext(), R.string.loading_compleate, Toast.LENGTH_SHORT).show();
    }

    private void addImage() {
        PickSetup setup = new PickSetup()
                .setTitle(getString(R.string.add_image))
                .setCancelText(getString(R.string.dialog_cancel))
                .setFlip(true)
                .setMaxSize(500)
                .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                .setCameraButtonText(getString(R.string.camera))
                .setGalleryButtonText(getString(R.string.gallery))
                .setButtonOrientation(LinearLayoutCompat.HORIZONTAL)
                .setSystemDialog(false);

        dialog = PickImageDialog.build(setup)
                .setOnClick(new IPickClick() {
                    @Override
                    public void onGalleryClick() {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , GALLERY_REQUEST);
                    }

                    @Override
                    public void onCameraClick() {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        File photo = new File(Environment.getExternalStorageDirectory(), "image_temp_" + new Date());
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photo));
                        imageUri = Uri.fromFile(photo);
                        startActivityForResult(intent, CAMERA_REQUEST);
                    }
                });

        dialog.show(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    initViews(1);
                    Uri mImageUri = data.getData();
                    Glide.with(this).load(mImageUri).into(mImageView);
                } else {
                    initViews(2);
                }
                closeDialog();
            break;
            case CAMERA_REQUEST:
                if(resultCode == RESULT_OK){
                    initViews(1);
                    Glide.with(this).load(imageUri).into(mImageView);
                    imageUri = null;
                } else {
                    initViews(2);
                }
                closeDialog();
                break;
        }

    }

    private void closeDialog() {
        dialog.dismiss();
    }


    private void initViews(int state){
        switch (state){
            case 1:
                mImageView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mImageView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

}
