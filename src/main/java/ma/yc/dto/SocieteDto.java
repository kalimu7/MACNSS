package ma.yc.dto;

import ma.yc.model.Patient;

import java.util.List;

public class SocieteDto {
    public int id;

    public String nom;
    public String description;
    public List<PatientDto> patientDtos;
}
