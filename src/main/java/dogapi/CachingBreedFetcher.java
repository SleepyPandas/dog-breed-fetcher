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
    private int callsMade = 0;

    /*
    The Grand idea, first when we get a request, we check the cache, if it is there, we return it,
    Otherwise we call the underlying fetcher, increment the callsMade counter, store the result in the cache,
    and return it. If it is not in the cache, and not in the underlying fetcher, we throw a BreedNotFoundException.
     */

    public CachingBreedFetcher(BreedFetcher fetcher) {

    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
        return new ArrayList<>();
    }

    public int getCallsMade() {
        return callsMade;
    }
}