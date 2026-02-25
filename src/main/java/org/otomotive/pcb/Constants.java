package org.otomotive.pcb;

/**
 * Constants.
 */
public class Constants {

    /**
     * ZIP type.
     */
    public static final String APPLICATION_ZIP = "application/zip";

    /**
     * Application name.
     */
    public static final String APP_NAME = "PCB converter";

    /**
     * Not applicable.
     */
    public static final String N_A = "N/A";

    /**
     * Empty.
     */
    public static final String EMPTY = "";

    /**
     * Comma.
     */
    public static final String COMMA = ",";

    /**
     * Double quote.
     */
    public static final char DOUBLE_QUOTE = '"';

    /**
     * Millimeters.
     */
    public static final String MILLIMETERS = "mm";

    /**
     * New line.
     */
    public static final String NEWLINE = "\n";

    /**
     * New line patterne.
     */
    public static final String NEWLINE_PATTERN = "\\r?\\n";

    /**
     * Start time for {@link PerformanceLog}.
     */
    public static final String PERF_START = "PerformanceLogStart";

    /**
     * Field upn.
     */
    public static final String F_UPN = "upn";

    /**
     * Field ID.
     */
    public static final String F_ID = "id";

    /**
     * Field e-mail.
     */
    public static final String F_EMAIL = "email";


    /**
     * Field UUID.
     */
    public static final String F_UUID = "uuid";

    /**
     * Example UUID.
     */
    public static final String EX_UUID = "123e4567-e89b-12d3-a456-426614174000";

    /**
     * Example for POI pattern.
     */
    public static final String EX_POI_PATTERN = "(?<altitude>[0-9]{1,4}) - (?<country>[A-Z]{1,2}[-]{0,1}[A-Z]{0,2}) - (?<name>[\\p{IsAlphabetic}- ']{2,255})?(?<separator>, |\\()?(?<description>[\\p{IsAlphabetic}- ,']{0,1024})?(?<parenthesis>\\))?";

    /**
     * JWT audience.
     */
    public static final String JWT_AUDIENCE = "tbturn";

    /**
     * JWT issuer.
     */
    public static final String JWT_ISSUER = "https://otomotive.org/issuer";

    /**
     * Administrator role.
     */
    public static final String ROLE_ADMIN = "admin";

    /**
     * Professional role.
     */
    public static final String ROLE_PRO = "pro";

    /**
     * User role.
     */
    public static final String ROLE_USER = "user";

    /**
     * Security scheme name.
     */
    public static final String SECURITY_SCHEME_NAME = "TBT";


    /**
     * HTTP status {@code 100 Continue}.
     */
    public static final int HS_CONTINUE = 100;

    /**
     * HTTP status {@code 101 Switching Protocols}.
     */
    public static final int HS_SWITCHING_PROTOCOLS = 101;

    /**
     * HTTP status {@code 102 Processing}.
     */
    public static final int HS_PROCESSING = 102;

    /**
     * HTTP status {@code 103 Early Hints}.
     */
    public static final int HS_EARLY_HINTS = 103;

    /**
     * HTTP status {@code 200 OK}.
     */
    public static final int HS_OK = 200;

    /**
     * HTTP status {@code 201 Created}.
     */
    public static final int HS_CREATED = 201;

    /**
     * HTTP status {@code 202 Accepted}.
     */
    public static final int HS_ACCEPTED = 202;

    /**
     * HTTP status {@code 203 Non-Authoritative Information}.
     */
    public static final int HS_NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * HTTP status {@code 204 No Content}.
     */
    public static final int HS_NO_CONTENT = 204;

    /**
     * HTTP status {@code 205 Reset Content}.
     */
    public static final int HS_RESET_CONTENT = 205;

    /**
     * HTTP status {@code 206 Partial Content}.
     */
    public static final int HS_PARTIAL_CONTENT = 206;

    /**
     * HTTP status {@code 207 Multi-Status}.
     */
    public static final int HS_MULTI_STATUS = 207;

    /**
     * HTTP status {@code 208 Already Reported}.
     */
    public static final int HS_ALREADY_REPORTED = 208;

    /**
     * HTTP status {@code 226 IM Used}.
     */
    public static final int HS_IM_USED = 226;

    /**
     * HTTP status {@code 300 Multiple Choices}.
     */
    public static final int HS_MULTIPLE_CHOICES = 300;

    /**
     * HTTP status {@code 301 Moved Permanently}.
     */
    public static final int HS_MOVED_PERMANENTLY = 301;

    /**
     * HTTP status {@code 302 Found}.
     */
    public static final int HS_FOUND = 302;

    /**
     * HTTP status {@code 303 See Other}.
     */
    public static final int HS_SEE_OTHER = 303;

