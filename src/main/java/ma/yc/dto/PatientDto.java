package ma.yc.dto;

import ma.yc.model.Dossier;
import ma.yc.model.SalaireRetrait;
import ma.yc.model.Societe;

import java.util.List;

public class PatientDto {
    public String matricule;
    public String nom;
    public int cotisationSalaire;
    public String idsociete;
    public float salaire;
    public float salaireRetrait;
    public String statusRetrait;
    public int numberWorkingdays;

    public int pensionVeillesse;
    public SocieteDto societeDto;


}
