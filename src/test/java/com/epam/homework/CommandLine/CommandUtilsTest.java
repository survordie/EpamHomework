package com.epam.homework.CommandLine;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class CommandUtilsTest {

    @Test
    public void createFileTest() {
        CommandUtils command = new CommandUtils();
        command.createFile("11.txt");
        Path file = Paths.get("11.txt");

        assertTrue(Files.exists(file));
    }

    @Test
    public void renameFileTest() {
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
    public void createDirTest() {
        CommandUtils command = new CommandUtils();
        command.createDir("2");
        Path dir = Paths.get("2");

        assertTrue(Files.exists(dir));
    }

    @Test
    public void copyTest() {
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
    public void deleteFileTest() {
        CommandUtils command = new CommandUtils();
        command.delete("11.txt");
        Path file = Paths.get("11.txt");

        assertFalse(Files.exists(file));
    }

    @Test
    public void deleteDir() {
        CommandUtils command = new CommandUtils();
        command.delete("2");
        Path dir = Paths.get("2");

        assertFalse(Files.exists(dir));
    }

    @Test
    public void moveTest() {
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

    @Test
    public void showTest() {
        CommandUtils command = new CommandUtils();
        command.createDir("5");
        command.createDir("5/2");
        command.createFile("5/2/2.txt");
        command.createFile("5/2/1.txt");
        command.createFile("5/2/3.txt");
        command.createFile("5/2/4.txt");

        command.showDir("5");

        command.delete("5");
    }

    @Test
    public void zipUnZipTest() {
        CommandUtils command = new CommandUtils();

        command.createFile("1.txt");

        Path file = Paths.get("1.txt");
        assertTrue(Files.exists(file));

        try {
            command.zip("1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = Paths.get("1.txt.zip");
        assertTrue(Files.exists(file));

        command.delete("1.txt.zip");
        command.delete("1.txt");

        command.createDir("7");
        command.createDir("7/2");
        command.createFile("7/3.txt");
        command.createFile("7/2.txt");
        command.createFile("7/2/2.txt");
        command.createFile("7/2/1.txt");
        command.createFile("7/2/3.txt");
        command.createFile("7/2/4.txt");

        file = Paths.get("7");
        assertTrue(Files.exists(file));
        try {
            command.zip("7");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            command.unZip("7.zip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(Files.exists(file));

        command.delete("7.zip");
        assertFalse(Files.exists(Paths.get("7.zip")));

        command.delete("7");
        assertFalse(Files.exists(Paths.get("7")));
    }

    @Test
    public void unZipFailedTest() {
        CommandUtils command = new CommandUtils();

        try {
            command.unZip("");
        } catch (FileNotFoundException e) {
            assertThat(e.getMessage(), is("File not found!"));
        }
    }
}
