package com.example.ElearningAndExamJNPT.controller;

import com.example.ElearningAndExamJNPT.dto.VocabularyDTO;
import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import com.example.ElearningAndExamJNPT.service.impl.VocabularyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {
    @Autowired
    private VocabularyServiceImpl vocabularyService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllVocabulary() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", vocabularyService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody VocabularyDTO vocabularyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert vocabulary successfully", vocabularyService.save(vocabularyDTO))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Vocabulary> foundVocabulary = vocabularyService.getById(id);
        return foundVocabulary.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query vocabulary successfully", foundVocabulary)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find vocabulary with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody VocabularyDTO newVocabularyDTO) {
        Vocabulary updatedVocabulary = vocabularyService.getById(id)
                .map(vocabulary -> {
                    return vocabularyService.save(vocabulary);
                }).orElseGet(()->{
//                    newVocabularyDTO.setId(id);
                    return vocabularyService.save(newVocabularyDTO);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update vocabularyFolder successfully", updatedVocabulary)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Vocabulary> foundVocabulary = vocabularyService.getById(id);
        if (foundVocabulary.isPresent()) {
            vocabularyService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete vocabulary successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find vocabulary with id = " + id, "")
            );
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Vocabulary>> searchVocabularies(@RequestParam("query") String query){
        return ResponseEntity.ok(vocabularyService.searchVocabularies(query));
    }
}
