package com.sample.ipl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RelationshipGraph {

	// Graph defined using AdjacentList
	// Using set to remove any duplicate node(s)
	private Map<Node, Set<Node>> adjacencyMap = new HashMap<>();

	public Map<Node, Set<Node>> getAdjacencyMap() {
		return adjacencyMap;
	}

	public void addEdge(Node a, Node b) {
		if (adjacencyMap.containsKey(a) && adjacencyMap.get(a) != null) {
			adjacencyMap.get(a).add(b);
		} else {
			Set<Node> adjacentNodeList = new LinkedHashSet<>();
			adjacentNodeList.add(b);
			adjacencyMap.put(a, adjacentNodeList);
		}
		// Adding a route in reverse direction as it is undirected graph
		addReverseEdge(b, a);
	}

	public void addReverseEdge(Node b, Node a) {
		if (adjacencyMap.containsKey(b) && adjacencyMap.get(b) != null) {
			adjacencyMap.get(b).add(a);
		} else {
			Set<Node> adjacentNodeList = new LinkedHashSet<>();
			adjacentNodeList.add(a);
			adjacencyMap.put(b, adjacentNodeList);
		}
	}

	public void printGraph() {
		
		Set<Node> franchiseList = adjacencyMap.keySet().stream().filter(aNode -> aNode.getType().equals("F")).collect(Collectors.toSet());
		Set<Node> playerList = adjacencyMap.keySet().stream().filter(aNode -> aNode.getType().equals("P")).collect(Collectors.toSet());
		
		System.out.println("Total no. of franchises: " +franchiseList.size());
		System.out.println("Total no. of players: " +playerList.size());
		
		System.out.println("List of franchises:");
		franchiseList.forEach(aNode -> System.out.println(aNode.getName()));

		System.out.println("List of players:");
		playerList.forEach(aNode -> System.out.println(aNode.getName()));
	}

	public void displayPlayers(String franchise) {
		System.out.println("Player List for Franchise>>>" + franchise);
		Optional<Node> fNode = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(franchise))
				.findFirst();
		if (fNode.isPresent()) {
			adjacencyMap.get(fNode.get()).forEach(aNode -> System.out.println(aNode.getName()));
		}
	}

	public void displayFranchises(String playerName) {
		System.out.println("Franchise(s) for player>>>" + playerName);
		Optional<Node> fNode = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(playerName))
				.findFirst();
		if (fNode.isPresent() && adjacencyMap.get(fNode.get()) != null) {
			adjacencyMap.get(fNode.get()).forEach(aNode -> System.out.println(aNode.getName()));
		} else if (adjacencyMap.get(fNode.get()) == null || adjacencyMap.get(fNode.get()).isEmpty()) {
			System.out.println("No Franchise found for>>>" + playerName);
		} else {
			System.out.println("Player not found");
		}
	}

	public void franchiseBuddies(String player1, String player2) {
		Optional<Node> p1Node = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(player1))
				.findFirst();
		Optional<Node> p2Node = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(player2))
				.findFirst();

		if (p1Node.isPresent() && p2Node.isPresent()) {
			List<Node> buddyList = adjacencyMap.get(p1Node.get()).stream()
					.filter(aNode -> adjacencyMap.get(p2Node.get()).contains(aNode)).collect(Collectors.toList());
			if (buddyList != null && !buddyList.isEmpty()) {
				StringBuilder sb = new StringBuilder("Yes, ");
				for (Node aNode : buddyList) {
					sb.append(aNode.getName());
				}
				System.out.println(sb.toString());
			} else {
				System.out.println("They are not buddies");
			}
		}
	}

	public void findPlayerConnect(String player1, String player2) {
		Optional<Node> p1Node = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(player1))
				.findFirst();
		Optional<Node> p2Node = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(player2))
				.findFirst();

		Set<Node> p1Franchises = adjacencyMap.get(p1Node.get());
		Set<Node> p2Franchises = adjacencyMap.get(p2Node.get());
		StringBuilder sb = new StringBuilder();

		for (Node aP1Franchise : p1Franchises) {
			Set<Node> playersForFranchise = getPlayersByFranchise(aP1Franchise.getName());
			// Traversing on each player node to find target Franchise
			for (Node aPlayer : playersForFranchise) {
				Set<Node> otherFranchises = getFranchisesByPlayer(aPlayer.getName());
				for (Node aFranchise : otherFranchises) {
					if (p2Franchises.contains(aFranchise)) {
						sb.append("Yes, ").append(player1).append(" > ").append(aP1Franchise.getName()).append(" > ")
								.append(aPlayer.getName()).append(" > ").append(aFranchise.getName()).append(" > ")
								.append(player2);
						System.out.println(sb.toString());
						break;
					}
				}
			}
		}

		if (sb.length() == 0) {
			System.out.println("No relationship found");
		}

	}

	private Set<Node> getFranchisesByPlayer(String playerName) {
		Optional<Node> fNode = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(playerName))
				.findFirst();
		return adjacencyMap.get(fNode.get());
	}

	private Set<Node> getPlayersByFranchise(String franchiseName) {
		Optional<Node> fNode = adjacencyMap.keySet().stream().filter(aNode -> aNode.getName().equals(franchiseName))
				.findFirst();
		return adjacencyMap.get(fNode.get());
	}

}
