package ma.yc.service.impl;

import ma.yc.Mapper.impl.PatientMapper;
import ma.yc.Mapper.impl.SocieteMapper;
import ma.yc.core.Print;
import ma.yc.dao.SocieteDao;
import ma.yc.dao.impl.SocieteDaoImp;
import ma.yc.dto.PatientDto;
import ma.yc.dto.SocieteDto;
import ma.yc.enums.statusRetraitment;
import ma.yc.model.Patient;
import ma.yc.model.Societe;
import ma.yc.service.SocieteService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;


public class SocieteServiceImp implements SocieteService {
    private SocieteMapper societeMapper;
    private SocieteDao societeDao;
    private PatientMapper patientMapper;
    public SocieteServiceImp(){
        this.societeMapper = new SocieteMapper();
        this.societeDao = new SocieteDaoImp();
        this.patientMapper = new PatientMapper();
    }
    @Override
    public boolean ajouteSociete(SocieteDto societeDto) {
        Societe societe = this.societeMapper.toEntity(societeDto);
        boolean returnvalue = this.societeDao.ajouteSociete(societe);
        if(returnvalue == true){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean ajouteEmployee(List<PatientDto> patientDtos, String idsociete) {
        for (PatientDto p: patientDtos){
            //todo : implement max 70 %;change the logic to 1320
                p.statusRetrait = statusRetraitment.ne_peut_pas_bénéficier.toString();
                p.pensionVeillesse = 0;
                p.salaireRetrait = 0;
                p.numberWorkingdays = 0;

            Patient patient =  this.patientMapper.toEntity(p);
            this.societeDao.ajouteEmployee(patient);
        }
        return false;
    }
    @Override
    public void calculateRetraiteSalary(){
        List<Patient> patients = this.societeDao.selectEligible();
        for (Patient p : patients){
            this.societeDao.calculateRetriatSalary(p.getMatricule(),p.getPensionVeillesse());
        }
    }

    @Override
    public boolean accederDashboardSociete(String idSociete, String Password) {
        boolean state =  this.societeDao.accederSocieteDashboard(idSociete,Password);
        return state;
    }

    @Override
    public List<PatientDto> SelectAllYourEmployee(String idSociete) {

        return null;
    }

    @Override
    public boolean changesociete(String matricule, String societeid) {
        return this.societeDao.ChangePatienCompany(matricule,societeid);
    }

    @Override
    public boolean checkifemployeealredyexist(String matricule) {
        return this.societeDao.checkIfuserAlreadyexist(matricule);
    }
    public void augmenterNombreJourTravaille(String matricule, int NJT) {
        this.societeDao.augmenterNombreJourTravaille(matricule,NJT);
    }
    public PatientDto checkYourRetirementSalary(String matricule){
        Patient patient = this.societeDao.CheckYourRetiremntSalaire(matricule);
        PatientDto patientDto = this.patientMapper.toDto(patient);
        return  patientDto;
    }

}

