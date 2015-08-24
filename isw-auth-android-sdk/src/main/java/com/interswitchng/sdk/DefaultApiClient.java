package com.interswitchng.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interswitchng.sdk.auth.AuthConfig;
import com.interswitchng.sdk.exception.APIConnectionException;
import com.interswitchng.sdk.exception.APIException;
import com.interswitchng.sdk.exception.AuthenticationException;
import com.interswitchng.sdk.exception.AuthorisationException;
import com.interswitchng.sdk.exception.InvalidRequestException;
import com.interswitchng.sdk.model.IswError;
import com.interswitchng.sdk.model.IswRequest;
import com.interswitchng.sdk.model.IswResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by crownus on 8/5/15.
 */
public class DefaultApiClient<S extends IswRequest, T extends IswResponse> {
    protected RestTemplate restTemplate;
    protected HttpHeaders headers;
    private String apiBase;

    public DefaultApiClient(String apiBase) {
        setupAuthHeaders(null);
        this.restTemplate = new RestTemplate();
        this.apiBase = apiBase;
    }

    public DefaultApiClient(String apiBase, AuthConfig authConfig) {
        Assert.hasText(apiBase, "'apiBase' cannot be empty");
        Assert.notNull(authConfig, "'authConfig' cannot be null");
        setupAuthHeaders(authConfig);
        this.restTemplate = new RestTemplate();
        this.apiBase = apiBase;
    }

    public DefaultApiClient(String apiBase, RestTemplate restTemplate, AuthConfig authConfig) {
        setupAuthHeaders(authConfig);
        this.apiBase = apiBase;
        this.restTemplate = restTemplate;
    }

    private void setupAuthHeaders(AuthConfig authConfig) {
        this.headers = new HttpHeaders();
        if (authConfig != null) {
            headers.set("Authorization", authConfig.getAuthorization());
            headers.set("Timestamp", Long.toString(authConfig.getTimestamp()));
            headers.set("Nonce", authConfig.getNonce());
            headers.set("Signature", authConfig.getSignature());
            headers.set("SignatureMethod", authConfig.getCrypto().replace("-", ""));
        }
    }

    public void setHeader(String key, String value) {
        this.headers.set(key, value);
    }

    public String getHeader(String key) {
        return this.headers.get(key) != null ? this.headers.get(key).toString() : "";
    }

    public ResponseEntity<T> process(S request, String apiPath, HttpMethod method, Map<String, String> params, Class<T> responseClass) throws APIConnectionException, InvalidRequestException, AuthenticationException, APIException, AuthorisationException {
        HttpEntity<S> req = new HttpEntity<>(request, headers);
        String requestId = null;
        if (request != null && request.getRequestId() != null) {
            requestId = request.getRequestId();
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiBase)
                .path(apiPath);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        URI targetUrl = builder.build().toUri();
        try {
            ResponseEntity<T> response = this.restTemplate.exchange(targetUrl, method, req, responseClass);
            return response;
        } catch (ResourceAccessException e) {
            throw new APIConnectionException(String.format(
                    "IOException during API request to Interswitch (%s): %s "
                            + "Please check your internet connection and try again. If this problem persists,"
                            + "you should check Interswitch's service status at https://twitter.com/iswconnect,"
                            + " or let us know at connect@interswitchng.com.",
                    apiBase, e.getMessage()), e);
        } catch (HttpClientErrorException e) {
            String message = e.getResponseBodyAsString();
            if (StringUtils.countOccurrencesOf(message, "error") > 0) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    IswResponse response = mapper.readValue(message, IswResponse.class);
                    if (response != null && response.getErrors() != null) {
                        List<IswError> errors = response.getErrors();
                        message = "";
                        for (IswError error : errors) {
                            message += "\n" + error.getMessage();
                        }
                    }
                } catch (IOException ioe) {

                }
            } else {
                message = e.getLocalizedMessage();
            }
            int code = e.getStatusCode().value();
            switch (code) {
                case 400:
                    throw new InvalidRequestException(message, message, requestId, null);
                case 404:
                    throw new InvalidRequestException(message, message, requestId, null);
                case 401:
                    throw new AuthenticationException(message, requestId);
                case 403:
                    throw new AuthorisationException(message, requestId);
                default:
                    throw new APIException(message, requestId, null);
            }
        } catch (RestClientException e) {
            throw new InvalidRequestException(e.getLocalizedMessage(), "", requestId, null);
        }
    }
}
