package com.interswitchng.sdk;

import android.test.suitebuilder.annotation.SmallTest;

import com.interswitchng.sdk.auth.AuthConfig;
import com.interswitchng.sdk.model.ApiStatus;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Created by crownus on 8/6/15.
 */
@SmallTest
public class ApiClientTests {

    @Test
    public void testGetStatus() throws Exception {
        String apiBase = "http://172.26.40.26:8080/";
        String apiPath = "/health";
        String url = String.format("%s%s", apiBase, apiPath);
        AuthConfig config = new AuthConfig("GET", url, "IKIA543A67D085A3D9DDAB1173A67779900F07B66D9E", "HJUR8yBS5dJxpAiMKDFfgo2MfXHTJv/1bg36TXm3jUQ=", "eyJhbGciOiJSUzI1NiJ9.eyJhdWQiOlsiY2FyZGxlc3Mtc2VydmljZSIsInBhc3Nwb3J0IiwicHJvamVjdC14LWNvbnN1bWVyIl0sInNjb3BlIjpbInByb2ZpbGUiXSwiZXhwIjoxNDM5Mjg1Nzc1LCJqdGkiOiJlMzhkN2IwNy0yYmEwLTRkMDUtYTlhMi1lOTVmZWUyYTFjNDMiLCJjbGllbnRfaWQiOiJwcm9qZWN0LXgtYXBwIn0.X6h7lHguhYuet5Ig5BKSY7HGfn3D8UFOkQ0QYw5MexmkRxck4twgboCfZuNqo1z2633045yAydlfvVx_3uFW_oKxnrwLlcjYhnF8w1W3H-03aOJic2NFyNV3qxLtgZELQxGQNSdBZjrKgdzxubSqBlGlZxxqwmFMBnEykarXJn5ci7i7Hkr3YsKDShcZr7T2cU3b9jf0tq9H-2XnTt-q9yGXPSbGag98TDDYmOYINRuJM5H-peK2hbwxYvoZlIN3SKV5Rt78CMK9LKLHhyPPTnohvHX0bufHpcc1z6eYuExUFmVTHhq9sGPJ4AdqzKU0hgOk7WfJLlMzEN2dxiymKA", "", AuthConfig.SHA1);
        DefaultApiClient client = new DefaultApiClient(apiBase, config);
        client.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        client.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<ApiStatus> response = client.process(null, apiPath, HttpMethod.GET,null,  ApiStatus.class);
        ApiStatus status = response.getBody();
        Assert.assertEquals("UP", status.getStatus());
    }
}
