package com.example.dfrolov.allureandroidjava8.wrappers;


import android.media.MediaPlayer;
import android.os.SystemClock;
import android.view.SurfaceView;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class MediaPlayerAdvanced implements
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnSeekCompleteListener {


    private Object lock = new Object();
    private boolean seeking;

    @Override
    public void onCompletion(MediaPlayer mp) {
        System.out.println("Complete file");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                System.out.println(("Unknown playback error" + mp.toString()));
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Assert.assertFalse("Server connection died", true);
            default:
                break;
//                Assert.assertFalse("Generic audio playback error",true);
        }


        switch (extra) {
            case MediaPlayer.MEDIA_ERROR_IO:
                Assert.assertFalse("IO media error", true);
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                Assert.assertFalse("Media error, malformed", true);
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                Assert.assertFalse("Unsupported media content", true);
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                Assert.assertFalse("Media timeout error", true);
            default:
                System.out.println(("Unknown playback error" + mp.toString()));
        }

        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_UNKNOWN:
                System.out.println("INFO UNKNOWN");
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                System.out.println("MEDIA_INFO_VIDEO_TRACK_LAGGING");
                break;
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                System.out.println("MEDIA_INFO_BAD_INTERLEAVING");

                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                System.out.println("MEDIA_INFO_NOT_SEEKABLE");
                Assert.assertTrue("MEDIA_INFO_NOT_SEEKABLE", false);
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                System.out.println("MEDIA_INFO_METADATA_UPDATE");
                break;
        }
        return true;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
            seeking = false;
        mp.start();
    }

    public MediaPlayer playSample(MediaPlayer mediaPlayer, File sample, SurfaceView view) throws IOException {
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        mediaPlayer.setDisplay(view.getHolder());
        mediaPlayer.setDataSource(sample.getPath());
        mediaPlayer.prepare();
//        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        return mediaPlayer;
    }

    public int randomSeekTo(MediaPlayer mediaPlayer) throws InterruptedException {
        int duration = mediaPlayer.getDuration();
        int lastSeek = (int) (Math.random() * duration);
        System.out.println("Before Seek");
        mediaPlayer.pause();
        synchronized (lock) {
            seeking = true;
        }
        mediaPlayer.seekTo(lastSeek);

            while (seeking) {
                SystemClock.sleep(1000);
                System.out.println("wait");
            }

        return lastSeek;
    }

    public void randomAction(MediaPlayer mediaPlayer) throws InterruptedException {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int mAction = (int) (random.nextInt() % 12);
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
//                    mediaPlayer.prepareAsync();
                    break;
                case 7:
                    int lastSeek = randomSeekTo(mediaPlayer);
                    break;
                case 8:
                    mediaPlayer.setLooping((random.nextInt() % 1000000) % 2 == 0);
                    break;
                case 9:
                    mediaPlayer.setVolume(((random.nextInt() % 1000000) % 1000) / 500.0f,
                            ((random.nextInt() % 1000000) / 1000) / 500.0f);
                    break;
                case 10:
                    mediaPlayer.start();
                    break;
                case 11:
//                    Thread.sleep((random.nextInt() % 20));
                    break;
            }
        }catch (Exception e){
            Assert.assertTrue(e.toString(),false);
        }
    }


}
