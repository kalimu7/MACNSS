package ma.yc.dto;

import ma.yc.model.Patient;

import java.util.List;

public class SocieteDto {
    public String id;

    public String nom;
    public String description;

    public String password;
    public List<PatientDto> patientDtos;
}
