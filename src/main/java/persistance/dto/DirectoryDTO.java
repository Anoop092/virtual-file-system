package persistance.dto;

import model.Permission;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DirectoryDTO extends FileSystemDTO{
    private final List<FileSystemDTO> children;
    public DirectoryDTO(UUID id, String name, LocalDateTime createdAt, LocalDateTime modifiedAt,
                        Set<Permission> permissions,List<FileSystemDTO> children){
        super(id,name,createdAt,modifiedAt,permissions,NodeType.DIRECTORY);
        this.children = new ArrayList<>(children);
    }
    public List<FileSystemDTO> getChildren(){
        return new ArrayList<>(children);
    }
}
