package ma.yc.service;

import ma.yc.dto.PatientDto;
import ma.yc.dto.SalaireDto;
import ma.yc.model.Patient;
import ma.yc.model.Salaire;

import java.util.List;

public interface SalaireService {
    Boolean  addSalaire(List<SalaireDto> salaireDto);
    float calculateAverageSalary(String matricule);




}
