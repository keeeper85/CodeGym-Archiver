package com.codegym.task.task31.task3110;

import com.codegym.task.task31.task3110.command.ExitCommand;
import com.codegym.task.task31.task3110.exception.NoSuchZipFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Archiver {

    public static void main(String[] args) {

        Operation operation = null;

        do {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);

            } catch (Exception e){
                if (e instanceof NoSuchZipFileException){
                    ConsoleHelper.writeMessage("You didn't select an archive or you selected an invalid file.");
                    continue;
                }
                ConsoleHelper.writeMessage("An error occurred. Please check the entered data.");
            }

        } while (operation != Operation.EXIT);

//        ConsoleHelper.writeMessage("Enter the full path to the archive: ");
//
//        ZipFileManager zipFileManager = new ZipFileManager(Paths.get(ConsoleHelper.readString()));
//        ConsoleHelper.writeMessage("Enter the full path to the file to be zipped: ");
//
//        zipFileManager.createZip(Paths.get(ConsoleHelper.readString()));
//
//        try {
//            CommandExecutor.execute(Operation.EXIT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }






    }

    public static Operation askOperation(){
        ConsoleHelper.writeMessage("Select an operation:");
        ConsoleHelper.writeMessage("0 - Zip files into an archive");
        ConsoleHelper.writeMessage("1 - Add a file to an archive");
        ConsoleHelper.writeMessage("2 - Remove a file from an archive");
        ConsoleHelper.writeMessage("3 - Extract an archive");
        ConsoleHelper.writeMessage("4 - View the contents of an archive");
        ConsoleHelper.writeMessage("5 - Exit");

        int choice = ConsoleHelper.readInt();

        switch (choice){
            case 0:
                return Operation.CREATE;
            case 1:
                return Operation.ADD;
            case 2:
                return Operation.REMOVE;
            case 3:
                return Operation.EXTRACT;
            case 4:
                return Operation.CONTENT;
            case 5:
                return Operation.EXIT;
            default:
                ConsoleHelper.writeMessage("Wrong number!");
                return Operation.EXIT;

        }
    }
}
