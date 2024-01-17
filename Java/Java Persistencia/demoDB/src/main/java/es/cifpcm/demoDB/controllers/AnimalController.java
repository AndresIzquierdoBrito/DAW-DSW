package es.cifpcm.demoDB.controllers;

import es.cifpcm.demoDB.data.services.AnimalService;
import jakarta.persistence.NotNull;
import jakarta.persistence.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping
    public String save(@Valid @RequestBody Animal animal) {
        return animalService.save().toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        animalService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody AnimalUpdate) {
        animalService.update(id, );
    }

    @GetMapping("/{id}")
    public Animal getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return animalService.getById(id);
    }

    @GetMapping
    public Page<Animal> query(@Valid AnimalQuery) {
        return animalService.query();
    }
}
