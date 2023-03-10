package com.example.ElearningAndExamJNPT.controller;

import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.VocabularyFolder;
import com.example.ElearningAndExamJNPT.service.impl.VocabularyFolderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vocabularyFolders")
public class VocabularyFolderController {
    @Autowired
    private VocabularyFolderServiceImpl vocabularyFolderService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllVocabularyFolder() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", vocabularyFolderService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody VocabularyFolder vocabularyFolder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert vocabularyFolder successfully", vocabularyFolderService.save(vocabularyFolder))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<VocabularyFolder> foundVocabularyFolder = vocabularyFolderService.getById(id);
        return foundVocabularyFolder.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query vocabularyFolder successfully", foundVocabularyFolder)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find vocabularyFolder with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody VocabularyFolder newVocabularyFolder) {
        VocabularyFolder updatedVocabularyFolder = vocabularyFolderService.getById(id)
                .map(vocabularyFolder -> {
                    vocabularyFolder.setLevel(newVocabularyFolder.getLevel());
                    vocabularyFolder.setTitle(newVocabularyFolder.getTitle());
                    return vocabularyFolderService.save(vocabularyFolder);
                }).orElseGet(()->{
                    newVocabularyFolder.setId(id);
                    return vocabularyFolderService.save(newVocabularyFolder);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update vocabularyFolder successfully", updatedVocabularyFolder)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<VocabularyFolder> foundVocabularyFolder = vocabularyFolderService.getById(id);
        if (foundVocabularyFolder.isPresent()) {
            vocabularyFolderService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete vocabularyFolder successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find vocabularyFolder with id = " + id, "")
            );
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<VocabularyFolder>> searchVocabularyFolders(@RequestParam("query") String query){
        return ResponseEntity.ok(vocabularyFolderService.searchVocabularyFolders(query));
    }
}
