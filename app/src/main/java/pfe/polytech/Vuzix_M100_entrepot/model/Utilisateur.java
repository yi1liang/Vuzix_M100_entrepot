package pfe.polytech.Vuzix_M100_entrepot.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pfe.polytech.Vuzix_M100_entrepot.Connexionasync;

/**
 * Cette classe correspond à un utilisateur des lunettes.
 *
 * Elle comprend son nom et le code barre qui lui ai associé
 */

public class Utilisateur  {

    /** Nom de l'utilisateur*/
    private String nom;
    /** Code barre associé à l'utilisateur*/
    private String codeBarre;
    /** Id de l'utilisateur (base de données)*/
    private int idUser;

    /**
     * Constructeur de la classe Utilisateur
     * @param nomUser Nom de l'utilisateur
     * @param CodeBarreUser Code barre associé à l'utilisateur
     *  //Todo: ajouter id user dans le constructeur
     */
    public Utilisateur( String id, String nomUser, String CodeBarreUser)
    {
        nom = nomUser;
        codeBarre = CodeBarreUser;
        idUser = Integer.parseInt(id);
    }


    /**
     * Vérifie dans la base de données si l'utilisateur existe via son code barre.
     * Si l'utilisateur existe, créer un objet utilisateur.
     * Retourne un booléen True si l'utilisateur existe, false sinon.
     * @param codeBarreLunette code barre vu par les lunettes
     * @return True si l'utilisateur existe, false sinon.
     * //TODO: ajouter l'id dans le JSON
     */
    public static Utilisateur verifieUtilisateur( String codeBarreLunette)
    {
        Connexionasync connexion = new Connexionasync();
        connexion.execute("http://bartholomeau.fr/indentification.php?cb="+codeBarreLunette);
        try {
            Log.d("utilisateur","message "+connexion.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(connexion.getResult().equals("false"))
        {
            return null;
        }
        else
        {
            //position de la premiere virgule
            List<String> listUser = Arrays.asList( connexion.getResult().split(","));
            Utilisateur user = new Utilisateur(listUser.get(0),listUser.get(1), listUser.get(2));
            return user;
        }
    }


    /**
     * Récupère le nom de l'utilisateur des lunettes
     * @return le nom de l'utilisateur des lunettes ( String)
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le code barre de l'utilisateur des lunettes
     * @return le code barre de l'utilisateur des lunettes ( String)
     */
    public String getCodeBarre() {
        return codeBarre;
    }

    /**
     * Récupère l'id de l'utilisateur des lunettes
     * @return l'id de l'utilisateur des lunettes ( int)
     */
    public int getIdUser() {
        return idUser;
    }



}
