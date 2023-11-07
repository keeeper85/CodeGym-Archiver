package com.codegym.task.task31.task3110;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private Path rootPath;
    private List<Path> fileList = new ArrayList<>();

    public FileManager(Path rootPath) throws IOException {
        this.rootPath = rootPath;
        collectFileList(rootPath);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException{
        if (Files.isRegularFile(path)){
//            this.rootPath = path;
//            fileList.add(rootPath);

            Path relativePath = path.subpath(rootPath.getNameCount(), path.getNameCount());
            fileList.add(relativePath);

        }
        if (Files.isDirectory(path)){
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);

            for (Path files : directoryStream){

                collectFileList(files);
            }

            directoryStream.close();
        }

    }
}
