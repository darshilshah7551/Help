package com.example.admin.help1;


import java.util.ArrayList;

public class DataClass {

    public static class InnerData {


        private static String dataName;
        private static String dataPhone;
        private static String dataGender;
        private static String dataEmail;
        private static ArrayList<String> number;

        public static String getSmsName() {
            return smsName;
        }

        public static void setSmsName(String smsName) {
            InnerData.smsName = smsName;
        }

        private static String smsName;

        public static ArrayList<String> getName() {
            return name;
        }

        public static void setName(ArrayList<String> name) {
            InnerData.name = name;
        }

        private static ArrayList<String> name;


        public static String getDataEmail() {
            return dataEmail;
        }

        public static void setDataEmail(String dataEmail) {
            InnerData.dataEmail = dataEmail;
        }

        public static String getDataName() {
            return dataName;
        }

        public static void setDataName(String dataName) {
            InnerData.dataName = dataName;
        }

        public static String getDataPhone() {
            return dataPhone;
        }

        public static void setDataPhone(String dataPhone) {
            InnerData.dataPhone = dataPhone;
        }

        public static String getDataGender() {
            return dataGender;
        }

        public static ArrayList<String> getNumber() {
            return number;
        }

        public static void setNumber(ArrayList<String> number) {
            InnerData.number = number;
        }

        public static void setDataGender(String dataGender) {
            InnerData.dataGender = dataGender;
        }


    }
}
