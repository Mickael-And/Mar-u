package com.example.maru.model;

import androidx.annotation.NonNull;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Représente une salle de réunion.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Room {

    /**
     * Identifiant de la salle de réunion.
     */
    @Getter
    private UUID id = UUID.randomUUID();

    /**
     * Numéro de la salle.
     */
    @Getter
    @Setter
    private Integer number;

    /**
     * Nom de la salle de réunion.
     */
    @Getter
    @Setter
    private String name;

    /**
     * Couleur définissant la salle.
     */
    @Getter
    @Setter
    private int color;

    /**
     * Redéfinition du toString() pour permettre d'afficher le nom de la salle dans le spinner de création d'une réunion.
     *
     * @return le nom de la salle
     */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
