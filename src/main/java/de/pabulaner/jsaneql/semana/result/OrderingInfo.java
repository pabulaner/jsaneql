package de.pabulaner.jsaneql.semana.result;

public class OrderingInfo {

    private boolean descending;

    public OrderingInfo(boolean descending) {
        this.descending = descending;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }
}
