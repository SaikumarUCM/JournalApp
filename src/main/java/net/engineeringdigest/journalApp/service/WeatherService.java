package net.engineeringdigest.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import net.engineeringdigest.journalApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
@Slf4j
public class WeatherService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);

        if(weatherResponse!=null){
            return weatherResponse;
        }
        else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
            ResponseEntity<WeatherResponse> response = null;
            try {
                response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            } catch (Exception e) {
                log.error("Failed to fetch weather data from API");
            }
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 3000L);
            }
            return body;
        }

    }



/*
    Example for Post request by the External Application.
    public WeatherResponse getWeather(String city){
        String finalAPI= API.replace("API_KEY",apiKey).replace("CITY",city);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","value");
        User user= User.builder().userName("vipul").password("vipul").build();
        HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders);
        ResponseEntity<WeatherResponse> response =restTemplate.exchange(finalAPI, HttpMethod.POST,httpEntity, WeatherResponse.class);
        WeatherResponse body= response.getBody();
        return body;
    }
*/


}
