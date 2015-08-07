package com.interswitchng.sdk.auth;


import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by crownus on 8/5/15.
 */
public class AuthConfig {
    public static final String SEPARATOR = "&";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA512 = "SHA-512";
    private String HTTPVerb;
    private String url;
    private String clientId;
    private String secretKey;
    private String accessToken;
    private String postData;
    private long timestamp;
    private String nonce;
    private String crypto;
    private SecureRandom random = new SecureRandom();
    private String[] postDataParams;

    public AuthConfig(String HTTPVerb, String url, String clientId, String secretKey, String accessToken, String postData, String crypto, String... postDataParams) {
        this(HTTPVerb, url, clientId, secretKey, secretKey, postData, crypto);
        this.postDataParams = postDataParams;

    }

    public AuthConfig(String HTTPVerb, String url, String clientId, String secretKey, String accessToken, String postData, long timestamp, String nonce, String crypto, String... postDataParams) {
        this(HTTPVerb, url, clientId, secretKey, secretKey, postData, timestamp, nonce, crypto);
        this.postDataParams = postDataParams;

    }

    public AuthConfig(String HTTPVerb, String url, String clientId, String secretKey, String accessToken, String postData, String crypto) {
        Assert.hasText(HTTPVerb, "'HTTPVerb' cannot be empty");
        Assert.hasText(url, "'url' cannot be empty");
        Assert.hasText(clientId, "'clientId' cannot be empty");
        Assert.hasText(secretKey, "'secretKey' cannot be empty");
        Assert.hasText(accessToken, "'accessToken' cannot be empty");
        Assert.hasText(crypto, "'crypto' cannot be empty");
        this.HTTPVerb = HTTPVerb;
        this.url = url;
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.accessToken = accessToken;
        this.postData = postData;
        this.timestamp = timestamp();
        this.nonce = nonce();
        this.crypto = crypto;
    }


    public AuthConfig(String HTTPVerb, String url, String clientId, String secretKey, String accessToken, String postData, long timestamp, String nonce, String crypto) {
        Assert.hasText(HTTPVerb, "'HTTPVerb' cannot be empty");
        Assert.hasText(url, "'url' cannot be empty");
        Assert.hasText(clientId, "'clientId' cannot be empty");
        Assert.hasText(secretKey, "'secretKey' cannot be empty");
        Assert.hasText(accessToken, "'accessToken' cannot be empty");
        Assert.notNull(timestamp, "'timestamp' cannot be null");
        Assert.hasText(nonce, "'nonce' cannot be empty");
        Assert.hasText(crypto, "'crypto' cannot be empty");
        this.HTTPVerb = HTTPVerb;
        this.url = url;
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.accessToken = accessToken;
        this.postData = postData;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.crypto = crypto;
    }

    public String getAuthorization() {
        return "Bearer " + accessToken;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getCrypto() {
        return crypto;
    }

    public String getSignature() {
        StringBuilder signature = new StringBuilder(HTTPVerb);
        url.replaceAll("(?<!(http:|https:))//", "/");
        try {
            if (!url.startsWith("https")) {
                url.replaceFirst("http", "https");
            }
            signature.append(SEPARATOR).append(URLEncoder.encode(url, "UTF-8")).append(SEPARATOR).append(Long.toString(timestamp)).append(SEPARATOR).append(nonce).append(SEPARATOR).append(clientId).append(SEPARATOR).append(secretKey);
            if (postData != null && postData.length() > 0) {
                signature.append(SEPARATOR).append(postData);
            }
            if (this.postDataParams != null && postDataParams.length > 0) {
                for (String postDataParam : postDataParams) {
                    signature.append(SEPARATOR).append(postDataParam);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash(signature.toString());
    }

    private String nonce() {
        return new BigInteger(130, random).toString(32);
    }

    private long timestamp() {
        return new Date().getTime() / 1000;
    }

    private String hash(String data) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(crypto);
            hash = Base64Utils.encodeToString(digest.digest(data.getBytes(Charset.forName("UTF-8"))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

}
