package com.sample.ipl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainProgram {

	public static void main(String[] args) throws IOException {

		File file = new File("/Volumes/BOOTCAMP/Workspaces/IPLPlayerProblem/src/inputPS10.txt");
		RelationshipGraph graph = new RelationshipGraph();

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line;
		String lineTokens[] = null;
		Node franchiseNode = null;
		Node aNode = null;

		while ((line = br.readLine()) != null) {
			lineTokens = line.split("/");
			for (int i = 0; i < lineTokens.length; i++) {
				if (i == 0) {
					franchiseNode = new Node(lineTokens[i].trim(), "F");
					graph.getAdjacencyMap().put(franchiseNode, null);
				} else {
					aNode = new Node(lineTokens[i].trim(), "P");
					graph.addEdge(franchiseNode, aNode);
				}

			}
		}
		br.close();
		fr.close();
		graph.printGraph();
		System.out.println("========================================");
		graph.displayFranchises("Ben Stokes");
		System.out.println("========================================");
		graph.displayPlayers("RR");
		System.out.println("========================================");
		graph.franchiseBuddies("Krunal Pandya","Kieron Pollard");
		System.out.println("========================================");
		graph.findPlayerConnect("Kedar Jadhav","Ishan Kishan");
		System.out.println("========================================");
	}

}
