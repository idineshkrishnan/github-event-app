package com.idineshkrishnan.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private String id;
    private String type;
    private String actorId;
    private String actorName;
    private String actorUrl;
    private String actorPicture;
    private String createdAt;
}
