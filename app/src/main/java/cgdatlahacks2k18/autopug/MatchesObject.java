package cgdatlahacks2k18.autopug;

public class MatchesObject {
    private String userID, displayName, profileImageUrl, battleTag;

    public MatchesObject(String userID, String displayName, String profileImageUrl, String battleTag)
    {
        this.userID = userID;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.battleTag = battleTag;
    }

    public String getUserID()
    {
        return userID;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public String getBattleTag() {
        return battleTag;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }
    public void setDisplayName(String name)
    {
        this.displayName = name;
    }
    public void setProfileImageUrl(String url)
    {
        this.profileImageUrl = url;
    }
    public void setBattleTag(String tag)
    {
        this.battleTag = tag;
    }
}