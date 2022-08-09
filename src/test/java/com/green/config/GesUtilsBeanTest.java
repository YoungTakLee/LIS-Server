package com.green.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GesUtilsBeanTest {
    @Test
    void gesUtilBeanTest() throws Exception{
        GesUtilsBean gesUtilsBean = new GesUtilsBean();

        Assertions.assertEquals("8",gesUtilsBean.getGenreCode("향 814.6-김65ㅁ-7"));
        Assertions.assertEquals("8",gesUtilsBean.getGenreCode("814.6-김65ㅁ-7"));
        Assertions.assertEquals("",gesUtilsBean.getGenreCode(""));
        Assertions.assertEquals("",gesUtilsBean.getGenreCode(null));
        Assertions.assertEquals("https://bookthumb-phinf.pstatic.net/cover/000/273/00027317.jpg",gesUtilsBean.getNaverHQImg("https://bookthumb-phinf.pstatic.net/cover/000/273/00027317.jpg?abc"));
        Assertions.assertEquals("https://bookthumb-phinf.pstatic.net/cover/000/273/00027317.jpg",gesUtilsBean.getNaverHQImg("https://bookthumb-phinf.pstatic.net/cover/000/273/00027317.jpg"));

        for (int i = 0; i < 11; i++) {
            switch (i) {
                case 0:
                    Assertions.assertEquals("총류", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("총류", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 1:
                    Assertions.assertEquals("철학", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("철학", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 2:
                    Assertions.assertEquals("종교", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("종교", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 3:
                    Assertions.assertEquals("사회과학", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("사회과학", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 4:
                    Assertions.assertEquals("자연과학", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("자연과학", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 5:
                    Assertions.assertEquals("기술과학", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("기술과학", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 6:
                    Assertions.assertEquals("예술", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("예술", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 7:
                    Assertions.assertEquals("언어", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("언어", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 8:
                    Assertions.assertEquals("문학", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("문학", gesUtilsBean.genreNameFromKdc(i));
                    break;
                case 9:
                    Assertions.assertEquals("역사", gesUtilsBean.getGenre(String.valueOf(i)));
                    Assertions.assertEquals("역사", gesUtilsBean.genreNameFromKdc(i));
                    break;
                default:
                    break;

            }

        }
    }

}
