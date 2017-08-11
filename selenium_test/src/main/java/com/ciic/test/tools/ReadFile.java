package com.ciic.test.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuecheng on 2017/7/21.
 */
public class ReadFile {
    static   String FILE_PATH="E:\\20170330迁移工具\\20170330迁移工具\\Debug";
    static List<String> list=new ArrayList<>();
    public static void main(String[] args) {
       File f= new File(ReadFile.FILE_PATH);
        nextFile(f,"1");
        System.out.println(ReadFile.list);}
    static  void nextFile(File file,String level){
       // list.add(file.getAbsolutePath()+"_"+level);

        String aa=file.getAbsolutePath();
                aa=aa.substring(3);
                aa=aa.replace("\\","/");
                        aa=aa.replace("/",".");
        File fnew=new File(file.getAbsolutePath().replace(file.getName(),aa));

      //  file.renameTo(fnew);

       // File[ ] fa=fnew.listFiles();
        File[ ] fa=file.listFiles();
        List<File> fl=new ArrayList<>();
        for (File f:fa){if(f.isDirectory()){fl.add(f);}}
        for (int i = 0; i <fl.size(); i++) {nextFile(fl.get(i),level+"."+(i+1));}}}
//    static  void nextFile(FileInfo fileInfo){
//        File[] fa=fileInfo.getFile().listFiles();
//        List<File> fl=new ArrayList<>();
//        for (File f:fa){
//            if(f.isDirectory()){
//                fl.add(f);
//            }
//
//        }
//        if(fl.size()>0){
//            for(File f:fl){
//
//                    FileInfo fileInfo1=new FileInfo();
//                fileInfo1.setFileName(f.getName());
//                fileInfo1.setFilePath(f.getAbsolutePath().replace("\\","/"));
//                fileInfo1.setLevel(fileInfo.getLevel()+1);
//                fileInfo1.setParentFileName(fileInfo.getFileName());
//                fileInfo1.setParentFilePath(fileInfo.getFilePath());
//                fileInfo1.setFile(f);
//                nextFile(fileInfo1);
//
//            }
//
//        }else {
//            list.add(fileInfo);
//
//        }
//
//
//
//    }



// class FileInfo{
//    private String fileName;
//    private String filePath;
//    private String parentFileName;
//    private String parentFilePath;
//    private int level;
//    private  File file;
//
//     public File getFile() {
//         return file;
//     }
//
//     public void setFile(File file) {
//         this.file = file;
//     }
//
//     public String getFileName() {
//         return fileName;
//     }
//
//     public void setFileName(String fileName) {
//         this.fileName = fileName;
//     }
//
//     public String getFilePath() {
//         return filePath;
//     }
//
//     public void setFilePath(String filePath) {
//         this.filePath = filePath;
//     }
//
//     public String getParentFileName() {
//         return parentFileName;
//     }
//
//     public void setParentFileName(String parentFileName) {
//         this.parentFileName = parentFileName;
//     }
//
//     public String getParentFilePath() {
//         return parentFilePath;
//     }
//
//     public void setParentFilePath(String parentFilePath) {
//         this.parentFilePath = parentFilePath;
//     }
//
//     public int getLevel() {
//         return level;
//     }
//
//     public void setLevel(int level) {
//         this.level = level;
//     }
//
//     @Override
//     public String toString() {
//         final StringBuilder sb = new StringBuilder("{");
//         sb.append("\"文件名称\":\"")
//                 .append(fileName).append('\"');
//         sb.append(",\"文件路径\":\"")
//                 .append(filePath).append('\"');
//         sb.append(",\"父文件名称\":\"")
//                 .append(parentFileName).append('\"');
//         sb.append(",\"父文件路径\":\"")
//                 .append(parentFilePath).append('\"');
//         sb.append(",\"层级\":")
//                 .append(level);
//         sb.append('}');
//         return sb.toString();
//     }
// }