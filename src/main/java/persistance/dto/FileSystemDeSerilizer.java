package persistance.dto;

import com.google.gson.*;

import java.lang.reflect.Type;

public class FileSystemDeSerilizer implements JsonDeserializer<FileSystemDTO> {

    @Override
    public FileSystemDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String t = obj.get("type").getAsString();
        NodeType nodeType = NodeType.valueOf(t);
        if(nodeType == NodeType.FILE){
            return jsonDeserializationContext.deserialize(jsonElement,VirtualFileDTO.class);
        }
        return  jsonDeserializationContext.deserialize(jsonElement,DirectoryDTO.class);


    }
}
