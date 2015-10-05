/*********************************************************************************
 * 
 *   Copyright 2014 BOUSSEJRA Malik Olivier, HALDEBIQUE Geoffroy, ROYER Johan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 ********************************************************************************/
package controllers.ajax.expert.requetes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import controllers.ajax.expert.requetes.calculs.HistoriqueDesEspeces;
import controllers.ajax.expert.requetes.calculs.MaillesParEspece;
import controllers.ajax.expert.requetes.calculs.CarteSomme;
import controllers.ajax.expert.requetes.calculs.CarteSommeBiodiversite;
import controllers.ajax.expert.requetes.calculs.ChronologieDUnTemoin;
import controllers.ajax.expert.requetes.calculs.HistogrammeDesImagos;
import controllers.ajax.expert.requetes.calculs.MaillesParPeriode;
import controllers.ajax.expert.requetes.calculs.TemoinsParPeriode;
import controllers.ajax.expert.requetes.nvCalculs.ListeDesTemoins;
import controllers.ajax.expert.requetes.nvCalculs.CarnetDeChasse;
import functions.excels.Excel;
import functions.excels.exports.HistoriqueDesEspecesExcel;
import functions.excels.exports.MaillesParEspeceExcel;
import functions.excels.exports.CarteSommeBiodiversiteExcel;
import functions.excels.exports.CarteSommeExcel;
import functions.excels.exports.ChronologieDUnTemoinExcel;
import functions.excels.exports.HistogrammeDesImagosExcel;
import functions.excels.exports.MaillesParPeriodeExcel;
import functions.excels.exports.TemoinsParPeriodeExcel;
import functions.excels.exports.ListeDesTemoinsExcel;
import functions.excels.exports.CarnetDeChasseExcel;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.expert.requetes.ajax.resultats.temoinsParPeriode;
import views.html.expert.requetes.ajax.resultats.histogrammeDesImagos;
import views.html.expert.requetes.ajax.resultats.carteSomme;
import views.html.expert.requetes.ajax.resultats.carteSommeBiodiversite;
import views.html.expert.requetes.ajax.resultats.maillesParEspece;
import views.html.expert.requetes.ajax.resultats.chronologieDUnTemoin;
import views.html.expert.requetes.ajax.resultats.historiqueDesEspeces;
import views.html.expert.requetes.ajax.resultats.maillesParPeriode;
import views.html.expert.requetes.ajax.resultats.emptyExcel;
import views.html.expert.requetes.ajax.resultats.exportExcel;
import models.UTMS;
import models.InformationsComplementaires;

public class Calculs extends Controller {
	
