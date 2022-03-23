package Weather.Weather.demo1.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import Weather.Weather.demo1.model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    private static Logger logger = Logger.getLogger(WeatherService.class.getName());
    private String OPENWEATHER_KEY;
    
    @PostConstruct
    public void init() {
        this.OPENWEATHER_KEY = System.getenv("OPENWEATHER_KEY");
    }
    public Weather saveData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather";

        url = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("q", city)
                .queryParam("appid", OPENWEATHER_KEY)
                .queryParam("units","metric")
                .toUriString();
        
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        JsonObject weatherData = null;

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            weatherData = reader.readObject();
        } catch (Exception ex) {
            logger.warning("cannot read website");
            System.exit(1);
        }
        
        Weather weather = new Weather();
        JsonArray weatherValues = weatherData.getJsonArray("weather");
        System.out.println((">>>>>>>>> weatherValues " + weatherValues.toString()));
        JsonObject insideWeatherValues = weatherValues.getJsonObject(0);
        weather.setDescription(insideWeatherValues.getString("description"));
        weather.setImage(insideWeatherValues.getString("icon"));

        JsonObject temperatureValues = weatherData.getJsonObject("main");
        weather.setTemperature(temperatureValues.getInt("temp"));
        weather.setMaxTemp(temperatureValues.getInt("temp_max"));
        weather.setMinTemp(temperatureValues.getInt("temp_min"));
        weather.setHumidity(temperatureValues.getInt("humidity"));

        return weather;
    }
}
