package com.autohome.be.enums;

public enum UserResponse {
    SUCCESS("00", "success"),
    FAIL("01", "fail"),
    USER_NOT_EXIST("02", "user not exist"),
    USER_EXIST("03", "user exist"),
    USER_NOT_LOGIN("04", "user not login"),
    USER_NOT_LOGOUT("05", "user not logout"),
    USER_NOT_REGISTER("06", "user not register"),
    USER_NOT_UNREGISTER("07", "user not unregister"),
    USER_NOT_UPDATE("08", "user not update"),
    USER_NOT_DELETE("09", "user not delete");

    private String code;
    private String desc;

    UserResponse(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserResponse getUserResponseByCode(String code) {
        for (UserResponse userResponse : UserResponse.values()) {
            if (code.equals(userResponse.getCode())) {
                return userResponse;
            }
        }
        return null;
    }

}
