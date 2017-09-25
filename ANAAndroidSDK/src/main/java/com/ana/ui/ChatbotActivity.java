package com.ana.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.util.List;

import com.ana.R;
import com.ana.datarepository.Injection;
import com.ana.models.Button;
import com.ana.utils.ActivityUtils;
import com.ana.utils.Constants;
import com.ana.utils.NetworkFactory;
import com.ana.viewmodels.ChatViewModel;
import com.ana.viewmodels.ViewModelHolder;


/**
 * Created by Admin on 29-06-2017.
 */

public class ChatbotActivity extends AppCompatActivity implements NetworkFactory.NetworkCallResponse {

    private ChatViewModel mChatViewModel;

    private ChatbotFragment mChatViewFragment;

    public static final String CHAT_VIEWMODEL_TAG = "CHAT_VIEWMODEL_TAG";

    public Handler mHandler;

    private static final int RC_CAMERA = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        setUpWindow();

        setupToolbar();


        mHandler = new Handler(Looper.getMainLooper());

        mChatViewFragment = findOrCreateViewFragment();

        mChatViewModel = findOrCreateViewModel();

        mChatViewFragment.setViewModel(mChatViewModel);

        listenKeyBoardCallBack();

        listenMediaButtonCallBack();

    }

    @Override
    public void onResponse(String response) {

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpWindow() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary));
        }
    }

    @NonNull
    private ChatbotFragment findOrCreateViewFragment() {
        ChatbotFragment tasksFragment =
                (ChatbotFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = ChatbotFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }
        return tasksFragment;
    }

    private ChatViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<ChatViewModel> retainedViewModel =
                (ViewModelHolder<ChatViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(CHAT_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            ChatViewModel viewModel = new ChatViewModel(
                    Injection.provideTasksRepository(getApplicationContext()),
                    getApplicationContext());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    CHAT_VIEWMODEL_TAG);
            return viewModel;
        }

    }

    private void listenKeyBoardCallBack() {

        Observable.OnPropertyChangedCallback mShowInputCallBack = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {

                final boolean showKeyboard = mChatViewModel.showKeyBoard.get();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (showKeyboard) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        } else {
                            if (getCurrentFocus() != null) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                }, 300);

            }
        };
        mChatViewModel.showKeyBoard.addOnPropertyChangedCallback(mShowInputCallBack);
    }

    private void listenMediaButtonCallBack() {

        Observable.OnPropertyChangedCallback mMediaChangedCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {

                final Button mButton = mChatViewModel.mMediaButton.get();

                if (mButton != null) {

                    switch (mButton.getButtonType()) {
                        case Constants.ButtonType.TYPE_GET_IMAGE:
                            showImagePicker();
                            break;
                    }
                }

            }
        };
        mChatViewModel.mMediaButton.addOnPropertyChangedCallback(mMediaChangedCallback);
    }

    private void showImagePicker() {
        new AlertDialog.Builder(ChatbotActivity.this)
                .setTitle(getString(R.string.media_picker_select_from))
                .setPositiveButton(getString(R.string.media_picker_camera), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final Activity activity = ChatbotActivity.this;
                        final String[] permissions = new String[]{Manifest.permission.CAMERA};
                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(activity, permissions, RC_CAMERA);
                        } else {
                            new CameraImagePicker(ChatbotActivity.this).pickImage();
                        }

                    }
                })
                .setNegativeButton(getString(R.string.media_picker_gallery), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new ImagePicker(ChatbotActivity.this).pickImage();

                    }
                })
                .setCancelable(false)
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_CAMERA) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new CameraImagePicker(ChatbotActivity.this).pickImage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Picker.PICK_IMAGE_CAMERA || requestCode == Picker.PICK_IMAGE_DEVICE) {

            if (data == null || data.getData() == null) {

            } else {
                List<ChosenImage> lsChosenImages = (List<ChosenImage>) data;
                mChatViewModel.replyToRia(Constants.SectionType.TYPE_IMAGE, lsChosenImages.get(0).getOriginalPath());

            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
