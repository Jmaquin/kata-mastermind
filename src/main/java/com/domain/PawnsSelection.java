package com.domain;

import com.Exception.PawnsSelectionAlreadyCompleteException;

import java.util.ArrayList;


public class PawnsSelection extends ArrayList<Pawn> {
    /**
     * Unckecked exception is thrown because we don't want to add more than four elements and throw a checked exception is not possible when overriding add method
     *
     * @param pawn Pawn to be added to the list
     * @return true when pawn is added
     */
    public boolean add(Pawn pawn) {
        if (this.size() == 4)
            throw new PawnsSelectionAlreadyCompleteException("Pawns selection already complete!");
        else
            return super.add(pawn);
    }
}
