package cgdatlahacks2k18.autopug.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overwatch implements GameTitle {
    private String mName = "Overwatch";
    private String mBio;
    private Integer mMaxPartySize = 6;
    private Map<String, Boolean> mRoles;  // DPS, Healer, Tank, Defense, Symmetra, Any
    private Map<String, Boolean> mModes;  // pick from Ranked, Casual, Arcade, Custom, Any
    private Map<String, Boolean> mPlatforms;  // PS4, PC, XBONE
    public enum Ids {
        DPS(1, "DPS"), HEALER(2, "Healer"), TANK(3, "Tank"),
        DEFENSE(4, "Defense"), SYMMETRA(5, "Symmetra"), ANYROLE(6, "AnyRole"),
        RANKED(7, "Ranked"), CASUAL(8, "Casual"), ARCADE(9, "Arcade"), CUSTOM(10, "Custom"),
        ANYMODE(11, "AnyMode"), PS4(12, "PS4"), PC(13, "PC"), XBONE(14, "XBONE");
        private final int id;
        private final String name;
        Ids(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }

    static public int assignIdToString(String s) {
        if (s.equals(Ids.DPS.getName())) {
            return Ids.DPS.getId();
        } else if (s.equals(Ids.HEALER.getName())) {
            return Ids.HEALER.getId();
        } else if (s.equals(Ids.DEFENSE.getName())) {
            return Ids.DEFENSE.getId();
        } else if (s.equals(Ids.TANK.getName())) {
            return Ids.TANK.getId();
        } else if (s.equals(Ids.SYMMETRA.getName())) {
            return Ids.SYMMETRA.getId();
        } else if (s.equals(Ids.ANYROLE.getName())) {
            return Ids.ANYROLE.getId();
        } else if (s.equals(Ids.RANKED.getName())) {
            return Ids.RANKED.getId();
        } else if (s.equals(Ids.CASUAL.getName())) {
            return Ids.CASUAL.getId();
        } else if (s.equals(Ids.CUSTOM.getName())) {
            return Ids.CUSTOM.getId();
        } else if (s.equals(Ids.ARCADE.getName())) {
            return Ids.ARCADE.getId();
        } else if (s.equals(Ids.ANYMODE.getName())) {  // Any Mode
            return Ids.ANYMODE.getId();
        } else if (s.equals(Ids.PS4.getName())) {  // Any Mode
            return Ids.PS4.getId();
        } else if (s.equals(Ids.PC.getName())) {  // Any Mode
            return Ids.PC.getId();
        } else if (s.equals(Ids.XBONE.getName())) {  // Any Mode
            return Ids.XBONE.getId();
        }
        return -1;
    }

    static public String idToString(int id) {
        switch (id) {
            case 1:
                return Ids.DPS.getName();
            case 2:
                return Ids.HEALER.getName();
            case 3:
                return Ids.TANK.getName();
            case 4:
                return Ids.DEFENSE.getName();
            case 5:
                return Ids.SYMMETRA.getName();
            case 6:
                return Ids.ANYROLE.getName();
            case 7:
                return Ids.RANKED.getName();
            case 8:
                return Ids.CASUAL.getName();
            case 9:
                return Ids.CUSTOM.getName();
            case 10:
                return Ids.ARCADE.getName();
            case 11:
                return Ids.ANYMODE.getName();
            case 12: return Ids.PS4.getName();
            case 13: return Ids.PC.getName();
            case 14: return Ids.XBONE.getName();
            default: return "ERROR";
        }
    }

    public Overwatch() {
        mRoles = new HashMap<>();
        mRoles.put(Ids.DPS.getName(), false);
        mRoles.put(Ids.HEALER.getName(), false);
        mRoles.put(Ids.TANK.getName(), false);
        mRoles.put(Ids.DEFENSE.getName(), false);
        mRoles.put(Ids.SYMMETRA.getName(), false);
        mRoles.put(Ids.ANYROLE.getName(), false);

        mModes = new HashMap<>();
        mModes.put(Ids.RANKED.getName(), false);
        mModes.put(Ids.CASUAL.getName(), false);
        mModes.put(Ids.ARCADE.getName(), false);
        mModes.put(Ids.CUSTOM.getName(), false);
        mModes.put(Ids.ANYMODE.getName(), false);

        mPlatforms = new HashMap<>();
        mPlatforms.put(Ids.PS4.getName(), false);
        mPlatforms.put(Ids.PC.getName(), false);
        mPlatforms.put(Ids.XBONE.getName(), false);

        mBio = "";
    }

    public String getName() {
        return mName;
    }

    public String getBio() {
        return mBio;
    }
    public Integer getMaxPartySize() {
        return mMaxPartySize;
    }

    public void setBio(String bio) {
        this.mBio = bio;
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

    public List<String> getModes() {
        List<String> modes = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mModes.entrySet()) {
            String mode = keyval.getKey();
            Boolean value = keyval.getValue();
            if (value) {
                modes.add(mode);
            }
        }
        return modes;
    }

    public List<String> getAllModes() {
        List<String> modes = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mModes.entrySet()) {
            String mode = keyval.getKey();
            modes.add(mode);
        }
        return modes;
    }

    public List<String> getPlatforms() {
        List<String> platforms = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mPlatforms.entrySet()) {
            String platform = keyval.getKey();
            Boolean value = keyval.getValue();
            if (value) {
                platforms.add(platform);
            }
        }
        return platforms;
    }

    public List<String> getAllPlatforms() {
        List<String> platforms = new ArrayList<String>();
        for (Map.Entry<String, Boolean> keyval : mPlatforms.entrySet()) {
            String platform = keyval.getKey();
            platforms.add(platform);
        }
        return platforms;
    }

    public void setRole(String role, Boolean val) {
        mRoles.put(role, val);
    }

    public void setMode(String mode, Boolean val) {
        mModes.put(mode, val);
    }

    public void setPlatform(String platform, Boolean val) {
        mPlatforms.put(platform, val);
    }
}
