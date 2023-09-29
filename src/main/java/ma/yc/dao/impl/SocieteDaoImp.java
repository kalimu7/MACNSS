package ma.yc.dao.impl;

import ma.yc.dao.SocieteDao;
import ma.yc.model.Patient;
import ma.yc.model.Societe;

public class SocieteDaoImp implements SocieteDao {
    @Override
    public boolean ajouteSociete(Societe societe) {
        return false;
    }

    @Override
    public boolean ajouteEmployee(Patient patient) {
        return false;
    }

    @Override
    public void entreNobmreJourTraivaille(int NJT) {

    }

    @Override
    public void dimunierNombreJourTravaille(int NJT) {

    }
}
