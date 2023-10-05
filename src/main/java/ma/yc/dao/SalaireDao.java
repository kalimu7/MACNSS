package ma.yc.dao;

import ma.yc.model.Salaire;

public interface SalaireDao {
    Boolean addSalaire(Salaire salaire);

    float calculateAverageSalary(String matricule);



}
