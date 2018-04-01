package cgdatlahacks2k18.autopug;

import java.util.List;

public class Card {
    private String mUserId;
    private String mName;
    private String mProfileImageUrl;
    private List<String> mRoles;
    private List<String> mTeamRoles;
    private String mMode;
    private String mPlatform;
    private String mBio;


    public Card(String uid, String name, String imageUrl, String mode, String platform, String bio,
                List<String> roles, List<String> teamRoles) {
        this.mUserId = uid;
        this.mName = name;
        this.mProfileImageUrl = imageUrl;
        this.mMode = mode;
        this.mPlatform = platform;
        this.mBio = bio;
        this.mRoles = roles;
        this.mTeamRoles = teamRoles;
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
    public String getMode() {
        return mMode;
    }
    public String getPlatform() {
        return mPlatform;
    }
    public List<String> getRoles() {
        return mRoles;
    }
    public List<String> getTeamRoles() {
        return mTeamRoles;
    }
    public String getRolesAsString() { return listToString(mRoles); }
    public String getTeamRolesAsString() { return listToString(mTeamRoles); }
    public String getBio() { return mBio; }

    public void setUserId(String uid) {
        this.mUserId = uid;
    }
    public void setName(String name) {
        this.mName = name;
    }
    public void setProfileImageUrl(String url) {
        this.mProfileImageUrl = url;
    }
    public void setMode(String mode) {
        this.mMode = mode;
    }
    public void setPlatform(String platform) {
        this.mPlatform= platform;
    }
    public void setRoles(List<String> roles) {
        this.mRoles= roles;
    }
    public void setTeamRoles(List<String> teamRoles) {
        this.mTeamRoles= teamRoles;
    }

    private String listToString(List<String> list) {
        String ret = "";
        for (int i = 0; i < list.size(); i++) {
            ret += list.get(i);
            if (i < list.size() - 1) {
                ret += ", ";
            }
        }
        return ret;
    }
}
