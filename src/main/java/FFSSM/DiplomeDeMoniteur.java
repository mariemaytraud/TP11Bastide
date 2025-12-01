/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiplomeDeMoniteur {

    private final int numeroDiplome;
    private final Plongeur possesseur;
    private final List<Embauche> emplois;

    public DiplomeDeMoniteur(Plongeur possesseur, int numeroDiplome) {
        this.numeroDiplome = numeroDiplome;
        this.possesseur = possesseur;
        this.emplois = new ArrayList<>();
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * @return l'employeur actuel de ce moniteur ou null s'il n'en a pas
     */
    public Club employeurActuel() {
      if (emplois.isEmpty()) {
          return null;
      }
      Embauche derniereEmbauche = emplois.get(emplois.size() - 1);
      if (derniereEmbauche.estTerminee()) {
          return null;
      }
      return derniereEmbauche.getEmployeur();
    }
    
    
    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * @param employeur le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {   
        // Si un employeur est actuellement actif, on termine son contrat la veille
        if (employeurActuel() != null) {
            for (int i = emplois.size() - 1; i >= 0; i--) {
                Embauche embauche = emplois.get(i);
                if (!embauche.estTerminee()) {
                    embauche.terminer(debutNouvelle.minusDays(1));
                    break; // on termine la plus récente non terminée
                }
            }
        }
        // On crée la nouvelle embauche et on l'ajoute
        Embauche nouvelle = new Embauche(debutNouvelle, this, employeur);
        emplois.add(nouvelle);
    }

    public List<Embauche> emplois() {
        return emplois;
    
    }

}
