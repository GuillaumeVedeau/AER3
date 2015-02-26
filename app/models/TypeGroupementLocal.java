package models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import play.db.ebean.Model;

/**
 * Created by Guillaume on 26/02/15.
 */

@SuppressWarnings("serial")
@Entity
public class TypeGroupementLocal extends Model{
    @Id
    public String type_groupement_local_intitule;

    public static Model.Finder<Integer,Groupe> find = new Model.Finder<Integer,Groupe>(Integer.class, Groupe.class);

    public TypeGroupementLocal (String intitule){
        type_groupement_local_intitule = intitule;
    }

}