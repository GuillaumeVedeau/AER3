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
package functions.excels.exports;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import models.Espece;
import models.Fiche;
import models.FicheHasMembre;
import models.Groupe;
import models.InformationsComplementaires;
import models.Observation;
import models.StadeSexe;
import models.UTMS;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import controllers.ajax.expert.requetes.calculs.ChronologieDUnTemoin;
import functions.excels.Excel;

public class CarnetDeChasseExcel extends Excel {

	public CarnetDeChasseExcel(Map<String,String> info, Map<UTMS,ArrayList<InformationsComplementaires>> carnetDeChasse){
		super();
		Sheet sheet = wb.createSheet("Chronologie d'un témoin");

		String titre = "Chronologie de mes témoignages ";
		
		sheet.createRow(0).createCell(0).setCellValue(titre);
		sheet.addMergedRegion(new CellRangeAddress(
				0, //first row (0-based)
				0, //last row  (0-based)
				0, //first column (0-based)
				24  //last column  (0-based)
				));
		Row rowHead = sheet.createRow(1);
		rowHead.createCell(0).setCellValue("Maille");
		rowHead.createCell(1).setCellValue("Espèces observées");

		CellStyle cellStyleDate = wb.createCellStyle();
		CreationHelper creationHelper = wb.getCreationHelper();
		cellStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		int i = 2;
		
		for (Entry <UTMS,ArrayList<InformationsComplementaires>> mailleObs : carnetDeChasse.entrySet()){
			Row row = sheet.createRow(i);
			if (mailleObs.getKey()!=null){
			UTMS maille = mailleObs.getKey();
			row.createCell(0).setCellValue(maille.utm);
			}else{
				row.createCell(0).setCellValue("Maille inconnue ");
			}
			ArrayList<InformationsComplementaires> observations = mailleObs.getValue();


			int j=1;
			for (InformationsComplementaires observation : observations){
				
				if (observation.informations_complementaires_nombre_de_specimens != null){
				row.createCell(j).setCellValue(observation.informations_complementaires_observation.observation_espece.espece_nom 
						+ " ("+observation.informations_complementaires_nombre_de_specimens 
						+ " " + observation.informations_complementaires_stade_sexe 
						+ ")");
				}else{
				row.createCell(j).setCellValue(observation.informations_complementaires_observation.observation_espece.espece_nom 
						+ " ("+observation.informations_complementaires_stade_sexe 
						+ ")");
				}
				j++;
			}
			i++;
		}

		for(int j = 0; j<11 ; j++)
			sheet.autoSizeColumn(j);
	}

}
