package persistance.dto;

import model.Permission;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class VirtualFileDTO extends FileSystemDTO{
    private final String content;
    public VirtualFileDTO(UUID id, String name, LocalDateTime createAt, LocalDateTime modifiedAt, Set<Permission> permissions,String content){
        super(id,name,createAt,modifiedAt,permissions,NodeType.FILE);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
