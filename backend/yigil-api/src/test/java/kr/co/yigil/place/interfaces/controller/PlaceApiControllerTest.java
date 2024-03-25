package kr.co.yigil.place.interfaces.controller;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Detail;
import kr.co.yigil.place.domain.PlaceInfo.Keyword;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.interfaces.dto.PlaceCoordinateDto;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageRequest;
import kr.co.yigil.place.interfaces.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(PlaceApiController.class)
@AutoConfigureRestDocs
class PlaceApiControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private PlaceFacade placeFacade;

	@MockBean
	private PlaceMapper placeMapper;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentationContextProvider)).build();
	}

	@DisplayName("findPlaceStaticImage가 잘 동작하는지")
	@Test
	void findPlaceStaticImage_ShouldReturnOk() throws Exception {
		Long memberId = 1L;

		PlaceInfo.MapStaticImageInfo mockInfo = mock(MapStaticImageInfo.class);
		String placeName = "어느장소";
		String placeAddress = "어느주소";
		PlaceImageRequest mockRequest = new PlaceImageRequest(placeName, placeAddress);

		Accessor mockAccessor = mock(Accessor.class);
		when(mockAccessor.getMemberId()).thenReturn(memberId);
		when(mockInfo.getImageUrl()).thenReturn("http://yigil.co.kr");
		when(mockInfo.isExists()).thenReturn(true);
		PlaceStaticImageResponse mockResponse = new PlaceStaticImageResponse(true, "http://yigil.co.kr", false);

		when(placeFacade.findPlaceStaticImage(anyLong(), anyString(), anyString())).thenReturn(mockInfo);
		when(placeMapper.toPlaceStaticImageResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/static-image")
				.param("name", mockRequest.getName())
				.param("address", mockRequest.getAddress()))
			.andExpect(status().isOk())
			.andDo(document(
				"places/find-static-image",
				getDocumentRequest(),
				getDocumentResponse(),
				queryParameters(
					parameterWithName("name").description("찾고있는 장소의 이름"),
					parameterWithName("address").description("찾고있는 장소의 주소")
				),
				responseFields(
					fieldWithPath("exists").type(JsonFieldType.BOOLEAN).description("해당 파일이 존재하는지 여부"),
					fieldWithPath("map_static_image_url").type(JsonFieldType.STRING).description("찾은 파일의 이미지 Url"),
					fieldWithPath("registered_place").type(JsonFieldType.BOOLEAN)
						.description("멤버가 해당 장소의 스팟을 등록했는지 여부")
				)
			));
	}

	@DisplayName("getPopularPlace가 잘 동작하는지")
	@Test
	void getPopularPlace_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		PopularPlaceResponse mockResponse = new PopularPlaceResponse(List.of(mockDto));

		when(placeFacade.getPopularPlace(any(Accessor.class))).thenReturn(mockInfo);
		when(placeMapper.toPopularPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/popular"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-popular-place",
				getDocumentRequest(),
				getDocumentResponse(),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}

	@DisplayName("getPopularPlaceMore가 잘 동작하는지")
	@Test
	void getPopularPlaceMore_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		PopularPlaceResponse mockResponse = new PopularPlaceResponse(List.of(mockDto));

		when(placeFacade.getPopularPlaceMore(any(Accessor.class))).thenReturn(mockInfo);
		when(placeMapper.toPopularPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/popular/more"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-popular-place-more",
				getDocumentRequest(),
				getDocumentResponse(),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));

	}

	@DisplayName("getPopularPlaceByDemographics가 잘 동작하는지")
	@Test
	void getPopularPlaceByDemographics_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		PopularPlaceResponse mockResponse = new PopularPlaceResponse(List.of(mockDto));

		when(placeFacade.getPopularPlaceByDemographics(anyLong())).thenReturn(mockInfo);
		when(placeMapper.toPopularPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/popular-demographics"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-popular-place-by-demographics",
				getDocumentRequest(),
				getDocumentResponse(),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}

	@DisplayName("getPopularPlaceByDemographicsMore가 잘 동작하는지")
	@Test
	void getPopularPlaceByDemographicsMore_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		PopularPlaceResponse mockResponse = new PopularPlaceResponse(List.of(mockDto));

		when(placeFacade.getPopularPlaceByDemographicsMore(anyLong())).thenReturn(mockInfo);
		when(placeMapper.toPopularPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/popular-demographics-more"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-popular-place-by-demographics-more",
				getDocumentRequest(),
				getDocumentResponse(),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}

	@DisplayName("retrievePlace 메서드가 잘 동작하는지")
	@Test
	void retrievePlace_ShouldReturnOk() throws Exception {
		Long placeId = 1L;
		Detail mockDetail = mock(Detail.class);
		PlaceDetailInfoDto.PointDto pointDto = new PlaceDetailInfoDto.PointDto();
		pointDto.setX(1.0);
		pointDto.setY(1.0);
		PlaceDetailInfoDto mockResponse = new PlaceDetailInfoDto(placeId, "장소명", "장소주소", "image.com", "image.net", pointDto,true,
			3.0, 50);

		when(placeFacade.retrievePlaceInfo(anyLong(), any(Accessor.class))).thenReturn(mockDetail);
		when(placeMapper.toPlaceDetailInfoDto(mockDetail)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/{placeId}", placeId))
			.andExpect(status().isOk())
			.andDo(document(
				"places/retrieve-place",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("placeId").description("장소의 고유 아이디")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("장소의 고유 아이디"),
					fieldWithPath("place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("장소의 주소"),
					fieldWithPath("thumbnail_image_url").type(JsonFieldType.STRING).description("장소의 대표 이미지 URL"),
					fieldWithPath("map_static_image_url").type(JsonFieldType.STRING).description("장소의 지도 이미지 URL"),
					fieldWithPath("point.x").type(JsonFieldType.NUMBER).description("장소의 x 좌표"),
					fieldWithPath("point.y").type(JsonFieldType.NUMBER).description("장소의 y 좌표"),
					fieldWithPath("bookmarked").type(JsonFieldType.BOOLEAN).description("유저의 장소 북마크 여부"),
					fieldWithPath("rate").type(JsonFieldType.NUMBER).description("장소의 평점"),
					fieldWithPath("review_count").type(JsonFieldType.NUMBER).description("장소 내의 리뷰 개수")
				)
			));
	}

	@DisplayName("getRegionPlaceMore 메서드가 잘 동작하는지")
	@Test
	void getRegionPlaceMore_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		RegionPlaceResponse mockResponse = new RegionPlaceResponse(List.of(mockDto));

		when(placeFacade.getPlaceInRegionMore(anyLong(), any(Accessor.class))).thenReturn(mockInfo);
		when(placeMapper.toRegionPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/region/{regionId}/more", 1L))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-region-place-more",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("regionId").description("지역의 고유 아이디")
				),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}

	@DisplayName("getRegionPlace 메서드가 잘 동작하는지")
	@Test
	void getRegionPlace_ShouldReturnOk() throws Exception {
		Main placeInfo = mock(Main.class);
		List<Main> mockInfo = List.of(placeInfo);
		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		RegionPlaceResponse mockResponse = new RegionPlaceResponse(List.of(mockDto));

		when(placeFacade.getPlaceInRegion(anyLong(), any(Accessor.class))).thenReturn(mockInfo);
		when(placeMapper.toRegionPlaceResponse(mockInfo)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/region/{regionId}", 1L))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-region-place",
				getDocumentRequest(),
				getDocumentResponse(),
				pathParameters(
					parameterWithName("regionId").description("지역의 고유 아이디")
				),
				responseFields(
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}

	@DisplayName("getNearPlace 메서드가 잘 동작하는지")
	@Test
	void getNearPlace_ShouldReturnOk() throws Exception {
		PlaceCommand.NearPlaceRequest mockRequest = mock(NearPlaceRequest.class);
		when(placeMapper.toNearPlaceCommand(any(
			kr.co.yigil.place.interfaces.dto.request.NearPlaceRequest.class))).thenReturn(mockRequest);

		Page<Place> mockSlice = mock(Page.class);
		when(placeFacade.getNearPlace(mockRequest)).thenReturn(mockSlice);

		PlaceCoordinateDto mockDto = new PlaceCoordinateDto(1L, 127.0, 38.0, "장소명");
		NearPlaceResponse mockResponse = new NearPlaceResponse(List.of(mockDto), 1, 1);

		when(placeMapper.toNearPlaceResponse(mockSlice)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/near")
				.param("minX", "1")
				.param("minY", "1")
				.param("maxX", "2")
				.param("maxY", "2")
				.param("page", "1"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-near-place",
				getDocumentRequest(),
				getDocumentResponse(),
				queryParameters(
					parameterWithName("minX").description("최소 x 좌표"),
					parameterWithName("minY").description("최소 y 좌표"),
					parameterWithName("maxX").description("최대 x 좌표"),
					parameterWithName("maxY").description("최대 y 좌표"),
					parameterWithName("page").description("페이지 번호")
				),
				responseFields(
					subsectionWithPath("places").description("주변 장소의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("장소의 고유 아이디"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 이름"),
					fieldWithPath("places[].x").type(JsonFieldType.NUMBER).description("장소의 x 좌표"),
					fieldWithPath("places[].y").type(JsonFieldType.NUMBER).description("장소의 y 좌표"),
					fieldWithPath("current_page").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
					fieldWithPath("total_pages").type(JsonFieldType.NUMBER).description("총 페이지의 개수")
				)
			));
	}

	@DisplayName("getPlaceKeyword 메서드가 잘 동작하는지")
	@Test
	void getPlaceKeyword_ShouldReturnOk() throws Exception {
		String keyword = "키워드";
		Keyword mockKeyword = mock(Keyword.class);
		when(placeFacade.getPlaceKeywords(keyword)).thenReturn(List.of(mockKeyword));

		PlaceKeywordResponse mockResponse = new PlaceKeywordResponse(List.of("키워드"));
		when(placeMapper.toPlaceKeywordResponse(List.of(mockKeyword))).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/keyword")
				.param("keyword", keyword))
			.andExpect(status().isOk())
			.andDo(document(
				"places/get-place-keyword",
				getDocumentRequest(),
				getDocumentResponse(),
				queryParameters(
					parameterWithName("keyword").description("검색하고자 하는 키워드")
				),
				responseFields(
					fieldWithPath("keywords[]").type(JsonFieldType.ARRAY).description("추천 키워드의 이름")
				)
			));
	}

	@DisplayName("searchPlace 메서드가 잘 동작하는지")
	@Test
	void searchPlace_ShouldReturnOk() throws Exception {
		Pageable pageable = PageRequest.of(0, 5);
		Accessor accessor = mock(Accessor.class);
		Slice<Main> mockSlice = mock(Slice.class);
		when(placeFacade.searchPlace("키워드", pageable, accessor)).thenReturn(mockSlice);

		PlaceInfoDto mockDto = new PlaceInfoDto(1L, "장소명", "10", "http://image.com", "3.5", true);
		PlaceSearchResponse mockResponse = new PlaceSearchResponse(List.of(mockDto), true);

		when(placeFacade.searchPlace(anyString(), any(Pageable.class), any(Accessor.class))).thenReturn(mockSlice);
		when(placeMapper.toPlaceSearchResponse(mockSlice)).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/places/search")
				.param("keyword", "키워드")
				.param("page", "1")
				.param("size", "5")
				.param("sortBy", "latest_uploaded_time")
				.param("sortOrder", "desc"))
			.andExpect(status().isOk())
			.andDo(document(
				"places/search-place",
				getDocumentRequest(),
				getDocumentResponse(),
				queryParameters(
					parameterWithName("keyword").description("검색하고자 하는 키워드"),
					parameterWithName("page").description("현재 페이지 - default:1").optional(),
					parameterWithName("size").description("페이지 크기 - default:5").optional(),
					parameterWithName("sortBy").description("정렬 옵션 - latest_uploaded_time(디폴트값) / rate")
						.optional(),
					parameterWithName("sortOrder").description("정렬 순서 - desc(디폴트값) 내림차순 / asc 오름차순")
						.optional()
				),
				responseFields(
					fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
					subsectionWithPath("places").description("place의 정보"),
					fieldWithPath("places[].id").type(JsonFieldType.NUMBER).description("place의 고유 Id"),
					fieldWithPath("places[].place_name").type(JsonFieldType.STRING).description("장소의 장소명"),
					fieldWithPath("places[].review_count").type(JsonFieldType.STRING).description("리뷰의 개수"),
					fieldWithPath("places[].thumbnail_image_url").type(JsonFieldType.STRING)
						.description("장소의 대표 이미지의 Url"),
					fieldWithPath("places[].rate").type(JsonFieldType.STRING).description("장소의 평점 정보"),
					fieldWithPath("places[].bookmarked").type(JsonFieldType.BOOLEAN).description("해당 장소의 북마크 여부")
				)
			));
	}
}
