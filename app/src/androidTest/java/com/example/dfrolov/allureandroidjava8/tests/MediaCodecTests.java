package com.example.dfrolov.allureandroidjava8.tests;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.provider.Settings;
import android.support.test.rule.GrantPermissionRule;

import com.example.dfrolov.allureandroidjava8.allure_implementation.RenesasRunner;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Issue;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Link;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Severity;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.SeverityLevel;
import com.example.dfrolov.allureandroidjava8.allure_implementation.junit4.DisplayName;
import com.example.dfrolov.allureandroidjava8.core.BluetoothAdapterAllure;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RenesasRunner.class)
public class MediaCodecTests extends BaseTest{
    private MediaCodecList regularCodecs;

    @Rule
    public GrantPermissionRule permissionsRules =
            GrantPermissionRule.grant(
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_LOGS);

    @Before
    public void beforeMediaCodecsTests() {
        regularCodecs = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
    }


    @Issue("JIRA EXAMPLE ISSUE")
    @Link(name = "RNSS-4045 EXAMPLE", url = "https://embedded.globallogic.com.ua/testlink/")
    @DisplayName("Required media codecs test")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void codec_1_RequiredCodecsTest(){
        for (MediaCodecInfo info : regularCodecs.getCodecInfos()) {
            System.out.println(info.getName());
        }
    }

    @After
    public void afterMediaCodecsTests() {

    }
}
