import model.Directory;
import model.FileSystems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryTest {
    Directory root;
    @BeforeEach
    void setUp(){
       this.root = new Directory("/",null);
    }
    @Test
    void shouldCreateDirectorySuccessfully(){
        root.createDirectory("docs");
        FileSystems fs = root.getChild("docs");
        assertNotNull(fs);
        assertEquals(root,fs.getParent());
        assertInstanceOf(Directory.class, fs);
    }
    @Test
    void shouldThrowExceptionWhenDirectoryExists(){
        root.createDirectory("docs");
        assertThrows(RuntimeException.class,() -> {
            root.createDirectory("docs");
        });
        assertEquals(1,root.getChildren().size());

    }
    @Test
    void shouldThrowExceptionWhenDirectoryNameIsNull(){
       assertThrows(RuntimeException.class,()->root.createDirectory(null));
       assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDirectoryNameIsEmpty(){
        assertThrows(RuntimeException.class,()->root.createDirectory(""));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDirectoryNameOnlyContainsWhiteSpaces(){
        assertThrows(RuntimeException.class,()->root.createDirectory("          "));
        assertEquals(0,root.getChildren().size());
    }

}
