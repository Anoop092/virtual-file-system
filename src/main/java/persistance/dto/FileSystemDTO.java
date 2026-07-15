package persistance.dto;

import model.Permission;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class FileSystemDTO {
    private final UUID id;
    private final String name;
    private final NodeType type;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Set<Permission> permissions;

    public FileSystemDTO(UUID id, String name,LocalDateTime createdAt,LocalDateTime modifiedAt,Set<Permission> permissions,
                         NodeType type){
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        if(permissions == null)  throw new RuntimeException("cannot be null");
        this.permissions = new HashSet<>(permissions);
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Set<Permission> getPermissions(){
        return new HashSet<>(permissions);
    }
}
