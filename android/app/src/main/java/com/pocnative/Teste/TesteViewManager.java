package com.pocnative.Teste;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.pocnative.R;

public class TesteViewManager extends SimpleViewManager<PreviewView>{
    @NonNull
    @Override
    public String getName() {
        return "RTCImageViewer";
    }

    @NonNull
    @Override
    protected PreviewView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new PreviewView(reactContext, null);
    }

}
