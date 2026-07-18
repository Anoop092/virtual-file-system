import model.Directory;
import model.FileSystems;
import model.Permission;
import model.VirtualFile;
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
    @Test
    void shouldThrowExceptionWhenDirectoryNameMatchesExistingFileName(){
        root.createFile("hello");
        assertThrows(RuntimeException.class,()->root.createDirectory("hello"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldCreateFileSuccessfully(){
       // Act
        root.createFile("hello.txt");
        FileSystems fs = root.getChild("hello.txt");
        // assert
        assertNotNull(fs);
        assertEquals(1,root.getChildren().size());
        assertEquals(root,fs.getParent());
        assertInstanceOf(VirtualFile.class,fs);

    }
    @Test
    void shouldThrowExceptionWhenFileNameExists(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,() -> root.createFile("hello.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenFileNameIsNull(){
        assertThrows(RuntimeException.class, ()-> root.createFile(null));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenFileNameIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> root.createFile(""));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenFileNameIsWhiteSpace(){
        assertThrows(IllegalArgumentException.class, ()-> root.createFile("        "));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenFileNameMatchesExistingDirectoryName(){
        root.createDirectory("hello");
        //act
        assertThrows(IllegalArgumentException.class,()->root.createFile("hello"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenWritePermissionIsMissing(){
        root.revokePermission(Permission.WRITE);
        assertThrows(RuntimeException.class,()->root.createFile("hello"));
        assertEquals(0,root.getChildren().size());

    }
    @Test
    void shouldDeleteDirectorySuccessfully(){
        root.createDirectory("hello");
        FileSystems fs = root.getChild("hello");
        root.deleteDirectory("hello");
        assertNull(fs.getParent());

        fs = root.getChild("hello");
        assertNull(fs);
        assertEquals(0,root.getChildren().size());

    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryWithEmptyName(){
        assertThrows(RuntimeException.class,
                () -> root.deleteDirectory(""));

        assertEquals(0, root.getChildren().size());

    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryIfNameIsNull(){
        assertThrows(RuntimeException.class,
                () -> root.deleteDirectory(null));

        assertEquals(0, root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryHasChildren(){
        root.createDirectory("dir");
        FileSystems fs = root.getChild("dir");
        Directory child = (Directory) fs;
        child.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.deleteDirectory("dir"));
        assertNotNull(child.getChild("hello.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryWithoutWritePermission(){
        root.createDirectory("docs");
        root.revokePermission(Permission.WRITE);
        assertThrows(RuntimeException.class,()-> root.deleteDirectory("docs"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingFileUsingDeleteDirectory(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()-> root.deleteDirectory("hello.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryThatDoesNotExists(){
        assertThrows(RuntimeException.class,()-> root.deleteDirectory("hello"));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldDeleteFileSuccessfully(){
        root.createFile("hello.txt");
        FileSystems fs = root.getChild("hello.txt");
        root.deleteFile("hello.txt");
        assertNull(root.getChild("hello.txt"));
        assertNull(fs.getParent());
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingFileWithoutName(){
        assertThrows(RuntimeException.class, ()->root.deleteFile(null));
        assertEquals(0,root.getChildren().size());

    }
    @Test
    void shouldThrowExceptionWhenDeletingFileWithEmptyName(){
        assertThrows(RuntimeException.class, ()->root.deleteFile(""));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingFileWithNameIsBlank(){
        assertThrows(RuntimeException.class, ()->root.deleteFile("    "));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingFileThatDoesNotExists(){
        assertThrows(RuntimeException.class, ()->root.deleteFile("hello.txt"));
        assertEquals(0,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenDeletingDirectoryWithDeletingFile(){
        root.createDirectory("dir");
        assertThrows(RuntimeException.class,()->root.deleteFile("dir"));
        assertNotNull(root.getChild("dir"));
    }
    @Test
    void shouldThrowExceptionWhenDeletingFileWithoutWritePermission(){
        root.createFile("hello.txt");
        root.revokePermission(Permission.WRITE);
        assertThrows(RuntimeException.class ,()-> root.deleteFile("hello.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldRenameFileSuccessFully(){
        root.createFile("hello.txt");
        FileSystems oldFs = root.getChild("hello.txt");
        root.rename("hello.txt","notes.txt");
        FileSystems newFs = root.getChild("notes.txt");

        assertNull(root.getChild("hello.txt"));
        assertNotNull(root.getChild("notes.txt"));
        assertSame(oldFs,newFs);
        assertEquals(root,root.getChild("notes.txt").getParent());
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenOldNameIsNull(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename(null,"notes.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenNewNameIsNull(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename("hello.txt",null));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenOldNameIsEmpty(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename("","notes.txt"));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenNewFileNameIsEmpty(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename("hello.txt",""));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenNewFileNameIsWhiteSpace(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename("hello.txt","    "));
        assertEquals(1,root.getChildren().size());
    }
    @Test
    void shouldThrowExceptionWhenOldFileNameIsWhiteSpace(){
        root.createFile("hello.txt");
        assertThrows(RuntimeException.class,()->root.rename("       ","notes.txt"));
        assertNull(root.getChild("notes.txt"));
    }
    @Test
    void shouldThrowExceptionWhenFileDoesNotHaveWritePermission(){
        root.createFile("hello.txt");
        root.revokePermission(Permission.WRITE);
        assertThrows(RuntimeException.class,()-> root.rename("hello.txt","new.txt"));
        assertNull(root.getChild("new.txt"));
    }
    @Test
    void shouldThrowExceptionWhileSourceFileAlreadyExists(){
        root.createFile("notes.txt");

        assertThrows(RuntimeException.class,
                () -> root.rename("hello.txt", "notes.txt"));

        assertNotNull(root.getChild("notes.txt"));
        assertNull(root.getChild("hello.txt"));
        assertEquals(1, root.getChildren().size());
    }

    @Test
    void shouldThrowExceptionWhenDestinationAlreadyExists(){
        root.createFile("hello.txt");
        root.createFile("notes.txt");
        assertThrows(RuntimeException.class,()->root.rename("hello.txt","notes.txt"));
    }
    @Test
    void shouldDoNothingWhenOldAndNewNamesAreSame() {
        root.createFile("hello.txt");

        FileSystems original = root.getChild("hello.txt");

        root.rename("hello.txt", "hello.txt");

        assertSame(original, root.getChild("hello.txt"));
        assertEquals(1, root.getChildren().size());
        assertEquals(root, original.getParent());
    }



}
