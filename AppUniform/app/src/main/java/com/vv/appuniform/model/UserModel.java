package com.vv.appuniform.model;

/**
 * The type User model.
 */
public class UserModel {
    private final String userId;
    private String coverUrl;
    private String fullName;
    private String description;

    /**
     * Instantiates a new User model.
     *
     * @param userId the user id
     */
    public UserModel(String userId) {
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets cover url.
     *
     * @return the cover url
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * Sets cover url.
     *
     * @param coverUrl the cover url
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
