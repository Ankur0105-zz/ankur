public class Outcast {
    
    private final WordNet wordNet;
    
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }
    
    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDist = 0;
        
        for (String nounA : nouns) {
            int dist = 0;
            for (String nounB : nouns) {
                if (!nounA.equals(nounB)) {
                    dist += wordNet.distance(nounA, nounB);
                }
            }
            
            if (dist > maxDist) {
                maxDist = dist;
                outcast = nounA;
            }
        }
        
        return outcast;
    }
    
}