package library.JsonTests;

import com.google.gson.Gson;
import library.TestUtils;
import library.models.Author;
import org.junit.Test;

public class JsonTest {
    @Test
    public void test(){
        Gson gson=new Gson();
        Author author= TestUtils.createAuthor();
        String json=gson.toJson(author);
        Author author1=gson.fromJson(json, Author.class);
        System.out.println(author+"\n");
        System.out.println(author1);
    }
}
