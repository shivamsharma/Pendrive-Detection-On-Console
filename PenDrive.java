/*This program is written by @Author Shivam Sharma(28shivamsharma@gmail.com).*
* This code has proprietary right to Author.*
* Read LICENCE to use it.*/


/**												PENDRIVE DETECTION ON CONSOLE															*/
/**The programme run on console but be conscious  about few facts
*1.Please specify full path address for COPY or MOVE otherwise typing 
only like C:\ can PASTE any where in C drive. 
*2.This programme has infinte running while loop which can give problem
in visualization if there is more than one drive.
3.If drive is pluggedin before runnnig it than it cann't be detected 
because no state change.
*4.More than one Drive can be plugged in.
*5.SWITCH ON FULL SCREEN MODE FOR BEST VIEW.
*6.Only FILE can handle the operation of 
COPY
DELETE
MOVE
*/


import java.awt.Desktop;
import java.io.*;
public class PenDrive
{
	public static void main(String arg[])throws Exception //Throwing the IO Exception on JVM
	{
		System.out.println("\n\t\t\tPlease First read the documentation of programme before using this.\n\n");
		String drive_name[]={"C","D","E","F","G","H","I","J","K"}; //Drives names that are present and more drives that are not
		File drive[]=new File[drive_name.length];
		boolean first_check[]=new boolean[drive_name.length];

		for(int i=0;i<drive_name.length;i++)
		{
			drive[i]=new File(drive_name[i]+":");
			first_check[i]=drive[i].canRead(); //Checking for the status of a each drive
		}

		boolean second_check[]=new boolean[drive_name.length];
		boolean terminator=false; //Termination of while loop
		System.out.println("....................Please switch to FULL SCREEN MODE for best view..............");
		System.out.println("....................Waiting for a drive to be inserted....................");

		while(true)
		{
			for(int i=0;i<drive_name.length;i++)
			{
				second_check[i]=drive[i].canRead(); //Checking the status of each drive after inserting a drive
				if(second_check[i]!=first_check[i])
				{
					if(second_check[i])
					{
						System.out.println("Found Drive\n");
						System.out.println("Drive:"+drive_name[i]+":\\");
						general(drive[i]);
					}
				}
			}
		}
	}
	
	public static void general(File ob)throws Exception
	{
		int z=1;
		String file_name[]=ob.list();
		File file[]=new File[file_name.length];
		System.out.println("....................CONTENTS....................\n");

		for(int j=0;j<file_name.length;j++)
		{
			file[j]=new File(ob.getPath()+"\\"+file_name[j]); //Listing all directories
			System.out.println(j+"."+file[j].getName());
		}
				System.out.println("\n....................FUNCTIONS....................\n");
				System.out.println("\nIf you want to EXIT press \"exit\":-");
				System.out.println("\nIf you want to OPEN any FILE or DIRECTORY type \"open\" :-");
				System.out.println("\nIf you want to COPY, DELETE , MOVE , RENAME any FILE \ntype \"copy\", \"delete\", \"move\", \"rename \" respectively :-\n");
				String command=System.console().readLine("");
			
		if(command.equals("exit"))
		{
			System.exit(0);
		}	

		String s=System.console().readLine("....................Enter the Serial no. of FILE or DIRECTORY on which you want to do the selected operation :-\n");
		int j=Integer.parseInt(s);
		String d=file[j].getPath();
		String e=file[j].getName();
		
		
		if(command.equals("open"))
		{
			open(d);
		}
		
		if(command.equals("copy"))
		{
			copy(d,e);
		}
		
		if(command.equals("delete"))
		{
			delete(d);
		}
		
		if(command.equals("move"))
		{
			move(d);
		}	
		
		if(command.equals("rename"))
		{
			rename(d,ob.getPath());
		}
	}
	
	
	//Open particular FILE with its default application.
	//With the help of DESKTOP class in awt Package.
	//Folder will open in console only......
	public static void open(String d)throws Exception
	{
						
						
						File store=new File(d);
						if(store.isFile())
						{
							if(store.exists())
							{ 
								if(Desktop.isDesktopSupported())
								{
									Desktop.getDesktop().open(store);
								}
								else System.out.println("....................FILE  or DIRECTORY doesn't exist....................");
							}
						}
						else
						{
							general(store);
						}
		
	}
	
	//Copying a particular FILE not a FOLDER
	public static void copy(String d,String e)throws Exception
	{
						System.out.println("....................Enter the complete destination path(using \\ ( path separator )), where you have to copy");
						String dpath=System.console().readLine(" ");	//Path where to copy
						FileInputStream fin=new FileInputStream(new File(d));
						File desfile=new File(dpath+e);			//Entered full pathname to reach the drive folder
						FileOutputStream fout=new FileOutputStream(desfile);
						int a=fin.read();
						System.out.println("....................Please wait copying may take some time....................");
						while(a!=-1)
						{
							fout.write(a);
							a=fin.read();
						}
						fin.close();
						fout.close();
						System.out.println("....................File has been copied....................");	
	}

	//Cut Paste
	public static void move(String d)throws Exception
	{
		
						
						System.out.println("....................Enter the complete destination path(using \\ ( path separator )), where you have to move the file");
						String dpath=System.console().readLine(" ");
						File t=new File(d);
						FileInputStream fin=new FileInputStream(t);
						File desfile=new File(dpath+t.getName());
						FileOutputStream fout=new FileOutputStream(desfile);
						int a=fin.read();
						System.out.println("....................Please wait copying may take some time....................");
						while(a!=-1)
						{
							fout.write(a);
							a=fin.read();
						}
						fout.close();
						fin.close();
						if(t.delete())
						System.out.println("....................FILE has been moved....................");
						else
						System.out.println("....................FILE has not been moved correctly....................");
						
		
	}

	//DELETE the particular FILE
	public static void delete(String d)throws Exception
	{
						
						File t=new File(d);
						if(t.isFile())
						{
							if(t.delete())
							System.out.println("....................file has been deleted....................");
							else 
							System.out.println("....................file you entered does not exist....................");
						}
						else
						{	
							boolean flag=true;
							String file_name[]=t.list();
							File file[]=new File[file_name.length];
							for(int j=0;j<file_name.length;j++)
							{
								file[j]=new File(t.getPath()+"\\"+file_name[j]);
								if(file[j].isDirectory())
								{
									delete(file[j].getPath());
								}
								else
								{
									if(!file[j].delete())
									{
										flag=false;
									}
								}
							}
							if(flag)
							{
								if(t.delete())
								System.out.println("....................Directory has been deleted....................");	
							}
							else
							{
								System.out.println("....................Directory has not been deleted....................");
							}
						}
	}
	
	//RENAME the FILE OR DIRECTORY
	public static void rename(String d,String f)throws Exception
	{
		
						File t=new File(d);
						System.out.println("....................Enter the new name....................");
						String new_name=System.console().readLine(" ");
						if(t.renameTo(new File(f+new_name)));
						System.out.println("....................Name has been changed....................");
		
	}
}