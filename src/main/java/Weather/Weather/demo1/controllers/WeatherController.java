package Weather.Weather.demo1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Weather.Weather.demo1.model.Weather;
import Weather.Weather.demo1.services.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;
    
    // @GetMapping("/weather/{city}")
    // public String showCityWeather(@PathVariable String city, Model model) {
    //     Weather cityWeather = weatherSvc.saveData(city);
    //     model.addAttribute("cityWeather", cityWeather);
    //     return "cityWeather";
    // }

    
    @GetMapping("/weather")
    public String showCityWeather(@RequestParam(name="cityWeather") String city, Model model) {
        Weather cityWeather = weatherSvc.saveData(city);
        model.addAttribute("cityWeather", cityWeather);
        return "cityWeather";
    }
}
