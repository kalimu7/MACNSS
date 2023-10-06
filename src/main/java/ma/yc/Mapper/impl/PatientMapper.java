package ma.yc.Mapper.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.core.Print;
import ma.yc.dto.DossierDto;
import ma.yc.dto.PatientDto;
import ma.yc.model.Patient;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PatientMapper implements Mapper<PatientDto , Patient> {
    @Override
    public Patient toEntity() {
        return null;
    }

    @Override
    public Patient toEntity(PatientDto patientDto) {


        Patient patient = new Patient();
        patient.setMatricule(patientDto.matricule);
        patient.setNom(patientDto.nom);
        patient.setIdSociete(patientDto.idsociete);
        patient.setSalaire(patientDto.salaire);
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(patientDto.datadeNaissance,simpleDateFormat) ;
        patient.setDatenaissance(date);

        patient.setCotisationSalaire(patientDto.cotisationSalaire);
        patient.setNombreJourtravaille(patientDto.numberWorkingdays);
        patient.setStatusretrait(patientDto.statusRetrait);
        patient.setPensionVeillesse(patientDto.pensionVeillesse);
        patient.setSalaireRetrait(patientDto.salaireRetrait);
        return patient;


    }

    @Override
    public PatientDto toDto() {
        return null;

    }

    @Override

    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.matricule = patient.getMatricule();
        patientDto.nom = patient.getNom();
        patientDto.idsociete = patient.getIdSociete();
        patientDto.salaire = patient.getSalaire();
        patientDto.cotisationSalaire = patient.getCotisationSalaire();
        patientDto.numberWorkingdays = patient.getNombreJourtravaille();
        patientDto.statusRetrait = patient.getStatusretrait();
        patientDto.pensionVeillesse = patient.getPensionVeillesse();
        patientDto.salaireRetrait = patient.getSalaireRetrait();
        return patientDto;
    }

    @Override
    public PreparedStatement toPreparedStatement(Patient patient, PreparedStatement preparedStatement) {
        return null;
    }
}
