package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.GrantPermissionRule;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;


import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.R;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions.SkipExeption;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.utils.Samples;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;


@Epic("Media core features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
public class MediaCoreTest extends BaseTest {
    private MainActivity activity;
    private BitmapFactory.Options options;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private SurfaceView surfaceView;
    private String IMAGE_FOLDER_PATH = "/sdcard/samples/image/";
    private String VIDEO_FOLDER_PATH = "/sdcard/samples/video/";
    private String AUDIO_FOLDER_PATH = "/sdcard/samples/audio/";

    public void testImage(File image) throws FileNotFoundException {
        TestUtils.setTitle(activity, image.getName());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(image), null, options);
        int beforeH=imageView.getMeasuredHeight();
        int beforeW = imageView.getMeasuredWidth();
        activity.setImageView(bitmap);
        SystemClock.sleep(5000);
        int afterH=imageView.getMeasuredHeight();
        int afterW = imageView.getMeasuredWidth();
        assertNotSame("imageView Height not changed after set src",beforeH, afterH);
        assertNotSame("imageView Width not changed after set src",beforeW, afterW);
        assertTrue("imageView size incorrect",(afterH>100 && afterW>100));
    }

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
    public void beforeMediaTests() throws SkipExeption {
        activity = (MainActivity) activityActivityTestRle.getActivity();
        imageView = (ImageView) activity.findViewById(R.id.imageView);
        surfaceView = (SurfaceView) activity.findViewById(R.id.surfaceView);
        options = new BitmapFactory.Options();

        File imageFolder = new File(IMAGE_FOLDER_PATH);
        File videoFolder = new File(VIDEO_FOLDER_PATH);
        File audioFolder = new File(AUDIO_FOLDER_PATH);

        if(!imageFolder.exists() || !videoFolder.exists() || !audioFolder.exists()){
            throw new SkipExeption("Can't find samples folders");
        }

    }

    @DisplayName("Image format .jpg I000287 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpegI000287() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000287.toString()));
    }

    @DisplayName("Image format .jpg I000309 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000309() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000309.toString()));
    }

    @DisplayName("Image format .jpg I000311 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000311() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000311.toString()));
    }

    @DisplayName("Image format .jpg I000001 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000001() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000001.toString()));
    }

    @DisplayName("Image format .webp I000308 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testWebpI000308() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.webpI000308.toString()));
    }

    @DisplayName("Image format .gif I000021 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testGifI000021() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.gifI000021.toString()));
    }

    @DisplayName("Image format .bmp I000312 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testBmpI000312() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.bmpI000312.toString()));
    }

    @DisplayName("Image format .png I000301 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI0003012() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000301.toString()));
    }

    @DisplayName("Image format .png I000313 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI000313() throws FileNotFoundException {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000313.toString()));
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
