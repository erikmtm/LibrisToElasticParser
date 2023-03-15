package se.mtm.LibrisToElasticParser.controllers.requestModel;

import java.time.LocalDateTime;

public class DateTime {

    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
