package com.codegym.task.task31.task3110;

public class FileProperties {

    private String name;
    private long size;
    private long compressedSize;
    private int compressionMethod;

    public FileProperties(String name, long size, long compressedSize, int compressionMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressionMethod = compressionMethod;
    }

    public long getCompressionRatio(){
//        100 - ((compressedSize * 100) / size)
        return (100 - ((compressedSize * 100) / size));
    }

    @Override
    public String toString() {

        if (size > 0){
//            name size KB (compressedSize KB) compression: compressionRatio%
            int sizeKB = (int) (size / 1024);
            int comprKB = (int) (compressedSize / 1024);
            int ratio = (int) getCompressionRatio();
            String txt = String.format("%s %d KB (%d KB) compression: %d%%", name, sizeKB, comprKB, ratio);


            String otherWay = name + " " + size + " KB (" + comprKB + " KB) compression: " + ratio + "%";
            return txt;
        }

        return name;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compressionMethod;
    }
}
