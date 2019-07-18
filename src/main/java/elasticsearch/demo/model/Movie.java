package elasticsearch.demo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie implements Cacheable<String> {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private String startYear;
    private String endYear;
    private String runtimeMinutes;
    private List<String> genres;

    private Movie(String tconst, String titleType, String primaryTitle, String originalTitle,
                  boolean isAdult, String startYear, String endYear, String runtimeMinutes, List<String> genres) {
        this.tconst = tconst;
        this.titleType = titleType;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.isAdult = isAdult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
    }

    public String getTconst() {
        return tconst;
    }

    public String getTitleType() {
        return titleType;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Map<String, String> getList() {
        Map<String, String> data = new HashMap<>();
        data.put("tconst", this.tconst);
        data.put("titleType", this.titleType);
        data.put("primaryTitle", this.primaryTitle);
        data.put("originalTitle", this.originalTitle);
        data.put("isAdult", String.valueOf(this.isAdult));
        data.put("startYear", String.valueOf(this.startYear));
        data.put("endYear", String.valueOf(this.endYear));
        data.put("runtimeMinutes", String.valueOf(this.runtimeMinutes));
        data.put("genres", String.valueOf(genres));
        return data;
    }

    @Override
    public String getKey() {
        return this.tconst;
    }

    public static class Builder {
        private String tconst;
        private String titleType;
        private String primaryTitle;
        private String originalTitle;
        private boolean isAdult;
        private String startYear;
        private String endYear;
        private String runtimeMinutes;
        private List<String> genres;

        public Builder setTconst(String tconst) {
            this.tconst = tconst;
            return this;
        }

        public Builder setTitleType(String titleType) {
            this.titleType = titleType;
            return this;
        }

        public Builder setPrimaryTitle(String primaryTitle) {
            this.primaryTitle = primaryTitle;
            return this;
        }

        public Builder setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder setAdult(boolean adult) {
            isAdult = adult;
            return this;
        }

        public Builder setStartYear(String startYear) {
            this.startYear = startYear;
            return this;
        }

        public Builder setEndYear(String endYear) {
            this.endYear = endYear;
            return this;
        }

        public Builder setRuntimeMinutes(String runtimeMinutes) {
            this.runtimeMinutes = runtimeMinutes;
            return this;
        }

        public Builder setGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public Movie build() {
            return new Movie(this.tconst, this.titleType, this.primaryTitle, this.originalTitle
                    , this.isAdult, this.startYear, this.endYear, this.runtimeMinutes, this.genres);
        }
    }
}
