package com.capstone.moa.entity.enums;

public enum Profile {

    PROFILE1("profile_1"),
    PROFILE2("profile_2"),
    PROFILE3("profile_3"),
    PROFILE4("profile_4"),
    PROFILE5("profile_5"),
    PROFILE6("profile_6"),
    PROFILE7("profile_7"),
    PROFILE8("profile_8"),
    PROFILE9("profile_9"),
    PROFILE10("profile_10"),
    PROFILE11("profile_11"),
    PROFILE12("profile_12"),
    PROFILE13("profile_13"),
    PROFILE14("profile_14"),
    PROFILE15("profile_15")
    ;

    private final String profileName;

    Profile(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }

    public static String[] getProfileNames() {
        Profile[] profiles = Profile.values();
        String[] profileNames = new String[profiles.length];
        for (int i = 0; i < profiles.length; i++) {
            profileNames[i] = profiles[i].getProfileName();
        }
        return profileNames;
    }

    public static Profile fromProfileName(String input) {
        for (Profile profile : Profile.values()) {
            if (profile.getProfileName().equals(input)) {
                return profile;
            }
        }
        return PROFILE1;
    }
}