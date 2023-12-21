package kr.co.yigil.travel.util;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;

public class GeojsonConverter {
    public static LineString convertToLineString(String geoJson) {
        GeoJsonReader reader = new GeoJsonReader();
        try {
            return (LineString) reader.read(geoJson);
        } catch (ParseException e) {

            throw new BadRequestException(ExceptionCode.INVALID_LINESTRING_JSON);
        }
    }

    public static Point convertToPoint(String geoJson) {
        GeoJsonReader reader = new GeoJsonReader();
        try {
            return (Point) reader.read(geoJson);
        } catch (ParseException e) {

            throw new BadRequestException(ExceptionCode.INVALID_LINESTRING_JSON);
        }
    }

    public static String convertToJson(Point point) {
        GeoJsonWriter writer = new GeoJsonWriter();
        return writer.write(point);
    }

    public static String convertToJson(LineString lineString) {
        GeoJsonWriter writer = new GeoJsonWriter();
        return writer.write(lineString);
    }

}
