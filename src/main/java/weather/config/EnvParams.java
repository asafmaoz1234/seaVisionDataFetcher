package weather.config;

public class EnvParams {
    public static final String WEATHER_API_BASE_URL = System.getenv("WEATHER_API_BASE_URL");
    public static final String API_REQUEST_POINT_LAT = System.getenv("API_REQUEST_POINT_LAT");
    public static final String API_REQUEST_POINT_LNG = System.getenv("API_REQUEST_POINT_LNG");
    public static final String API_REQUEST_PARAMS = System.getenv("API_REQUEST_PARAMS");
    public static final String API_REQUEST_SOURCE = System.getenv("API_REQUEST_SOURCE");
    public static final String API_AUTH_KEY = System.getenv("API_AUTH_KEY");

    public static final String AWS_KEY = System.getenv("AWS_KEY");
    public static final String AWS_SECRET = System.getenv("AWS_SECRET");
    public static final String SQS_EMAIL_QUEUE_URL = System.getenv("SQS_EMAIL_QUEUE_URL");

    public static String stringAllParams() {
        return "EnvParams{ "+
                ", WEATHER_API_BASE_URL=" + WEATHER_API_BASE_URL +
                ", API_REQUEST_POINT_LAT=" + API_REQUEST_POINT_LAT +
                ", API_REQUEST_POINT_LNG=" + API_REQUEST_POINT_LNG +
                ", API_REQUEST_PARAMS=" + API_REQUEST_PARAMS +
                ", API_REQUEST_SOURCE=" + API_REQUEST_SOURCE +
                ", API_AUTH_KEY=" + API_AUTH_KEY +
        '}';
    }
}
