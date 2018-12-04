package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.media.MediaPlayer;
import android.support.test.rule.GrantPermissionRule;
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

@Epic("Audio stress features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AudioStressTest extends BaseTest {
    protected MainActivity activity;
    protected SurfaceView surfaceView;
    protected String AUDIO_FOLDER_PATH = "/sdcard/samples/audio/";
    protected MediaPlayerAdvanced mediaPlayerWrapper;
    private MediaPlayer mediaPlayer;
    protected int NUMBER_OF_PLAYER_RANDOM_ACTIONS = 500;

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

    @DisplayName("Stress Test audio Mp3A000001")
    @Test
    public void testStressMp3A000001() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + mp3A000001);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }


    @DisplayName("Stress Test audio flacA000484")
    @Test
    public void testStressflacA000484() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + flacA000484);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio wavA000354")
    @Test
    public void testStressWavA000354() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + wavA000354);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio aacA000157")
    @Test
    public void testStressAacA000157() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + aacA000157);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio sample3gpA000123")
    @Test
    public void testStressSample3gpA000123() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + sample3gpA000123);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio oggA000480")
    @Test
    public void testStressOggA000480() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + oggA000480);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio mkaA000485")
    @Test
    public void testStressMkaA000485() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + mkaA000485);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio mp4A000454")
    @Test
    public void testStressMp4A000454() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + mp4A000454);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer, video, surfaceView);
        for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++) {
            mediaPlayerWrapper.randomAction(mediaPlayer);
            Assert.assertNotNull("Media player is dying", mediaPlayer);
            Assert.assertTrue("Activity is dying", activity.activityVisible);
        }
    }

    @DisplayName("Stress Test audio amrA000443")
    @Test
    public void testStressAmrA000443() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH + amrA000443);
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
