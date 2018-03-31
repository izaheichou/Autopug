package cgdatlahacks2k18.autopug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overwatch implements GameTitle {
    private String mName = "Overwatch";
    private String mBio;
    private Map<String, Boolean> mRoles;  // DPS, Healer, Tank, Defense, Symmetra, Any
    private Map<String, Boolean> mModes;  // pick from Ranked, Casual, Arcade, Custom, Any

    public Overwatch() {
        mRoles = new HashMap<>();
        mRoles.put("DPS", false);
        mRoles.put("Healer", false);
        mRoles.put("Tank", false);
        mRoles.put("Defense", false);
        mRoles.put("Symmetra", false);
        mRoles.put("Any", false);

        mModes = new HashMap<>();
        mModes.put("Ranked", false);
        mModes.put("Casual", false);
        mModes.put("Arcade", false);
        mModes.put("Custom", false);
        mModes.put("Any", false);
    }

    public String getName() {
        return mName;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        this.mBio = bio;
    }

    public void setRoles() {  // TODO: Figure out what param would be passed.
        // Should this update the database?

    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mRoles.entrySet()) {
            String role = keyval.getKey();
            Boolean value = keyval.getValue();
            if (value) {
                roles.add(role);
            }
        }
        return roles;
    }

    public void setModes() {  // TODO: Figure out what param would be passed.
        // Should this update the database?

    }

    public List<String> getModes() {
        List<String> modes = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mModes.entrySet()) {
            String role = keyval.getKey();
            Boolean value = keyval.getValue();
            if (value) {
                modes.add(role);
            }
        }
        return modes;
    }

}
