package com.green.enums;


public enum ResponseStatus {

    SUCCESS(200,"Success")
    ,UPDATE_SUCCESS(201, "Update Success")
    ,DELETE_SUCCESS(202, "Delete Success")
    ,DELETE_FAIL(203, "Delete Failed")
    ,ALREADY_DELETED(204, "Already Deleted")
    ,PARAMETER_ERROR(400,"Check the Parameter")
    ,UPDATE_FAILED(401,"Update is Failed")
    ,RESPONSE_DATA_IS_EMPTY(404, "Response data is empty")
    ,DATA_NOT_FOUND(405,"There is no Data")
    ,DATA_EXIST(406,"The data is already exist")
    ,INSERT_FAILED(410,"Insert is Failed")
    ,UNSUPPORTED_MEDIA_TYPE(415,"The media format of the requested data is not supported by the server")
    ,INTERNAL_SERVER_ERROR(500,"Internal server error")
    ,REMOTE_SERVER_ERROR(510,"Remote server error")
    ,BETWEEN_PARAMETER_ERROR(406,"The toData is must be bigger than fromData")
    ,EXCEL_INSERT_ERROR(500,"Check the excel file.")
    ,ALREADY_REGISTERED(505,"The user is already registered")
    //희망도서 관련 Status Code 앞자리 1000번대부터 시작
    ,LAS_HOPE_BOOK_APPLY_SUCCESS(1200,"LAS Hope book apply Success")
    ,HOPE_BOOK_APPLY_SUCCESS(1210,"Apply Success")
    ,HOPE_BOOK_MODIFY_SUCCESS(1211,"Apply Modify Success")
    ,HOPE_BOOK_CANCEL_SUCCESS(1212,"Cancel Success")
    ,HOPE_BOOK_IS_EXIST(1220,"This book is existed the library")
    ,LAS_HOPE_BOOK_APPLY_FAILED(1400,"Hope book apply failed")
    //행사관련 Status Code 앞자리 2000번대부터 시작
    ,ALREADY_APPLIED(2210,"This user is already applied")
    ,ALREADY_CANCELED(2212,"This user is already canceled")
    ,NOT_FOUND_EVENT(2404,"There is no Event")
    ,EXCEED_APPLY_COUNT(2405,"The number of people who can apply has exceeded.")
    ,EXCEED_APPLY_GROUP_COUNT(2406,"The number of people as group who can apply has exceeded.")
    //블랙리스트 관련 Status Code 앞자리 3000번대부터 시작
    ,THIS_USER_IS_BANED(3200,"This user is registered the blacklist.")
    ,SEARCH_TYPE_ERROR(3400,"Check the search type parameter")
    ,USER_IS_NO_REG_THE_BLACKLIST(3404,"This user is not registered the blacklist.")
    //LIS 강좌 등록관련 4000번대부터 시작
    ,CHECK_THE_CENTER_PASSWORD(4001,"Check the center password.")
    //LAS 관련 Status Code 9000번대부터 시작
    ,LAS_SERVER_ERROR(9000,"Las Server Error")
    ,THERE_IS_NO_LOAN_HISTORY(9400,"Not exist loan history")
    ,THERE_IS_NO_LAS(9401,"There is no LAS")
    ,NOT_FOUND_USER(9404,"There is no User")
    ,NOT_EXIST_GENRE_CODE(9410,"The Genre Code is Not Exist")
    ,NOT_EXIST_LOCATION_CODE(9420,"The Location Code is Not Exist");

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() {
        return message;
    }

}
