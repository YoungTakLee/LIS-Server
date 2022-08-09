package com.green.config;

import lombok.Data;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Data
public class GesUtilsBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RestTemplate restTemplate;
    private String numeric = "[^0-9]";

    public GesUtilsBean() {
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setMaxConnTotal(50) // 오픈가능한 최대 커넥션 수
                .setMaxConnPerRoute(10) // 특정 경로(IP)에 연결 수제한
                .evictIdleConnections(60L * 2L * 1000L, TimeUnit.MILLISECONDS)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(60 * 1000); //Connection Time out 60초
        factory.setReadTimeout(60 * 1000); //Read Time out 60초
        this.restTemplate = new RestTemplate(factory);
    }

    public String getGenre(String callNo) {

        String genreNum = getGenreCode(callNo);

        String genreStr = "";

        switch (genreNum) {
            case "0":
                genreStr = "총류";
                break;
            case "1":
                genreStr = "철학";
                break;
            case "2":
                genreStr = "종교";
                break;
            case "3":
                genreStr = "사회과학";
                break;
            case "4":
                genreStr = "자연과학";
                break;
            case "5":
                genreStr = "기술과학";
                break;
            case "6":
                genreStr = "예술";
                break;
            case "7":
                genreStr = "언어";
                break;
            case "8":
                genreStr = "문학";
                break;
            case "9":
                genreStr = "역사";
                break;
            default:
                break;
        }

        return genreStr;

    }

    public String genreNameFromKdc(int kdc) {
        String genreStr = null;
        switch (kdc) {
            case 0:
                genreStr = "총류";
                break;
            case 1:
                genreStr = "철학";
                break;
            case 2:
                genreStr = "종교";
                break;
            case 3:
                genreStr = "사회과학";
                break;
            case 4:
                genreStr = "자연과학";
                break;
            case 5:
                genreStr = "기술과학";
                break;
            case 6:
                genreStr = "예술";
                break;
            case 7:
                genreStr = "언어";
                break;
            case 8:
                genreStr = "문학";
                break;
            case 9:
                genreStr = "역사";
                break;
            default:
                break;
        }

        return genreStr;
    }

    public String getGenreCode(String callNo) {
        String kdc = null;
        try {
            // 향 814.6-김65ㅁ-7
            String[] splitForSpace = callNo.split(" ");

            if (splitForSpace.length > 1) {
                // 별치가 있을 때
                kdc = splitForSpace[1].split("-")[0].replaceAll(numeric, "");
            } else {
                // 별치가 없을 때
                kdc = splitForSpace[0].split("-")[0].replaceAll(numeric, "");
            }
        } catch (Exception e) {
            logger.debug("message : {}", e.getMessage());
        }

        String genreNum = "";
        if (StringUtils.hasText(kdc)) {
            genreNum = Character.toString(kdc.charAt(0));
        }

        return genreNum;
    }

    public String getNaverHQImg(String naverImageUrl) {
        String imgUrl = naverImageUrl;
        if (naverImageUrl.contains("?")) {
            imgUrl = naverImageUrl.substring(0, naverImageUrl.indexOf("?"));
        }
        return imgUrl;
    }

    public String getRegNo(String regNo) {
        String regString = getRegString2(regNo);

        Pattern p = Pattern.compile("[^\\D]");
        Matcher m = p.matcher(regNo);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group());
        }
        while(sb.length() < (12-regString.length())) {
            sb.insert(0,"0");
        }
        sb.insert(0,regString.toUpperCase());
        return sb.toString();
    }

    public String getRegString2(String regNo) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(regNo);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group());
        }
        return sb.toString();
    }

}
