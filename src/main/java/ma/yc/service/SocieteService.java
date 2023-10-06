package ma.yc.service;

import ma.yc.dto.PatientDto;
import ma.yc.dto.SocieteDto;

import java.util.List;

public interface SocieteService {
        boolean ajouteSociete(SocieteDto societeDto);

        boolean ajouteEmployee(List<PatientDto> patientDtos, String idSociete);

        void calculateRetraiteSalary();

        boolean accederDashboardSociete(String idSociete, String Password);

        List<PatientDto> SelectAllYourEmployee(String idSociete);

        public boolean changesociete(String matricule, String societeid);
        public boolean checkifemployeealredyexist(String matricule);
        public void augmenterNombreJourTravaille(String matricule, int NJT);

         public PatientDto checkYourRetirementSalary(String matricule);


}
