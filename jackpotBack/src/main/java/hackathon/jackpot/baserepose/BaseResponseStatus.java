package hackathon.jackpot.baserepose;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true,  "요청에 성공하였습니다."),
    DATABASE_ERROR(false,"데이터베이스 오류"),

    //실패 메시지
    NOT_FOUND_IMG(false, "이미지가 존재하지 않습니다"),
    FAIL_TO_UPLOAD_IMG(false, "이미지 저장에 실패하였습니다"),
    FAIL_TO_LOAD_IMG(false, "이미지 출력에 실패하였습니다"),

    DELETE_BY_NUM_OF_NOTIFY(true,"신고 누적으로 게시글이 삭제되었습니다.");

    private final boolean isSuccess;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
