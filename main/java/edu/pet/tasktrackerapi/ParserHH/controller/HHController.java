package edu.pet.tasktrackerapi.ParserHH.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pet.tasktrackerapi.ParserHH.dto.VacancyDTO;
import edu.pet.tasktrackerapi.ParserHH.model.Vacancy;
import edu.pet.tasktrackerapi.ParserHH.model.VacancySkills;
import edu.pet.tasktrackerapi.ParserHH.service.HHService;
import edu.pet.tasktrackerapi.ParserHH.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Tag(name = "ParserHH", description = "Methods for parser hh")
public class HHController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpHeaders hhApiHeaders;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private HHService hhService;
    @Autowired
    private VacancyService vacancyService;

    @GetMapping(value = "/updateData", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getVacancyJava() {
        String searchQuery = "Java";
        String URL = "https://api.hh.ru/vacancies";

        UriComponentsBuilder queryParams = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("text", "NAME:(!" + searchQuery + ")")
                .queryParam("ored_clusters", "true")
                .queryParam("enable_snippets", "true")
                .queryParam("area", "113");

        String search = queryParams.toUriString();

        RestTemplate restTemplateWithHeaders = new RestTemplate();
        restTemplateWithHeaders.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().addAll(hhApiHeaders);
            return execution.execute(request, body);
        }));

        ResponseEntity<String> response = restTemplate.getForEntity(search, String.class);

        try {
            Vacancy vacancy = objectMapper.readValue(response.getBody(), Vacancy.class);

            // Создаем объект ModelAndView и добавляем в него данные для JSON-ответа и имя HTML-страницы
            ModelAndView modelAndView = new ModelAndView("HHStatistic");
            modelAndView.addObject("vacancyInfo", vacancy);

            return modelAndView;
        } catch (IOException e) {
            // В случае ошибки возвращаем соответствующий JSON-ответ
            ModelAndView errorModelAndView = new ModelAndView("errorPage");
            return errorModelAndView;
        }
    }

    @GetMapping(value = "/updateData", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Getting Vacansy start update data")
    public ResponseEntity getVacancyJavJson() {
        String searchQuery = "Java";
        String URL = "https://api.hh.ru/vacancies";

        UriComponentsBuilder queryParams = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("text", "NAME:(!" + searchQuery + ")")
                .queryParam("ored_clusters", "true")
                .queryParam("enable_snippets", "true")
                .queryParam("area", "113")
                .queryParam("page", 0);

        String search = queryParams.toUriString();

        // бесполезная ерунда
        RestTemplate restTemplateWithHeaders = new RestTemplate();
        restTemplateWithHeaders.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().addAll(hhApiHeaders);
            return execution.execute(request, body);
        }));

        ResponseEntity<String> response = restTemplate.getForEntity(search, String.class);

        ArrayList<Integer> allIds = hhService.idVacancies(response, queryParams);


        Map<String, Integer> skillsKey = hhService.countAllSkills(allIds);

        //  Map<String, Integer> skillsKey = allSkills.getKey();


        VacancyDTO VacancyDTO;
        try {

            VacancyDTO = objectMapper.readValue(response.getBody(), VacancyDTO.class);
            VacancyDTO.setProfession(searchQuery);

            vacancyService.saveProfession(VacancyDTO);
            vacancyService.saveSkills(skillsKey);
            return new ResponseEntity<>(VacancyDTO, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/getStatistics")
    @Operation(description = "Getting Vacansy all Skills info")
    public ResponseEntity<List<VacancySkills>> getStatistic(){

        return new ResponseEntity<>(vacancyService.getAllSkils(),HttpStatus.OK);
    }

    @GetMapping(value = "/getVacansy")
    @Operation(description = "Getting Vacansy all info")
    public ResponseEntity<List<Vacancy>> getInfo(){

        return new ResponseEntity<>(vacancyService.getallData(),HttpStatus.OK);
    }

}
