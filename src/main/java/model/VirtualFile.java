package model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class VirtualFile extends FileSystems{
    private String content;
    public VirtualFile(String name, Directory parent){
        super(name,parent);
        grantPermission(Permission.READ);
        grantPermission(Permission.WRITE);
        this.content = "";

    }
    public VirtualFile(UUID id, String name, Directory parent, LocalDateTime createdAt, LocalDateTime modifiedAt,
                       Set<Permission> permissions, String content){
        super(id,name,parent,createdAt,modifiedAt,permissions);
        this.content = content;
    }
    public void write(String str){
        if(!this.hasPermission(Permission.WRITE)) throw new IllegalArgumentException("You don't have permission to this file");
        this.content = str;
        touch();
    }
    public String read(){
        if(!this.hasPermission(Permission.READ)) throw new IllegalArgumentException("permission denied");
        return this.content;
    }
    public VirtualFile copy(){
        VirtualFile copiedFile = new VirtualFile(this.getName(),null);
        copiedFile.write(this.content);
        return copiedFile;
    }

    @Override
    public long getSize() {
        return this.content.length();
    }
}
