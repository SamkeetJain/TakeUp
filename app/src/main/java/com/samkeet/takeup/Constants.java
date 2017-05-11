package com.samkeet.takeup;

import android.content.SharedPreferences;

/**
 * Created by leelash on 09-05-2017.
 */

public class Constants {
    public static class URLs {
        public static String BASE = "http://52.66.31.167/takeup/";
        public static String ADD_PLANT = "addplant.php";
        public static String LOGIN = "login.php";
        public static String ADOPTION = "adopted.php";
        public static String PLANT_DB = "plant_details.php";
        public static String NOT_ADOPTED = "notadopted.php";
        public static String ADOPTED_DB = "adopted_details.php";
        public static String SIGNUP = "signup.php";
        public static String GET_PROFILE = "profile.php";
        public static String PUT_PROFILE = "updateprofile.php";
        public static String FIREBASE = "firebase.php";
    }

    public static class LocationsData {
        public static double lat = 0;
        public static double lon = 0;
        public static double acc = 0;

    }

    public static class SharedPreferenceData {
        public static SharedPreferences sharedPreferences = null;
        public static SharedPreferences.Editor editor = null;

        public static String SHAREDPREFERENCES = "TakeUp";
        public static String IS_LOGGED_IN = "isloggedin";
        public static String EMAIL = "email";
        public static String TOKEN = "token";
        public static String AUTH = "auth";
        public static String NAME = "name";

        public static void initSharedPreferenceData(SharedPreferences sharedPreferences1) {
            sharedPreferences = sharedPreferences1;
            editor = sharedPreferences.edit();
        }

        public static boolean isSharedPreferenceInited() {
            if (sharedPreferences != null)
                return true;
            return false;
        }

        public static String getTOKEN() {
            return sharedPreferences.getString(TOKEN, "NOT_AVALIBLE");
        }

        public static String getEMAIL() {
            return sharedPreferences.getString(EMAIL, "NOT_AVALIBLE");
        }

        public static String getIsLoggedIn() {
            return sharedPreferences.getString(IS_LOGGED_IN, "NOT_AVALIBLE");
        }

        public static String getNAME() {
            return sharedPreferences.getString(NAME, "NOT_AVALIBLE");
        }

        public static void setNAME(String name) {
            editor.putString(NAME, name);
            editor.apply();
        }

        public static String getAUTH() {
            return sharedPreferences.getString(AUTH, "NOT_AVALIBLE");
        }

        public static void setIsLoggedIn(String isLoggedIn) {
            editor.putString(IS_LOGGED_IN, "yes");
            editor.apply();
        }

        public static void setAUTH(String auth) {
            editor.putString(AUTH, auth);
            editor.apply();
        }

        public static void setEMAIL(String email) {
            editor.putString(EMAIL, email);
            editor.apply();
        }

        public static void setTOKEN(String token) {
            editor.putString(TOKEN, token);
            editor.apply();
        }

        public static void clearData() {
            editor.clear();
            editor.apply();
        }
    }

    public static class FireBase {
        public static String token;
    }
}
