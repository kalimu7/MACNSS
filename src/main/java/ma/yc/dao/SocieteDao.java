package ma.yc.dao;

import ma.yc.model.Patient;
import ma.yc.model.Societe;

public interface SocieteDao {
    boolean ajouteSociete(Societe societe);

    boolean ajouteEmployee(Patient patient);

    void entreNobmreJourTraivaille(int NJT);

    void dimunierNombreJourTravaille(int NJT);


}
