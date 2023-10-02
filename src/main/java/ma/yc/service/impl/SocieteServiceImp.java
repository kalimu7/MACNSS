package ma.yc.service.impl;

import ma.yc.Mapper.impl.PatientMapper;
import ma.yc.Mapper.impl.SocieteMapper;
import ma.yc.dao.SocieteDao;
import ma.yc.dao.impl.SocieteDaoImp;
import ma.yc.dto.PatientDto;
import ma.yc.dto.SocieteDto;
import ma.yc.model.Patient;
import ma.yc.model.Societe;
import ma.yc.service.SocieteService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
            if(p.numberWorkingdays > 3210){
                p.statusRetrait = "Peut Benefice";
                int Plus = p.numberWorkingdays - 3210;
                if(Plus > 0){
                    Plus = Plus/216;
                    p.pensionVeillesse = 50 + Plus;
                    p.salaireRetrait = p.salaire * (p.pensionVeillesse / 100);
                }else{
                    p.pensionVeillesse = 50;
                    p.salaireRetrait = p.salaire / 2;
                }

            }else{
                p.statusRetrait = "Ne Peut Pas Benefice";
                p.pensionVeillesse = 0;
                p.salaireRetrait = 0;
            }
            Patient patient =  this.patientMapper.toEntity(p);
            this.societeDao.ajouteEmployee(patient);
        }
        return false;
    }

    @Override
    public boolean accederDashboardSociete(String idSociete, String Password) {
        boolean state =  this.societeDao.accederSocieteDashboard(idSociete,Password);
        return state;
    }
}