	private static boolean checkMaille(Map<String,String> info) {
		return true;
	}
	private static boolean checkTemoins(Map<String,String> info) {
		return true;
	}
	private static boolean checkDates(Map<String,String> info) {
		return true;
	}
	
	
	public static Result temoinsParPeriode() throws ParseException, IOException{
		Map<String,String> info = getData();
		List<TemoinsParPeriode> temoins = TemoinsParPeriode.calculeTemoinsParPeriode(info);
		TemoinsParPeriodeExcel tppe = new TemoinsParPeriodeExcel(info,temoins);
		tppe.writeToDisk();
		return ok(temoinsParPeriode.render(temoins,info,tppe.getFileName()));
	}
	public static Result histogrammeDesImagos() throws ParseException, IOException{
		Map<String,String> info = getData();
		HistogrammeDesImagos hdi = new HistogrammeDesImagos(info);
		HistogrammeDesImagosExcel hdie = new HistogrammeDesImagosExcel(info,hdi);
		hdie.writeToDisk();
		return ok(histogrammeDesImagos.render(hdi,info,hdie.getFileName()));
	}
	public static Result carteSomme() throws ParseException, IOException{
		Map<String,String> info = getData();
		CarteSomme cs = new CarteSomme(info);
		CarteSommeExcel cse = new CarteSommeExcel(info,cs);
		cse.writeToDisk();
		return ok(carteSomme.render(cs,info,cse.getFileName()));
	}
	public static Result carteSommeBiodiversite() throws ParseException, IOException{
		Map<String,String> info = getData();
		CarteSommeBiodiversite csb = new CarteSommeBiodiversite(info);
		CarteSommeBiodiversiteExcel csbe = new CarteSommeBiodiversiteExcel(info,csb);
		csbe.writeToDisk();
		return ok(carteSommeBiodiversite.render(csb,info,csbe.getFileName()));
	}
	public static Result chronologieDUnTemoin() throws ParseException, IOException{
		Map<String,String> info = getData();
		ChronologieDUnTemoin cdut = new ChronologieDUnTemoin(info);
		ChronologieDUnTemoinExcel cdute = new ChronologieDUnTemoinExcel(info,cdut);
		cdute.writeToDisk();
		return ok(chronologieDUnTemoin.render(cdut,info,cdute.getFileName()));
	}
	public static Result maillesParEspece() throws ParseException, IOException{
		Map<String,String> info = getData();
		MaillesParEspece mpe = new MaillesParEspece(info);
		MaillesParEspeceExcel mpee = new MaillesParEspeceExcel(info,mpe);
		mpee.writeToDisk();
		return ok(maillesParEspece.render(mpe,info,mpee.getFileName()));
	}
	public static Result historiqueDesEspeces() throws ParseException, IOException{
		Map<String,String> info = getData();
		HistoriqueDesEspeces hde = new HistoriqueDesEspeces(info);
		HistoriqueDesEspecesExcel hdee = new HistoriqueDesEspecesExcel(info,hde);
		hdee.writeToDisk();
		return ok(historiqueDesEspeces.render(hde,info,hdee.getFileName()));
	}
	public static Result maillesParPeriode() throws ParseException, IOException{
		Map<String,String> info = getData();
		MaillesParPeriode mpp = new MaillesParPeriode(info);
		MaillesParPeriodeExcel mppe = new MaillesParPeriodeExcel(info,mpp);
		mppe.writeToDisk();
		return ok(maillesParPeriode.render(mpp,info,mppe.getFileName()));
	}
	public static Result exportDonnees() throws ParseException, IOException{
		Map<String,String> info = getData();
		for (String str: info.keySet()) {
			System.out.println(str + " : " + info.get(str));
		}
		
		Excel excelData = null;
		String message = new String();
		if ((info.get("typeDonnees") != null) && (!info.get("typeDonnees").equals("null"))) {
			int typeStat = Integer.parseInt(info.get("typeDonnees"));
			StringBuilder temp = new StringBuilder();
			switch (typeStat) {
				case 10 : // Carte par espèce
					// Pour une période donnée, et par espèce, liste des premiers témoignages de chaque maille (maille, index, date, témoin(s)) avec carte du nombre de témoignages par mailles</td>
				break;

				case 20 : // Carte 20x20 par espèce
					// Pour une période donnée, et par espèce, liste des premiers témoignages de chaque maille UTM 20km X 20km (maille, index, date, témoin(s)) avec carte du nombre de témoignages par mailles</td>
				break;

				case 30 : // Carte somme
					// Pour une période donnée, et pour toutes les espèces du groupe ou du sous-groupe choisi, carte du nombre d'espèces témoignées par maille</td>
				break;

				case 40 : // Carte somme 20x20
					// Pour une période donnée, et pour toutes les espèces du groupe ou du sous-groupe choisi, carte du nombre d'espèces témoignées par maille UTM 20km X 20km</td>
				break;

				case 50 : // Liste des témoins
					// Liste alphabétique des témoins pour une période donnée</td>
					List<ListeDesTemoins> listeTemoins = ListeDesTemoins.calculeListeDesTemoins(info);
					excelData = new ListeDesTemoinsExcel(info,listeTemoins);
					
					temp.append("Témoignages du ");
					temp.append(info.get("jour1"));
					temp.append("/");
					temp.append(info.get("mois1"));
					temp.append("/");
					temp.append(info.get("annee1"));
					temp.append(" au ");
					temp.append(info.get("jour2"));
					temp.append("/");
					temp.append(info.get("mois2"));
					temp.append("/");
					temp.append(info.get("annee2"));
//					temp.append(" pour ");
//					temp.append(info.get("temoin"));
					
					message = temp.toString();
					break;

				case 60 : // Liste des espèces
					// Liste des espèces par ordre systématique pour une période donnée avec le nombre de mailles renseignées</td>
				break;

				case 70 : // Espèces par maille(s)
					// Pour une période donnée liste maille par maille des espèces renseignées avec le nombre des témoignages de ces espèces</td>
				break;

				case 80 : // Espèces par commune
					// Pour une période donnée liste par commune des espèces renseignées avec le nombre des témoignages de ces espèces</td>
				break;

				case 90 : // Espèces par département
					// Pour une période donnée liste par département des espèces renseignées avec le nombre des témoignages de ces espèces</td>
				break;

				case 100 : // Phénologie
					// Pour une période donnée, et par espèce, histogramme par décades (mois divisé en trois) du nombre de témoignages (quels que soient le nombre d'individus)</td>
				break;

				case 110 : // Carnet de Chasse
					// liste chronologique des différents lieux prospectés et, dans ces lieux, des différentes espèces observées avec détail des nombres et stade/sexe</td>
					Map<UTMS,ArrayList<InformationsComplementaires>> carnetDeChasse = CarnetDeChasse.calculeCarnetDeChasse(info);
					excelData = new CarnetDeChasseExcel(info,carnetDeChasse);
					
					temp.append("Carnet de chasse de ");
					
					message = temp.toString();

					break;

				case 120 : // Carte des observations
					// Pour un témoin donné, carte du nombre d'espèces différentes par mailles prospectées</td>
				break;

				case 130 : // Historique
					// Graphique par période de 20 ans du nombre de témoignages</td>
				break;

				// OLD STATS
				case 1001 :	// temoins par periode
					List<TemoinsParPeriode> temoins = TemoinsParPeriode.calculeTemoinsParPeriode(info);
					excelData = new TemoinsParPeriodeExcel(info,temoins);
					
					temp.append("Témoignages du ");
					temp.append(info.get("jour1"));
					temp.append("/");
					temp.append(info.get("mois1"));
					temp.append("/");
					temp.append(info.get("annee1"));
					temp.append(" au ");
					temp.append(info.get("jour2"));
					temp.append("/");
					temp.append(info.get("mois2"));
					temp.append("/");
					temp.append(info.get("annee2"));
					temp.append(" pour ");
					temp.append(info.get("temoin"));
					
					message = temp.toString();
					break;
				case 1002 :	// Historique des especes selectionnées
					HistoriqueDesEspeces hde = new HistoriqueDesEspeces(info);
					excelData = new HistoriqueDesEspecesExcel(info,hde);
				break;
				case 1003 : // Chronologie d'un témoin
					ChronologieDUnTemoin cdut = new ChronologieDUnTemoin(info);
					excelData = new ChronologieDUnTemoinExcel(info,cdut);
					message = "Chronologie d'un témoin";
				break;
				case 1004 : // Mailles par période
					MaillesParPeriode mpp = new MaillesParPeriode(info);
					excelData = new MaillesParPeriodeExcel(info,mpp);
					message = "Mailles par période";
				break;
				case 1005 : // Histogramme des stades
					HistogrammeDesImagos hdi = new HistogrammeDesImagos(info);
					excelData = new HistogrammeDesImagosExcel(info,hdi);
				break;
				case 1006 : // Mailles par espèces
					MaillesParEspece mpe = new MaillesParEspece(info);
					excelData = new MaillesParEspeceExcel(info,mpe);
					message = "Mailles par espèces";
				break;
				case 1007 : // Carte somme
					CarteSomme cs = new CarteSomme(info);
					excelData = new CarteSommeExcel(info,cs);
					message = "Carte somme";
				break;
				case 1008 : // Carte somme biodiversité
					CarteSommeBiodiversite csb = new CarteSommeBiodiversite(info);
					excelData = new CarteSommeBiodiversiteExcel(info,csb);
					message = "Carte somme biodiversité";
				break;
			}
		}
		if (excelData != null) {
			excelData.writeToDisk();
			return ok(exportExcel.render(message,excelData.getFileName()));
		} else {
			return ok(emptyExcel.render());
		}
	}
	
