package models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListDeserializer extends StdDeserializer<List<User>> {

    private static final ObjectMapper mapper = new ObjectMapper();
    //private static final String AUTHOR = "author";
    // vielleicht datenfeld mit string für name des feldes im json (buddies, interested, participating)
    private static final CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(List.class, User.class);

    public CustomListDeserializer() {
        this(null);
    }

    public CustomListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<User> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
    // https://stackoverflow.com/questions/27895376/deserialize-nested-array-as-arraylist-with-jackson
        ArrayNode nodeUsers = mapper.readTree(jsonParser);
        //JsonNode nodeUsers = objectNode.get("buddies");

        if (null == nodeUsers                     // if no user node could be found
                || !nodeUsers.isArray()           // or user node is not an array
                || !nodeUsers.elements().hasNext())   // or user node doesn't contain any authors
            return null;

        return mapper.readerFor(collectionType).readValue(nodeUsers);

        //return new ArrayList<>();
    }
}

/* public class AuthorArrayDeserializer extends JsonDeserializer<List<Author>> {



    @Override
    public List<Author> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        ObjectNode objectNode = mapper.readTree(jsonParser);
        JsonNode nodeAuthors = objectNode.get(AUTHOR);

        if (null == nodeAuthors                     // if no author node could be found
                || !nodeAuthors.isArray()           // or author node is not an array
                || !nodeAuthors.elements().hasNext())   // or author node doesn't contain any authors
            return null;

        return mapper.reader(collectionType).readValue(nodeAuthors);
    }
} */
