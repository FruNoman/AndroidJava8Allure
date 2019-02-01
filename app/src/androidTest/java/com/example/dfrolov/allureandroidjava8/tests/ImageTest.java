package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.test.rule.GrantPermissionRule;
import android.widget.ImageView;

import com.example.dfrolov.allureandroidjava8.MainActivity;
import com.example.dfrolov.allureandroidjava8.R;
import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Epic;
import com.example.dfrolov.allureandroidjava8.allure_implementation.exceptions.SkipException;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.dfrolov.allureandroidjava8.utils.Samples.bmpI000312;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.gifI000021;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.jpgI000001;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.jpgI000287;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.jpgI000309;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.jpgI000310;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.jpgI000311;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.pngI000301;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.pngI000313;
import static com.example.dfrolov.allureandroidjava8.utils.Samples.webpI000308;

@Epic("Image features")
@DisplayName("Media core suite")
@RunWith(RenesasRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageTest extends BaseTest {
    protected MainActivity activity;
    protected BitmapFactory.Options options;
    protected ImageView imageView;
    protected String IMAGE_FOLDER_PATH = "/sdcard/samples/image/";

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
        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    @DisplayName("Test image jpgI000287")
    @Test
    public void testJpegI000287() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + jpgI000287);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image jpgI000309")
    @Test
    public void testJpegI000309() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + jpgI000309);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image jpgI000310")
    @Test
    public void testJpegI000310() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + jpgI000310);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image jpgI000311")
    @Test
    public void testJpegI000311() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + jpgI000311);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image jpgI000001")
    @Test
    public void testJpegI000001() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + jpgI000001);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image jpgI000001")
    @Test
    public void testPngI000301() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + pngI000301);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image pngI000313")
    @Test
    public void testPngI000313() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + pngI000313);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image webpI000308")
    @Test
    public void testWebpI000308() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + webpI000308);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image gifI000021")
    @Test
    public void testGifI000021() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + gifI000021);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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

    @DisplayName("Test image bmpI000312")
    @Test
    public void testBmpI000312() throws FileNotFoundException {
        File file = new File(IMAGE_FOLDER_PATH + bmpI000312);
        final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
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


}
