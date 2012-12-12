package com.rjh.hashmofo;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * @author Raymond John Hill
 * 
 */
public class Main {

	public static void main(String[] args) {
		HashDigest hd = new HashDigest();
		HashList oldHashes = new HashList();

		//The array which will hold the names of
		//all of the files in the given directory		
		Object[] fileList;
		String theAlgorithm = "MD5";
		Scanner in = new Scanner(System.in);
		
		System.out.print("Directory to watch: ");
		String filePath = in.nextLine();
		System.out.print("Using algorithm: " + theAlgorithm + "\n");
		
		//MD5, SHA-1, SHA-256, SHA-384, SHA-512
		File directory = new File(filePath);
		fileList = FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).toArray();
		
		for(int i=0; i<fileList.length; i++) {
			hd.getDigestOfFile((File) fileList[i], theAlgorithm);
			oldHashes.addItem(fileList[i].toString(), hd.getDigest());
		}
		
		while(true) {
			fileList = FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).toArray();
			for(int i=0; i<fileList.length; i++) {
				//System.out.println(fileList[i]);
				hd.getDigestOfFile((File) fileList[i], theAlgorithm);
				if(!oldHashes.checkExistence(fileList[i].toString())) {
					System.out.println(System.currentTimeMillis() + "ms" + ": " + fileList[i].toString() + " has been added!");
					oldHashes.addItem(fileList[i].toString(), hd.getDigest());
				} else if(!hd.getDigest().equalsIgnoreCase(oldHashes.getItemValue(fileList[i].toString()))) {
					System.out.println(System.currentTimeMillis() + "ms" + ": " + fileList[i].toString() + " has changed!");
					System.out.println("Old hash: " + oldHashes.getItemValue(fileList[i].toString()));
					oldHashes.addItem(fileList[i].toString(), hd.getDigest());
					System.out.println("New hash: " + oldHashes.getItemValue(fileList[i].toString()));
				}
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
