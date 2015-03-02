package controllers.admin;

import java.io.IOException;

import controllers.membre.SecuredMembre;
import models.*;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;

import javax.naming.NamingException;
import javax.persistence.PersistenceException;

import java.io.File;
import java.io.IOException;

import functions.UploadImage;
import play.mvc.Security;
import views.html.admin.organisationLocale.gererOrganisationLocale;

@Security.Authenticated(SecuredMembre.class)
public class GererOrganisationLocale extends Controller{

    public static Result main(){
        if (Admin.isAdminConnected()){
            return ok(gererOrganisationLocale.render());
        } else {
            return Admin.nonAutorise();
        }
    }

}
