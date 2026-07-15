import cli.CLI;
import model.Directory;
import persistance.FileSystemMapper;
import persistance.PersistanceService;
import persistance.dto.DirectoryDTO;
import service.VirtualFileSystem;

public class Main {
    private static final VirtualFileSystem vs = new VirtualFileSystem();
    private static final PersistanceService ps = new PersistanceService();
    private static final FileSystemMapper mapper = new FileSystemMapper();
    public static void main(String[] args) {
        DirectoryDTO dir = ps.load();
        VirtualFileSystem vfs;
        if(dir == null){
            vfs = new VirtualFileSystem();
        }else{
            Directory root = (Directory) mapper.fromDTO(dir);
            vfs = new VirtualFileSystem(root);
        }
        CLI cli = new CLI(vfs);
        cli.start();

    }
}
