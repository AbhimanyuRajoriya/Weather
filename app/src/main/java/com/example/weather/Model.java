package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("rain")
    private Rain rain;

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public static class Main {
        @SerializedName("temp")
        private Double temp;

        @SerializedName("humidity")
        private Integer humidity;

        public Double getTemp() {
            return temp;
        }

        public Integer getHumidity() {
            return humidity;
        }
    }

    public static class Wind {
        @SerializedName("speed")
        private Double speed;

        public Double getSpeed() {
            return speed;
        }
    }

    public static class Rain {
        @SerializedName("1h")
        private Double oneHour;

        @SerializedName("3h")
        private Double threeHour;

        public Double getOneHour() {
            return oneHour;
        }

        public Double getThreeHour() {
            return threeHour;
        }
    }
}
