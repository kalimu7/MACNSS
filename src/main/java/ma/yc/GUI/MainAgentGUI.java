package ma.yc.GUI;

import ma.yc.core.EmailProvider;
import ma.yc.core.Print;
import ma.yc.core.Util;
import ma.yc.dto.*;
import ma.yc.enums.statusDossier;
import ma.yc.model.*;
import ma.yc.service.AgentService;
import ma.yc.service.DossierService;
import ma.yc.service.PatientService;
import ma.yc.service.impl.AgentServiceImpl;
import ma.yc.service.impl.DossierServiceImpl;
import ma.yc.service.impl.PatientServiceImpl;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAgentGUI implements DisplayGUI {

    private AgentService agentService;
    private DossierDto dossierDto = new DossierDto();
    private DossierService dossierService;
    private PatientService patientService;
    private boolean isAuthentificated = false;
    private Scanner scanner ;
    private AgentDto agentDto;
    public MainAgentGUI() {
        this.agentService = new AgentServiceImpl();
        this.dossierService = new DossierServiceImpl();
        this.agentDto = new AgentDto();
        this.patientService = new PatientServiceImpl();
    }




    void verifyCodeVerification(AgentDto agentDto){
        //if the authentification is successful then show the Agent dashboard
        Print.log("Send the verification code to your email");
        String code = Util.readString("Code",scanner);

        this.agentDto.codeVerification = code;
        boolean isCodeVerified = this.agentService.verifyCodeVerification(agentDto);
        if (isCodeVerified){
            this.agentDashboard();
        }else {
            Print.log("the Code is expered !");
        }
    }
    @Override
    public int displayMainOptions(Scanner scanner ) {
        this.scanner = scanner;
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("Authentification");
        String email = Util.readString("Email",scanner);
        agentDto.email = email;
        if (Util.verifyEmail(email)){
            String password = Util.readString("Password",scanner);
            agentDto.password = password;
            String code = this.agentService.authentifier(agentDto);
            if (!code.isEmpty()){
                System.out.println("Success: " + code);

                this.agentDto.codeVerification=code;
                if(this.agentService.insertCode(agentDto)){
                      try{
                          EmailProvider.sendMail(code, "code", agentDto.email);
                          verifyCodeVerification(agentDto);
                      }catch (Exception e) {
                          System.out.println("the code is not correct");
                      }


                }


            }else {
                Print.log("Ops");
            }
        }else {
            Print.log(email + " is not a valid email address.");
            Print.log("something went worng password or email are not correct .");

        }
        return 0;
    }




    private void agentDashboard( ) {
        Print.log("=== Agent Operation  ===");
        Print.log("1 - Add a new Dossier ");
        Print.log("2 - Change a Dossier status");
        Print.log("3 - Consult a Dossier ");
        Print.log("4 - Consult all Dossiers ");
        //go back to the main menu
        Print.log("5 - Go back to the main menu ");
        Print.log("0 - Exit ");

        // : make those option shoort by using HashMap
        HashMap<Integer , String> options = new HashMap<>();
        options.put(1,"Add a new Dossier");
        options.put(2,"Change a Dossier status");
        options.put(3,"Consult a Dossier");
        options.put(4,"Consult all Dossiers");
        options.put(5,"Go back to the main menu");
        options.put(0,"Exit");


        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                //add a new Dossier
                this.addDossier();
                break;
            case 2:
                //change a Dossier status
                this.changeDossierStatus();
                break;
            case 3:
                //consult a Dossier
                this.consultDossier();
                break;
            case 4:
                //consult all Dossiers
                this.consultAllDossiers();
                break;
            case 0:
                //go back to the main menu
                break;
            default:
                Print.log("Invalid choice");
                break;
        }
    }

    private void consultAllDossiers( ) {
        //:Test Just for test
        // : the consulterDossiers function must return List of DossierDto
        DossierDto dossierDto1 = new DossierDto();
        dossierDto1.patientDto = new PatientDto();
        dossierDto1.patientDto.matricule = Util.readString("Matricule",scanner);
        List<DossierDto> dossierDtoList = this.dossierService.consulterDossiers(dossierDto1);
        //:stream data and map it to be more nice to be seen
        dossierDtoList.stream().map(dossierDto -> {
            Print.log(dossierDto.toString());
            return dossierDto;
        });

    }

    private void consultDossier( ) {
        // : ask about the dossier id and show it
        // : ask about dossier :code_bar
         Print.log("Entre le code bar de votre dossier");
         String code_bar = Util.readString("Code bar",scanner);
         this.dossierService.consulterDossier();
    }

    private void changeDossierStatus( ) {
        // : ask about the dossier id and the new status and update it
        Print.log("Entre le num dossier de votre dossier");
        String code_bar = Util.readString("num_dossier",scanner);
        Print.log("Entre le nouveau status");
        String status = Util.readString("Status",scanner);
        System.out.println(status);
        DossierDto dossierDto = new DossierDto();
        dossierDto.numDossier = code_bar;

       boolean result = this.dossierService.suiviEtatDossier(dossierDto,status);
       if (result){
           Print.log("---------------**********--------------------");
           Print.log("\nthe status dossier changed successfully");
           Print.log("---------------**********--------------------");
           EmailProvider.sendMail("Your dossier Is changed to : "+ status, "code", agentDto.email);
           Print.log("Email sent successfully to user");

       }else {
           Print.log("---------------**********--------------------");
           Print.log("\nthe status is not changed");
           Print.log("---------------**********--------------------");
       }

    }

    private void addDossier( ) {
        Print.log("Entre le matricule de votre patient");
        String matricule = Util.readString("Matricule",scanner);
        PatientDto patientDto = new PatientDto();
        patientDto.matricule = matricule;
        if (!this.patientService.authentification(patientDto))
        {
            Print.log("Matricule does not exist");
            return;
        }
        dossierDto.patientDto =patientDto  ;
        String num_dossier = Util.readString("Num dossier",scanner);
        dossierDto.numDossier = num_dossier;
        dossierDto.status = statusDossier.En_attend;

        dossierDto.fichier = this.saveFicher();
        dossierDto.medicamentsDto = this.saveMedicament();
        dossierDto.analysesDto = this.saveAnalyses();
        dossierDto.scannersDto = this.saveScanners();
        dossierDto.radiosDto = this.saveRadios();
        dossierDto.visitesDto = this.saveVisits();

        Print.log(dossierDto.toString());
        this.dossierService.enregistrerDossier(dossierDto);

        Float totalRemoursement =  this.dossierService.totalRemoursement("0");
        Print.log("Total Remboursement : " + totalRemoursement);

    }

    private FichierDto saveFicher() {
        Print.log("ajouter fichier");
        this.dossierDto.fichier = new FichierDto();
        String dateDepot = Util.readString("dateDepot",scanner);
        String specialite = Util.readString("specialite",scanner);
        Print.log("entrer nemero de fichier");
        int nemeroFichier = scanner.nextInt();
        Print.log("Entre les total frais dossier ");
        float totalFraisDossier = scanner.nextFloat();
        dossierDto.fichier.numeroFicher = nemeroFichier;
        dossierDto.fichier.dateDepot = dateDepot;
        dossierDto.fichier.specialite = specialite;
        dossierDto.fichier.totalFraisDossier = totalFraisDossier;
        return dossierDto.fichier ;
    }

    private List<VisiteDto> saveVisits() {
        this.dossierDto.visitesDto = new ArrayList<>();
        Print.log("Voulez vous ajouter un autre visite ? (y/n)");
        String saveVisits = scanner.nextLine();
        boolean isVisites = false;
        if (saveVisits.equals("y")){
            isVisites = true;
        }
        while (isVisites){
            VisiteDto visiteDto = new VisiteDto();
            visiteDto.description = Util.readString("Description",scanner);
            Print.log("Entre le prix de la visite : ");
            visiteDto.prix =  scanner.nextFloat();
            Print.log("Entre l'id de la visite : ");
            visiteDto.visiteId = scanner.nextLong();
            //: add visite to list
            dossierDto.visitesDto.add(visiteDto);
            Print.log("Voulez vous ajouter un autre visite ? (y/n)");
            String choice = Util.readString("Choix",scanner);
            if (choice.equals("n")){
                break;
            }
        }
        return this.dossierDto.visitesDto;
    }

    private List<RadioDto> saveRadios() {
        this.dossierDto.radiosDto = new ArrayList<>();

        Print.log("Voulez vous ajouter un autre radio ? (y/n)");
        String saveRadois = scanner.nextLine();
        boolean isRadios = false;
        if (saveRadois.equals("y")){
            isRadios = true;
        }
        while (isRadios){
            RadioDto radioDto = new RadioDto();
            radioDto.description = Util.readString("Description",scanner);
            Print.log("Entre le prix de la radio : ");
            radioDto.prix =  scanner.nextFloat();
            Print.log("Entre l'id de la radio : ");
            radioDto.radioId = scanner.nextLong();
            //: add radio to list
            dossierDto.radiosDto.add(radioDto);
            Print.log("Voulez vous ajouter un autre radio ? (y/n)");
            String choice = Util.readString("Choix",scanner);
            if (choice.equals("n")){
                break;
            }
        }

        return this.dossierDto.radiosDto;
    }

    private List<ScannerDto> saveScanners() {
        this.dossierDto.scannersDto = new ArrayList<>();

        Print.log("Voulez vous ajouter un autre scanner ? (y/n)");
        String saveScanner = Util.readString("choice",scanner);
        boolean isScanners = false;
        if (saveScanner.equals("y")){
            isScanners= true;
        }
        while (isScanners){
            ScannerDto scannerDto = new ScannerDto();
            scannerDto.description = Util.readString("Description",scanner);
            Print.log("Entre le prix de la scanner : ");
            scannerDto.prix =  scanner.nextFloat();
            Print.log("Entre l'id de la scanner : ");
            scannerDto.scannerId = scanner.nextLong();
            //: add scanner to list
            dossierDto.scannersDto.add(scannerDto);
            Print.log("Voulez vous ajouter un autre scanner ? (y/n)");
            String choice = Util.readString("Choix",scanner);
            if (choice.equals("n")){
                break;
            }
        }
        return  this.dossierDto.scannersDto;
    }

    private List<AnalyseDto> saveAnalyses() {
        dossierDto.analysesDto = new ArrayList<>();
        List<AnalyseDto> analyseDtoList = new ArrayList<>();
        Print.log("Voulez vous ajouter un autre analyse ? (y/n)");
        String saveAnaylayse = scanner.nextLine();
        boolean isAnalyses = false;
        if (saveAnaylayse.equals("y")){
            isAnalyses = true;
        }
        while (isAnalyses){
            AnalyseDto analyseDto = new AnalyseDto();
            analyseDto.description = Util.readString("Description ",scanner);
            Print.log("Entre le prix de l'analyse : ");
            analyseDto.prix =  scanner.nextFloat();
            Print.log("Entre l'id de l'analyse : ");
            analyseDto.analyseId = scanner.nextLong();
            //: add analyse to list
            dossierDto.analysesDto.add(analyseDto);
            Print.log("Voulez vous ajouter un autre analyse ? (y/n)");
            String choice = Util.readString("Choix",scanner);
            if (choice.equals("n")){
                break;
            }
        }

        return analyseDtoList;
    }

    public List<MedicamentDto> saveMedicament(){
        List<MedicamentDto> medicamentDtoList = new ArrayList<>();
        MedicamentDto medicamentDto = new MedicamentDto();
        Print.log("Voulez vous ajouter un autre medicament ? (y/n)");
        scanner.nextLine();
        String addMedicament =scanner.nextLine();
        boolean isMedicamentSave = false;
        if (addMedicament.equals("y") ){
            isMedicamentSave = true;
        }
        while (isMedicamentSave){
            Print.log("Entre le code bar  de medicament");
            long codeBar = scanner.nextLong();
            Print.log("Entre la quantite de medicament");
            int quantite = scanner.nextInt();
            Print.log("entrer le prix de ce medicament");
            medicamentDto.prix = scanner.nextFloat();
            medicamentDto.codeBarre = codeBar;
            medicamentDto.quantite = quantite;


            //: add medicament to list
            medicamentDtoList.add(medicamentDto);

            Print.log("Voulez vous ajouter un autre medicament ? (y/n)");
            String choice = Util.readString("Choix",scanner);
            if (choice.equals("n")){
                break;
            }
        }
        return medicamentDtoList;
    }


}
