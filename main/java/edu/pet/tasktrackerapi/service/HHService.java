package edu.pet.tasktrackerapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Map;

public interface HHService {

    public ArrayList<Integer> idVacancies(ResponseEntity<String> responseHH, UriComponentsBuilder quereParams);

    public Map<String, Integer> countAllSkills(ArrayList<Integer> skillsId);
    public Map<String, Integer> SkillsKey(ResponseEntity<String> response);
    public Map<String, Integer> sortAndLimit(Map<String, Integer> map);


    }
