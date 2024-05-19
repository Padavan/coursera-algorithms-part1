import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BaseballElimination {
    private Node[] rating;
    private FlowNetwork network;
    private ST<String, Bag<String>> teamCertificatesMap = new ST<String, Bag<String>>();

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        String numberOfTeams = in.readLine();
        int size = Integer.parseInt(numberOfTeams);
        this.rating = new Node[size];

        int index = 0;
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] wordArr = line.trim().split("\\s++");

            String teamName = wordArr[0];
            int wins = Integer.parseInt(wordArr[1]);
            int loses = Integer.parseInt(wordArr[2]);
            int gamesToPlay = Integer.parseInt(wordArr[3]);

            int[] schedule = new int[size];
            for (int i = 0; i < size; i++) {
                schedule[i] = Integer.parseInt(wordArr[i + 4]);
            }

            Node info = new Node(teamName);
            info.wins = wins;
            info.loses = loses;
            info.gamesToPlay = gamesToPlay;
            info.schedule = schedule;
            info.position = index;
            this.rating[index] = info;
            index++;
        }

        // this.result = new Bag[];

        for (int i = 0; i < this.rating.length; i++) {
            Bag<String> bag = this.solution(i);
            this.teamCertificatesMap.put(this.rating[i].teamName, bag);
        }

        // for (String x : this.teamCertificatesMap.keys()) {
        //     for (String y : this.teamCertificatesMap.get(x)) {
        //         System.out.println("bag for " + x + ": " + y);
        //     }
        // }
    }

    private class Node {
        String teamName;
        int wins;
        int loses;
        int gamesToPlay;
        int position;
        int[] schedule;

        public Node(String teamName) {
            this.teamName = teamName;
        }

        public String toString() {
            String str = "";
            str = teamName + ":" + wins + "/" + loses + "/" + gamesToPlay + " - " + Arrays.toString(
                    schedule);
            return str;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return this.rating.length;
    }

    // all teams
    public Iterable<String> teams() {
        Queue<String> queue = new Queue<String>();
        for (Node x : this.rating) {
            queue.enqueue(x.teamName);
        }
        return queue;
    }

    // number of wins for given team
    public int wins(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        Node findedTeam = null;
        for (Node x : this.rating) {
            if (x.teamName.equals(team)) {
                findedTeam = x;
            }
        }

        if (findedTeam == null) {
            throw new IllegalArgumentException();
        }
        return findedTeam.wins;
    }

    // number of losses for given team
    public int losses(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        Node findedTeam = null;
        for (Node x : this.rating) {
            if (x.teamName.equals(team)) {
                findedTeam = x;
            }
        }

        if (findedTeam == null) {
            throw new IllegalArgumentException();
        }
        return findedTeam.loses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        Node findedTeam = null;
        for (Node x : this.rating) {
            if (x.teamName.equals(team)) {
                findedTeam = x;
            }
        }

        if (findedTeam == null) {
            throw new IllegalArgumentException();
        }
        return findedTeam.gamesToPlay;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException();
        }

        Node findedTeam1 = null;
        Node findedTeam2 = null;
        for (Node x : this.rating) {
            if (x.teamName.equals(team1)) {
                findedTeam1 = x;
            }
            if (x.teamName.equals(team2)) {
                findedTeam2 = x;
            }
        }
        if (findedTeam1 == null || findedTeam2 == null) {
            throw new IllegalArgumentException();
        }

        return findedTeam1.schedule[findedTeam2.position];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }


        if (!this.teamCertificatesMap.contains(team)) {
            throw new IllegalArgumentException();
        }

        Bag<String> bag = this.teamCertificatesMap.get(team);
        if (bag.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    private Bag<String> solution(int targetTeam) {
        boolean isEliminated = false;
        Bag<String> bag = new Bag<String>();

        int size = this.rating.length;
        int combination = (size - 1) * (size - 2);
        int numberOfNodes = combination + size + 2;
        FlowNetwork solutionNetwork = new FlowNetwork(numberOfNodes);

        int root = 0;
        int sink = numberOfNodes - 1;

        int targetWins = this.rating[targetTeam].wins + this.rating[targetTeam].gamesToPlay;

        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int teamI = i + combination + 1;
                int teamJ = j + combination + 1;
                if (i == targetTeam || j == targetTeam) {
                    // System.out.println("teamI: " + teamI);
                    continue;
                }
                else if (i == j) {
                    // System.out.println("endEdge: " + teamI + " - " + sink);
                    int capacity = targetWins - this.rating[i].wins;
                    if (capacity < 0) {
                        isEliminated = true;
                        bag.add(this.rating[i].teamName);
                        capacity = 0;
                    }
                    FlowEdge endEdge = new FlowEdge(teamI, sink, capacity);
                    solutionNetwork.addEdge(endEdge);
                }
                else {
                    int capacity = this.rating[i].schedule[j];
                    // System.out.println(
                    // "startEdge: " + 0 + " - " + count + " capacity: " + capacity);
                    FlowEdge startEdge = new FlowEdge(root, count, capacity);
                    solutionNetwork.addEdge(startEdge);

                    // System.out.println("centerEdge: " + count + " - " + teamI);
                    // System.out.println("centerEdge: " + count + " - " + teamJ);
                    FlowEdge centerEdge1 = new FlowEdge(count, teamI, Double.POSITIVE_INFINITY);
                    FlowEdge centerEdge2 = new FlowEdge(count, teamJ, Double.POSITIVE_INFINITY);
                    solutionNetwork.addEdge(centerEdge1);
                    solutionNetwork.addEdge(centerEdge2);

                    count++;

                }
            }
        }

        // trivial exit
        if (isEliminated) {
            return bag;
        }

        FordFulkerson ff = new FordFulkerson(solutionNetwork, 0, sink);

        for (int i = 0; i < size; i++) {
            int teamNode = i + combination + 1;
            boolean inCut = ff.inCut(teamNode);
            if (inCut) {
                isEliminated = true;
                bag.add(this.rating[i].teamName);
            }
            // System.out.println(i + (inCut ? " inCut" : " not inCut"));
        }

        return bag;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || !this.teamCertificatesMap.contains(team)) {
            throw new IllegalArgumentException();
        }

        Bag<String> bag = this.teamCertificatesMap.get(team);
        return bag;
    }

    private int getTeamEdge(int position) {
        int size = this.rating.length;
        return (size - 1) * (size - 2) + 1 + position;
    }

    private int getSinkEdge() {
        int size = this.rating.length;
        return (size - 1) * (size - 2) + size + 1;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}