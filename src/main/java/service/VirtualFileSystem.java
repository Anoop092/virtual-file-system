package service;

import model.Directory;
import model.FileSystems;
import model.Permission;
import model.VirtualFile;
import persistance.FileSystemMapper;
import persistance.PersistanceService;
import persistance.dto.DirectoryDTO;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VirtualFileSystem {
    // points to root folder
    private Directory root;
    // points to current folder;
    private Directory currentDirectory;

    public VirtualFileSystem(){
        this(new Directory("/",null));
    }
    public VirtualFileSystem(Directory root ){
        this.root = root;
        this.currentDirectory = this.root;
    }
    public Directory getRoot() {
        return this.root;
    }
    public Directory resolvePath(String path){
        if(path.isBlank()) throw new IllegalArgumentException("path cannot be empty");
        String[] pathToTransverse = path.split("/");
        // find directory
        Directory temp = currentDirectory;
        if(path.startsWith("/")){
            temp  = root;
            pathToTransverse = Arrays.copyOfRange(pathToTransverse,1,pathToTransverse.length);
        }
        if(!temp.hasPermission(Permission.EXECUTE)) throw new RuntimeException("Permission denied");

        FileSystems child;
        for(String str : pathToTransverse){
            if("..".equals(str)) {
                temp = temp.getParent();
                if(temp == null) temp = root;
                continue;
            }
            if(".".equals(str)) continue;
            child = temp.getChild(str);
            if(child == null) {
                throw new RuntimeException("no such directory");
            }
            if(!(child  instanceof Directory)) {
                throw new RuntimeException("provided file path instead " +
                        "of directory");
            }
            if(!child.hasPermission(Permission.EXECUTE))  throw new RuntimeException("Permission denied");
            temp = (Directory) child;

        }
        return temp;
    }

    public Directory changeDirectory(String path){
       return currentDirectory = resolvePath(path);
    }
    public List<FileSystems> ls() {
        return currentDirectory.getChildren();
    }
    public void mkdir(String name){
            currentDirectory.createDirectory(name);
    }
    public void rmdir(String name){
        currentDirectory.deleteDirectory(name);
    }
    public void touch(String name){
        currentDirectory.createFile(name);
    }
    public String cat(String name){
        FileSystems fs = currentDirectory.getChild(name);
        if(!(fs instanceof VirtualFile)) throw new IllegalArgumentException("cannot perform actions on Directory");
        return ((VirtualFile) fs).read();
    }
    public void write(String name, String content){
        FileSystems fs = currentDirectory.getChild(name);
        if(!(fs instanceof VirtualFile)) throw new IllegalArgumentException("Write operation is only supported for files");
        VirtualFile file = (VirtualFile) fs;
        file.write(content);
    }
    public void rm(String name){
        currentDirectory.deleteFile(name);
    }
    public void rename(String name,String newName ){
            currentDirectory.rename(name, newName);
    }
    public void mv(String current,String destinationDir){
        Directory dest = resolvePath(destinationDir);
        FileSystems child = currentDirectory.getChild(current);
        if((!currentDirectory.hasPermission(Permission.WRITE)) || !dest.hasPermission(Permission.WRITE))
            throw new RuntimeException("permission denied");
        if(child == dest.getParent()) return;

        Directory temp = dest;
        while(temp != null){
            if(temp == child) return;
            temp = temp.getParent();
        }

        currentDirectory.removeChild(child);
        dest.addChild(child);

    }
    public String pwd(){
        FileSystems temp = currentDirectory;
        if(temp.getName().equals( "/")) return "/";
        List<String> folders = new ArrayList<>();
        while(temp != root){
            folders.add(temp.getName());
            temp = temp.getParent();
        }
        List<String> reversed = folders.reversed();
        StringBuilder sb = new StringBuilder();
        for(String str : reversed){
            sb.append("/");
            sb.append(str);
        }
        return  sb.toString();
    }
    public void printTree(){
        root.printTree(0);
    }
    public void copy(String from , String to){
        Directory destination = resolvePath(to);
        if(!destination.hasPermission(Permission.WRITE)) throw new RuntimeException("permission denied");
        VirtualFile copiedFile = currentDirectory.getCopy(from);
        destination.addChild(copiedFile);
    }
    public void cpRecursively(String src,String dest){
        if(src.isBlank() || dest.isBlank()) throw new IllegalArgumentException("Given parameters are empty");
        Directory newDir = resolvePath(dest);
        if(newDir == null) throw new IllegalArgumentException("Directory not found");
        Directory srcDirectory = resolvePath(src);
        Directory temp =  newDir;
        while(temp != null){
            if(temp == srcDirectory){
                throw new IllegalArgumentException(
                        "Cannot copy a directory into one of its descendants."
                );
            }
            temp = temp.getParent();
        }

        Directory fs = srcDirectory.copyRecursively();
        newDir.addChild(fs);
    }




}
