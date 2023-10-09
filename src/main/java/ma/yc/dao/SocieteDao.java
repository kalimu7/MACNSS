package ma.yc.dao;

import ma.yc.model.Patient;
import ma.yc.model.Societe;

import java.util.List;

public interface SocieteDao {
    boolean ajouteSociete(Societe societe);

    boolean ajouteEmployee(Patient patient);





    void augmenterNombreJourTravaille(String matricule, int NJT);

    boolean accederSocieteDashboard(String societeid,String password);

    boolean checkIfuserAlreadyexist(String matricule);

    boolean ChangePatienCompany(String matricule, String Societeid);

    List<Patient> SelectAllYourEmployee(String societeid);

    public List<Patient> selectEligible();

    public List<Patient> selectEligible1();


    public float calculateRetriatSalary(String matricule, int PV,float Salary);
    public Patient CheckYourRetiremntSalaire(String matricule);

}
