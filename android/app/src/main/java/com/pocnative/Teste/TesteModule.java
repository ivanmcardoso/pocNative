package com.pocnative.Teste;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.google.common.util.concurrent.ListenableFuture;
import com.pocnative.MainActivity2;
import com.pocnative.R;
import com.pocnative.TesteActivity;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

public class TesteModule extends ReactContextBaseJavaModule {

    private LifecycleRegistry lifecycleRegistry;

    public TesteModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "TesteModule";
    }

    @ReactMethod
    public void abreAi(){
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, TesteActivity.class);
        activity.startActivity(intent);
    }

    @ReactMethod
    public void startCamera(final int viewTag, final Promise promise) {
        final ReactApplicationContext context = getReactApplicationContext();
        UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);
        AppCompatActivity activity = (AppCompatActivity) getCurrentActivity();

        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            PreviewView previewView = (PreviewView) nativeViewHierarchyManager.resolveView(viewTag);

            ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(activity);

            cameraProviderFuture.addListener(()->{
                ProcessCameraProvider cameraProvider = null;
                try {
                    cameraProvider = cameraProviderFuture.get();
                } catch (ExecutionException e) {
                    promise.reject("Fail","fail camera provider execution");
                } catch (InterruptedException e) {
                    promise.reject("Fail","fail camera provider interrupted");
                }

                try {
                    CameraSelector cameraSelector =  new CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build();

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.createSurfaceProvider());


                    // Select back camera as a default

                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll();

                        // Bind use cases to camera
                        Camera camera = cameraProvider.bindToLifecycle(
                                activity, cameraSelector, preview);
                        promise.resolve(camera.toString());

                    } catch(Exception exc) {
                        Log.e("TAG", "Use case binding failed", exc);
                        promise.reject("E_TAKE_PICTURE_FAILED", "Use case binding failed");
                    }
                }
                catch (Exception e) {
                    promise.reject("E_TAKE_PICTURE_FAILED", e.getMessage());
                }

            },  ContextCompat.getMainExecutor(context));

            });
    }

}
