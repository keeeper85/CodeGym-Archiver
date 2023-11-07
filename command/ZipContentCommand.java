package com.codegym.task.task31.task3110.command;

import com.codegym.task.task31.task3110.ConsoleHelper;
import com.codegym.task.task31.task3110.FileProperties;
import com.codegym.task.task31.task3110.ZipFileManager;

public class ZipContentCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Viewing contents of the archive.");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Archive contents:");

        for (FileProperties files : zipFileManager.getFileList()){
            ConsoleHelper.writeMessage(files.toString());
        }

        ConsoleHelper.writeMessage("Archive contents viewed.");



    }
}
