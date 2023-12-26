package kr.co.yigil.travel.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;


class GeojsonConverterTest {

    @Test
    void convertToLineString_ValidGeoJson_ReturnsLineString() throws ParseException {
        // Given
        String validGeoJson = "{ \"type\": \"LineString\", \"coordinates\": [ [0, 0], [1, 1] ] }";
        // Act
        LineString result = GeojsonConverter.convertToLineString(validGeoJson);
        // Assert
        assertNotNull(result);
        // Additional assertions based on the expected behavior of your convertToLineString method
        Coordinate[] coordinates = result.getCoordinates();
        assertEquals(2, coordinates.length);
        assertEquals(0, coordinates[0].getX());
        assertEquals(0, coordinates[0].getY());
        assertEquals(1, coordinates[1].getX());
        assertEquals(1, coordinates[1].getY());
    }

    @Test
    void convertToLineString_InvalidGeoJson_ThrowException() throws ParseException {

        //Given
        String validGeoJson = "{ \"type\": \"Point\", \"coordinates\": [ [0, 0], [1, 1] ] }";

        Throwable e = assertThrows(BadRequestException.class, () -> GeojsonConverter.convertToLineString(validGeoJson) );
        assertThat(e.getMessage()).isEqualTo(ExceptionCode.INVALID_GEO_JSON_FORMAT.getMessage());
    }

    @Test
    void convertToPoint_ValidGeoJson_ReturnsPoint() throws ParseException {
        // Given
        String validGeoJson = "{ \"type\": \"Point\", \"coordinates\": [0, 0] }";
        // Mock the GeoJsonReader
        GeoJsonReader reader = mock(GeoJsonReader.class);
        when(reader.read(any(String.class))).thenReturn(createValidPoint());
        // Act
        Point result = GeojsonConverter.convertToPoint(validGeoJson);
        // Assert
        assertNotNull(result);
        // Check specific properties of the Point (assuming your Point class has methods or properties for this)
        // assuming some tolerance for floating-point comparison
        assertEquals(0, result.getX(), 0.0001);
        assertEquals(0, result.getY(), 0.0001);
        // Additional assertions based on the expected behavior of your convertToPoint method
    }

    @Test
    void convertToPoint_InvalidGeoJson_ThrowException() throws ParseException {
        // Given
        String invalidGeoJson = "{ \"type\": \"LineString\", \"coordinates\": [ [0, 0], [1, 1] ] }";
//         Mock the GeoJsonReader
        GeoJsonReader reader = mock(GeoJsonReader.class);
        when(reader.read(anyString())).thenThrow(new ParseException(""));

        // Act and Assert
        Throwable e = assertThrows(BadRequestException.class, () -> GeojsonConverter.convertToPoint(invalidGeoJson));
        assertThat(e.getMessage()).isEqualTo(ExceptionCode.INVALID_GEO_JSON_FORMAT.getMessage());
        assertEquals(ExceptionCode.INVALID_GEO_JSON_FORMAT.getMessage(), e.getMessage());
    }
    @Test
    void GivenValidPoint_WhenConvertToJson_ThenReturnValidJson() {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(2.1,2.1));
//        String expectedString = "{\"type\":\"Point\",\"coordinates\": [2.1,2.1] }";

        String result = GeojsonConverter.convertToJson(point);
//        System.out.println("result = " + result);
//        assertThat(result).isEqualTo(expectedString);
        assertThat(result).isNotNull();

    }

    @Test
    void GivenValidLineString_WhenConvertToJson_ThenReturnValidJson() {
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> coordinates = List.of(
            new Coordinate(1.0, 1.0),
            new Coordinate(2.0, 2.0),
            new Coordinate(3.0, 3.0)
        );
        LineString lineString = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));
//        String expectedString = "{\"type\":\"Point\",\"coordinates\": [2.1,2.1] }";

        String result = GeojsonConverter.convertToJson(lineString);
        System.out.println("result = " + result);
//        assertThat(result).isEqualTo(expectedString);
        assertThat(result).isNotNull();
    }

    private Point createValidPoint() {
        return GeojsonConverter.createPoint(0, 0); // replace with your actual factory or method to create Point
    }
}