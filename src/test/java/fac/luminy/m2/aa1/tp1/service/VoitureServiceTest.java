package fac.luminy.m2.aa1.tp1.service;


import fac.luminy.m2.aa1.tp1.model.TypeVoiture;
import fac.luminy.m2.aa1.tp1.model.dto.VoitureDTO;
import fac.luminy.m2.aa1.tp1.model.entity.Voiture;
import fac.luminy.m2.aa1.tp1.repository.VoitureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoitureServiceTest {

    @Mock
    private VoitureRepository voitureRepository;

    @InjectMocks
    private VoitureService voitureService;
    Voiture voiture1 = new Voiture();
    Voiture voiture2 = new Voiture();
    Voiture voiture3 = new Voiture();

    @BeforeEach
    public void setUp() {

        voiture1.setId(1L);
        voiture1.setModele("Model S");
        voiture1.setMarque("Tesla");
        voiture1.setAnnee(2020);
        voiture1.setType(TypeVoiture.SUV);
        voiture1.setChevauxFiscaux(10);
        voiture1.setPrix(80000.0);
        voiture1.setConsommation(15.0);
        voiture1.setCouleur("Red");


        voiture2.setId(2L);
        voiture2.setModele("Model 3");
        voiture2.setMarque("Tesla");
        voiture2.setAnnee(2021);
        voiture2.setType(TypeVoiture.BERLINE);
        voiture2.setChevauxFiscaux(8);
        voiture2.setPrix(50000.0);
        voiture2.setConsommation(10.0);
        voiture2.setCouleur("Blue");

        voiture3.setId(3L);
        voiture3.setModele("Micra");
        voiture3.setMarque("Nissan");
        voiture3.setAnnee(2021);
        voiture3.setType(TypeVoiture.SUV);
        voiture3.setChevauxFiscaux(8);
        voiture3.setPrix(55000.0);
        voiture3.setConsommation(10.0);
        voiture3.setCouleur("Blue");
    }

    @Test
    public void testGetVoitures() {
        // Arrange
        List<Voiture> voitures = Arrays.asList(voiture1, voiture2);
        when(voitureRepository.findByProprietaireNom("Doe")).thenReturn(voitures);

        // Act
        List<VoitureDTO> result = voitureService.recupererVoituresProprietaire("Doe");

        // Assert
        assertEquals(2, result.size());
        assertEquals(voiture1.getId(), result.getFirst().getId());
        assertEquals(voiture1.getModele(), result.getFirst().getModele());
        assertEquals(voiture1.getMarque(), result.getFirst().getMarque());
        assertEquals(voiture1.getAnnee(), result.getFirst().getAnnee());
        assertEquals(voiture1.getType(), result.getFirst().getType());
        assertEquals(voiture1.getChevauxFiscaux(), result.getFirst().getChevauxFiscaux());
        assertEquals(voiture1.getPrix(), result.getFirst().getPrix());
        assertEquals(voiture1.getConsommation(), result.getFirst().getConsommation());
        assertEquals(voiture1.getCouleur(), result.getFirst().getCouleur());

        assertEquals(voiture2.getId(), result.get(1).getId());
        assertEquals(voiture2.getModele(), result.get(1).getModele());
        assertEquals(voiture2.getMarque(), result.get(1).getMarque());
        assertEquals(voiture2.getAnnee(), result.get(1).getAnnee());
        assertEquals(voiture2.getType(), result.get(1).getType());
        assertEquals(voiture2.getChevauxFiscaux(), result.get(1).getChevauxFiscaux());
        assertEquals(voiture2.getPrix(), result.get(1).getPrix());
        assertEquals(voiture2.getConsommation(), result.get(1).getConsommation());
        assertEquals(voiture2.getCouleur(), result.get(1).getCouleur());
    }

    @Test
    public void testGetVoituresWithNullList() {
        // Arrange
        when(voitureRepository.findByProprietaireNom("Doe")).thenReturn(null);

        // Act
        List<VoitureDTO> result = voitureService.recupererVoituresProprietaire("Doe");

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void getVoitureParType(){
        //Mock
        List<Voiture> voitureBerline = Collections.singletonList(voiture2);
        when(voitureRepository.findByTypeIgnoreCase(TypeVoiture.BERLINE)).thenReturn(voitureBerline);

        //Act
        List<VoitureDTO> result = voitureService.rechercherVoituresParType(TypeVoiture.BERLINE);

        //Test
        assertEquals(1, result.size());
        assertEquals(TypeVoiture.BERLINE, result.getFirst().getType());
    }

    @Test
    public void getVoitureParPrix(){
        //Mock
        List<Voiture> voiture = Collections.singletonList(voiture2);
        when(voitureRepository.findByPrixBetween(45000,55000)).thenReturn(voiture);

        //Act
        List<VoitureDTO> result = voitureService.rechercheVoituresParPrix(50000);

        //Test
        assertEquals(1, result.size());
        assertTrue(result.getFirst().getPrix() <= 55000);
        assertTrue(result.getLast().getPrix() >= 45000);

    }

}
