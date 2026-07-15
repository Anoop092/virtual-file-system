package cli;

import model.FileSystems;
import persistance.FileSystemMapper;
import persistance.PersistanceService;
import persistance.dto.DirectoryDTO;
import service.VirtualFileSystem;

import java.util.List;
import java.util.Scanner;

public class CLI {
    private final VirtualFileSystem vfs;
    private final Scanner sc;
    private final PersistanceService ps;
    private final FileSystemMapper mapper;
    private boolean flag;

    public CLI(VirtualFileSystem vfs){
        this.vfs = vfs;
        this.sc = new Scanner(System.in);
        this.ps = new PersistanceService();
        this.mapper = new FileSystemMapper();
        this.flag = true;
    }
    public void start(){
       while (flag){
           System.out.print(vfs.pwd()+"> ");
           String input = readCommand();
           CommandLine cl = parser(input);
           executeCommand(cl);
       }

    }
    public String readCommand(){
        return sc.nextLine();
    }
    public CommandLine parser(String str){
       return new CommandParser().parse(str);
    }
    public void executeCommand(CommandLine cl){
        String command = cl.getCommand();
        List<String> args = cl.getArgs();

        switch (command) {
            case "pwd" -> pwd(args);
            case "ls" -> ls(args);
            case "mkdir" -> mkdir(args);
            case "cd" -> cd(args);
            case "touch" -> touch(args);
            case "cat" -> cat(args);
            case "write" -> write(args);
            case "rm" -> rm(args);
            case "rmdir" -> rmdir(args);
            case "rename" -> rename(args);
            case "mv" -> mv(args);
            case "cp" -> cp(args);
            case "tree" -> tree(args);
            case "save" -> save(args);
            case "exit" -> exit(args);
            default -> System.out.println("Unknown command: " + command);
        }

    }
    public void pwd(List<String> args){
        if (!args.isEmpty()){
            System.out.println("Usage: pwd");
            return;
        }
        System.out.println(vfs.pwd());
    }
    public void mkdir(List<String> args){
        if(args.size() != 1){
           System.out.println("Usage: mkdir");
           return;
        }
        vfs.mkdir(args.getFirst());
    }
    public void ls(List<String> args){
        if(!args.isEmpty()){
            System.out.println("Usage: ls");
            return;
        }
        for(FileSystems fs : vfs.ls()){
            System.out.print(fs.getName()+ "  ");
        }
        System.out.println();

    }
    public void cd(List<String> args){
       if(args.size() != 1) {
           System.out.println("Usage:cd <path>");
           return;
       }
       vfs.changeDirectory(args.getFirst());
    }
    public void touch(List<String> args){
        if(args.size() != 1) {
            System.out.println("Usage: touch <file-name>");
            return;
        }
        vfs.touch(args.getFirst());
    }
    public void cat(List<String> args){
        if(args.size() != 1) {
            System.out.println("Usage: cat <file-name>");
            return;
        }
        System.out.println(vfs.cat(args.getFirst()));
    }
    public void write(List<String> args){
        if(args.size() != 1){
            System.out.println("Usage: write <file-name>");
            return;
        }
        System.out.println("Enter : content");
        String input = sc.nextLine();
        vfs.write(args.getFirst(), input);
    }
    public void rm(List<String> args){
        if(args.size() != 1){
            System.out.println("Usage: rm <file-name>");
            return;
        }
        vfs.rm(args.getFirst());

    }
    public void rmdir(List<String> args){
        if(args.size() != 1){
            System.out.println("Usage: rmdir <directory-name>");
            return;
        }
        vfs.rmdir(args.getFirst());

    }
    public void rename(List<String> args){
        if(args.size() != 2){
            System.out.println("Usage: rename <old-name> <new-name>");
            return;
        }
        vfs.rename(args.getFirst(), args.get(1));
    }
    public void mv(List<String> args){
        if(args.size() != 2){
            System.out.println("Usage: mv <source> <destination-directory>");
            return;
        }
        vfs.mv(args.getFirst(), args.get(1));

    }
    public void cp(List<String> args){
        if(args.size() != 2){
            System.out.println("Usage: cp <source-file> <destination-directory>");
            return;
        }
        vfs.copy(args.getFirst(), args.get(1));
    }
    public void tree(List<String> args){
        if(!args.isEmpty()) System.out.println("Usage: tree");
        vfs.printTree();
    }
    public void save(List<String> args){
        DirectoryDTO dto = mapper.toDTO(vfs.getRoot());
        ps.save(dto);
    }
    public void exit(List<String> args){
        if (!args.isEmpty()) {
            System.out.println("Usage: exit");
            return;
        }

        flag = false;
    }


}
