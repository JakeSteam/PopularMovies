package uk.co.jakelee.popularmovies.model;

public class Trailer {
    private String key;
    private String name;
    private String site;

    public Trailer() {
    }

    public Trailer(String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
