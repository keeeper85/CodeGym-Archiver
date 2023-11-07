package com.codegym.task.task31.task3110.command;

import com.codegym.task.task31.task3110.ConsoleHelper;
import com.codegym.task.task31.task3110.ZipFileManager;
import com.codegym.task.task31.task3110.exception.PathNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {

        try{
            ConsoleHelper.writeMessage("Extracting an archive.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter the directory where to extract the archive:");
            zipFileManager.extractAll(Paths.get(ConsoleHelper.readString()));

            ConsoleHelper.writeMessage("Archive extracted.");
        } catch (PathNotFoundException e){
            ConsoleHelper.writeMessage("You didn't correctly enter a file name or directory.");
        } catch (Exception e){
            throw new RuntimeException();
        }

    }
}
