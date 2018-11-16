package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.test.rule.GrantPermissionRule;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.R;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions.SkipException;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.wrappers.MediaPlayerAdvanced;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;


@Epic("Video features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideoTest extends BaseTest {
    protected MainActivity activity;
    protected BitmapFactory.Options options;
    protected ImageView imageView;
    protected SurfaceView surfaceView;
    protected String IMAGE_FOLDER_PATH = "/sdcard/samples/image/";
    protected String VIDEO_FOLDER_PATH = "/sdcard/samples/video/";
    protected String AUDIO_FOLDER_PATH = "/sdcard/samples/audio/";
    protected MediaPlayerAdvanced mediaPlayerWrapper;
    private MediaPlayer mediaPlayer;


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
        imageView = (ImageView) activity.findViewById(R.id.imageView);
        surfaceView = (SurfaceView) activity.findViewById(R.id.surfaceView);
        options = new BitmapFactory.Options();

        File imageFolder = new File(IMAGE_FOLDER_PATH);
        File videoFolder = new File(VIDEO_FOLDER_PATH);
        File audioFolder = new File(AUDIO_FOLDER_PATH);

        mediaPlayerWrapper = new MediaPlayerAdvanced();
        mediaPlayer = new MediaPlayer();
    }


    @DisplayName("Test video h263AV001248")
    @Test
    public void testH263AV001248() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h263AV001248);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);

    }

    @DisplayName("Test video h264AV000869")
    @Test
    public void testH264AV000869() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h264AV000869);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video h264AV001097")
    @Test
    public void testH264AV001097() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h264AV001097);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video h264AV001187")
    @Test
    public void testH264AV001187() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h264AV001187);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }


    @DisplayName("Test video h265AV001429")
    @Test
    public void testH265AV001429() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h265AV001429);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }


    @DisplayName("Test video H263AV001248")
    @Test
    public void testH265AV001430() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h265AV001430);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video h265AV001431")
    @Test
    public void testH265AV001431() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+h265AV001431);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video mpeg4AV000022")
    @Test
    public void testMPEG4AV000022() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+mpeg4AV000022);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

    @DisplayName("Test video webmAV000962")
    @Test
    public void testWebmAV000962() throws IOException, InterruptedException {
        File video = new File(VIDEO_FOLDER_PATH+webmAV000962);
        mediaPlayer = mediaPlayerWrapper.playSample(mediaPlayer,video,surfaceView);
        int lastSeek = mediaPlayerWrapper.randomSeekTo(mediaPlayer);
        SystemClock.sleep(4000);
        Assert.assertTrue("Video "+video.getName()+" not playing",mediaPlayer.isPlaying());
        int currentSeek = mediaPlayer.getCurrentPosition();
        Assert.assertTrue("Current position "+currentSeek+" lower last seek "+lastSeek,currentSeek>=lastSeek*0.8);
    }

}
