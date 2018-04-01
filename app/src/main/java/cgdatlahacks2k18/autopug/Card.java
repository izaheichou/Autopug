package cgdatlahacks2k18.autopug;

public class Card {
    private String mUserId;
    private String mName;
    private String mProfileImageUrl;

    public Card(String uid, String name, String imageUrl) {
        this.mUserId = uid;
        this.mName = name;
        this.mProfileImageUrl = imageUrl;
    }

    public String getUserId() {
        return mUserId;
    }
    public String getName() {
        return mName;
    }
    public String getProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void setUserId(String uid) {
        this.mUserId = uid;
    }
    public void setName(String name) {
        this.mName = name;
    }
    public void setProfileImageUrl(String url) {
        this.mProfileImageUrl = url;
    }
}
