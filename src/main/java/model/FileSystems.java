package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class FileSystems {
    private final UUID id;
    private String name;
    private Directory parent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Set<Permission> permissions;

    abstract public long getSize();
    public FileSystems(UUID id, String name, Directory parent,LocalDateTime createdAt, LocalDateTime modifiedAt,
                       Set<Permission> permissions){
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.permissions = new HashSet<>(permissions);
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public FileSystems(String name, Directory parent){
        this.id = UUID.randomUUID();
        this.name = name;
        this.parent = parent;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.permissions = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        touch();
    }

    public Directory getParent() {
        return parent;
    }
    public void setParent(Directory parent){
        this.parent = parent;
    }
    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }
    public LocalDateTime getModifiedAt(){
        return this.modifiedAt;
    }
    public void touch(){
        this.modifiedAt = LocalDateTime.now();
    }
    public boolean hasPermission(Permission permission){
        return this.permissions.contains(permission);
    }
    public void grantPermission(Permission permission){
        this.permissions.add(permission);
    }
    public void revokePermission(Permission permission){
        this.permissions.remove(permission);
    }

    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions);
    }
}
