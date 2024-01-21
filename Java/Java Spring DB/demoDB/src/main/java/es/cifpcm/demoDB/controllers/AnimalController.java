package es.cifpcm.demoDB.controllers;

import es.cifpcm.demoDB.data.services.AnimalService;
import es.cifpcm.demoDB.models.Animal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;


    @GetMapping
    public List<Animal> getAllAnimals() {

        return animalService.getAllAnimals();
    }

    @PostMapping
    public String save(@Valid @RequestBody Animal animal) {

        return animalService.save(animal).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        animalService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Animal animal) {
        animalService.update(id, animal);
    }

    @GetMapping("/{id}")
    public Animal getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return animalService.getById(id);
    }

//   @GetMapping
//    public Page<Animal> query(@Valid Animal animal) {
//        return animalService.query(animal);
//    }
}
