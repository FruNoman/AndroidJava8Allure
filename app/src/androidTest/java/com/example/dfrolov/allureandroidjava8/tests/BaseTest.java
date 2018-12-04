package com.example.dfrolov.allureandroidjava8.tests;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected final int REPEAT_TIMES = 30;
    protected Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    public static Context appContext = InstrumentationRegistry.getTargetContext();
    protected PackageManager packageManager = appContext.getPackageManager();
    protected UiDevice mDevice = UiDevice.getInstance(instrumentation);
    protected int TIMEOUT = 3000;
    protected final static int LOW_QUALITY = 20;
    protected final static int MEDIUM_QUALITY = 50;
    protected final static int FULL_QUALITY = 100;
    protected final static int SHORT_TIME_WAIT = 30000;
    protected final static int LONG_TIME_WAIT = 30000;
    public String time;

    String webmAV000962 = "AV_000962_big_buck_bunny_vga_vp8_25fps_vorbis.webm";
    String h263AV001248 =
            "AV_001248_lunarsurface_H263_CIF_2mbps_30fps_NB_AMR.mp4";
    String h264AV000869 =
            "AV_000869_H264_High_Profile_level_31_VGA_30fps_4Mbps_AAC_48kHz_128kbps_stereo.mp4";
    String h264AV001097 =
            "AV_001097_1080p_crowdrun_HP_cabac_2B_wBpred_adct_30fps_20mbps_1000fr_AAC_HE_48kHz_64kbps_stereo.mp4";
    String h264AV001187 =
            "AV_001187_Toy_Story3Official_Trailer_in_720p_h264_MP_L2_1280x720_24fps_1Mbps_eAACplus_64kbps_44100Hz.mp4";
    String h265AV001429 =
            "AV_001429_life_in_orbit_720p_main_2.mp4";
    String h265AV001430 =
            "AV_001430_iss_tour_720p_main_3.mp4";
    String h265AV001431 =
            "AV_001431_arthur_christmas_720p_psnr_main_22.mp4";
    String mpeg2AV000327 =
            "AV_000327_MPEG2_Main_Profile_Main_Level_D1_PAL_25fps_9.8Mbps_AAC_48khz_128kbps_Stereo.mp4";
    String mpeg2AV000328 =
            "AV_000328_MPEG2_Simple_Profile_Main_Level_QVGA_24fps_384kbps_WBAMR_23.85kbps.mp4";
    String mpeg2AV000472 =
            "AV_000472_MPEG2_QVGA_30fps_2Mbps_Main_Low_AMR_NB_12_2kbps.mp4";
    String mpeg2AV000474 =
            "AV_000474_MPEG2_QVGA_2Mbps_30fps_AAC_128kbps_48KHz.mp4";
    String mpeg2AV001235 =
            "AV_001235_nasa_sts131_landing_mpeg2_mp_ml_25fps_bf2_aac_hev2.mp4";
    String mpeg2AV001238 =
            "AV_001238_nasa_greenflight_mpeg2_mp_ll_30fps_bf2_aac_hev2.mp4";
    String mpeg2AV001239 =
            "AV_001239_launch_mpeg2_sp_ml_24fps_bf0_nbamr.mp4";

    String mpeg4AV000022 =
            "AV_000022_SF145.mp4";

    String mp3A000001 =
            "A_000001_02_Tetanus.mp3";
    String wavA000354 =
            "A_000354_PCM_16bit_48Khz_1536kbps_stereo.wav";

    String flacA000484 =
            "A_000484_SpoonRevenge.flac";
    String sample3gpA000123 =
            "A_000123_15dot85kbps_ex30.3gp";
    String aacA000157 =
            "A_000157_128kbps_werk32.aac";
    String amrA000443 =
            "A_000443_NB_AMR_8000Hz_10.2kbps.AMR";
    String mp4A000454 =
            "A_000454_eAACplus_48000Hz_32Kbps_Stereo_track1.mp4";
    String oggA000480 =
            "A_000480_The_Abyss-4T.ogg";
    String mkaA000485 =
            "A_000485_ehren-paper_lights.mka";


    public void setTitle(final Activity act, final String title) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.setTitle(title);
            }
        });
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static String getLogs(String time) {
        StringBuilder builder = new StringBuilder();

        try {
            String[] command = new String[]{"logcat", "-d", "-t", time};

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                builder.append(line + "\n");
                //Code here

            }
        } catch (Exception ex) {

        }
        return builder.toString();
    }

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRle =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void beforeEachTests() throws IOException, InterruptedException {
        String[] command = new String[]{"logcat", "-c"};
        Runtime.getRuntime().exec(command).waitFor();
        time = getCurrentTimeStamp();
    }

    @After
    public void afterEachTests() throws IOException {
        mDevice.pressBack();
        String logcat = getLogs(time);
        Allure.addAttachment("logcat", logcat);
    }
}
