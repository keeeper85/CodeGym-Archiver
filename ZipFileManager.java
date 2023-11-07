package com.codegym.task.task31.task3110;

import com.codegym.task.task31.task3110.exception.NoSuchZipFileException;
import com.codegym.task.task31.task3110.exception.PathNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {

    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception{

        if (Files.notExists(zipFile.getParent()))
            Files.createDirectories(zipFile.getParent());

        if(!Files.isDirectory(source) && !Files.isRegularFile(source))
            throw new PathNotFoundException();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            if (Files.isRegularFile(source))
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path fileName : fileNames) {
                    addNewZipEntry(zipOutputStream, source, fileName);
                }
            }
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {

//        while (in.available() > 0){
//            out.write(in.read());
//        }

        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception{

        Path fullPath = Paths.get(String.valueOf(filePath), String.valueOf(fileName));

        try (InputStream inputStream = Files.newInputStream(fullPath)){
            ZipEntry zipEntry = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(zipEntry);

            copyData(inputStream, zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }

    public List<FileProperties> getFileList() throws Exception{
        if (!Files.isRegularFile(zipFile)) throw new NoSuchZipFileException();

        List<FileProperties> list = new ArrayList<>();

        try(ZipInputStream in = new ZipInputStream(Files.newInputStream(zipFile))){
            ZipEntry zipEntry;
            while ((zipEntry = in.getNextEntry()) != null){

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    copyData(in, baos);
                    list.add(new FileProperties(zipEntry.getName(), zipEntry.getSize(),
                            zipEntry.getCompressedSize(), zipEntry.getMethod()));
                }
            }
            in.closeEntry();
        }

        return list;
    }

    public void extractAll(Path outputFolder) throws Exception {
        // Check whether the zip file exists
        if (!Files.isRegularFile(zipFile)) {
            throw new NoSuchZipFileException();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            // Create the output directory if it doesn't exist
            // probably not necessary cause we do that in the while loop again
            if (Files.notExists(outputFolder))
                Files.createDirectories(outputFolder);

            // Go through the contents of the zip stream (file)
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName);

                // Create the necessary directories
                Path parent = fileFullName.getParent();
                if (Files.notExists(parent))
                    Files.createDirectories(parent);

                try (OutputStream outputStream = Files.newOutputStream(fileFullName)) {
                    copyData(zipInputStream, outputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    public void removeFiles(List<Path> pathList) throws Exception {
        if(!Files.isRegularFile(zipFile))
            throw new NoSuchZipFileException();
        Path tempFile = Files.createTempFile(zipFile.getParent(),"tempFile",  ".zip");
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))){
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if(pathList.contains(Paths.get(zipEntry.getName()))) {
                    ConsoleHelper.writeMessage("Removed this file " + zipEntry.getName());
                }
                else {
                    try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempFile))) {
                        zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
                        copyData(zipInputStream, zipOutputStream);
                        zipOutputStream.closeEntry();
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void removeFile(Path path) throws Exception{
        List<Path> list = new ArrayList<>();
        list.add(path);
        removeFiles(list);
    }

    public void addFiles(List<Path> absolutePathList) throws Exception {
        // Check whether the zip file exists
        if (!Files.isRegularFile(zipFile)) {
            throw new NoSuchZipFileException();
        }

        // Create a temporary file
        Path tempZipFile = Files.createTempFile(null, null);
        List<Path> archiveFiles = new ArrayList<>();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))) {
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {

                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    archiveFiles.add(Paths.get(fileName));

                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    copyData(zipInputStream, zipOutputStream);

                    zipInputStream.closeEntry();
                    zipOutputStream.closeEntry();

                    zipEntry = zipInputStream.getNextEntry();
                }
            }

            // Archive new files
            for (Path file : absolutePathList) {
                if (Files.isRegularFile(file)) {
                    if (archiveFiles.contains(file.getFileName()))
                        ConsoleHelper.writeMessage(String.format("File '%s' already exists in the archive.", file.toString()));
                    else {
                        addNewZipEntry(zipOutputStream, file.getParent(), file.getFileName());
                        ConsoleHelper.writeMessage(String.format("File '%s' was added to the archive.", file.toString()));
                    }
                } else
                    throw new PathNotFoundException();
            }
        }

        // Move the temporary file to the location of the original
        Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public void addFile(Path path) throws Exception{
        List<Path> list = new ArrayList<>();
        list.add(path);
        addFiles(list);
    }

}
