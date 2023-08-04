package com.capstone.moa.entity.enums;

public enum Job {
    STUDENT,
    DEVELOPER,
    DESIGNER,
    PLANNER,
    MARKETER,
    ETC;

    public static Job find(String job) {
        return Job.valueOf(job.toUpperCase());
    }
}
