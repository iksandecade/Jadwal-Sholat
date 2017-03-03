package com.example.iksandecade.jadwalsholat.eventbus;

/**
 * Created by iksandecade on 24/02/17.
 */

public class Events {

    public static class ActivityActivityMessage {
        private String message;
        private String secondMessage;

        public ActivityActivityMessage(String message, String secondMessage) {
            this.message = message;
            this.secondMessage = secondMessage;
        }

        public String getMessage() {
            return message;
        }

        public String getSecondMessage() {
            return secondMessage;
        }
    }

    public static class DayMessage {
        private String day;

        public DayMessage(String day) {
            this.day = day;
        }


        public String getDay() {
            return day;
        }

    }
}
