package weather.pojos;

public class SnorkelingResult {
    private boolean canGoSnorkeling;
    private Integer readingCount;
    private Integer readingsAboveMinimum;
    private Integer consecutiveReadingsAboveMinimum;

    public SnorkelingResult(Integer readingCount, Integer readingsAboveMinimum, Integer consecutiveReadingsAboveMinimum) {
        this.readingCount = readingCount;
        this.readingsAboveMinimum = readingsAboveMinimum;
        this.consecutiveReadingsAboveMinimum = consecutiveReadingsAboveMinimum;
    }

    public boolean canGo() {
        return canGoSnorkeling;
    }

    public void setCanGoSnorkeling(boolean canGoSnorkeling) {
        this.canGoSnorkeling = canGoSnorkeling;
    }

    public Integer getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(Integer readingCount) {
        this.readingCount = readingCount;
    }

    public Integer getReadingsAboveMinimum() {
        return readingsAboveMinimum;
    }

    public void setReadingsAboveMinimum(Integer readingsAboveMinimum) {
        this.readingsAboveMinimum = readingsAboveMinimum;
    }

    public Integer getConsecutiveReadingsAboveMinimum() {
        return consecutiveReadingsAboveMinimum;
    }

    public void setConsecutiveReadingsAboveMinimum(Integer consecutiveReadingsAboveMinimum) {
        this.consecutiveReadingsAboveMinimum = consecutiveReadingsAboveMinimum;
    }
}
