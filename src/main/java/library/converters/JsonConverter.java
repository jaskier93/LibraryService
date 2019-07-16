package library.converters;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JsonConverter {

    public ActionJson convertJsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ActionJson.class);
    }
}
