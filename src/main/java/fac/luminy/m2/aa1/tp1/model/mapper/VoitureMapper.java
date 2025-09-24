package fac.luminy.m2.aa1.tp1.model.mapper;

import fac.luminy.m2.aa1.tp1.model.dto.VoitureDTO;
import fac.luminy.m2.aa1.tp1.model.entity.Voiture;

public class VoitureMapper {

    public static VoitureDTO toDTO(Voiture voiture) {
        if (voiture == null) {
            return null;
        }
        return new VoitureDTO(voiture);
    }

}