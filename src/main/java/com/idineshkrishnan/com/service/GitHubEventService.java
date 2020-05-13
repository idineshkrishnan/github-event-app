package com.idineshkrishnan.com.service;

import com.idineshkrishnan.com.dto.Response;
import com.idineshkrishnan.com.error.ApplicationException;
import com.idineshkrishnan.com.error.InvalidInputException;
import com.idineshkrishnan.com.utils.ApplicationConstant;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class GitHubEventService {

    public Response fetch(final String owner, final String repo, final String eventType) {
        if((owner != null && !owner.isEmpty()) && (repo != null && !repo.isEmpty())) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(ApplicationConstant.SERVICE_BASE_URL+owner+"/"+repo+"/events");
            try(CloseableHttpResponse response = httpClient.execute(get)) {
                String responseStr = EntityUtils.toString(response.getEntity());
                Integer code = response.getStatusLine().getStatusCode();
                return new Response(code, responseStr);
            } catch(Exception e) {
                throw new ApplicationException("Internal Server Error.");
            }
        } else {
            throw new InvalidInputException("Invalid or Empty Input Is Give.");
        }
    }
}
