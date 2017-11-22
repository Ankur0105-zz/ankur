import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
    
    private final Map<Integer, String> idToSynsetMap;
    private final Map<String, Set<Integer>> nounToIdMap;
    private final SAP sap;
    
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new java.lang.IllegalArgumentException();
        
        idToSynsetMap = new HashMap<Integer, String>();
        nounToIdMap = new HashMap<String, Set<Integer>>();
        
        initialiseSynset(synsets);
        Digraph graph = initialiseHypernyms(hypernyms);
        
        sap = new SAP(graph);
    }
    
    private void initialiseSynset(String synsetFile) {
        In file = new In(synsetFile);
        
        while (file.hasNextLine()) {
            String[] synsetEntry = file.readLine().split(",");
            int sysnsetId = Integer.parseInt(synsetEntry[0]);
            idToSynsetMap.put(sysnsetId, synsetEntry[1]);
            
            String[] nouns = synsetEntry[1].split(" ");
            for (String noun : nouns) {
                Set<Integer> synsetIds = nounToIdMap.get(noun);
                if (synsetIds == null) {
                    synsetIds = new HashSet<Integer>();
                }
                synsetIds.add(sysnsetId);
                nounToIdMap.put(noun, synsetIds);
            }
        }
    }
    
    private Digraph initialiseHypernyms(String hypernymFile) {
        Digraph graph = new Digraph(idToSynsetMap.size());
        In file = new In(hypernymFile);
        
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            int synsetId = Integer.parseInt(line[0]);
            for (int i = 0; i < line.length; i++) {
                int id = Integer.parseInt(line[i]);
                graph.addEdge(synsetId, id);
            }
        }
        return graph;
    }
    
    public Iterable<String> nouns() {
        return nounToIdMap.keySet();
    }
    
    public boolean isNoun(String word) {
        if (null == word) throw new java.lang.IllegalArgumentException(); 
        return nounToIdMap.containsKey(word);
    }
    
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
        Set<Integer> idsOfNounA = nounToIdMap.get(nounA);
        Set<Integer> idsOfNounB = nounToIdMap.get(nounB);
        return sap.length(idsOfNounA, idsOfNounB);
    }
    
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException();
        Set<Integer> idsOfNounA = nounToIdMap.get(nounA);
        Set<Integer> idsOfNounB = nounToIdMap.get(nounB);
        int ancestor =  sap.ancestor(idsOfNounA, idsOfNounB);
        return idToSynsetMap.get(ancestor);
    }
    
}