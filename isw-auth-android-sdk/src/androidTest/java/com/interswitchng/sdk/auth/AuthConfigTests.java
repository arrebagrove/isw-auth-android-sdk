package com.interswitchng.sdk.auth;


import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by crownus on 8/5/15.
 */
@SmallTest
public class AuthConfigTests {
    @Test
    public void testSignature() {
        AuthConfig config = new AuthConfig("POST", "https://sandbox.interswitchng.com/api/v1/payments", "IKIA255D2F7E51CD0620462D8218AAA9C8A1A6FDF3BB", "zPJX5PcIrIZ6yVD3PiQ2iW/V2I932vpUAW1vSPhMJWc=", "", "", 1404136176, "89de80e5aef84b61bea21693243c4419", AuthConfig.SHA1);
        Assert.assertEquals("MgY5oidrZ0xqcNV+4wVBq0ibMp4=", config.getSignature());
    }
}
