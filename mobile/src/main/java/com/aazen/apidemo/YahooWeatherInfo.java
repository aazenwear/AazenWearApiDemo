package com.aazen.apidemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class YahooWeatherInfo implements Parcelable {

    public static final Creator<YahooWeatherInfo> CREATOR = new Creator<YahooWeatherInfo>() {

        @Override
        public YahooWeatherInfo[] newArray(int size) {
            return new YahooWeatherInfo[size];
        }

        @Override
        public YahooWeatherInfo createFromParcel(Parcel source) {
            return new YahooWeatherInfo(source);
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityCode);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(country);
        dest.writeString(unitsTemperature);
        dest.writeString(unitsDistance);
        dest.writeString(unitsPressure);
        dest.writeString(unitsSpeed);
        dest.writeString(windChill);
        dest.writeString(windDirection);
        dest.writeString(windSpeed);
        dest.writeString(humidity);
        dest.writeString(visibility);
        dest.writeString(pressure);
        dest.writeString(timezone);
        dest.writeString(sunrise);
        dest.writeString(sunset);
        dest.writeString(text);
        dest.writeInt(code);
        dest.writeInt(temp);
    }

    public static final class Columns implements BaseColumns {

        public static final Uri CONTENT_URI =
                Uri.parse("content://com.igeak.weather/weatherinfo");

        public static final String CITYCODE = "cityCode";
        public static final String CITY = "city";
        public static final String REGION = "region";
        public static final String COUNTRY = "country";
        public static final String UNITSTEMP = "unitsTemperature";
        public static final String UNITSDIST = "unitsDistance";
        public static final String UNITSPRESS = "unitsPressure";
        public static final String UNITSSPEED = "unitsSpeed";
        public static final String WINDCHILL = "windChill";
        public static final String WINDDIRECT = "windDirection";
        public static final String WINDSPEED = "windSpeed";
        public static final String HUMIDITY = "humidity";
        public static final String VISIBILITY = "visibility";
        public static final String PRESSURE = "pressure";
        public static final String TIMEZONE = "timezone";
        public static final String SUNRISE = "sunrise";
        public static final String SUNSET = "sunset";
        public static final String TEXT = "text";
        public static final String CODE = "code";
        public static final String TEMP = "temp";
        public static final String DATE = "date";
        public static final String DESCRIPTION = "description";
        public static final String FORECASTINFOS = "forecastInfos";

        public static final int FORECASTINFO_CITYCODE_INDEX = 1;
        public static final int FORECASTINFO_CITY_INDEX = 2;
        public static final int FORECASTINFO_REGION_INDEX = 3;
        public static final int FORECASTINFO_COUNTRY_INDEX = 4;
        public static final int FORECASTINFO_UNITSTEMP_INDEX = 5;
        public static final int FORECASTINFO_UNITSDIST_INDEX = 6;
        public static final int FORECASTINFO_UNITSPRESS_INDEX = 7;
        public static final int FORECASTINFO_UNITSSPEED_INDEX = 8;
        public static final int FORECASTINFO_WINDCHILL_INDEX = 9;
        public static final int FORECASTINFO_WINDDIRECT_INDEX = 10;
        public static final int FORECASTINFO_WINDSPEED_INDEX = 11;
        public static final int FORECASTINFO_HUMIDITY_INDEX = 12;
        public static final int FORECASTINFO_VISIBILITY_INDEX = 13;
        public static final int FORECASTINFO_PRESSURE_INDEX = 14;
        public static final int FORECASTINFO_TIMEZONE_INDEX = 15;
        public static final int FORECASTINFO_SUNRISE_INDEX = 16;
        public static final int FORECASTINFO_SUNSET_INDEX = 17;
        public static final int FORECASTINFO_TEXT_INDEX = 18;
        public static final int FORECASTINFO_CODE_INDEX = 19;
        public static final int FORECASTINFO_TEMP_INDEX = 20;
        public static final int FORECASTINFO_DATE_INDEX = 21;
        public static final int FORECASTINFO_DESCRIPTION_INDEX = 22;

        public static final String[] WEATHERINFO_QUERY_COLUMNS = {
                CITYCODE, CITY, REGION, COUNTRY, UNITSTEMP, UNITSDIST, UNITSPRESS,
                UNITSSPEED, WINDCHILL, WINDDIRECT, WINDSPEED, HUMIDITY, VISIBILITY,
                PRESSURE, TIMEZONE, SUNRISE, SUNSET, TEXT, CODE, TEMP};

    }

    public String cityCode;
    public String city;
    public String region;
    public String country;
    public String unitsTemperature;
    public String unitsDistance;
    public String unitsPressure;
    public String unitsSpeed;
    public String windChill;
    public String windDirection;
    public String windSpeed;
    public String humidity;
    public String visibility;
    public String pressure;
    public String timezone;
    public String sunrise;
    public String sunset;
    public String text;
    public int code;
    public int temp;

    public YahooWeatherInfo() {
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(Columns.CITYCODE,cityCode);
        values.put(Columns.CITY,city);
        values.put(Columns.REGION,region);
        values.put(Columns.UNITSTEMP,unitsTemperature);
        values.put(Columns.UNITSDIST,unitsDistance);
        values.put(Columns.UNITSPRESS,unitsPressure);
        values.put(Columns.UNITSPRESS,unitsSpeed);
        values.put(Columns.WINDCHILL,windChill);
        values.put(Columns.WINDDIRECT,windDirection);
        values.put(Columns.WINDSPEED,windSpeed);
        values.put(Columns.HUMIDITY,humidity);
        values.put(Columns.VISIBILITY,visibility);
        values.put(Columns.PRESSURE,pressure);
        values.put(Columns.TIMEZONE,timezone);
        values.put(Columns.SUNRISE,sunrise);
        values.put(Columns.SUNSET,sunset);
        values.put(Columns.TEXT,text);
        values.put(Columns.CODE,code);
        values.put(Columns.TEMP,temp);
        return values;
    }

    public YahooWeatherInfo(Cursor cursor) {
        cityCode = cursor.getString(Columns.FORECASTINFO_CITYCODE_INDEX);
        city = cursor.getString(Columns.FORECASTINFO_CITY_INDEX);
        region = cursor.getString(Columns.FORECASTINFO_REGION_INDEX);
        country = cursor.getString(Columns.FORECASTINFO_COUNTRY_INDEX);
        unitsTemperature = cursor.getString(Columns.FORECASTINFO_UNITSTEMP_INDEX);
        unitsDistance = cursor.getString(Columns.FORECASTINFO_UNITSDIST_INDEX);
        unitsPressure = cursor.getString(Columns.FORECASTINFO_UNITSPRESS_INDEX);
        unitsSpeed = cursor.getString(Columns.FORECASTINFO_UNITSSPEED_INDEX);
        windChill = cursor.getString(Columns.FORECASTINFO_WINDCHILL_INDEX);
        windDirection = cursor.getString(Columns.FORECASTINFO_WINDDIRECT_INDEX);
        windSpeed = cursor.getString(Columns.FORECASTINFO_WINDSPEED_INDEX);
        humidity = cursor.getString(Columns.FORECASTINFO_HUMIDITY_INDEX);
        visibility = cursor.getString(Columns.FORECASTINFO_VISIBILITY_INDEX);
        pressure = cursor.getString(Columns.FORECASTINFO_PRESSURE_INDEX);
        timezone = cursor.getString(Columns.FORECASTINFO_TIMEZONE_INDEX);
        sunrise = cursor.getString(Columns.FORECASTINFO_SUNRISE_INDEX);
        sunset = cursor.getString(Columns.FORECASTINFO_SUNSET_INDEX);
        text = cursor.getString(Columns.FORECASTINFO_TEXT_INDEX);
        code = cursor.getInt(Columns.FORECASTINFO_CODE_INDEX);
        temp = cursor.getInt(Columns.FORECASTINFO_TEMP_INDEX);
    }

    public YahooWeatherInfo(Parcel parcel) {
        cityCode = parcel.readString();
        city = parcel.readString();
        region = parcel.readString();
        country = parcel.readString();
        unitsTemperature = parcel.readString();
        unitsDistance = parcel.readString();
        unitsPressure = parcel.readString();
        unitsSpeed = parcel.readString();
        windChill = parcel.readString();
        windDirection = parcel.readString();
        windSpeed = parcel.readString();
        humidity = parcel.readString();
        visibility = parcel.readString();
        pressure = parcel.readString();
        timezone = parcel.readString();
        sunrise = parcel.readString();
        sunset = parcel.readString();
        text = parcel.readString();
        code = parcel.readInt();
        temp = parcel.readInt();
    }

    @Override
    public String toString() {
        return "YahooWeatherInfo{" +
                "cityCode='" + cityCode + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", unitsTemperature='" + unitsTemperature + '\'' +
                ", unitsDistance='" + unitsDistance + '\'' +
                ", unitsPressure='" + unitsPressure + '\'' +
                ", unitsSpeed='" + unitsSpeed + '\'' +
                ", windChill='" + windChill + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", timezone='" + timezone + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", text='" + text + '\'' +
                ", code=" + code +
                ", temp=" + temp +"}";
    }
}
