package com.example.dfrolov.allureandroidjava8.tests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions.SkipExeption;
import com.example.dfrolov.allureandroidjava8.utils.TestUtils;

import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class VideoTest extends BaseTest {
    protected MainActivity activity;
    protected BitmapFactory.Options options;
    protected MediaPlayer mediaPlayer;
    protected ImageView imageView;
    protected SurfaceView surfaceView;
    protected String IMAGE_FOLDER_PATH = "/sdcard/samples/image/";
    protected String VIDEO_FOLDER_PATH = "/sdcard/samples/video/";
    protected String AUDIO_FOLDER_PATH = "/sdcard/samples/audio/";
    protected int WAIT_FOR_COMMAND_TO_COMPLETE = 60000;  //1 min max.
    protected boolean mPrepareReset = false;
    protected Looper mLooper = null;
    protected final Object mLock = new Object();
    protected final Object mPrepareDone = new Object();
    protected final Object mOnCompletion = new Object();
    protected final long PAUSE_WAIT_TIME = 2000;
    protected final long WAIT_TIME = 2000;
    protected final int SEEK_TIME = 10000;
    protected boolean mInitialized = false;

    protected boolean mOnPrepareSuccess = false;
    public boolean mOnCompleteSuccess = false;
    public boolean mPlaybackError = false;
    public int mMediaInfoUnknownCount = 0;
    public int mMediaInfoVideoTrackLaggingCount = 0;
    public int mMediaInfoBadInterleavingCount = 0;
    public int mMediaInfoNotSeekableCount = 0;
    public int mMediaInfoMetdataUpdateCount = 0;
    public volatile boolean mMediaServerDied;
    public static final int NUMBER_OF_PLAYER_RANDOM_ACTIONS   = 300;
    public volatile int mAction;
    public volatile int mParam;

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

    public MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
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
    public MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            synchronized (mOnCompletion) {
                mOnCompletion.notify();
                mOnCompleteSuccess = true;
            }
        }
    };

    public MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
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

    public MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
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

    public void testMedia(File video) throws SkipExeption, IOException {
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
    }

    class Watchdog extends Thread {
        private final long mTimeoutMs;
        private boolean mWatchdogStop;
        private boolean mWatchdogPinged;
        public Watchdog(long timeoutMs) {
            mTimeoutMs = timeoutMs;
            mWatchdogStop = false;
            mWatchdogPinged = false;
        }
        public synchronized void run() {
            while (true) {
                // avoid early termination by "spurious" waitup.
                final long startTimeMs = System.currentTimeMillis();
                long remainingWaitTimeMs = mTimeoutMs;
                do {
                    try {
                        wait(remainingWaitTimeMs);
                    } catch (InterruptedException ex) {
                        // ignore.
                    }
                    remainingWaitTimeMs = mTimeoutMs - (System.currentTimeMillis() - startTimeMs);
                } while (remainingWaitTimeMs > 0);
                if (mWatchdogStop) {
                    break;
                }
                if (!mWatchdogPinged) {
                    Assert.assertTrue(false);
                    return;
                }
                mWatchdogPinged = false;
            }
        }
        public synchronized void ping() {
            mWatchdogPinged = true;
            this.notify();
        }
        public synchronized void end() {
            mWatchdogStop = true;
            this.notify();
        }
    }

    public void testPlayerRandomAction(String fileName) throws Exception {
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
        Watchdog watchdog = new Watchdog(5000);
        try {
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (mediaPlayer == mp &&
                            what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                        mMediaServerDied = true;
                    }
                    return true;
                }
            });
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            mediaPlayer.start();
            long seed = System.currentTimeMillis();
            Random r = new Random(seed);
            watchdog.start();
            for (int i = 0; i < NUMBER_OF_PLAYER_RANDOM_ACTIONS; i++){
                watchdog.ping();
                Assert.assertTrue(!mMediaServerDied);
                mAction = (int)(r.nextInt() % 12);
                mParam = (int)(r.nextInt() % 1000000);
                try {
                    switch (mAction) {
                        case 0:
                            mediaPlayer.getCurrentPosition();
                            break;
                        case 1:
                            mediaPlayer.getDuration();
                            break;
                        case 2:
                            mediaPlayer.getVideoHeight();
                            break;
                        case 3:
                            mediaPlayer.getVideoWidth();
                            break;
                        case 4:
                            mediaPlayer.isPlaying();
                            break;
                        case 5:
                            mediaPlayer.pause();
                            break;
                        case 6:
                            // Don't add mMediaPlayer.prepare() call here for two reasons:
                            // 1. calling prepare() is a bad idea since it is a blocking call, and
                            // 2. when prepare() is in progress, mediaserver died message will not be sent to apps
                            mediaPlayer.prepareAsync();
                            break;
                        case 7:
                            mediaPlayer.seekTo((int)(mParam));
                            break;
                        case 8:
                            mediaPlayer.setLooping(mParam % 2 == 0);
                            break;
                        case 9:
                            mediaPlayer.setVolume((mParam % 1000) / 500.0f,
                                    (mParam / 1000) / 500.0f);
                            break;
                        case 10:
                            mediaPlayer.start();
                            break;
                        case 11:
                            Thread.sleep(mParam % 20);
                            break;
                    }
                } catch (Exception e) {

                }
            }
            mediaPlayer.stop();
        } catch (Exception e) {
        } finally {
            watchdog.end();
            watchdog.join();
        }
    }
}
