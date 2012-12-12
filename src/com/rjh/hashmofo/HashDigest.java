/**
 * 
 */
package com.rjh.hashmofo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Random;

import org.apache.commons.io.FileUtils;

/**
 * Builds and times a hash digest.
 * @author Raymond John Hill
 *
 */
public class HashDigest {

	//don't forget to add org.apache.commons.io.FileUtils to the build path.
	
	 byte[] theDigest;
	 private byte[] msgBytes;
	 private long executionTime;
	 private MessageDigest msgDigest;
	 private org.apache.commons.io.FileUtils fileUtils;
	
	/**
	 * Class instantiator.
	 */
	public HashDigest() { }
	
	/**
	 * For printing the execution time.
	 * @return the execution time.
	 */
	public long getExecutionTime() {
		return this.executionTime;
	}
	
	/**
	 * For printing the message digest.
	 * @return the message digest.
	 */
	public String getDigest() {
		return this.printHex(theDigest);
	}
	
	/**
	 * It creates a MessageDigest with the specified algorithm and then it
	 * creates a byte array from the specified file and passes that to createDigest().
	 * @param fileList the name of the file to get the digest of.
	 * @param algorithm the algorithm to use when producing the digest.
	 */
	public void getDigestOfFile(File theFile, String algorithm) {
		//uses our helper method to set the algorithm for our digest.
		this.createMsgDigestWithAlgorithm(algorithm);
		//creates us a nice place to keep out byte array.
		byte[] fileToDigest = null;
		try {
			//reads the whole file into a byte array. very convenient.
			fileToDigest = this.fileUtils.readFileToByteArray(theFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.createDigest(fileToDigest);		
	}
	
	/**
	 * Creates an instance of the MessageDigest with the given algorithm.
	 * @param algorithm the algorithm to be used.
	 */
	private void createMsgDigestWithAlgorithm(String algorithm) {
		try {
			//creates an instance with a specified algorithm. e.g.: MD5, SHA-512, etc.
			this.msgDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the digest creation and execution timing.
	 * @param bytesToDigest the byteArray to get the digest of.
	 */
	private void createDigest(byte[] bytesToDigest) {
		//grabs the start time.
		long timerStart = System.nanoTime();
		//creates the digest.
		this.theDigest = this.msgDigest.digest(bytesToDigest);
		//grabs the end time and subtracts the start time from it.
		this.executionTime = System.nanoTime() - timerStart;
	}
	
	/**
	 * This returns the hex value of the hash from the byte array value.
	 * @param byteArray the byte array to convert.
	 * @return the byteArray in a more readable format.
	 */
	private String printHex(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		//iterates over the byte array converting every byte.
        for (int i = 0; i < byteArray.length; i++) {
          sb.append(byteToHexString(byteArray[i]));
        }
        return sb.toString();
	}
	
	/**
	 * This helper method changes the byte value to hex.
	 * @param theByte the byte to convert.
	 * @return the converted byte.
	 */
	private String byteToHexString(byte theByte) {
		return Integer.toString((theByte & 0xff) + 0x100, 16).substring(1);
	}
}
