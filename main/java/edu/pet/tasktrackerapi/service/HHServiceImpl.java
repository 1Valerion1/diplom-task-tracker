package edu.pet.tasktrackerapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HHServiceImpl implements HHService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ArrayList<Integer> idVacancies(ResponseEntity<String> responseHH, UriComponentsBuilder queryParams) {
        log.info("Start collection id vacancies");

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Integer> vacancyIds = new ArrayList<>(2000);

        try {
            JsonNode rootNode = objectMapper.readTree(responseHH.getBody());
            int allPages = rootNode.path("pages").asInt();  // Получаем общее количество страниц


            for (int countPage = 0; countPage < allPages; countPage++) {

                queryParams.replaceQueryParam("page", countPage);  // Обновляем параметр "page" в URL
                String search = queryParams.toUriString();

                ResponseEntity<String> pageResponse = restTemplate.getForEntity(search, String.class);
                JsonNode itemsNode = new ObjectMapper().readTree(pageResponse.getBody()).path("items");

                if (itemsNode.isArray()) {

                    for (JsonNode item : itemsNode) {

                        int id = item.get("id").asInt();
                        //    System.out.println("ID "+id);
                        vacancyIds.add(id);

                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return vacancyIds;
    }


    @Override
    public Map<String, Integer> countAllSkills(ArrayList<Integer> skillsId) {
        log.info("Start collection countAllSkills");

        Map<String, Integer> skillsKey = new HashMap<>();
        int vacancyCount = 0;

        for (int id : skillsId) {
            vacancyCount++;

            String vacancyURL = "https://api.hh.ru/vacancies/" + id;
            ResponseEntity<String> response = restTemplate.getForEntity(vacancyURL, String.class);

            Map<String, Integer> newSkillsKey = SkillsKey(response);

            // Объединяем новые значения с существующими в skillsKey
            newSkillsKey.forEach((key, value) -> skillsKey.merge(key, value, Integer::sum));

            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        // Сортируем и ограничиваем количество записей до 100
        Map<String, Integer> sortedSkillsKey = sortAndLimit(skillsKey);
        //Map<String, Integer> sortedSkillsDesc = sortAndLimit(skillsDesc);


        return sortedSkillsKey;
    }

    private Map<String, Integer> sortAndLimit(Map<String, Integer> map) {
        return map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(100).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> SkillsKey(ResponseEntity<String> response) {
        log.info("Start collection SkillsKey");

        Map<String, Integer> skillCountMap = new HashMap<>();
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBody());
            JsonNode keySkills = root.path("key_skills");

            for (JsonNode skill : keySkills) {
                String skillName = skill.get("name").asText();
                skillCountMap.computeIfAbsent(skillName, k -> 0);
                skillCountMap.put(skillName, skillCountMap.get(skillName) + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return skillCountMap;
    }


}