    /**
     * HTTP status {@code 304 Not Modified}.
     */
    public static final int HS_NOT_MODIFIED = 304;

    /**
     * HTTP status {@code 305 Use Proxy}.
     */
    public static final int HS_USE_PROXY = 305;

    /**
     * HTTP status {@code 306 Switch Proxy}.
     */
    public static final int HS_TEMPORARY_REDIRECT = 307;

    /**
     * HTTP status {@code 307 Temporary Redirect}.
     */
    public static final int HS_PERMANENT_REDIRECT = 308;

    /**
     * HTTP status {@code 400 Bad Request}.
     */
    public static final int HS_BAD_REQUEST = 400;

    /**
     * HTTP status {@code 401 Unauthorized}.
     */
    public static final int HS_UNAUTHORIZED = 401;

    /**
     * HTTP status {@code 402 Payment Required}.
     */
    public static final int HS_PAYMENT_REQUIRED = 402;

    /**
     * HTTP status {@code 403 Forbidden}.
     */
    public static final int HS_FORBIDDEN = 403;

    /**
     * HTTP status {@code 404 Not Found}.
     */
    public static final int HS_NOT_FOUND = 404;

    /**
     * HTTP status {@code 405 Method Not Allowed}.
     */
    public static final int HS_METHOD_NOT_ALLOWED = 405;

    /**
     * HTTP status {@code 406 Not Acceptable}.
     */
    public static final int HS_NOT_ACCEPTABLE = 406;

    /**
     * HTTP status {@code 407 Proxy Authentication Required}.
     */
    public static final int HS_PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * HTTP status {@code 408 Request Timeout}.
     */
    public static final int HS_REQUEST_TIMEOUT = 408;

    /**
     * HTTP status {@code 409 Conflict}.
     */
    public static final int HS_CONFLICT = 409;

    /**
     * HTTP status {@code 410 Gone}.
     */
    public static final int HS_GONE = 410;

    /**
     * HTTP status {@code 411 Length Required}.
     */
    public static final int HS_LENGTH_REQUIRED = 411;

    /**
     * HTTP status {@code 412 Precondition Failed}.
     */
    public static final int HS_PRECONDITION_FAILED = 412;

    /**
     * HTTP status {@code 413 Payload Too Large}.
     */
    public static final int HS_REQUEST_TOO_LONG = 413;

    /**
     * HTTP status {@code 414 URI Too Long}.
     */
    public static final int HS_REQUEST_URI_TOO_LONG = 414;

    /**
     * HTTP status {@code 415 Unsupported Media Type}.
     */
    public static final int HS_UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * HTTP status {@code 416 Range Not Satisfiable}.
     */
    public static final int HS_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * HTTP status {@code 417 Expectation Failed}.
     */
    public static final int HS_EXPECTATION_FAILED = 417;

    /**
     * HTTP status {@code 421 public static final int HS_}.
     */
    public static final int HS_MISDIRECTED_REQUEST = 421;

    /**
     * HTTP status {@code 422 Unprocessable Entity}.
     */
    public static final int HS_UNPROCESSABLE_ENTITY = 422;

    /**
     * HTTP status {@code 423 Locked}.
     */
    public static final int HS_LOCKED = 423;

    /**
     * HTTP status {@code 424 Failed Dependency}.
     */
    public static final int HS_FAILED_DEPENDENCY = 424;

    /**
     * HTTP status {@code 425 Too Early}.
     */
    public static final int HS_TOO_EARLY = 425;

    /**
     * HTTP status {@code 426 Upgrade Required}.
     */
    public static final int HS_UPGRADE_REQUIRED = 426;

    /**
     * HTTP status {@code 428 Precondition Required}.
     */
    public static final int HS_PRECONDITION_REQUIRED = 428;

    /**
     * HTTP status {@code 429 Too Many Requests}.
     */
    public static final int HS_TOO_MANY_REQUESTS = 429;

    /**
     * HTTP status {@code 431 Request Header Fields Too Large}.
     */
    public static final int HS_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /**
     * HTTP status {@code 451 Unavailable For Legal Reasons}.
     */
    public static final int HS_UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /**
     * HTTP status {@code 500 Internal Server Error}.
     */
    public static final int HS_INTERNAL_SERVER_ERROR = 500;

    /**
     * HTTP status {@code 501 Not Implemented}.
     */
    public static final int HS_NOT_IMPLEMENTED = 501;

    /**
     * HTTP status {@code 502 Bad Gateway}.
     */
    public static final int HS_BAD_GATEWAY = 502;

    /**
     * HTTP status {@code 503 Service Unavailable}.
     */
    public static final int HS_SERVICE_UNAVAILABLE = 503;

