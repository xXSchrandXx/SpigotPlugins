package de.xxschrandxx.bca.bungee.api.password;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;

import de.xxschrandxx.bca.bungee.api.SQLHandlerBungee;

/*

Author: Vik1395
Project: BungeeAuth

Copyright 2015

Licensed under Creative CommonsAttribution-ShareAlike 4.0 International Public License (the "License");
You may not use this file except in compliance with the License.

You may obtain a copy of the License at http://creativecommons.org/licenses/by-sa/4.0/legalcode

You may find an abridged version of the License at http://creativecommons.org/licenses/by-sa/4.0/
 */

/*
 * xAuth for Bukkit
 * Copyright (C) 2012 Lycano <https://github.com/lycano/xAuth/>
 *
 * Copyright (C) 2011 CypherX <https://github.com/CypherX/xAuth/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class PasswordHandler {

    public PasswordHandler(SQLHandlerBungee sqlhb) {
        t = sqlhb;
    }

    protected SQLHandlerBungee t;

    public int getMaxTypes() {
        return 7;
    }
    public boolean checkPassword(String checkPass, UUID uuid) throws SQLException {
        String realPass = t.getPassword(uuid);
        Integer type = t.getType(uuid);
        boolean pwCheck = false;
        String checkPassHash = "";
        String salt = "";
        
        if(type.equals(0))
        {
        	int saltPos = (checkPass.length() >= realPass.length() ? realPass.length() - 1 : checkPass.length());
            salt = realPass.substring(saltPos, saltPos + 12);
            String hash = whirlpool(salt + checkPass);
            checkPassHash = hash.substring(0, saltPos) + salt + hash.substring(saltPos);
            
            pwCheck = checkPassHash.equals(realPass);
        }
        
        else if(type.equals(1))
        {
        	checkPassHash = whirlpool(checkPass);
        	pwCheck = checkPassHash.equals(realPass);
        }
        
        else if(type.equals(2))
        {
        	checkPassHash = hash(checkPass, "MD5");
        	pwCheck = checkPassHash.equals(realPass);
        }
        
        else if(type.equals(3))
        {
        	checkPassHash = hash(checkPass, "SHA-1");
        	pwCheck = checkPassHash.equals(realPass);
        }
        
        else if(type.equals(4))
        {
        	checkPassHash = hash(checkPass, "SHA-256");
        	pwCheck = checkPassHash.equals(realPass);
        }
        
        else if(type.equals(5))
        {
        	String[] line = realPass.split("\\$");
            if (line.length > 3 && line[1].equals("SHA")) 
            {
                pwCheck = realPass.equals(getSaltedHash(checkPass, line[2]));
            }
        }
        
        else if(type.equals(6))
        {
        	PBKDF2Hash ph = new PBKDF2Hash();
        	try 
			{
				pwCheck = ph.validatePassword(checkPass, realPass);
			} 
			catch (InvalidKeySpecException | NoSuchAlgorithmException e)
			{
				System.out.println("Error in Validation");
				e.printStackTrace();
			}
			
        }
        
        else if(type.equals(7))
        {
        	int saltPos = (checkPass.length() >= realPass.length() ? realPass.length() - 1 : checkPass.length());
            String xsalt = realPass.substring(saltPos, saltPos + 12);
            pwCheck = realPass.equals(getXAuth(checkPass, xsalt));
        }

        return pwCheck;
    }

    // xAuth's custom hashing technique
    public String hash(String toHash) {
        String salt = whirlpool(UUID.randomUUID().toString()).substring(0, 12);
        String hash = whirlpool(salt + toHash);
        int saltPos = (toHash.length() >= hash.length() ? hash.length() - 1 : toHash.length());
        return hash.substring(0, saltPos) + salt + hash.substring(saltPos);
    }

    public String newHash(String toHash, Integer type) {
        String result="";
        if(type.equals(0))
        {
        	result = hash(toHash);
        }
        
        else if(type.equals(1))
        {
        	result = whirlpool(toHash);
        }
        
        else if(type.equals(2))
        {
        	result = hash(toHash, "MD5");
        }
        
        else if(type.equals(3))
        {
        	result = hash(toHash, "SHA-1");
        }
        
        else if(type.equals(4))
        {
        	result = hash(toHash, "SHA-256");
        }
        
        else if(type.equals(5))
        {
        	String salt = createSalt(16);
        	result = getSaltedHash(toHash, salt);
        }
        
        else if(type.equals(6))
        {
        	PBKDF2Hash ph = new PBKDF2Hash();
        	try 
        	{
				result = ph.generateStrongPasswordHash(toHash);
			} 
        	catch (Exception e) 
			{
				e.printStackTrace();
			}
        }
        
        else if(type.equals(7))
        {
        	String xsalt = createSalt(12);
        	result = getXAuth(toHash, xsalt);
        }

        return result;
    }

    private String hash(String toHash, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(toHash.getBytes());
            byte[] digest = md.digest();
            return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String whirlpool(String toHash) {
        Whirlpool w = new Whirlpool();
        byte[] digest = new byte[Whirlpool.DIGESTBYTES];
        w.NESSIEinit();
        w.NESSIEadd(toHash);
        w.NESSIEfinalize(digest);
        return Whirlpool.display(digest);
    }

    private String createSalt(int length){
        byte[] msg = new byte[40];
        new SecureRandom().nextBytes(msg);

        MessageDigest sha1 = null;
		try {
			sha1 = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        sha1.reset();
        byte[] digest = sha1.digest(msg);
        return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1,digest)).substring(0, length);
    }
    
    private String getSaltedHash(String message, String salt){
    	return "$SHA$" + salt + "$" + hash(hash(message, "SHA-256") + salt, "SHA-256");
    }

    private String getXAuth(String message, String salt) {
        String hash = whirlpool(salt + message).toLowerCase();
        int saltPos = (message.length() >= hash.length() ? hash.length() - 1 : message.length());
        return hash.substring(0, saltPos) + salt + hash.substring(saltPos);
    }
}
