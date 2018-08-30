package com.epam.homework.CommandLine;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *  Some utilities for working with files
 */
public class CommandUtils {

    public static void createFile(String name) {

        try {
            Path file = Paths.get(name);
            Files.createFile(file);

            System.out.println("File " + file + " was created.");
        } catch (AccessDeniedException ade) {
            System.out.println("Access denied!");
        } catch (FileAlreadyExistsException fae) {
            System.out.println("File already exist.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createDir(String name) {

        try {
            Path dir = Paths.get(name);
            Files.createDirectory(dir);

            System.out.println("Directory " + dir + " was created.");
        } catch (AccessDeniedException ade) {
            System.out.println("Access denied!");
        } catch (FileAlreadyExistsException fae) {
            System.out.println("Directory already exist.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void rename(String begin, String aim) {

        try {
            Path source = Paths.get(begin);
            Path target = Paths.get(aim);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(begin + " was renamed to " + aim);
        } catch (IOException ex) {
            System.out.println("File or directory is not able to renamed.");
        }
    }

    public static void copy(String begin, String aim) {

        try {
            Path source = Paths.get(begin).toAbsolutePath().normalize();
            Path target = Paths.get(aim).toAbsolutePath().normalize();

            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {

                    try {
                        Path medium = source.relativize(file);
                        Path end = target.resolve(medium).normalize();
                        Files.copy(file, end, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        System.out.println("IOException in function visitFile");
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {

                    try {
                        Path medium = source.relativize(dir);
                        Path end = target.resolve(medium);
                        Files.createDirectories(end);
                    } catch (IOException e) {
                        System.out.println("IOException in function preVisitDirectory");
                        e.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Directory " + begin + " was copied to " + aim);

        } catch (NoSuchFileException nsfe) {
            System.out.println("File or directory not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void move(String begin, String aim) {
        try {
            Path source = Paths.get(begin);
            Path target = Paths.get(aim);
            Files.move(source, target);

            System.out.println(begin + " was moved to " + aim);
        } catch (IOException ex) {
            System.out.println("File or directory is not able to moved.");
        }
    }

    public static void delete(String name) {

        try {
            Path target = Paths.get(name);

            if (!Files.isDirectory(target)) {
                Files.delete(target);

                System.out.println("File " + name + " was deleted.");
            } else {
                Files.walkFileTree(target, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {

                        try {
                            Files.delete(file);
                        } catch (IOException ex) {
                            System.out.println("IOException in function visitFile");
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException ex) {

                        try {
                            Files.delete(dir);
                        } catch (IOException e) {
                            System.out.println("IOException in function postVisitDirectory");
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

                System.out.println("Directory " + name + " was deleted.");
            }
        } catch (NoSuchFileException nsfe) {
            System.out.println("File or directory not found.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void showDir(String name) {
        final List<Path> filesList = new ArrayList<>();
        try {
            Path dir = Paths.get(name).toAbsolutePath().normalize();
            if (Files.isDirectory(dir)) {
                Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                        filesList.add(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                        filesList.add(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            Collections.sort(filesList);

            for (Path path : filesList) {
                System.out.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zip(String name) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(name + ".zip"));

        if (!Files.isDirectory(Paths.get(name))) {
            out.putNextEntry(new ZipEntry(name.toString()));
            writeZip(new FileInputStream(name.toString()), out);
        } else {
            final List<Path> pathList = new ArrayList<>();
            Files.walkFileTree(Paths.get(name), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    pathList.add(file);
                    return FileVisitResult.CONTINUE;
                }
            });

            for (Path path : pathList) {
                out.putNextEntry(new ZipEntry(path.toString()));
                writeZip(new FileInputStream(path.toString()), out);
            }
        }
        out.close();
    }

    public static void unZip(String name) throws FileNotFoundException {
        if (name == null) {
            throw new NullPointerException("File must exist!");
        }
        File file = new File(name);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found!");
        }

        try (ZipFile zip = new ZipFile(name)) {
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                Path dir = Paths.get(entry.getName());
                int count = dir.getNameCount();
                if (count > 0) {
                    Files.createDirectories(dir.subpath(0, count - 1));
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(file.getParent(), entry.getName())));
                writeZip(zip.getInputStream(entry), bos);
                bos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeZip(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int lenth;

        while ((lenth = in.read(buffer)) >= 0) {
            out.write(buffer, 0, lenth);
        }
        in.close();
    }
}