    /**
     * HTTP status {@code 504 Gateway Timeout}.
     */
    public static final int HS_GATEWAY_TIMEOUT = 504;

    /**
     * HTTP status {@code 505 HTTP Version Not Supported}.
     */
    public static final int HS_HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * HTTP status {@code 506 Variant Also Negotiates}.
     */
    public static final int HS_VARIANT_ALSO_NEGOTIATES = 506;

    /**
     * HTTP status {@code 507 Insufficient Storage}.
     */
    public static final int HS_INSUFFICIENT_STORAGE = 507;

    /**
     * HTTP status {@code 508 Loop Detected}.
     */
    public static final int HS_LOOP_DETECTED = 508;

    /**
     * HTTP status {@code 510 Not Extended}.
     */
    public static final int HS_NOT_EXTENDED = 510;

    /**
     * HTTP status {@code 511 Network Authentication Required}.
     */
    public static final int HS_NETWORK_AUTHENTICATION_REQUIRED = 511;

    /**
     * HTTP status {@code 100 Continue}.
     */
    public static final String SHS_CONTINUE = "100";

    /**
     * HTTP status {@code 101 Switching Protocols}.
     */
    public static final String SHS_SWITCHING_PROTOCOLS = "101";

    /**
     * HTTP status {@code 102 Processing}.
     */
    public static final String SHS_PROCESSING = "102";

    /**
     * HTTP status {@code 103 Early Hints}.
     */
    public static final String SHS_EARLY_HINTS = "103";

    /**
     * HTTP status {@code 200 OK}.
     */
    public static final String SHS_OK = "200";

    /**
     * HTTP status {@code 201 Created}.
     */
    public static final String SHS_CREATED = "201";

    /**
     * HTTP status {@code 202 Accepted}.
     */
    public static final String SHS_ACCEPTED = "202";

    /**
     * HTTP status {@code 203 Non-Authoritative Information}.
     */
    public static final String SHS_NON_AUTHORITATIVE_INFORMATION = "203";

    /**
     * HTTP status {@code 204 No Content}.
     */
    public static final String SHS_NO_CONTENT = "204";

    /**
     * HTTP status {@code 205 Reset Content}.
     */
    public static final String SHS_RESET_CONTENT = "205";

    /**
     * HTTP status {@code 206 Partial Content}.
     */
    public static final String SHS_PARTIAL_CONTENT = "206";

    /**
     * HTTP status {@code 207 Multi-Status}.
     */
    public static final String SHS_MULTI_STATUS = "207";

    /**
     * HTTP status {@code 208 Already Reported}.
     */
    public static final String SHS_ALREADY_REPORTED = "208";

    /**
     * HTTP status {@code 226 IM Used}.
     */
    public static final String SHS_IM_USED = "226";

    /**
     * HTTP status {@code 300 Multiple Choices}.
     */
    public static final String SHS_MULTIPLE_CHOICES = "300";

    /**
     * HTTP status {@code 301 Moved Permanently}.
     */
    public static final String SHS_MOVED_PERMANENTLY = "301";

    /**
     * HTTP status {@code 302 Found}.
     */
    public static final String SHS_FOUND = "302";

    /**
     * HTTP status {@code 303 See Other}.
     */
    public static final String SHS_SEE_OTHER = "303";

    /**
     * HTTP status {@code 304 Not Modified}.
     */
    public static final String SHS_NOT_MODIFIED = "304";

    /**
     * HTTP status {@code 305 Use Proxy}.
     */
    public static final String SHS_USE_PROXY = "305";

    /**
     * HTTP status {@code 306 Switch Proxy}.
     */
    public static final String SHS_TEMPORARY_REDIRECT = "307";

    /**
     * HTTP status {@code 307 Temporary Redirect}.
     */
    public static final String SHS_PERMANENT_REDIRECT = "308";

    /**
     * HTTP status {@code 400 Bad Request}.
     */
    public static final String SHS_BAD_REQUEST = "400";

    /**
     * HTTP status {@code 401 Unauthorized}.
     */
    public static final String SHS_UNAUTHORIZED = "401";

    /**
     * HTTP status {@code 402 Payment Required}.
     */
    public static final String SHS_PAYMENT_REQUIRED = "402";

    /**
     * HTTP status {@code 403 Forbidden}.
     */
    public static final String SHS_FORBIDDEN = "403";

    /**
     * HTTP status {@code 404 Not Found}.
     */
    public static final String SHS_NOT_FOUND = "404";

    /**
     * HTTP status {@code 405 Method Not Allowed}.
     */
    public static final String SHS_METHOD_NOT_ALLOWED = "405";

    /**
     * HTTP status {@code 406 Not Acceptable}.
     */
    public static final String SHS_NOT_ACCEPTABLE = "406";

