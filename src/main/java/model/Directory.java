package model;

import java.time.LocalDateTime;
import java.util.*;

public class Directory extends FileSystems{
    private Map<String, FileSystems> childrens;
    public Directory(String name, Directory parent){
        super(name,parent);
        super.grantPermission(Permission.READ);
        super.grantPermission(Permission.WRITE);
        super.grantPermission(Permission.EXECUTE);
        childrens = new HashMap<>();
    }
    public Directory(UUID id,String name, Directory parent, LocalDateTime createdAt,
                     LocalDateTime modifiedAt, Set<Permission> permissions){
        super(id,name,parent,createdAt,modifiedAt,permissions);
        childrens = new HashMap<>();

    }
    public void addChild(FileSystems fs){
        if(fs == null) throw new RuntimeException("The Directory cannot be null");
        if(this.childrens.containsKey(fs.getName())) throw new IllegalArgumentException("The file/directory already exist with name "
                + fs.getName());
        fs.setParent(this);
        childrens.put(fs.getName(),fs);
        touch();
    }
    private boolean isEmpty(){
        return this.childrens.isEmpty();
    }
    public void createDirectory(String name){
        if(name == null) throw new RuntimeException("name cannot be null");
        if(name.isBlank()) throw  new RuntimeException("Cannot be empty");
        if(!hasPermission(Permission.WRITE)) throw new RuntimeException("Permission denied");
        if(childrens.containsKey(name)) throw  new RuntimeException("Directory already exist");
        FileSystems directory = new Directory(name,this);
        addChild(directory);
    }
    public void removeChild(FileSystems child){
        childrens.remove(child.getName());
        child.setParent(null);
        touch();
    }
    public FileSystems getChild(String name){
        return this.childrens.get(name);
    }
    public void deleteDirectory(String name){
        if(name == null) throw new RuntimeException("name cannot be null");
        if(name.isBlank()) throw new RuntimeException("name cannot be blank");
        if(!hasPermission(Permission.WRITE)) throw new RuntimeException("Permission Denied");
        FileSystems child = getChild(name);
        if(child == null) throw new RuntimeException("No such Directory");
        if(!(child instanceof  Directory)){
            throw new RuntimeException("Given name does not belong to Directory");
        }
        Directory dir = (Directory)child ;
        if(!dir.isEmpty()) throw new RuntimeException("unable to perform this operations files and directory exists");
        removeChild(dir);

    }
    public List<FileSystems> getChildren(){
        if(!hasPermission(Permission.READ)) throw new RuntimeException("Permission denied...");
        return new ArrayList<>(childrens.values());
    }
    public void createFile(String name){
        if(name == null) throw new RuntimeException("file name cannot be null");
        if(name.isBlank()) throw  new IllegalArgumentException("Cannot be empty");
        if(!hasPermission(Permission.WRITE)) throw new RuntimeException("permission denied");
        if(childrens.containsKey(name)) throw  new IllegalArgumentException("File already exist");
        FileSystems fs = new VirtualFile(name,this);
        addChild(fs);
    }
    public void deleteFile(String name){
        if(name == null) throw new RuntimeException("name cannot be null");
        if(name.isBlank()) throw new RuntimeException("name cannot be blank");
        if(!hasPermission(Permission.WRITE)) throw new RuntimeException("Permission Denied");
        FileSystems child = getChild(name);
        if(child == null) throw new RuntimeException("No such Directory");
        if(!(child instanceof  VirtualFile)){
            throw new RuntimeException("rm operation is only supported for files");
        }
        removeChild(child);

    }
    public void rename(String name,String newName){
        if(name.isBlank() || newName.isBlank()) throw new RuntimeException("name cannot be blank");
        if(!hasPermission(Permission.WRITE)) throw new RuntimeException("Permission Denied");
        if (name.equals(newName)) return;
        FileSystems child = getChild(name);
        if(child == null) throw new RuntimeException("No such Directory");
        if(this.childrens.containsKey(newName)) throw  new IllegalArgumentException("Directory or File exits with this name "+ newName);
        removeChild(child);
        child.setName(newName);
        addChild(child);

    }
    public List<FileSystems> find(List<FileSystems> result, String name){
        if(!hasPermission(Permission.READ)) throw new RuntimeException("Permission Denied");
        if(this.getName().equals(name)){
            result.add(this);
        }
        for(FileSystems child : childrens.values()){
            if(child instanceof  VirtualFile){
                if(child.getName().equals(name)) {
                    result.add(child);
                }
            }else {
                Directory dir = (Directory) child;
                dir.find(result, name);
            }

        }
        return result;
    }
    public void printTree(int level){
        if(!hasPermission(Permission.READ)) throw new RuntimeException("Permission Denied");
        System.out.println(" ".repeat(level) + this.getName());
        for(FileSystems fis :this.childrens.values()){
            if(fis instanceof  VirtualFile){
                System.out.println(" ".repeat(level * 2)+ fis.getName());
            }
            else{
                Directory dir = (Directory) fis;
                dir.printTree(level+1);
            }
        }

    }
    public VirtualFile getCopy(String name){
        if(!hasPermission(Permission.READ)) throw new RuntimeException("Permission Denied");
        FileSystems fis = getChild(name);
        if(!(fis instanceof VirtualFile)) throw new IllegalArgumentException("excepted filename but recived directory");
        VirtualFile file = (VirtualFile) fis;
        file = file.copy();
        return file;
    }
    public Directory copyRecursively() {
        if(!hasPermission(Permission.READ)) throw new RuntimeException("Permission Denied");
        Directory newDir = new Directory(this.getName(), null);
        for (FileSystems fs : this.childrens.values()) {
            if (fs instanceof VirtualFile) {
                VirtualFile copiedFile = (VirtualFile) ((VirtualFile) fs).copy();
                newDir.addChild(copiedFile);
            } else {
                FileSystems child = ((Directory) fs).copyRecursively();
                newDir.addChild(child);
            }
        }
        return newDir;
    }

    @Override
    public long getSize() {
        long sum = 0;
        for(FileSystems fs :this.childrens.values()){
            sum += fs.getSize();
        }
        return sum;
    }
}
