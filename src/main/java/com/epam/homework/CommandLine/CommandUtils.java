package com.epam.homework.CommandLine;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class CommandUtils{
	
	public static void createFile(String name){
		
		try{
			Path file = Paths.get(name);			
			Files.createFile(file);
			
			System.out.println("File " + file + " was created.");
		} catch(AccessDeniedException ade){
			System.out.println("Access denied!");
		} catch(FileAlreadyExistsException fae){
			System.out.println("File already exist.");
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static void createDir(String name){
		
		try{
			Path dir = Paths.get(name);
			Files.createDirectory(dir);
			
			System.out.println("Directory " + dir + " was created.");
		} catch(AccessDeniedException ade){
			System.out.println("Access denied!");
		} catch(FileAlreadyExistsException fae){
			System.out.println("Directory already exist.");
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public static void rename(String begin, String aim){
		
		try{
			Path source = Paths.get(begin);
			Path target = Paths.get(aim);
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println(begin + " was renamed to " + aim);
		} catch (IOException ex){
			System.out.println("File or directory is not able to renamed.");
		}
	}
	
	public static void copy(String begin, String aim){
		
		try{
			Path source = Paths.get(begin).toAbsolutePath().normalize();
			Path target = Paths.get(aim).toAbsolutePath().normalize();
			
			Files.walkFileTree(source, new SimpleFileVisitor<Path>(){
				
				public FileVisitResult visitFile(Path file, BasicFileAttributes attr){
					
					try{
						Path medium = source.relativize(file);
						Path end = target.resolve(medium).normalize();
						Files.copy(file, end, StandardCopyOption.REPLACE_EXISTING);
					}catch (IOException ex){
						System.out.println("IOException in function visitFile");
					}
					return FileVisitResult.CONTINUE;
				}
				
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr){
					
					try{
						Path medium = source.relativize(dir);
						Path end = target.resolve(medium);
						Files.createDirectories(end);
					}catch (IOException e){
						System.out.println("IOException in function preVisitDirectory");
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}
			});
			
			System.out.println("Directory " + begin + " was copied to " + aim);
			
		} catch(NoSuchFileException nsfe){
			System.out.println("File or directory not found.");
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}


	public static void move(String begin, String aim){
		try{
			Path source = Paths.get(begin);
			Path target = Paths.get(aim);
			Files.move(source, target);
			
			System.out.println(begin + " was moved to " + aim);
		} catch (IOException ex){
			System.out.println("File or directory is not able to moved.");
		}
	}

	public static void delete(String name){
		
		try{
			Path target = Paths.get(name);
			//File file;
			
			if(!Files.isDirectory(target)){
				Files.delete(target);
				
				System.out.println("File " + name + " was deleted.");
			} else{
				Files.walkFileTree(target, new SimpleFileVisitor<Path>(){
					
					public FileVisitResult visitFile(Path file, BasicFileAttributes attr){
						
						try{
							Files.delete(file);
						}catch (IOException ex){
							System.out.println("IOException in function visitFile");
						}
						return FileVisitResult.CONTINUE;
					}
					
					public FileVisitResult postVisitDirectory(Path dir, IOException ex){
						
						try{
							Files.delete(dir);
						}catch (IOException e){
							System.out.println("IOException in function postVisitDirectory");
						}
						return FileVisitResult.CONTINUE;
					}
				});
				
				System.out.println("Directory " + name + " was deleted.");
			}
		} catch(NoSuchFileException nsfe){
			System.out.println("File or directory not found.");
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}

}