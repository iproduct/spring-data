package demos.springdata.jsondemo.gson;

import com.google.gson.*;
import demos.springdata.jsondemo.exception.InvalidEntityException;
import demos.springdata.jsondemo.model.Post;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostGsonDeserializer implements JsonDeserializer<Post> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Post deserialize(JsonElement json, Type type,
         JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement jsonId = jsonObject.get("id");
        JsonElement jsonTile = jsonObject.get("title");
//        JsonArray jsonContent = jsonObject.getAsJsonArray("filmography");
        JsonElement jsonContent = jsonObject.get("content");
        JsonElement jsonAuthorId = jsonObject.get("authorId");
        JsonElement jsonCreated = jsonObject.get("created");

        Post post = new Post(jsonTile.getAsString().toUpperCase(), jsonContent.getAsString());
        if(jsonId != null) {
            post.setId(jsonId.getAsLong());
        }
        if(jsonAuthorId != null) {
            post.setAuthorId(jsonAuthorId.getAsLong());
        }
        if(jsonCreated != null) {
            try {
                post.setCreated(sdf.parse(jsonCreated.getAsString()));
            } catch (ParseException e) {
                throw new InvalidEntityException("Error parsing creation date: " + e.getMessage());
            }
        }
        return post;
    }
}
