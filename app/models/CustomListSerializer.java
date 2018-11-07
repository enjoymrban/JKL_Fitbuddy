package models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomListSerializer extends StdSerializer<List<User>> {

    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(Class<List> t) {
        super(t, false);
    }

    @Override
    public void serialize(
            List<User> buddies,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Long> ids = new ArrayList<>();
        /*for(int i = 0; i < buddies.size(); i++) {
            if(i == 0) {
                ids.add(buddies.get(i).getId());
            } else {
                if(ids.get(i-1) == buddies.get(i).getId()) {
                    System.out.println("");// do nothing, no double ids in this bitch
                } else {
                    ids.add(buddies.get(i).getId());
                }
            }
        } */
        for (User buddy : buddies) {
            ids.add(buddy.getId());
        }
        // remove duplicates
        Set<Long> hs = new HashSet<>();
        hs.addAll(ids);
        ids.clear();
        ids.addAll(hs);
        generator.writeObject(ids);
    }
}