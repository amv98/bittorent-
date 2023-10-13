package org.toytorrent;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Utils {

    CloseableHttpClient httpClient;

    Utils() {
        httpClient = HttpClients.createDefault();
    }

    InputStream sendHTTPRequest(String url, Map<String, String> params) throws Exception{
        try {

            StringBuilder urlParams = new StringBuilder();

            for (String key: params.keySet()) {
                if (urlParams.length() > 0) {
                    urlParams.append("&");
                }
                urlParams.append(key);
                urlParams.append("=");
                urlParams.append(params.get(key));
            }

            String fullUrl = url + "?" + urlParams;

            System.out.println(fullUrl);

            HttpGet httpGet = new HttpGet(fullUrl);
            HttpResponse response = httpClient.execute(httpGet);
            return response.getEntity().getContent();
        } catch (Exception e) {
           throw new Exception(e.toString());
        }
    }
}
