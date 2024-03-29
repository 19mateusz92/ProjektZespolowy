/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pwr.Package;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Michał Sypniewski
 */

//*****************************************\\
/*
    Uwaga, bo to ważne. Jako argument konstruktora
podajemy nazwę pliku zip. Następnie poprzez metodę
addFile dodajemy do zipa kolejne pliki (Argument to
ścieżka do pliku). Jak już zakończymy pracę z zipem
zamykamy go metodą "closeZip".
*/
//*****************************************//


public class ZipPacker {
    
    private File file;
    private ZipOutputStream output;
    private ZipEntry zipEntry;
    
    
    public ZipPacker(String directory)
    {
    	Path path = Paths.get(directory);
    	String fileName=path.getFileName().toString();
    	String folder = directory.replaceAll(fileName, "");
    	fileName=fileName.replaceAll(" ","");
    	fileName=fileName.replaceAll("[^A-Za-z0-9]", "_");
    	fileName=fileName.substring(0,fileName.length()-4)+".zip";
    	directory=folder+fileName;
        file=new File(directory);
        if(file.exists())
        {
        	file.delete();
        }
        try {
            output=new ZipOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipPacker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addFile(String directory) throws IOException
    {
    	Path path = Paths.get(directory);
    	String ZipDirectory = path.getFileName().toString();
        zipEntry = new ZipEntry(ZipDirectory);
        byte[] buffer = new byte[1024];
        int bytesRead=0;
        CRC32 crc = new CRC32();        
        File tempFile = new File(directory);
        if(tempFile.exists()){
        BufferedInputStream bufferedInput = new BufferedInputStream(new FileInputStream(tempFile));
        
        
        crc.reset();
        while ((bytesRead = bufferedInput.read(buffer))!=-1){
                crc.update(buffer, 0, bytesRead);
        }
        bufferedInput.close();
        bufferedInput = new BufferedInputStream(new FileInputStream(tempFile));
        zipEntry.setMethod(ZipEntry.STORED);
        zipEntry.setCompressedSize(tempFile.length());
        zipEntry.setSize(tempFile.length());
        zipEntry.setCrc(crc.getValue());
        
        output.putNextEntry(zipEntry);
                    
        while((bytesRead = bufferedInput.read(buffer))!=-1)
        {
            output.write(buffer,0,bytesRead);
        }
        bufferedInput.close();

        output.closeEntry();}

    }
    
    
    public void closeZip()
    {
        try {
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(ZipPacker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
