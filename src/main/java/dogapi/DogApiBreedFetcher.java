package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.

        // Basically make the API Call

        if (breed == null || breed.isBlank()) {
            throw new BreedNotFoundException("Breed must be non-empty; Or Bread is invalid");
        }


        String url = "https://dog.ceo/api/breed/" + breed.toLowerCase() + "/list";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .build();


        // Parse the JSON Response

        //return new ArrayList<>();

        try (Response response = client.newCall(request).execute()) {
            // To handle null body and unsuccessful response(s)0
            if (response.body() == null || !response.isSuccessful()) {
                throw new BreedNotFoundException("Breed not found: " + breed);
            }



            String body = response.body().string();
            JSONObject json = new JSONObject(body);

            // Reads the status and message from the JSON response
            if (!"success".equalsIgnoreCase(json.optString("status"))) {
                throw new BreedNotFoundException(json.optString("message", "Breed not found: " + breed));
            }

            JSONArray arr = json.getJSONArray("message");
            List<String> result = new ArrayList<>(arr.length());
            for (int i = 0; i < arr.length(); i++) {
                System.out.println(result);
                result.add(arr.getString(i));
            }
            return result;
        } catch (IOException e) {
            throw new BreedNotFoundException("Failed to fetch breed: " + breed);
        }



    }

//    public void main(String[] args) {
//        List<String> test = getSubBreeds("Mastiff");
//        System.out.println(test);
//    }


}

