package persistance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistance.dto.DirectoryDTO;
import persistance.dto.FileSystemDTO;
import persistance.dto.FileSystemDeSerilizer;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class PersistanceService {
    private final Gson gson =  new GsonBuilder()
            .registerTypeAdapter(
                    FileSystemDTO.class,
                    new FileSystemDeSerilizer()
            )
            .create();


    public void save(DirectoryDTO root){
        String json = gson.toJson(root);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("sampledata/vfs.json"))){
            writer.write(json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public DirectoryDTO load(){
        DirectoryDTO dto = null;
        try(BufferedReader reader = new BufferedReader(new FileReader("sampledata/vfs.json"))){

            String result =reader.lines().reduce("", (ans,line)-> ans+line);
            dto = gson.fromJson(result,DirectoryDTO.class);

        }catch (IOException io){
            System.out.println(io);
            return null;
        }
        return dto;
    }
}
