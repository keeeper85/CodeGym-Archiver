package com.codegym.task.task31.task3110.command;

import com.codegym.task.task31.task3110.ConsoleHelper;
import com.codegym.task.task31.task3110.ZipFileManager;
import com.codegym.task.task31.task3110.exception.PathNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {

        try{
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Which file do you want to add?");
            Path path = Paths.get(ConsoleHelper.readString());
            zipFileManager.addFile(path);
            ConsoleHelper.writeMessage("File added.");
        } catch (PathNotFoundException e){
            ConsoleHelper.writeMessage("You didn't correctly enter a file name or directory.");
        } catch (Exception e){
            throw new RuntimeException();
        }



    }
}
