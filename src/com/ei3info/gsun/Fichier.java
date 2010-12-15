package com.ei3info.gsun;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/* A copier au début de gSun.java :
 * protected static File gsun;
 * protected static File temp;
 * protected static File temptxt;
 * A copier au début du onCreate dans gSun.java :
 * gsun = this.getApplicationContext().getDir("gsun", MODE_PRIVATE); 
 * new File(gsun, "defaut").mkdirs();
 * new File(gsun.getAbsolutePath() + File.separator + "defaut" + File.separator + "carac").mkdirs();
 */

public class Fichier {
	
	//Convert the date into a string format
	public static String conversionDate(int jour, int mois) {
		String sJour = String.valueOf(jour);
		if (jour < 10) {
			sJour = "0" + String.valueOf(jour);
		}
		return sJour + "/" + String.valueOf(mois);
	}
	
	//Convert the time into a string format
	public static String conversionHeure(int heure) {
		return String.valueOf(heure) + ":00";
	}
	
	//Save the picture and its information in temporary files when the picture has been taken.
	//Information are stored in this order : date, time, precision, state(in the sun/in the shade...)
	public static void saveTempFile(byte[] data) throws IOException {
		//Delete the content of temporary files in case it contains data of a previous picture
		gSun.temp = new File(gSun.gsun, "temp");
		if (gSun.temp.exists()) {
			gSun.temp.delete();
		}
		try {
			gSun.temp.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gSun.temptxt = new File(gSun.gsun, "temptxt");
		if (gSun.temptxt.exists()) {
			gSun.temptxt.delete();
		}
		try {
			gSun.temptxt.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Save the picture in a temporary file
		FileOutputStream fOut = new FileOutputStream(gSun.temp);
		BufferedOutputStream bufOut = new BufferedOutputStream(fOut);
		bufOut.write(data);
		bufOut.flush();
		bufOut.close();
		//Save information about the picture in another temporary file
		try {
			FileOutputStream fOut2 = new FileOutputStream(gSun.temptxt);
			OutputStreamWriter osw = new OutputStreamWriter(fOut2);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(conversionDate(gSun.temps.jour, gSun.temps.mois));
			bfw.newLine();
			bfw.write(conversionHeure(gSun.temps.heure));
			bfw.newLine();
			bfw.write(String.valueOf(gSun.precision));
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Returns a bitmap containing the picture stored in the temporary file
	public static Bitmap getTempPicture() {
		Bitmap picToDisplay = null;
		try {
			FileInputStream fIn = new FileInputStream(gSun.temp);
			BufferedInputStream bufIn = new BufferedInputStream(fIn);
			//Decodes the picture into a bitmap
			picToDisplay = BitmapFactory.decodeStream(bufIn);
			/*This can be used to display the bitmap in an ImageView for example. 
			ImageView ViewPhoto = new ImageView(this);
			ViewPhoto.setImageBitmap(photoAafficher);
			this.setContentView(ViewPhoto);*/
			bufIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picToDisplay;
	}
	
	//Save the state(in the sun/in the shade...) in the temporary file
	public static void saveState(String state) {
		//Gets information before deleting
		String[] listInfo = new String[3];
		try {
			FileInputStream fIn = new FileInputStream(gSun.temptxt);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader bfr = new BufferedReader(isr);
			listInfo[0] = bfr.readLine();
			listInfo[1] = bfr.readLine();
			listInfo[2] = bfr.readLine();
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Delete the content of the temporary file
		gSun.temptxt.delete();
		try {
			gSun.temptxt.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Save all new information
		try {
			FileOutputStream fOut = new FileOutputStream(gSun.temptxt);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(listInfo[0]);
			bfw.newLine();
			bfw.write(listInfo[1]);
			bfw.newLine();
			bfw.write(listInfo[2]);
			bfw.newLine();
			bfw.write(state);
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Returns the list of all directories of gsun
	public static ArrayList<String> getAllDir() {
		ArrayList<String> listDir = new ArrayList<String>();
		String[] vectorDir = gSun.gsun.list();
		for (String name : vectorDir) {
			listDir.add(name);
		}
		listDir.remove("temp");
		listDir.remove("temptxt");
		return listDir;
	}
	
	/*Creates a new directory in gsun. 
	 * The method returns false and the directory is not effectively created if
	 * a directory which has the same name already exists.
	 * */
	public static boolean newDir(String nameDir) {
		boolean success = true;
		ArrayList<String> listDir = getAllDir();
		for (String name : listDir) {
			if (nameDir == name) {
				success = false;
			}
		}
		if (success = true) {
			new File(gSun.gsun, nameDir).mkdirs();
		}
		return success;
	}
	
	//Returns the list of the name of all pictures stored in a directory 
	public static ArrayList<String> getAllPic(String nameDir) {
		ArrayList<String> listPic = new ArrayList<String>();
		File dir = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir);
		String[] vectorFiles = dir.list();
		for (String name : vectorFiles) {
			if (!name.endsWith("txt") && !name.equals("carac")) {
				listPic.add(name);
			}
		}
		return listPic;
	}
	
	/*Create a new pair of files for a picture, and copy data from temporary files.
	 * The method returns false and the "saving" is not effectively done in the following cases :
	 * - a file which has the same name already exists
	 * - the user chooses a name which ends with "txt"
	 * - the user chooses the name "carac"
	 * */
	public static boolean savePicture(String namePic, String nameDir) {
		boolean success = true;
		ArrayList<String> listPic = getAllPic(nameDir);
		for (String name : listPic) {
			if (namePic == name) {
				success = false;
			}
		}
		if (namePic.endsWith("txt")|namePic.equals("carac")) {
			success = false;
		}
		if (success = true) {
			File pic = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic);
			gSun.temp.renameTo(pic);
			File picInfo = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic + "txt");
			gSun.temptxt.renameTo(picInfo);
		}
		return success;
	}
	
	//Returns information about a picture. 
	//This methods needs the name of the directory which contains this picture.
	public static String[] getPicInfo(String namePic, String nameDir) {
		String[] info = new String[4];
		File picInfo = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic + "txt");
		try {
			FileInputStream fIn = new FileInputStream(picInfo);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader bfr = new BufferedReader(isr);
			info[0] = bfr.readLine();//date
			info[1] = bfr.readLine();//time
			info[2] = bfr.readLine();//precision
			info[3] = bfr.readLine();//state
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	//Returns the state of a picture. 
	//This methods needs the name of the directory which contains this picture.
	public static String getPicState(String namePic, String nameDir) {
		String info = "";
		File picInfo = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic + "txt");
		try {
			FileInputStream fIn = new FileInputStream(picInfo);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader bfr = new BufferedReader(isr);
			bfr.readLine();
			bfr.readLine();
			bfr.readLine();
			info = bfr.readLine();
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	//Set the state(in the sun/in the shade...) of a picture. 
	//This methods needs the name of the directory which contains this picture.
	public static void setPicState(String state, String namePic, String nameDir) {
		File picInfo = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic + "txt");
		//Gets information before deleting
		String[] listInfo = new String[3];
		try {
			FileInputStream fIn = new FileInputStream(picInfo);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader bfr = new BufferedReader(isr);
			listInfo[0] = bfr.readLine();
			listInfo[1] = bfr.readLine();
			listInfo[2] = bfr.readLine();
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Delete the content of the information file
		picInfo.delete();
		try {
			picInfo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Save all new information
		try {
			FileOutputStream fOut = new FileOutputStream(picInfo);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(listInfo[0]);
			bfw.newLine();
			bfw.write(listInfo[1]);
			bfw.newLine();
			bfw.write(listInfo[2]);
			bfw.newLine();
			bfw.write(state);
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Returns the picture stored in the file named "namePic" as a Bitmap
	public static Bitmap getPicture(String namePic, String nameDir) {
		Bitmap picToDisplay = null;
		File pic = new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + namePic);
		try {
			FileInputStream fIn = new FileInputStream(pic);
			BufferedInputStream bufIn = new BufferedInputStream(fIn);
			//Decodes the picture into a bitmap
			picToDisplay = BitmapFactory.decodeStream(bufIn);
			/*This can be used to display the bitmap in an ImageView for example. 
			ImageView ViewPhoto = new ImageView(this);
			ViewPhoto.setImageBitmap(photoAafficher);
			this.setContentView(ViewPhoto);*/
			bufIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picToDisplay;
	}
	
}