	/**
	 * Récupère les paramètres du formulaire
	 * et les charges dans une Map
	 * @return
	 */
	public static Map<String,String> getData(){
		DynamicForm df = DynamicForm.form().bindFromRequest();
		Map<String,String> info = new HashMap<String,String>();
		
		info.put("groupe", df.get("groupe"));
		info.put("sous_groupe", df.get("sous_groupe"));
		info.put("espece", df.get("espece"));
		
		info.put("stade", df.get("stade"));
		
		info.put("maille", df.get("maille"));
		info.put("mailles", df.get("mailles"));
		
		info.put("temoin", df.get("temoin"));
		
		info.put("jour1", df.get("jour1"));
		info.put("mois1", df.get("mois1"));
		info.put("annee1", df.get("annee1"));
		info.put("jour2", df.get("jour2"));
		info.put("mois2", df.get("mois2"));
		info.put("annee2", df.get("annee2"));
		
		info.put("typeDonnees", df.get("typeDonnees"));
		return info;
	}
	
	/**
	 * Renvoie la date 1 de le Map sous forme de Calendar
	 * @param info
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getDataDate1(Map<String,String> info) throws ParseException{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
		c.setTime(date_format.parse(info.get("jour1")+"/"+info.get("mois1")+"/"+info.get("annee1")));
		return c;
	}
	/**
	 * Renvoie la date 2 de le Map sous forme de Calendar
	 * @param info
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getDataDate2(Map<String,String> info) throws ParseException{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
		c.setTime(date_format.parse(info.get("jour2")+"/"+info.get("mois2")+"/"+info.get("annee2")));
		return c;
	}
}
