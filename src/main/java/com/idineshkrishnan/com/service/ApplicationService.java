package com.idineshkrishnan.com.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.idineshkrishnan.com.dto.Event;
import com.idineshkrishnan.com.dto.Response;
import com.idineshkrishnan.com.error.ApplicationException;
import com.idineshkrishnan.com.error.ResourceNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private GitHubEventService gitHubEventService;

    public List<Event> getEvents(final String owner, final String repo, final String eventType) {
        Response response = gitHubEventService.fetch(owner, repo, eventType);
        if (response != null && response.getCode() == 200) {
            JsonArray jsonArray = new Gson().fromJson(response.getResponse(), JsonArray.class);
            return build(jsonArray);
        } else {
            throw new ApplicationException(response.getResponse());
        }
    }

    private List<Event> build(final JsonArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            List<Event> events = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                JsonObject actorDetails = jsonObject.get("actor").getAsJsonObject();
                String id = jsonObject.get("id").getAsString();
                String type = jsonObject.get("type").getAsString();
                String createdAt = jsonObject.get("created_at").getAsString();
                String actorId = actorDetails.get("id").getAsString();
                String actorName = actorDetails.get("display_login").getAsString();
                String actorUrl = actorDetails.get("url").getAsString();
                String actorPicture = actorDetails.get("avatar_url").getAsString();
                events.add(new Event(id, type, actorId, actorName, actorUrl, actorPicture, createdAt));
            }
            return events;
        } else {
            throw new ResourceNotFountException("Resource Not Found.");
        }
    }
}
