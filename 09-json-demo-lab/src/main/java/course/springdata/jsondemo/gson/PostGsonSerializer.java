package course.springdata.jsondemo.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import course.springdata.jsondemo.entity.Post;

import javax.xml.crypto.Data;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class PostGsonSerializer implements JsonSerializer<Post> {
    private static final DateTimeFormatter format =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    @Override
    public JsonElement serialize(Post post, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject postJsonObj = new JsonObject();
        postJsonObj.addProperty("id", post.getId());
        postJsonObj.addProperty("title", post.getTitle());
        postJsonObj.addProperty("content", post.getContent());
        postJsonObj.addProperty("author", post.getAuthor().getFirstName()
        + " " + post.getAuthor().getLastName());
        postJsonObj.addProperty("imageUrl", post.getImageUrl());
        postJsonObj.addProperty("created", post.getCreated().format(format));
        postJsonObj.addProperty("modified", post.getModified().format(format));
        return postJsonObj;
    }
}
