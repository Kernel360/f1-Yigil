package kr.co.yigil.travel.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

public class GeoJsonConverterTest {

    @DisplayName("유효한 GeoJson을 Point로 잘 변환하는지")
    @Test
    void convertToPoint_ValidGeoJson_ReturnsPoint() {
        String validGeoJsonPoint = "{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}";

        Point point = GeojsonConverter.convertToPoint(validGeoJsonPoint);

        assertNotNull(point);
        assertEquals(100.0, point.getX());
        assertEquals(0.0, point.getY());
    }

    @DisplayName("유효한 GeoJson을 LineString으로 잘 변환하는지")
    @Test
    void convertToLineString_ValidGeoJson_ReturnsLineString() {
        String validGeoJsonLineString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}";

        LineString lineString = GeojsonConverter.convertToLineString(validGeoJsonLineString);

        assertNotNull(lineString);
        assertEquals(2, lineString.getNumPoints());
        assertEquals(100.0, lineString.getCoordinateN(0).x);
        assertEquals(0.0, lineString.getCoordinateN(0).y);
        assertEquals(101.0, lineString.getCoordinateN(1).x);
        assertEquals(1.0, lineString.getCoordinateN(1).y);
    }

    @DisplayName("Point를 유효한 GeoJson으로 변환하는지")
    @Test
    void convertToJson_Point_ReturnsValidGeoJson() {
        Point point = GeojsonConverter.convertToPoint("{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}");

        String geoJson = GeojsonConverter.convertToJson(point);

        assertNotNull(geoJson);
        assertTrue(geoJson.contains("\"type\":\"Point\""));
        assertTrue(geoJson.contains("\"coordinates\":[100,0.0]"));
    }

    @DisplayName("LineString을 유효한 GeoJson으로 변환하는지")
    @Test
    void convertToJson_LineString_ReturnsValidGeoJson() {
        LineString lineString = GeojsonConverter.convertToLineString("{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}");

        String geoJson = GeojsonConverter.convertToJson(lineString);

        assertNotNull(geoJson);
        assertTrue(geoJson.contains("\"type\":\"LineString\""));
        assertTrue(geoJson.contains("\"coordinates\":[[100,0.0],[101,1]]"));
    }
}
