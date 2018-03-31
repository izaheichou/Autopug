package cgdatlahacks2k18.autopug;

import java.util.List;

public interface GameTitle {
    String getName();
    String getBio();
    void setBio(String bio);
    List<String> getRoles();
    List<String> getAllRoles();
    List<String> getModes();
    List<String> getAllModes();
    List<String> getPlatforms();
    List<String> getAllPlatforms();
    void setRole(String role, Boolean val);
    void setMode(String mode, Boolean val);
    void setPlatform(String platform, Boolean val);
}
