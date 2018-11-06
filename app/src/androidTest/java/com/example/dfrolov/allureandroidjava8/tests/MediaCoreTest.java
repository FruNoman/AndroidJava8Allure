package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.GrantPermissionRule;
import android.view.SurfaceView;
import android.widget.ImageView;


import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.R;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;



@Epic("Media core features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
public class MediaCoreTest extends BaseTest {
    private MainActivity activity;
    private BitmapFactory.Options options;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private SurfaceView surfaceView;
    private String IMAGE_FOLDER_PATH = "/sdcard/samples/image";
    private String VIDEO_FOLDER_PATH = "/sdcard/samples/video";

    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.WAKE_LOCK);

    @Before
    public void setup() {
        activity = (MainActivity) activityActivityTestRle.getActivity();
        imageView = (ImageView) activity.findViewById(R.id.imageView);
        surfaceView = (SurfaceView) activity.findViewById(R.id.surfaceView);
        options = new BitmapFactory.Options();
    }

    @DisplayName("Image formats displaying test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void imageFormatsTest() throws Throwable {
        File imageFolder = new File(IMAGE_FOLDER_PATH);
        for (File image : imageFolder.listFiles()) {
            String path = image.getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            activity.setImageView(bitmap);
            Thread.sleep(1000);
            Assert.assertTrue(TestUtils.imageDisplayed(imageView));
        }
    }

    @DisplayName("Video formats displaying tests")
    @Severity(SeverityLevel.BLOCKER)
    @UiThreadTest
    @Test
    public void videoFormatsTest() throws IOException, InterruptedException {
        mediaPlayer = new MediaPlayer();
        for (File video : new File(VIDEO_FOLDER_PATH).listFiles()) {
            String path = video.getAbsolutePath();
            activity.getSurfaceView();
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.setDataSource(path);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Thread.sleep(5000);
            Assert.assertTrue(mediaPlayer.isPlaying());
            mediaPlayer.reset();
        }
        mediaPlayer.stop();
    }

    @After
    public void afterTest() {

    }
}
