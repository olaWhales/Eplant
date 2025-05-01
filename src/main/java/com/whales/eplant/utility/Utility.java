package com.whales.eplant.utility;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String USER_CREATED_MESSAGE = "Account created successfully";
    public static final String PASSWORD_NOT_MATCH = "Password does not match, please check your password";
    public static final String VENDOR_ALREADY_REGISTER = "User already exists";
    public static final String START_AND_END_TIME_REQUIRED_MESSAGE = "Start time and end time are required";
    public static final String START_TIME_REQUIRE_MESSAGE = "End time must be after start time";
    public static final String USER_NOT_AUTHENTICATED_MESSAGE = "User not authenticated";
    public static final String EVENT_REGISTER_SUCCESSFULLY = "Event registered successfully";
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String INVALID_CREDENTIAL = "Invalid username or password" ;
    public static final String LOGIN_SUCCESSFUL_MESSAGE ="Successful login";



    @SuppressWarnings("unchecked")
    public static List<String> safeCastStringList(Object obj) {
        if (obj instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof String)) {
                    throw new IllegalArgumentException("Non-String element found: " + item.getClass());
                }
            }
            return (List<String>) list;
        }
        return new ArrayList<>();
    }

}
