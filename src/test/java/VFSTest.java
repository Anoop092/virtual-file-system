import model.Directory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.VirtualFileSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VFSTest {
    VirtualFileSystem vfs;
    @BeforeEach
    void setup(){
        this.vfs = new VirtualFileSystem();
    }
    @Test
    void shouldChangeDirectorySuccessfully(){
        vfs.mkdir("docs");
        vfs.changeDirectory("docs");
        assertEquals("/docs",vfs.pwd());
    }
    @Test
    void shouldThrowExceptionDirectoryNameIsNull(){
        assertThrows(RuntimeException.class, ()-> vfs.changeDirectory(null));
    }
    @Test
    void shouldResolvePathSuccessfully(){
        vfs.mkdir("docs");
        vfs.changeDirectory("docs");
        assertEquals("/docs",vfs.pwd());

    }


}
