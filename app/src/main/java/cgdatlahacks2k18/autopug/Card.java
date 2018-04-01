package cgdatlahacks2k18.autopug;

public class Card {
    private String mUserId;
    private String mName;

    public Card(String uid, String name) {
        this.mUserId = uid;
        this.mName = name;
    }

    public String getUserId() {
        return mUserId;
    }
    public String getName() {
        return mName;
    }

    public void setUserId(String uid) {
        this.mUserId = uid;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
