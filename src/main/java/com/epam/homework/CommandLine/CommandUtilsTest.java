package com.epam.homework.CommandLine;

public class CommandUtilsTest{
	
	public static void main(String[] args){
	try{
		String sw = args[0];
		
		CommandUtils command = new CommandUtils();
		
		switch (sw){
		case "create":
			command.createFile(args[1]);
			break;
			
		case "mkdir":
			command.createDir(args[1]);
			break;
			
		case "rename":
			command.rename(args[1], args[2]);
			break;
			
		case "copy":
			command.copy(args[1], args[2]);
			break;
			
		case "move":
			command.move(args[1], args[2]);
			break;
			
		case "delete":
			command.delete(args[1]);
			break;
			
		default:
			System.out.println("Unsupported operation.");
			break;
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}