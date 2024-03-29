package kr.co.yigil.travel.util;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import org.jetbrains.annotations.NotNull;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

public class GeojsonConverter {

    public static LineString convertToLineString(String geoJson) {

        GeoJsonReader reader = getGeoJsonReader();
        try {
            if (reader.read(geoJson) instanceof LineString lineString) {
                return lineString;
            }
            throw new BadRequestException(ExceptionCode.INVALID_LINESTRING_GEO_JSON);
        } catch (ParseException e) {
            throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
        } catch (ClassCastException e) {
            throw new BadRequestException(ExceptionCode.GEO_JSON_CASTING_ERROR);
        }
    }

    public static Point convertToPoint(String geoJson) {
        GeoJsonReader reader = getGeoJsonReader();
        try {
            if (reader.read(geoJson) instanceof Point point) {
                return point;
            } else {
                throw new BadRequestException(ExceptionCode.INVALID_POINT_GEO_JSON);
            }
        } catch (ParseException e) {

            throw new BadRequestException(ExceptionCode.INVALID_GEO_JSON_FORMAT);
        } catch (ClassCastException e) {
            throw new BadRequestException(ExceptionCode.GEO_JSON_CASTING_ERROR);
        }
    }

    @NotNull
    private static GeoJsonReader getGeoJsonReader() {
//        PrecisionModel precisionModel = new PrecisionModel(5186);
//        GeometryFactory geometryFactory = new GeometryFactory(precisionModel);
//        GeoJsonReader reader = new GeoJsonReader(geometryFactory);
        GeoJsonReader reader = new GeoJsonReader();
        return reader;
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
