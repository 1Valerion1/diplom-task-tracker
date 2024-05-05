package edu.pet.tasktrackerapi.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Map;

public interface HHService {

   // Метод для извлечения всех id
    // метод для вытаскивания навыков и ключевых
    // добавление в бд
    public ArrayList<Integer> idVacancies(ResponseEntity<String> responseHH, UriComponentsBuilder quereParams); // массив id


    public Map<String, Integer> countAllSkills(ArrayList<Integer> skillsId);


}
