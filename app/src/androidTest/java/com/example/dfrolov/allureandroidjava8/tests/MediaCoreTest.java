package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Looper;
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

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;


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
    private int WAIT_FOR_COMMAND_TO_COMPLETE = 60000;  //1 min max.
    private boolean mPrepareReset = false;
    private Looper mLooper = null;
    private final Object mLock = new Object();
    private final Object mPrepareDone = new Object();
    private final Object mOnCompletion = new Object();
    private final long PAUSE_WAIT_TIME = 2000;
    private final long WAIT_TIME = 2000;
    private final int SEEK_TIME = 10000;
    private boolean mInitialized = false;

    private boolean mOnPrepareSuccess = false;
    public boolean mOnCompleteSuccess = false;
    public boolean mPlaybackError = false;
    public int mMediaInfoUnknownCount = 0;
    public int mMediaInfoVideoTrackLaggingCount = 0;
    public int mMediaInfoBadInterleavingCount = 0;
    public int mMediaInfoNotSeekableCount = 0;
    public int mMediaInfoMetdataUpdateCount = 0;


    public void testImage(File image) throws FileNotFoundException, SkipExeption {
        if (!image.exists()) {
            throw new SkipExeption("Can't find sample " + image.getPath());
        }
        TestUtils.setTitle(activity, image.getName());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(image), null, options);
        int beforeH = imageView.getMeasuredHeight();
        int beforeW = imageView.getMeasuredWidth();
        activity.setImageView(bitmap);
        SystemClock.sleep(5000);
        int afterH = imageView.getMeasuredHeight();
        int afterW = imageView.getMeasuredWidth();
        Assert.assertNotSame("imageView Height not changed after set src", beforeH, afterH);
        Assert.assertNotSame("imageView Width not changed after set src", beforeW, afterW);
        Assert.assertTrue("imageView size incorrect", (afterH > 100 && afterW > 100));
    }

    private void initializeMessageLooper() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mLooper = Looper.myLooper();
                mediaPlayer = new MediaPlayer();
                synchronized (mLock) {
                    mInitialized = true;
                    mLock.notify();
                }
                Looper.loop();
            }
        }.start();
    }

    private void terminateMessageLooper() {
        mLooper.quit();
        mediaPlayer.release();
    }

    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            synchronized (mPrepareDone) {
                if (mPrepareReset) {
                    mediaPlayer.reset();
                }
                mPrepareDone.notify();
                mOnPrepareSuccess = true;
            }
        }
    };
    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            synchronized (mOnCompletion) {
                mOnCompletion.notify();
                mOnCompleteSuccess = true;
            }
        }
    };

    MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            mPlaybackError = true;
            mp.reset();
            synchronized (mOnCompletion) {
                mOnCompletion.notify();
                mOnCompleteSuccess = false;
            }
            Assert.assertTrue(false);
            return true;
        }
    };

    MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_UNKNOWN:
                    mMediaInfoUnknownCount++;
                    break;
                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    mMediaInfoVideoTrackLaggingCount++;
                    Assert.assertTrue(false);

                    break;
                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    mMediaInfoBadInterleavingCount++;
                    Assert.assertTrue(false);

                    break;
                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    mMediaInfoNotSeekableCount++;
                    Assert.assertTrue(false);
                    break;
                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    mMediaInfoMetdataUpdateCount++;
                    break;
            }
            return true;
        }
    };

    public void testMedia(File video) throws SkipExeption {
        if (!video.exists()) {
            throw new SkipExeption("Can't find sample " + video.getPath());
        }
        int duration = 0;
        int waittime = 0;
        mOnCompleteSuccess = false;
        mMediaInfoUnknownCount = 0;
        mMediaInfoVideoTrackLaggingCount = 0;
        mMediaInfoBadInterleavingCount = 0;
        mMediaInfoNotSeekableCount = 0;
        mMediaInfoMetdataUpdateCount = 0;
        mPlaybackError = false;
        initializeMessageLooper();
        synchronized (mLock) {
            try {
                mLock.wait(WAIT_FOR_COMMAND_TO_COMPLETE);
            } catch (Exception e) {
                Assert.assertTrue(false);
            }
        }
        try {
            mediaPlayer.setOnCompletionListener(mCompletionListener);
            mediaPlayer.setOnErrorListener(mOnErrorListener);
            mediaPlayer.setOnInfoListener(mInfoListener);
            mediaPlayer.setDataSource(video.getAbsolutePath());
            TestUtils.setTitle(activity, video.getName());
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
            mediaPlayer.start();
            int lastSeek = duration * 8 / 10;
            mediaPlayer.seekTo(lastSeek);

            int currentPosition = mediaPlayer.getCurrentPosition();
            Assert.assertTrue(currentPosition > lastSeek * 0.8);
            waittime = duration - mediaPlayer.getCurrentPosition();
            SystemClock.sleep(1000);

            synchronized (mOnCompletion) {
                try {
                    mOnCompletion.wait(waittime + 3000);
                } catch (Exception e) {
                    Assert.assertTrue(false);
                }
            }
            //mMediaPlayer.stop();


            Assert.assertTrue(mOnCompleteSuccess);
            terminateMessageLooper();
            if (activity != null) activity.finish();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
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

        if (!imageFolder.exists() || !videoFolder.exists() || !audioFolder.exists()) {
            throw new SkipExeption("Can't find samples folders");
        }

    }

    @DisplayName("Image format .jpg I000287 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpegI000287() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000287.toString()));
    }

    @DisplayName("Image format .jpg I000309 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000309() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000309.toString()));
    }

    @DisplayName("Image format .jpg I000311 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000311() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000311.toString()));
    }

    @DisplayName("Image format .jpg I000001 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testJpgI000001() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.jpgI000001.toString()));
    }

    @DisplayName("Image format .webp I000308 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testWebpI000308() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.webpI000308.toString()));
    }

    @DisplayName("Image format .gif I000021 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testGifI000021() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.gifI000021.toString()));
    }

    @DisplayName("Image format .bmp I000312 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testBmpI000312() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.bmpI000312.toString()));
    }

    @DisplayName("Image format .png I000301 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI0003012() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000301.toString()));
    }

    @DisplayName("Image format .png I000313 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testPngI000313() throws FileNotFoundException, SkipExeption {
        testImage(new File(IMAGE_FOLDER_PATH + Samples.pngI000313.toString()));
    }

    @DisplayName("Video format h263AV001248 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH263AV001248() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h263AV001248.toString()));
    }

    @DisplayName("Video format h264AV000869 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV000869() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV000869.toString()));
    }

    @DisplayName("Video format h264AV001097 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV001097() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV001097.toString()));
    }

    @DisplayName("Video format h264AV001187 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH264AV001187() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h264AV001187.toString()));
    }


    @DisplayName("Video format h265AV001429 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001429() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001429.toString()));
    }

    @DisplayName("Video format h265AV001430 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001430() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001430.toString()));
    }

    @DisplayName("Video format h265AV001431 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testH265AV001431() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.h265AV001431.toString()));
    }

    @DisplayName("Video format mpeg4AV000022 tests")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void testMPEG4AV000022() throws SkipExeption {
        testMedia(new File(VIDEO_FOLDER_PATH + Samples.mpeg4AV000022.toString()));
    }

//    @DisplayName("Video formats displaying tests")
//    @Severity(SeverityLevel.BLOCKER)
//    @UiThreadTest
//    @Test
//    public void videoFormatsTest() throws IOException, InterruptedException {
//        mediaPlayer = new MediaPlayer();
//        for (File video : new File(VIDEO_FOLDER_PATH).listFiles()) {
//            String path = video.getAbsolutePath();
//            activity.getSurfaceView();
//            mediaPlayer.setDisplay(surfaceView.getHolder());
//            mediaPlayer.setDataSource(path);
//            mediaPlayer.setLooping(true);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            Thread.sleep(5000);
//            Assert.assertTrue(mediaPlayer.isPlaying());
//            mediaPlayer.reset();
//        }
//        mediaPlayer.stop();
//    }

    @After
    public void afterTest() {

    }
}