    /**
     * HTTP status {@code 407 Proxy Authentication Required}.
     */
    public static final String SHS_PROXY_AUTHENTICATION_REQUIRED = "407";

    /**
     * HTTP status {@code 408 Request Timeout}.
     */
    public static final String SHS_REQUEST_TIMEOUT = "408";

    /**
     * HTTP status {@code 409 Conflict}.
     */
    public static final String SHS_CONFLICT = "409";

    /**
     * HTTP status {@code 410 Gone}.
     */
    public static final String SHS_GONE = "410";

    /**
     * HTTP status {@code 411 Length Required}.
     */
    public static final String SHS_LENGTH_REQUIRED = "411";

    /**
     * HTTP status {@code 412 Precondition Failed}.
     */
    public static final String SHS_PRECONDITION_FAILED = "412";

    /**
     * HTTP status {@code 413 Payload Too Large}.
     */
    public static final String SHS_REQUEST_TOO_LONG = "413";

    /**
     * HTTP status {@code 414 URI Too Long}.
     */
    public static final String SHS_REQUEST_URI_TOO_LONG = "414";

    /**
     * HTTP status {@code 415 Unsupported Media Type}.
     */
    public static final String SHS_UNSUPPORTED_MEDIA_TYPE = "415";

    /**
     * HTTP status {@code 416 Range Not Satisfiable}.
     */
    public static final String SHS_REQUESTED_RANGE_NOT_SATISFIABLE = "416";

    /**
     * HTTP status {@code 417 Expectation Failed}.
     */
    public static final String SHS_EXPECTATION_FAILED = "417";

    /**
     * HTTP status {@code 421 public static final String SHS_}.
     */
    public static final String SHS_MISDIRECTED_REQUEST = "421";

    /**
     * HTTP status {@code 422 Unprocessable Entity}.
     */
    public static final String SHS_UNPROCESSABLE_ENTITY = "422";

    /**
     * HTTP status {@code 423 Locked}.
     */
    public static final String SHS_LOCKED = "423";

    /**
     * HTTP status {@code 424 Failed Dependency}.
     */
    public static final String SHS_FAILED_DEPENDENCY = "424";

    /**
     * HTTP status {@code 425 Too Early}.
     */
    public static final String SHS_TOO_EARLY = "425";

    /**
     * HTTP status {@code 426 Upgrade Required}.
     */
    public static final String SHS_UPGRADE_REQUIRED = "426";

    /**
     * HTTP status {@code 428 Precondition Required}.
     */
    public static final String SHS_PRECONDITION_REQUIRED = "428";

    /**
     * HTTP status {@code 429 Too Many Requests}.
     */
    public static final String SHS_TOO_MANY_REQUESTS = "429";

    /**
     * HTTP status {@code 431 Request Header Fields Too Large}.
     */
    public static final String SHS_REQUEST_HEADER_FIELDS_TOO_LARGE = "431";

    /**
     * HTTP status {@code 451 Unavailable For Legal Reasons}.
     */
    public static final String SHS_UNAVAILABLE_FOR_LEGAL_REASONS = "451";

    /**
     * HTTP status {@code 500 Internal Server Error}.
     */
    public static final String SHS_INTERNAL_SERVER_ERROR = "500";

    /**
     * HTTP status {@code 501 Not Implemented}.
     */
    public static final String SHS_NOT_IMPLEMENTED = "501";

    /**
     * HTTP status {@code 502 Bad Gateway}.
     */
    public static final String SHS_BAD_GATEWAY = "502";

    /**
     * HTTP status {@code 503 Service Unavailable}.
     */
    public static final String SHS_SERVICE_UNAVAILABLE = "503";

    /**
     * HTTP status {@code 504 Gateway Timeout}.
     */
    public static final String SHS_GATEWAY_TIMEOUT = "504";

    /**
     * HTTP status {@code 505 HTTP Version Not Supported}.
     */
    public static final String SHS_HTTP_VERSION_NOT_SUPPORTED = "505";

    /**
     * HTTP status {@code 506 Variant Also Negotiates}.
     */
    public static final String SHS_VARIANT_ALSO_NEGOTIATES = "506";

    /**
     * HTTP status {@code 507 Insufficient Storage}.
     */
    public static final String SHS_INSUFFICIENT_STORAGE = "507";

    /**
     * HTTP status {@code 508 Loop Detected}.
     */
    public static final String SHS_LOOP_DETECTED = "508";

    /**
     * HTTP status {@code 510 Not Extended}.
     */
    public static final String SHS_NOT_EXTENDED = "510";

    /**
     * HTTP status {@code 511 Network Authentication Required}.
     */
    public static final String SHS_NETWORK_AUTHENTICATION_REQUIRED = "511";

}
