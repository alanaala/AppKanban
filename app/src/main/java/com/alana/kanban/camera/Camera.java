package com.alana.kanban.camera;

import static android.provider.Settings.System.getString;

import android.telecom.InCallService;
import android.util.Log;

import com.alana.kanban.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class Camera {

    private InCallService.VideoCall preview;

    public static class Camera;


    public static Camera open(int cameraId) {
        return null;
    }

    private boolean safeCameraOpen(int id, boolean camera) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            camera = Camera.open(id);
            qOpened = (camera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        preview.setCamera(null);
        AtomicBoolean camera = new AtomicBoolean(false);
        if (camera.get() != null) {
            camera.release();
            camera.set(null);
        }
    }
}
