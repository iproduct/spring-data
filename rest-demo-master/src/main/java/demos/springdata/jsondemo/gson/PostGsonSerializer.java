package demos.springdata.jsondemo.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import demos.springdata.jsondemo.model.Post;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class PostGsonSerializer implements JsonSerializer<Post> {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public JsonElement serialize(Post post, Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject actorJsonObj = new JsonObject();

        actorJsonObj.addProperty("Id", post.getId());
        actorJsonObj.addProperty("Title", post.getTitle().toUpperCase());

        actorJsonObj.addProperty("Published Date",
                post.getCreated() != null ?
                        sdf.format(post.getCreated()) : null);

        actorJsonObj.addProperty("Author", post.getAuthor()  != null ?
                post.getAuthor().getFname() + " " + post.getAuthor().getLname() : null);

        actorJsonObj.addProperty("Content", post.getContent());

        return actorJsonObj;
    }

//    private String convertFilmography(List<String> filmography) {
//        return filmography.stream()
//                .collect(Collectors.joining("-"));
//    }
}
