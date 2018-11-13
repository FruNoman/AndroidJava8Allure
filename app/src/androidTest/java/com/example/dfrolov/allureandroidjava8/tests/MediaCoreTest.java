package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.support.test.rule.GrantPermissionRule;
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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


@Epic("Media core features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
public class MediaCoreTest extends VideoTest {

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

        if (!imageFolder.exists() || !videoFolder.exists() || !audioFolder.exists()) {
            throw new SkipExeption("Can't find samples folders");
        }

    }

    @DisplayName("Image format .jpg I000287 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpegI000287() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000287.toString()));
    }

    @DisplayName("Image format .jpg I000309 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000309() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000309.toString()));
    }

    @DisplayName("Image format .jpg I000311 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000311() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000311.toString()));
    }

    @DisplayName("Image format .jpg I000001 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000001() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000001.toString()));
    }

    @DisplayName("Image format .webp I000308 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testWebpI000308() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.webpI000308.toString()));
    }

    @DisplayName("Image format .gif I000021 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testGifI000021() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.gifI000021.toString()));
    }

    @DisplayName("Image format .bmp I000312 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testBmpI000312() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.bmpI000312.toString()));
    }

    @DisplayName("Image format .png I000301 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI0003012() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000301.toString()));
    }

    @DisplayName("Image format .png I000313 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI000313() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000313.toString()));
    }

    @DisplayName("Video format h263AV001248 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH263AV001248() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h263AV001248.toString()));
    }

    @DisplayName("Video format h264AV000869 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV000869() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV000869.toString()));
    }

    @DisplayName("Video format h264AV001097 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV001097() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV001097.toString()));
    }

    @DisplayName("Video format h264AV001187 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV001187() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV001187.toString()));
    }


    @DisplayName("Video format h265AV001429 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001429() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001429.toString()));
    }

    @DisplayName("Video format h265AV001430 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001430() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001430.toString()));
    }

    @DisplayName("Video format h265AV001431 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001431() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001431.toString()));
    }

    @DisplayName("Video format mpeg4AV000022 test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testMPEG4AV000022() throws SkipExeption, IOException {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.mpeg4AV000022.toString()));
    }

    @DisplayName("Video format h265AV001429 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActionH263AV001248() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h263AV001248.toString());
    }

    @DisplayName("Video format h264AV000869 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH264AV000869() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h264AV000869.toString());
    }

    @DisplayName("Video format h264AV001097 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH264AV001097() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h264AV001097.toString());
    }

    @DisplayName("Video format h264AV001187 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH264AV001187() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h264AV001187.toString());
    }

    @DisplayName("Video format h265AV001429 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH265AV001429() throws Exception {

        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h265AV001429.toString());
    }

    @DisplayName("Video format h265AV001430 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH265AV001430() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h265AV001430.toString());
    }

    @DisplayName("Video format h265AV001431 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioH265AV001431() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.h265AV001431.toString());
    }

    @DisplayName("Video format mpeg4AV000022 random actions test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testRandomActioMPEG4AV000022() throws Exception {
        testPlayerRandomAction(VIDEO_FOLDER_PATH + Samples.mpeg4AV000022.toString());
    }


}
