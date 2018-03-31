package cgdatlahacks2k18.autopug;

import java.util.List;

public interface GameTitle {
    String getName();
    String getBio();
    void setBio(String bio);
    void setRoles();
    List<String> getRoles();
    void setModes();
    List<String> getModes();
}
