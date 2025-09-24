package fac.luminy.m2.aa1.tp1.service;

import fac.luminy.m2.aa1.tp1.model.TypeVoiture;
import fac.luminy.m2.aa1.tp1.model.dto.VoitureDTO;
import fac.luminy.m2.aa1.tp1.model.entity.Voiture;
import fac.luminy.m2.aa1.tp1.model.mapper.VoitureMapper;
import fac.luminy.m2.aa1.tp1.repository.VoitureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class VoitureService {

    @Autowired
    private VoitureRepository voitureRepository;

    /**
     * Récupère la liste des voitures pour un propriétaire donné.
     *
     * @param nomProprietaire le nom du propriétaire dont les voitures doivent être récupérées
     * @return une liste de {@link VoitureDTO} représentant les voitures du propriétaire
     */

    public List <VoitureDTO> recupererVoituresProprietaire(String nomProprietaire){
        log.info("Demande de recuperation des voitures pour le proprietaire avec le nom {}", nomProprietaire);
        List<VoitureDTO> listeRetour;

        List<Voiture> listeDeVoiture = voitureRepository.findByProprietaireNom(nomProprietaire);
        if (listeDeVoiture == null) { return new ArrayList<>(); }

        listeRetour = listeDeVoiture
                .stream()
                .map(VoitureDTO::new) // ou voiture -> new VoitureDTO(voiture)
                .toList();

        // Retourner la liste des voitures
        log.info("{} voitures pour le proprietaire avec le nom {}",listeRetour.size(),nomProprietaire);
        return listeRetour;
    }

    public List<VoitureDTO> rechercherVoituresParType(TypeVoiture type) {
        return voitureRepository.findByTypeIgnoreCase(type)
                .stream()
                .map(VoitureMapper::toDTO)
                .toList();
    }

    public List<VoitureDTO> rechercheVoituresParPrix(int prix){
        List<VoitureDTO> listeRetour ;
        listeRetour = voitureRepository.findByPrixBetween(lowerLimit(prix),upperLimit(prix))
                .stream().
                map(VoitureMapper::toDTO).
                toList();

        return listeRetour;
    }
    private static double upperLimit(double value) {
        return value + (value * 10 / 100);
    }
    private static double lowerLimit(double value) {
        return value - (value * 10 / 100);
    }

}
