package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.test.rule.GrantPermissionRule;
import android.util.Log;
import android.view.SurfaceView;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.R;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions.SkipException;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.wrappers.MediaPlayerAdvanced;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Epic("Video stress features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideoStressTest extends BaseTest {
    protected MainActivity activity;
    protected SurfaceView surfaceView;
    protected String VIDEO_FOLDER_PATH = "/sdcard/samples/video/";
    protected MediaPlayerAdvanced mediaPlayerWrapper;
    private MediaPlayer mediaPlayer;
    protected int NUMBER_OF_PLAYER_RANDOM_ACTIONS = 100;

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
    public void beforeVideoTests() throws SkipException {
        activity = (MainActivity) activityActivityTestRle.getActivity();
        surfaceView = (SurfaceView) activity.findViewById(R.id.surfaceView);
        mediaPlayer = new MediaPlayer();
        mediaPlayerWrapper = new MediaPlayerAdvanced();
    }

    @DisplayName("Stress Test video h263AV001248")
    @Test
    public void testStressH263AV001248() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h263AV001248);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }

    }

    @DisplayName("Stress Test video h264AV000869")
    @Test
    public void testStressH264AV000869() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h264AV000869);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test video h264AV001097")
    @Test
    public void testStressH264AV001097() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h264AV001097);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test video h264AV001187")
    @Test
    public void testStressH264AV001187() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h264AV001187);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }


    @DisplayName("Stress Test video h265AV001429")
    @Test
    public void testStressH265AV001429() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h265AV001429);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }


    @DisplayName("Stress Test video H263AV001248")
    @Test
    public void testStressH265AV001430() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h265AV001430);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test video h265AV001431")
    @Test
    public void testStressH265AV001431() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + h265AV001431);

        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test video mpeg4AV000022")
    @Test
    public void testStressMPEG4AV000022() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + mpeg4AV000022);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test video webmAV000962")
    @Test
    public void testStressWebmAV000962() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH + webmAV000962);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @After
    public void afterVideoTest() {
        mediaPlayer.stop();
    }
}
