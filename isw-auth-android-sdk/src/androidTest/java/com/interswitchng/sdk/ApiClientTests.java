package com.interswitchng.sdk;

import android.test.suitebuilder.annotation.SmallTest;

import com.interswitchng.sdk.auth.AuthConfig;
import com.interswitchng.sdk.model.ApiStatus;
import com.interswitchng.passport.android.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Created by crownus on 8/6/15.
 */
@SmallTest
public class ApiClientTests {

    @Test
    public void testGetStatus() throws Exception {
        PassportClient passportClient = new PassportClient("project-x-app", "secret", "http://172.25.20.140:5050");
        AccessToken accessToken = passportClient.getAccessToken();
        String apiBase = "http://172.26.40.26:8080/";
        String apiPath = "/health";
        String url = String.format("%s%s", apiBase, apiPath);
        AuthConfig config = new AuthConfig("GET", url, "IKIA543A67D085A3D9DDAB1173A67779900F07B66D9E", "HJUR8yBS5dJxpAiMKDFfgo2MfXHTJv/1bg36TXm3jUQ=", accessToken.getToken(), "", AuthConfig.SHA1);
        DefaultApiClient client = new DefaultApiClient(apiBase, config);
        ResponseEntity<ApiStatus> response = client.process(null, apiPath, HttpMethod.GET,null,  ApiStatus.class);
        ApiStatus status = response.getBody();
        Assert.assertEquals("UP", status.getStatus());
    }
}
