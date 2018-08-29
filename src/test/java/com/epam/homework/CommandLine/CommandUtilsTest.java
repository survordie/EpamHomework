package com.epam.homework.CommandLine;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CommandUtilsTest {

    @Test
    public void createFileTest(){
        CommandUtils command = new CommandUtils();
        command.createFile("11.txt");
        Path file = Paths.get("11.txt");

        assertTrue(Files.exists(file));
    }

    @Test
    public void renameFileTest(){
        CommandUtils command = new CommandUtils();
        command.createFile("11.txt");
        command.rename("11.txt", "22.txt");
        Path file = Paths.get("11.txt");

        assertFalse(Files.exists(file));

        file = Paths.get("22.txt");
        assertTrue(Files.exists(file));

        command.delete("22.txt");
    }

    @Test
    public void createDirTest(){
        CommandUtils command = new CommandUtils();
        command.createDir("2");
        Path dir = Paths.get("2");

        assertTrue(Files.exists(dir));
    }

    @Test
    public void copyTest(){
        CommandUtils command = new CommandUtils();
        command.createDir("3");
        command.createDir("3/2");
        command.createFile("3/2/1.txt");
        command.copy("3", "4");

        Path dir = Paths.get("3/2");

        assertTrue(Files.exists(dir));

        command.delete("3");

        dir = Paths.get("3");

        assertFalse(Files.exists(dir));

        command.delete("4");

        dir = Paths.get("4");

        assertFalse(Files.exists(dir));
    }

    @Test
    public void deleteFileTest(){
        CommandUtils command = new CommandUtils();
        command.delete("11.txt");
        Path file = Paths.get("11.txt");

        assertFalse(Files.exists(file));
    }

    @Test
    public void deleteDir(){
        CommandUtils command = new CommandUtils();
        command.delete("2");
        Path dir = Paths.get("2");

        assertFalse(Files.exists(dir));
    }

    @Test
    public void moveTest(){
        CommandUtils command = new CommandUtils();
        command.createDir("5");
        command.createDir("5/2");
        command.createFile("5/2/1.txt");

        Path dir = Paths.get("5/2");

        assertTrue(Files.exists(dir));

        command.move("5", "6");

        dir = Paths.get("6/2");

        assertTrue(Files.exists(dir));

        command.delete("6");

        dir = Paths.get("6/2");

        assertFalse(Files.exists(dir));
    }
}
