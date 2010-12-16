package com.ei3info.gsun;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;

/*Some methods take a File into parameters, but some constructors are defined to
 * create new object File easily. 
 */

public class Fichier {
	
	//Create an object File with the path : gsun/nameDir
	public static File File(String nameDir) {
		return new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir);
	}
	//Create an object File with the path : gsun/nameDir/nameFile
	public static File File(String nameDir, String nameFile) {
		return new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + nameFile);
	}
	//Create an object File with the path : gsun/nameDir/nameFile/namePic
	public static File File(String nameDir, String nameFile, String namePic) {
		return new File(gSun.gsun.getAbsolutePath() + File.separator + nameDir + File.separator + nameFile + File.separator + namePic);
	}
	
	//Effectively creates the File file. 
	public static void create(File file) {
		try {file.createNewFile();} catch (IOException e) {e.printStackTrace();}
	}
	
	//Convert a Drawable into a bitmap. A Drawable can be for example :
	//getResources().getDrawable(R.drawable.picture)
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bmp = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
    	drawable.setBounds(0, 0, 200, 200);
        Canvas canvas = new Canvas(bmp);
        drawable.draw(canvas);
        return bmp;
	}
	
	//Convert a bitmap into byte array
	public static byte[] bitmapToByte(Bitmap bmp) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
	}
	
	//General method to get a picture stored in a file as a Bitmap
	public static Bitmap getPicture(File file) {
		Bitmap picToDisplay = null;
		try {
			FileInputStream fIn = new FileInputStream(file);
			BufferedInputStream bufIn = new BufferedInputStream(fIn);
			//Decodes the picture into a bitmap
			picToDisplay = BitmapFactory.decodeStream(bufIn);
			/*This can be used to display the bitmap in an ImageView for example :
			ImageView ViewPhoto = new ImageView(this);
			ViewPhoto.setImageBitmap(photoAafficher);
			this.setContentView(ViewPhoto);*/
			bufIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picToDisplay;
	}
	
	//General method to save an array of bytes into a file
	public static void setPicture(File file, byte[] data) {
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			BufferedOutputStream bufOut = new BufferedOutputStream(fOut);
			bufOut.write(data);
			bufOut.flush();
			bufOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Save an array of bytes into the "carac" folder of the nameDir directory
	public static void setCaracPicture(String nameDir, byte[] data) {
		int count = File(nameDir, "carac").list().length;
		setPicture(File(nameDir, "carac", String.valueOf(count+1)), data);
	}
	
	//General method to get information about a picture stored in a file
	public static String[] getInfo(File file) {
		String[] listInfo = new String[4];
		try {
			FileInputStream fIn = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fIn);
			BufferedReader bfr = new BufferedReader(isr);
			listInfo[0] = bfr.readLine();//date
			listInfo[1] = bfr.readLine();//time
			listInfo[2] = bfr.readLine();//precision
			listInfo[3] = bfr.readLine();//state(in the sun, in the shade...)
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listInfo;
	}
	
	//General method to save information in a file
	public static void setInfo(File file, String date, String time, String precision, String state) {
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			BufferedWriter bfw = new BufferedWriter(osw);
			bfw.write(date);
			bfw.newLine();
			bfw.write(time);
			bfw.newLine();
			bfw.write(precision);
			bfw.newLine();
			bfw.write(state);
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//General method to get the state(in the sun/in the shade...) of a picture in a file
	public static String getState(File file) {
		return getInfo(file)[3];
	}
	
	//General method to save the state(in the sun/in the shade...) of a picture in a file
	public static void setState(File file, String state) {
		//Gets information before deleting
		String[] listInfo = getInfo(file);
		//Delete the content of the temporary file
		file.delete();
		create(file);
		//Save all new information
		setInfo(file, listInfo[0], listInfo[1], listInfo[2], state);
	}
	
	//Save the picture and its information in temporary files when the picture has been taken.
	public static void setTempPicture(byte[] data) {
		//Delete the content of temporary files in case it contains data of a previous picture
		gSun.temp = File("temp");
		if (gSun.temp.exists()) {
			gSun.temp.delete();
		}
		create(gSun.temp);
		gSun.temptxt = File("temptxt");
		if (gSun.temptxt.exists()) {
			gSun.temptxt.delete();
		}
		create(gSun.temptxt);
		//Save the picture in a temporary file
		setPicture(gSun.temp, data);
		//Save information about the picture in another temporary file
		setInfo(gSun.temptxt, conversionDate(gSun.temps.jour, gSun.temps.mois), conversionHeure(gSun.temps.heure), String.valueOf(gSun.precision), "");
	}
	
	//Returns a bitmap containing the picture stored in the temporary file
	public static Bitmap getTempPicture() {
		return getPicture(gSun.temp);
	}
	
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
	
	/*Creates a new directory in gsun, and its sub-directory "carac". 
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
			File(nameDir).mkdirs();
			File(nameDir, "carac").mkdirs();
		}
		return success;
	}
	
	//Returns the list of the name of all pictures stored in a directory 
	public static ArrayList<String> getAllPic(String nameDir) {
		ArrayList<String> listPic = new ArrayList<String>();
		String[] vectorFiles = File(nameDir).list();
		for (String name : vectorFiles) {
			if (!name.endsWith("txt") && !name.equals("carac")) {
				listPic.add(name);
			}
		}
		return listPic;
	}
	
	//Returns the default name of a file
	public static String getDefaultName() {
		return "gSun_" + String.valueOf(gSun.temps.jour) + String.valueOf(gSun.temps.mois) + "_" + String.valueOf(gSun.temps.heure) + "_" + String.valueOf(gSun.fileCount+1);
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
		if (nameDir.equals("defaut")) {
			gSun.fileCount += 1;
		}
		return success;
	}
	
	//Returns an ArrayList of all characterization pictures of a directory as Bitmaps
	public static ArrayList<Bitmap> getAllCaracPic(String nameDir) {
		ArrayList<Bitmap> listPic = new ArrayList<Bitmap>();
		for (String name : File(nameDir, "carac").list()) {
			listPic.add(getPicture(File(nameDir, "carac", name)));
		}
		return listPic;
	}
	
}
