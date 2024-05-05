package edu.pet.tasktrackerapi.api.service;

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
            newSkillsKey.forEach((key, value) ->
                    skillsKey.merge(key, value, Integer::sum)
            );


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
        return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(100)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
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

    public Map<String, Integer> SkillsDescription(ResponseEntity<String> response) {
        log.info("Start collection SkillsDescription");


        Map<String, Integer> skillCountMap = new HashMap<>();
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBody());
            JsonNode descSkills = root.path("description");

            // Удаляем HTML тэги
            String description = descSkills.asText().replaceAll("<[^>]*>", "");
            // Разбиваем текст на слова и фразы
            String[] words = description.split("\\s+");

            // Пример списка ключевых навыков (должен быть предварительно заполнен или получен)
            Set<String> keySkills = new HashSet<>(Arrays.asList(
                    "java", "docker", "springboot", "data", "security", "git", "sql",
                    "hibernate", "spring", "spring framework",
                    "postgresql", "mysql", "rabbitmq", "ооп", "rest",
                    "quarkus", "maven", "nginx", "ci\\/cd", "junit5", "kafka",
                    "oгасlе", "gradle", "mongodb", "redis", "pitest", "scrum",
                    "agile", "tdd", "микросервисы", "soap", "kubenetes", "gitlab",
                    "aнглийский язык", "jdbc", "javascript", "postgres", "java se",
                    "java ee", "qa", "spring framework", "selenium", "microservices",
                    "websocket", "java core", "linux", "flyway", "swagger", "java core",
                    ""
            ));
            // Проверяем каждое слово
            for (String word : words) {
                // Нормализуем слово (приводим к общему регистру, удаляем знаки препинания и т.д.)
                String normalizedWord = word.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "");

                // Проверяем, является ли слово ключевым навыком
                if (keySkills.contains(normalizedWord)) {
                    // Если да, увеличиваем счетчик вхождений этого навыка
                    skillCountMap.computeIfAbsent(normalizedWord, k -> 0);
                    skillCountMap.put(normalizedWord, skillCountMap.get(normalizedWord) + 1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return skillCountMap;
    }


}
