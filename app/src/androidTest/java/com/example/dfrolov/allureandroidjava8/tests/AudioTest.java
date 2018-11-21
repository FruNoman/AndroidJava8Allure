package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.SystemClock;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

@Epic("Audio features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
public class AudioTest extends BaseTest {
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

    @DisplayName("Test video mp3A000001")
    @Test
    public void testMp3A000001() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+mp3A000001);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video flacA000484")
    @Test
    public void testFlacA000484() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+flacA000484);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video flacA000484")
    @Test
    public void testWavA000354() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+wavA000354);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video aacA000157")
    @Test
    public void testAacA000157() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+aacA000157);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video sample3gpA000123")
    @Test
    public void testSample3gpA000123() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+sample3gpA000123);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video oggA000480")
    @Test
    public void testOggA000480() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+oggA000480);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video mkaA000485")
    @Test
    public void testMkaA000485() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+mkaA000485);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video mp4A000454")
    @Test
    public void testMp4A000454() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+mp4A000454);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video amrA000443")
    @Test
    public void testAmrA000443() throws IOException, InterruptedException {
        File video = new File(AUDIO_FOLDER_PATH+amrA000443);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Audio "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @After
    public void afterVideoTest() {
        mediaPlayer.stop();
    }
}
