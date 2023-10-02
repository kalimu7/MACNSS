package ma.yc.service;

import ma.yc.dto.PatientDto;

public interface SalaireRetariteService {
    void increaseNumberWorkingDay();
    void decreaseNumberWorkingDay();

    PatientDto  CheckYourRetiringAccount(String matricule);


}
