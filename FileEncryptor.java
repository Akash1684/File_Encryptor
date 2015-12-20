/* Project done as part of Summer Training at Hewlett-Packard Nodal Center, India.*/

import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor{
    
 private String algo;
 private String path;
 
    public FileEncryptor(String algo,String path) {
     this.algo = algo; //setting algo
     this.path = path;//setting file path
    }
    
    public void encrypt() throws Exception{
         //generating key
         byte k[] = "HignDlPs".getBytes();   
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher encrypt =  Cipher.getInstance(algo);  
         encrypt.init(Cipher.ENCRYPT_MODE, key);
         //opening streams
         FileOutputStream fos =new FileOutputStream(path+".enc");
         try(FileInputStream fis =new FileInputStream(path)){
            try(CipherOutputStream cout=new CipherOutputStream(fos, encrypt)){
                copy(fis,cout);
            }
         }
     }
     
     public void decrypt() throws Exception{
         //generating same key
         byte k[] = "HignDlPs".getBytes();   
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher decrypt =  Cipher.getInstance(algo);  
         decrypt.init(Cipher.DECRYPT_MODE, key);
         //opening streams
         FileInputStream fis = new FileInputStream(path);
         try(CipherInputStream cin=new CipherInputStream(fis, decrypt)){  
            try(FileOutputStream fos =new FileOutputStream(path.substring(0,path.lastIndexOf(".")))){
               copy(cin,fos);
           }
         }
      }
     
  private void copy(InputStream is,OutputStream os) throws Exception{
     byte buf[] = new byte[4096];  //4K buffer set
     int read = 0;
     while((read = is.read(buf)) != -1)  //reading
        os.write(buf,0,read);  //writing
  }
  
     public static void main (String[] args)throws Exception {
		 int pp=1;
		InputStreamReader nt=new InputStreamReader(System.in);
		BufferedReader bt=new BufferedReader(nt);
		FileOutputStream fw=new FileOutputStream("sample.txt"); //writing out in file

		while(pp==1)
		{   System.out.println("\nENTER THE DATA YOU WANT\n");
			String s=bt.readLine();
			byte ch[]=s.getBytes();
			for(int i=0;i<s.length();i++)
			fw.write((ch[i]));
			fw.write((' '));
			System.out.println("\nENTER 1.TO ENTER ANOTHER DATA 2.EXIT");
			pp=Integer.parseInt(bt.readLine());
		} 
      new FileEncryptor("DES/ECB/PKCS5Padding","sample.txt").encrypt();
      new FileEncryptor("DES/ECB/PKCS5Padding","sample.txt.enc").decrypt();
  }
}
