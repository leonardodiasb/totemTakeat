package com.totemtakeat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.view.View;
import android.os.Handler;

public class ImmersiveMode extends ReactContextBaseJavaModule {
    Handler uiHandler;

    @Override
    public String getName() {
      return "ImmersiveMode";
    }

    public ImmersiveMode(ReactApplicationContext reactContext) {
      super(reactContext);
      this.uiHandler = new Handler(reactContext.getMainLooper());
    }

    @ReactMethod
    public void enterLeanBackMode() {
      uiHandler.post(runnableEnterLeanBackMode);
    }

    @ReactMethod
    public void enterImmersiveMode() {
      uiHandler.post(runnableEnterImmersiveMode);
    }

    @ReactMethod
    public void enterStickyImmersiveMode() {
      uiHandler.post(runnableEnterStickyImmersiveMode);
    }

    @ReactMethod
    public void exitImmersiveMode() {
      uiHandler.post(runnableExitImmersiveMode);
    }

    private Runnable runnableEnterLeanBackMode = new Runnable(){
      @Override
      public void run() {
        setSystemUIFlags(
          View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
      }
    };

    private Runnable runnableEnterImmersiveMode = new Runnable(){
      @Override
      public void run() {
        setSystemUIFlags(
          View.SYSTEM_UI_FLAG_IMMERSIVE
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
      }
    };

    private Runnable runnableEnterStickyImmersiveMode = new Runnable(){
      @Override
      public void run() {
        setSystemUIFlags(
          View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
      }
    };

    private Runnable runnableExitImmersiveMode = new Runnable(){
      @Override
      public void run() {
        setSystemUIFlags(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
      }
    };

    private void setSystemUIFlags(int visibility) {
      View decorView = getCurrentActivity().getWindow().getDecorView();
      decorView.setSystemUiVisibility(visibility);
    }
}