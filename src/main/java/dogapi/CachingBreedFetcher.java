package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class

    /*
    The Grand idea, first when we get a request, we check the cache, if it is there, we return it,
    Otherwise we call the underlying fetcher, increment the callsMade counter, store the result in the cache,
    and return it. If it is not in the cache, and not in the underlying fetcher, we throw a BreedNotFoundException.
     */

    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        // store the injected fetcher for delegation
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (breed == null || breed.isBlank()) {
            // Let the behavior be consistent with other implementations: treat invalid input as not found
            throw new BreedNotFoundException(breed);
        }

        String key = breed.toLowerCase();

        List<String> cached = cache.get(key);
        if (cached != null) {
            return new ArrayList<>(cached);
        }

        callsMade++;
        List<String> result = fetcher.getSubBreeds(breed);
        cache.put(key, result);
        return result;

    }

    public int getCallsMade() {
        return callsMade;
    }
}