package com.example.bookfortune;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class BookService {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public BookDto getRandomBookFortune(int id) {
        // 검색 키워드 후보군 (id에 따라 다른 검색어로 네이버 API 호출)
        String[] keywords = {"위로", "행복", "인생", "용기", "철학"};
        String query = keywords[(id - 1) % keywords.length];
        
        String url = "https://openapi.naver.com/v1/search/book.json?query=" + query + "&display=10&start=1";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map<String, String>> items = (List<Map<String, String>>) response.getBody().get("items");
            
            if (items != null && !items.isEmpty()) {
                // 검색된 10개의 책 중 무작위로 하나를 선택해서 점괘로 제공
                int randomIndex = new Random().nextInt(items.size());
                Map<String, String> book = items.get(randomIndex);
                
                String title = book.get("title").replaceAll("<[^>]*>", "");
                String author = book.get("author").replaceAll("<[^>]*>", "");
                String description = book.get("description").replaceAll("<[^>]*>", "");
                
                if(description.length() > 150) {
                    description = description.substring(0, 150) + "...";
                }
                
                return new BookDto(title, author, description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new BookDto("기적의 시작", "작자미상", "오늘 당신의 하루에는 예상치 못한 행운이 찾아올 것입니다. 걱정하지 말고 나아가세요.");
    }
}