package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class EspeceIsInGroupementLocal {
    @Id
    public Integer espece_is_in_groupement_id;
    @NotNull
    @ManyToOne
    public Espece espece;
    @NotNull
    @ManyToOne
    public Groupe groupe;

    public static Model.Finder<Integer,EspeceIsInGroupementLocal> find = new Model.Finder<Integer,EspeceIsInGroupementLocal>(Integer.class, EspeceIsInGroupementLocal.class);

    public EspeceIsInGroupementLocal(Espece espece, Groupe groupe) {
        this.espece=espece;
        this.groupe=groupe;
    }

}
