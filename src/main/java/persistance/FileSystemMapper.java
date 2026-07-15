package persistance;

import model.Directory;
import model.FileSystems;
import model.VirtualFile;
import persistance.dto.DirectoryDTO;
import persistance.dto.FileSystemDTO;
import persistance.dto.VirtualFileDTO;

import java.util.*;

public class FileSystemMapper {
    public DirectoryDTO toDTO(Directory fileSystem){
        List<FileSystemDTO> dtos = new ArrayList<>();
        for(FileSystems fs : fileSystem.getChildren()){
            if(fs instanceof VirtualFile){
                VirtualFileDTO dto = new VirtualFileDTO(fs.getId(),fs.getName(),fs.getCreatedAt()
                        ,fs.getModifiedAt(),fs.getPermissions(), ((VirtualFile) fs).read());
                dtos.add(dto);
            }else{
               FileSystemDTO dir = toDTO((Directory) fs);
               dtos.add(dir);
            }
        }
        return new DirectoryDTO(fileSystem.getId(),fileSystem.getName(),fileSystem.getCreatedAt(),
                 fileSystem.getModifiedAt(),fileSystem.getPermissions(),dtos);
    }
    public FileSystems fromDTO(FileSystemDTO dto){
        if (dto instanceof VirtualFileDTO){
            return  new VirtualFile(
                    dto.getId(), dto.getName(), null,dto.getCreatedAt(),dto.getModifiedAt(),
                    dto.getPermissions(), ((VirtualFileDTO) dto).getContent()
            );
        }
        Directory directory = new Directory(dto.getId(), dto.getName(),
                null,dto.getCreatedAt(),dto.getModifiedAt(),dto.getPermissions());
        for(FileSystemDTO fs: ((DirectoryDTO) dto).getChildren()){
            FileSystems child = fromDTO(fs);
            directory.addChild(child);
        }
        return  directory;


    }
}
