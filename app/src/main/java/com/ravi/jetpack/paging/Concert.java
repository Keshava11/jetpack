package com.ravi.jetpack.paging;

public class Concert {

    private String mConcertName;
    private String mConcertLocation;
    private String mOrganiserBand;

    public String getConcertName() {
        return mConcertName;
    }

    public void setConcertName(String mConcertName) {
        this.mConcertName = mConcertName;
    }

    public String getConcertLocation() {
        return mConcertLocation;
    }

    public void setConcertLocation(String mConcertLocation) {
        this.mConcertLocation = mConcertLocation;
    }

    public String getOrganiserBand() {
        return mOrganiserBand;
    }

    public void setOrganiserBand(String mOrganiserBand) {
        this.mOrganiserBand = mOrganiserBand;
    }
}
