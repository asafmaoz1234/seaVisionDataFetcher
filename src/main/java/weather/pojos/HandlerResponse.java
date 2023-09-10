package weather.pojos;

import static weather.config.EnvParams.API_REQUEST_POINT_LAT;
import static weather.config.EnvParams.API_REQUEST_POINT_LNG;

public class HandlerResponse {
    private String message;
    private boolean allConditionsPassed;

    public HandlerResponse() {
    }

    public HandlerResponse(boolean allConditionsPassed) {
        this.allConditionsPassed = allConditionsPassed;
        this.updateMessage();
    }

    public boolean isAllConditionsPassed() {
        return allConditionsPassed;
    }

    public void setAllConditionsPassed(boolean allConditionsPassed) {
        this.allConditionsPassed = allConditionsPassed;
        this.updateMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void updateMessage() {
        this.message = "Nope, maybe tomorrow.";
        if(this.allConditionsPassed) {
            this.message = "Yes! got snorkeling";
        }
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + message + '\'' +
                ", pointChecked=" +  API_REQUEST_POINT_LAT+API_REQUEST_POINT_LNG +
                '}';
    }
}
