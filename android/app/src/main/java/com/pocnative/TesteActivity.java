package com.pocnative;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class TesteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    public void startCamera() {

            PreviewView previewView = findViewById(R.id.previewView);

            ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

            cameraProviderFuture.addListener(()->{
                ProcessCameraProvider cameraProvider = null;
                try {
                    cameraProvider = cameraProviderFuture.get();
                } catch (ExecutionException e) {
                    Log.e("TAG", e.getMessage());
                } catch (InterruptedException e) {
                    Log.e("TAG", e.getMessage());
                }

                try {
                    CameraSelector cameraSelector =  CameraSelector.DEFAULT_FRONT_CAMERA;

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.createSurfaceProvider());


                    // Select back camera as a default

                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll();

                        // Bind use cases to camera
                        Camera camera = cameraProvider.bindToLifecycle(
                                this, cameraSelector, preview);

                    } catch(Exception exc) {
                        Log.e("TAG", "Use case binding failed", exc);
                    }
                }
                catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }

            },  ContextCompat.getMainExecutor(this));
    }
}
