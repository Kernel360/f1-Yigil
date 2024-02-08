package kr.co.yigil.global.exception;

import lombok.Getter;

@Getter
public class FileException extends BadRequestException {
    //
    public FileException(final ExceptionCode excepionCode) {
        super(excepionCode);
    }
}
