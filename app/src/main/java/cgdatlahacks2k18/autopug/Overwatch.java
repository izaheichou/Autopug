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
    public enum RolesAndModes {
        DPS(1), HEALER(2), TANK(3),
        DEFENSE(4), SYMMETRA(5), ANYROLE(6),
        RANKED(7), CASUAL(8), ARCADE(9), CUSTOM(10), ANYMODE(11);
        private final int id;
        RolesAndModes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    };

    static public int assignIdToString(String s) {
        if (s.equals("DPS")) {
            return RolesAndModes.DPS.getId();
        } else if (s.equals("Healer")) {
            return RolesAndModes.HEALER.getId();
        } else if (s.equals("Defense")) {
            return RolesAndModes.DEFENSE.getId();
        } else if (s.equals( "Tank")) {
            return RolesAndModes.TANK.getId();
        } else if (s.equals( "Symmetra")) {
            return RolesAndModes.SYMMETRA.getId();
        } else if (s.equals( "AnyRole")) {
            return RolesAndModes.ANYROLE.getId();
        } else if (s.equals( "Ranked")) {
            return RolesAndModes.RANKED.getId();
        } else if (s.equals( "Casual")) {
            return RolesAndModes.CASUAL.getId();
        } else if (s.equals( "Custom")) {
            return RolesAndModes.CUSTOM.getId();
        } else if (s.equals( "Arcade")) {
            return RolesAndModes.ARCADE.getId();
        } else {  // Any Mode
            return RolesAndModes.ANYMODE.getId();
        }
    }

    static public String idToString(int id) {
        switch (id) {
            case 1:
                return "DPS";
            case 2:
                return "Healer";
            case 3:
                return "Tank";
            case 4:
                return "Defense";
            case 5:
                return "Symmetra";
            case 6:
                return "AnyRole";
            case 7:
                return "Ranked";
            case 8:
                return "Casual";
            case 9:
                return "Arcade";
            case 10:
                return "Custom";
            case 11:
                return "AnyMode";
            default: return "ERROR";
        }
    }

    public Overwatch() {
        mRoles = new HashMap<>();
        mRoles.put("DPS", false);
        mRoles.put("Healer", false);
        mRoles.put("Tank", false);
        mRoles.put("Defense", false);
        mRoles.put("Symmetra", false);
        mRoles.put("AnyRole", false);

        mModes = new HashMap<>();
        mModes.put("Ranked", false);
        mModes.put("Casual", false);
        mModes.put("Arcade", false);
        mModes.put("Custom", false);
        mModes.put("AnyMode", false);

        mBio = "";
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

    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mRoles.entrySet()) {
            String role = keyval.getKey();
            roles.add(role);
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

    public List<String> getAllModes() {
        List<String> modes = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mRoles.entrySet()) {
            String mode = keyval.getKey();
            modes.add(mode);
        }
        return modes;
    }

    public void setRole(String role, Boolean val) {
        mRoles.put(role, val);
    }

    public void setMode(String mode, Boolean val) {
        mModes.put(mode, val);
    }
}
