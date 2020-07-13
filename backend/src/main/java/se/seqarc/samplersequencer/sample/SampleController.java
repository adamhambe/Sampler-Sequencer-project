package se.seqarc.samplersequencer.sample;

import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import se.seqarc.samplersequencer.category.CategoryNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sample")
public class SampleController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<SampleDTO> getSampleById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(sampleService.getSampleById(id), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error fetching sample, full stacktrace follows: ", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sample not found");
        }
    }


    @GetMapping("/filteredsearch")
    public ResponseEntity<List<SampleDTO>> filteredSearch(@RequestParam String searchphrase, @RequestParam String category) {
        if (searchphrase.isEmpty() && category.isEmpty()) {
//            LOGGER.error("You must supply at least on off these params: searchprase or category");
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must supply at least on off these params: searchprase or category");
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } else if (!searchphrase.isEmpty() && category.isEmpty()) {
            return searchSample(searchphrase);
        } else if (searchphrase.isEmpty()) {
            return findSampleByCategory(category);
        } else {
            return findSampleByCategoryAndSearchphrase(searchphrase, category);
        }
    }

    private ResponseEntity<List<SampleDTO>> findSampleByCategoryAndSearchphrase(@RequestParam String searchphrase, @RequestParam String category) {
        try {
            return new ResponseEntity<>(sampleService.searchAndFilterByCategory(searchphrase, category), HttpStatus.OK);
        } catch (SampleNotFoundException e) {
            LOGGER.info("Filtered search: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No samples found with in category: " + category + ", with searchphrase: " + searchphrase);
        } catch (CategoryNotFoundException e) {
            LOGGER.info("Filtered search: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private ResponseEntity<List<SampleDTO>> findSampleByCategory(@RequestParam String category) {
        try {
            return new ResponseEntity<>(sampleService.getSamplesByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Filtered search: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private ResponseEntity<List<SampleDTO>> searchSample(@RequestParam String searchphrase) {
        try {
            return new ResponseEntity<>(sampleService.search(searchphrase), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Filtered search: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No samples found with searchphrase: " + searchphrase);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<SampleDTO> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("category") String category, @RequestParam("id") Long id) {
        try {
            SampleDTO sampleDTO = sampleService.uploadSample(file, name, category, id);
            LOGGER.info("Sample successfully uploaded: " + name);
            return new ResponseEntity<>(sampleDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error uploading sample, full stacktrace follows: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading file: " + e.getMessage());
        }
    }


}
