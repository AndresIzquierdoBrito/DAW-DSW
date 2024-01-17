package es.cifpcm.demoDB.data.services;

import es.cifpcm.demoDB.controllers.Animal;
import es.cifpcm.demoDB.data.repositories.AnimalRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public Integer save(Animal) {
        Animal bean = new Animal();
        BeanUtils.copyProperties(, bean);
        bean = animalRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        animalRepository.deleteById(id);
    }

    public void update(Integer id, AnimalUpdate) {
        Animal bean = requireOne(id);
        BeanUtils.copyProperties(, bean);
        animalRepository.save(bean);
    }

    public Animal getById(Integer id) {
        Animal original = requireOne(id);
        return to(original);
    }

    public Page<AnimalDTO> query(AnimalQuery) {
        throw new UnsupportedOperationException();
    }

    private Animal to(Animal original) {
        Animal bean = new Animal();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Animal requireOne(Integer id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
