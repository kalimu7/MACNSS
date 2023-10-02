package ma.yc.service;

import ma.yc.dto.PatientDto;
import ma.yc.dto.SocieteDto;

import java.util.List;

public interface SocieteService {
        boolean ajouteSociete(SocieteDto societeDto);

        boolean ajouteEmployee(List<PatientDto> patientDtos, String idSociete);

        boolean accederDashboardSociete(String idSociete,String Password);




}
