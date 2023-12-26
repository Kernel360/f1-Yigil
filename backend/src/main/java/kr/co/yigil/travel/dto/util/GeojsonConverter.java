package kr.co.yigil.travel.dto.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;

public class GeojsonConverter {

    private static final GeometryFactory geometryFactory = new GeometryFactory();
    public static LineString convertToLineString(String geoJson) {
        GeoJsonReader reader = new GeoJsonReader();
        try {
            if(reader.read(geoJson) instanceof  LineString lineString)
                return lineString;
            else
                throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
        } catch (ParseException e) {
            throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
        }
    }

    public static Point convertToPoint(String geoJson) {
        GeoJsonReader reader = new GeoJsonReader();
        try {
            if(reader.read(geoJson) instanceof Point point) {
                return point;
            }
            else {
                throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
            }
        } catch (ParseException | ClassCastException e) {
            throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
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
    public static Point createPoint(double x, double y) {
        return geometryFactory.createPoint(new Coordinate(x, y));
    }

}
