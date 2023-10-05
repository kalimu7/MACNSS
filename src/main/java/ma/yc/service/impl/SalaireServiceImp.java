package ma.yc.service.impl;

import ma.yc.Mapper.impl.SalaireMapper;
import ma.yc.dao.SalaireDao;
import ma.yc.dao.impl.SalaireDaoImp;
import ma.yc.dto.SalaireDto;
import ma.yc.model.Salaire;
import ma.yc.service.SalaireService;

import java.util.List;


public class SalaireServiceImp implements SalaireService {
    private SalaireDao salaireDao;
    private SalaireMapper salaireMapper;
    public SalaireServiceImp(){
        this.salaireDao = new SalaireDaoImp();
        this.salaireMapper = new SalaireMapper();
    }
    @Override
    public Boolean addSalaire(List<SalaireDto> salaireDtos) {

            for(SalaireDto S : salaireDtos){
                Salaire salaire = this.salaireMapper.toEntity(S);
                this.salaireDao.addSalaire(salaire);
            }
            return null;

    }





    @Override
    public float calculateAverageSalary(String matricule) {
        return 0;
    }
}
