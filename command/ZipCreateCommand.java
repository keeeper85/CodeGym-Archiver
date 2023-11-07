package com.codegym.task.task31.task3110.command;

import com.codegym.task.task31.task3110.ConsoleHelper;
import com.codegym.task.task31.task3110.ZipFileManager;
import com.codegym.task.task31.task3110.exception.PathNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {

        try{
            ConsoleHelper.writeMessage("Creating an archive.");
//            ZipCreateCommand zipCommand = new ZipCreateCommand();
            ZipFileManager zipFileManager = this.getZipFileManager();

            ConsoleHelper.writeMessage("Enter the full path to the file or directory to be zipped:");
            String path = ConsoleHelper.readString();
            Path sourcePath = Paths.get(path);
            zipFileManager.createZip(sourcePath);

            ConsoleHelper.writeMessage("Archive created.");
        } catch (PathNotFoundException e){
            ConsoleHelper.writeMessage("You didn't correctly enter a file name or directory.");
        } catch (Exception e){
            throw new RuntimeException();
        }



    }
}
