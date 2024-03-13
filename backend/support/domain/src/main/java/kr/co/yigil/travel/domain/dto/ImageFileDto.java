package kr.co.yigil.travel.domain.dto;

public abstract class ImageFileDto {
    protected String getImageUrl(String imageUrl){
        if(imageUrl == null){
            return null;
        }
        if(imageUrl.startsWith("http://") || imageUrl.startsWith("https://")){
            return imageUrl;
        }
        return "http://cdn.yigil.co.kr/"+imageUrl;
    }

}
