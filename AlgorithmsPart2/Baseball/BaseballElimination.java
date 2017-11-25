import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;

public class BaseballElimination {
    
    private final Map<String, Integer> teamtoIdMap;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] gamesLeft;
    private final int numberOfTeams;
    private int maxWins = Integer.MIN_VALUE;
    private String teamAtTop;
    
    public BaseballElimination(String fileName) {
        if (null == fileName) throw new java.lang.IllegalArgumentException();
        In file = new In(fileName);
        numberOfTeams = file.readInt();
        teamtoIdMap = new HashMap<String, Integer>();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        gamesLeft = new int[numberOfTeams][numberOfTeams];
        
        for (int i = 0; i < numberOfTeams; i++) {
            String team = file.readString();
            teamtoIdMap.put(team, i);
            wins[i] = file.readInt();
            losses[i] = file.readInt();
            remaining[i] = file.readInt();
            
            for (int j = 0; j < numberOfTeams; j++) {
                gamesLeft[i][j] = file.readInt();
            }
            
            if (wins[i] > maxWins) {
                maxWins = wins[i];
                teamAtTop = team;
            }
        }
    }
    
    public int numberOfTeams() {
        return numberOfTeams;
    }
    
    public Iterable<String> teams() {
        return teamtoIdMap.keySet();
    }
    
    public int wins(String team) {
        if (!teamtoIdMap.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return wins[teamtoIdMap.get(team)];
    }
    
    public int losses(String team) {
        if (!teamtoIdMap.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return losses[teamtoIdMap.get(team)];
    }
    
    public int remaining(String team) {
        if (!teamtoIdMap.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return remaining[teamtoIdMap.get(team)];
    }
    
    public int against(String team1, String team2) {
        if (!teamtoIdMap.containsKey(team1) || !teamtoIdMap.containsKey(team2)) {
            throw new java.lang.IllegalArgumentException();
        }
        return gamesLeft[teamtoIdMap.get(team1)][teamtoIdMap.get(team2)];
    }
    
    public boolean isEliminated(String team) {
        if (!teamtoIdMap.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        
        int id = teamtoIdMap.get(team);
        if (isTriviallyEliminated(id)) {
            return true;
        }
        
        Graph graph = buildGraphForBaseballElimination(id);
        for (FlowEdge edge : graph.network.adj(graph.source)) {
            if (edge.flow() < edge.capacity()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isTriviallyEliminated(int id) {
        if (wins[id] + remaining[id] < maxWins) {
            return true;
        }
        
        return false;
    }
    
    private Graph buildGraphForBaseballElimination(int id) {
        int n = numberOfTeams();
        int source = n;
        int target = n + 1;
        int gameNode = n + 2;
        int currentMaxWins = wins[id] + remaining[id];
        Set<FlowEdge> edges = new HashSet<FlowEdge>();
        for (int i = 0; i < n; i++) {
            if (i == id || wins[i] + remaining[i] < maxWins) {
                continue;
            }

            for (int j = 0; j < i; j++) {
                if (j == id || gamesLeft[i][j] == 0 || wins[j] + remaining[j] < maxWins) {
                    continue;
                }

                edges.add(new FlowEdge(source, gameNode, gamesLeft[i][j]));
                edges.add(new FlowEdge(gameNode, i, Double.POSITIVE_INFINITY));
                edges.add(new FlowEdge(gameNode, j, Double.POSITIVE_INFINITY));
                gameNode++;
            }
            edges.add(new FlowEdge(i, target, currentMaxWins - wins[i]));
        }

        FlowNetwork network = new FlowNetwork(gameNode);
        for (FlowEdge edge : edges) {
            network.addEdge(edge);
        }
        FordFulkerson ff = new FordFulkerson(network, source, target);
        return new Graph(ff, network, source, target);
    }
    
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamtoIdMap.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        
        int teamId = teamtoIdMap.get(team);
        Set<String> set = new HashSet<>();
        if (isTriviallyEliminated(teamId)) {
            set.add(teamAtTop);
            return set;
        }
        
        Graph g = buildGraphForBaseballElimination(teamId);
        for (FlowEdge edge : g.network.adj(g.source)) {
            if (edge.flow() < edge.capacity()) {
                for (String t : teams()) {
                    int id = teamtoIdMap.get(t);
                    if (g.ff.inCut(id)) {
                        set.add(t);
                    }
                }
            }
        }
        g = null;
        if (set.isEmpty()) {
            return null;
        }
        
        return set;
    }
    
    private class Graph {
        FordFulkerson ff;
        FlowNetwork network;
        int source;
        int target;

        public Graph(FordFulkerson ff, FlowNetwork network, int source, int target) {
            super();
            this.ff = ff;
            this.network = network;
            this.source = source;
            this.target = target;
        }
    }
}