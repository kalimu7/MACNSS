package ma.yc.dao;

public interface SalaireRetraitDao {
    float calculeSalaire(String matriculeEmployee);
    int changePensionvieillesse(String matriculeEmployee);
    boolean PossibiliteBenification(String matriculeEmployee);

    float calculeSalaireRetraite(String matriculeEmployee);

}
